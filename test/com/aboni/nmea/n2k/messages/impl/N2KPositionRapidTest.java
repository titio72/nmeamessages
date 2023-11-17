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

import com.aboni.nmea.message.MsgPosition;
import com.aboni.nmea.n2k.PGNDataParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class N2KPositionRapidTest {

    @Test
    public void testPosOk() throws PGNDataParseException {
        MsgPosition s = new N2KPositionRapidImpl(null, new byte[]{(byte) 0x36, (byte) 0x1a, (byte) 0xab, (byte) 0x19, (byte) 0x04, (byte) 0x42, (byte) 0xde, (byte) 0x05});

        assertEquals(43.0643766, s.getPosition().getLatitude(), 0.0000001);
        assertEquals(9.8451972, s.getPosition().getLongitude(), 0.0000001);
        assertNotNull(s.getPosition());
        assertEquals(43.0643766, s.getPosition().getLatitude(), 0.0000001);
        assertEquals(9.8451972, s.getPosition().getLongitude(), 0.0000001);
    }

}