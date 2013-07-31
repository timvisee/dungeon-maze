package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class SilverfishBlockPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 3;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE = 75;
	public static final int ITERATIONS = 8;
	public static final double CHANCE_ADDITION_PER_LEVEL = -4.167; /* to 75 */

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int floorOffset = args.getFloorOffset();
		int z = args.getChunkZ();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			if(rand.nextInt(100) < CHANCE + (CHANCE_ADDITION_PER_LEVEL * (y - 30) / 6)) {
				int blockX = x + rand.nextInt(8);
				int blockY = y + rand.nextInt(4 - floorOffset) + 1 + floorOffset;
				int blockZ = z + rand.nextInt(8);
				
				Block lanternBlock = c.getBlock(blockX, blockY, blockZ);
				if(lanternBlock.getTypeId() == 1) {
					lanternBlock.setTypeId(97);
					lanternBlock.setData((byte) 0);
				} else if(lanternBlock.getTypeId() == 4) {
					lanternBlock.setTypeId(97);
					lanternBlock.setData((byte) 1);
				} else if(lanternBlock.getTypeId() == 48) {
					lanternBlock.setTypeId(97);
					lanternBlock.setData((byte) 1);
				} else if(lanternBlock.getTypeId() == 98) {
					lanternBlock.setTypeId(97);
					lanternBlock.setData((byte) 2);
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
