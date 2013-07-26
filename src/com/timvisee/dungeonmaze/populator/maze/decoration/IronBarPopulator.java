package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulatorArgs;

public class IronBarPopulator extends DMMazeBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE = 25;
	public static final int CHANCE_2_HEIGHT = 66;
	public static final int ITERATIONS = 2;

	@Override
	public void populateMaze(DMMazeBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		int floorOffset = args.getFloorOffset();
		
		// Iterate
		for (int i = 0; i < ITERATIONS; i++) {
			if (rand.nextInt(100) < CHANCE) {
				int blockX = x + rand.nextInt(8);
				int blockY = y + rand.nextInt(4 - floorOffset) + 1 + floorOffset;
				int blockZ = z + rand.nextInt(8);
				
				Block b = c.getBlock(blockX, blockY, blockZ);
				if(b.getTypeId() == 4 || b.getTypeId() == 48 || b.getTypeId() == 98) {
					b.setTypeId(101);
					if(rand.nextInt(100) < CHANCE_2_HEIGHT) {
						Block block2 = c.getBlock(blockX, blockY + 1, blockZ);
						block2.setTypeId(101);
					}
				}
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
