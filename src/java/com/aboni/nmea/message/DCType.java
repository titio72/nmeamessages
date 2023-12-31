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

public enum DCType {

    UNKNOWN(-1),
    BATTERY(0),
    ALTERNATOR(1),
    CONVERTER(2),
    SOLAR_CELL(3),
    WIND_GENERATOR(4);

    private final int id;

    DCType(int id) {
        this.id = id;
    }

    public static DCType valueOf(int v) {
        switch (v) {
            case 0:
                return BATTERY;
            case 1:
                return ALTERNATOR;
            case 2:
                return CONVERTER;
            case 3:
                return SOLAR_CELL;
            case 4:
                return WIND_GENERATOR;
            default:
                return UNKNOWN;
        }
    }

    @Override
    public final String toString() {
        switch (id) {
            case 0:
                return "Battery";
            case 1:
                return "Alternator";
            case 2:
                return "Convertor";
            case 3:
                return "Solar Cell";
            case 4:
                return "Wind Generator";
            default:
                return "Unknown";
        }
    }

    public int toValue() {
        return id;
    }
}
