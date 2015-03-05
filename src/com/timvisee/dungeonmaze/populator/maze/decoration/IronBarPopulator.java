package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class IronBarPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
    public static final int ROOM_ITERATIONS = 2;
	public static final float ROOM_ITERATIONS_CHANCE = .25f;

    /** Populator constants. */
	public static final int CHANCE_DOUBLE_HEIGHT = 66;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int y = args.getChunkY();
		final int z = args.getChunkZ();
		final int floorOffset = args.getFloorOffset();
        int blockX = x + rand.nextInt(8);
        int blockY = y + rand.nextInt(4 - floorOffset) + 1 + floorOffset;
        int blockZ = z + rand.nextInt(8);

        Block b = c.getBlock(blockX, blockY, blockZ);
        if(b.getType() == Material.COBBLESTONE || b.getType() == Material.MOSSY_COBBLESTONE || b.getType() == Material.SMOOTH_BRICK) {
            b.setType(Material.IRON_FENCE);
            if(rand.nextInt(100) < CHANCE_DOUBLE_HEIGHT) {
                Block block2 = c.getBlock(blockX, blockY + 1, blockZ);
                block2.setType(Material.IRON_FENCE);
            }
        }
	}

    @Override
    public int getRoomIterations() {
        return ROOM_ITERATIONS;
    }

    @Override
    public float getRoomIterationsChance() {
        return ROOM_ITERATIONS_CHANCE;
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
