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

package com.aboni.nmea.nmea0183.impl;

import com.aboni.nmea.n2k.N2KMessage;
import com.aboni.nmea.n2k.messages.impl.N2KSystemTimeImpl;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.ZDASentence;
import net.sf.marineapi.nmea.util.Date;
import net.sf.marineapi.nmea.util.Time;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Message2NMEA0183ImplTest {

    @Test
    public void testSystemTime() {
        N2KMessage s = new N2KSystemTimeImpl(new byte[]{(byte) 0x01, (byte) 0xf0, (byte) 0x02, (byte) 0x48, (byte) 0x90, (byte) 0x30, (byte) 0x05, (byte) 0x12});
        Sentence[] sentences = new Message2NMEA0183Impl().convert(s);
        assertEquals(1, sentences.length);
        assertTrue(sentences[0] instanceof ZDASentence);
        assertEquals(new Time("082353"), ((ZDASentence) sentences[0]).getTime());
        assertEquals(new Date("21062020"), ((ZDASentence) sentences[0]).getDate());
    }
}