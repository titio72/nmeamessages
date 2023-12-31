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
import com.aboni.log.Log;
import com.aboni.log.SafeLog;
import com.aboni.log.LogStringBuilder;
import com.google.common.hash.HashCode;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class N2KStreamImpl implements N2KStream {

    private static final long MAX_AGE = 750L;
    private static final long MIN_AGE = 250L;

    private final boolean throttling;

    private static class Payload {
        int hashcode;
        long timestamp;
    }

    private final Map<Integer, Payload> payloadMap;
    private final Log logger;
    private final PGNSourceFilter srcFilter;
    private final N2KMessageParserFactory parserFactory;

    @Inject
    public N2KStreamImpl(Log log, PGNSourceFilter pgnSrcFilter, N2KMessageParserFactory parserFactory) {
        this(log, false, pgnSrcFilter, parserFactory);
    }

    public N2KStreamImpl(Log logger, boolean throttling, PGNSourceFilter pgnSrcFilter, N2KMessageParserFactory parserFactory) {
        if (parserFactory==null) throw new IllegalArgumentException("N2K message parser factory is null");
        if (pgnSrcFilter==null) throw new IllegalArgumentException("PGN filter is null");
        this.logger = SafeLog.getSafeLog(logger);
        this.srcFilter = pgnSrcFilter;
        this.parserFactory = parserFactory;
        this.throttling = throttling;
        payloadMap = new HashMap<>();
    }

    @Override
    public N2KMessage getMessage(String sMessage) {
        try {
            N2KMessageParser p = parserFactory.getNewParser();
            p.addString(sMessage);
            int pgn = p.getHeader().getPgn();
            if (p.isSupported() && srcFilter.accept(p.getHeader().getSource(), pgn) &&
                    isSend(pgn, p.getHeader().getTimestamp().toEpochMilli(), p.getData())) {
                return p.getMessage();
            } else return null;
        } catch (Exception e) {
            logger.error(LogStringBuilder.start("N2KStream").wO("parse").wV("message", sMessage).toString(), e);
            return null;
        }
    }

    private static int hashCodeOf(byte[] data) {
        return HashCode.fromBytes(data).hashCode();
    }

    private boolean isSend(int pgn, long ts, byte[] data) {
        if (throttling) {
            Payload p = payloadMap.getOrDefault(pgn, null);
            if (p == null) {
                p = new Payload();
                p.timestamp = ts;
                p.hashcode = hashCodeOf(data);
                payloadMap.put(pgn, p);
                return true;
            } else {
                int hash = hashCodeOf(data);
                // check for minimum age (active throttling) then check for maximum age or some changes
                if ((ts - MIN_AGE) > p.timestamp &&
                        ((ts - MAX_AGE) > p.timestamp || hash != p.hashcode)) {
                    p.timestamp = ts;
                    p.hashcode = hash;
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }
}
