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

import com.aboni.nmea.message.DirectionReference;
import com.aboni.nmea.message.MsgHeading;
import com.aboni.nmea.n2k.PGNDataParseException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class N2KHeadingTest {

    @Test
    public void testHeadingOk() throws PGNDataParseException {
        MsgHeading h = new N2KHeadingImpl(new byte[]{(byte) 0xff, (byte) 0xf7, (byte) 0x12, (byte) 0xff, (byte) 0x7f, (byte) 0xff, (byte) 0x7f, (byte) 0xfd});
        assertEquals(0xFF, h.getSID());
        assertEquals(27.8, h.getHeading(), 0.1);
        assertTrue(Double.isNaN(h.getVariation()));
        assertTrue(Double.isNaN(h.getDeviation()));
        Assert.assertEquals(DirectionReference.MAGNETIC, h.getReference());
        assertFalse(h.isTrueHeading());
    }
}