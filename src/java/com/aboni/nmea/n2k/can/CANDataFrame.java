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

package com.aboni.nmea.n2k.can;

import com.google.common.hash.HashCode;

import java.util.Arrays;

public class CANDataFrame {

    private final long id;
    private final byte[] data;

    public static CANDataFrame create(long id, byte[] data) {
        return new CANDataFrame(id, data);
    }

    private CANDataFrame(long id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public int getDataSize() {
        return data.length;
    }

    @Override
    public int hashCode() {
        return HashCode.fromLong(id).asInt() + HashCode.fromBytes(data).asInt();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CANDataFrame) {
            return id == ((CANDataFrame) o).id &&
                    Arrays.equals(data, ((CANDataFrame) o).data);
        } else return false;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("frame ").append(getId()).append(" [").append(getDataSize()).append("]");
        for (byte bt : data) b.append(String.format(" %02x", bt));
        return b.toString();

    }
}
