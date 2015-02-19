package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.CommandParts;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import com.timvisee.dungeonmaze.permission.PermissionsManager;
import com.timvisee.dungeonmaze.util.Profiler;
import com.timvisee.dungeonmaze.world.WorldManager;
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
    public boolean executeCommand(CommandSender sender, CommandParts commandReference, CommandParts commandArguments) {
        // Profile the reload process
        Profiler p = new Profiler(true);

        // Set whether the reload is forced
        boolean force = false;

        // Get whether the reload should be forced from the command arguments
        if(commandArguments.getCount() >= 1) {
            String arg = commandArguments.get(0);

            // Check whether the argument equals 'force'
            if(arg.equalsIgnoreCase("force") || arg.equalsIgnoreCase("forced"))
                force = true;

            else if(arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("t") || arg.equalsIgnoreCase("yes") || arg.equalsIgnoreCase("y"))
                force = true;

            else if(arg.equalsIgnoreCase("false") || arg.equalsIgnoreCase("f") || arg.equalsIgnoreCase("no") || arg.equalsIgnoreCase("n"))
                force = false;

            else {
                sender.sendMessage(ChatColor.DARK_RED + arg);
                sender.sendMessage(ChatColor.DARK_RED + "Invalid argument!");
                return true;
            }
        }

        // Show a reload warning
        if(force) {
            sender.sendMessage(ChatColor.YELLOW + "Force reloading Dungeon Maze...");
            Core.getLogger().info("Force reloading Dungeon Maze...");
        } else {
            sender.sendMessage(ChatColor.YELLOW + "Reloading Dungeon Maze...");
            Core.getLogger().info("Reloading Dungeon Maze...");
        }

        // Profile the Dungeon Maze Core destruction
        Profiler stopCoreProfiler = new Profiler(true);

        // Show a status message
        sender.sendMessage(ChatColor.YELLOW + "Stopping the Dungeon Maze Core...");

        // Destroy the Dungeon Maze core
        if(!DungeonMaze.instance.destroyCore(force)) {
            // Failed to destroy the core, show a status message
            sender.sendMessage(ChatColor.DARK_RED + "Failed to stop the Dungeon Maze Core after " + stopCoreProfiler.getTimeFormatted() + "!");
            sender.sendMessage(ChatColor.DARK_RED + "Please use " + ChatColor.GOLD + "/reload" + ChatColor.DARK_RED + " for plugin instability reasons!");
            Core.getLogger().error("Failed to stop the core, after " + stopCoreProfiler.getTimeFormatted() + "!");

            // Return if the reload isn't force
            if(!force)
                return true;
        }

        // Show a status message
        sender.sendMessage(ChatColor.YELLOW + "Dungeon Maze Core stopped, took " + stopCoreProfiler.getTimeFormatted() + "!");
        sender.sendMessage(ChatColor.YELLOW + "Starting the Dungeon Maze Core...");

        // Profile the core starting
        Profiler startCoreProfiler = new Profiler(true);

        // Initialize the core, show the result status
        if(!DungeonMaze.instance.initCore()) {
            // Core failed to initialize, show a status message
            sender.sendMessage(ChatColor.DARK_RED + "Failed to start the Dungeon Maze Core after " + startCoreProfiler.getTimeFormatted() + "!");
            sender.sendMessage(ChatColor.DARK_RED + "Please use " + ChatColor.GOLD + "/reload" + ChatColor.DARK_RED + " for plugin instability reasons!");
            Core.getLogger().error("Failed to start the core, after " + startCoreProfiler.getTimeFormatted() + "!");

            // Return if the reload isn't forced
            if(!force)
                return true;
        }

        // Core initialized, show a status message, return the result
        Core.getLogger().info("Core started successfully, took " + p.getTimeFormatted() + "!");
        sender.sendMessage(ChatColor.YELLOW + "Dungeon Maze Core started, took " + startCoreProfiler.getTimeFormatted() + "!");
        sender.sendMessage(ChatColor.GREEN + "Dungeon Maze has been reloaded successfully, took " + p.getTimeFormatted() + "!");
        return true;
    }
}
