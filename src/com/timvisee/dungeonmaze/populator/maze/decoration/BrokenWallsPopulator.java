package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;

import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulatorArgs;

public class BrokenWallsPopulator extends DMMazeBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_OF_BROKENWALL = 50;

	@Override
	public void populateMaze(DMMazeBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int z = args.getChunkZ();
		
		// Calculate chances
		if (rand.nextInt(100) < CHANCE_OF_BROKENWALL) {
			
			// Define the positions var
			int posX, posY, posZ;
			
			switch (rand.nextInt(2)) {
			case 0:
				posX = x + (rand.nextInt(2) * 7);
				posY = args.getFloorY();
				posZ = z + 1 + rand.nextInt(6);
				break;
			case 1:
				posX = x + 1 + rand.nextInt(6);
				posY = args.getFloorY();
				posZ = z + (rand.nextInt(2) * 7);
				break;
			default:
				posX = x + (rand.nextInt(2) * 7);
				posY = args.getFloorY();
				posZ = z + 1 + rand.nextInt(6);	
			}
			
			c.getBlock(posX, posY + 1, posZ).setTypeId(0);
			c.getBlock(posX, posY + 2, posZ).setTypeId(0);
			
			// TODO: Make a more exciting broken wall
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