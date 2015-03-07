package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class EntrancePopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 7;
	public static final int LAYER_MAX = 7;
	public static final int CHANCE_ENTRANCE = 5; // Promile

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk chunk = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		// Apply chances
		if (rand.nextInt(1000) < CHANCE_ENTRANCE) {
							
			int yGround;
			
			// Choose a rand hole look
			switch(rand.nextInt(4)) {
			case 0:
				// Get ground worldHeight
                //noinspection StatementWithEmptyBody
                for(yGround = 100; chunk.getBlock(x, yGround, z + 3).getType() == Material.AIR; yGround--);
				
				// Generate the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						for(int yy = y; yy < yGround + 1; yy++) {
							if(xx == 0 || xx == 7 || zz == 0 || zz == 7)
								chunk.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
							else
								chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
						}
					}
				}
				
				// Generate ladders in the hole with some randomness for ladders which looks like broken and old ladders
				for(int yy = y; yy < yGround + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						chunk.getBlock(x + 1, yy, z + 3).setType(Material.LADDER);
						chunk.getBlock(x + 1, yy, z + 3).setData((byte) 5);
					}
					if(rand.nextInt(100) < 80) {
						chunk.getBlock(x + 1, yy, z + 4).setType(Material.LADDER);
						chunk.getBlock(x + 1, yy, z + 4).setData((byte) 5);
					}
				}
				
				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int yy = yGround + 1; yy < yGround + 4; yy++) {
						for(int zz = 0; zz < 8; zz++) {
							chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
						}
					}
				}
				
				// Get the floor location of the room
				int yFloor = y - 6; /* -6 to start 1 floor lower */
				Block roomBottomBlock = chunk.getBlock(x + 2, y, z + 2);
				Material type = roomBottomBlock.getType();

				if(!(type==Material.COBBLESTONE || type==Material.MOSSY_COBBLESTONE || type==Material.NETHERRACK || type==Material.SOUL_SAND))
					yFloor++;

				// Generate corner poles inside the hole
				if(chunk.getBlock(x + 1, yFloor, z + 1).getType() == Material.AIR) {
					chunk.getBlock(x + 1, yFloor, z + 1).setType(Material.WOOD);
					chunk.getBlock(x + 1, yFloor, z + 6).setType(Material.WOOD);
					chunk.getBlock(x + 6, yFloor, z + 1).setType(Material.WOOD);
					chunk.getBlock(x + 6, yFloor, z + 6).setType(Material.WOOD);
				}
				for(int yy = yFloor + 1; yy < yGround + 4; yy++) {
					chunk.getBlock(x + 1, yy, z + 1).setType(Material.WOOD);
					chunk.getBlock(x + 1, yy, z + 6).setType(Material.WOOD);
					chunk.getBlock(x + 6, yy, z + 1).setType(Material.WOOD);
					chunk.getBlock(x + 6, yy, z + 6).setType(Material.WOOD);
				}


				// Generate the house on the hole
				//   corners
				for(int yy = yGround + 1; yy < yGround + 4; yy++) {
					chunk.getBlock(x, yy, z).setType(Material.SMOOTH_BRICK);
					chunk.getBlock(x, yy, z + 7).setType(Material.SMOOTH_BRICK);
					chunk.getBlock(x + 7, yy, z).setType(Material.SMOOTH_BRICK);
					chunk.getBlock(x + 7, yy, z + 7).setType(Material.SMOOTH_BRICK);
				}

				//   walls
				for(int xx = 1; xx < 7; xx++) {
					for(int yy = yGround + 1; yy < yGround + 4; yy++) {
						chunk.getBlock(x + xx, yy, z).setType(Material.COBBLESTONE);
						chunk.getBlock(x + xx, yy, z + 7).setType(Material.COBBLESTONE);
						chunk.getBlock(x, yy, z + xx).setType(Material.COBBLESTONE);
						chunk.getBlock(x + 7, yy, z + xx).setType(Material.COBBLESTONE);
					}
				}

				//   ceiling
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						int yy = yGround + 4;
						if(rand.nextInt(100) < 90 || (xx == 0 || xx == 7 || zz == 0 || zz == 7)) {
							chunk.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
						}
					}
				}

				//   struct bars
				for(int zz = 1; zz < 7; zz++) {
					int yy = yGround + 3;
					chunk.getBlock(x + 2, yy, z + zz).setType(Material.WOOD);
					chunk.getBlock(x + 5, yy, z + zz).setType(Material.WOOD);
				}

				//   gate
				chunk.getBlock(x, yGround + 1, z + 2).setType(Material.FENCE);
				chunk.getBlock(x, yGround + 1, z + 5).setType(Material.FENCE);
				chunk.getBlock(x, yGround + 2, z + 2).setType(Material.FENCE);
				chunk.getBlock(x, yGround + 2, z + 5).setType(Material.FENCE);
				chunk.getBlock(x, yGround + 1, z + 3).setType(Material.AIR);
				chunk.getBlock(x, yGround + 1, z + 4).setType(Material.AIR);
				chunk.getBlock(x, yGround + 2, z + 3).setType(Material.AIR);
				chunk.getBlock(x, yGround + 2, z + 4).setType(Material.AIR);
				for(int zz = 2; zz < 6; zz++)
					chunk.getBlock(x, yGround + 3, z + zz).setType(Material.WOOD);
				chunk.getBlock(x - 1, yGround + 2, z + 1).setType(Material.TORCH);
				chunk.getBlock(x - 1, yGround + 2, z + 6).setType(Material.TORCH);

				break;

			case 1:

				// Get ground worldHeight
                //noinspection StatementWithEmptyBody
                for(yGround = 100; chunk.getBlock(x + 3, yGround, z + 7).getType() == Material.AIR; yGround--);

				// Generate the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						for(int yy = y; yy < yGround + 1; yy++) {
							if(xx == 0 || xx == 7 || zz == 0 || zz == 7)
								chunk.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
							else
								chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
						}
					}
				}

				// Generate ladders in the hole with some noice for ladders which looks like broken & old ladders
				for(int yy = y; yy < yGround + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						chunk.getBlock(x + 3, yy, z + 6).setType(Material.LADDER);
						chunk.getBlock(x + 3, yy, z + 6).setData((byte) 2);
					}
					if(rand.nextInt(100) < 80) {
						chunk.getBlock(x + 4, yy, z + 6).setType(Material.LADDER);
						chunk.getBlock(x + 4, yy, z + 6).setData((byte) 2);
					}
				}

				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++)
					for(int yy = yGround + 1; yy < yGround + 4; yy++)
						for(int zz = 0; zz < 8; zz++)
							chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);

				// Generate the house on the hole
				//   corners
				for(int yy = yGround + 1; yy < yGround + 4; yy++) {
					chunk.getBlock(x, yy, z).setType(Material.SMOOTH_BRICK);
					chunk.getBlock(x, yy, z + 7).setType(Material.SMOOTH_BRICK);
					chunk.getBlock(x + 7, yy, z).setType(Material.SMOOTH_BRICK);
					chunk.getBlock(x + 7, yy, z + 7).setType(Material.SMOOTH_BRICK);
				}

				//   walls
				for(int xx = 1; xx < 7; xx++) {
					for(int yy = yGround + 1; yy < yGround + 4; yy++) {
						chunk.getBlock(x + xx, yy, z).setType(Material.COBBLESTONE);
						chunk.getBlock(x + xx, yy, z + 7).setType(Material.COBBLESTONE);
						chunk.getBlock(x, yy, z + xx).setType(Material.COBBLESTONE);
						chunk.getBlock(x + 7, yy, z + xx).setType(Material.COBBLESTONE);
					}
				}

				//   ceiling
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						int yy = yGround + 4;
						if(rand.nextInt(100) < 90 || (xx == 0 || xx == 7 || zz == 0 || zz == 7)) {
							chunk.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
						}
					}
				}

				//   struct bars
				for(int xx = 1; xx < 7; xx++) {
					int yy = yGround + 3;
					chunk.getBlock(x + xx, yy, z + 2).setType(Material.WOOD);
					chunk.getBlock(x + xx, yy, z + 5).setType(Material.WOOD);
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
				chunk.getBlock(x + 3, yGround + 1, z + 7).setType(Material.AIR);
				chunk.getBlock(x + 4, yGround + 1, z + 7).setType(Material.AIR);
				chunk.getBlock(x + 3, yGround + 2, z + 7).setType(Material.AIR);
				chunk.getBlock(x + 4, yGround + 2, z + 7).setType(Material.AIR);
				chunk.getBlock(x + 2, yGround + 2, z + 8).setType(Material.TORCH);
				chunk.getBlock(x + 5, yGround + 2, z + 8).setType(Material.TORCH);

				break;

			case 2:

				// Get ground worldHeight
                //noinspection StatementWithEmptyBody
                for(yGround = 100; chunk.getBlock(x + 3, yGround, z + 3).getType() == Material.AIR; yGround--);

				// Generate the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						for(int yy = y; yy < yGround + 1; yy++) {
							if(xx == 0 || xx == 7 || zz == 0 || zz == 7)
								chunk.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
							else
								chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
						}
					}
				}

				// Generate ladders in the hole with some noice for ladders which looks like broken & old ladders
				for(int yy = y; yy < yGround + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						chunk.getBlock(x + 1, yy, z + 3).setType(Material.LADDER);
						chunk.getBlock(x + 1, yy, z + 3).setData((byte) 5);
					}
					if(rand.nextInt(100) < 80) {
						chunk.getBlock(x + 1, yy, z + 4).setType(Material.LADDER);
						chunk.getBlock(x + 1, yy, z + 4).setData((byte) 5);
					}
				}

				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++)
					for(int yy = yGround + 1; yy < yGround + 4; yy++)
						for(int zz = 0; zz < 8; zz++)
							chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);


				// Generate the house on the hole
				//   corners
				for(int yy = yGround + 1; yy < yGround + 4; yy++) {
					chunk.getBlock(x, yy, z).setType(Material.SMOOTH_BRICK);
					chunk.getBlock(x, yy, z + 7).setType(Material.SMOOTH_BRICK);
					chunk.getBlock(x + 7, yy, z).setType(Material.SMOOTH_BRICK);
					chunk.getBlock(x + 7, yy, z + 7).setType(Material.SMOOTH_BRICK);
				}

				//   ceiling
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						int yy = yGround + 4;
						if(xx == 0 || xx == 7 || zz == 0 || zz == 7)
							chunk.getBlock(x + xx, yy, z + zz).setType(Material.NETHER_BRICK);

                        else if(rand.nextInt(100) < 95) {
							chunk.getBlock(x + xx, yy + 1, z + zz).setType(Material.STEP);
							chunk.getBlock(x + xx, yy + 1, z + zz).setData((byte) 5);
						}
					}
				}

				//   struct bars
				for(int xx = 1; xx < 7; xx++) {
					int yy = yGround + 4;
					chunk.getBlock(x + xx, yy, z + 2).setType(Material.NETHER_BRICK);
					chunk.getBlock(x + xx, yy, z + 5).setType(Material.NETHER_BRICK);
				}

				break;

			case 3:
				// Get ground worldHeight
                //noinspection StatementWithEmptyBody
                for(yGround = 100; chunk.getBlock(x + 3, yGround, z + 3).getType() == Material.AIR; yGround--);

				// Generate the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						for(int yy = y; yy < yGround + 1; yy++) {
							if(xx == 0 || xx == 7 || zz == 0 || zz == 7)
								chunk.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
							else
								chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
						}
					}
				}

				// Generate ladders/VINES! in the hole with some noice for ladders which looks like broken & old ladders
				if(rand.nextInt(2) == 0) {
					for(int yy = y; yy < yGround + 1; yy++) {
						if(rand.nextInt(100) < 80) {
							chunk.getBlock(x + 3, yy, z + 6).setType(Material.LADDER);
							chunk.getBlock(x + 3, yy, z + 6).setData((byte) 2);
						}
						if(rand.nextInt(100) < 80) {
							chunk.getBlock(x + 4, yy, z + 6).setType(Material.LADDER);
							chunk.getBlock(x + 4, yy, z + 6).setData((byte) 2);
						}
					}
				} else {
					for(int yy = y; yy < yGround + 1; yy++) {
						if(rand.nextInt(100) < 60) {
							chunk.getBlock(x + 3, yy, z + 1).setType(Material.VINE);
							chunk.getBlock(x + 3, yy, z + 1).setData((byte) 4);
						}
						if(rand.nextInt(100) < 60) {
							chunk.getBlock(x + 4, yy, z + 1).setType(Material.VINE);
							chunk.getBlock(x + 4, yy, z + 1).setData((byte) 4);
						}
					}
				}


				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++)
					for(int yy = yGround + 1; yy < yGround + 4; yy++)
						for(int zz = 0; zz < 8; zz++)
							chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);

				// Generate torches on the corners
				//   corners
				chunk.getBlock(x + 0, yGround + 1, z + 0).setType(Material.TORCH);
				chunk.getBlock(x + 0, yGround + 1, z + 7).setType(Material.TORCH);
				chunk.getBlock(x + 7, yGround + 1, z + 0).setType(Material.TORCH);
				chunk.getBlock(x + 7, yGround + 1, z + 7).setType(Material.TORCH);
				break;

			default:
				// Get ground worldHeight
                //noinspection StatementWithEmptyBody
                for(yGround = 100; chunk.getBlock(x + 3, yGround, z + 3).getType() == Material.AIR; yGround--);

				// Generate the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						for(int yy = y; yy < yGround + 1; yy++) {
							if(xx == 0 || xx == 7 || zz == 0 || zz == 7)
								chunk.getBlock(x + xx, yy, z + zz).setType(Material.SMOOTH_BRICK);
							else
								chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
						}
					}
				}

				// Generate ladders in the hole with some noice for ladders which looks like broken & old ladders
				for(int yy = y; yy < yGround + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						chunk.getBlock(x + 3, yy, z + 6).setType(Material.LADDER);
						chunk.getBlock(x + 3, yy, z + 6).setData((byte) 2);
					}
					if(rand.nextInt(100) < 80) {
						chunk.getBlock(x + 4, yy, z + 6).setType(Material.LADDER);
						chunk.getBlock(x + 4, yy, z + 6).setData((byte) 2);
					}
				}

				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++)
					for(int yy = yGround + 1; yy < yGround + 4; yy++)
						for(int zz = 0; zz < 8; zz++)
							chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);


				// Generate torches on the corners
				chunk.getBlock(x, yGround + 1, z).setType(Material.TORCH);
				chunk.getBlock(x, yGround + 1, z + 7).setType(Material.TORCH);
				chunk.getBlock(x + 7, yGround + 1, z).setType(Material.TORCH);
				chunk.getBlock(x + 7, yGround + 1, z + 7).setType(Material.TORCH);
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