package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class BrokenWallsPopulator extends MazeRoomBlockPopulator {

    // TODO: 'Finish' this populator!

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .33f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
		final int roomX = args.getRoomChunkX();
		final int roomZ = args.getRoomChunkZ();

        // Define the position variables
        int posX, posY, posZ;

        // Determine the y position of the gap
        posY = args.getFloorY() + 1 + rand.nextInt(2);

        // Define the x and z position of the broken wall
        if(rand.nextBoolean()) {
            posX = roomX + (rand.nextBoolean() ? 0 : 7);
            posZ = roomZ + rand.nextInt(6) + 1;

        } else {
            posX = roomZ + rand.nextInt(6) + 1;
            posZ = roomX + (rand.nextBoolean() ? 0 : 7);
        }

		// TODO: Improve this!
        // Make a gap in the wall
        chunk.getBlock(posX, posY, posZ).setType(Material.AIR);
        chunk.getBlock(posX, posY + 1, posZ).setType(Material.AIR);
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