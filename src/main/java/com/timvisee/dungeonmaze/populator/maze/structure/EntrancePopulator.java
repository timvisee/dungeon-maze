package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.util.MaterialUtils;

public class EntrancePopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 7;
	private static final int LAYER_MAX = 7;
	private static final int CHANCE_ENTRANCE = 5; // Promile

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk chunk = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getRoomChunkX();
		int y = args.getChunkY();
		int z = args.getRoomChunkZ();
        final Material netherBricks = MaterialUtils.requireBlockMaterial("NETHER_BRICKS", "NETHER_BRICK");
		
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
								setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.STONE_BRICKS);
							else
								setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.AIR);
						}
					}
				}
				
				// Generate ladders in the hole with some randomness for ladders which looks like broken and old ladders
				for(int yy = y; yy < yGround + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						MaterialUtils.setLadderFacing(chunk.getBlock(x + 1, yy, z + 3), BlockFace.EAST);
					}
					if(rand.nextInt(100) < 80) {
						MaterialUtils.setLadderFacing(chunk.getBlock(x + 1, yy, z + 4), BlockFace.EAST);
					}
				}
				
				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++) {
					for(int yy = yGround + 1; yy < yGround + 4; yy++) {
						for(int zz = 0; zz < 8; zz++) {
							setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.AIR);
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
					setGeneratedBlock(chunk.getBlock(x + 1, yFloor, z + 1), Material.OAK_PLANKS);
					setGeneratedBlock(chunk.getBlock(x + 1, yFloor, z + 6), Material.OAK_PLANKS);
					setGeneratedBlock(chunk.getBlock(x + 6, yFloor, z + 1), Material.OAK_PLANKS);
					setGeneratedBlock(chunk.getBlock(x + 6, yFloor, z + 6), Material.OAK_PLANKS);
				}
				for(int yy = yFloor + 1; yy < yGround + 4; yy++) {
					setGeneratedBlock(chunk.getBlock(x + 1, yy, z + 1), Material.OAK_PLANKS);
					setGeneratedBlock(chunk.getBlock(x + 1, yy, z + 6), Material.OAK_PLANKS);
					setGeneratedBlock(chunk.getBlock(x + 6, yy, z + 1), Material.OAK_PLANKS);
					setGeneratedBlock(chunk.getBlock(x + 6, yy, z + 6), Material.OAK_PLANKS);
				}


				// Generate the house on the hole
				//   corners
				for(int yy = yGround + 1; yy < yGround + 4; yy++) {
					setGeneratedBlock(chunk.getBlock(x, yy, z), Material.STONE_BRICKS);
					setGeneratedBlock(chunk.getBlock(x, yy, z + 7), Material.STONE_BRICKS);
					setGeneratedBlock(chunk.getBlock(x + 7, yy, z), Material.STONE_BRICKS);
					setGeneratedBlock(chunk.getBlock(x + 7, yy, z + 7), Material.STONE_BRICKS);
				}

				//   walls
				for(int xx = 1; xx < 7; xx++) {
					for(int yy = yGround + 1; yy < yGround + 4; yy++) {
						setGeneratedBlock(chunk.getBlock(x + xx, yy, z), Material.COBBLESTONE);
						setGeneratedBlock(chunk.getBlock(x + xx, yy, z + 7), Material.COBBLESTONE);
						setGeneratedBlock(chunk.getBlock(x, yy, z + xx), Material.COBBLESTONE);
						setGeneratedBlock(chunk.getBlock(x + 7, yy, z + xx), Material.COBBLESTONE);
					}
				}

				//   ceiling
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						int yy = yGround + 4;
						if(rand.nextInt(100) < 90 || (xx == 0 || xx == 7 || zz == 0 || zz == 7)) {
							setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.STONE_BRICKS);
						}
					}
				}

				//   struct bars
				for(int zz = 1; zz < 7; zz++) {
					int yy = yGround + 3;
					setGeneratedBlock(chunk.getBlock(x + 2, yy, z + zz), Material.OAK_PLANKS);
					setGeneratedBlock(chunk.getBlock(x + 5, yy, z + zz), Material.OAK_PLANKS);
				}

				//   gate
				setGeneratedBlock(chunk.getBlock(x, yGround + 1, z + 2), Material.OAK_FENCE);
				setGeneratedBlock(chunk.getBlock(x, yGround + 1, z + 5), Material.OAK_FENCE);
				setGeneratedBlock(chunk.getBlock(x, yGround + 2, z + 2), Material.OAK_FENCE);
				setGeneratedBlock(chunk.getBlock(x, yGround + 2, z + 5), Material.OAK_FENCE);
				setGeneratedBlock(chunk.getBlock(x, yGround + 1, z + 3), Material.AIR);
				setGeneratedBlock(chunk.getBlock(x, yGround + 1, z + 4), Material.AIR);
				setGeneratedBlock(chunk.getBlock(x, yGround + 2, z + 3), Material.AIR);
				setGeneratedBlock(chunk.getBlock(x, yGround + 2, z + 4), Material.AIR);
				for(int zz = 2; zz < 6; zz++)
					setGeneratedBlock(chunk.getBlock(x, yGround + 3, z + zz), Material.OAK_PLANKS);
				MaterialUtils.setWallTorch(chunk.getBlock(x + 1, yGround + 2, z + 2), BlockFace.EAST);
				MaterialUtils.setWallTorch(chunk.getBlock(x + 1, yGround + 2, z + 5), BlockFace.EAST);

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
								setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.STONE_BRICKS);
							else
								setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.AIR);
						}
					}
				}

				// Generate ladders in the hole with some noise for ladders which looks like broken & old ladders
				for(int yy = y; yy < yGround + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						MaterialUtils.setLadderFacing(chunk.getBlock(x + 3, yy, z + 6), BlockFace.NORTH);
					}
					if(rand.nextInt(100) < 80) {
						MaterialUtils.setLadderFacing(chunk.getBlock(x + 4, yy, z + 6), BlockFace.NORTH);
					}
				}

				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++)
					for(int yy = yGround + 1; yy < yGround + 4; yy++)
						for(int zz = 0; zz < 8; zz++)
							setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.AIR);

				// Generate the house on the hole
				// Corners
				for(int yy = yGround + 1; yy < yGround + 4; yy++) {
					setGeneratedBlock(chunk.getBlock(x, yy, z), Material.STONE_BRICKS);
					setGeneratedBlock(chunk.getBlock(x, yy, z + 7), Material.STONE_BRICKS);
					setGeneratedBlock(chunk.getBlock(x + 7, yy, z), Material.STONE_BRICKS);
					setGeneratedBlock(chunk.getBlock(x + 7, yy, z + 7), Material.STONE_BRICKS);
				}

				// Walls
				for(int xx = 1; xx < 7; xx++) {
					for(int yy = yGround + 1; yy < yGround + 4; yy++) {
						setGeneratedBlock(chunk.getBlock(x + xx, yy, z), Material.COBBLESTONE);
						setGeneratedBlock(chunk.getBlock(x + xx, yy, z + 7), Material.COBBLESTONE);
						setGeneratedBlock(chunk.getBlock(x, yy, z + xx), Material.COBBLESTONE);
						setGeneratedBlock(chunk.getBlock(x + 7, yy, z + xx), Material.COBBLESTONE);
					}
				}

				//   ceiling
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						int yy = yGround + 4;
						if(rand.nextInt(100) < 90 || (xx == 0 || xx == 7 || zz == 0 || zz == 7)) {
							setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.STONE_BRICKS);
						}
					}
				}

				// Struct bars
				for(int xx = 1; xx < 7; xx++) {
					int yy = yGround + 3;
					setGeneratedBlock(chunk.getBlock(x + xx, yy, z + 2), Material.OAK_PLANKS);
					setGeneratedBlock(chunk.getBlock(x + xx, yy, z + 5), Material.OAK_PLANKS);
				}

				// Doors
				setGeneratedBlock(chunk.getBlock(x + 3, yGround + 1, z + 7), Material.AIR);
				setGeneratedBlock(chunk.getBlock(x + 4, yGround + 1, z + 7), Material.AIR);
				setGeneratedBlock(chunk.getBlock(x + 3, yGround + 2, z + 7), Material.AIR);
				setGeneratedBlock(chunk.getBlock(x + 4, yGround + 2, z + 7), Material.AIR);
				MaterialUtils.setWallTorch(chunk.getBlock(x + 2, yGround + 2, z + 6), BlockFace.NORTH);
				MaterialUtils.setWallTorch(chunk.getBlock(x + 5, yGround + 2, z + 6), BlockFace.NORTH);

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
								setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.STONE_BRICKS);
							else
								setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.AIR);
						}
					}
				}

				// Generate ladders in the hole with some noise for ladders which looks like broken & old ladders
				for(int yy = y; yy < yGround + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						MaterialUtils.setLadderFacing(chunk.getBlock(x + 1, yy, z + 3), BlockFace.EAST);
					}
					if(rand.nextInt(100) < 80) {
						MaterialUtils.setLadderFacing(chunk.getBlock(x + 1, yy, z + 4), BlockFace.EAST);
					}
				}

				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++)
					for(int yy = yGround + 1; yy < yGround + 4; yy++)
						for(int zz = 0; zz < 8; zz++)
							setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.AIR);


				// Generate the house on the hole
				//   corners
				for(int yy = yGround + 1; yy < yGround + 4; yy++) {
					setGeneratedBlock(chunk.getBlock(x, yy, z), Material.STONE_BRICKS);
					setGeneratedBlock(chunk.getBlock(x, yy, z + 7), Material.STONE_BRICKS);
					setGeneratedBlock(chunk.getBlock(x + 7, yy, z), Material.STONE_BRICKS);
					setGeneratedBlock(chunk.getBlock(x + 7, yy, z + 7), Material.STONE_BRICKS);
				}

				//   ceiling
				for(int xx = 0; xx < 8; xx++) {
					for(int zz = 0; zz < 8; zz++) {
						int yy = yGround + 4;
						if(xx == 0 || xx == 7 || zz == 0 || zz == 7)
							setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), netherBricks);

                        else if(rand.nextInt(100) < 95) {
							setGeneratedBlock(chunk.getBlock(x + xx, yy + 1, z + zz), Material.STONE_BRICK_SLAB);
						}
					}
				}

				//   struct bars
				for(int xx = 1; xx < 7; xx++) {
					int yy = yGround + 4;
					setGeneratedBlock(chunk.getBlock(x + xx, yy, z + 2), netherBricks);
					setGeneratedBlock(chunk.getBlock(x + xx, yy, z + 5), netherBricks);
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
								setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.STONE_BRICKS);
							else
								setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.AIR);
						}
					}
				}

				// Generate ladders/VINES! in the hole with some noise for ladders which looks like broken & old ladders
				if(rand.nextInt(2) == 0) {
					for(int yy = y; yy < yGround + 1; yy++) {
						if(rand.nextInt(100) < 80) {
							MaterialUtils.setLadderFacing(chunk.getBlock(x + 3, yy, z + 6), BlockFace.NORTH);
						}
						if(rand.nextInt(100) < 80) {
							MaterialUtils.setLadderFacing(chunk.getBlock(x + 4, yy, z + 6), BlockFace.NORTH);
						}
					}
				} else {
					for(int yy = y; yy < yGround + 1; yy++) {
						if(rand.nextInt(100) < 60) {
							MaterialUtils.setVines(chunk.getBlock(x + 3, yy, z + 1), BlockFace.NORTH);
						}
						if(rand.nextInt(100) < 60) {
							MaterialUtils.setVines(chunk.getBlock(x + 4, yy, z + 1), BlockFace.NORTH);
						}
					}
				}


				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++)
					for(int yy = yGround + 1; yy < yGround + 4; yy++)
						for(int zz = 0; zz < 8; zz++)
							setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.AIR);

				// Generate torches on the corners
				//   corners
				setGeneratedBlock(chunk.getBlock(x, yGround + 1, z), Material.TORCH);
				setGeneratedBlock(chunk.getBlock(x, yGround + 1, z + 7), Material.TORCH);
				setGeneratedBlock(chunk.getBlock(x + 7, yGround + 1, z), Material.TORCH);
				setGeneratedBlock(chunk.getBlock(x + 7, yGround + 1, z + 7), Material.TORCH);
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
								setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.STONE_BRICKS);
							else
								setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.AIR);
						}
					}
				}

				// Generate ladders in the hole with some noise for ladders which looks like broken & old ladders
				for(int yy = y; yy < yGround + 1; yy++) {
					if(rand.nextInt(100) < 80) {
						MaterialUtils.setLadderFacing(chunk.getBlock(x + 3, yy, z + 6), BlockFace.NORTH);
					}
					if(rand.nextInt(100) < 80) {
						MaterialUtils.setLadderFacing(chunk.getBlock(x + 4, yy, z + 6), BlockFace.NORTH);
					}
				}

				// Remove all the dirt above the hole
				for(int xx = 0; xx < 8; xx++)
					for(int yy = yGround + 1; yy < yGround + 4; yy++)
						for(int zz = 0; zz < 8; zz++)
							setGeneratedBlock(chunk.getBlock(x + xx, yy, z + zz), Material.AIR);


				// Generate torches on the corners
				setGeneratedBlock(chunk.getBlock(x, yGround + 1, z), Material.TORCH);
				setGeneratedBlock(chunk.getBlock(x, yGround + 1, z + 7), Material.TORCH);
				setGeneratedBlock(chunk.getBlock(x + 7, yGround + 1, z), Material.TORCH);
				setGeneratedBlock(chunk.getBlock(x + 7, yGround + 1, z + 7), Material.TORCH);
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
