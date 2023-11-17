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

import java.time.Instant;

public class N2KMessageHeaderImpl implements N2KMessageHeader {

    private int pgn;
    private int priority;
    private int src;
    private int dst;

    public N2KMessageHeaderImpl(long id) {
        getISO11783BitsFromCanId(id);
    }

    public int getPgn() {
        return pgn;
    }

    @Override
    public int getSource() {
        return src;
    }

    @Override
    public int getDest() {
        return dst;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public Instant getTimestamp() {
        return null;
    }

    private void getISO11783BitsFromCanId(long id) {
        int pf = (int) ((id >> 16) & 0xFF);
        int ps = (int) ((id >> 8) & 0xFF);
        int dp = (int) ((id >> 24) & 1);

        src = (int) (id & 0xFF);
        priority = (int) ((id >> 26) & 0x7);

        if (pf < 240) {
            /* PDU1 format, the PS contains the destination address */
            dst = ps;
            pgn = (dp << 16) + (pf << 8);
        } else {
            /* PDU2 format, the destination is implied global and the PGN is extended */
            dst = 0xff;
            pgn = (dp << 16) + (pf << 8) + ps;
        }
    }
}
