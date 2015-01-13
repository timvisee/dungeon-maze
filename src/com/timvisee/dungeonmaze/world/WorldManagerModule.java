package com.timvisee.dungeonmaze.world;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.module.Module;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class WorldManagerModule extends Module {

    /** Module name. */
    private static final String MODULE_NAME = "World Manager";

    /** World Manager instance. */
    private WorldManager worldManager;

    /**
     * Initialize the module.
     *
     * @return True on success, false on failure. True will also be returned if the module was initialized already.
     */
    @Override
    public boolean init() {
        // Set the world manager
        this.worldManager = new WorldManager(false);

        // Initialize the world manager, make sure to refresh and preload. Return false on failure
        if(!this.worldManager.init(true, true))
            return false;

        // Return the result
        return this.worldManager.isInit();
    }

    /**
     * Check whether the module is initialized.
     *
     * @return True if the module is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        if(this.worldManager == null)
            return false;

        // Check whether the world manager is initialized, return the result
        return this.worldManager.isInit();
    }

    /**
     * Destroy the module. The destruction won't be forced.
     *
     * @param force True to force the destruction. This wil re-destroy the module even if it isn't initialized.
     *              This will also force the initialization state to be set to false even if an error occurred while
     *              destroying.
     *
     * @return True on success, false on failure. True will also be returned if the module wasn't initialized. False
     * might be returned if force is set to true, even though the initialization state is set to false.
     */
    @Override
    public boolean destroy(boolean force) {
        // Make sure the world manager is initialized or the destruction must be forced
        if(!isInit() && !force)
            return true;

        // Get the config instance
        FileConfiguration c = Core.getConfigHandler().config;

        // Unload all Dungeon Maze worlds if set in the config file
        if(c.getBoolean("unloadWorldsOnPluginDisable", true)) {
            // Make sure the world count is greater than zero
            if(c.getStringList("worlds").size() > 0) {
                // Dungeon Maze does have some worlds
                Core.getLogger().info("[DungeonMaze] Unloading Dungeon Maze worlds...");

                // Unload the Dungeon Maze worlds
                List<String> worlds = new ArrayList<String>();
                for(World w : Bukkit.getWorlds())
                    if(c.getStringList("worlds").contains(w.getName()))
                        worlds.add(w.getName());

                // Unload each world
                for(String w : worlds)
                    Bukkit.unloadWorld(w, true);

                Core.getLogger().info("[DungeonMaze] All Dungeon Maze worlds have been unloaded!");
            } else
                Core.getLogger().info("[DungeonMaze] No Dungeon Maze worlds to unload avaiable");

        } else
            Core.getLogger().info("[DungeonMaze] Unloading worlds has been disabled!");

        // Destroy the world manager if it's set, return false on failure if the destruction isn't forced
        if(this.worldManager != null)
            if(this.worldManager.destroy(force))
                if(!force)
                    return false;

        // Unset the world manager instance, return the result
        this.worldManager = null;
        return true;
    }

    /**
     * Get the name of the module.
     *
     * @return Module name.
     */
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    /**
     * Get the world manager.
     * @return World manager instance.
     */
    public WorldManager getWorldManager() {
        return this.worldManager;
    }
}
