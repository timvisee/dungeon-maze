package com.timvisee.dungeonmaze.test;

import com.timvisee.dungeonmaze.populator.ChunkBlockPopulator;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.Random;

public class DelayedPopulator {

    private ChunkBlockPopulator populator;
    private World world;
    private Random rand;
    private Chunk chunk;

    public DelayedPopulator(ChunkBlockPopulator populator, World world, Random rand, Chunk chunk) {
        this.populator = populator;
        this.world = world;
        this.rand = rand;
        this.chunk = chunk;
    }

    public ChunkBlockPopulator getPopulator() {
        return populator;
    }

    public World getWorld() {
        return world;
    }

    public Random getRand() {
        return rand;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void populate() {
        this.populator.populate(this.world, this.rand, this.chunk, true);
    }
}
