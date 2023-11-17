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

import com.aboni.nmea.message.MsgSOGAdCOG;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class N2KSOGAdCOGRapidTest {

    @Test
    public void testSOGCOGOk() {
        MsgSOGAdCOG s = new N2KSOGAdCOGRapidImpl(new byte[]{(byte) 0xff, (byte) 0xfc, (byte) 0x68, (byte) 0x1b, (byte) 0x89, (byte) 0x00, (byte) 0xff, (byte) 0xff});
        assertEquals(40.2, s.getCOG(), 0.1);
        assertEquals(2.67, s.getSOG(), 0.01);
        assertEquals("True", s.getCOGReference());
        assertTrue(s.isTrueCOG());
    }
}