package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class TorchPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 2;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_OF_TORCH = 10;
	public static final double CHANCE_OF_TORCH_ADDITION_PER_LEVEL = 3.333; /* to 30 */

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		// Apply chances
		if(rand.nextInt(100) < CHANCE_OF_TORCH + (CHANCE_OF_TORCH_ADDITION_PER_LEVEL * (y - 30) / 6)) {
								
			int torchX = x + rand.nextInt(6) + 1;
			int torchY = args.getFloorY() + 1;
			int torchZ = z + rand.nextInt(6) + 1;
								
			if(c.getBlock(torchX, torchY - 1, torchZ).getTypeId() != 0) {
				Block torchBlock = c.getBlock(torchX, torchY, torchZ);
				if(torchBlock.getTypeId() == 0) {
					torchBlock = c.getBlock(torchX, torchY, torchZ);
					torchBlock.setTypeId(50);
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