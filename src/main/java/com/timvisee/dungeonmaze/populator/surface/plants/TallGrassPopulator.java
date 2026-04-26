package com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.Random;

import org.bukkit.Chunk;

import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulator;
import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulatorArgs;
import com.timvisee.dungeonmaze.util.MaterialUtils;
import org.bukkit.Material;

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
        final Material grassPlant = MaterialUtils.requireBlockMaterial("SHORT_GRASS", "GRASS");

        // Get the surface level
        int ySurface = args.getSurfaceLevel(xGrass, zGrass);

        if(chunk.getBlock(xGrass, ySurface, zGrass).getType() == Material.GRASS_BLOCK) {
            final int yGrass = ySurface + 1;

            setGeneratedBlock(chunk.getBlock(xGrass, yGrass, zGrass), grassPlant);
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
