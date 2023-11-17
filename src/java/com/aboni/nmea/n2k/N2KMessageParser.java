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

package com.aboni.nmea.n2k;

/**
 * N2K message parser. It reads raw messages (untyped N2KMessage that represent a single frame) or strings in the
 * candump2analyzer format (see https://github.com/canboat/canboat) and extract a typed N2KMessage.
 * It supports "fast" messages, that are messages made up of multiple frames.
 * The class is used by adding the message parts and keep adding until needMore is true.
 */
public interface N2KMessageParser {

    void addMessage(N2KMessage msg) throws PGNDataParseException;

    /**
     * Add strings that represent a frame in the candump2analyzer format.
     * Ex: 2023-11-15-06:57:11.704,2,129025,22,255,8,b7,6d,00,19,2c,9c,e0,04
     * @param s The can message to be added to the parser.
     * @throws PGNDataParseException When the message is malformed or inconsistent with the other messages.
     */
    void addString(String s) throws PGNDataParseException;

    N2KMessageHeader getHeader();

    int getLength();

    byte[] getData();

    boolean needMore();

    /**
     * Checks if the PGN of the underlying message is supported, so the message can be parsed and a typed message returned.
     * @return true when the message pgn is supported.
     */
    boolean isSupported();

    /**
     * Extract the typed message (if supported).
     * @return A typed message.
     * @throws PGNDataParseException If the parsing fails.
     */
    N2KMessage getMessage() throws PGNDataParseException;

    boolean isFast();

    int getFastSequenceNo();
}
