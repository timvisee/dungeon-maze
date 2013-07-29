package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulatorArgs;

public class MushroomPopulator extends DMMazeBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 6;
	public static final int CHANCE_OF_BROWN_MUSHROOM = 1;
	public static final int CHANCE_OF_RED_MUSHROOM = 1;

	@Override
	public void populateMaze(DMMazeBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();

		// Apply chances
		if(rand.nextInt(100) < CHANCE_OF_BROWN_MUSHROOM) {
			int spawnerX = x + rand.nextInt(6) + 1;
			int spawnerY = yFloor + 1;
			int spawnerZ = z + rand.nextInt(6) + 1;
			
			if(c.getBlock(spawnerX, spawnerY - 1, spawnerZ).getTypeId() != 0) {
				Block b = c.getBlock(spawnerX, spawnerY, spawnerZ);
				b = c.getBlock(spawnerX, spawnerY, spawnerZ);
				if(b.getTypeId() == 0)
					b.setTypeId(39);
			}
		}

		// Apply chances
		if(rand.nextInt(100) < CHANCE_OF_RED_MUSHROOM) {
			int spawnerX = x + rand.nextInt(6) + 1;
			int spawnerY = yFloor + 1;
			int spawnerZ = z + rand.nextInt(6) + 1;
			
			if(c.getBlock(spawnerX, spawnerY - 1, spawnerZ).getTypeId() != 0) {
				Block b = c.getBlock(spawnerX, spawnerY, spawnerZ);
				b = c.getBlock(spawnerX, spawnerY, spawnerZ);
				if(b.getTypeId() == 0)
					b.setTypeId(40);
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