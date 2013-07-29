package com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.Random;

import org.bukkit.Chunk;

import com.timvisee.dungeonmaze.populator.surface.DMSurfaceBlockPopulator;
import com.timvisee.dungeonmaze.populator.surface.DMSurfaceBlockPopulatorArgs;

public class TallGrassPopulator extends DMSurfaceBlockPopulator {
	public static final int CHANCE_OF_GRASS = 35;
	public static final int ITERATIONS = 100;

	@Override
	public void populateSurface(DMSurfaceBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			// Apply chances
			if(rand.nextInt(100) < CHANCE_OF_GRASS) {
				int xGrass = rand.nextInt(16);
				int zGrass = rand.nextInt(16);
				
				// Get the surface level
				int ySurface = args.getSurfaceLevel(xGrass, zGrass);
				
				if(c.getBlock(xGrass, ySurface, zGrass).getTypeId() == 2) {
					int yGrass = ySurface + 1;
					
					c.getBlock(xGrass, yGrass, zGrass).setTypeId(31);
					c.getBlock(xGrass, yGrass, zGrass).setData((byte) 1);
				}
			}
		}
	}
}