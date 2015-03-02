package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class MushroomPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 6;
	public static final int CHANCE_MUSHROOM_BROWN = 1;
	public static final int CHANCE_MUSHROOM_RED = 1;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();

		// Apply chances
		if(rand.nextInt(100) < CHANCE_MUSHROOM_BROWN) {
			int spawnerX = x + rand.nextInt(6) + 1;
			int spawnerY = yFloor + 1;
			int spawnerZ = z + rand.nextInt(6) + 1;
			
			if(c.getBlock(spawnerX, spawnerY - 1, spawnerZ).getType() != Material.AIR) {
				Block b = c.getBlock(spawnerX, spawnerY, spawnerZ);
				b = c.getBlock(spawnerX, spawnerY, spawnerZ);
				if(b.getType() == Material.AIR)
					b.setType(Material.BROWN_MUSHROOM);
			}
		}

		// Apply chances
		if(rand.nextInt(100) < CHANCE_MUSHROOM_RED) {
			int spawnerX = x + rand.nextInt(6) + 1;
			int spawnerY = yFloor + 1;
			int spawnerZ = z + rand.nextInt(6) + 1;
			
			if(c.getBlock(spawnerX, spawnerY - 1, spawnerZ).getType() != Material.AIR) {
				Block b = c.getBlock(spawnerX, spawnerY, spawnerZ);
				b = c.getBlock(spawnerX, spawnerY, spawnerZ);
				if(b.getType() == Material.AIR)
					b.setType(Material.RED_MUSHROOM);
			}
		}
	}

    @Override
    public float getRoomPopulationChance() {
        // TODO: Improve this!
        return 1.0f;
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