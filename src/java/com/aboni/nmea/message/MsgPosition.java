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

import com.aboni.utils.Utils;
import net.sf.marineapi.nmea.util.Position;
import org.json.JSONObject;

public interface MsgPosition extends Message {

    Position getPosition();

    @Override
    default String getMessageContentType() {
        return "Position";
    }

    @Override
    default JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("topic", "position");
        if (getPosition() != null) {
            j.put("latitude", Utils.formatLatitude(getPosition().getLatitude()));
            j.put("longitude", Utils.formatLongitude(getPosition().getLongitude()));
            j.put("dec_latitude", getPosition().getLatitude());
            j.put("dec_longitude", getPosition().getLongitude());
        }
        return j;
    }
}
