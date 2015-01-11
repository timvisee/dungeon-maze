package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class FloodedRoomPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
	public static final int CHANCE_FLOODEDROOM = 5; //Promile
	public static final int CHANCE_WATER = 33; // If it's no water it will be lava

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();
		
		if (rand.nextInt(1000) < CHANCE_FLOODEDROOM) {
			// Register the current room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c.getX(), c.getZ(), x, y, z);
								
			// Walls
			for(int x2=x; x2 <= x + 7; x2+=1) {
			    for(int y2= yFloor; y2 <= y + 6; y2+=1) {
			    	if(c.getBlock(x2, y2, z).getType() != Material.COBBLESTONE && c.getBlock(x2, y2, z).getType() != Material.MOSSY_COBBLESTONE)
			    		c.getBlock(x2, y2, z).setType(Material.SMOOTH_BRICK);
			    	
			    	if(c.getBlock(x2, y2, z + 7).getType() != Material.COBBLESTONE && c.getBlock(x2, y2, z + 7).getType() != Material.MOSSY_COBBLESTONE)
			    		c.getBlock(x2, y2, z + 7).setType(Material.SMOOTH_BRICK);
			    	
			    	if(c.getBlock(x, y2, x2).getType() != Material.COBBLESTONE && c.getBlock(x, y2, x2).getType() != Material.MOSSY_COBBLESTONE)
			    		c.getBlock(x, y2, x2).setType(Material.SMOOTH_BRICK);
			    	
			    	if(c.getBlock(x + 7, y2, x2).getType() != Material.COBBLESTONE && c.getBlock(x + 7, y2, x2).getType() != Material.MOSSY_COBBLESTONE)
			    		c.getBlock(x + 7, y2, x2).setType(Material.SMOOTH_BRICK);
			    }
			}

			// Fill the room with lava or water
			Material type = Material.LAVA;
			if(rand.nextInt(100) < CHANCE_WATER)
				type = Material.WATER;
				
			for (int x2=x + 1; x2 <= x + 6; x2+=1) {
			    for (int y2 = yFloor + 1; y2 <= y + 5; y2+=1) {
	    			for (int z2=z + 1; z2 <= z + 6; z2+=1) {
    					c.getBlock(x2, y2, z2).setType(type);
				    }
			    }
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
}