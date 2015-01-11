package com.timvisee.dungeonmaze.world;

import com.timvisee.dungeonmaze.world.WorldManager;
import com.timvisee.dungeonmaze.module.Module;

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
        // Initialize and the world manager
        this.worldManager = new WorldManager();

        // Refresh the manager
        this.worldManager.refresh();

        // Pre-load the worlds
        this.worldManager.preloadWorlds();

        // TODO: Do some error checking!

        return true;
    }

    /**
     * Check whether the module is initialized.
     *
     * @return True if the module is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        return this.worldManager != null;
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
        // TODO: Unload the world manager
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
