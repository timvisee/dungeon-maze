package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class LanternPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 3;
	public static final int LAYER_MAX = 7;
	public static final int CHANCE_SINGLE = 30;
	public static final double CHANCE_SINGLE_ADDITION_EACH_LEVEL = 7.5; /* to 75 */
	public static final int ITERATIONS_SINGLE = 2;
	public static final int CHANCE_DOUBLE = 10;
	public static final double CHANCE_DOUBLE_ADDITION_EACH_LEVEL = 4.167; /* to 35 */
	public static final int ITERATIONS_DOUBLE = 2;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		int floorOffset = args.getFloorOffset();
		
		// Apply chances
		if(rand.nextInt(100) < CHANCE_SINGLE + (CHANCE_SINGLE_ADDITION_EACH_LEVEL * (y - 30) / 6)) {
			for(int i = 0; i < ITERATIONS_SINGLE; i++) {
				int lanternX = x + rand.nextInt(8);
				int lanternY = y + rand.nextInt(4 - floorOffset) + 2 + floorOffset;
				int lanternZ = z + rand.nextInt(8);
				
				Block b = c.getBlock(lanternX, lanternY, lanternZ);
				if(b.getType() == Material.COBBLESTONE || b.getType() == Material.MOSSY_COBBLESTONE || b.getType() == Material.SMOOTH_BRICK)
					b.setType(Material.JACK_O_LANTERN);
			}
		}
		
		if(rand.nextInt(100) < CHANCE_DOUBLE + (CHANCE_DOUBLE_ADDITION_EACH_LEVEL * (y - 30) / 6)) {
			for(int i = 0; i < ITERATIONS_DOUBLE; i++) {
				int lanternX = x + rand.nextInt(8);
				int lanternY = rand.nextInt(4 - floorOffset) + 2 + floorOffset;
				int lanternZ = z + rand.nextInt(8);
				
				Block b = c.getBlock(lanternX, lanternY, lanternZ);
				if(b.getType() == Material.COBBLESTONE || b.getType() == Material.MOSSY_COBBLESTONE || b.getType() == Material.SMOOTH_BRICK)
					b.setType(Material.PUMPKIN);
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

	// Deprecated, might use later again to rotate pumpkins correctly
	/*private byte getData(int x, int z, int x2, int z2, Material type) {
		if (x == x2) {
			if (z < z2)
				return (byte) (type == Material.JACK_O_LANTERN ? 0 : 2);
			return (byte) (type == Material.JACK_O_LANTERN ? 2 : 0);
		}
		if (x < x2)
			return (byte) (type == Material.JACK_O_LANTERN ? 3 : 1);
		return (byte) (type == Material.JACK_O_LANTERN ? 1 : 3);
	}*/
}
