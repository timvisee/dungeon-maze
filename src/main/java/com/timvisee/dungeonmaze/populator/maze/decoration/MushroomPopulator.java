package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class MushroomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 6;
    public static final float ROOM_CHANCE = .02f;

    /** Populator constants. */
    private static final float MUSHROOM_RED_CHANCE = .5f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int yFloor = args.getFloorY();
		final int z = args.getChunkZ();

        // Set the mushroom type
        Material mushroomType = Material.BROWN_MUSHROOM;

		// Apply chances
		if(rand.nextFloat() < MUSHROOM_RED_CHANCE)
            mushroomType = Material.RED_MUSHROOM;

        final int spawnerX = x + rand.nextInt(6) + 1;
        final int spawnerY = yFloor + 1;
        final int spawnerZ = z + rand.nextInt(6) + 1;

        if(chunk.getBlock(spawnerX, spawnerY - 1, spawnerZ).getType() != Material.AIR) {
            Block b = chunk.getBlock(spawnerX, spawnerY, spawnerZ);
            if(b.getType() == Material.AIR)
                b.setType(mushroomType);
        }
	}

    @Override
    public float getRoomChance() {
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