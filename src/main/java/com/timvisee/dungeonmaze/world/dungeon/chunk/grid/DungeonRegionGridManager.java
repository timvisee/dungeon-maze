package com.timvisee.dungeonmaze.world.dungeon.chunk.grid;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.util.Profiler;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DungeonRegionGridManager {

    /**
     * Defines whether the manager is instantiated.
     */
    boolean init = false;

    /**
     * The list of loaded region grids.
     */
    private List<DungeonRegionGrid> grids = new ArrayList<>();

    /**
     * Defines the Dungeon Maze data directory name.
     */
    public static final String DUNGEON_MAZE_DATA_DIRECTORY = "DungeonMaze";

    /**
     * Constructor.
     */
    public DungeonRegionGridManager() { }

    /**
     * Initialize the dungeon region grid manager.
     *
     * @return True if the initialization succeed, false otherwise. True will also be returned if the dungeon
     * region grid manager was already instantiated.
     */
    public boolean init() {
        // Make sure the manager isn't initialized already
        if(this.init)
            return true;

        // Get the world manager
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager != null)
            // Load the chunk grids for all the loaded Dungeon Maze worlds
            loadRegionGridsForWorldNames(worldManager.getLoadedDungeonMazeWorlds());

        // Initialize the manager, return the result
        this.init = true;
        return true;
    }

    /**
     * Check whether the dungeon region grid manager is instantiated.
     *
     * @return True if the manager is instantiated, false otherwise.
     */
    public boolean isInit() {
        return this.init;
    }

    /**
     * Destroy the dungeon region grid manager if it's instantiated.
     *
     * @return True if the dungeon region grid manager is destroyed, false otherwise. True will also be returned if the
     * manager wasn't instantiated.
     */
    public boolean destroy() {
        // Make sure the manager is instantiated
        if(!this.init)
            return true;

        // Unload all region grids
        if(!unloadAllRegionGrids())
            return false;

        // Clear the grids list
        this.grids.clear();

        // Set the initialization state, return the result
        this.init = false;
        return true;
    }

    /**
     * Get the list of loaded region grids. Regions that aren't loaded will not be returned.
     *
     * @return The list of loaded region grids.
     */
    public List<DungeonRegionGrid> getRegions() {
        return this.grids;
    }

    /**
     * Get the dungeon region grid for a specific world. This will automatically load the grid if it isn't loaded.
     *
     * @param world The world to get the dungeon region grid for.
     *
     * @return The dungeon region grid, or null if the grid couldn't be loaded.
     */
    public DungeonRegionGrid getOrCreateRegionGrid(World world) {
        // Check whether the dungeon region grid for this world is already loaded, return it if that's the case
        DungeonRegionGrid dungeonRegionGrid = getLoadedRegionGrid(world);
        if(dungeonRegionGrid != null)
            return dungeonRegionGrid;

        // Load the dungeon region grid if it's available
        dungeonRegionGrid = loadRegionGrid(world);
        if(dungeonRegionGrid != null)
            return dungeonRegionGrid;

        // Create the region grid, return the result
        return createRegionGridData(world);
    }

    /**
     * Load the dungeon region grid data that isn't loaded for a world.
     *
     * @param world The world to load the dungeon region grid data for.
     *
     * @return The dungeon region grid, or null on failure.
     */
    public DungeonRegionGrid loadRegionGrid(World world) {
        // Make sure the dungeon region grid isn't loaded already
        DungeonRegionGrid dungeonRegionGrid = getLoadedRegionGrid(world);
        if(dungeonRegionGrid != null)
            return dungeonRegionGrid;

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

        // Make sure this world has any region grid data available, if not create and return it
        return createRegionGridData(world);
    }

    /**
     * Load the dungeon region grids for a list of worlds.
     *
     * @param worlds The list of worlds to load the region grids for.
     *
     * @return The list of loaded region grids. The list may contain null elements for worlds the chunk grid couldn't
     * be loaded for.
     */
    public List<DungeonRegionGrid> loadRegionGrids(List<World> worlds) {
        // Create a list of region grids
        List<DungeonRegionGrid> grids = new ArrayList<>();

        // Load the chunk grid for each world
        for(World world : worlds) {
            // Make sure the item isn't null
            if(world == null)
                grids.add(null);
            else
                grids.add(loadRegionGrid(world));
        }

        // Return the list of region grids
        return grids;
    }

    /**
     * Load the dungeon region grids for a list of worlds.
     *
     * @param worldNames The list of world names to load the region grids for. The worlds must be loaded.
     *
     * @return The list of loaded region grids. The list may contain null elements for worlds the chunk grid couldn't be
     * loaded for.
     */
    public List<DungeonRegionGrid> loadRegionGridsForWorldNames(List<String> worldNames) {
        // Get the world manager, and make sure it's valid
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager == null)
            return null;

        // Create a list of worlds
        List<World> worlds = new ArrayList<>();

        // Fill the worlds list with world instances
        worlds.addAll(worldNames.stream().map(Bukkit::getWorld).collect(Collectors.toList()));

        // Load the region grids for the worlds, return the result
        return loadRegionGrids(worlds);
    }

    /**
     * Unload the region grid for a specific world.
     *
     * @param world The world to unload the region grid for. Or null to unload the region grids for all the worlds.
     *
     * @return The number of saved and unloaded regions.
     */
    public int unloadRegionGrid(World world) {
        return saveRegionGrid(world, true);
    }

    /**
     * Save the region grid with all regions for a specific world.
     *
     * @param world The world to save the region grid for. Or null to save the region grids for all the worlds.
     *
     * @return The number of saved regions.
     */
    public int saveRegionGrid(World world) {
        return saveRegionGrid(world, false);
    }

    /**
     * Save the region grid with all regions for a specific world.
     *
     * @param world The world to save the region grid for. Or null to save the region grids for all the worlds.
     * @param unload True to unload all saved regions, false to leave them in memory.
     *
     * @return The number of saved regions.
     */
    public int saveRegionGrid(World world, boolean unload) {
        // Store the number of unloaded and saved dungeon region
        int saved = 0;

        // Start a profiler for the unloading process
        Profiler p = new Profiler(true);

        // Show a status message
        if(world != null)
            Core.getLogger().info((unload ? "Unloading" : "Saving") + " dungeon regions for '" + world.getName() + "'...");
        else
            Core.getLogger().info((unload ? "Unloading" : "Saving") + " dungeon regions for all worlds...");

        // Loop through each loaded region grid to see if it matches the world
        for(int i = this.grids.size() - 1; i >= 0; i--) {
            // Get the current grid
            DungeonRegionGrid grid = this.grids.get(i);

            // Make sure the world matches
            if(world != null)
                if(!grid.getWorld().equals(world))
                    continue;

            // Save all the dungeon regions in the grid
            saved += grid.saveRegions();

            // Remove the grid from the list
            if(unload)
                this.grids.remove(i);
        }

        // Show a status message, return the number of saved regions
        Core.getLogger().info((unload ? "Unloaded" : "Saved") + " " + saved + " regions, took " + p.getTimeFormatted() + "!");
        return saved;
    }

    /**
     * Unload all the loaded region grids.
     *
     * @return True if all region grids are unloaded.
     */
    public boolean unloadAllRegionGrids() {
        // Unload all region grids
        unloadRegionGrid(null);

        // Check whether there's any region grid left, return the result
        return this.grids.size() == 0;
    }

    /**
     * Create region grid data for a world.
     *
     * @param world The world to create the data for.
     *
     * @return True if any data is created, false otherwise. True will also be returned if the region grid data was
     * already available for this world.
     */
    public DungeonRegionGrid createRegionGridData(World world) {
        // Make sure no region grid is loaded for this world
        if(isLoadedRegionGrid(world))
            return getLoadedRegionGrid(world);

        // Make sure the world has no region grid data
        if(!hasRegionGridData(world)) {
            // Get the region grid data folder
            File regionGridDataFolder = getRegionDataDirectory(world);

            // Create the region grid data for a world, return the result
            if(!regionGridDataFolder.mkdirs())
                return null;
        }

        DungeonRegionGrid dungeonRegionGrid = new DungeonRegionGrid(world);
        this.grids.add(dungeonRegionGrid);
        return dungeonRegionGrid;
    }

    /**
     * Check whether a world has any region grid data.
     *
     * @param world The world to check for.
     *
     * @return True if the world has any region grid data, false otherwise. False will also be returned on error.
     */
    public boolean hasRegionGridData(World world) {
        // Get the world manager and make sure it's valid
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager == null)
            return false;

        // Make sure the world exists
        if(!worldManager.isWorld(world.getName()))
            return false;

        // Get the region grid data directory for this world
        File regionGridDataFolder = getRegionDataDirectory(world);

        // Check whether the directory exists
        return regionGridDataFolder.isDirectory();
    }

    /**
     * Get a loaded dungeon region grid for a specific world.
     *
     * @param world The world to get the dungeon chunk grid for.
     *
     * @return The dungeon region grid, or null if the region grid for the world isn't loaded.
     */
    public DungeonRegionGrid getLoadedRegionGrid(World world) {
        // Loop through all the loaded grids, check whether one matches the world
        for(DungeonRegionGrid grid : this.grids)
            if(grid.getWorld().equals(world))
                return grid;

        // No match found, return null
        return null;
    }

    /**
     * Check whether the dungeon region grid for a specific world is loaded.
     *
     * @param world The world to check for.
     *
     * @return True if the region grid for this world is loaded, false otherwise.
     */
    public boolean isLoadedRegionGrid(World world) {
        return getLoadedRegionGrid(world) != null;
    }

    /**
     * Get the number of loaded regions.
     *
     * @return The number of loaded regions.
     */
    public int getLoadedGridCount() {
        return this.grids.size();
    }

    /**
     * Get the number of loaded region in all loaded grids.
     *
     * @return The number of loaded regions.
     */
    public int getLoadedRegionCount() {
        // Store the number of loaded regions
        int loaded = 0;

        // Loop through each grid and get the number of loaded regions
        for(DungeonRegionGrid grid : this.grids)
            loaded += grid.getLoadedRegionCount();

        // Return the number of loaded region grids
        return loaded;
    }

    /**
     * Get the region grid data directory for a specific world.
     *
     * @param world The world to get the data directory for.
     *
     * @return The region grid data directory for the world.
     */
    public static File getRegionDataDirectory(World world) {
        return new File(getWorldDataDirectory(world), DungeonRegionGrid.REGION_DATA_DIR);
    }

    /**
     * Get the Dungeon Maze data directory for the given world.
     *
     * @param world The world to get the data directory for.
     *
     * @return The Dungeon Maze data directory for the world.
     */
    public static File getWorldDataDirectory(World world) {
        return new File(world.getWorldFolder(), DUNGEON_MAZE_DATA_DIRECTORY);
    }
}
