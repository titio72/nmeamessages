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

public class Satellite {
    private final int id;
    private final int elevation;
    private final int azimuth;
    private final int srn;
    private final int status;

    public Satellite(int id, int elevation, int azimuth, int srn, int status) {
        this.id = id;
        this.azimuth = azimuth;
        this.elevation = elevation;
        this.srn = srn;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getElevation() {
        return elevation;
    }

    public int getAzimuth() {
        return azimuth;
    }

    public int getSrn() {
        return srn;
    }

    public String getStatus() {
        switch (status) {
            case 0:
                return "Not tracked";
            case 1:
                return "Tracked";
            case 2:
                return "Used";
            case 3:
                return "Not tracked+Diff";
            case 4:
                return "Tracked+Diff";
            case 5:
                return "Used+Diff";
            default:
                return "Undef";
        }
    }
}
