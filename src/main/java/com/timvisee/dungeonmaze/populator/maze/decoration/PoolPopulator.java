package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class PoolPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 1;
	private static final float ROOM_CHANCE = .05f; // Includes lava pools

    /** Populator constants. */
	private static final int NO_LAVA_NEAR_SPAWN_RADIUS = 2; // In chunks
	private static final int LAVA_CHANCE = 35; // Rest is water

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();

        // Determine whether the lava liquid type is allowed
		boolean allowLava = true;
		if(Math.abs(chunk.getX()) < NO_LAVA_NEAR_SPAWN_RADIUS || Math.abs(chunk.getZ()) < NO_LAVA_NEAR_SPAWN_RADIUS)
			allowLava = false;

        // Determine the liquid type of the pool
        LiquidType liquidType = LiquidType.WATER;
        if(allowLava && rand.nextInt(100) < LAVA_CHANCE)
            liquidType = LiquidType.LAVA;

        // Specify the pool location and size
        final int xPool = x + rand.nextInt(6) + 1;
        final int yPool = args.getFloorY();
        final int zPool = z + rand.nextInt(6) + 1;
        final int poolWidth = rand.nextInt(5);
        final int poolLength = rand.nextInt(5);

        // Create/spawn the pool with the specified liquid
        for(int i = Math.max(xPool - poolWidth / 2, 1); i < Math.min(xPool - poolWidth / 2 + poolWidth, 6); i++) {
            for(int j = Math.max(zPool - poolLength / 2, 1); j < Math.min(zPool - poolLength / 2 + poolLength, 6); j++) {
                chunk.getBlock(i, yPool, j).setType(liquidType.getMaterial());
                chunk.getBlock(i, yPool - 1, j).setType(Material.MOSSY_COBBLESTONE);
            }
        }
    }
	
	public enum LiquidType {
		WATER(Material.STATIONARY_WATER),
		LAVA(Material.STATIONARY_LAVA);

        /** The liquid type as a Bukkit material. */
		private final Material mat;

        /**
         * Constructor.
         *
         * @param mat The material type of the liquid.
         */
		LiquidType(Material mat) {
			this.mat = mat;
		}

        /**
         * Get the Bukkit material type of the liquid.
         *
         * @return The material type of the liquid.
         */
		public Material getMaterial() {
			return mat;
		}
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
