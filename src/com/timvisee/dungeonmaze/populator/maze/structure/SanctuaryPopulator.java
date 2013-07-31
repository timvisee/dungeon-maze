package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class SanctuaryPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 1;
	public static final int CHANCE_OF_SANCTUARY = 3; //Promile

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();
		
		// Apply chances
		if (rand.nextInt(1000) < CHANCE_OF_SANCTUARY) {
			
			// Register the current room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c.getX(), c.getZ(), x, 30, z);
			
			for (int x2=x; x2 < x+8; x2+=1)
				for (int z2=z; z2 < z+8; z2+=1)
					c.getBlock(x2, yFloor, z2).setTypeId(49);
			
			// Outline altar right
			c.getBlock(x + 2, yFloor + 1, z + 2).setTypeId(41);
			c.getBlock(x + 3, yFloor + 1, z + 2).setTypeId(87);
			c.getBlock(x + 4, yFloor + 1, z + 2).setTypeId(87);
			c.getBlock(x + 5, yFloor + 1, z + 2).setTypeId(41);
			
			// Center altar
			c.getBlock(x + 2, yFloor + 1, z + 3).setTypeId(87);
			c.getBlock(x + 3, yFloor + 1, z + 3).setTypeId(88);
			c.getBlock(x + 4, yFloor + 1, z + 3).setTypeId(88);
			c.getBlock(x + 5, yFloor + 1, z + 3).setTypeId(87);
			
			// Outline altar left
			c.getBlock(x + 2, yFloor + 1, z + 4).setTypeId(41);
			c.getBlock(x + 3, yFloor + 1, z + 4).setTypeId(87);
			c.getBlock(x + 4, yFloor + 1, z + 4).setTypeId(87);
			c.getBlock(x + 5, yFloor + 1, z + 4).setTypeId(41);
			
			// Torches
			c.getBlock(x + 2, yFloor + 2, z + 2).setTypeId(50);
			c.getBlock(x + 5, yFloor + 2, z + 2).setTypeId(50);
			c.getBlock(x + 2, yFloor + 2, z + 4).setTypeId(50);
			c.getBlock(x + 5, yFloor + 2, z + 4).setTypeId(50);
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