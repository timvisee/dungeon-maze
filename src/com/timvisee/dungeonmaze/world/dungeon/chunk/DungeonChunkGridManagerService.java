package com.timvisee.dungeonmaze.world.dungeon.chunk;

import com.timvisee.dungeonmaze.service.Service;

public class DungeonChunkGridManagerService extends Service {

    /** Service name. */
    private static final String SERVICE_NAME = "Dungeon Chunk Grid Manager";

    /** Dungeon chunk grid manager instance. */
    private DungeonChunkGridManager dungeonChunkGridManager;

    /**
     * Initialize the service.
     *
     * @return True on success, false on failure. True will also be returned if the service was initialized already.
     */
    @Override
    public boolean init() {
        // Construct the dungeon chunk grid manager
        this.dungeonChunkGridManager = new DungeonChunkGridManager();

        // Initialize the dungeon chunk grid manager, return the result
        return this.dungeonChunkGridManager.init();
    }

    /**
     * Check whether the service is initialized.
     *
     * @return True if the service is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        // Make sure the dungeon chunk grid manager is set
        if(this.dungeonChunkGridManager == null)
            return false;

        // Check whether the dungeon chunk grid manager is initialized
        return this.dungeonChunkGridManager.isInit();
    }

    /**
     * Destroy the service. The destruction won't be forced.
     *
     * @param force True to force the destruction. This wil re-destroy the service even if it isn't initialized.
     *              This will also force the initialization state to be set to false even if an error occurred while
     *              destroying.
     *
     * @return True on success, false on failure. True will also be returned if the service wasn't initialized. False
     * might be returned if force is set to true, even though the initialization state is set to false.
     */
    @Override
    public boolean destroy(boolean force) {
        // Make sure the dungeon chunk grid manager is initialized
        if(!this.isInit() && !force)
            return true;

        // Destroy the dungeon chunk grid manager
        if(this.dungeonChunkGridManager != null) {
            if(!this.dungeonChunkGridManager.destroy()) {
                if(force)
                    this.dungeonChunkGridManager = null;
                return false;
            }
        }

        // Return the result
        this.dungeonChunkGridManager = null;
        return true;
    }

    /**
     * Get the name of the service.
     *
     * @return Service name.
     */
    @Override
    public String getName() {
        return SERVICE_NAME;
    }

    /**
     * Get the dungeon chunk grid manager.
     *
     * @return Dungeon chunk grid manager instance.
     */
    public DungeonChunkGridManager getDungeonChunkGridManager() {
        return this.dungeonChunkGridManager;
    }
}
