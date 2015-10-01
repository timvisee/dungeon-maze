package com.timvisee.dungeonmaze.world.dungeon.chunk.grid.room;

import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;

public class DungeonChunkRoom {

    // TODO: Make this variable?
    /** Defines the size (length and width) of a room. */
    public static final int ROOM_SIZE = 8;

    /** Defines the dungeon chunk the room is in. */
    private DungeonChunk chunk;
    /** Defines the X position of the room in a chunk. */
    private int x;
    /** Defines the Z position of the room in a chunk. */
    private int z;
    /** Defines the layer position of the room in a chunk. */
    private int layer;

    /**
     * Constructor.
     *
     * @param chunk The chunk the room is in.
     * @param x The X position of the room.
     * @param z The Z position of the room.
     * @param layer The layer position of the room.
     */
    public DungeonChunkRoom(DungeonChunk chunk, int x, int z, int layer) {
        this.chunk = chunk;
        this.x = x;
        this.z = z;
        this.layer = layer;
    }

    /**
     * Get the dungeon chunk the room is in.
     *
     * @return The dungeon chunk the room is in.
     */
    public DungeonChunk getChunk() {
        return this.chunk;
    }

    /**
     * Set the dungeon chunk the room is in.
     *
     * @param chunk The dungeon chunk the room is in.
     */
    @SuppressWarnings("UnusedDeclaration")
    private void setChunk(DungeonChunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Get the X position of the room.
     *
     * @return The X position of the room.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Set the X position of the room.
     *
     * @param x The X position of the room.
     */
    @SuppressWarnings("UnusedDeclaration")
    private void setX(int x) {
        this.x = x;
    }

    /**
     * Get the Z position of the room.
     *
     * @return The Z position of the room.
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Set the Z position of the room.
     *
     * @param z The Z position of the room.
     */
    @SuppressWarnings("UnusedDeclaration")
    private void setZ(int z) {
        this.z = z;
    }

    /**
     * Get the layer position of the room.
     *
     * @return The layer position of the room.
     */
    public int getLayer() {
        return this.layer;
    }

    /**
     * Set the layer position of the room.
     *
     * @param layer The layer position of the room.
     */
    @SuppressWarnings("UnusedDeclaration")
    private void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * Get the X coordinate of the room in the world space.
     *
     * @return The X coordinate of the room in the world space.
     */
    @SuppressWarnings("UnusedDeclaration")
    public int getWorldX() {
        // Make sure the room is valid
        if(!isValid())
            return 0;

        // Get the X coordinate of the chunk in the world
        final int xChunk = this.chunk.getWorldX();

        // Calculate and return the X coordinate of the room in the world space
        return (this.x * ROOM_SIZE) + xChunk;
    }

    // TODO: Get a 'GetWorldY()' method!

    /**
     * Get the Z coordinate of the room in the world space.
     *
     * @return The Z coordinate of the room in the world space.
     */
    @SuppressWarnings("UnusedDeclaration")
    public int getWorldZ() {
        // Make sure the room is valid
        if(!isValid())
            return 0;

        // Get the Z coordinate of the chunk in the world
        final int zChunk = this.chunk.getWorldZ();

        // Calculate and return the Z coordinate of the room in the world space
        return (this.z * ROOM_SIZE) + zChunk;
    }

    /**
     * Make sure the room is valid.
     *
     * @return True if the room is valid, false otherwise.
     */
    public boolean isValid() {
        // Make sure the chunk isn't null, return the result
        return this.chunk != null;
    }
}
