/*
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
 */

package edu.self.startux.lastLog;

import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.kyori.adventure.text.Component;

final class LogInfoExecutor implements CommandExecutor {
    private LastLogPlugin plugin;

    LogInfoExecutor(LastLogPlugin plugin) {
        this.plugin = plugin;
    }

    public OfflinePlayer findPlayer(UUID uuid) {
        // try match exact name with any known player
        OfflinePlayer player = plugin.getServer().getOfflinePlayer(uuid);
        // if none is found, be more lenient with online players
        if (player != null && player.getFirstPlayed() <= 0) {
            player = plugin.getServer().getPlayer(uuid);
        }

        return player;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender.equals(plugin.getServer().getConsoleSender())) {
                return false;
            }
            args = new String[1];
            args[0] = sender.getName();
        }
        if (args.length == 1 && (args[0].equals("help") || args[0].equals("?"))) {
            plugin.help(sender);
            return true;
        }
        for (String arg : args) {
            if (arg.equalsIgnoreCase(sender.getName())) {
                if (!sender.hasPermission("lastlog.loginfo") && !sender.hasPermission("lastlog.self")) {
                    sender.sendMessage(LastLogColors.ERROR + "You don't have permission!");
                    return true;
                }
            } else {
                if (!sender.hasPermission("lastlog.loginfo")) {
                    sender.sendMessage(LastLogColors.ERROR + "You don't have permission!");
                    return true;
                }
            }
            OfflinePlayer player = findByName(arg);
            if (player == null) {
                sender.sendMessage(Component.text()
                    .append(Component.text("Player "))
                    .append(Component.text(arg, LastLogColors.UNKNOWN))
                    .append(Component.text(" is unknown", LastLogColors.RESET))
                    .build());
            } else {
                String name = player.getName();
                long first = player.getFirstPlayed();
                // Sometimes Bukkit stubbornly reports bogus dates even though they were accurate during initialization.
                // In those cases, fetch them from the cache.
                if (first <= 0L) {
                    PlayerList.Entry entry = findByName(plugin.getPlayerList(false), name);
                    if (entry != null) {
                        first = entry.time;
                    }
                }
                long last = player.getLastLogin();
                if (last <= 0L) {
                    PlayerList.Entry entry = findByName(plugin.getPlayerList(true), name);
                    if (entry != null) {
                        last = entry.time;
                    }
                }
                sender.sendMessage(Component.text()
                    .append(Component.text("Player "))
                    .append(Component.text(name, LastLogColors.PLAYER_NAME))
                    .append(Component.text(player.isOnline() ? " Online" : " Offline", player.isOnline() ? LastLogColors.ONLINE : LastLogColors.OFFLINE))
                    .build());
                sender.sendMessage(Component.text()
                    .append(Component.text(new LastLogDate(first).toString(), LastLogColors.DATE))
                    .append(Component.text(" First Login", LastLogColors.RESET))
                    .build());
                sender.sendMessage(Component.text()
                    .append(Component.text(new LastLogDate(last).toString(), LastLogColors.DATE))
                    .append(Component.text(" Last Login", LastLogColors.RESET))
                    .build());
            }
        }
        return true;
    }

    public PlayerList.Entry findByName(PlayerList playerList, String name) {
        for (PlayerList.Entry entry: playerList) {
            if (entry.name.equalsIgnoreCase(name)) {
                return entry;
            }
        }
        return null;
    }

    public OfflinePlayer findByName(String name) {
        OfflinePlayer[] players = plugin.getServer().getOfflinePlayers();
        for (OfflinePlayer player: players) {
            //Not sure why these nulls can happen but they do...
            if (player != null && player.getName() != null && player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }
}
