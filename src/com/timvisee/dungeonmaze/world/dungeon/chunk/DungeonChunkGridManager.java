package com.timvisee.dungeonmaze.world.dungeon.chunk;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class DungeonChunkGridManager {

    /** Defines whether the manager is instantiated. */
    boolean init = false;

    /** The list of loaded chunk grids. */
    private List<DungeonChunkGrid> grids = new ArrayList<DungeonChunkGrid>();

    /**
     * Constructor.
     */
    public DungeonChunkGridManager() { }

    /**
     * Initialize the dungeon chunk grid manager.
     *
     * @return True if the initialization succeed, false otherwise. True will also be returned if the dungeon chunk grid
     * manager was already instantiated.
     */
    public boolean init() {
        // Make sure the manager isn't initialized already
        if(this.init)
            return true;

        // Initialize the manager, return the result
        // TODO: Initialization code here!
        this.init = true;
        return true;
    }

    /**
     * Check whether the dungeon chunk grid manager is instantiated.
     *
     * @return True if the manager is instantiated, false otherwise.
     */
    public boolean isInit() {
        return this.init;
    }

    /**
     * Destroy the dungeon chunk grid manager if it's instantiated.
     *
     * @return True if the dungeon chunk grid manager is destroyed, false otherwise. True will also be returned if the
     * manager wasn't instantiated.
     */
    public boolean destroy() {
        // Make sure the manager is instantiated
        if(!this.init)
            return true;

        // TODO: Unload all grids!
        // TODO: Destroy code here!

        // Set the initialization state, return the result
        this.init = false;
        return true;
    }

    /**
     * Get the list of loaded chunk grids.
     *
     * @return The list of loaded cunk grids.
     */
    public List<DungeonChunkGrid> getLoadedGrids() {
        return this.grids;
    }

    /**
     * Get the dungeon chunk grid for a specific world. This will automatically load the grid if it isn't loaded.
     *
     * @param world The world to get the dungeon chunk grid for.
     *
     * @return The dungeon chunk grid, or null if the grid couldn't be loaded.
     */
    public DungeonChunkGrid getChunkGrid(World world) {
        // Check whether the dungeon chunk grid for this world is already loaded, return it if that's the case
        DungeonChunkGrid dungeonChunkGrid = getLoadedChunkGrid(world);
        if(dungeonChunkGrid != null)
            return dungeonChunkGrid;

        // Load the dungeon chunk grid if it's available
        dungeonChunkGrid = loadChunkGrid(world);
        if(dungeonChunkGrid != null)
            return dungeonChunkGrid;

        // TODO: Should we create the dungeon chunk grid for this world?
        return null;
    }

    /**
     * Load the dungeon chunk grid data that isn't loaded for a world.
     *
     * @param world The world to load the dungeon chunk grid data for.
     *
     * @return The dungeon chunk grid, or null on failure.
     */
    private DungeonChunkGrid loadChunkGrid(World world) {
        // Make sure the dungeon chunk grid isn't loaded already
        DungeonChunkGrid dungeonChunkGrid = getLoadedChunkGrid(world);
        if(dungeonChunkGrid != null)
            return dungeonChunkGrid;

        // Get the world manager and make sure it's valid
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager == null)
            return null;

        // Make sure the world exists
        // TODO: Should we create the dungeon chunk grid if it doesn't exist? Create a separate method for this?
        if(!worldManager.isWorld(world.getName()))
            return null;

        // TODO: Load the data!
        return null;
    }

    /**
     * Get a loaded dungeon chunk grid for a specific world.
     *
     * @param world The world to get the dungeon chunk grid for.
     *
     * @return The dungeon chunk grid, or null if the chunk grid for the world isn't loaded.
     */
    public DungeonChunkGrid getLoadedChunkGrid(World world) {
        // Loop through all the loaded grids, check whether one matches the world
        for(DungeonChunkGrid grid : this.grids)
            if(grid.getWorld().equals(world))
                return grid;

        // No match found, return null
        return null;
    }

    /**
     * Check whether the dungeon chunk grid for a specific world is loaded.
     *
     * @param world The world to check for.
     *
     * @return True if the chunk grid for this world is loaded, false otherwise.
     */
    public boolean isLoadedChunkGrid(World world) {
        return getLoadedChunkGrid(world) != null;
    }
}
