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

package com.aboni.nmea.nmea0183;

import com.aboni.nmea.message.Message;
import net.sf.marineapi.nmea.sentence.*;

public class NMEA0183Message implements Message {

    private final Sentence sentence;

    public NMEA0183Message(Sentence sentence) {
        if (sentence==null) throw new IllegalArgumentException("Sentence is null");
        this.sentence = sentence;
    }

    public Sentence getSentence() {
        return sentence;
    }

    @Override
    public String toString() {
        return String.format("Message {%s}", sentence);
    }

    @Override
    public String getMessageType() {
        return sentence.getSentenceId();
    }

    @Override
    public String getMessageOrigin() {
        return sentence.getTalkerId().name();
    }

    @Override
    public String getMessageContentType() {
        return "";
    }
}
