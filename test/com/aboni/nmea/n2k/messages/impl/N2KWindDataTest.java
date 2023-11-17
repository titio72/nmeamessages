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

import com.aboni.nmea.message.MsgWindData;
import com.aboni.nmea.n2k.PGNDataParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class N2KWindDataTest {

    @Test
    public void testWindOk() throws PGNDataParseException {
        MsgWindData w = new N2KWindDataImpl(new byte[]{(byte) 0xb1, (byte) 0x5c, (byte) 0x00, (byte) 0xee, (byte) 0xf0, (byte) 0xfa, (byte) 0xff, (byte) 0xff});
        assertEquals(353.4, w.getAngle(), 0.1);
        assertEquals(1.79, w.getSpeed(), 0.01);
        assertEquals(177, w.getSID());
        assertTrue(w.isApparent());
    }
}