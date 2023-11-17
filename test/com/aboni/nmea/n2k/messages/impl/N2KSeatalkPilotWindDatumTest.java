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

import com.aboni.nmea.message.MsgSeatalkPilotWindDatum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class N2KSeatalkPilotWindDatumTest {

    @Test
    public void test() {
        MsgSeatalkPilotWindDatum w = new N2KSeatalkPilotWindDatumImpl(new byte[]{(byte) 0x3b, (byte) 0x9f, (byte) 0xc2, (byte) 0x03, (byte) 0xcc, (byte) 0x08, (byte) 0xfa, (byte) 0xff});
        assertEquals(5.5, w.getWindDatum(), 0.01);
        assertEquals(12.9, w.getRollingAverageWind(), 0.01);

    }

}