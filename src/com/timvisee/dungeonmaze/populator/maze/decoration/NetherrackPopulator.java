package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class NetherrackPopulator extends MazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 2;
	public static final int ITERATIONS = 15;
	public static final int CHANCE = 5;
	public static final int BURNING_NETHERRACK = 20;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		// Iterate
		for (int i = 0; i < ITERATIONS; i++) {
			if (rand.nextInt(100) < CHANCE) {
				Block b = c.getBlock(x + rand.nextInt(8), rand.nextInt(2)+ y, z + rand.nextInt(8));
				if (b.getType() == Material.COBBLESTONE) {
					b.setType(Material.NETHERRACK);
					
					// Decide if the netherrack should be burning
					if(rand.nextInt(100) < BURNING_NETHERRACK) {
						Block burnBlock = c.getBlock(b.getX(), b.getY() + 1, b.getZ());
						if (burnBlock.getType() == Material.AIR)
							burnBlock.setType(Material.FIRE);
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