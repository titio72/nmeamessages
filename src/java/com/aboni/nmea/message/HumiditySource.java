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

public enum HumiditySource {

    UNKNOWN(-1),
    INSIDE(0),
    OUTSIDE(1);

    private final int source;

    HumiditySource(int i) {
        source = i;
    }

    public static HumiditySource valueOf(int i) {
        switch (i) {
            case 0:
                return INSIDE;
            case 1:
                return OUTSIDE;
            default:
                return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        switch (source) {
            case 0:
                return "Inside";
            case 1:
                return "Outside";
            default:
                return "Unknown";
        }
    }
}
