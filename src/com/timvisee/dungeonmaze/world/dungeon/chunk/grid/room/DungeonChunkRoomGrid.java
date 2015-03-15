package com.timvisee.dungeonmaze.world.dungeon.chunk.grid.room;

import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;

import java.util.ArrayList;
import java.util.List;

public class DungeonChunkRoomGrid {

    /** Defines the chunk this set of rooms is in. */
    private DungeonChunk chunk;
    /** The list of rooms in this dungeon chunk. */
    private List<DungeonChunkRoom> rooms = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param chunk The dungeon chunk this set of rooms is in.
     */
    public DungeonChunkRoomGrid(DungeonChunk chunk) {
        // Set the fields
        setChunk(chunk);
    }

    /**
     * Get the dungeon chunk this set of rooms is in.
     *
     * @return The dungeon chunk this set of rooms is in.
     */
    public DungeonChunk getChunk() {
        return this.chunk;
    }

    /**
     * Set the dungeon chunk this set of rooms is in.
     *
     * @param chunk The dungeon chunk this set of rooms is in.
     */
    private void setChunk(DungeonChunk chunk) {
        this.chunk = chunk;
    }
}
