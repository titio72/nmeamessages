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

package com.aboni.nmea.n2k.messages.impl;

import com.aboni.nmea.message.MsgSystemTime;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class N2KSystemTimeTest {

    @Test
    public void testSystemTimeOK() {
        MsgSystemTime s = new N2KSystemTimeImpl(new byte[]{(byte) 0x01, (byte) 0xf0, (byte) 0x02, (byte) 0x48, (byte) 0x90, (byte) 0x30, (byte) 0x05, (byte) 0x12});
        assertEquals(1, s.getSID());
        assertEquals("GPS", s.getTimeSourceType());
        assertEquals(Instant.parse("2020-06-21T08:23:53Z"), s.getTime());
    }

}