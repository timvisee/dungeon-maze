package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class BrokenWallsPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
	public static final int CHANCE_BROKEN_WALL = 50;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int z = args.getChunkZ();
		
		// Calculate chances
		if (rand.nextInt(100) < CHANCE_BROKEN_WALL) {
			
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
			
			c.getBlock(posX, posY + 1, posZ).setType(Material.AIR);
			c.getBlock(posX, posY + 2, posZ).setType(Material.AIR);
			
			// TODO: Make a more exciting broken wall
		}
	}
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return LAYER_MIN;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return LAYER_MAX;
	}
}