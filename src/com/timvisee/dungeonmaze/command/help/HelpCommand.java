package com.timvisee.dungeonmaze.command.help;

import com.timvisee.dungeonmaze.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends Command {

    /** Defines the applicable command labels for this command. */
    private static final List<String> APPLICABLE_COMMANDS = new ArrayList<String>() {{
        add("help");
        add("hlp");
        add("h");
        add("sos");
        add("?");
        add(".");
        add("-");
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
        // View the help
        sender.sendMessage(ChatColor.GREEN + "==========[ DUNGEON MAZE HELP ]==========");
        sender.sendMessage(ChatColor.GOLD + "/" + parentCmd + " help " + ChatColor.WHITE + ": View help");
        sender.sendMessage(ChatColor.GOLD + "/" + parentCmd + " createworld <name>" + ChatColor.WHITE + ": Create a Dungeon Maze world");
        sender.sendMessage(ChatColor.GOLD + "/" + parentCmd + " teleport <world> " + ChatColor.WHITE + ": Teleport to a world");
        sender.sendMessage(ChatColor.GOLD + "/" + parentCmd + " listworlds " + ChatColor.WHITE + ": List Dungeon Maze worlds");
        sender.sendMessage(ChatColor.GOLD + "/" + parentCmd + " reload " + ChatColor.WHITE + ": Reload config files");
        sender.sendMessage(ChatColor.GOLD + "/" + parentCmd + " reloadperms " + ChatColor.WHITE + ": Reload permissions system");
        sender.sendMessage(ChatColor.GOLD + "/" + parentCmd + " checkupdates " + ChatColor.WHITE + ": Check for updates");
        sender.sendMessage(ChatColor.GOLD + "/" + parentCmd + " installupdate" + ChatColor.WHITE + ": Install new updates");
        sender.sendMessage(ChatColor.GOLD + "/" + parentCmd + " version " + ChatColor.WHITE + ": Check plugin version");

        // Return the result
        return true;
    }
}
