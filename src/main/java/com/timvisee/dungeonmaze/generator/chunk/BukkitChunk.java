package com.timvisee.dungeonmaze.generator.chunk;

import org.bukkit.World;

public class BukkitChunk extends ShortChunk {

    /**
     * Constructor.
     *
     * @param world  The world of the chunk.
     * @param chunkX The X coordinate of the chunk.
     * @param chunkZ The Z coordinate of the chunk.
     */
    public BukkitChunk(World world, int chunkX, int chunkZ) {
        super(world, chunkX, chunkZ);
    }
}
