package com.timvisee.dungeonmaze.command.listworld;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.command.Command;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ListWorldCommand extends Command {

    /** Defines the applicable command labels for this command. */
    private static final List<String> APPLICABLE_COMMANDS = new ArrayList<String>() {{
        add("listworlds");
        add("listworld");
        add("list");
        add("worlds");
        add("lw");
    }};
    /** Defines the minimum number of required arguments for this command. */
    private static final int MIN_ARGS = 0;
    /** Defines the maximum number of required arguments for this command, or a negative number to ignore this. */
    private static final int MAX_ARGS = 0;
    /** Defines the permission node required to execute this command. */
    private static final String PERMISSION_NODE = "dungeonmaze.command.listworlds";

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
        // Get the list of Dungeon Maze worlds
        List<String> worlds = Core.getWorldManager().getDungeonMazeWorlds();

        // Get the Dungeon Maze world manager, and make sure the instance is valid
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to list the Dungeon Maze worlds!");
            return false;
        }

        // Show the list of worlds
        sender.sendMessage(ChatColor.YELLOW + "==========[ DUNGEON MAZE WORLDS ]==========");
        if(worlds.size() > 0) {
            for(String w : worlds) {
                if(worldManager.isLoadedDungeonMazeWorld(w))
                    sender.sendMessage(ChatColor.GOLD + " - " + w + "   " + ChatColor.GREEN + "Loaded");
                else
                    sender.sendMessage(ChatColor.GOLD + " - " + w + "   " + ChatColor.DARK_RED + "Not Loaded");
            }
        } else
            // No Dungeon Maze world available, show a message
            sender.sendMessage(ChatColor.DARK_RED + "You don't have any Dungeon Maze world yet!");

        // Return the result
        return true;
    }
}
