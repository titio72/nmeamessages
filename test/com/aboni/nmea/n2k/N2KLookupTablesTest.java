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

package com.aboni.nmea.n2k;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class N2KLookupTablesTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGNS() {
        Map<Integer, String> m = N2KLookupTables.getTable(N2KLookupTables.LOOKUP_MAPS.GNS);
        assertNotNull(m);
        assertEquals(9, m.size());
        assertEquals("GPS", m.get(0));
        assertEquals("GLONASS", m.get(1));
        assertEquals("GPS+GLONASS", m.get(2));
        assertEquals("GPS+SBAS/WAAS", m.get(3));
        assertEquals("GPS+SBAS/WAAS+GLONASS", m.get(4));
        assertEquals("Chayka", m.get(5));
        assertEquals("integrated", m.get(6));
        assertEquals("surveyed", m.get(7));
        assertEquals("Galileo", m.get(8));
    }
}