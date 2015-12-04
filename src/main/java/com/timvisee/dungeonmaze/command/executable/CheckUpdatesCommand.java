package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.CommandParts;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import com.timvisee.dungeonmaze.update.UpdateChecker;
import com.timvisee.dungeonmaze.update.UpdateCheckerService;
import com.timvisee.dungeonmaze.util.MinecraftUtils;
import com.timvisee.dungeonmaze.util.Profiler;
import com.timvisee.dungeonmaze.util.SystemUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CheckUpdatesCommand extends ExecutableCommand {

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

        // Show a status message
        // TODO: Keep this message?
        sender.sendMessage(ChatColor.YELLOW + "Update checking succeed, took " + p.getTimeFormatted() + "!");

        // TODO: Make sure some sort of update check has been done here!

        // Print the update checker header
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.GOLD + "==========[ " + DungeonMaze.getPluginName().toUpperCase() + " UPDATE CHECKER ]==========");

        // Failed to check for updates
        if(updateChecker.hasFailed()) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to check for " + DungeonMaze.getPluginName() + " updates!");
            sender.sendMessage(" ");
            return true;
        }

        // No new version found
        if(!updateChecker.isUpdateAvailable()) {
            sender.sendMessage(ChatColor.GREEN + DungeonMaze.getPluginName() + " is up to date!");
            sender.sendMessage(" ");
            return true;
        }

        // Make sure the new version is compatible
        if(!updateChecker.isUpdateCompatible()) {
            // Show the new Dungeon Maze version
            sender.sendMessage(ChatColor.GOLD + "A new " + DungeonMaze.getPluginName() + " version is available, but isn't compatible!");
            sender.sendMessage(ChatColor.GOLD + DungeonMaze.getPluginName() + " update: " + ChatColor.WHITE + DungeonMaze.getVersionName() + " (" + DungeonMaze.getVersionCode() + ") " +
                    ChatColor.GOLD + ChatColor.STRIKETHROUGH + "-->" + ChatColor.GREEN +
                    " " + updateChecker.getUpdateVersionName() + " (" + updateChecker.getUpdateVersionCode() + ")");

            // Show the Minecraft version
            if(!updateChecker.isUpdateMinecraftCompatible())
                sender.sendMessage(ChatColor.GOLD + "Required Minecraft version: " + ChatColor.DARK_RED + MinecraftUtils.getMinecraftVersion() + " " +
                        ChatColor.GOLD + ChatColor.STRIKETHROUGH + " -->" +
                        ChatColor.GREEN + " " + updateChecker.getUpdateMinecraftVersion());

            // Show the Java version
            if(!updateChecker.isUpdateJavaCompatible())
                sender.sendMessage(ChatColor.GOLD + "Required Java version: " + ChatColor.DARK_RED + SystemUtils.getJavaVersion() + " " +
                        ChatColor.GOLD + ChatColor.STRIKETHROUGH + " -->" +
                        ChatColor.GREEN + " " + updateChecker.getUpdateJavaVersion());

            sender.sendMessage(" ");
            return true;
        }

        // Check whether the update has already been installed
        if(updateChecker.isUpdateInstalled()) {
            sender.sendMessage(ChatColor.GREEN + "A new " + DungeonMaze.getPluginName() + " version is available, and has been installed!");
            sender.sendMessage(ChatColor.GREEN + "Reload or restart your server to apply this update.");
            sender.sendMessage(ChatColor.GOLD + DungeonMaze.getPluginName() + " update: " + ChatColor.WHITE + DungeonMaze.getVersionName() + " (" + DungeonMaze.getVersionCode() + ") " +
                    ChatColor.GOLD + ChatColor.STRIKETHROUGH + "-->" + ChatColor.GREEN +
                    " " + updateChecker.getUpdateVersionName() + " (" + updateChecker.getUpdateVersionCode() + ")");
            sender.sendMessage(" ");
            return true;
        }

        // Check whether the update has already been downloaded
        if(updateChecker.isUpdateDownloaded()) {
            sender.sendMessage(ChatColor.GREEN + "A new " + DungeonMaze.getPluginName() + " version is available, and has been downloaded!");
            sender.sendMessage(ChatColor.GREEN + "Use " + ChatColor.WHITE + "/dm installupdate" + ChatColor.GOLD + " to install the update.");
            sender.sendMessage(ChatColor.GOLD + DungeonMaze.getPluginName() + " update: " + ChatColor.WHITE + "" + DungeonMaze.getVersionName() + " (" + DungeonMaze.getVersionCode() + ") " +
                    ChatColor.GOLD + ChatColor.STRIKETHROUGH + "-->" + ChatColor.GREEN +
                    " " + updateChecker.getUpdateVersionName() + " (" + updateChecker.getUpdateVersionCode() + ")");
            sender.sendMessage(" ");
            return true;
        }

        // Check whether an update is available
        if(updateChecker.isUpdateAvailable()) {
            sender.sendMessage(ChatColor.GREEN + "A new " + DungeonMaze.getPluginName() + " version is available!");
            sender.sendMessage(ChatColor.GREEN + "Use " + ChatColor.WHITE + "/dm installupdate" + ChatColor.GOLD + " to download and install the update.");
            sender.sendMessage(ChatColor.GOLD + DungeonMaze.getPluginName() + " update: " + ChatColor.WHITE + DungeonMaze.getVersionName() + " (" + DungeonMaze.getVersionCode() + ") " +
                    ChatColor.GOLD + ChatColor.STRIKETHROUGH + "-->" + ChatColor.GREEN +
                    " " + updateChecker.getUpdateVersionName() + " (" + updateChecker.getUpdateVersionCode() + ")");
            sender.sendMessage(" ");
            return true;
        }

        // An error occurred, show a message
        sender.sendMessage(ChatColor.DARK_RED + "Failed to check for " + DungeonMaze.getPluginName() + " updates!");
        sender.sendMessage(" ");
        return true;
    }
}
