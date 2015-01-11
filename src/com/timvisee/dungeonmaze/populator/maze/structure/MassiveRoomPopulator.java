package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class MassiveRoomPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
	public static final int CHANCE_MASSIVEROOM = 5; //Promile

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int ceilingOffset = args.getCeilingOffset();
		int z = args.getChunkZ();
		
		// Apply chances
		if (rand.nextInt(1000) < CHANCE_MASSIVEROOM) {
			// Register the current room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c, x, y, z);
			
			// Walls
			for (int x2=x; x2 <= x + 7; x2+=1) {
			    for (int y2=yFloor; y2 <= y + 6; y2+=1) {
			    	c.getBlock(x2, y2, z).setType(Material.SMOOTH_BRICK);
			    	c.getBlock(x2, y2, z + 7).setType(Material.SMOOTH_BRICK);
			    }
			}
			for (int z2=z; z2 <= z + 7; z2+=1) {
			    for (int y2=yFloor; y2 <= y + 6; y2+=1) {
			    	c.getBlock(x, y2, z2).setType(Material.SMOOTH_BRICK);
			    	c.getBlock(x + 7, y2, z2).setType(Material.SMOOTH_BRICK);
			    }
			}
			
			// Make the room massive with stone
			for (int x2=x + 1; x2 <= x + 6; x2+=1)
			    for (int y2=yFloor + 1; y2 <= y + 5 + ceilingOffset; y2+=1)
	    			for (int z2=z + 1; z2 <= z + 6; z2+=1)
    					c.getBlock(x2, y2, z2).setType(Material.STONE);
			
			// Fill the massive room with some ores!
			for (int x2=x + 1; x2 <= x + 6; x2+=1) {
			    for (int y2=yFloor + 1; y2 <= y + 5 + ceilingOffset; y2+=1) {
	    			for (int z2=z + 1; z2 <= z + 6; z2+=1) {
	    				if (rand.nextInt(100) < 2) {
	    					switch (rand.nextInt(8))
	    					{
	    					case 0:
	    						c.getBlock(x2, y2, z2).setType(Material.GOLD_ORE);
	    						break;
	    					case 1:
	    						c.getBlock(x2, y2, z2).setType(Material.IRON_ORE);
	    						break;
	    					case 2:
	    						c.getBlock(x2, y2, z2).setType(Material.COAL_ORE);
	    						break;
	    					case 3:
	    						c.getBlock(x2, y2, z2).setType(Material.LAPIS_ORE);
	    						break;
	    					case 4:
	    						c.getBlock(x2, y2, z2).setType(Material.DIAMOND_ORE);
	    						break;
	    					case 5:
	    						c.getBlock(x2, y2, z2).setType(Material.REDSTONE_ORE);
	    						break;
	    					case 6:
	    						c.getBlock(x2, y2, z2).setType(Material.EMERALD_ORE);
	    						break;
	    					case 7:
	    						c.getBlock(x2, y2, z2).setType(Material.CLAY);
	    						break;
	    					case 8:
	    						c.getBlock(x2, y2, z2).setType(Material.COAL_ORE);
	    						break;
	    					default:
	    						c.getBlock(x2, y2, z2).setType(Material.COAL_ORE);
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