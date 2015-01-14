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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
    /** Minecraft world name validation Regex. */
    private static final String MINECRAFT_WORLD_NAME_REGEX = "^[[\\p{Alnum}]_-]+";

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
     * Handle the command.
     *
     * @param sender The command sender.
     * @param cmd    The command label.
     * @param args   The command arguments.
     *
     * @return True if the command was executed, false otherwise.
     */
    @Override
    public boolean onCommand(CommandSender sender, String cmd, List<String> args) {
        // If the command is executed by a player, make sure the player has permission
        if(sender instanceof Player) {
            if(!Core.getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.createworld", sender.isOp())) {
                sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
                return true;
            }
        }

        // Get and trim the preferred world name
        String worldName = args.get(0).trim();

        // Validate the world name
        if(!isValidWorldName(worldName)) {
            sender.sendMessage(ChatColor.DARK_RED + worldName);
            sender.sendMessage(ChatColor.DARK_RED + "The world name contains invalid characters!");
            return true;
        }

        // Make sure the world doesn't exist
        if(DungeonMaze.instance.worldExists(worldName)) {
            sender.sendMessage(ChatColor.DARK_RED + "The world '" + worldName + "' already exists!");
            return true;
        }

        // Show a status message
        sender.sendMessage(ChatColor.YELLOW + "Preparing the server...");

        // Get the world manager, and make sure it's available
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to prepare the server, world manager not available!");
            return true;
        }
        if(!worldManager.isInit()) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to prepare the server, world manager not available!");
            return true;
        }

        // Prepare the server for the new world
        if(!worldManager.prepareDMWorld(worldName)) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to prepare the server!");
            return true;
        }

        // Show a status message
        sender.sendMessage(ChatColor.YELLOW + "Generating the DungeonMaze '" + worldName + "'...");
        Bukkit.broadcastMessage("[DungeonMaze] Generating a new world, expecting lag for a while...");

        // Create the world
        WorldCreator newWorld = new WorldCreator(worldName);
        newWorld.generator(DungeonMaze.instance.getDMWorldGenerator());
        World world = newWorld.createWorld();

        // Show a status message
        // TODO: Is this message a duplicate of the message bellow?
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

    /**
     * Check whether a Minecraft world name is valid.
     *
     * param worldName The world name to validate.
     *
     * @return True if the world name is valid, false otherwise.
     */
    public boolean isValidWorldName(String worldName) {
        // Do a regex check
        return Pattern.compile(MINECRAFT_WORLD_NAME_REGEX).matcher(worldName).matches();
    }
}
