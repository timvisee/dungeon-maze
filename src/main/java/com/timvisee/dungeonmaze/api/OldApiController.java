package com.timvisee.dungeonmaze.api;

import com.timvisee.dungeonmaze.DungeonMaze;

@SuppressWarnings("deprecation")
public class OldApiController {

    /** Old Dungeon Maze api instance. */
    private DungeonMazeApiOld api;

    /**
     * Constructor.
     *
     * @param init True to immediately initialize, false otherwise.
     */
    public OldApiController(boolean init) {
        // Initialize
        if(init)
            init();
    }

    /**
     * Initialize.
     *
     * @return True on success, false on failure.
     */
    public boolean init() {
        // Make sure the controller isn't initialized already
        if(isInit())
            return true;

        // Instantiate the dungeon maze API
        this.api = new DungeonMazeApiOld(DungeonMaze.instance);
        return isInit();
    }

    /**
     * Check whether the controller is initialized.
     *
     * @return True if the controller is initialized, false if not.
     */
    public boolean isInit() {
        return this.api != null;
    }

    /**
     * Destroy the controller.
     *
     * @param force True to force the destruction, even if the controller doesn't seem to be initialized.
     */
    public void destroy(boolean force) {
        // Make sure the controller is initialized or the destruction must be forced
        if(!isInit() && !force)
            return;

        // Destroy the API instance, return the result
        this.api = null;
    }

    /**
     * Get the dungeon maze API.
     *
     * @return Dungeon maze API instance.
     */
    public DungeonMazeApiOld getApi() {
        return this.api;
    }
}
