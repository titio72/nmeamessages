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

import com.aboni.nmea.message.GNSSInfo;
import net.sf.marineapi.nmea.util.Position;

class GNSSInfoImpl implements GNSSInfo {

    private Position position;
    private double cog = Double.NaN;
    private double sog = Double.NaN;

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public double getCOG() {
        return cog;
    }

    @Override
    public double getSOG() {
        return sog;
    }

    void setCOG(double cog) {
        this.cog = cog;
    }

    void setSOG(double sog) {
        this.sog = sog;
    }

    void setPosition(Position p) {
        position = p;
    }
}
