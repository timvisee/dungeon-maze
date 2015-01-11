package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class WaterWellRoomPopulator extends MazeRoomBlockPopulator {
	public static final int MIN_LAYER = 3;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_OF_WATERWELL = 2; //Promile

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();
		
		// Apply chance
		if (rand.nextInt(1000) < CHANCE_OF_WATERWELL) {
			
			// Register the current room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c.getX(), c.getZ(), x, y, z);
			
			// Floor
			for (int x2=x; x2 <= x + 7; x2+=1)
			    for (int z2=z; z2 <= z + 7; z2+=1)
			        c.getBlock(x2,yFloor, z2).setType(Material.STONE);
			
			// Floor (cobbelstone underneeth the stone floor)
			for (int x2=x; x2 <= x + 7; x2+=1)
			    for (int z2=z; z2 <= z + 7; z2+=1)
			        c.getBlock(x2,yFloor - 1, z2).setType(Material.COBBLESTONE);
			
			// Well
			for (int x2=x + 2; x2 <= x + 4; x2+=1)
			    for (int z2=z + 2; z2 <= z + 4; z2+=1)
			        c.getBlock(x2,yFloor + 1, z2).setType(Material.SMOOTH_BRICK);
			c.getBlock(x + 3,yFloor + 1, z + 3).setType(Material.STATIONARY_WATER);
			
			// Poles
			c.getBlock(x + 2,yFloor + 2, z + 2).setType(Material.FENCE);
			c.getBlock(x + 2,yFloor + 2, z + 4).setType(Material.FENCE);
			c.getBlock(x + 4,yFloor + 2, z + 2).setType(Material.FENCE);
			c.getBlock(x + 4,yFloor + 2, z + 4).setType(Material.FENCE);
			
			// Roof
			c.getBlock(x + 2,yFloor + 3, z + 2).setType(Material.STEP);
			c.getBlock(x + 2,yFloor + 3, z + 2).setData((byte) 2);
			c.getBlock(x + 2,yFloor + 3, z + 3).setType(Material.WOOD_STAIRS);
			c.getBlock(x + 2,yFloor + 3, z + 3).setData((byte) 0);
			c.getBlock(x + 2,yFloor + 3, z + 4).setType(Material.STEP);
			c.getBlock(x + 2,yFloor + 3, z + 4).setData((byte) 2);
			c.getBlock(x + 3,yFloor + 3, z + 2).setType(Material.WOOD_STAIRS);
			c.getBlock(x + 3,yFloor + 3, z + 2).setData((byte) 2); 
			c.getBlock(x + 3,yFloor + 3, z + 3).setType(Material.GLOWSTONE);
			c.getBlock(x + 3,yFloor + 3, z + 4).setType(Material.WOOD_STAIRS);
			c.getBlock(x + 3,yFloor + 3, z + 4).setData((byte) 3);
			c.getBlock(x + 4,yFloor + 3, z + 2).setType(Material.STEP);
			c.getBlock(x + 4,yFloor + 3, z + 2).setData((byte) 2);
			c.getBlock(x + 4,yFloor + 3, z + 3).setType(Material.WOOD_STAIRS);
			c.getBlock(x + 4,yFloor + 3, z + 3).setData((byte) 1);
			c.getBlock(x + 4,yFloor + 3, z + 4).setType(Material.STEP);
			c.getBlock(x + 4,yFloor + 3, z + 4).setData((byte) 2);
		}
	}
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return MIN_LAYER;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return MAX_LAYER;
	}
}