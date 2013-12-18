package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class MossPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int ITERATIONS = 80;
	public static final int CHANCE = 70;

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		// Iterate
		for (int i = 0; i < ITERATIONS; i++) {
			
			// Apply chances
			if (rand.nextInt(100) < CHANCE) {
				Block block = c.getBlock(x + rand.nextInt(8), rand.nextInt((y + 6) - y + 1) + y, z + rand.nextInt(8));
				if (block.getType() == Material.COBBLESTONE)
					block.setType(Material.MOSSY_COBBLESTONE);
					
				if (block.getType() == Material.SMOOTH_BRICK)
					block.setData((byte) 1);
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
