package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VersionCommand extends ExecutableCommand {

    /**
     * Execute the command.
     *
     * @param sender           The command sender.
     * @param commandReference The command reference.
     * @param commandArguments The command arguments.
     *
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean executeCommand(CommandSender sender, CommandReference commandReference, CommandArguments commandArguments) {
        // Show some version info
        sender.sendMessage(ChatColor.GREEN + "==========[ DUNGEON MAZE ABOUT ]==========");
        sender.sendMessage(ChatColor.GOLD + "Release: " + ChatColor.WHITE + "Dungeon Maze v" + DungeonMaze.instance.getVersion());
        sender.sendMessage(ChatColor.GOLD + "Developers:");
        printDeveloper(sender, "Tim Visee", "timvisee", "Lead Developer");
        printDeveloper(sender, "Xephi", "xephi", "Contributor");
        sender.sendMessage(ChatColor.GOLD + "Website: " + ChatColor.WHITE + "http://timvisee.com/");
        sender.sendMessage(ChatColor.GOLD + "License: " + ChatColor.WHITE + "GNU GPL v2.0");
        sender.sendMessage(ChatColor.GOLD + "Copyright: " + ChatColor.WHITE + "Copyright (c) Tim Visee 2015. All rights reserved.");
        return true;
    }

    /**
     * Print a developer with proper styling.
     *
     * @param sender The command sender.
     * @param name The display name of the developer.
     * @param minecraftName The Minecraft username of the developer, if available.
     * @param function The function of the developer.
     */
    @SuppressWarnings("StringConcatenationInsideStringBufferAppend")
    private void printDeveloper(CommandSender sender, String name, String minecraftName, String function) {
        // Print the name
        StringBuilder msg = new StringBuilder();
        msg.append(ChatColor.DARK_GRAY + " - " + ChatColor.WHITE);
        msg.append(name);

        // Append the Minecraft name, if available
        if(minecraftName.length() != 0)
            msg.append(ChatColor.DARK_GRAY + ", " + ChatColor.WHITE + minecraftName);
        msg.append(ChatColor.DARK_GRAY + " (" + function + ")");

        // Show the online status
        if(minecraftName.length() != 0)
            if(isPlayerOnline(minecraftName))
                msg.append(ChatColor.GREEN + " (In-Game)");

        // Print the message
        sender.sendMessage(msg.toString());
    }

    /**
     * Check whether a player is online.
     *
     * @param minecraftName The Minecraft player name.
     *
     * @return True if the player is online, false otherwise.
     */
    private boolean isPlayerOnline(String minecraftName) {
        for(Player player : Bukkit.getOnlinePlayers())
            if(player.getName().equalsIgnoreCase(minecraftName))
                return true;
        return false;
    }
}
