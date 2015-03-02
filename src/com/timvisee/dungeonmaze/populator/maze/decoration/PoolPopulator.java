package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class PoolPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 1;
	public static final float ROOM_CHANCE = .05f; // Includes lava pools

	public static final int NO_LAVA_NEAR_SPAWN_RADIUS = 2; // In chunks
	public static final int CHANCE_LAVA = 35; // Rest is water

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int z = args.getChunkZ();
		
		boolean allowLava = true;
		if(Math.abs(c.getX()) < NO_LAVA_NEAR_SPAWN_RADIUS || Math.abs(c.getZ()) < NO_LAVA_NEAR_SPAWN_RADIUS)
			allowLava = false;

        LiquidType liquidType = LiquidType.WATER;

        if(allowLava && rand.nextInt(100) < CHANCE_LAVA)
            liquidType = LiquidType.LAVA;

        int poolX = x + rand.nextInt(6) + 1;
        int poolY = args.getFloorY();
        int poolZ = z + rand.nextInt(6) + 1;
        int poolW = rand.nextInt(5);
        int poolL = rand.nextInt(5);

        for (int i = Math.max(poolX - poolW / 2, 1); i < Math.min(poolX - poolW / 2 + poolW, 6); i++) {
            for (int j = Math.max(poolZ - poolL / 2, 1); j < Math.min(poolZ - poolL / 2 + poolL, 6); j++) {
                c.getBlock(i, poolY, j).setType(liquidType.getMaterial());
                c.getBlock(i, poolY - 1, j).setType(Material.MOSSY_COBBLESTONE);
            }
        }
	}
	
	public enum LiquidType {
		WATER(Material.STATIONARY_WATER),
		LAVA(Material.STATIONARY_LAVA);
		
		private final Material mat;
		
		LiquidType(Material mat) {
			this.mat = mat;
		}
		
		public Material getMaterial() {
			return mat;
		}
	}

    @Override
    public float getRoomPopulationChance() {
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
