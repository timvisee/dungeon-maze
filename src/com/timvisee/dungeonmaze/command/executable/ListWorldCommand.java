package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.command.CommandParts;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListWorldCommand extends ExecutableCommand {

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
    public boolean executeCommand(CommandSender sender, CommandParts commandReference, CommandParts commandArguments) {
        // Get the list of Dungeon Maze worlds
        List<String> worlds = Core.getWorldManager().getDungeonMazeWorlds();

        // Get the Dungeon Maze world manager, and make sure the instance is valid
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to list the Dungeon Maze worlds!");
            return false;
        }

        // Show the list of worlds
        sender.sendMessage(ChatColor.GREEN + "==========[ DUNGEON MAZE WORLDS ]==========");
        if(worlds.size() > 0) {
            for(String w : worlds) {
                if(worldManager.isLoadedDungeonMazeWorld(w))
                    sender.sendMessage(ChatColor.GOLD + " " + w + ": " + ChatColor.GREEN  + ChatColor.ITALIC + "Loaded");
                else
                    sender.sendMessage(ChatColor.GOLD + " " + w + ": " + ChatColor.DARK_RED + ChatColor.ITALIC + "Not Loaded");
            }
        } else
            // No Dungeon Maze world available, show a message
            sender.sendMessage(ChatColor.DARK_RED + "You don't have any Dungeon Maze world yet!");

        // Return the result
        return true;
    }
}
