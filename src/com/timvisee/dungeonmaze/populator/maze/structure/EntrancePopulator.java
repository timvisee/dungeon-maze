package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class EntrancePopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 7;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_OF_ENTRANCE = 5; // Promile

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		// Apply chances
		if (rand.nextInt(1000) < CHANCE_OF_ENTRANCE) {
							
			int yground;
			
			// Choose a rand hole look
			switch(rand.nextInt(4)) {
			case 0:
				// Get ground height
				for(yground = 100; c.getBlock(x + 0, yground, z + 3).getType() == Material.AIR; yground--);
				
				// Generate the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						for(int yy = y; yy < yground + 1; yy++) {
							if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
								c.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
							} else {
								c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
							}
						}
					}
				}
				
				// Generate ladders in the hole with some randness for ladders which looks like broken & old ladders
				for(int yy = y; yy < yground + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						c.getBlock(x + 1, yy, z + 3).setType(Material.LADDER);
						c.getBlock(x + 1, yy, z + 3).setData((byte) 5);
					}
					if(rand.nextInt(100) < 80) {
						c.getBlock(x + 1, yy, z + 4).setType(Material.LADDER);
						c.getBlock(x + 1, yy, z + 4).setData((byte) 5);
					}
				}
				
				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int yy = yground + 1; yy < yground + 4; yy++) {
						for(int zz = 0; zz < 8; zz++) {
							c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
						}
					}
				}
				
				// Get the floor location of the room
				int yfloor = y - 6; /* -6 to start 1 floor lower */
				Block roomBottomBlock = c.getBlock(x + 2, y, z + 2);
				Material type = roomBottomBlock.getType();
				if(!(type==Material.COBBLESTONE || type==Material.MOSSY_COBBLESTONE || type==Material.NETHERRACK || type==Material.SOUL_SAND)) {  // x and z +2 so that you aren't inside a wall!							
					yfloor++;
				}
				
				// Generate corner poles inside the hole
				if(c.getBlock(x + 1, yfloor, z + 1).getType() == Material.AIR) {
					c.getBlock(x + 1, yfloor, z + 1).setType(Material.WOOD);
					c.getBlock(x + 1, yfloor, z + 6).setType(Material.WOOD);
					c.getBlock(x + 6, yfloor, z + 1).setType(Material.WOOD);
					c.getBlock(x + 6, yfloor, z + 6).setType(Material.WOOD);
				}
				for(int yy = yfloor + 1; yy < yground + 4; yy++) {
					c.getBlock(x + 1, yy, z + 1).setType(Material.WOOD);
					c.getBlock(x + 1, yy, z + 6).setType(Material.WOOD);
					c.getBlock(x + 6, yy, z + 1).setType(Material.WOOD);
					c.getBlock(x + 6, yy, z + 6).setType(Material.WOOD);
				}
				
				
				// Generate the house on the hole
				//   corners
				for(int yy = yground + 1; yy < yground + 4; yy++) {
					c.getBlock(x + 0, yy, z + 0).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + 0, yy, z + 7).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + 7, yy, z + 0).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + 7, yy, z + 7).setType(Material.SMOOTH_BRICK);
				}
				
				//   walls
				for(int xx = 1; xx < 7; xx++) {
					for(int yy = yground + 1; yy < yground + 4; yy++) {
						c.getBlock(x + xx, yy, z + 0).setType(Material.COBBLESTONE);
						c.getBlock(x + xx, yy, z + 7).setType(Material.COBBLESTONE);
						c.getBlock(x + 0, yy, z + xx).setType(Material.COBBLESTONE);
						c.getBlock(x + 7, yy, z + xx).setType(Material.COBBLESTONE);
					}
				}
				
				//   ceiling
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						int yy = yground + 4;
						if(rand.nextInt(100) < 90 || (xx == 0 || xx == 7 || zz == 0 || zz == 7)) {
							c.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
						}
					}
				}
				
				//   struct bars
				for(int zz = 1; zz < 7; zz++) {
					int yy = yground + 3;
					c.getBlock(x + 2, yy, z + zz).setType(Material.WOOD);
					c.getBlock(x + 5, yy, z + zz).setType(Material.WOOD);
				}
				
				//   gate
				c.getBlock(x + 0, yground + 1, z + 2).setType(Material.FENCE);
				c.getBlock(x + 0, yground + 1, z + 5).setType(Material.FENCE);
				c.getBlock(x + 0, yground + 2, z + 2).setType(Material.FENCE);
				c.getBlock(x + 0, yground + 2, z + 5).setType(Material.FENCE);
				c.getBlock(x + 0, yground + 1, z + 3).setType(Material.AIR);
				c.getBlock(x + 0, yground + 1, z + 4).setType(Material.AIR);
				c.getBlock(x + 0, yground + 2, z + 3).setType(Material.AIR);
				c.getBlock(x + 0, yground + 2, z + 4).setType(Material.AIR);
				for(int zz = 2; zz < 6; zz++) {
					c.getBlock(x + 0, yground + 3, z + zz).setType(Material.WOOD);
				}
				c.getBlock(x - 1, yground + 2, z + 1).setType(Material.TORCH);
				c.getBlock(x - 1, yground + 2, z + 6).setType(Material.TORCH);
				
				break;
				
			case 1:
				
				// Get ground height
				for(yground = 100; c.getBlock(x + 3, yground, z + 7).getType() == Material.AIR; yground--);
				
				// Generate the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						for(int yy = y; yy < yground + 1; yy++) {
							if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
								c.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
							} else {
								c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
							}
						}
					}
				}
				
				// Generate ladders in the hole with some noice for ladders which looks like broken & old ladders
				for(int yy = y; yy < yground + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						c.getBlock(x + 3, yy, z + 6).setType(Material.LADDER);
						c.getBlock(x + 3, yy, z + 6).setData((byte) 2);
					}
					if(rand.nextInt(100) < 80) {
						c.getBlock(x + 4, yy, z + 6).setType(Material.LADDER);
						c.getBlock(x + 4, yy, z + 6).setData((byte) 2);
					}
				}
				
				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int yy = yground + 1; yy < yground + 4; yy++) {
						for(int zz = 0; zz < 8; zz++) {
							c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
						}
					}
				}
				
				// Generate the house on the hole
				//   corners
				for(int yy = yground + 1; yy < yground + 4; yy++) {
					c.getBlock(x + 0, yy, z + 0).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + 0, yy, z + 7).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + 7, yy, z + 0).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + 7, yy, z + 7).setType(Material.SMOOTH_BRICK);
				}
				
				//   walls
				for(int xx = 1; xx < 7; xx++) {
					for(int yy = yground + 1; yy < yground + 4; yy++) {
						c.getBlock(x + xx, yy, z + 0).setType(Material.COBBLESTONE);
						c.getBlock(x + xx, yy, z + 7).setType(Material.COBBLESTONE);
						c.getBlock(x + 0, yy, z + xx).setType(Material.COBBLESTONE);
						c.getBlock(x + 7, yy, z + xx).setType(Material.COBBLESTONE);
					}
				}
				
				//   ceiling
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						int yy = yground + 4;
						if(rand.nextInt(100) < 90 || (xx == 0 || xx == 7 || zz == 0 || zz == 7)) {
							c.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
						}
					}
				}
				
				//   struct bars
				for(int xx = 1; xx < 7; xx++) {
					int yy = yground + 3;
					c.getBlock(x + xx, yy, z + 2).setType(Material.WOOD);
					c.getBlock(x + xx, yy, z + 5).setType(Material.WOOD);
				}
				
				//   doors
				/*c.getBlock(x + 3, y + 10, z + 7).setTypeId(64);
				c.getBlock(x + 3, y + 10, z + 7).setData((byte) 3);
				c.getBlock(x + 4, y + 10, z + 7).setTypeId(64);
				c.getBlock(x + 4, y + 10, z + 7).setData((byte) 3);
				c.getBlock(x + 3, y + 11, z + 7).setTypeId(64);
				c.getBlock(x + 3, y + 11, z + 7).setData((byte) 8);
				c.getBlock(x + 4, y + 11, z + 7).setTypeId(64);
				c.getBlock(x + 4, y + 11, z + 7).setData((byte) 9);*/
				c.getBlock(x + 3, yground + 1, z + 7).setType(Material.AIR);
				c.getBlock(x + 4, yground + 1, z + 7).setType(Material.AIR);
				c.getBlock(x + 3, yground + 2, z + 7).setType(Material.AIR);
				c.getBlock(x + 4, yground + 2, z + 7).setType(Material.AIR);
				c.getBlock(x + 2, yground + 2, z + 8).setType(Material.TORCH);
				c.getBlock(x + 5, yground + 2, z + 8).setType(Material.TORCH);
				
				break;
				
			case 2:
				
				// Get ground height
				for(yground = 100; c.getBlock(x + 3, yground, z + 3).getType() == Material.AIR; yground--);
				
				// Generate the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						for(int yy = y; yy < yground + 1; yy++) {
							if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
								c.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
							} else {
								c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
							}
						}
					}
				}
				
				// Generate ladders in the hole with some noice for ladders which looks like broken & old ladders
				for(int yy = y; yy < yground + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						c.getBlock(x + 1, yy, z + 3).setType(Material.LADDER);
						c.getBlock(x + 1, yy, z + 3).setData((byte) 5);
					}
					if(rand.nextInt(100) < 80) {
						c.getBlock(x + 1, yy, z + 4).setType(Material.LADDER);
						c.getBlock(x + 1, yy, z + 4).setData((byte) 5);
					}
				}
				
				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int yy = yground + 1; yy < yground + 4; yy++) {
						for(int zz = 0; zz < 8; zz++) {
							c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
						}
					}
				}
				
				
				// Generate the house on the hole
				//   corners
				for(int yy = yground + 1; yy < yground + 4; yy++) {
					c.getBlock(x + 0, yy, z + 0).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + 0, yy, z + 7).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + 7, yy, z + 0).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + 7, yy, z + 7).setType(Material.SMOOTH_BRICK);
				}
				
				//   ceiling
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						int yy = yground + 4;
						if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
							c.getBlock(x + xx, yy, z + zz).setType(Material.NETHER_BRICK);
						} else if(rand.nextInt(100) < 95) {
							c.getBlock(x + xx, yy + 1, z + zz).setType(Material.STEP);
							c.getBlock(x + xx, yy + 1, z + zz).setData((byte) 5);
						}
					}
				}
				
				//   struct bars
				for(int xx = 1; xx < 7; xx++) {
					int yy = yground + 4;
					c.getBlock(x + xx, yy, z + 2).setType(Material.NETHER_BRICK);
					c.getBlock(x + xx, yy, z + 5).setType(Material.NETHER_BRICK);
				}
				
				break;
				
			case 3:
				
				// Get ground height
				for(yground = 100; c.getBlock(x + 3, yground, z + 3).getType() == Material.AIR; yground--);
				
				// Generate the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						for(int yy = y; yy < yground + 1; yy++) {
							if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
								c.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
							} else {
								c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
							}
						}
					}
				}
				
				// Generate ladders/VINES! in the hole with some noice for ladders which looks like broken & old ladders
				if(rand.nextInt(2) == 0) {
					for(int yy = y; yy < yground + 1; yy++) {
						if(rand.nextInt(100) < 80) {
							c.getBlock(x + 3, yy, z + 6).setType(Material.LADDER);
							c.getBlock(x + 3, yy, z + 6).setData((byte) 2);
						}
						if(rand.nextInt(100) < 80) {
							c.getBlock(x + 4, yy, z + 6).setType(Material.LADDER);
							c.getBlock(x + 4, yy, z + 6).setData((byte) 2);
						}
					}
				} else {
					for(int yy = y; yy < yground + 1; yy++) {
						if(rand.nextInt(100) < 60) {
							c.getBlock(x + 3, yy, z + 1).setType(Material.VINE);
							c.getBlock(x + 3, yy, z + 1).setData((byte) 4);
						}
						if(rand.nextInt(100) < 60) {
							c.getBlock(x + 4, yy, z + 1).setType(Material.VINE);
							c.getBlock(x + 4, yy, z + 1).setData((byte) 4);
						}
					}
				}
					
				
				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int yy = yground + 1; yy < yground + 4; yy++) {
						for(int zz = 0; zz < 8; zz++) {
							c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
						}
					}
				}
				
				
				// Generate torches on the corners
				//   corners
				c.getBlock(x + 0, yground + 1, z + 0).setType(Material.TORCH);
				c.getBlock(x + 0, yground + 1, z + 7).setType(Material.TORCH);
				c.getBlock(x + 7, yground + 1, z + 0).setType(Material.TORCH);
				c.getBlock(x + 7, yground + 1, z + 7).setType(Material.TORCH);
				
				break;
				
			default:
				
				// Get ground height
				for(yground = 100; c.getBlock(x + 3, yground, z + 3).getType() == Material.AIR; yground--);
				
				// Generate the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						for(int yy = y; yy < yground + 1; yy++) {
							if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
								c.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
							} else {
								c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
							}
						}
					}
				}
				
				// Generate ladders in the hole with some noice for ladders which looks like broken & old ladders
				for(int yy = y; yy < yground + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						c.getBlock(x + 3, yy, z + 6).setType(Material.LADDER);
						c.getBlock(x + 3, yy, z + 6).setData((byte) 2);
					}
					if(rand.nextInt(100) < 80) {
						c.getBlock(x + 4, yy, z + 6).setType(Material.LADDER);
						c.getBlock(x + 4, yy, z + 6).setData((byte) 2);
					}
				}
				
				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int yy = yground + 1; yy < yground + 4; yy++) {
						for(int zz = 0; zz < 8; zz++) {
							c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
						}
					}
				}
				
				
				// Generate torches on the corners
				//   corners
				c.getBlock(x + 0, yground + 1, z + 0).setType(Material.TORCH);
				c.getBlock(x + 0, yground + 1, z + 7).setType(Material.TORCH);
				c.getBlock(x + 7, yground + 1, z + 0).setType(Material.TORCH);
				c.getBlock(x + 7, yground + 1, z + 7).setType(Material.TORCH);
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