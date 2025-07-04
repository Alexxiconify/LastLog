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

import java.io.InputStreamReader;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.Component;

final class HelpScreen {
    private Component helpComponent;

    HelpScreen(LastLogPlugin plugin) {
        ConfigurationSection config = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("help.yml")));
        MiniMessage miniMessage = MiniMessage.miniMessage();
        helpComponent = miniMessage.deserialize(config.getString("helpmessage"));
    }

    void send(CommandSender sender) {
        sender.sendMessage(Component.text("[LastLog] Help", LastLogColors.HEADER));
        sender.sendMessage(helpComponent);
    }
}
