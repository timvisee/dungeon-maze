package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulatorArgs;

public class FloodedRoomPopulator extends DMMazeBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_OF_FLOODEDROOM = 5; //Promile
	public static final int CHANCE_OF_WATER = 33; // If it's no water it will be lava

	@Override
	public void populateMaze(DMMazeBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();
		
		if (rand.nextInt(1000) < CHANCE_OF_FLOODEDROOM) {
			// Register the current room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c.getX(), c.getZ(), x, y, z);
								
			// Walls
			for(int x2=x; x2 <= x + 7; x2+=1) {
			    for(int y2= yFloor; y2 <= y + 6; y2+=1) {
			    	if(c.getBlock(x2, y2, z).getTypeId() != 4 && c.getBlock(x2, y2, z).getTypeId() != 48)
			    		c.getBlock(x2, y2, z).setTypeId(98);
			    	
			    	if(c.getBlock(x2, y2, z + 7).getTypeId() != 4 && c.getBlock(x2, y2, z + 7).getTypeId() != 48)
			    		c.getBlock(x2, y2, z + 7).setTypeId(98);
			    	
			    	if(c.getBlock(x, y2, x2).getTypeId() != 4 && c.getBlock(x, y2, x2).getTypeId() != 48)
			    		c.getBlock(x, y2, x2).setTypeId(98);
			    	
			    	if(c.getBlock(x + 7, y2, x2).getTypeId() != 4 && c.getBlock(x + 7, y2, x2).getTypeId() != 48)
			    		c.getBlock(x + 7, y2, x2).setTypeId(98);
			    }
			}

			// Fill the room with lava or water
			int typeId = 10;
			if(rand.nextInt(100) < CHANCE_OF_WATER)
				typeId = 8;
				
			for (int x2=x + 1; x2 <= x + 6; x2+=1) {
			    for (int y2 = yFloor + 1; y2 <= y + 5; y2+=1) {
	    			for (int z2=z + 1; z2 <= z + 6; z2+=1) {
    					c.getBlock(x2, y2, z2).setTypeId(typeId);
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