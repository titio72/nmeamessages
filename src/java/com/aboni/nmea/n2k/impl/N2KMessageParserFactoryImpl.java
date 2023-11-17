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

package com.aboni.nmea.n2k.impl;

import com.aboni.nmea.n2k.messages.N2KMessageFactory;
import com.aboni.nmea.n2k.N2KMessageParser;
import com.aboni.nmea.n2k.N2KMessageParserFactory;

import javax.inject.Inject;

public class N2KMessageParserFactoryImpl implements N2KMessageParserFactory {

    private final N2KMessageFactory messageFactory;

    @Inject
    public N2KMessageParserFactoryImpl(N2KMessageFactory messageFactory) {
        if (messageFactory==null) throw new IllegalArgumentException("Message factory is null");
        this.messageFactory = messageFactory;
    }

    @Override
    public N2KMessageParser getNewParser() {
        return new N2KMessageParserImpl(messageFactory);
    }
}
