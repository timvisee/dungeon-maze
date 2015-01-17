package com.timvisee.dungeonmaze.command.installupdate;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.command.Command;
import com.timvisee.dungeonmaze.update.Updater;
import com.timvisee.dungeonmaze.util.Profiler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class InstallUpdateCommand extends Command {

    /** Defines the applicable command labels for this command. */
    private static final List<String> APPLICABLE_COMMANDS = new ArrayList<String>() {{
        add("installupdates");
        add("installupdate");
        add("install");
        add("iu");
    }};
    /** Defines the minimum number of required arguments for this command. */
    private static final int MIN_ARGS = 0;
    /** Defines the maximum number of required arguments for this command, or a negative number to ignore this. */
    private static final int MAX_ARGS = 0;
    /** Defines the permission node required to execute this command. */
    private static final String PERMISSION_NODE = "dungeonmaze.command.installupdate";

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
        // Profile the process
        Profiler p = new Profiler(true);

        // Show a status message
        sender.sendMessage(ChatColor.YELLOW + "Checking for Dungeon Maze updates...");

        // Get the update checker and refresh the updates data
        // TODO: Force check for an update!
        // TODO: Automatically install!
        Updater uc = Core.getUpdateChecker();

        // Make sure any update is available
        if(uc.getResult() != Updater.UpdateResult.SUCCESS && uc.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE) {
            sender.sendMessage(ChatColor.GREEN + "You are running the latest Dungeon Maze version!");
            return true;
        }

        // Get the version number of the new update
        String newVer = uc.getLatestName();

        // Show a status message
        sender.sendMessage(ChatColor.GREEN + "Update checking succeed, took " + p.getTimeFormatted() + "!");

        // Make sure the new version is compatible with the current bukkit version
        if(uc.getResult() == Updater.UpdateResult.FAIL_NOVERSION) {
            // Show a message
            sender.sendMessage(ChatColor.GREEN + "New Dungeon Maze version available: " + String.valueOf(newVer));
            sender.sendMessage(ChatColor.GREEN + "The new version is not compatible with your Bukkit version!");
            sender.sendMessage(ChatColor.GREEN + "Please update your Bukkit to " +  uc.getLatestGameVersion() + " or higher!");
            return true;
        }

        // Check whether the update was installed or not
        if(uc.getResult() == Updater.UpdateResult.SUCCESS)
            sender.sendMessage(ChatColor.GREEN + "New version installed (v" + String.valueOf(newVer) + "). Server reboot required!");

        else {
            sender.sendMessage(ChatColor.GREEN + "New version found: " + String.valueOf(newVer));
            sender.sendMessage(ChatColor.DARK_RED + "Automatic installation failed, please update manually!");
        }

        // Return the result
        return true;
    }
}
