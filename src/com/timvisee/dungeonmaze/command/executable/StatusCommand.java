package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.CommandParts;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import com.timvisee.dungeonmaze.permission.PermissionsManager;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatusCommand extends ExecutableCommand {

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
        // Print the status info header
        sender.sendMessage(ChatColor.GOLD + "==========[ DUNGEON MAZE STATUS ]==========");

        // Get the world manager
        WorldManager worldManager = Core.getWorldManager();

        // Print the number of Dungeon Maze worlds
        if(worldManager != null)
            sender.sendMessage(ChatColor.GOLD + "Dungeon Maze worlds: " + ChatColor.WHITE + worldManager.getDungeonMazeWorlds().size());
        else
            sender.sendMessage(ChatColor.GOLD + "Dungeon Maze worlds: " + ChatColor.DARK_RED + ChatColor.ITALIC + "Unknown!");

        // Print the Dungeon Maze player count
        int playerCount = Bukkit.getOnlinePlayers().size();
        int dungeonMazePlayerCount = 0;
        if(worldManager != null) {
            for(Player player : Bukkit.getOnlinePlayers())
                if(worldManager.isDungeonMazeWorld(player.getWorld().getName()))
                    dungeonMazePlayerCount++;

            sender.sendMessage(ChatColor.GOLD + "Dungeon Maze players: " + ChatColor.WHITE + dungeonMazePlayerCount + ChatColor.GRAY + " / " + playerCount);

        } else
            sender.sendMessage(ChatColor.GOLD + "Dungeon Maze players: " + ChatColor.DARK_RED + ChatColor.ITALIC + "Unknown!");

        // Get the permissions manager
        PermissionsManager permissionsManager = Core.getPermissionsManager();

        // Print the permissions manager status
        if(permissionsManager != null) {
            // Get the used permissions system
            PermissionsManager.PermissionsSystemType type = permissionsManager.getUsedPermissionsSystemType();

            if(!type.equals(PermissionsManager.PermissionsSystemType.NONE))
                sender.sendMessage(ChatColor.GOLD + "Permissions System: " + ChatColor.GREEN + permissionsManager.getUsedPermissionsSystemType().getName());
            else
                sender.sendMessage(ChatColor.GOLD + "Permissions System: " + ChatColor.GRAY + ChatColor.ITALIC + permissionsManager.getUsedPermissionsSystemType().getName());
        } else
            sender.sendMessage(ChatColor.GOLD + "Permissions System: " + ChatColor.DARK_RED + ChatColor.ITALIC + "Unknown!");

        // Print the plugin runtime
        printPluginRuntime(sender);

        // Show the version status
        sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.WHITE + "Dungeon Maze v" + DungeonMaze.getVersionName() + ChatColor.GRAY + " (code: " + DungeonMaze.getVersionCode() + ")");

        // Print the server status header
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Server Status:");

        // Print the server status
        sender.sendMessage(ChatColor.GOLD + "Server Version: " + ChatColor.WHITE + Bukkit.getVersion());
        sender.sendMessage(ChatColor.GOLD + "Bukkit Version: " + ChatColor.WHITE + Bukkit.getBukkitVersion());
        sender.sendMessage(ChatColor.GOLD + "Running Plugins: " + ChatColor.WHITE + String.valueOf(Bukkit.getPluginManager().getPlugins().length));

        // Print the server time
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sender.sendMessage(ChatColor.GOLD + "Server Time: " + ChatColor.WHITE + dateFormat.format(new Date()));

        // Print the machine status header
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Machine Status:");

        // Return server/java info
        sender.sendMessage(ChatColor.GOLD + "OS Name: " + ChatColor.WHITE + System.getProperty("os.name"));
        sender.sendMessage(ChatColor.GOLD + "OS Version: " + ChatColor.WHITE + System.getProperty("os.version"));
        sender.sendMessage(ChatColor.GOLD + "Java Version: " + ChatColor.WHITE + System.getProperty("java.version"));
        return true;
    }

    /**
     * Print the plugin runtime.
     *
     * @param sender Command sender to print the runtime to.
     */
    public void printPluginRuntime(CommandSender sender) {
        // Get the runtime
        long runtime = new Date().getTime() - Core.getInitializationTime().getTime();

        // Calculate the timings
        int millis = (int) (runtime % 1000);
        runtime/=1000;
        int seconds = (int) (runtime % 60);
        runtime/=60;
        int minutes = (int) (runtime % 60);
        runtime/=60;
        int hours = (int) runtime;

        // Create a double and triple digit formatter
        DecimalFormat doubleDigit = new DecimalFormat("######00");
        DecimalFormat tripleDigit = new DecimalFormat("000");

        // Generate the timing string
        StringBuilder runtimeStr = new StringBuilder(ChatColor.WHITE + doubleDigit.format(seconds) + ChatColor.GRAY + "." + ChatColor.WHITE + tripleDigit.format(millis));
        String measurement = "Seconds";
        if(minutes > 0 || hours > 0) {
            runtimeStr.insert(0, ChatColor.WHITE + doubleDigit.format(minutes) + ChatColor.GRAY + ":");
            measurement = "Minutes";
            if(hours > 0) {
                runtimeStr.insert(0, ChatColor.WHITE + doubleDigit.format(hours) + ChatColor.GRAY + ":");
                measurement = "Hours";
            }
        }

        // Print the runtime
        sender.sendMessage(ChatColor.GOLD + "Runtime: " + ChatColor.WHITE + runtimeStr + " " + ChatColor.GRAY + measurement);
    }
}
