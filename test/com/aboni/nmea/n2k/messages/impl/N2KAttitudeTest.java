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

import com.aboni.nmea.message.MsgAttitude;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class N2KAttitudeTest {

    @Test
    public void testAttitudeOk() {
        MsgAttitude a = new N2KAttitudeImpl(new byte[]{(byte) 0xff, (byte) 0xea, (byte) 0x12, (byte) 0x1d, (byte) 0x01, (byte) 0x91, (byte) 0xfd, (byte) 0xff});
        assertEquals(27.7, a.getYaw(), 0.01);
        assertEquals(1.6, a.getPitch(), 0.01);
        assertEquals(-3.6, a.getRoll(), 0.01);
    }

}