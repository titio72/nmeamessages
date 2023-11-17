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

import com.aboni.log.ConsoleLog;
import com.aboni.log.Log;
import com.aboni.utils.*;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class UtilsModule extends AbstractModule {

    private final Log logger = ConsoleLog.getLogger();

    @Override
    protected void configure() {
        bind(Log.class).toInstance(logger);
        bind(TimestampProvider.class).to(DefaultTimestampProvider.class).in(Singleton.class);

    }
}
