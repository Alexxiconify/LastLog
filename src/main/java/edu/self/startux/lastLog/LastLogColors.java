/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright 2012-2018 StarTux.
 *
 * This file is part of LastLog.
 *
 * LastLog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LastLog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LastLog.  If not, see <http://www.gnu.org/licenses/>.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package edu.self.startux.lastLog;

import net.kyori.adventure.text.format.NamedTextColor;

final class LastLogColors {
    private LastLogColors() { }

    public static final NamedTextColor PLAYER_NAME = NamedTextColor.GOLD;
    public static final NamedTextColor HEADER = NamedTextColor.AQUA;
    public static final NamedTextColor MINOR = NamedTextColor.DARK_AQUA;
    public static final NamedTextColor DATE = NamedTextColor.YELLOW;
    public static final NamedTextColor UNKNOWN = NamedTextColor.GRAY;
    public static final NamedTextColor ERROR = NamedTextColor.RED;
    public static final NamedTextColor RESET = NamedTextColor.WHITE;
    public static final NamedTextColor ONLINE = NamedTextColor.GREEN;
    public static final NamedTextColor OFFLINE = NamedTextColor.RED;
}
