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

public interface MsgWaterDepth extends Message {

    int getSID();

    double getDepth();

    double getOffset();

    double getRange();

    @Override
    default JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("topic", "DPT");
        double d = getDepth();
        double o = getOffset();
        if (JSONUtils.addDouble(json, d, "raw_depth") && JSONUtils.addDouble(json, o, "offset")) {
            JSONUtils.addDouble(json, d + o, "depth");
        }
        JSONUtils.addDouble(json, getRange(), "range");
        return json;
    }

    @Override
    default String getMessageContentType() {
        return "Depth";
    }
}
