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

public class N2KBitUtilsTest {

    private byte[] b = new byte[]{
            (byte) 0xb1, (byte) 0x5c, (byte) 0x00, (byte) 0xee,
            (byte) 0xf0, (byte) 0xfa, (byte) 0xff, (byte) 0xff};

    @Test
    public void extractShort() {
        long v = N2KBitUtils.extractBits(b, 0, 8, 16, false).v;
        assertEquals(92, v);
    }

    @Test
    public void extractLessThanOneByte() {
        long v1 = N2KBitUtils.extractBits(b, 0, 40, 3, false).v;
        assertEquals(2, v1);
    }

    @Test
    public void test1() {
        assertEquals(0xb1, N2KBitUtils.getByte(b, 0));
        assertEquals(N2KBitUtils.extractBits(b, 0, 0, 16, false).v, N2KBitUtils.get2ByteInt(b, 0));
        assertEquals(N2KBitUtils.extractBits(b, 0, 0, 24, false).v, N2KBitUtils.get3ByteInt(b, 0));
        assertEquals(N2KBitUtils.extractBits(b, 0, 0, 32, false).v, N2KBitUtils.get4ByteInt(b, 0));
    }

    @Test
    public void test2BytesUnsigned() {
        assertEquals(0x005c, N2KBitUtils.get2ByteInt(b, 1));
        assertEquals(0xf0ee, N2KBitUtils.get2ByteInt(b, 3));
        assertEquals(N2KBitUtils.extractBits(b, 0, 24, 16, false).v, N2KBitUtils.get2ByteInt(b, 3));
    }

    @Test
    public void test3BytesUnsigned() {
        assertEquals(0xee005c, N2KBitUtils.get3ByteInt(b, 1));
    }

    @Test
    public void test4BytesUnsigned() {
        assertEquals(0xf0ee005cL, N2KBitUtils.get4ByteInt(b, 1));
    }

    @Test
    public void extractDouble() {
        byte[] data = new byte[]{ (byte)0x00,(byte)0x9c,(byte)0x01,(byte)0x72,(byte)0xaa,(byte)0x02,(byte)0xff,(byte)0xff};
        assertEquals(4.3634, N2KBitUtils.parseDouble(data, 24, 16, 0.0001, false), 0.0001);
        // it's 250 degrees in radians
    }
}