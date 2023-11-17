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

import com.aboni.utils.JSONUtils;
import org.json.JSONObject;

public interface MsgAttitude extends Message {

    int getSID();

    double getYaw();

    double getPitch();

    double getRoll();

    default @Override
    JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("topic", "attitude");
        JSONUtils.addDouble(json, getYaw(), "yaw");
        JSONUtils.addDouble(json, getRoll(), "roll");
        JSONUtils.addDouble(json, getPitch(), "pitch");
        return json;
    }

    default @Override
    String getMessageContentType() {
        return "Attitude";
    }
}
