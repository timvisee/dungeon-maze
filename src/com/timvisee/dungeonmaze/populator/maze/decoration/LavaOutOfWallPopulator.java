package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class LavaOutOfWallPopulator extends MazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 4;
	public static final int CHANCE_OF_LAVA = 5;
	public static final double CHANCE_OF_LAVA_ADDITION_PER_LEVEL = -0.833; /* to 0 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		int floorOffset = args.getFloorOffset();
		
		if(rand.nextInt(100) < CHANCE_OF_LAVA + (CHANCE_OF_LAVA_ADDITION_PER_LEVEL * (y - 30) / 6)) {
			int lanternX = x + rand.nextInt(8);
			int lanternY = y + rand.nextInt(4 - floorOffset) + 2 + floorOffset;
			int lanternZ = z + rand.nextInt(8);
			
			Block b = c.getBlock(lanternX, lanternY, lanternZ);
			if(b.getType() == Material.COBBLESTONE || b.getType() == Material.MOSSY_COBBLESTONE || b.getType() == Material.SMOOTH_BRICK)
				b.setType(Material.LAVA);
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
