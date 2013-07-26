package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulatorArgs;

public class SoulsandPopulator extends DMMazeBlockPopulator {
	public static final int MIN_LAYER = 3;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE = 20;
	public static final double CHANCE_ADDITION_PER_LEVEL = -1.667; /* to 10 */
	public static final int ITERATIONS = 15;

	@Override
	public void populateMaze(DMMazeBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			if(rand.nextInt(100) < CHANCE + (CHANCE_ADDITION_PER_LEVEL * (y - 30) / 6)) {
				Block b = c.getBlock(x + rand.nextInt(8), rand.nextInt(2) + y, z + rand.nextInt(8));
				
				if (b.getTypeId() == 4)
					b.setTypeId(88);
			}
		}	
	}
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return MIN_LAYER;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return MAX_LAYER;
	}
}
