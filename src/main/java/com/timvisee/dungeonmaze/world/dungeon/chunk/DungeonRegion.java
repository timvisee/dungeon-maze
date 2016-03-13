package com.timvisee.dungeonmaze.world.dungeon.chunk;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonRegionGrid;
import org.bukkit.World;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.*;

public class DungeonRegion {

    /**
     * Defines the size of the region on a single side of the region.
     * Thus, a size of 32 will result in 32*32=1024 chunks in total inside this region.
     */
    public final static int REGION_SIZE = 32;

    /**
     * Defines the prefix for the region file names.
     */
    private static final String REGION_FILE_PREFIX = "r.";

    /**
     * Defines the separator that is used for the region file names.
     */
    private static final String REGION_FILE_SEPARATOR = ".";

    /**
     * Defines the suffix for the region file names.
     */
    private static final String REGION_FILE_SUFFIX = ".dmr";

    /**
     * Defines the dungeon region grid this region is in.
     */
    private final DungeonRegionGrid regionGrid;

    /**
     * Defines the X of the region (on a 2D region plane) in the world.
     */
    private final int x;

    /**
     * Defines the Y of the region (on a 2D region plane) in the world.
     */
    private final int y;

    /**
     * The grid of chunks inside this region.
     */
    // TODO: Make sure this is initialized with null fields!
    private DungeonChunk[][] chunks = new DungeonChunk[REGION_SIZE][REGION_SIZE];

    /**
     * The last region that has been accessed.
     */
    private DungeonChunk lastChunkCache = null;

    /**
     * Constructor.
     *
     * @param regionGrid The region grid this region is in.
     * @param regionX Region X coordinate.
     * @param regionY Region Y coordinate.
     */
    public DungeonRegion(DungeonRegionGrid regionGrid, int regionX, int regionY) {
        this.regionGrid = regionGrid;
        this.x = regionX;
        this.y = regionY;
    }

    /**
     * Get the dungeon grid this region is in.
     *
     * @return The grid.
     */
    public DungeonRegionGrid getGrid() {
        return this.regionGrid;
    }

    /**
     * Get the world the region is in.
     *
     * @return The world the region is in.
     */
    public World getWorld() {
        return this.regionGrid.getWorld();
    }

    /**
     * Get the name of the world this region is in.
     *
     * @return Name of the world.
     */
    public String getWorldName() {
        return getWorld().getName();
    }

    /**
     * Check whether this region is in the given world.
     *
     * @param world The world.
     *
     * @return True if the region is in the given world, false if not.
     */
    public boolean isWorld(World world) {
        return this.regionGrid.isWorld(world);
    }

    /**
     * Get the X coordinate of the region.
     *
     * @return The X coordinate of the region.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the X coordinate of the region in the world space.
     *
     * @return The X coordinate of the region in the world space.
     */
    public int getWorldX() {
        return this.x * REGION_SIZE * DungeonChunk.CHUNK_SIZE;
    }

    /**
     * Get the Y coordinate of the chunk.
     *
     * @return The Y coordinate of the chunk.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Get the Z coordinate of the region in the world space.
     *
     * @return The Z coordinate of the region in the world space.
     */
    public int getWorldZ() {
        return this.y * REGION_SIZE * DungeonChunk.CHUNK_SIZE;
    }

    /**
     * Check whether this region is at a specific position.
     *
     * @param regionX The X coordinate of the region.
     * @param regionY The Z coordinate of the region.
     *
     * @return True if the region is at this position, false otherwise.
     */
    public boolean isAt(int regionX, int regionY) {
        return this.x == regionX && this.y == regionY;
    }

    /**
     * Get the chunk data file.
     *
     * @param regionGrid The Dungeon Chunk Grid instance.
     *
     * @return The chunk data file.
     */
    public File getRegionDataFile(DungeonRegionGrid regionGrid) {
        return regionGrid.getRegionDataFile(this.x, this.y);
    }

    // TODO: Saving and loading!

    /**
     * Get the grid of chunks inside this region as a 2 dimensional array with the specified region size.
     *
     * @return Grid of chunks.
     */
    public DungeonChunk[][] getChunks() {
        return this.chunks;
    }

    /**
     * Get the total number of chunks that can be store in the region.
     *
     * @return Number of chunks that can be stored in this region.
     */
    public int getChunkCapacity() {
        return REGION_SIZE * REGION_SIZE;
    }

    /**
     * Get the number of chunks in this region. Chunks that haven't been created yet will not be included in the count.
     *
     * @return Numbers in this chunk.
     */
    public int getChunkCount() {
        // Keep track of the count
        int count = 0;

        // Loop through the grid, increase the count if it's a chunk
        for(int x = 0; x < REGION_SIZE; x++)
            for(int y = 0; y < REGION_SIZE; y++)
                if(getChunk(x, y) != null)
                    count++;

        // Return the number of chunks
        return count;
    }

    /**
     * Get a specific dungeon chunk instance. The dungeon chunk data will be created if it doesn't exist yet.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkY The Y coordinate of the chunk.
     *
     * @return The dungeon chunk instance, or null on failure.
     */
    public DungeonChunk getOrCreateChunk(int chunkX, int chunkY) {
        // Compare the requested chunk with the cache
        if(lastChunkCache != null && lastChunkCache.isAt(chunkX, chunkY))
            return lastChunkCache;

        // Make sure the chunk coordinates are in-bound
        if(chunkX < 0 || chunkX >= REGION_SIZE || chunkY < 0 || chunkY >= REGION_SIZE)
            // TODO: Add some sort of exception here?
            return null;

        // Check whether this chunk exists, return it if it exists
        DungeonChunk chunk = getChunk(chunkX, chunkY);
        if(chunk != null)
            return chunk;

        // Create the chunk data, return the result
        return (lastChunkCache = createChunk(chunkX, chunkY));
    }

    /**
     * Get the dungeon chunk at the specified coordinate.
     * If a chunk at this coordinate doesn't exist yet, null will be returned.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkY The Y coordinate of the chunk.
     *
     * @return The dungeon chunk, or null if it doesn't exist yet.
     */
    public DungeonChunk getChunk(int chunkX, int chunkY) {
        return this.chunks[chunkX][chunkY];
    }

    /**
     * Create a dungeon chunk at the specified coordinates.
     *
     * @param chunkX The X coordinate of the chunk to create.
     * @param chunkY The Y coordinate of the chunk to create.
     *
     * @return The dungeon chunk instance on success, or null on failure.
     */
    public DungeonChunk createChunk(int chunkX, int chunkY) {
        // Make sure no data exists for this chunk
        if(hasChunk(chunkX, chunkY))
            return getOrCreateChunk(chunkX, chunkY);

        // Create the chunk data for this chunk, save it and return the instance
        DungeonChunk dungeonChunk = new DungeonChunk(this, chunkX, chunkY);

        // Set the dungeon chunk in the grid
        this.chunks[chunkX][chunkY] = dungeonChunk;

        // Unload the excess regions
        // TODO: Get this to work again!
        //unloadExcessRegions();

        // Return the new chunk
        return dungeonChunk;
    }

    /**
     * Check whether a dungeon chunk is available at the specified location.
     * If a chunk isn't available, it might not have been created yet.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkY The Y coordinate of the chunk.
     *
     * @return True if the chunk is available, false if not.
     */
    public boolean hasChunk(int chunkX, int chunkY) {
        return getChunk(chunkX, chunkY) != null;
    }

    /**
     * Get the file name for this region.
     *
     * @return Region file name.
     */
    public String getRegionFileName() {
        return getRegionFileName(this.x, this.y);
    }

    /**
     * Get the file name for a region at the given coordinates.
     *
     * @param x Region X coordinate.
     * @param y Region Y coordinate.
     *
     * @return Region file name.
     */
    public static String getRegionFileName(int x, int y) {
        return REGION_FILE_PREFIX +
                x +
                REGION_FILE_SEPARATOR +
                y +
                REGION_FILE_SUFFIX;
    }

    /**
     * Load the dungeon region at the specified location.
     *
     * @param regionGrid The dungeon region grid of the world.
     * @param regionX The X coordinate of the region.
     * @param regionY The Y coordinate of the region.
     *
     * @return The dungeon region instance.
     */
    public static DungeonRegion load(DungeonRegionGrid regionGrid, int regionX, int regionY) throws IOException {
        // Get the region data file for the given region coordinates
        File regionFile = regionGrid.getRegionDataFile(regionX, regionY);

        // Make sure the region file exists before loading it
        if(!regionFile.exists())
            // TODO: Throw a proper exception!
            return null;

        // Create a reader for the region data
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(new FileInputStream(regionFile));

        // Get the version code and version name of the plugin that was used to write the data file
        //noinspection unused
        int versionCode = unpacker.unpackInt();
        //noinspection unused
        String versionName = unpacker.unpackString();

        // Load the region from the given unpacker
        DungeonRegion region = load(regionGrid, unpacker);

        // Close the unpacker
        unpacker.close();

        // Return the region
        return region;
    }

    /**
     * Load a dungeon region from the given unpacker.
     *
     * @param regionGrid The dungeon region grid of the world.
     * @param unpacker The unpacker the data should be unpacked from.
     *
     * @return The dungeon region instance.
     */
    public static DungeonRegion load(DungeonRegionGrid regionGrid, MessageUnpacker unpacker) throws IOException {
        // Get the coordinates of the region
        int x = unpacker.unpackInt();
        int y = unpacker.unpackInt();

        // Construct the region instance
        DungeonRegion region = new DungeonRegion(regionGrid, x, y);

        // Get the number of chunks that are stored
        int chunkCount = unpacker.unpackInt();

        // Loop through all the sections in the chunks section to load the chunks from
        for(int i = 0; i < chunkCount; i++) {
            // Get the size of the chunk data, and read it's payload
            int chunkDataSize = unpacker.unpackInt();
            byte[] bytes = unpacker.readPayload(chunkDataSize);
            MessageUnpacker chunkUnpacker = MessagePack.newDefaultUnpacker(bytes);

            // Load the given chunk in the region from the unpacker of the chunk
            region.loadChunkFromUnpacker(chunkUnpacker);

            // Close the unpacker
            chunkUnpacker.close();
        }

        // Show a status message, that the region was loaded
        Core.getLogger().debug("Loaded region at (" + region.getWorldName() + ", " + x + ", " + y + ")");

        // Return the dungeon region
        return region;
    }

    /**
     * Load a dungeon chunk from the given unpacker.
     *
     * @param unpacker The unpacker to unpack the data from.
     *
     * @return The dungeon chunk.
     */
    public DungeonChunk loadChunkFromUnpacker(MessageUnpacker unpacker) throws IOException {
        // Load the dungeon chunk
        DungeonChunk chunk = DungeonChunk.load(this, unpacker);

        // Add the chunk to the grid
        this.chunks[chunk.getX()][chunk.getY()] = chunk;

        // Return the loaded chunk
        return chunk;
    }

    /**
     * Save the region to the default file.
     *
     * @param regionGrid The dungeon region grid instance, used to determine the save location based on the world.
     *
     * @return The number of chunks that were saved in this region.
     * This doesn't count the chunks that weren't available yet.
     *
     * @throws IOException Throws if an error occurred while saving the region file.
     */
    public int save(DungeonRegionGrid regionGrid) throws IOException {
        // Get the file to save the region to
        File regionFile = getRegionDataFile(regionGrid);

        // Save the file, and return the result
        return save(regionFile);
    }

    /**
     * Save the region to the specified file.
     *
     * @param regionFile The file to save the region in.
     *
     * @return The number of chunks that were saved in this region.
     * This doesn't count the chunks that weren't available yet.
     *
     * @throws IOException Throws if an error occurred while saving the region file.
     */
    public int save(File regionFile) throws IOException {
        // Open an output stream for the data to write to the target file
        FileOutputStream out = new FileOutputStream(regionFile);

        // Create the data packer
        MessagePacker packer = MessagePack.newDefaultPacker(out);

        // Pack the data
        packer.packInt(DungeonMaze.getVersionCode());
        packer.packString(DungeonMaze.getVersionName());

        // Save the actual region to the section
        int saved = save(packer);

        // Close the file
        packer.close();

        // Return the number of saved chunks
        return saved;
    }

    /**
     * Save all the loaded chunks in the current chunks grid.
     *
     * @param packer The message packer for storage.
     *
     * @return The number of chunks that were saved in this region.
     * This doesn't count the chunks that weren't available yet.
     */
    public int save(MessagePacker packer) throws IOException {
        // Count how many chunks are saved
        int chunkCount = getChunkCount();

        // Pack the X and Y coordinate of the region
        packer.packInt(this.x);
        packer.packInt(this.y);

        // Pack the number of chunks that will be stored
        packer.packInt(chunkCount);

        // Loop through the chunk grid to save all of them
        for(int x = 0; x < REGION_SIZE; x++) {
            for(int y = 0; y < REGION_SIZE; y++) {
                // Get the dungeon chunk at this coordinate, but skip it if there's no chunk at this location
                DungeonChunk chunk = getChunk(x, y);
                if(chunk == null)
                    continue;

                // Create a buffered packer for the chunk
                MessageBufferPacker chunkPacker = MessagePack.newDefaultBufferPacker();

                // Save the chunk to the given section
                chunk.save(chunkPacker);

                // Pack the chunk data
                byte[] chunkPackerData = chunkPacker.toByteArray();
                packer.packInt(chunkPackerData.length);
                packer.writePayload(chunkPackerData);

                // Close the chunk packer
                chunkPacker.close();
            }
        }

        // Show a status message, that the region was saved
        Core.getLogger().debug("Saved region for '" + getWorldName() + "' at (" + getX() + ", " + getY() + ")");

        // Return the number of saved chunks
        return chunkCount;
    }
}
