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

import com.aboni.nmea.n2k.N2KMessageHeader;
import com.aboni.nmea.n2k.PGNDataParseException;

import static com.aboni.nmea.n2k.messages.N2KMessagePGNs.AIS_POSITION_REPORT_CLASS_B_EXT_PGN;

@SuppressWarnings("ClassTooDeepInInheritanceTree")
public class N2KAISPositionReportBExt extends N2KAISPositionReportBImpl {

    public N2KAISPositionReportBExt(byte[] data) throws PGNDataParseException {
        super(getDefaultHeader(AIS_POSITION_REPORT_CLASS_B_EXT_PGN), data);
        fill();
    }

    public N2KAISPositionReportBExt(N2KMessageHeader header, byte[] data) throws PGNDataParseException {
        super(header, data);
        if (header == null) throw new PGNDataParseException("Null message header!");
        if (header.getPgn() != AIS_POSITION_REPORT_CLASS_B_EXT_PGN)
            throw new PGNDataParseException(String.format("Incompatible header: expected %d, received %d",
                    AIS_POSITION_REPORT_CLASS_B_EXT_PGN, header.getPgn()));
        fill();
    }

    @Override
    public String toString() {
        return String.format("PGN {%s} Src {%d} MMSI {%s} AIS Class {%s} Position {%s} COG {%.1f} SOG {%.1f} Timestamp {%d}",
                AIS_POSITION_REPORT_CLASS_B_EXT_PGN, getHeader().getSource(), getMMSI(), getAISClass(),
                getGPSInfo().getPosition(),
                getGPSInfo().getCOG(), getGPSInfo().getSOG(), getTimestamp()
        );
    }}