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

import com.aboni.nmea.message.MsgEngine;
import com.aboni.nmea.message.impl.MsgEngineImpl;
import com.aboni.nmea.n2k.N2KMessageHeader;
import com.aboni.nmea.n2k.PGNDataParseException;

import static com.aboni.nmea.n2k.messages.N2KMessagePGNs.ATTITUDE_PGN;
import static com.aboni.nmea.n2k.messages.N2KMessagePGNs.ENGINE_PGN;

public class N2KEngineImpl extends N2KMessageImpl implements MsgEngine {

    private final MsgEngine msgEngine;

    public N2KEngineImpl(byte[] data) {
        super(getDefaultHeader(ENGINE_PGN), data);
        msgEngine = fill(data);
    }

    public N2KEngineImpl(N2KMessageHeader header, byte[] data) throws PGNDataParseException {
        super(header, data);
        if (header == null) throw new PGNDataParseException("Null message header!");
        if (header.getPgn() != ENGINE_PGN)
            throw new PGNDataParseException(String.format("Incompatible header: expected %d, received %d", ENGINE_PGN, header.getPgn()));
        msgEngine = fill(data);
    }

    private static MsgEngine fill(byte[] data) {
        int instance = N2KBitUtils.getByte(data, 0, 0xFF);
        double rpm = N2KBitUtils.parseDoubleSafe(data, 8, 16, 0.25, false);
        return new MsgEngineImpl(instance, rpm);
    }

    @Override
    public int getInstance() {
        return msgEngine.getInstance();
    }

    @Override
    public double getRPM() {
        return msgEngine.getRPM();
    }

    @Override
    public String toString() {
        return String.format("PGN {%s} Source {%d} Instance {%d} EngineRPM {%.0f}",
                ATTITUDE_PGN, getHeader().getSource(), getInstance(), getRPM());
    }
}
