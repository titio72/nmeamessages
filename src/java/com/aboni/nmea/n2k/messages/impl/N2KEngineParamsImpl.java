package com.aboni.nmea.n2k.messages.impl;

import com.aboni.nmea.message.MsgEngineParams;
import com.aboni.nmea.message.impl.MsgEngineParamsImpl;
import com.aboni.nmea.n2k.N2KMessageHeader;
import com.aboni.nmea.n2k.PGNDataParseException;

import static com.aboni.nmea.n2k.messages.N2KMessagePGNs.ATTITUDE_PGN;
import static com.aboni.nmea.n2k.messages.N2KMessagePGNs.ENGINE_PARAMS_PGN;

public class N2KEngineParamsImpl extends N2KMessageImpl implements MsgEngineParams {

    private final MsgEngineParams msgEngineParams;

    public N2KEngineParamsImpl(byte[] data) {
        super(getDefaultHeader(ENGINE_PARAMS_PGN), data);
        msgEngineParams = fill(data);
    }


    public N2KEngineParamsImpl(N2KMessageHeader header, byte[] data) throws PGNDataParseException {
        super(header, data);
        if (header == null) throw new PGNDataParseException("Null message header!");
        if (header.getPgn() != ENGINE_PARAMS_PGN)
            throw new PGNDataParseException(String.format("Incompatible header: expected %d, received %d", ENGINE_PARAMS_PGN, header.getPgn()));
        msgEngineParams = fill(data);
    }

    private static MsgEngineParams fill(byte[] data) {
        int instance = N2KBitUtils.getByte(data, 0, 0xFF);
        double h = N2KBitUtils.parseDoubleSafe(data, 88, 32, 1.0, false);
        return new MsgEngineParamsImpl(instance, h);
    }

    @Override
    public int getInstance() {
        return msgEngineParams.getInstance();
    }

    @Override
    public double getEngineHours() {
        return msgEngineParams.getEngineHours();
    }

    @Override
    public String toString() {
        return String.format("PGN {%s} Source {%d} Instance {%d} EngineH {%.0f}",
                ATTITUDE_PGN, getHeader().getSource(), getInstance(), getEngineHours());
    }
}
