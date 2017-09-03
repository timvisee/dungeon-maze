package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.CommandParts;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import com.timvisee.dungeonmaze.config.ConfigHandler;
import com.timvisee.dungeonmaze.permission.PermissionsManager;
import com.timvisee.dungeonmaze.update.UpdateChecker;
import com.timvisee.dungeonmaze.util.MinecraftUtils;
import com.timvisee.dungeonmaze.util.SystemUtils;
import com.timvisee.dungeonmaze.world.WorldManager;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonRegionGridManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
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
        sender.sendMessage(ChatColor.GOLD + "==========[ " + DungeonMaze.getPluginName().toUpperCase() + " STATUS ]==========");

        // Get the world manager
        WorldManager worldManager = Core.getWorldManager();

        // Print the number of Dungeon Maze worlds
        if(worldManager != null)
            sender.sendMessage(ChatColor.GOLD + DungeonMaze.getPluginName() + " worlds: " + ChatColor.WHITE + worldManager.getDungeonMazeWorlds().size());
        else
            sender.sendMessage(ChatColor.GOLD + DungeonMaze.getPluginName() + " worlds: " + ChatColor.DARK_RED + ChatColor.ITALIC + "Unknown!");

        // Print the Dungeon Maze player count
        int playerCount = Bukkit.getOnlinePlayers().size();
        int dungeonMazePlayerCount = 0;
        if(worldManager != null) {
            for(Player player : Bukkit.getOnlinePlayers())
                if(worldManager.isDungeonMazeWorld(player.getWorld().getName()))
                    dungeonMazePlayerCount++;

            sender.sendMessage(ChatColor.GOLD + DungeonMaze.getPluginName() + " players: " + ChatColor.WHITE + dungeonMazePlayerCount + ChatColor.GRAY + " / " + playerCount);

        } else
            sender.sendMessage(ChatColor.GOLD + DungeonMaze.getPluginName() + " players: " + ChatColor.DARK_RED + ChatColor.ITALIC + "Unknown!");

        // Get the permissions manager
        PermissionsManager permissionsManager = Core.getPermissionsManager();

        // Print the permissions manager status
        if(permissionsManager != null) {
            // Get the used permissions system
            PermissionsManager.PermissionsSystemType type = permissionsManager.getUsedPermissionsSystemType();

            if(type != null)
                sender.sendMessage(ChatColor.GOLD + "Permissions System: " + ChatColor.GREEN + permissionsManager.getUsedPermissionsSystemType().getName());
            else
                sender.sendMessage(ChatColor.GOLD + "Permissions System: " + ChatColor.GRAY + ChatColor.ITALIC + "None");
        } else
            sender.sendMessage(ChatColor.GOLD + "Permissions System: " + ChatColor.DARK_RED + ChatColor.ITALIC + "Unknown!");

        // Get the dungeon chunk grid manager
        // TODO: Show proper information, show loaded chunks too?
        DungeonRegionGridManager dungeonRegionGridManager = Core.getDungeonRegionGridManager();
        if(dungeonRegionGridManager != null) {
            int loadedChunks = dungeonRegionGridManager.getLoadedRegionCount();
            int loadedGrids = dungeonRegionGridManager.getLoadedGridCount();
            sender.sendMessage(ChatColor.GOLD + "Loaded Dungeon Regions: " + ChatColor.WHITE + loadedChunks + ChatColor.GRAY + " in " + ChatColor.WHITE + loadedGrids + ChatColor.GRAY + " grid" + (loadedGrids != 1 ? "s" : ""));
        } else
            sender.sendMessage(ChatColor.GOLD + "Loaded Dungeon Regions: " + ChatColor.DARK_RED + ChatColor.ITALIC + "Unknown!");

        // Print the service count
        sender.sendMessage(ChatColor.GOLD + "Running Services: " + ChatColor.WHITE + Core.instance.getServiceManager().getServiceCount(true) + ChatColor.GRAY + " / " + Core.instance.getServiceManager().getServiceCount());

        // Print the plugin runtime
        printPluginRuntime(sender);

        // Show the version status
        sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.WHITE + "Dungeon Maze v" + DungeonMaze.getVersionName() + ChatColor.GRAY + " (code: " + DungeonMaze.getVersionCode() + ")");

        printUpdaterStatus(sender);
        printServerStatus(sender);
        printMachineStatus(sender);
        return true;
    }

    /**
     * Print the updater status.
     *
     * @param sender Command sender to print the output to.
     */
    private void printUpdaterStatus(CommandSender sender) {
        // Get the Dungeon Maze config
        ConfigHandler configHandler = DungeonMaze.instance.getCore()._getConfigHandler();
        FileConfiguration config = configHandler.config;

        // Determine the sub description
        String updaterSub = "";
        if(config != null) {
            if(config.getBoolean("updateChecker.enabled", true))
                updaterSub = ChatColor.GRAY + " (Automatic updates enabled)";
            else
                updaterSub = ChatColor.DARK_RED + " (Automatic updates disabled in config)";
        }

        // Show the update checker status
        sender.sendMessage(ChatColor.GOLD + "Updater: " + ChatColor.WHITE + Core.getUpdateChecker().getType().getName() + updaterSub);

        // Get the update checker
        final UpdateChecker updateChecker = Core.getUpdateChecker();

        // No new version found
        if(!updateChecker.isUpdateAvailable()) {
            sender.sendMessage(ChatColor.GOLD + "Update status: " + ChatColor.WHITE + "Up to date");
            return;
        }

        // Make sure the new version is compatible
        if(!updateChecker.isUpdateCompatible()) {
            sender.sendMessage(ChatColor.GOLD + "Update status: " + ChatColor.DARK_RED + "Update available, but not compatible");
            sender.sendMessage(ChatColor.GRAY + " - Update: " + ChatColor.DARK_RED + DungeonMaze.getVersionName() + " (" + DungeonMaze.getVersionCode() + ") " +
                    ChatColor.GRAY + ChatColor.STRIKETHROUGH + "-->" + ChatColor.GRAY +
                    " " + updateChecker.getUpdateVersionName() + " (" + updateChecker.getUpdateVersionCode() + ")");

            // Show the Minecraft version
            if(!updateChecker.isUpdateMinecraftCompatible())
                sender.sendMessage(ChatColor.GRAY + " - Required Minecraft version " + ChatColor.DARK_RED + MinecraftUtils.getMinecraftVersion() + " " +
                        ChatColor.GRAY + ChatColor.STRIKETHROUGH + " -->" +
                        ChatColor.GRAY + " " + updateChecker.getUpdateMinecraftVersion());

            // Show the Java version
            if(!updateChecker.isUpdateJavaCompatible())
                sender.sendMessage(ChatColor.GRAY + " - Required Java version: " + ChatColor.DARK_RED + SystemUtils.getJavaVersion() + " " +
                        ChatColor.GRAY + ChatColor.STRIKETHROUGH + " -->" +
                        ChatColor.GRAY + " " + updateChecker.getUpdateJavaVersion());

            return;
        }

        // Check whether the update has already been installed
        if(updateChecker.isUpdateInstalled()) {
            sender.sendMessage(ChatColor.GOLD + "Update status: " + ChatColor.GREEN + "Installed, restart required");
            sender.sendMessage(ChatColor.GRAY + " - Update: " + ChatColor.GRAY + DungeonMaze.getVersionName() + " (" + DungeonMaze.getVersionCode() + ") " +
                    ChatColor.GRAY + ChatColor.STRIKETHROUGH + "-->" + ChatColor.GREEN +
                    " " + updateChecker.getUpdateVersionName() + " (" + updateChecker.getUpdateVersionCode() + ")");
            return;
        }

        // Check whether the update has already been downloaded
        if(updateChecker.isUpdateDownloaded()) {
            sender.sendMessage(ChatColor.GOLD + "Update status: " + ChatColor.DARK_RED + "Downloaded, but not installed");
            sender.sendMessage(ChatColor.GRAY + " - Update: " + ChatColor.DARK_RED + DungeonMaze.getVersionName() + " (" + DungeonMaze.getVersionCode() + ") " +
                    ChatColor.GRAY + ChatColor.STRIKETHROUGH + "-->" + ChatColor.GRAY +
                    " " + updateChecker.getUpdateVersionName() + " (" + updateChecker.getUpdateVersionCode() + ")");
            return;
        }

        // Check whether an update is available
        if(updateChecker.isUpdateAvailable()) {
            sender.sendMessage(ChatColor.GOLD + "Update status: " + ChatColor.DARK_RED + "Available, but not installed");
            sender.sendMessage(ChatColor.GRAY + " - Update: " + ChatColor.DARK_RED + DungeonMaze.getVersionName() + " (" + DungeonMaze.getVersionCode() + ") " +
                    ChatColor.GRAY + ChatColor.STRIKETHROUGH + "-->" + ChatColor.GRAY +
                    " " + updateChecker.getUpdateVersionName() + " (" + updateChecker.getUpdateVersionCode() + ")");
        }
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

    /**
     * Print the server status.
     *
     * @param sender The command sender to print the status to.
     */
    public void printServerStatus(CommandSender sender) {
        // Print the header
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Server Status:");

        // Print the server status
        sender.sendMessage(ChatColor.GOLD + "Detected Minecraft Version: " + ChatColor.WHITE + MinecraftUtils.getMinecraftVersion());
        sender.sendMessage(ChatColor.GOLD + "Detected Minecraft Server: " + ChatColor.WHITE + MinecraftUtils.getServerType().getName());
        sender.sendMessage(ChatColor.GOLD + "Server Version: " + ChatColor.WHITE + Bukkit.getVersion());
        sender.sendMessage(ChatColor.GOLD + "Bukkit Version: " + ChatColor.WHITE + Bukkit.getBukkitVersion());
        sender.sendMessage(ChatColor.GOLD + "Running Plugins: " + ChatColor.WHITE + Bukkit.getPluginManager().getPlugins().length);

        // Get the world manager
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager != null)
            sender.sendMessage(ChatColor.GOLD + "Loaded Worlds: " + ChatColor.WHITE + Bukkit.getWorlds().size() + ChatColor.GRAY + " / " + worldManager.getWorlds().size());

        // Print the server time
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sender.sendMessage(ChatColor.GOLD + "Server Time: " + ChatColor.WHITE + dateFormat.format(new Date()));
    }

    /**
     * Print the machine status.
     *
     * @param sender The command sender to print the status to.
     */
    public void printMachineStatus(CommandSender sender) {
        // Print the header
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Machine Status:");

        // Return the machine status
        sender.sendMessage(ChatColor.GOLD + "OS Name: " + ChatColor.WHITE + System.getProperty("os.name"));
        sender.sendMessage(ChatColor.GOLD + "OS Architecture: " + ChatColor.WHITE + SystemUtils.getSystemArchNumber() + "-bit" + ChatColor.GRAY + " (" + SystemUtils.getSystemArchFull() + ")");
        sender.sendMessage(ChatColor.GOLD + "OS Version: " + ChatColor.WHITE + System.getProperty("os.version"));
        sender.sendMessage(ChatColor.GOLD + "Java Version: " + ChatColor.WHITE + SystemUtils.getJavaVersion() + ChatColor.GRAY + " (" + SystemUtils.getJavaArchValue() + "-bit)");
    }
}
