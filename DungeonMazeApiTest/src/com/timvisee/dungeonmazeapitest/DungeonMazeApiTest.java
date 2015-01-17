package com.timvisee.dungeonmazeapitest;

import com.timvisee.dungeonmaze.api.DungeonMazeApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class DungeonMazeApiTest extends JavaPlugin {

    /** The Dungeon Maze API instance. */
    private DungeonMazeApi api;

    /**
     * On enable method, called when plugin is being enabled
     */
    public void onEnable() {
        // Show a status message
        getLogger().info("Starting Dungeon Maze API Test v" + getVersion() + "...");

        // Start the API
        startApi();

        // Show a startup message
        getLogger().info("Dungeon Maze API Test v" + getVersion() + " started!");
    }

    /**
     * On enable method, called when plugin is being disabled
     */
    public void onDisable() {
        // Show an disabling message
        getLogger().info("Disabling Dungeon Maze API Test...");

        // Stop the API
        stopApi();

        // Show an disabled message
        getLogger().info("Dungeon Maze API Test Disabled!");
    }

    /**
     * Start the Dungeon Maze API, and do some tests.
     */
    public void startApi() {
        // Set up and hook into the API
        this.api = new DungeonMazeApi(this);

        // Get the list of Dungeon Maze worlds through the API, and print the result in the console
        List<String> worlds = api.getWorldManager().getDungeonMazeWorlds();
        getLogger().info("Dungeon Maze worlds (through API):");
        for(String world : worlds)
            getLogger().info(" - " + world);

        // Stop the API
        stopApi();
    }

    /**
     * Stop the Dungeon Maze API.
     */
    public void stopApi() {
        // Force unhook the API
        this.api.unhook();
    }

    /**
     * Handle Bukkit commands.
     *
     * @param sender The command sender (Bukkit).
     * @param cmd The command (Bukkit).
     * @param commandLabel The command label (Bukkit).
     * @param args The command arguments (Bukkit).
     *
     * @return True if the command was executed, false otherwise.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        // Don't process any commands
        return false;
    }

    /**
     * Get the current installed Dungeon Maze version.
     *
     * @return The version number of the currently installed Dungeon Maze instance.
     */
    public String getVersion() {
        return getDescription().getVersion();
    }
}
