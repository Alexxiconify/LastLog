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

import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class LastLogPlugin extends JavaPlugin implements Listener {
    public static final String NOTIFY_PERMISSION = "lastlog.notify";
    private PlayerList firstlogList;
    private PlayerList lastlogList;
    private LastLogExecutor firstLogExecutor = new LastLogExecutor(this, false);
    private LastLogExecutor lastLogExecutor = new LastLogExecutor(this, true);
    private LogInfoExecutor logInfoExecutor = new LogInfoExecutor(this);
    private HelpScreen helpScreen;

    @Override
    public void onEnable() {
        // Register commands programmatically for Paper compatibility
        registerCommand("firstlog", firstLogExecutor, "lastlog.firstlog", "Show listing of the last players who logged in for the first time", "Usage: /firstlog [options] [pagenumber]");
        registerCommand("lastlog", lastLogExecutor, "lastlog.lastlog", "Show listing of the last logged in players", "Usage: /lastlog [options] [pagenumber]");
        registerCommand("loginfo", logInfoExecutor, "lastlog.loginfo", "Show first and last login date of a user", "Usage: /loginfo <playername>");
        
        getServer().getPluginManager().registerEvents(this, this);
        // Apparently what takes the most time are the following I/O heavy instructions.
        // Hence, their output will be cached. Initialize the cache with bukkit data.
        OfflinePlayer[] players = getServer().getOfflinePlayers();
        firstlogList = new PlayerList(players, false);
        lastlogList = new PlayerList(players, true);
        helpScreen = new HelpScreen(this);
    }

    @Override
    public void onDisable() {
        firstlogList = null;
        lastlogList = null;
        helpScreen = null;
    }

    /**
     * Register a command programmatically for Paper compatibility
     */
    private void registerCommand(String name, org.bukkit.command.CommandExecutor executor, String permission, String description, String usage) {
        Command command = new Command(name) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                return executor.onCommand(sender, this, commandLabel, args);
            }
        };
        command.setPermission(permission);
        command.setDescription(description);
        command.setUsage(usage);
        getServer().getCommandMap().register(name, command);
    }

    /**
     * Update the appropriate caches when a player joins.
     * At this point we get the time from System rather than
     * from Bukkit as the latter turned out faulty at times.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        UUID uuid = player.getUniqueId();
        long last = System.currentTimeMillis();
        lastlogList.set(uuid, name, last);
        if (!player.hasPlayedBefore()) {
            long first = last;
            firstlogList.set(uuid, name, first);
            String message = LastLogColors.UNKNOWN + name + LastLogColors.HEADER + " has logged in for the first time";
            // getServer().broadcast(message, NOTIFY_PERMISSION); // this acts weird
            getServer().getConsoleSender().sendMessage(message);
            for (Player rec : getServer().getOnlinePlayers()) {
                if (rec.hasPermission(NOTIFY_PERMISSION)) {
                    rec.sendMessage(message);
                }
            }
        }
    }

    public void help(CommandSender sender) {
        helpScreen.send(sender);
    }

    public PlayerList getPlayerList(boolean lastlog) {
        if (lastlog) return lastlogList;
        return firstlogList;
    }
}
