package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.command.CommandParts;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import com.timvisee.dungeonmaze.update.Updater;
import com.timvisee.dungeonmaze.util.Profiler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class InstallUpdateCommand extends ExecutableCommand {

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
