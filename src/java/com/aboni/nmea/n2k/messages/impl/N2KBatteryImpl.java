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

import com.aboni.nmea.message.MsgBattery;
import com.aboni.nmea.message.impl.MsgBatteryImpl;
import com.aboni.nmea.n2k.N2KMessageHeader;
import com.aboni.nmea.n2k.PGNDataParseException;

import static com.aboni.nmea.n2k.messages.N2KMessagePGNs.BATTERY_PGN;

public class N2KBatteryImpl extends N2KMessageImpl implements MsgBattery {

    private final MsgBattery msgHeading;

    public N2KBatteryImpl(byte[] data) {
        super(getDefaultHeader(BATTERY_PGN), data);
        msgHeading = fill(data);
    }

    public N2KBatteryImpl(N2KMessageHeader header, byte[] data) throws PGNDataParseException {
        super(header, data);
        if (header == null) throw new PGNDataParseException("Null message header!");
        if (header.getPgn() != BATTERY_PGN)
            throw new PGNDataParseException(String.format("Incompatible header: expected %d, received %d", BATTERY_PGN, header.getPgn()));
        msgHeading = fill(data);
    }

    private static MsgBattery fill(byte[] data) {
        int instance = N2KBitUtils.getByte(data, 0, 0xFF);
        double voltage = N2KBitUtils.parseDoubleSafe(data, 8, 16, 0.01, true);
        double current = N2KBitUtils.parseDoubleSafe(data, 24, 16, 0.1, true);
        double temperature = N2KBitUtils.parseDoubleSafe(data, 40, 16, 0.01, false);
        int sid = N2KBitUtils.getByte(data, 7, 0xFF);
        return new MsgBatteryImpl(sid, instance, voltage, current, temperature);
    }

    @Override
    public int getSID() {
        return msgHeading.getSID();
    }

    @Override
    public int getInstance() {
        return msgHeading.getInstance();
    }

    @Override
    public double getVoltage() {
        return msgHeading.getVoltage();
    }

    @Override
    public double getCurrent() {
        return msgHeading.getCurrent();
    }

    @Override
    public double getTemperature() {
        return msgHeading.getTemperature();
    }
}
