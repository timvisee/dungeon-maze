package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.CommandParts;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateWorldCommand extends ExecutableCommand {

    /**
     * Execute the command.
     *
     * @param sender The command sender.
     * @param commandReference The command reference.
     * @param commandArguments The command arguments.
     *
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean executeCommand(CommandSender sender, CommandParts commandReference, CommandParts commandArguments) {
        // Get and trim the preferred world name
        String worldName = commandArguments.get(0).trim();

        // Validate the world name
        if(!WorldManager.isValidWorldName(worldName)) {
            sender.sendMessage(ChatColor.DARK_RED + worldName);
            sender.sendMessage(ChatColor.DARK_RED + "The world name contains invalid characters!");
            return true;
        }

        // Get the world manager, and make sure it's valid
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to create the world, world manager not available!");
            return false;
        }
        if(!worldManager.isInit()) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to create the world, world manager not available!");
            return true;
        }

        // Make sure the world doesn't exist
        if(worldManager.isWorld(worldName)) {
            sender.sendMessage(ChatColor.DARK_RED + "The world " + ChatColor.GOLD + worldName + ChatColor.DARK_RED + " already exists!");
            return true;
        }

        // Show a status message
        sender.sendMessage(ChatColor.YELLOW + "Preparing the server...");

        // Prepare the server for the new world
        if(!worldManager.prepareDungeonMazeWorld(worldName)) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to prepare the server!");
            return true;
        }

        // Show a status message
        sender.sendMessage(ChatColor.YELLOW + "Generating the DungeonMaze " + ChatColor.GOLD + worldName + ChatColor.YELLOW + "...");
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Generating a new world, expecting lag for a while...");

        // Create the world
        WorldCreator newWorld = new WorldCreator(worldName);
        newWorld.generator(DungeonMaze.instance.getDungeonMazeGenerator());
        World world = newWorld.createWorld();

        // Show a status message
        Bukkit.broadcastMessage("World generation finished!");
        sender.sendMessage(ChatColor.GREEN + "The DungeonMaze " + ChatColor.GOLD + worldName + ChatColor.GREEN + " has successfully been generated!");

        // If the command was executed by a player, teleport the player
        if(sender instanceof Player) {
            // Teleport the player
            ((Player) sender).teleport(world.getSpawnLocation());
            sender.sendMessage(ChatColor.GREEN + "You have been teleported!");
        }

        // Return the result
        return true;
    }
}
