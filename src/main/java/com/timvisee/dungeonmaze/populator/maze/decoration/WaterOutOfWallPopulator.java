package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class WaterOutOfWallPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 5;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .04f;

    // TODO: Implement this!
	public static final double CHANCE_WATER_ADDITION_EACH_LEVEL = -0.833; /* to 0 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int y = args.getChunkY();
		final int z = args.getChunkZ();
		final int floorOffset = args.getFloorOffset();
        final int lanternX = x + rand.nextInt(8);
        final int lanternY = y + rand.nextInt(4 - floorOffset) + 2 + floorOffset;
        final int lanternZ = z + rand.nextInt(8);

        // Specify the water block
        final Block waterBlock = chunk.getBlock(lanternX, lanternY, lanternZ);

        // Set the block to water only if it will be replacing a valid wall block
        if(waterBlock.getType() == Material.COBBLESTONE ||
                waterBlock.getType() == Material.MOSSY_COBBLESTONE ||
                waterBlock.getType() == Material.SMOOTH_BRICK)
            waterBlock.setType(Material.WATER);
	}

    @Override
    public float getRoomChance() {
        return ROOM_CHANCE;
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
