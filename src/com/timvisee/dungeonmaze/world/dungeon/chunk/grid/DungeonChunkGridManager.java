package com.timvisee.dungeonmaze.world.dungeon.chunk.grid;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DungeonChunkGridManager {

    /** Defines whether the manager is instantiated. */
    boolean init = false;

    /** The list of loaded chunk grids. */
    private List<DungeonChunkGrid> grids = new ArrayList<DungeonChunkGrid>();

    /** Defines the Dungeon Maze data directory name. */
    private static final String DUNGEON_MAZE_DATA_DIRECTORY = "DungeonMaze";
    /** Defines the chunk data directory name. */
    private static final String CHUNK_DATA_DIRECTORY = "chunks";

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

        // Get the world manager
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager != null)
            // Load the chunk grids for all the loaded Dungeon Maze worlds
            loadChunkGridsForWorldNames(worldManager.getLoadedDungeonMazeWorlds());

        // Initialize the manager, return the result
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

        // Unload all chunk grids
        if(!unloadAllChunkGrids())
            return false;

        // Clear the grids list
        this.grids.clear();

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

        // The world doesn't seem to be an existing Dungeon Maze world, return null
        return null;
    }

    /**
     * Load the dungeon chunk grid data that isn't loaded for a world.
     *
     * @param world The world to load the dungeon chunk grid data for.
     *
     * @return The dungeon chunk grid, or null on failure.
     */
    public DungeonChunkGrid loadChunkGrid(World world) {
        // Make sure the dungeon chunk grid isn't loaded already
        DungeonChunkGrid dungeonChunkGrid = getLoadedChunkGrid(world);
        if(dungeonChunkGrid != null)
            return dungeonChunkGrid;

        // Get the world manager and make sure it's valid
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager == null)
            return null;

        // Make sure the world exists
        if(!worldManager.isWorld(world.getName()))
            return null;

        // Make sure the world is a Dungeon Maze world
        if(!worldManager.isDungeonMazeWorld(world.getName()))
            return null;

        // Make sure this world has any chunk grid data available, if not create it
        createChunkGridData(world);

        // Load the chunk grid, add it to the list and return the result
        dungeonChunkGrid = new DungeonChunkGrid(world);
        this.grids.add(dungeonChunkGrid);
        return dungeonChunkGrid;
    }

    /**
     * Load the dungeon chunk grids for a list of worlds.
     *
     * @param worlds The list of worlds to load the chunk grids for.
     *
     * @return The list of loaded chunk grids. The list may contain null elements for worlds the chunk grid couldn't
     * be loaded for.
     */
    public List<DungeonChunkGrid> loadChunkGrids(List<World> worlds) {
        // Create a list of chunk grids
        List<DungeonChunkGrid> grids = new ArrayList<DungeonChunkGrid>();

        // Load the chunk grid for each world
        for(World world : worlds) {
            // Make sure the item isn't null
            if(world == null)
                grids.add(null);
            else
                grids.add(loadChunkGrid(world));
        }

        // Return the list of chunk grids
        return grids;
    }

    /**
     * Load the dungeon chunk grids for a list of worlds.
     *
     * @param worldNames The list of world names to load the chunk grids for. The worlds must be loaded.
     *
     * @return The list of loaded chunk grids. The list may contain null elements for worlds the chunk grid couldn't be
     * loaded for.
     */
    public List<DungeonChunkGrid> loadChunkGridsForWorldNames(List<String> worldNames) {
        // Get the world manager, and make sure it's valid
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager == null)
            return null;

        // Create a list of worlds
        List<World> worlds = new ArrayList<World>();

        // Fill the worlds list with world instances
        for(String worldName : worldNames)
            worlds.add(Bukkit.getWorld(worldName));

        // Load the chunk grids for the worlds, return the result
        return loadChunkGrids(worlds);
    }

    /**
     * Unload the chunk grid for a specific world.
     *
     * @param world The world to unload the chunk grid for. Or null to unload the chunk grids for all the worlds.
     *
     * @return True if any chunk grid was unloaded, false otherwise. If there wasn't any loaded chunk grid for the
     * specified world, false will be returned.
     */
    public boolean unloadChunkGrid(World world) {
        // Set whether any chunk grid was unloaded
        boolean unloaded = false;

        // Show a status message
        if(world != null)
            Core.getLogger().info("Unloading all dungeon chunks for '" + world.getName() + "'...");
        else
            Core.getLogger().info("Unloading all dungeon chunks for all worlds...");

        // Loop through each loaded chunk grid to see if it matches the world
        for(int i = this.grids.size() - 1; i >= 0; i--) {
            // Get the current grid
            DungeonChunkGrid grid = this.grids.get(i);

            // Make sure the world matches
            if(world != null)
                if(!grid.getWorld().equals(world))
                    continue;

            // Save all the dungeon chunks in the grid
            grid.saveLoadedChunks();

            // Remove the grid from the list
            this.grids.remove(i);
            unloaded = true;
        }

        // Return true if any chunk grid was unloaded
        return unloaded;
    }

    /**
     * Unload all the loaded chunk grids.
     *
     * @return True if all chunk grids are unloaded.
     */
    public boolean unloadAllChunkGrids() {
        // Unload all chunk grids
        unloadChunkGrid(null);

        // Check whether there's any chunk grid left
        return this.grids.size() == 0;
    }

    /**
     * Create chunk grid data for a world.
     *
     * @param world The world to create the data for.
     *
     * @return True if any data is created, false otherwise. True will also be returned if the chunk grid data was
     * already available for this world.
     */
    public boolean createChunkGridData(World world) {
        // Make sure the world has no chunk grid data
        if(hasChunkGridData(world))
            return true;

        // Get the chunk grid data folder
        File chunkGridDataFolder = getChunkGridDataDirectory(world);

        // Create the chunk grid data for a world, return the result
        return chunkGridDataFolder.mkdirs();
    }

    /**
     * Check whether a world has any chunk grid data.
     *
     * @param world The world to check for.
     *
     * @return True if the world has any chunk grid data, false otherwise. False will also be returned on error.
     */
    public boolean hasChunkGridData(World world) {
        // Get the world manager and make sure it's valid
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager == null)
            return false;

        // Make sure the world exists
        if(!worldManager.isWorld(world.getName()))
            return false;

        // Get the chunk grid data directory for this world
        File chunkGridDataFolder = getChunkGridDataDirectory(world);

        // Check whether the directory exists
        return chunkGridDataFolder.isDirectory();
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

    /**
     * Get the chunk grid data directory for a specific world.
     *
     * @param world The world to get the data directory for.
     *
     * @return The chunk grid data directory for the world.
     */
    public static File getChunkGridDataDirectory(World world) {
        return new File(world.getWorldFolder(), DUNGEON_MAZE_DATA_DIRECTORY + "/" + CHUNK_DATA_DIRECTORY);
    }
}
