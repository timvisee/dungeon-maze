package com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.Random;

import org.bukkit.Chunk;

import com.timvisee.dungeonmaze.populator.surface.DMSurfaceBlockPopulator;
import com.timvisee.dungeonmaze.populator.surface.DMSurfaceBlockPopulatorArgs;

public class FlowerPopulator extends DMSurfaceBlockPopulator {
	public static final int CHANCE_OF_FLOWER = 15;
	public static final int ITERATIONS = 10;

	@Override
	public void populateSurface(DMSurfaceBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			// Apply chances
			if(rand.nextInt(100) < CHANCE_OF_FLOWER) {
				int xFlower = rand.nextInt(16);
				int zFlower = rand.nextInt(16);
				
				// Get the surface level at the location of the flower
				int ySurface = args.getSurfaceLevel(xFlower, zFlower);
				
				// Make sure the surface block is grass
				if(c.getBlock(xFlower, ySurface, zFlower).getTypeId() == 2) {
					int flowerY = ySurface + 1;
					
					// Spawn the flower
					c.getBlock(xFlower, flowerY, zFlower).setTypeId(getRandomFlowerType(rand));	
				}
			}
		}
	}
	
	/**
	 * Get a random flower type
	 * @param rand Random instance
	 * @return Random flower type ID
	 */
	public int getRandomFlowerType(Random rand) {
		return (37 + (rand.nextInt(2)));
	}
}