/*
(C) 2023, Andrea Boni
This file is part of NMEAMessages.
NMEAMessages is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
NMEAMessages is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with NMEAMessages.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.aboni.nmea.n2k.impl;

import com.aboni.nmea.n2k.*;
import com.aboni.nmea.n2k.messages.N2KMessageFactory;
import com.aboni.utils.TimestampProvider;
import com.aboni.log.Log;
import com.aboni.log.SafeLog;
import com.aboni.log.LogStringBuilder;
import com.aboni.utils.Utils;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class N2KFastCacheImpl implements N2KFastCache {

    private static final long REMOVE_TIMEOUT = 2000;
    public static final String N2K_FAST_CACHE_CATEGORY = "N2KFastCache";
    public static final String MESSAGE_KEY_NAME = "message";
    public static final String FAST_MESSAGE_HANDLING_KEY_NAME = "fast message handling";
    public static final String ERROR_TYPE_KEY_NAME = "error type";

    private static class Stats {
        private long fast;
        private long recv;
        private long outOfSequenceErrors;
        private long parseErrors;
        private long unknownErrors;


        public long getFast() {
            return fast;
        }

        public long getRecv() {
            return recv;
        }

        public long getOutOfSequenceErrors() {
            return outOfSequenceErrors;
        }

        public long getParseErrors() {
            return parseErrors;
        }

        public long getUnknownErrors() {
            return unknownErrors;
        }

        void reset() {
            synchronized (this) {
                recv = 0;
                fast = 0;
                unknownErrors = 0;
                parseErrors = 0;
                outOfSequenceErrors = 0;
            }
        }

        void incrFast() {
            synchronized (this) {
                fast++;
            }
        }

        void incrRecv() {
            synchronized (this) {
                recv++;
            }
        }

        void incrOutOfSeq() {
            synchronized (this) {
                outOfSequenceErrors++;
            }
        }


        void incrParse() {
            synchronized (this) {
                parseErrors++;
            }
        }


        void incrUnknown() {
            synchronized (this) {
                unknownErrors++;
            }
        }
    }

    private static class Payload {
        N2KMessageParser parser;
        long lastTS;
    }

    private static class N2KFastEnvelope {
        int pgn;
        int src;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            N2KFastEnvelope that = (N2KFastEnvelope) o;
            return pgn == that.pgn &&
                    src == that.src;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pgn, src);
        }
    }

    private final Stats stats = new Stats();

    private N2KMessageHandler callback;

    private final TimestampProvider timestampProvider;

    private final N2KMessageFactory messageFactory;

    private final Log log;

    private final Map<N2KFastEnvelope, Payload> cache;

    private final N2KMessageParserFactory parserFactory;

    private long lastStatsTime;

    @Inject
    public N2KFastCacheImpl(Log log, TimestampProvider tsp, N2KMessageFactory messageFactory,
                            N2KMessageParserFactory parserFactory) {
        if (tsp==null) throw new IllegalArgumentException("Timestamp provider is null");
        if (messageFactory==null) throw new IllegalArgumentException("Message factory is null");
        if (parserFactory==null) throw new IllegalArgumentException("N2K Parser factory is null");
        this.log = SafeLog.getSafeLog(log);
        this.cache = new HashMap<>();
        this.timestampProvider = tsp;
        this.messageFactory = messageFactory;
        this.parserFactory = parserFactory;
    }

    @Override
    public void setCallback(N2KMessageHandler callback) {
        this.callback = callback;
    }

    @Override
    public void onMessage(N2KMessage msg) {
        if (msg!=null) {
            N2KFastEnvelope id = new N2KFastEnvelope();
            id.pgn = msg.getHeader().getPgn();
            id.src = msg.getHeader().getSource();
            stats.incrRecv();
            if (messageFactory.isSupported(id.pgn)) {
                if (messageFactory.isFast(id.pgn)) {
                    handleFastMessage(msg, id);
                } else if (callback != null) {
                    N2KMessageParser p = parserFactory.getNewParser();
                    try {
                        p.addMessage(msg);
                        callback.onMessage(p.getMessage());
                    } catch (Exception e) {
                        log.error(() -> LogStringBuilder.start(N2K_FAST_CACHE_CATEGORY).wO("message received").wV(MESSAGE_KEY_NAME, msg).toString(), e);
                    }
                }
            }
        }
    }

    private void handleFastMessage(N2KMessage msg, N2KFastEnvelope id) {
        stats.incrFast();
        int seqId = msg.getData()[0] & 0xFF;
        N2KMessageParser p = getN2KMessageParser(id);
        if (!isEmpty(p) && seqId != (p.getFastSequenceNo() + 1) && isPotentialFirstMessage(msg)) {
            p = handlePrematureEndOfMessage(id, p);
        } else if (isEmpty(p) && !isPotentialFirstMessage(msg)) {
            // not a "start of sequence" but no parser is available - skip it because the previous messages were lost
            return;
        }

        boolean remove = false;
        try {
            p.addMessage(msg);
            if (!p.needMore()) {
                remove = true;
                if (callback != null) callback.onMessage(p.getMessage());
            }
        } catch (PGNFastException e) {
            stats.incrOutOfSeq();
            log.debug(() -> LogStringBuilder.start(N2K_FAST_CACHE_CATEGORY).wO(FAST_MESSAGE_HANDLING_KEY_NAME).wV(MESSAGE_KEY_NAME, msg).wV(ERROR_TYPE_KEY_NAME, "out of sequence message").toString());
            remove = true;
        } catch (PGNDataParseException e) {
            stats.incrParse();
            log.debug(() -> LogStringBuilder.start(N2K_FAST_CACHE_CATEGORY).wO(FAST_MESSAGE_HANDLING_KEY_NAME).wV(MESSAGE_KEY_NAME, msg).wV(ERROR_TYPE_KEY_NAME, "parsing").toString());
            remove = true;
        } catch (Exception e) {
            stats.incrUnknown();
            log.errorForceStacktrace(() -> LogStringBuilder.start(N2K_FAST_CACHE_CATEGORY).wO(FAST_MESSAGE_HANDLING_KEY_NAME).wV(MESSAGE_KEY_NAME, msg).toString(), e);
            remove = true;
        }
        if (remove) {
            synchronized (cache) {
                cache.remove(id);
            }
        }
    }

    private N2KMessageParser handlePrematureEndOfMessage(N2KFastEnvelope id, N2KMessageParser p) {
        // "start of sequence" has arrived but there's already a running sequence
        if (callback != null) {
            try {
                callback.onMessage(p.getMessage());
            } catch (PGNDataParseException e) {
                log.error(() -> LogStringBuilder.start(N2K_FAST_CACHE_CATEGORY).wO(FAST_MESSAGE_HANDLING_KEY_NAME).wV(ERROR_TYPE_KEY_NAME, "premature end of message").toString(), e);
            }
        }
        synchronized (cache) {
            cache.remove(id);
        }
        p = getN2KMessageParser(id);
        return p;
    }

    private boolean isEmpty(N2KMessageParser p) {
        return p.getLength() == 0;
    }

    private boolean isPotentialFirstMessage(N2KMessage msg) {
        int seqId = msg.getData()[0] & 0xFF;
        return (seqId & 0x0F) == 0;
    }

    private N2KMessageParser getN2KMessageParser(N2KFastEnvelope id) {
        N2KMessageParser p;
        synchronized (cache) {
            Payload entry = cache.getOrDefault(id, null);
            if (entry == null) {
                entry = new Payload();
                p = parserFactory.getNewParser();
                entry.parser = p;
                cache.put(id, entry);
            } else {
                p = entry.parser;
            }
            entry.lastTS = (timestampProvider != null) ? timestampProvider.getNow() : System.currentTimeMillis();
        }
        return p;
    }

    @Override
    public void cleanUp() {
        long now = (timestampProvider != null) ? timestampProvider.getNow() : System.currentTimeMillis();
        synchronized (cache) {
            cache.entrySet().removeIf(e -> Utils.isOlderThan(e.getValue().lastTS, now, REMOVE_TIMEOUT));
        }
    }

    @Override
    public void onTimer() {
        long now = timestampProvider.getNow();
        if (Utils.isOlderThan(lastStatsTime, now, 30000)) {
            lastStatsTime = now;
            synchronized (stats) {
                log.info(() -> LogStringBuilder.start(N2K_FAST_CACHE_CATEGORY).wO("Stats").wV("recv", stats.getRecv()).wV("fast", stats.fast)
                        .wV("oosError", stats.getOutOfSequenceErrors()).wV("parseError", stats.getParseErrors())
                        .wV("unknownErros", stats.getUnknownErrors()).toString());
                stats.reset();
            }
        }
    }
}
