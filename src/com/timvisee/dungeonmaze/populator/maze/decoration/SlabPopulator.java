package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulatorArgs;

public class SlabPopulator extends DMMazeBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_OF_SLAB = 50;
	public static final int ITERATIONS = 7;

	@Override
	public void populateMaze(DMMazeBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int z = args.getChunkZ();
		
		for(int i = 0; i < ITERATIONS; i++) {
			if(rand.nextInt(100) < CHANCE_OF_SLAB) {
				int slabX = x + rand.nextInt(6) + 1;
				int slabY = args.getFloorY() + 1;
				int slabZ = z + rand.nextInt(6) + 1;
				
				if(c.getBlock(slabX, slabY - 1, slabZ).getTypeId() != 0) {
					Block slabBlock = c.getBlock(slabX, slabY, slabZ);
					if(slabBlock.getTypeId() == 0) {
						slabBlock.setTypeId(44);
						slabBlock.setData((byte) 3);
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