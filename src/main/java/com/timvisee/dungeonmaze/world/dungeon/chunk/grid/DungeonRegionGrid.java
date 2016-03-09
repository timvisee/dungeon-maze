package com.timvisee.dungeonmaze.world.dungeon.chunk.grid;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonRegion;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DungeonRegionGrid {

    /**
     * Defines the name of the regions directory.
     */
    public static final String REGION_DATA_DIR = "region";

    // TODO: Update these limits!
    /**
     * Defines the preferred number of loaded dungeon regions.
     */
    private static final int REGION_LOADED_PREFERRED = 16;

    /**
     * Defines the maximum allowed number of loaded dungeon regions.
     */
    private static final int REGION_LOADED_MAX = 64;

    /**
     * Defines the world of the region grid.
     */
    private World world;

    /**
     * The list of loaded regions.
     */
    private List<DungeonRegion> regions = new ArrayList<>();

    /**
     * The last region that has been accessed.
     */
    private DungeonRegion lastRegionCache = null;

    /**
     * Constructor.
     *
     * @param world The world of the region grid.
     */
    public DungeonRegionGrid(World world) {
        this.world = world;
    }

    /**
     * Get the world of the region grid.
     *
     * @return The world of the region grid.
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Get the world name of the region grid.
     *
     * @return The world name of the region grid.
     */
    public String getWorldName() {
        return this.world.getName();
    }

    /**
     * Check whether this is the region grid for the given world.
     *
     * @param world The world.
     *
     * @return True if this is the grid for the given world, false if not.
     */
    public boolean isWorld(World world) {
        return this.world.equals(world);
    }

    /**
     * Get the list of loaded dungeon region. This is different than loaded Minecraft world regions and chunks.
     *
     * @return The list of loaded dungeon region.
     */
    @SuppressWarnings("UnusedDeclaration")
    public List<DungeonRegion> getLoadedRegions() {
        return this.regions;
    }

    /**
     * Get the number of loaded dungeon region. This is different than loaded Minecraft world regions and chunks.
     *
     * @return The number of loaded dungeon regions.
     */
    public int getLoadedRegionCount() {
        return this.regions.size();
    }

    /**
     * Get the directory all regions are stored in.
     *
     * @return Region directory.
     */
    public File getRegionDataDirectory() {
        return DungeonRegionGridManager.getRegionDataDirectory(this.world);
    }

    /**
     * Get the directory all regions are stored in.
     *
     * @return Region directory.
     */
    public File getRegionDataFile(int regionX, int regionY) {
        return new File(getRegionDataDirectory(), DungeonRegion.getRegionFileName(regionX, regionY));
    }

    /**
     * Get a specific dungeon region instance. The dungeon region data will be created if it doesn't exist yet.
     *
     * @param regionX The X coordinate of the region.
     * @param regionY The Y coordinate of the region.
     *
     * @return The dungeon region instance, or null on failure.
     */
    public DungeonRegion getOrCreateRegion(int regionX, int regionY) {
        // Check whether the last handled region is the correct one
        if(lastRegionCache != null && lastRegionCache.isAt(regionX, regionY))
            return lastRegionCache;

        // Loop through all the loaded region to see if it's loaded
        for(DungeonRegion region : this.regions) {
            if(region.isAt(regionX, regionY)) {
                // Set the last region
                lastRegionCache = region;

                // Return the region
                return region;
            }
        }

        // Create or load the region data, return the result
        return loadRegion(regionX, regionY);
    }

    /**
     * Get the specified dungeon chunk instance. The dungeon chunk will be retrieved from a dungeon region.
     * If the region isn't loaded, or the chunk hasn't been created yet, it will be create automatically.
     *
     * @param chunkX The X coordinate of the chunk in chunk space.
     * @param chunkY The Y coordinate of the chunk in chunk space.
     *
     * @return The dungeon chunk instance, or null on failure.
     */
    public DungeonChunk getOrCreateChunk(int chunkX, int chunkY) {
        // TODO: Cache this!

        // Get the coordinates of the region
        // TODO: Does this still work fine with big numbers?
        int regionX = (int) Math.floor((float) chunkX / (float) DungeonRegion.REGION_SIZE);
        int regionY = (int) Math.floor((float) chunkY / (float) DungeonRegion.REGION_SIZE);

        // Get the local coordinates of the chunk
        // TODO: Make sure this modulo works correctly for negative values!
        int localChunkX = Math.floorMod(chunkX, DungeonRegion.REGION_SIZE);
        int localChunkY = Math.floorMod(chunkY, DungeonRegion.REGION_SIZE);

        // Get or create the region
        DungeonRegion region = getOrCreateRegion(regionX, regionY);

        // Get or create the chunk, return it afterwards
        return region.getOrCreateChunk(localChunkX, localChunkY);
    }

    /**
     * Load a dungeon region. The region data will be created if it doesn't exist.
     *
     * @param regionX The X coordinate of the region.
     * @param regionY The Y coordinate of the region.
     *
     * @return The dungeon region instance, or null on failure.
     */
    // TODO: Force load, to prevent an infinite loop with isRegionLoaded->loadRegion->...
    public DungeonRegion loadRegion(int regionX, int regionY) {
        // Make sure the region isn't loaded yet
        if(isRegionLoaded(regionX, regionY))
            return getOrCreateRegion(regionX, regionY);

        // Create the region data if it doesn't have any data stored yet
        if(!hasRegionData(regionX, regionY))
            return createRegionData(regionX, regionY);

        // Load a dungeon region and make sure it's valid
        DungeonRegion dungeonRegion = DungeonRegion.load(this, regionX, regionY);
        if(dungeonRegion == null)
            return null;

        // Add the region to the list
        this.regions.add(dungeonRegion);

        // Unload the excess chunks
        // TODO: Make sure this is still working!
        unloadExcessRegions();

        // Return the new dungeon region
        return dungeonRegion;
    }

    /**
     * Create the region data for a specific region. The current region data will be returned if any data already exists
     * for this region.
     *
     * @param regionX The X coordinate of the region.
     * @param regionY The Y coordinate of the region.
     *
     * @return The dungeon region instance on success, or null on failure.
     */
    public DungeonRegion createRegionData(int regionX, int regionY) {
        // Make sure no data exists for this region
        if(hasRegionData(regionX, regionY))
            return getOrCreateRegion(regionX, regionY);

        // Create the region data for this region, save it and return the instance
        DungeonRegion dungeonRegion = new DungeonRegion(this.world, regionX, regionY);

        // Add the dungeon region to the list
        this.regions.add(dungeonRegion);

        // Unload the excess region
        // TODO: Make sure this is still working!
        unloadExcessRegions();

        // Return the new region
        return dungeonRegion;
    }

    /**
     * Check whether a region has any data stored.
     *
     * @param regionX The X coordinate of the region.
     * @param regionY The Y coordinate of the region.
     *
     * @return True if this region has any data stored, false otherwise.
     */
    public boolean hasRegionData(int regionX, int regionY) {
        // Get the data file of the region and check whether it has any data file
        return getRegionDataFile(regionX, regionY).exists();
    }

    /**
     * Check whether a specific region is loaded.
     *
     * @param regionX The X coordinate of the region.
     * @param regionY The Y coordinate of the region.
     *
     * @return True if the region is loaded, false otherwise.
     */
    public boolean isRegionLoaded(int regionX, int regionY) {
        // Loop through all the loaded region to see if it's loaded
        for(DungeonRegion region : this.regions)
            if(region.isAt(regionX, regionY))
                return true;

        // The region doesn't seem to be loaded, return false
        return false;
    }

    /**
     * Save all the loaded region in the current region grid.
     *
     * @return The number of saved regions in this region.
     */
    public int saveRegions() {
        // Count how many region are saved
        int saved = 0;

        // Loop through all region
        for(DungeonRegion region : this.regions)
            // Save the data, and add the number of saved chunks to the totals
            try {
                region.save(this);
                saved++;
            } catch(IOException e) {
                e.printStackTrace();
            }

        // Return the number of saved region
        return saved;
    }

    /**
     * Save a loaded dungeon region, specified by it's index.
     *
     * @param i The index of the loaded dungeon region.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean saveRegion(int i) throws IOException {
        return saveRegion(this.regions.get(i));
    }

    /**
     * Save a loaded dungeon region.
     *
     * @param dungeonRegion The loaded dungeon region to save.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean saveRegion(DungeonRegion dungeonRegion) throws IOException {
        // Get the region data file
        File regionDataFile = dungeonRegion.getRegionDataFile(this);

        // Save the data
        dungeonRegion.save(regionDataFile);

        // Return the result
        return true;
    }

    /**
     * Save and unload a dungeon region, specified by it's index.
     *
     * @param i The index of the loaded dungeon region.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean unloadRegion(int i) {
        // Reset the cached region
        lastRegionCache = null;

        // Save the region
        try {
            if(!saveRegion(i))
                return false;
        } catch(IOException e) {
            e.printStackTrace();
        }

        // Remove the region from the list
        this.regions.remove(i);
        return true;
    }

    /**
     * Save and unload a dungeon region.
     *
     * @param dungeonRegion The dungeon region to save and unload.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean unloadRegion(DungeonRegion dungeonRegion) {
        // Reset the region cache if that region is being unloaded
        if(lastRegionCache.equals(dungeonRegion))
            lastRegionCache = null;

        // Save the region
        try {
            if(!saveRegion(dungeonRegion))
                return false;
        } catch(IOException e) {
            e.printStackTrace();
        }

        // Remove the region from the list
        this.regions.remove(dungeonRegion);
        return true;
    }

    /**
     * Get the maximum number of loaded regions.
     *
     * @return The maximum number of loaded regions.
     */
    public int getMaximumLoadedRegions() {
        return REGION_LOADED_MAX;
    }

    /**
     * Unload all the excess region if more region are loaded than allowed.
     *
     * @return The number of unloaded region.
     */
    public int unloadExcessRegions() {
        // Get the number of loaded region higher than the maximum allowed count
        final int excessCount = getLoadedRegionCount() - getMaximumLoadedRegions();
        final int preferredUnload = Math.max(getLoadedRegionCount() - REGION_LOADED_PREFERRED, 0);

        // Make sure the maximum count of loaded region is reached, before unloading
        if(excessCount <= 0)
            return 0;

        // Show a debug message in the console
        Core.getLogger().debug("Unloading all excess dungeon regions for '" + getWorld().getName() + "'...");

        // Count the number of unloaded region
        int unloaded = 0;

        // Unload the excess regions
        for(int i = 0; i < preferredUnload; i++)
            if(unloadRegion(0))
                unloaded++;

        // Return the number of unloaded regions
        return unloaded;
    }
}
