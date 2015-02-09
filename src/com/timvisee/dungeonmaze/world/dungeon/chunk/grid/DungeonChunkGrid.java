package com.timvisee.dungeonmaze.world.dungeon.chunk.grid;

import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class DungeonChunkGrid {

    /** Defines the world of the chunk grid. */
    private World world;
    /** Defines the loaded chunks in the grid. */
    private List<DungeonChunk> chunks = new ArrayList<DungeonChunk>();

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
}
