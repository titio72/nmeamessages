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

import com.aboni.nmea.NMEAMessagesModule;
import com.aboni.nmea.UtilsModule;
import com.aboni.nmea.n2k.N2KFastCache;
import com.aboni.nmea.n2k.N2KMessage;
import com.aboni.nmea.n2k.N2KMessageHeader;
import com.aboni.nmea.n2k.PGNDataParseException;
import com.aboni.nmea.n2k.messages.N2KMessageFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class N2KFastCacheImplTest {

    private N2KFastCache cache;
    private N2KMessageFactory factory;

    private class MyH implements N2KMessageHeader {

        @Override
        public int getPgn() {
            return 129029;
        }

        @Override
        public int getSource() {
            return 22;
        }

        @Override
        public int getDest() {
            return 255;
        }

        @Override
        public int getPriority() {
            return 2;
        }

        @Override
        public Instant getTimestamp() {
            return Instant.now();
        }
    }

    @Before
    public void setUp() {
        Injector injector = Guice.createInjector(new NMEAMessagesModule(), new UtilsModule());

        cache = injector.getInstance(N2KFastCache.class);
        factory = injector.getInstance(N2KMessageFactory.class);
    }

    @Test
    public void test() throws PGNDataParseException {
        final AtomicInteger i = new AtomicInteger();
        cache.setCallback(msg -> {
            System.out.println(msg);
            assertEquals(7, i.get());
        });
        N2KMessage[] msgs = new N2KMessage[]{
                factory.newUntypedInstance(new MyH(), new byte[]{(byte) 0x00, (byte) 0x2b, (byte) 0x45, (byte) 0x70, (byte) 0x48, (byte) 0x50, (byte) 0x77, (byte) 0xd5})
                ,factory.newUntypedInstance(new MyH(), new byte[]{(byte) 0x01, (byte) 0x2b, (byte) 0x00, (byte) 0x31, (byte) 0xe6, (byte) 0xb2, (byte) 0xbc, (byte) 0xbc})
                ,factory.newUntypedInstance(new MyH(), new byte[]{(byte) 0x02, (byte) 0x0f, (byte) 0x06, (byte) 0x80, (byte) 0xd5, (byte) 0xc4, (byte) 0x57, (byte) 0x28})
                ,factory.newUntypedInstance(new MyH(), new byte[]{(byte) 0x03, (byte) 0x01, (byte) 0x6d, (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff})
                ,factory.newUntypedInstance(new MyH(), new byte[]{(byte) 0x04, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x7f, (byte) 0x10, (byte) 0xfd, (byte) 0x11})
                ,factory.newUntypedInstance(new MyH(), new byte[]{(byte) 0x05, (byte) 0x41, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00})
                ,factory.newUntypedInstance(new MyH(), new byte[]{(byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff})
                };
        for (N2KMessage m: msgs) {
            i.incrementAndGet();
            cache.onMessage(m);
        }
    }
}