package com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulator;
import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulatorArgs;

public class TallGrassPopulator extends SurfaceBlockPopulator {

	public static final int CHANCE_GRASS = 35;
	public static final int ITERATIONS = 100;

	@Override
	public void populateSurface(SurfaceBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			// Apply chances
			if(rand.nextInt(100) < CHANCE_GRASS) {
				int xGrass = rand.nextInt(16);
				int zGrass = rand.nextInt(16);
				
				// Get the surface level
				int ySurface = args.getSurfaceLevel(xGrass, zGrass);
				
				if(c.getBlock(xGrass, ySurface, zGrass).getType() == Material.GRASS) {
					int yGrass = ySurface + 1;
					
					c.getBlock(xGrass, yGrass, zGrass).setType(Material.LONG_GRASS);
					c.getBlock(xGrass, yGrass, zGrass).setData((byte) 1);
				}
			}
		}
	}
}