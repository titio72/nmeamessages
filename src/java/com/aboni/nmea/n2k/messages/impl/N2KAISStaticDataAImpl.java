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

import com.aboni.nmea.AISStaticData;
import com.aboni.nmea.n2k.N2KLookupTables;
import com.aboni.nmea.n2k.N2KMessageHeader;
import com.aboni.nmea.n2k.PGNDataParseException;

import static com.aboni.nmea.n2k.N2KLookupTables.LOOKUP_MAPS.AIS_TRANSCEIVER;
import static com.aboni.nmea.n2k.N2KLookupTables.LOOKUP_MAPS.SHIP_TYPE;
import static com.aboni.nmea.n2k.messages.N2KMessagePGNs.AIS_STATIC_DATA_CLASS_A_PGN;

public class N2KAISStaticDataAImpl extends N2KMessageImpl implements AISStaticData {

    private int messageId;
    private String sMMSI;
    private int repeatIndicator;
    private int imo;
    private String callSign;
    private String name;
    private String typeOfShip;
    private double length;
    private double beam;
    private String aisTransceiverInformation;

    public N2KAISStaticDataAImpl(byte[] data) {
        super(getDefaultHeader(AIS_STATIC_DATA_CLASS_A_PGN), data);
        fill();
    }

    public N2KAISStaticDataAImpl(N2KMessageHeader header, byte[] data) throws PGNDataParseException {
        super(header, data);
        if (header == null) throw new PGNDataParseException("Null message header!");
        if (header.getPgn() != AIS_STATIC_DATA_CLASS_A_PGN)
            throw new PGNDataParseException(String.format("Incompatible header: expected %d, received %d", AIS_STATIC_DATA_CLASS_A_PGN, header.getPgn()));
        fill();
    }

    private void fill() {
        messageId = (int) N2KBitUtils.parseIntegerSafe(data, 0, 0, 6, 0xFF);
        repeatIndicator = (int)N2KBitUtils.parseIntegerSafe(data, 6, 6, 2, 0);
        sMMSI = String.format("%d", N2KBitUtils.parseIntegerSafe(data, 8, 0, 32, 0));
        imo = (int) N2KBitUtils.parseIntegerSafe(data, 40, 0, 32, 0xFFFFFF);
        callSign = N2KBitUtils.getText(data, 9, 7);
        name = N2KBitUtils.getText(data, 16, 20);
        typeOfShip = N2KBitUtils.parseEnum(data, 288, 0, 8, N2KLookupTables.getTable(SHIP_TYPE));
        length = N2KBitUtils.parseDoubleSafe(data, 296, 16, 0.1, false);
        beam = N2KBitUtils.parseDoubleSafe(data, 312, 16, 0.1, false);
        aisTransceiverInformation = N2KBitUtils.parseEnum(data, 592, 0, 5, N2KLookupTables.getTable(AIS_TRANSCEIVER));

    }

    @Override
    public int getRepeatIndicator() {
        return repeatIndicator;
    }

    @Override
    public int getMessageId() {
        return messageId;
    }

    public int getImo() {
        return imo;
    }

    public String getCallSign() {
        return callSign;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMMSI() {
        return sMMSI;
    }

    @Override
    public String getTypeOfShip() {
        return typeOfShip;
    }

    @Override
    public String getAISClass() {
        return "A";
    }

    @Override
    public double getLength() {
        return length;
    }

    @Override
    public double getBeam() {
        return beam;
    }

    @Override
    public String getAisTransceiverInfo() {
        return aisTransceiverInformation;
    }

    @Override
    public String toString() {
        return String.format("PGN {%s} Source {%d} MsgId {%d} Repeat {%s} Name {%s} MMSI {%s} CallSign {%s} Type {%s} AISClass {%s} Transceiver {%s}",
                AIS_STATIC_DATA_CLASS_A_PGN, getHeader().getSource(),
                getMessageId(), getRepeatIndicator(), getName(), getMMSI(), getCallSign(),
                getTypeOfShip(), getAISClass(), getAisTransceiverInfo());
    }

    @Override
    public String getMessageContentType() {
        return "AISStaticDataA";
    }
}