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

package com.aboni.nmea.n2k.can;

import org.junit.Test;

import static org.junit.Assert.*;

public class CANDataFrameTest {

    @Test
    public void testFrame() {
        byte[] data = new byte[]{(byte) 0x00, (byte) 0xFC, (byte) 0xF3, (byte) 0x8E, (byte) 0x2F, (byte) 0x01, (byte) 0xFF, (byte) 0xFF};
        CANDataFrame frame = CANDataFrame.create(0x09F80216, data);
        assertEquals(8, frame.getDataSize());
        assertEquals(0x09F80216, frame.getId());
        for (int i = 0; i < frame.getDataSize(); i++) assertEquals(data[i], frame.getData()[i]);
    }

    @Test
    public void testFrameEquals() {
        byte[] data = new byte[]{(byte) 0x00, (byte) 0xFC, (byte) 0xF3, (byte) 0x8E, (byte) 0x2F, (byte) 0x01, (byte) 0xFF, (byte) 0xFF};
        CANDataFrame frame = CANDataFrame.create(0x09F80216, data);
        byte[] data1 = new byte[]{(byte) 0x00, (byte) 0xFC, (byte) 0xF3, (byte) 0x8E, (byte) 0x2F, (byte) 0x01, (byte) 0xFF, (byte) 0xFF};
        CANDataFrame frame1 = CANDataFrame.create(0x09F80216, data);
        assertEquals(frame1, frame);
    }

    @Test
    public void testFrameNotEquals() {
        byte[] data = new byte[]{(byte) 0x00, (byte) 0xFC, (byte) 0xF3, (byte) 0x8E, (byte) 0x2F, (byte) 0x01, (byte) 0xFF, (byte) 0xFF};
        CANDataFrame frame = CANDataFrame.create(0x09F80216, data);
        byte[] data1 = new byte[]{(byte) 0x00, (byte) 0xFD, (byte) 0xF3, (byte) 0x8E, (byte) 0x2F, (byte) 0x01, (byte) 0xFF, (byte) 0xFF};
        CANDataFrame frame1 = CANDataFrame.create(0x09F80216, data1);
        assertNotEquals(frame1, frame);
    }

    @Test
    public void testFrameNotEqualsId() {
        byte[] data = new byte[]{(byte) 0x00, (byte) 0xFC, (byte) 0xF3, (byte) 0x8E, (byte) 0x2F, (byte) 0x01, (byte) 0xFF, (byte) 0xFF};
        CANDataFrame frame = CANDataFrame.create(0x09F80217, data);
        byte[] data1 = new byte[]{(byte) 0x00, (byte) 0xFC, (byte) 0xF3, (byte) 0x8E, (byte) 0x2F, (byte) 0x01, (byte) 0xFF, (byte) 0xFF};
        CANDataFrame frame1 = CANDataFrame.create(0x09F80216, data1);
        assertNotEquals(frame1, frame);
    }
}