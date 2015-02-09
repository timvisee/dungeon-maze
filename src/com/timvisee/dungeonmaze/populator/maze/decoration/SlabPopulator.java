package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class SlabPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
	public static final int CHANCE_SLAB = 50;
	public static final int ITERATIONS = 7;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int z = args.getChunkZ();

		for(int i = 0; i < ITERATIONS; i++) {
			if(rand.nextInt(100) < CHANCE_SLAB) {
				int slabX = x + rand.nextInt(6) + 1;
				int slabY = args.getFloorY() + 1;
				int slabZ = z + rand.nextInt(6) + 1;
				
				if(c.getBlock(slabX, slabY - 1, slabZ).getType() != Material.AIR) {
					Block slabBlock = c.getBlock(slabX, slabY, slabZ);
					if(slabBlock.getType() == Material.AIR) {
						slabBlock.setType(Material.STEP);
						slabBlock.setData((byte) 3);
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