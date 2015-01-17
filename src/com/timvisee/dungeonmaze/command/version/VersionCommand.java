package com.timvisee.dungeonmaze.command.version;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class VersionCommand extends Command {

    /** Defines the applicable command labels for this command. */
    @SuppressWarnings("SpellCheckingInspection")
    private static final List<String> APPLICABLE_COMMANDS = new ArrayList<String>() {{
        add("version");
        add("vers");
        add("vrsn");
        add("ver");
        add("v");
    }};
    /** Defines the minimum number of required arguments for this command. */
    private static final int MIN_ARGS = 0;
    /** Defines the maximum number of required arguments for this command, or a negative number to ignore this. */
    private static final int MAX_ARGS = -1;
    /** Defines the permission node required to execute this command. */
    private static final String PERMISSION_NODE = "";

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
        return true;
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
        // Show some version info
        sender.sendMessage(ChatColor.GREEN + "This server is running Dungeon Maze v" + DungeonMaze.instance.getVersion());
        sender.sendMessage(ChatColor.GREEN + "Developed by Tim Visee - http://timvisee.com/");
        return true;
    }
}
