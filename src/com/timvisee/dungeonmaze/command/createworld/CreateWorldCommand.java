package com.timvisee.dungeonmaze.command.createworld;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.Command;
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

        // TODO: Validate the world name

        // Make sure the world doesn't exist
        if(DungeonMaze.instance.worldExists(worldName)) {
            sender.sendMessage(ChatColor.DARK_RED + "The world '" + worldName + "' already exists!");
            return true;
        }

        // Show a status message
        sender.sendMessage(ChatColor.YELLOW + "Preparing the server...");

        // Edit the bukkit.yml file so bukkit knows what generator to use for the Dungeon Maze worlds,
        // also update the Dungeon Maze files.
        System.out.println("Editing bukkit.yml file...");
        FileConfiguration serverConfig = DungeonMaze.instance.getConfigFromPath(new File("bukkit.yml"));
        serverConfig.set("worlds." + worldName + ".generator", "DungeonMaze");
        try {
            serverConfig.save(new File("bukkit.yml"));
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        System.out.println("Editing Dungeon Maze config.yml file...");
        List<String> worlds = Core.getConfigHandler().config.getStringList("worlds");
        if(!worlds.contains(worldName))
            worlds.add(worldName);
        Core.getConfigHandler().config.set("worlds", worlds);
        List<String> preloadWorlds = Core.getConfigHandler().config.getStringList("preloadWorlds");
        if(!preloadWorlds.contains(worldName))
            preloadWorlds.add(worldName);
        Core.getConfigHandler().config.set("preloadWorlds", preloadWorlds);
        DungeonMaze.instance.saveConfig();
        System.out.println("Editing finished!");

        // Show a status message
        sender.sendMessage(ChatColor.YELLOW + "Generating the DungeonMaze '" + worldName + "'...");
        // TODO: Could cause some lag, broadcast this to the server!

        // Create the world
        WorldCreator newWorld = new WorldCreator(worldName);
        newWorld.generator(DungeonMaze.instance.getDMWorldGenerator());
        World world = newWorld.createWorld();

        // Show a status message
        // TODO: Is this message a duplicate of the message bellow?
        sender.sendMessage(ChatColor.GREEN + "The DungeonMaze '" + worldName + "' has successfully been generated!");

        // If the command was executed by a player, teleport the player
        if(sender instanceof Player) {
            // Get the player instance
            Player player = (Player) sender;
            player.teleport(world.getSpawnLocation());
            player.sendMessage(ChatColor.GREEN + "The world has been successfully generated! You have been teleported.");
        }

        // Return the result
        return true;
    }
}
