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

import com.aboni.nmea.message.MsgSeatalkPilotLockedHeading;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class N2KSeatalkPilotLockedHeadingTest {

    @Test
    public void test() {
        MsgSeatalkPilotLockedHeading h = new N2KSeatalkPilotLockedHeadingImpl(new byte[]{(byte) 0x3b, (byte) 0x9f, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x7f, (byte) 0x0c, (byte) 0xff});
        assertEquals(18.3, h.getLockedHeadingMagnetic(), 0.01);
        assertTrue(Double.isNaN(h.getLockedHeadingTrue()));

    }

}