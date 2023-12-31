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

package com.aboni.nmea.message.impl;

import com.aboni.nmea.message.HumiditySource;
import com.aboni.nmea.message.MsgHumidity;

public class MsgHumidityImpl implements MsgHumidity {

    private final int sid;
    private final int instance;
    private final double humidity;
    private final double setHumidity;
    private final HumiditySource src;

    public MsgHumidityImpl(HumiditySource src, double humidity) {
        this(-1, 0, src, humidity, Double.NaN);
    }

    public MsgHumidityImpl(int sid, int instance, HumiditySource src, double humidity, double setHumidity) {
        this.src = src;
        this.humidity = humidity;
        this.setHumidity = setHumidity;
        this.sid = sid;
        this.instance = instance;
    }

    @Override
    public int getSID() {
        return sid;
    }

    @Override
    public HumiditySource getHumiditySource() {
        return src;
    }

    @Override
    public double getHumidity() {
        return humidity;
    }

    @Override
    public double getSetHumidity() {
        return setHumidity;
    }

    @Override
    public int getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return String.format("Humidity: Source {%s} Humidity {%.1f}", getHumiditySource(), getHumidity());
    }
}
