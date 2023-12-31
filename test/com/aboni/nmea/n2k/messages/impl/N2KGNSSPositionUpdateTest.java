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

import com.aboni.nmea.message.MsgGNSSPosition;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class N2KGNSSPositionUpdateTest {


    @Test
    public void test1() {
        byte[] bbb = new byte[] {
                (byte)0x45,(byte)0x70,(byte)0x48,(byte)0x50,(byte)0x77,(byte)0xd5,(byte)0x2b,(byte)0x00,(byte)0x31,(byte)0xe6,(byte)0xb2,
                (byte)0xbc,(byte)0xbc,(byte)0x0f,(byte)0x06,(byte)0x80,(byte)0xd5,(byte)0xc4,(byte)0x57,(byte)0x28,(byte)0x01,(byte)0x6d,
                (byte)0x01,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0x7f,(byte)0x10,(byte)0xfd,
                (byte)0x11,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0xff,(byte)0xff,
                (byte)0xff,(byte)0xff,(byte)0xff
        };
        MsgGNSSPosition s = new N2KGNSSPositionUpdateImpl(bbb);
        System.out.println(s);
    }


    @Test
    public void testGNSSUpdateOk() {
        MsgGNSSPosition s = new N2KGNSSPositionUpdateImpl(new byte[]{
                (byte) 0x2a, (byte) 0x02, (byte) 0x48, (byte) 0xc0, (byte) 0x45, (byte) 0xbb,
                (byte) 0x11, (byte) 0x00, (byte) 0xb0, (byte) 0x6d, (byte) 0xa6, (byte) 0x1c, (byte) 0xb1,
                (byte) 0xf9, (byte) 0x05, (byte) 0x00, (byte) 0x96, (byte) 0x59, (byte) 0xf2, (byte) 0xc5,
                (byte) 0xa8, (byte) 0x5d, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x7f, (byte) 0x13, (byte) 0xfc, (byte) 0x08,
                (byte) 0xc9, (byte) 0x01, (byte) 0xff, (byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x7f, (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        /*
        "SID":42,
        "Date":"2020.06.21",
        "Time": "08:15:48.05760",
        "Latitude":43.0569976,
        "Longitude": 9.8420335,
        "GNSS type":"GPS+SBAS/WAAS",
        "Method":"GNSS fix",
        "Integrity":"No integrity checking",
        "Number of SVs":8,
        "HDOP":4.57,
        "Reference Stations":0}
         */

        assertEquals(42, s.getSID());
        assertEquals("GPS+SBAS/WAAS", s.getGnssType());
        assertEquals("GNSS fix", s.getMethod());
        assertEquals(Instant.parse("2020-06-21T08:15:48.576Z"), s.getTimestamp());
        assertEquals(43.0569976, s.getPosition().getLatitude(), 0.00000001);
        assertEquals(9.8420335, s.getPosition().getLongitude(), 0.00000001);
        assertEquals(8, s.getNSatellites());
        assertEquals(4.57, s.getHDOP(), 0.001);
        assertNaN(s.getAltitude());
        assertNaN(s.getPDOP());
        assertNaN(s.getGeoidalSeparation());
        assertNaN(s.getAgeOfDgnssCorrections());
        assertEquals(0, s.getReferenceStations());
    }

    void assertNaN(double d) {
        assertTrue(Double.isNaN(d));
    }
}


