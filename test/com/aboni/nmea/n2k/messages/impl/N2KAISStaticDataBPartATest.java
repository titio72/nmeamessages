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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class N2KAISStaticDataBPartATest {

    byte[] data = new byte[]{(byte) 0x18, (byte) 0xc8, (byte) 0x53, (byte) 0xbc, (byte) 0x0e, (byte) 0x4e, (byte) 0x41, (byte) 0x53, (byte) 0x48,
            (byte) 0x4f, (byte) 0x52, (byte) 0x4e, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20,
            (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0xff, (byte) 0xff};

    @Test
    public void test() {
        N2KAISStaticDataBPartAImpl m = new N2KAISStaticDataBPartAImpl(data);
        assertEquals("NASHORN", m.getName());
        assertEquals("247223240", m.getMMSI());
    }
}