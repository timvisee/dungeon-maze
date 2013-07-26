package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulatorArgs;

public class MassiveRoomPopulator extends DMMazeBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_OF_MASSIVEROOM = 5; //Promile

	@Override
	public void populateMaze(DMMazeBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int ceilingOffset = args.getCeilingOffset();
		int z = args.getChunkZ();
		
		// Apply chances
		if (rand.nextInt(1000) < CHANCE_OF_MASSIVEROOM) {
			// Register the current room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c, x, y, z);
			
			// Walls
			for (int x2=x; x2 <= x + 7; x2+=1) {
			    for (int y2=yFloor; y2 <= y + 6; y2+=1) {
			    	c.getBlock(x2, y2, z).setTypeId(98);
			    	c.getBlock(x2, y2, z + 7).setTypeId(98);
			    }
			}
			for (int z2=z; z2 <= z + 7; z2+=1) {
			    for (int y2=yFloor; y2 <= y + 6; y2+=1) {
			    	c.getBlock(x, y2, z2).setTypeId(98);
			    	c.getBlock(x + 7, y2, z2).setTypeId(98);
			    }
			}
			
			// Make the room massive with stone
			for (int x2=x + 1; x2 <= x + 6; x2+=1)
			    for (int y2=yFloor + 1; y2 <= y + 5 + ceilingOffset; y2+=1)
	    			for (int z2=z + 1; z2 <= z + 6; z2+=1)
    					c.getBlock(x2, y2, z2).setTypeId(1);
			
			// Fill the massive room with some ores!
			for (int x2=x + 1; x2 <= x + 6; x2+=1) {
			    for (int y2=yFloor + 1; y2 <= y + 5 + ceilingOffset; y2+=1) {
	    			for (int z2=z + 1; z2 <= z + 6; z2+=1) {
	    				if (rand.nextInt(100) < 2) {
	    					switch (rand.nextInt(8))
	    					{
	    					case 0:
	    						c.getBlock(x2, y2, z2).setTypeId(14);
	    						break;
	    					case 1:
	    						c.getBlock(x2, y2, z2).setTypeId(15);
	    						break;
	    					case 2:
	    						c.getBlock(x2, y2, z2).setTypeId(16);
	    						break;
	    					case 3:
	    						c.getBlock(x2, y2, z2).setTypeId(21);
	    						break;
	    					case 4:
	    						c.getBlock(x2, y2, z2).setTypeId(56);
	    						break;
	    					case 5:
	    						c.getBlock(x2, y2, z2).setTypeId(73);
	    						break;
	    					case 6:
	    						c.getBlock(x2, y2, z2).setTypeId(82);
	    						break;
	    					case 7:
	    						c.getBlock(x2, y2, z2).setTypeId(16);
	    						break;
	    					default:
	    						c.getBlock(x2, y2, z2).setTypeId(16);
	    					}
	    				}
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