package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import com.timvisee.dungeonmaze.util.NumberUtils;
import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class StrutPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 2;
	private static final int LAYER_MAX = 7;
	private static final int ROOM_CHANCE = 2;

    /** Populator constants. */
	private static final float CHANCE_STRUT_NEAR_SPAWN = .5f;
	private static final int STRUT_DISTANCE_NEAR_SPAWN_MAX = 4; // Distance in chunks

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
        final int x = args.getRoomChunkX();
        final int yFloor = args.getFloorY();
        final int yCeiling = args.getCeilingY();
        final int z = args.getRoomChunkZ();
		
		// Make sure the distance between the spawn and the current chunk is allowed
		if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < STRUT_DISTANCE_NEAR_SPAWN_MAX) {
			// Strut near spawn
			if (rand.nextFloat() < CHANCE_STRUT_NEAR_SPAWN) {
				final int yStrutBar = yCeiling - 1;
				
				if(chunk.getBlock(x + 2, yStrutBar, z).getType() == Material.AIR) {
					// Generate strut bar
					for(int xx = 1; xx < 7; xx++)
						chunk.getBlock(x + xx, yStrutBar, z).setType(Material.WOOD);
						
					// Generate strut poles
					for(int yy = yFloor + 1; yy < yStrutBar; yy++) {
						chunk.getBlock(x + 1, yy, z).setType(Material.FENCE);
						chunk.getBlock(x + 6, yy, z).setType(Material.FENCE);
					}
				}	
			}
			if (rand.nextInt(100) < CHANCE_STRUT_NEAR_SPAWN) {
				final int yStrutBar = yCeiling - 1;

				if(chunk.getBlock(x, yStrutBar, z + 2).getType() == Material.AIR) {
					// Generate strut bar
					for(int zz = 1; zz < 7; zz++)
						chunk.getBlock(x, yStrutBar, z + zz).setType(Material.WOOD);

					// Generate strut poles
					for(int yy = yFloor + 1; yy < yStrutBar; yy++) {
						chunk.getBlock(x, yy, z+1).setType(Material.FENCE);
						chunk.getBlock(x, yy, z+6).setType(Material.FENCE);
					}
				}
				
			}
		} else {
			// Normal strut
			if(rand.nextInt(100) < ROOM_CHANCE) {
				final int yStrutBar = yCeiling - 1;
				
				if(chunk.getBlock(x + 2, yStrutBar, z).getType() == Material.AIR) {
					// Generate strut bar
					for(int xx = 1; xx < 7; xx++)
						chunk.getBlock(x + xx, yStrutBar, z).setType(Material.WOOD);
						
					// Generate strut poles
					for(int yy = yFloor + 1; yy < yStrutBar; yy++) {
						chunk.getBlock(x+1, yy, z).setType(Material.FENCE);
						chunk.getBlock(x+6, yy, z).setType(Material.FENCE);
					}
				}
					
			}
			if (rand.nextInt(100) < ROOM_CHANCE) {
				final int yStrutBar = yCeiling - 1;

				if(chunk.getBlock(x, yStrutBar, z + 2).getType() == Material.AIR) {
					// Generate strut bar
					for(int zz = 1; zz < 7; zz++)
						chunk.getBlock(x, yStrutBar, z + zz).setType(Material.WOOD);
						
					// Generate strut poles
					for(int yy = yFloor + 1; yy < yStrutBar; yy++) {
						chunk.getBlock(x, yy, z+1).setType(Material.FENCE);
						chunk.getBlock(x, yy, z+6).setType(Material.FENCE);
					}
				}
					
			}
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