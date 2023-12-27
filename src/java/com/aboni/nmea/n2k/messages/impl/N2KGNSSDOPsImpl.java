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

import com.aboni.nmea.message.GNSSFix;
import com.aboni.nmea.message.MsgGNSSDOPs;
import com.aboni.nmea.n2k.N2KMessageHeader;
import com.aboni.nmea.n2k.PGNDataParseException;
import org.json.JSONObject;

import static com.aboni.nmea.n2k.messages.N2KMessagePGNs.GNSS_DOP_PGN;

public class N2KGNSSDOPsImpl extends N2KMessageImpl implements MsgGNSSDOPs {

    private static GNSSFix of(int i) {
        switch (i) {
            case 0:
                return GNSSFix.FIX_1D;
            case 1:
                return GNSSFix.FIX_2D;
            case 2:
                return GNSSFix.FIX_3D;
            case 3:
                return GNSSFix.FIX_AUTO;
            case 7:
                return GNSSFix.FIX_UNAVAILABLE;
            default:
                return GNSSFix.FIX_ERROR;
        }
    }

    private int sid;
    private double hDOP;
    private double vDOP;
    private double tDOP;
    private GNSSFix fix = GNSSFix.FIX_ERROR;

    public N2KGNSSDOPsImpl(byte[] data) {
        super(getDefaultHeader(GNSS_DOP_PGN), data);
        fill();
    }

    public N2KGNSSDOPsImpl(N2KMessageHeader header, byte[] data) throws PGNDataParseException {
        super(header, data);
        if (header == null) throw new PGNDataParseException("Null message header!");
        if (header.getPgn() != GNSS_DOP_PGN)
            throw new PGNDataParseException(String.format("Incompatible DOPs: expected %d, received %d", GNSS_DOP_PGN, header.getPgn()));
        fill();
    }

    private void fill() {
        sid = N2KBitUtils.getByte(data, 0, 0xFF);
        fix = of((int) N2KBitUtils.parseIntegerSafe(data, 11, 3, 3, 6));
        hDOP = N2KBitUtils.parseDoubleSafe(data, 16, 16, 0.01, true);
        vDOP = N2KBitUtils.parseDoubleSafe(data, 32, 16, 0.01, true);
        tDOP = N2KBitUtils.parseDoubleSafe(data, 48, 16, 0.01, true);
    }

    @Override
    public int getSID() {
        return sid;
    }

    @Override
    public double getHDOP() {
        return hDOP;
    }

    @Override
    public double getVDOP() {
        return vDOP;
    }

    @Override
    public double getTDOP() {
        return tDOP;
    }

    @Override
    public GNSSFix getFix() {
        return fix;
    }

    @Override
    public String getFixDescription() {
        return fix.toString();
    }

    @Override
    public String toString() {
        return String.format("PGN {%s} Source {%d} Fix {%s} HDOP {%.1f} VDOP {%.1f} TDOP {%.1f}",
                GNSS_DOP_PGN, getHeader().getSource(), getFixDescription(),
                getHDOP(), getVDOP(), getTDOP());
    }

    @Override
    public JSONObject toJSON() {
        JSONObject res = new JSONObject();
        res.put("topic", "gnssdop");
        res.put("hdop", getHDOP());
        res.put("vdop", getVDOP());
        res.put("tdop", getTDOP());
        res.put("fix", getFixDescription());
        return res;
    }
}