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

import com.aboni.nmea.n2k.N2KMessageHeader;
import org.junit.Test;

import static org.junit.Assert.*;

public class N2KMessageHeaderImplTest {


    @Test
    public void testReadHeader() {
        N2KMessageHeader h = new N2KMessageHeaderImpl(0x09F80116);
        assertEquals(129025, h.getPgn());
        assertEquals(22, h.getSource());
        assertEquals(255, h.getDest());
        assertEquals(2, h.getPriority());

    }

}