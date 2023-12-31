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

public class MsgSpeedAndHeadingFacade implements MsgSpeedAndHeading {

    private final MsgSpeed speed;
    private final MsgHeading heading;

    public MsgSpeedAndHeadingFacade(MsgSpeed speed, MsgHeading heading) {
        if (speed==null || heading==null) throw new IllegalArgumentException("SPeed or heading is null");
        this.heading = heading;
        this.speed = speed;
    }


    @Override
    public int getSID() {
        return speed.getSID();
    }

    @Override
    public double getSpeedWaterRef() {
        return speed.getSpeedWaterRef();
    }

    @Override
    public double getSpeedGroundRef() {
        return speed.getSpeedGroundRef();
    }

    @Override
    public String getSpeedSensorType() {
        return speed.getSpeedSensorType();
    }

    @Override
    public int getSpeedDirection() {
        return speed.getSpeedDirection();
    }

    @Override
    public double getHeading() {
        return heading.getHeading();
    }

    @Override
    public double getDeviation() {
        return heading.getDeviation();
    }

    @Override
    public double getVariation() {
        return heading.getVariation();
    }

    @Override
    public DirectionReference getReference() {
        return heading.getReference();
    }

    @Override
    public boolean isTrueHeading() {
        return heading.isTrueHeading();
    }

    @Override
    public String toString() {
        return heading + " " + speed;
    }

    @Override
    public String getMessageOrigin() {
        return (heading.getMessageOrigin().equals(speed.getMessageOrigin())) ? heading.getMessageOrigin() : "";
    }
}
