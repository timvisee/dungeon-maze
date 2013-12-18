package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class IronBarPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE = 25;
	public static final int CHANCE_2_HEIGHT = 66;
	public static final int ITERATIONS = 2;

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
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
				if(b.getType() == Material.COBBLESTONE || b.getType() == Material.MOSSY_COBBLESTONE || b.getType() == Material.SMOOTH_BRICK) {
					b.setType(Material.IRON_FENCE);
					if(rand.nextInt(100) < CHANCE_2_HEIGHT) {
						Block block2 = c.getBlock(blockX, blockY + 1, blockZ);
						block2.setType(Material.IRON_FENCE);
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
