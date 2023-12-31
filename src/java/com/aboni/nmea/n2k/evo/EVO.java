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

package com.aboni.nmea.n2k.evo;

import com.aboni.nmea.n2k.N2KMessage;

public interface EVO {
    N2KMessage getAUTOMessage();

    N2KMessage getSTDBYMessage();

    N2KMessage getLockHeadingMessage(double heading);

    N2KMessage getWindDatumMessage(double windAngle);

    N2KMessage getVANEMessage();
}
