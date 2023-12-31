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

import com.aboni.nmea.message.MsgTemperature;
import com.aboni.nmea.message.TemperatureSource;
import com.aboni.nmea.message.impl.MsgTemperatureImpl;
import com.aboni.nmea.n2k.N2KMessageHeader;
import com.aboni.nmea.n2k.PGNDataParseException;
import com.aboni.utils.Utils;

import static com.aboni.nmea.n2k.messages.N2KMessagePGNs.ENVIRONMENT_TEMPERATURE_PGN;

public class N2KTemperatureImpl extends N2KMessageImpl implements MsgTemperature {

    private final MsgTemperature temperatureMessage;


    public N2KTemperatureImpl(byte[] data) {
        super(getDefaultHeader(ENVIRONMENT_TEMPERATURE_PGN), data);
        temperatureMessage = fill(data);
    }

    public N2KTemperatureImpl(N2KMessageHeader header, byte[] data) throws PGNDataParseException {
        super(header, data);
        if (header == null) throw new PGNDataParseException("Null message header!");
        if (header.getPgn() != ENVIRONMENT_TEMPERATURE_PGN)
            throw new PGNDataParseException(String.format("Incompatible header: expected %d, received %d", ENVIRONMENT_TEMPERATURE_PGN, header.getPgn()));
        temperatureMessage = fill(data);
    }

    private MsgTemperature fill(byte[] data) {

        int sid = N2KBitUtils.getByte(data, 0, 0xFF);
        int instance = N2KBitUtils.getByte(data, 1, 0xFF);

        TemperatureSource source = TemperatureSource.valueOf(N2KBitUtils.getByte(data, 2, 0));

        Double dT = N2KBitUtils.parseDouble(data, 24, 16, 0.01, false);
        double temperature = (dT == null) ? Double.NaN : Utils.round(kelvinToCelsius(dT), 1);

        Double dST = N2KBitUtils.parseDouble(data, 40, 16, 0.01, false);
        double setTemperature = (dST == null) ? Double.NaN : Utils.round(kelvinToCelsius(dST), 1);

        return new MsgTemperatureImpl(sid, instance, source, temperature, setTemperature);
    }

    private static double kelvinToCelsius(double t) {
        return t > 200.0 ? (t - 273.15) : t;
    }

    @Override
    public int getSID() {
        return temperatureMessage.getSID();
    }

    @Override
    public int getInstance() {
        return temperatureMessage.getInstance();
    }

    @Override
    public TemperatureSource getTemperatureSource() {
        return temperatureMessage.getTemperatureSource();
    }

    @Override
    public double getTemperature() {
        return temperatureMessage.getTemperature();
    }

    @Override
    public double getSetTemperature() {
        return temperatureMessage.getSetTemperature();
    }

    @Override
    public String toString() {
        return String.format("PGN {%s} Source {%d} Instance {%d} TempSource {%s} Temperature {%.1f} SetTemperature {%.1f}",
                ENVIRONMENT_TEMPERATURE_PGN, getHeader().getSource(), getInstance(), getTemperatureSource(), getTemperature(), getSetTemperature());
    }
}
