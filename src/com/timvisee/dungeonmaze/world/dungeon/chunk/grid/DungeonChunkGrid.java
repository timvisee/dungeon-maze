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

    /** Defines the chunk data directory name. */
    private static final String CHUNK_DATA_DIRECTORY = "chunks";

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
     * The the chunk grid data directory
     *
     * @return The chunk grid data directory
     */
    public File getChunkGridDirectory() {
        // TODO: Get the dungeon maze world data directory using some method!

        return new File(this.world.getWorldFolder(), "DungeonMaze/" + CHUNK_DATA_DIRECTORY);
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
            File chunkDataFile = chunk.getChunkDataFile(getChunkGridDirectory());

            // Save the data
            if(chunk.save(chunkDataFile))
                saved++;
        }

        // Return the number of saved chunks
        return saved;
    }
}
