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

import com.aboni.nmea.message.MsgSpeed;
import com.aboni.nmea.n2k.PGNDataParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class N2KSpeedTest {

    @Test
    public void testSpeedOk() throws PGNDataParseException {
        MsgSpeed s = new N2KSpeedImpl(new byte[]{(byte) 0x00, (byte) 0x7c, (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0xff, (byte) 0xff});
        assertEquals(0, s.getSID());
        assertEquals(2.41, s.getSpeedWaterRef(), 0.01);
        assertTrue(Double.isNaN(s.getSpeedGroundRef()));
        assertEquals("Paddle wheel", s.getSpeedSensorType());
    }

}