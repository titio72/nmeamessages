package com.aboni.nmea.n2k.messages.impl;

import com.aboni.nmea.message.MsgEngineParams;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class N2KEngineParamsTest {

    @Test
    public void testEngineHours() {
        MsgEngineParams m = new N2KEngineParamsImpl(new byte[] {
                (byte)0x1a, (byte)0x00, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                (byte)0xff, (byte)0xff, (byte)0x7f, (byte)0xff, (byte)0x7f, (byte)0x52, (byte)0x65,
                (byte)0x00, (byte)0x00, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x7f, (byte)0x7f, (byte)0xff});
        assertEquals(25938, m.getEngineHours(), 0.001);
    }
}
