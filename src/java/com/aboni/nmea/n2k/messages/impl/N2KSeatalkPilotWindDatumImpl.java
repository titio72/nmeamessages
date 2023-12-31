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

import com.aboni.nmea.message.MsgSeatalkPilotWindDatum;
import com.aboni.nmea.n2k.N2KMessageHeader;
import com.aboni.nmea.n2k.PGNDataParseException;
import com.aboni.utils.Utils;

import static com.aboni.nmea.n2k.messages.N2KMessagePGNs.SEATALK_PILOT_WIND_DATUM_PGN;

public class N2KSeatalkPilotWindDatumImpl extends N2KMessageImpl implements MsgSeatalkPilotWindDatum {

    private double windDatum;
    private double rollingAverageWind;

    public N2KSeatalkPilotWindDatumImpl(byte[] data) {
        super(getDefaultHeader(SEATALK_PILOT_WIND_DATUM_PGN), data);
        fill();
    }

    public N2KSeatalkPilotWindDatumImpl(N2KMessageHeader header, byte[] data) throws PGNDataParseException {
        super(header, data);
        if (header == null) throw new PGNDataParseException("Null message header!");
        if (header.getPgn() != SEATALK_PILOT_WIND_DATUM_PGN)
            throw new PGNDataParseException(String.format("Incompatible header: expected %d, received %d", SEATALK_PILOT_WIND_DATUM_PGN, header.getPgn()));
        fill();
    }

    private void fill() {
        Double d = N2KBitUtils.parseDouble(data, 16, 16, 0.0001, false);
        windDatum = (d == null) ? Double.NaN : Utils.round(Math.toDegrees(d), 1);

        d = N2KBitUtils.parseDouble(data, 32, 16, 0.0001, false);
        rollingAverageWind = (d == null) ? Double.NaN : Utils.round(Math.toDegrees(d), 1);
    }

    @Override
    public double getRollingAverageWind() {
        return rollingAverageWind;
    }

    @Override
    public double getWindDatum() {
        return windDatum;
    }
}
