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
	public static final float ROOM_CHANCE = .5f;
	public static final int ROOM_ITERATIONS = 7;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int z = args.getChunkZ();
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