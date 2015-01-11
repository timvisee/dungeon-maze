package com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulator;
import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulatorArgs;

public class FlowerPopulator extends SurfaceBlockPopulator {

	public static final int CHANCE_FLOWER = 15;
	public static final int ITERATIONS = 10;

	@Override
	public void populateSurface(SurfaceBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			// Apply chances
			if(rand.nextInt(100) < CHANCE_FLOWER) {
				int xFlower = rand.nextInt(16);
				int zFlower = rand.nextInt(16);
				
				// Get the surface level at the location of the flower
				int ySurface = args.getSurfaceLevel(xFlower, zFlower);
				
				// Make sure the surface block is grass
				if(c.getBlock(xFlower, ySurface, zFlower).getType() == Material.GRASS) {
					int flowerY = ySurface + 1;
					
					// Spawn the flower
					if (rand.nextInt(2) == 0) {
						c.getBlock(xFlower, flowerY, zFlower).setType(Material.YELLOW_FLOWER);
					} else {
						c.getBlock(xFlower, flowerY, zFlower).setType(Material.RED_ROSE);
						c.getBlock(xFlower, flowerY, zFlower).setData(getRandomFlowerType(rand));
					}
				}
			}
		}
	}
	
	/**
	 * Get a random flower type
	 * @param rand Random instance
	 * @return Random flower type ID
	 */
	public byte getRandomFlowerType(Random rand) {
		return (byte) (rand.nextInt(9));
	}
}