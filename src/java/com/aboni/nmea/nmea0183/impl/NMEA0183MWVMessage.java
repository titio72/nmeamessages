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

package com.aboni.nmea.nmea0183.impl;

import com.aboni.nmea.message.MsgWindData;
import com.aboni.nmea.nmea0183.NMEA0183Message;
import net.sf.marineapi.nmea.parser.DataNotAvailableException;
import net.sf.marineapi.nmea.sentence.MWVSentence;
import net.sf.marineapi.nmea.util.Units;

public class NMEA0183MWVMessage extends NMEA0183Message implements MsgWindData {

    public NMEA0183MWVMessage(MWVSentence sentence) {
        super(sentence);
    }

    @Override
    public int getSID() {
        return -1;
    }

    @Override
    public double getSpeed() {
        try {
            switch (getSpeedUnits()) {
                case KMH:
                    return ((MWVSentence) getSentence()).getSpeed() / 1.852;
                case METER:
                    return ((MWVSentence) getSentence()).getSpeed() * 1852.0 / 3600.0;
                default:
                    return ((MWVSentence) getSentence()).getSpeed();
            }
        } catch (DataNotAvailableException exception) {
            return Double.NaN;
        }
    }

    private Units getSpeedUnits() {
        try {
            return ((MWVSentence)getSentence()).getSpeedUnit();
        } catch (DataNotAvailableException e) {
            return Units.KNOT;
        }
    }

    @Override
    public double getAngle() {
        try {
            return ((MWVSentence)getSentence()).getAngle();
        } catch (DataNotAvailableException exception) {
            return Double.NaN;
        }
    }

    @Override
    public boolean isApparent() {
        try {
            return !((MWVSentence)getSentence()).isTrue();
        } catch (DataNotAvailableException exception) {
            return true;
        }
    }
}
