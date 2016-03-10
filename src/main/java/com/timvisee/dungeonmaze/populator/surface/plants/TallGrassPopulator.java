package com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulator;
import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulatorArgs;

public class TallGrassPopulator extends SurfaceBlockPopulator {

    /** General populator constants. */
    private static final int CHUNK_ITERATIONS = 100;
    private static final float CHUNK_ITERATIONS_CHANCE = .35f;

	@Override
	public void populateSurface(SurfaceBlockPopulatorArgs args) {
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
        final int xGrass = rand.nextInt(16);
        final int zGrass = rand.nextInt(16);

        // Get the surface level
        int ySurface = args.getSurfaceLevel(xGrass, zGrass);

        if(chunk.getBlock(xGrass, ySurface, zGrass).getType() == Material.GRASS) {
            final int yGrass = ySurface + 1;

            chunk.getBlock(xGrass, yGrass, zGrass).setType(Material.LONG_GRASS);
            chunk.getBlock(xGrass, yGrass, zGrass).setData((byte) 1);
        }
	}

    @Override
    public int getChunkIterations() {
        return CHUNK_ITERATIONS;
    }

    @Override
    public float getChunkIterationsChance() {
        return CHUNK_ITERATIONS_CHANCE;
    }
}