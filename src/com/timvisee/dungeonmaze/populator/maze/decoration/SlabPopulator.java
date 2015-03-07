package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class SlabPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
	public static final int ROOM_ITERATIONS = 7;
	public static final float ROOM_ITERATIONS_CHANCE = .5f;
    public static final int ROOM_ITERATIONS_MAX = 6;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int z = args.getChunkZ();
        int slabX = x + rand.nextInt(6) + 1;
        int slabY = args.getFloorY() + 1;
        int slabZ = z + rand.nextInt(6) + 1;

        if(chunk.getBlock(slabX, slabY - 1, slabZ).getType() != Material.AIR) {
            Block slabBlock = chunk.getBlock(slabX, slabY, slabZ);
            if(slabBlock.getType() == Material.AIR) {
                slabBlock.setType(Material.STEP);
                slabBlock.setData((byte) 3);
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

    @Override
    public int getRoomIterations() {
        return ROOM_ITERATIONS;
    }

    @Override
    public float getRoomIterationsChance() {
        return ROOM_ITERATIONS_CHANCE;
    }

    @Override
    public int getRoomIterationsMax() {
        return ROOM_ITERATIONS_MAX;
    }
}