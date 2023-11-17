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

package com.aboni.nmea.n2k.evo;

import com.aboni.nmea.n2k.N2KMessageHeader;

import java.time.Instant;

class N2kHeader126208 implements N2KMessageHeader {

    private final int src;
    private final Instant t;

    N2kHeader126208(int src, Instant time) {
        this.src = src;
        this.t = time;
    }

    @Override
    public int getPgn() {
        return 126208;
    }

    @Override
    public int getSource() {
        return src;
    }

    @Override
    public int getDest() {
        return 204;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public Instant getTimestamp() {
        return t;
    }
}
