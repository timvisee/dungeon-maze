package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.CommandParts;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import com.timvisee.dungeonmaze.update.UpdateChecker;
import com.timvisee.dungeonmaze.update.UpdateCheckerService;
import com.timvisee.dungeonmaze.update.bukkit.Updater;
import com.timvisee.dungeonmaze.util.MinecraftUtils;
import com.timvisee.dungeonmaze.util.Profiler;
import com.timvisee.dungeonmaze.util.SystemUtils;
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

        // Get the update checker service, shut it down and start it again to force an update check
        UpdateCheckerService service = Core.getUpdateCheckerService();
        service.shutdownUpdateChecker();
        service.setupUpdateChecker();

        // Get the update checker instance
        UpdateChecker updateChecker = service.getUpdateChecker();

        // TODO: Automatically install the actual update!

        // Show a status message
        // TODO: Should we keep this?
        sender.sendMessage(ChatColor.YELLOW + "Update checking succeed, took " + p.getTimeFormatted() + "!");

        // TODO: Make sure some sort of update check has been done, and that the file has been downloaded & installed!

        // Failed to check for updates
        if(updateChecker.hasFailed()) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to check for " + DungeonMaze.PLUGIN_NAME + " updates!");
            return true;
        }

        // No new version found
        if(!updateChecker.isUpdateAvailable()) {
            sender.sendMessage(ChatColor.GREEN + DungeonMaze.PLUGIN_NAME + " is up to date!");
            return true;
        }

        // Make sure the new version is compatible
        if(!updateChecker.isUpdateCompatible()) {
            // Show the new Dungeon Maze version
            sender.sendMessage(ChatColor.GOLD + "A new " + DungeonMaze.PLUGIN_NAME + " version is available, but isn't compatible!");
            sender.sendMessage(ChatColor.GOLD + DungeonMaze.PLUGIN_NAME + " version: " + ChatColor.WHITE + "v" + DungeonMaze.getVersionName() + " (" + DungeonMaze.getVersionCode() + ") " +
                    ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "-->" + ChatColor.GREEN +
                    " v" + updateChecker.getUpdateVersionName() + " (" + updateChecker.getUpdateVersionCode() + ")");

            // Show the Minecraft version
            if(updateChecker.isUpdateMinecraftCompatible())
                sender.sendMessage(ChatColor.GOLD + "Minecraft version: " + ChatColor.GREEN  + "v" + updateChecker.getUpdateMinecraftVersion());
            else
                sender.sendMessage(ChatColor.GOLD + "Required Minecraft version: " + ChatColor.DARK_RED + "v" + MinecraftUtils.getMinecraftVersion() + " " +
                        ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + " --> " +
                        ChatColor.GREEN + " v" + updateChecker.getUpdateMinecraftVersion());

            // Show the Java version
            if(updateChecker.isUpdateJavaCompatible())
                sender.sendMessage(ChatColor.GOLD + "Java version: " + ChatColor.GREEN  + "v" + updateChecker.getUpdateJavaVersion());
            else
                sender.sendMessage(ChatColor.GOLD + "Required Java version: " + ChatColor.DARK_RED + "v" + SystemUtils.getJavaVersion() + " " +
                        ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + " --> " +
                        ChatColor.GREEN + " v" + updateChecker.getUpdateJavaVersion());
            return true;
        }

        // Check whether the update has already been installed
        if(updateChecker.isUpdateInstalled()) {
            sender.sendMessage(ChatColor.GREEN + "A new " + DungeonMaze.PLUGIN_NAME + " version is available, and has been installed already!");
            sender.sendMessage(ChatColor.GOLD + "Reload or restart your server to apply this update.");
            sender.sendMessage(ChatColor.GOLD + DungeonMaze.PLUGIN_NAME + " version: " + ChatColor.WHITE + "v" + DungeonMaze.getVersionName() + " (" + DungeonMaze.getVersionCode() + ") " +
                    ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "-->" + ChatColor.GREEN +
                    " v" + updateChecker.getUpdateVersionName() + " (" + updateChecker.getUpdateVersionCode() + ")");
            return true;
        }

        // If the update has been downloaded, but not installed, something went wrong. Show an error message.
        if(updateChecker.isUpdateDownloaded() || updateChecker.isUpdateAvailable()) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to download and install new " + DungeonMaze.PLUGIN_NAME + " version!");
            return true;
        }

        // An error occurred, show a message
        sender.sendMessage(ChatColor.DARK_RED + "Failed to check for " + DungeonMaze.PLUGIN_NAME + " updates!");
        return true;
    }
}
