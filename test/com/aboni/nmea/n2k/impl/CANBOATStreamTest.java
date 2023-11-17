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

import com.aboni.nmea.n2k.N2KMessage;
import com.aboni.nmea.n2k.messages.N2KMessageFactory;
import com.aboni.nmea.n2k.messages.impl.N2KMessageFactoryImpl;
import com.aboni.nmea.n2k.N2KMessageParser;
import com.aboni.nmea.n2k.N2KMessageParserFactory;
import com.aboni.nmea.n2k.PGNSourceFilter;
import com.aboni.log.ConsoleLog;
import org.junit.Test;

import static org.junit.Assert.*;

public class CANBOATStreamTest {

    private static final N2KMessageFactory messageFactory = new N2KMessageFactoryImpl();

    private static final PGNSourceFilter filter = new PGNSourceFilter() {
        @Override
        public void init(String filtersNotUsed) {

        }

        @Override
        public void setPGNTimestamp(int source, int pgn, long time) {

        }

        @Override
        public boolean accept(int source, int pgn, long now) {
            return true;
        }

        @Override
        public boolean accept(int source, int pgn) {
            return true;
        }
    };

    private static class ParserFactory implements N2KMessageParserFactory {
        @Override
        public N2KMessageParser getNewParser() {
            return new N2KMessageParserImpl(messageFactory);
        }
    }

    @Test
    public void sendFirstMessage() {
        N2KMessage o = new N2KStreamImpl(ConsoleLog.getLogger(), filter, new ParserFactory()).getMessage(ss[0]);
        assertNotNull(o);
        assertEquals(127250, o.getHeader().getPgn());
    }

    @Test
    public void skipNewMessageTooSoon() {
        N2KStreamImpl stream = new N2KStreamImpl(ConsoleLog.getLogger(), true, filter, new ParserFactory());
        assertNotNull(stream.getMessage(ss[0]));
        assertNull(stream.getMessage(ss[1]));
    }

    @Test
    public void skipNewMessageUnchanged() {
        // skip the second because the long timeout (1000ms) is not expired and the values are the same
        N2KStreamImpl stream = new N2KStreamImpl(ConsoleLog.getLogger(), true, filter, new ParserFactory());
        assertNotNull(stream.getMessage(ss[0]));
        assertNull(stream.getMessage(ss[2]));
    }

    @Test
    public void sendSecondMessageBecauseChanged() {
        // send second because the short timeout is expired (350ms) and the value is different
        N2KStreamImpl stream = new N2KStreamImpl(ConsoleLog.getLogger(), filter, new ParserFactory());
        assertNotNull(stream.getMessage(ss[0]));
        assertNotNull(stream.getMessage(ss[3]));
    }

    @Test
    public void sendSecondMessageTimeout() {
        // send second because the long timeout is expired (so no matter the values are changed or not
        N2KStreamImpl stream = new N2KStreamImpl(ConsoleLog.getLogger(), filter, new ParserFactory());
        assertNotNull(stream.getMessage(ss[0]));
        assertNotNull(stream.getMessage(ss[4]));
    }

    String[] ss = new String[]{
            /*0*/"1970-01-01-18:09:59.257,2,127250,204,255,8,ff,87,be,ff,7f,ff,7f,fd",
            /*1*/"1970-01-01-18:09:59.357,2,127250,204,255,8,ff,80,be,ff,7f,ff,7f,fd",
            /*2*/"1970-01-01-18:10:00.000,2,127250,204,255,8,ff,87,be,ff,7f,ff,7f,fd",
            /*3*/"1970-01-01-18:10:00.157,2,127250,204,255,8,ff,6f,be,ff,7f,ff,7f,fd",
            /*4*/"1970-01-01-18:10:00.357,2,127250,204,255,8,ff,69,be,ff,7f,ff,7f,fd"
    };
}