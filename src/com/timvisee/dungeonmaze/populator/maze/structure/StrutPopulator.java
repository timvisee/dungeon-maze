package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class StrutPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 2;
	public static final int LAYER_MAX = 7;
	public static final int CHANCE_STRUT = 2;
	public static final int CHANCE_STRUT_NEAR_SPAWN = 50;
	public static final int STRUT_DISTANCE_NEAR_SPAWN_MAX = 4; // Distance in chunks

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int yFloor = args.getFloorY();
		int yCeiling = args.getCeilingY();
		int z = args.getChunkZ();
		
		// Make sure the distance between the spawn and the current chunk is allowed
		if(distance(c.getX(), c.getZ(), 0, 0) < STRUT_DISTANCE_NEAR_SPAWN_MAX) {
			// Strut near spawn
			if (rand.nextInt(100) < CHANCE_STRUT_NEAR_SPAWN) {
				int yStrutbar = yCeiling - 1;
				
				if(c.getBlock(x + 2, yStrutbar, z).getType() == Material.AIR) {
					// Generate strut bar
					for(int xx = 1; xx < 7; xx++)
						c.getBlock(x + xx, yStrutbar, z + 0).setType(Material.WOOD);
						
					// Generate strut poles
					for(int yy = yFloor + 1; yy < yStrutbar; yy++) {
						c.getBlock(x + 1, yy, z + 0).setType(Material.FENCE);
						c.getBlock(x + 6, yy, z + 0).setType(Material.FENCE);
					}
				}	
			}
			if (rand.nextInt(100) < CHANCE_STRUT_NEAR_SPAWN) {
				int yStrutbar = yCeiling - 1;

				if(c.getBlock(x, yStrutbar, z + 2).getType() == Material.AIR) {
					// Generate strut bar
					for(int zz = 1; zz < 7; zz++) {
						c.getBlock(x + 0, yStrutbar, z + zz).setType(Material.WOOD);
					}
					// Generate strut poles
					for(int yy = yFloor + 1; yy < yStrutbar; yy++) {
						c.getBlock(x+0, yy, z+1).setType(Material.FENCE);
						c.getBlock(x+0, yy, z+6).setType(Material.FENCE);
					}
				}
				
			}
		} else {
			// Normal strut
			if(rand.nextInt(100) < CHANCE_STRUT) {

				int yStrutbar = yCeiling - 1;
				
				if(c.getBlock(x + 2, yStrutbar, z).getType() == Material.AIR) {
					// Generate strut bar
					for(int xx = 1; xx < 7; xx++)
						c.getBlock(x + xx, yStrutbar, z+0).setType(Material.WOOD);
						
					// Generate strut poles
					for(int yy = yFloor + 1; yy < yStrutbar; yy++) {
						c.getBlock(x+1, yy, z+0).setType(Material.FENCE);
						c.getBlock(x+6, yy, z+0).setType(Material.FENCE);
					}
				}
					
			}
			if (rand.nextInt(100) < CHANCE_STRUT) {
				int yStrutbar = yCeiling - 1;

				if(c.getBlock(x, yStrutbar, z + 2).getType() == Material.AIR) {
					// Generate strut bar
					for(int zz = 1; zz < 7; zz++)
						c.getBlock(x + 0, yStrutbar, z + zz).setType(Material.WOOD);
						
					// Generate strut poles
					for(int yy = yFloor + 1; yy < yStrutbar; yy++) {
						c.getBlock(x+0, yy, z+1).setType(Material.FENCE);
						c.getBlock(x+0, yy, z+6).setType(Material.FENCE);
					}
				}
					
			}
		}
	}
	
	public double distance(int x1, int y1, int x2, int y2) {
		double dx   = x1 - x2;         //horizontal difference 
		double dy   = y1 - y2;         //vertical difference 
		double dist = Math.sqrt( dx*dx + dy*dy ); //distance using Pythagoras theorem
		return dist;
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