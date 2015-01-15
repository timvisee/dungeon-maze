package com.timvisee.dungeonmaze.command.createworld;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.Command;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreateWorldCommand extends Command {

    /** Defines the applicable command labels for this command. */
    private static final List<String> APPLICABLE_COMMANDS = new ArrayList<String>() {{
        add("createworld");
        add("createw");
        add("cworld");
        add("cw");
    }};
    /** Defines the minimum number of required arguments for this command. */
    private static final int MIN_ARGS = 1;
    /** Defines the maximum number of required arguments for this command, or a negative number to ignore this. */
    private static final int MAX_ARGS = 1;
    /** Defines the permission node required to execute this command. */
    private static final String PERMISSION_NODE = "dungeonmaze.command.createworld";

    /**
     * Get a list of applicable command labels for this command.
     *
     * @return A list of applicable command labels.
     */
    @Override
    public List<String> getApplicableCommands() {
        return APPLICABLE_COMMANDS;
    }

    /**
     * The minimum number of arguments this command requires.
     *
     * @return Minimum number of arguments.
     */
    @Override
    public int getMinArgs() {
        return MIN_ARGS;
    }

    /**
     * The maximum number of arguments this command requires.
     *
     * @return Maximum number of arguments or a negative value if this command doesn't have a maximum number of
     * arguments.
     */
    @Override
    public int getMaxArgs() {
        return MAX_ARGS;
    }

    /**
     * Get the permission node required to execute this command as a player.
     *
     * @return The permission node required to execute this command as a player, or an empty string if this command
     * doesn't require any permission.
     */
    @Override
    public String getPermissionNode() {
        return PERMISSION_NODE;
    }

    /**
     * Get the default permission used if the permission couldn't be checked using any permissions plugin.
     *
     * @param sender The command sender to get the default permission for.
     *
     * @return True if the command sender has permission if the permissions system couldn't be used, false otherwise.
     */
    @Override
    public boolean getDefaultPermission(CommandSender sender) {
        return sender.isOp();
    }

    /**
     * Handle the command.
     *
     * @param sender The command sender.
     * @param parentCmd The parent command, or an empty string if there isn't any.
     * @param cmd The command label.
     * @param args The command arguments.
     *
     * @return True if the command was executed, false otherwise.
     */
    @Override
    public boolean onCommand(CommandSender sender, String parentCmd, String cmd, List<String> args) {
        // Get and trim the preferred world name
        String worldName = args.get(0).trim();

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
            sender.sendMessage(ChatColor.DARK_RED + "The world '" + worldName + "' already exists!");
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
        sender.sendMessage(ChatColor.YELLOW + "Generating the DungeonMaze '" + worldName + "'...");
        Bukkit.broadcastMessage("[DungeonMaze] Generating a new world, expecting lag for a while...");

        // Create the world
        WorldCreator newWorld = new WorldCreator(worldName);
        newWorld.generator(DungeonMaze.instance.getDungeonMazeGenerator());
        World world = newWorld.createWorld();

        // Show a status message
        Bukkit.broadcastMessage("[DungeonMaze] World generation finished!");
        sender.sendMessage(ChatColor.GREEN + "The DungeonMaze '" + worldName + "' has successfully been generated!");

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
