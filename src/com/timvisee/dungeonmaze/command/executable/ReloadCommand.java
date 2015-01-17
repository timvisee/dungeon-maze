package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.command.CommandArguments;
import com.timvisee.dungeonmaze.command.CommandReference;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import com.timvisee.dungeonmaze.permission.PermissionsManager;
import com.timvisee.dungeonmaze.util.Profiler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends ExecutableCommand {

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
    public boolean executeCommand(CommandSender sender, CommandReference commandReference, CommandArguments commandArguments) {
        // Profile the reload process
        Profiler p = new Profiler(true);

        // Show a reload warning
        sender.sendMessage(ChatColor.YELLOW + "Reloading Dungeon Maze...");
        Core.getLogger().info("Reloading Dungeon Maze...");

        // Get the permissions manager and make sure it's valid
        PermissionsManager permissionsManager = Core.getPermissionsManager();
        if(permissionsManager == null) {
            Core.getLogger().info("Failed to access the permissions manager after " + p.getTimeFormatted() + "!");
            sender.sendMessage(ChatColor.DARK_RED + "Failed to access the permissions manager after " + p.getTimeFormatted() + "!");
            return true;
        }

        // Reload the permissions module, show an error on failure
        if(!permissionsManager.reload()) {
            Core.getLogger().info("Failed to reload permissions after " + p.getTimeFormatted() + "!");
            sender.sendMessage(ChatColor.DARK_RED + "Failed to reload permissions after " + p.getTimeFormatted() + "!");
            return true;
        }

        // Reload configs and worlds
        Core.getConfigHandler().load();
        Core.getWorldManager();
        Core.getWorldManager().preloadDungeonMazeWorlds();

        // Show a success message
        Core.getLogger().info("Dungeon Maze has been reloaded successfully, took " + p.getTimeFormatted() + "!");
        sender.sendMessage(ChatColor.GREEN + "Dungeon Maze has been reloaded successfully, took " + p.getTimeFormatted() + "!");
        return true;
    }
}
