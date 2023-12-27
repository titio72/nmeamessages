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

package com.aboni.nmea;

import com.aboni.nmea.n2k.N2KFastCache;
import com.aboni.nmea.n2k.N2KMessageParserFactory;
import com.aboni.nmea.n2k.N2KStream;
import com.aboni.nmea.n2k.PGNSourceFilter;
import com.aboni.nmea.n2k.impl.N2KFastCacheImpl;
import com.aboni.nmea.n2k.impl.N2KMessageParserFactoryImpl;
import com.aboni.nmea.n2k.impl.N2KStreamImpl;
import com.aboni.nmea.n2k.impl.PGNSourceFilterImpl;
import com.aboni.nmea.n2k.messages.N2KMessageFactory;
import com.aboni.nmea.n2k.messages.impl.N2KMessageFactoryImpl;
import com.aboni.nmea.nmea0183.Message2NMEA0183;
import com.aboni.nmea.nmea0183.NMEA0183MessageFactory;
import com.aboni.nmea.nmea0183.impl.Message2NMEA0183Impl;
import com.aboni.nmea.nmea0183.impl.NMEA0183MessageFactoryImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class NMEAMessagesModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Message2NMEA0183.class).to(Message2NMEA0183Impl.class);
        bind(N2KMessageFactory.class).to(N2KMessageFactoryImpl.class).in(Singleton.class);
        bind(NMEA0183MessageFactory.class).to(NMEA0183MessageFactoryImpl.class).in(Singleton.class);
        bind(N2KMessageParserFactory.class).to(N2KMessageParserFactoryImpl.class).in(Singleton.class);
        bind(N2KFastCache.class).to(N2KFastCacheImpl.class);
        bind(N2KStream.class).to(N2KStreamImpl.class);
        bind(PGNSourceFilter.class).to(PGNSourceFilterImpl.class).in(Singleton.class);
    }
}
