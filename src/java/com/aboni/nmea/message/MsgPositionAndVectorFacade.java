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

package com.aboni.nmea.message;

import net.sf.marineapi.nmea.util.Position;

import java.time.Instant;

public class MsgPositionAndVectorFacade implements MsgPositionAndVector {

    private final MsgSOGAdCOG vector;
    private final MsgGNSSPosition position;

    public MsgPositionAndVectorFacade(MsgGNSSPosition position, MsgSOGAdCOG vector) {
        if (position==null || vector==null) throw new IllegalArgumentException("Position or vector is null");
        this.position = position;
        this.vector = vector;
    }

    @Override
    public Instant getTimestamp() {
        return position.getTimestamp();
    }

    @Override
    public double getVariation() {
        return Double.NaN;
    }

    @Override
    public Position getPosition() {
        return position.getPosition();
    }

    @Override
    public int getSID() {
        return -1;
    }

    @Override
    public double getSOG() {
        return vector.getSOG();
    }

    @Override
    public double getCOG() {
        return vector.getCOG();
    }

    @Override
    public String getCOGReference() {
        return vector.getCOGReference();
    }

    @Override
    public boolean isTrueCOG() {
        return vector.isTrueCOG();
    }

    @Override
    public String toString() {
        return position + " " + vector;
    }

    @Override
    public String getMessageType() {
        return "";
    }

    @Override
    public String getMessageOrigin() {
        return (position.getMessageOrigin().equals(vector.getMessageOrigin())) ? position.getMessageOrigin() : "";
    }
}
