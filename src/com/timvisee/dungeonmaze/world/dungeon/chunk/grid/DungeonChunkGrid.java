package com.timvisee.dungeonmaze.world.dungeon.chunk.grid;

import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DungeonChunkGrid {

    /** Defines the world of the chunk grid. */
    private World world;
    /** Defines the loaded chunks in the grid. */
    private List<DungeonChunk> chunks = new ArrayList<DungeonChunk>();

    /** Defines the name of the dungeon chunk data file. */
    private final static String CHUNK_DATA_FILE = "data.dmc";

    /**
     * Constructor.
     *
     * @param world The world of the chunk grid.
     */
    public DungeonChunkGrid(World world) {
        this.world = world;
    }

    /**
     * Get the world of the chunk grid.
     *
     * @return The world of the chunk grid.
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Get the list of loaded dungeon chunks. This is different than loaded minecraft world chunks.
     *
     * @return The list of loaded dungeon chunks.
     */
    public List<DungeonChunk> getLoadedChunks() {
        return this.chunks;
    }

    /**
     * Get the number of loaded dungeon chunks. This is different than loaded minecraft world chunks.
     *
     * @return The number of loaded dungeon chunks.
     */
    public int getLoadedChunksCount() {
        return this.chunks.size();
    }

    /**
     * The the chunk grid data directory
     *
     * @return The chunk grid data directory
     */
    public File getChunkGridDirectory() {
        return DungeonChunkGridManager.getChunkGridDataDirectory(this.world);
    }

    /**
     * Get the data file for a chunk.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkZ The Z coordinate of the chunk.
     *
     * @return The chunk data file.
     */
    public File getChunkDataFile(int chunkX, int chunkZ) {
        return new File(getChunkGridDirectory(), chunkX + "/" + chunkZ + "/" + CHUNK_DATA_FILE);
    }

    /**
     * Get a specific dungeon chunk instance. The dungeon chunk data will be created if it doesn't exist yet.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkZ The Z coordinate of the chunk.
     *
     * @return The dungeon chunk instance, or null on failure.
     */
    public DungeonChunk getOrCreateChunk(int chunkX, int chunkZ) {
        // Loop through all the loaded chunks to see if it's loaded
        for(DungeonChunk chunk : this.chunks)
            if(chunk.isAt(chunkX, chunkZ))
                return chunk;

        // Create or load the chunk data, return the result
        return loadChunk(chunkX, chunkZ);
    }

    /**
     * Load a dungeon chunk. The chunk data will be created if it doesn't exist.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkZ The Z coordinate of the chunk.
     *
     * @return The dungeon chunk instance, or null on failure.
     */
    public DungeonChunk loadChunk(int chunkX, int chunkZ) {
        // Make sure the chunk isn't loaded yet
        if(isChunkLoaded(chunkX, chunkZ))
            return getOrCreateChunk(chunkX, chunkZ);

        // Create the chunk data if it doesn't have any data stored yet
        if(!hasChunkData(chunkX, chunkZ))
            return createChunkData(chunkX, chunkZ);

        // Load a dungeon chunk and make sure it's valid
        DungeonChunk dungeonChunk = DungeonChunk.load(this.world, getChunkDataFile(chunkX, chunkZ));
        if(dungeonChunk == null)
            return null;

        // Add the chunk to the chunk list, return the result
        this.chunks.add(dungeonChunk);
        return dungeonChunk;
    }

    /**
     * Create the chunk data for a specific hunk. The current chunk data will be returned if any data already exists
     * for this chunk.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkZ The Z coordinate of the chunk.
     *
     * @return The dungeon chunk instance on success, or null on failure.
     */
    public DungeonChunk createChunkData(int chunkX, int chunkZ) {
        // Make sure no data exists for this chunk
        if(hasChunkData(chunkX, chunkZ))
            return getOrCreateChunk(chunkX, chunkZ);

        // Create the chunk data for this chunk, save it and return the instance
        DungeonChunk dungeonChunk = new DungeonChunk(this.world, chunkX, chunkZ);
        dungeonChunk.save(getChunkDataFile(chunkX, chunkZ));

        // Add the dungeon chunk to the list
        this.chunks.add(dungeonChunk);

        // Return the new chunk
        return dungeonChunk;
    }

    /**
     * Check whether a chunk has any data stored.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkZ The Z coordinate of the chunk.
     *
     * @return True if this chunk has any data stored, false otherwise.
     */
    public boolean hasChunkData(int chunkX, int chunkZ) {
        // Get the data file of the chunk
        File chunkDataFile = getChunkDataFile(chunkX, chunkZ);

        // Check whether this chunk has any data file
        return chunkDataFile.exists();
    }

    /**
     * Check whether a specific chunk is loaded.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkZ The Z coordinate of the chunk.
     *
     * @return True if the chunk is loaded, false otherwise.
     */
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        // Loop through all the loaded chunks to see if it's loaded
        for(DungeonChunk chunk : this.chunks)
            if(chunk.isAt(chunkX, chunkZ))
                return true;

        // The chunk doesn't seem to be loaded, return false
        return false;
    }

    /**
     * Save all the loaded chunks in the current chunks grid.
     *
     * @return The number of saved chunks.
     */
    public int saveLoadedChunks() {
        // Count how many chunks are saved
        int saved = 0;

        // Loop through all chunks
        for(DungeonChunk chunk : this.chunks) {
            // Get the chunk data file
            File chunkDataFile = chunk.getChunkDataFile(this);

            // Save the data
            if(chunk.save(chunkDataFile))
                saved++;
        }

        // Return the number of saved chunks
        return saved;
    }
}
