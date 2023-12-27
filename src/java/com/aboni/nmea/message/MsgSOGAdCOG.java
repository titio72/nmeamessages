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

import org.json.JSONObject;

public interface MsgSOGAdCOG extends Message {

    int getSID();

    double getSOG();

    double getCOG();

    String getCOGReference();

    boolean isTrueCOG();

    @Override
    default String getMessageContentType() {
        return "SOGAndCOG";
    }

    @Override
    default JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("sog", getSOG());
        j.put("cog", getCOG());
        j.put("cogReference", getCOGReference());
        return j;
    }
}
