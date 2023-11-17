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

package com.aboni.nmea.message;

public enum SeatalkAlarmStatus {

    OFF(0, "Alarm condition not met"),
    ON(1, "Alarm condition met and not silenced"),
    SILENCED(2, "Alarm condition met and silenced");

    private final int value;
    private final String description;

    SeatalkAlarmStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static SeatalkAlarmStatus valueOf(int v) {
        switch (v) {
            case 0: return OFF;
            case 1: return ON;
            case 2: return SILENCED;
            default: return null;
        }
    }

    @Override
    public String toString() {
        return description;
    }

    public int getValue() {
        return value;
    }
}
