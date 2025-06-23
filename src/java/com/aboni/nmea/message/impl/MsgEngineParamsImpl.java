package com.aboni.nmea.message.impl;

import com.aboni.nmea.message.MsgEngineParams;

public class MsgEngineParamsImpl implements MsgEngineParams {

    private final int instance;
    private final double hours;

    public MsgEngineParamsImpl(int instance, double hours) {
        this.instance = instance;
        this.hours = hours;
    }

    @Override
    public int getInstance() {
        return instance;
    }

    @Override
    public double getEngineHours() {
        return hours;
    }
}
