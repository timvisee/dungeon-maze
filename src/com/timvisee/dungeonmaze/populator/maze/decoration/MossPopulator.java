package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class MossPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
	public static final float ROOM_CHANCE = .7f;
	public static final int ROOM_ITERATIONS = 80;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final Chunk c = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int y = args.getChunkY();
		final int z = args.getChunkZ();
        final Block block = c.getBlock(x + rand.nextInt(8), rand.nextInt((y + 6) - y + 1) + y, z + rand.nextInt(8));

        if (block.getType() == Material.COBBLESTONE)
            block.setType(Material.MOSSY_COBBLESTONE);

        if (block.getType() == Material.SMOOTH_BRICK)
            block.setData((byte) 1);
	}

    @Override
    public float getRoomPopulationChance() {
        return ROOM_CHANCE;
    }

    @Override
    public int getRoomPopulationIterations() {
        return ROOM_ITERATIONS;
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
