package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class GravelPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 2;
	public static final int LAYER_MAX = 7;
    public static final float ROOM_CHANCE = .05f;
    public static final int ROOM_ITERATIONS = 3;
	public static final int ROOM_ITERATIONS_MAX = 2;

    /** Populator constants. */
	public static final BlockFace[] DIRECTIONS = new BlockFace[] {
			BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.EAST,
            BlockFace.WEST
    };

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int yFloor = args.getFloorY();
		final int z = args.getChunkZ();
        final int startX = x + rand.nextInt(6) + 1;
        final int startY = yFloor + 1;
        final int startZ = z + rand.nextInt(6) + 1;
        final int startHeight = rand.nextInt(2) + 1;

        // Choose two random directions
        BlockFace dir1 = DIRECTIONS[rand.nextInt(DIRECTIONS.length)];
        BlockFace dir2 = DIRECTIONS[rand.nextInt(DIRECTIONS.length)];

        int height = startHeight;
        int x2 = startX;
        int z2 = startZ;
        while(height > 0 && 0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
            for(int y2 = startY; y2 < startY + height; y2++)
                if(chunk.getBlock(x2, y2, z2).getType() == Material.AIR)
                    chunk.getBlock(x2, y2, z2).setType(Material.GRAVEL);

            height -= rand.nextInt(1);

            x2 += dir1.getModX();
            z2 += dir1.getModZ();
        }

        if(dir1 != dir2) {
            height = startHeight;
            x2 = startX;
            z2 = startZ;
            while(height > 0 && 0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
                for(int y2 = startY; y2 < startY + height; y2++)
                    if(chunk.getBlock(x2, y2, z2).getType() == Material.AIR)
                        chunk.getBlock(x2, y2, z2).setType(Material.GRAVEL);

                height -= rand.nextInt(1);

                x2 += dir2.getModX();
                z2 += dir2.getModZ();
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
    public float getRoomPopulationChance() {
        return ROOM_CHANCE;
    }

    @Override
    public int getRoomPopulationIterations() {
        return ROOM_ITERATIONS;
    }

    @Override
    public int getRoomPopulationIterationsMax() {
        return ROOM_ITERATIONS_MAX;
    }
}