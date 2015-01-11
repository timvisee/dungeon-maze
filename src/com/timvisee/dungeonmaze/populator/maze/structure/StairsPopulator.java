package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class StairsPopulator extends MazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 6;
	public static final int CHANCE_OF_STAIRS = 2;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();
		
		// Apply chances
		if(rand.nextInt(100) < CHANCE_OF_STAIRS) {
			
			if(c.getBlock(x, y - 1, z).getType() != Material.AIR) {
				c.getBlock(x + 5, yFloor + 1, z + 2).setType(Material.COBBLESTONE_STAIRS);
				c.getBlock(x + 6, yFloor + 1, z + 2).setType(Material.COBBLESTONE_STAIRS);
				c.getBlock(x + 5, yFloor + 1 + 1, z + 3).setType(Material.COBBLESTONE_STAIRS);
				c.getBlock(x + 6, yFloor + 1 + 1, z + 3).setType(Material.COBBLESTONE_STAIRS);
				c.getBlock(x + 5, yFloor + 1 + 2, z + 4).setType(Material.COBBLESTONE_STAIRS);
				c.getBlock(x + 6, yFloor + 1 + 2, z + 4).setType(Material.COBBLESTONE_STAIRS);
				c.getBlock(x + 5, yFloor + 1 + 2, z + 5).setType(Material.COBBLESTONE);
				c.getBlock(x + 6, yFloor + 1 + 2, z + 5).setType(Material.COBBLESTONE);
				c.getBlock(x + 5, yFloor + 1 + 2, z + 6).setType(Material.COBBLESTONE);
				c.getBlock(x + 6, yFloor + 1 + 2, z + 6).setType(Material.COBBLESTONE);
				c.getBlock(x + 4, yFloor + 1 + 3, z + 5).setType(Material.COBBLESTONE_STAIRS);
				c.getBlock(x + 4, yFloor + 1 + 3, z + 6).setType(Material.COBBLESTONE_STAIRS);
				c.getBlock(x + 3, yFloor + 1 + 4, z + 5).setType(Material.COBBLESTONE_STAIRS);
				c.getBlock(x + 3, yFloor + 1 + 4, z + 6).setType(Material.COBBLESTONE_STAIRS);
				c.getBlock(x + 2, yFloor + 1 + 5, z + 5).setType(Material.COBBLESTONE_STAIRS);
				c.getBlock(x + 2, yFloor + 1 + 5, z + 6).setType(Material.COBBLESTONE_STAIRS);
				
				c.getBlock(x + 3, yFloor + 1 + 5, z + 5).setType(Material.AIR);
				c.getBlock(x + 3, yFloor + 1 + 5, z + 6).setType(Material.AIR);
				c.getBlock(x + 4, yFloor + 1 + 5, z + 5).setType(Material.AIR);
				c.getBlock(x + 4, yFloor + 1 + 5, z + 6).setType(Material.AIR);
				c.getBlock(x + 5, yFloor + 1 + 5, z + 5).setType(Material.AIR);
				c.getBlock(x + 5, yFloor + 1 + 5, z + 6).setType(Material.AIR);
				c.getBlock(x + 2, yFloor + 1 + 6, z + 5).setType(Material.AIR);
				c.getBlock(x + 2, yFloor + 1 + 6, z + 6).setType(Material.AIR);
				c.getBlock(x + 3, yFloor + 1 + 6, z + 5).setType(Material.AIR);
				c.getBlock(x + 3, yFloor + 1 + 6, z + 6).setType(Material.AIR);
				c.getBlock(x + 4, yFloor + 1 + 6, z + 5).setType(Material.AIR);
				c.getBlock(x + 4, yFloor + 1 + 6, z + 6).setType(Material.AIR);
				c.getBlock(x + 5, yFloor + 1 + 6, z + 5).setType(Material.AIR);
				c.getBlock(x + 5, yFloor + 1 + 6, z + 6).setType(Material.AIR);
				c.getBlock(x + 2, yFloor + 1 + 7, z + 5).setType(Material.AIR);
				c.getBlock(x + 2, yFloor + 1 + 7, z + 6).setType(Material.AIR);
				c.getBlock(x + 3, yFloor + 1 + 7, z + 5).setType(Material.AIR);
				c.getBlock(x + 3, yFloor + 1 + 7, z + 6).setType(Material.AIR);
				c.getBlock(x + 4, yFloor + 1 + 7, z + 5).setType(Material.AIR);
				c.getBlock(x + 4, yFloor + 1 + 7, z + 6).setType(Material.AIR);
				c.getBlock(x + 5, yFloor + 1 + 7, z + 5).setType(Material.AIR);
				c.getBlock(x + 5, yFloor + 1 + 7, z + 6).setType(Material.AIR);
				
				// Set the data values!
				c.getBlock(x + 5, yFloor + 1, z + 2).setData((byte) 2);
				c.getBlock(x + 6, yFloor + 1, z + 2).setData((byte) 2);
				c.getBlock(x + 5, yFloor + 1 + 1, z + 3).setData((byte) 2);
				c.getBlock(x + 6, yFloor + 1 + 1, z + 3).setData((byte) 2);
				c.getBlock(x + 5, yFloor + 1 + 2, z + 4).setData((byte) 2);
				c.getBlock(x + 6, yFloor + 1 + 2, z + 4).setData((byte) 2);
				c.getBlock(x + 4, yFloor + 1 + 3, z + 5).setData((byte) 1);
				c.getBlock(x + 4, yFloor + 1 + 3, z + 6).setData((byte) 1);
				c.getBlock(x + 3, yFloor + 1 + 4, z + 5).setData((byte) 1);
				c.getBlock(x + 3, yFloor + 1 + 4, z + 6).setData((byte) 1);
				c.getBlock(x + 2, yFloor + 1 + 5, z + 5).setData((byte) 1);
				c.getBlock(x + 2, yFloor + 1 + 5, z + 6).setData((byte) 1);

				c.getBlock(x + 5, yFloor + 1 + 1, z + 4).setType(Material.COBBLESTONE);
				c.getBlock(x + 6, yFloor + 1 + 1, z + 4).setType(Material.COBBLESTONE);
				c.getBlock(x + 5, yFloor + 1 + 1, z + 5).setType(Material.COBBLESTONE);
				c.getBlock(x + 6, yFloor + 1 + 1, z + 5).setType(Material.COBBLESTONE);
				c.getBlock(x + 5, yFloor + 1 + 1, z + 6).setType(Material.COBBLESTONE);
				c.getBlock(x + 6, yFloor + 1 + 1, z + 6).setType(Material.COBBLESTONE);
				c.getBlock(x + 4, yFloor + 1 + 2, z + 5).setType(Material.COBBLESTONE);
				c.getBlock(x + 4, yFloor + 1 + 2, z + 6).setType(Material.COBBLESTONE);
				c.getBlock(x + 3, yFloor + 1 + 3, z + 5).setType(Material.COBBLESTONE);
				c.getBlock(x + 3, yFloor + 1 + 3, z + 6).setType(Material.COBBLESTONE);
				c.getBlock(x + 2, yFloor + 1 + 4, z + 5).setType(Material.COBBLESTONE);
				c.getBlock(x + 2, yFloor + 1 + 4, z + 6).setType(Material.COBBLESTONE);
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