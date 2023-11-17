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

package com.aboni.nmea.n2k.messages;

import com.aboni.nmea.n2k.N2KMessage;
import com.aboni.nmea.n2k.N2KMessageHeader;
import com.aboni.nmea.n2k.PGNDataParseException;

public interface N2KMessageFactory {

    boolean isSupported(int pgn);

    boolean isFast(int pgn);

    N2KMessage newUntypedInstance(N2KMessageHeader h, byte[] data);

    N2KMessage newInstance(N2KMessageHeader h, byte[] data) throws PGNDataParseException;

    N2KMessage newInstance(int pgn, byte[] data) throws PGNDataParseException;

}
