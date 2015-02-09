package com.timvisee.dungeonmaze.world.dungeon.chunk;

import org.bukkit.World;

public class DungeonChunk {

    /** Defines the world the chunks is in. */
    private World world;
    /** Defines the X and Z coordinate of the chunk in the world. */
    private int x, z;

    /**
     * Constructor.
     *
     * @param world The world the chunks is in.
     * @param x The X coordinate of the chunk.
     * @param z The Z coordinate of the chunk.
     */
    public DungeonChunk(World world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    /**
     * Get the world the chunk is in.
     *
     * @return The world the chunk is in.
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Get the X coordinate of the chunk.
     *
     * @return The X coordinate of the chunk.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the Z coordinate of the chunk.
     *
     * @return The Z coordinate of the chunk.
     */
    public int getZ() {
        return this.z;
    }
}
