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

import com.aboni.nmea.message.MsgWaterDepth;
import com.aboni.nmea.n2k.PGNDataParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class N2KWaterDepthTest {

    @Test
    public void testWaterDepthOk() throws PGNDataParseException {
        MsgWaterDepth d = new N2KWaterDepthImpl(new byte[]{(byte) 0x00, (byte) 0x25, (byte) 0x1c, (byte) 0x00, (byte) 0x00, (byte) 0xc8, (byte) 0x00, (byte) 0xff});
        assertEquals(72.05, d.getDepth(), 0.01);
        assertEquals(0.200, d.getOffset(), 0.001);
        assertTrue(Double.isNaN(d.getRange()));
        assertEquals(0, d.getSID());
    }
}