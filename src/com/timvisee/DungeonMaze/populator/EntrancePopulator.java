package com.timvisee.DungeonMaze.populator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class EntrancePopulator extends BlockPopulator {
	public static final int CHANCE_OF_ENTRANCE = 5; // Promile
	public static DungeonMaze plugin;
	
	
	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			int y = 30+(7*6);
				
			// The 4 rooms on each layer saved in the variables x and z
			for (int x=0; x < 16; x+=8) {
				for (int z=0; z < 16; z+=8) {
					
					if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
						if (random.nextInt(1000) < CHANCE_OF_ENTRANCE) {
							
							int yground;
							// Choose a random hole look
							switch(random.nextInt(4)) {
							case 0:
								// Get ground height
								for(yground = 100; source.getBlock(x + 0, yground, z + 3).getType() == Material.AIR; yground--);
								
								// Generate the hole
								for(int xx = 0; xx < 8; xx++) {
									for(int zz = 0; zz < 8; zz++) {
										for(int yy = y; yy < yground + 1; yy++) {
											if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
												source.getBlock(x + xx, yy, z + zz).setTypeId(98);
											} else {
												source.getBlock(x + xx, yy, z + zz).setTypeId(0);
											}
										}
									}
								}
								
								// Generate ladders in the hole with some randomness for ladders which looks like broken & old ladders
								for(int yy = y; yy < yground + 1; yy++) {
									if(random.nextInt(100) < 80) {
										source.getBlock(x + 1, yy, z + 3).setTypeId(65);
										source.getBlock(x + 1, yy, z + 3).setData((byte) 5);
									}
									if(random.nextInt(100) < 80) {
										source.getBlock(x + 1, yy, z + 4).setTypeId(65);
										source.getBlock(x + 1, yy, z + 4).setData((byte) 5);
									}
								}
								
								// Remove all the dirt above the hole
								for(int xx = 0; xx < 8; xx++) {
									for(int yy = yground + 1; yy < yground + 4; yy++) {
										for(int zz = 0; zz < 8; zz++) {
											source.getBlock(x + xx, yy, z + zz).setTypeId(0);
										}
									}
								}
								
								// Get the floor location of the room
								int yfloor = y - 6; /* -6 to start 1 floor lower */
								Block roomBottomBlock = source.getBlock(x + 2, y, z + 2);
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!							
									yfloor++;
								}
								
								// Generate corner poles inside the hole
								if(source.getBlock(x + 1, yfloor, z + 1).getTypeId() == 0) {
									source.getBlock(x + 1, yfloor, z + 1).setTypeId(5);
									source.getBlock(x + 1, yfloor, z + 6).setTypeId(5);
									source.getBlock(x + 6, yfloor, z + 1).setTypeId(5);
									source.getBlock(x + 6, yfloor, z + 6).setTypeId(5);
								}
								for(int yy = yfloor + 1; yy < yground + 4; yy++) {
									source.getBlock(x + 1, yy, z + 1).setTypeId(5);
									source.getBlock(x + 1, yy, z + 6).setTypeId(5);
									source.getBlock(x + 6, yy, z + 1).setTypeId(5);
									source.getBlock(x + 6, yy, z + 6).setTypeId(5);
								}
								
								
								// Generate the house on the hole
								//   corners
								for(int yy = yground + 1; yy < yground + 4; yy++) {
									source.getBlock(x + 0, yy, z + 0).setTypeId(98);
									source.getBlock(x + 0, yy, z + 7).setTypeId(98);
									source.getBlock(x + 7, yy, z + 0).setTypeId(98);
									source.getBlock(x + 7, yy, z + 7).setTypeId(98);
								}
								
								//   walls
								for(int xx = 1; xx < 7; xx++) {
									for(int yy = yground + 1; yy < yground + 4; yy++) {
										source.getBlock(x + xx, yy, z + 0).setTypeId(4);
										source.getBlock(x + xx, yy, z + 7).setTypeId(4);
										source.getBlock(x + 0, yy, z + xx).setTypeId(4);
										source.getBlock(x + 7, yy, z + xx).setTypeId(4);
									}
								}
								
								//   ceiling
								for(int xx = 0; xx < 8; xx++) {
									for(int zz = 0; zz < 8; zz++) {
										int yy = yground + 4;
										if(random.nextInt(100) < 90 || (xx == 0 || xx == 7 || zz == 0 || zz == 7)) {
											source.getBlock(x + xx, yy, z + zz).setTypeId(98);
										}
									}
								}
								
								//   struct bars
								for(int zz = 1; zz < 7; zz++) {
									int yy = yground + 3;
									source.getBlock(x + 2, yy, z + zz).setTypeId(5);
									source.getBlock(x + 5, yy, z + zz).setTypeId(5);
								}
								
								//   gate
								source.getBlock(x + 0, yground + 1, z + 2).setTypeId(85);
								source.getBlock(x + 0, yground + 1, z + 5).setTypeId(85);
								source.getBlock(x + 0, yground + 2, z + 2).setTypeId(85);
								source.getBlock(x + 0, yground + 2, z + 5).setTypeId(85);
								source.getBlock(x + 0, yground + 1, z + 3).setTypeId(0);
								source.getBlock(x + 0, yground + 1, z + 4).setTypeId(0);
								source.getBlock(x + 0, yground + 2, z + 3).setTypeId(0);
								source.getBlock(x + 0, yground + 2, z + 4).setTypeId(0);
								for(int zz = 2; zz < 6; zz++) {
									source.getBlock(x + 0, yground + 3, z + zz).setTypeId(5);
								}
								source.getBlock(x - 1, yground + 2, z + 1).setTypeId(50);
								source.getBlock(x - 1, yground + 2, z + 6).setTypeId(50);
								
								break;
								
							case 1:
								
								// Get ground height
								for(yground = 100; source.getBlock(x + 3, yground, z + 7).getType() == Material.AIR; yground--);
								
								// Generate the hole
								for(int xx = 0; xx < 8; xx++) {
									for(int zz = 0; zz < 8; zz++) {
										for(int yy = y; yy < yground + 1; yy++) {
											if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
												source.getBlock(x + xx, yy, z + zz).setTypeId(98);
											} else {
												source.getBlock(x + xx, yy, z + zz).setTypeId(0);
											}
										}
									}
								}
								
								// Generate ladders in the hole with some noice for ladders which looks like broken & old ladders
								for(int yy = y; yy < yground + 1; yy++) {
									if(random.nextInt(100) < 80) {
										source.getBlock(x + 3, yy, z + 6).setTypeId(65);
										source.getBlock(x + 3, yy, z + 6).setData((byte) 2);
									}
									if(random.nextInt(100) < 80) {
										source.getBlock(x + 4, yy, z + 6).setTypeId(65);
										source.getBlock(x + 4, yy, z + 6).setData((byte) 2);
									}
								}
								
								// Remove all the dirt above the hole
								for(int xx = 0; xx < 8; xx++) {
									for(int yy = yground + 1; yy < yground + 4; yy++) {
										for(int zz = 0; zz < 8; zz++) {
											source.getBlock(x + xx, yy, z + zz).setTypeId(0);
										}
									}
								}
								
								// Generate the house on the hole
								//   corners
								for(int yy = yground + 1; yy < yground + 4; yy++) {
									source.getBlock(x + 0, yy, z + 0).setTypeId(98);
									source.getBlock(x + 0, yy, z + 7).setTypeId(98);
									source.getBlock(x + 7, yy, z + 0).setTypeId(98);
									source.getBlock(x + 7, yy, z + 7).setTypeId(98);
								}
								
								//   walls
								for(int xx = 1; xx < 7; xx++) {
									for(int yy = yground + 1; yy < yground + 4; yy++) {
										source.getBlock(x + xx, yy, z + 0).setTypeId(4);
										source.getBlock(x + xx, yy, z + 7).setTypeId(4);
										source.getBlock(x + 0, yy, z + xx).setTypeId(4);
										source.getBlock(x + 7, yy, z + xx).setTypeId(4);
									}
								}
								
								//   ceiling
								for(int xx = 0; xx < 8; xx++) {
									for(int zz = 0; zz < 8; zz++) {
										int yy = yground + 4;
										if(random.nextInt(100) < 90 || (xx == 0 || xx == 7 || zz == 0 || zz == 7)) {
											source.getBlock(x + xx, yy, z + zz).setTypeId(98);
										}
									}
								}
								
								//   struct bars
								for(int xx = 1; xx < 7; xx++) {
									int yy = yground + 3;
									source.getBlock(x + xx, yy, z + 2).setTypeId(5);
									source.getBlock(x + xx, yy, z + 5).setTypeId(5);
								}
								
								//   doors
								/*source.getBlock(x + 3, y + 10, z + 7).setTypeId(64);
								source.getBlock(x + 3, y + 10, z + 7).setData((byte) 3);
								source.getBlock(x + 4, y + 10, z + 7).setTypeId(64);
								source.getBlock(x + 4, y + 10, z + 7).setData((byte) 3);
								source.getBlock(x + 3, y + 11, z + 7).setTypeId(64);
								source.getBlock(x + 3, y + 11, z + 7).setData((byte) 8);
								source.getBlock(x + 4, y + 11, z + 7).setTypeId(64);
								source.getBlock(x + 4, y + 11, z + 7).setData((byte) 9);*/
								source.getBlock(x + 3, yground + 1, z + 7).setTypeId(0);
								source.getBlock(x + 4, yground + 1, z + 7).setTypeId(0);
								source.getBlock(x + 3, yground + 2, z + 7).setTypeId(0);
								source.getBlock(x + 4, yground + 2, z + 7).setTypeId(0);
								source.getBlock(x + 2, yground + 2, z + 8).setTypeId(50);
								source.getBlock(x + 5, yground + 2, z + 8).setTypeId(50);
								
								break;
								
							case 2:
								
								// Get ground height
								for(yground = 100; source.getBlock(x + 3, yground, z + 3).getType() == Material.AIR; yground--);
								
								// Generate the hole
								for(int xx = 0; xx < 8; xx++) {
									for(int zz = 0; zz < 8; zz++) {
										for(int yy = y; yy < yground + 1; yy++) {
											if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
												source.getBlock(x + xx, yy, z + zz).setTypeId(98);
											} else {
												source.getBlock(x + xx, yy, z + zz).setTypeId(0);
											}
										}
									}
								}
								
								// Generate ladders in the hole with some noice for ladders which looks like broken & old ladders
								for(int yy = y; yy < yground + 1; yy++) {
									if(random.nextInt(100) < 80) {
										source.getBlock(x + 1, yy, z + 3).setTypeId(65);
										source.getBlock(x + 1, yy, z + 3).setData((byte) 5);
									}
									if(random.nextInt(100) < 80) {
										source.getBlock(x + 1, yy, z + 4).setTypeId(65);
										source.getBlock(x + 1, yy, z + 4).setData((byte) 5);
									}
								}
								
								// Remove all the dirt above the hole
								for(int xx = 0; xx < 8; xx++) {
									for(int yy = yground + 1; yy < yground + 4; yy++) {
										for(int zz = 0; zz < 8; zz++) {
											source.getBlock(x + xx, yy, z + zz).setTypeId(0);
										}
									}
								}
								
								
								// Generate the house on the hole
								//   corners
								for(int yy = yground + 1; yy < yground + 4; yy++) {
									source.getBlock(x + 0, yy, z + 0).setTypeId(98);
									source.getBlock(x + 0, yy, z + 7).setTypeId(98);
									source.getBlock(x + 7, yy, z + 0).setTypeId(98);
									source.getBlock(x + 7, yy, z + 7).setTypeId(98);
								}
								
								//   ceiling
								for(int xx = 0; xx < 8; xx++) {
									for(int zz = 0; zz < 8; zz++) {
										int yy = yground + 4;
										if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
											source.getBlock(x + xx, yy, z + zz).setTypeId(112);
										} else if(random.nextInt(100) < 95) {
											source.getBlock(x + xx, yy + 1, z + zz).setTypeId(44);
											source.getBlock(x + xx, yy + 1, z + zz).setData((byte) 5);
										}
									}
								}
								
								//   struct bars
								for(int xx = 1; xx < 7; xx++) {
									int yy = yground + 4;
									source.getBlock(x + xx, yy, z + 2).setTypeId(112);
									source.getBlock(x + xx, yy, z + 5).setTypeId(112);
								}
								
								break;
								
							case 3:
								
								// Get ground height
								for(yground = 100; source.getBlock(x + 3, yground, z + 3).getType() == Material.AIR; yground--);
								
								// Generate the hole
								for(int xx = 0; xx < 8; xx++) {
									for(int zz = 0; zz < 8; zz++) {
										for(int yy = y; yy < yground + 1; yy++) {
											if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
												source.getBlock(x + xx, yy, z + zz).setTypeId(98);
											} else {
												source.getBlock(x + xx, yy, z + zz).setTypeId(0);
											}
										}
									}
								}
								
								// Generate ladders/VINES! in the hole with some noice for ladders which looks like broken & old ladders
								if(random.nextInt(2) == 0) {
									for(int yy = y; yy < yground + 1; yy++) {
										if(random.nextInt(100) < 80) {
											source.getBlock(x + 3, yy, z + 6).setTypeId(65);
											source.getBlock(x + 3, yy, z + 6).setData((byte) 2);
										}
										if(random.nextInt(100) < 80) {
											source.getBlock(x + 4, yy, z + 6).setTypeId(65);
											source.getBlock(x + 4, yy, z + 6).setData((byte) 2);
										}
									}
								} else {
									for(int yy = y; yy < yground + 1; yy++) {
										if(random.nextInt(100) < 60) {
											source.getBlock(x + 3, yy, z + 1).setTypeId(106);
											source.getBlock(x + 3, yy, z + 1).setData((byte) 4);
										}
										if(random.nextInt(100) < 60) {
											source.getBlock(x + 4, yy, z + 1).setTypeId(106);
											source.getBlock(x + 4, yy, z + 1).setData((byte) 4);
										}
									}
								}
									
								
								// Remove all the dirt above the hole
								for(int xx = 0; xx < 8; xx++) {
									for(int yy = yground + 1; yy < yground + 4; yy++) {
										for(int zz = 0; zz < 8; zz++) {
											source.getBlock(x + xx, yy, z + zz).setTypeId(0);
										}
									}
								}
								
								
								// Generate torches on the corners
								//   corners
								source.getBlock(x + 0, yground + 1, z + 0).setTypeId(50);
								source.getBlock(x + 0, yground + 1, z + 7).setTypeId(50);
								source.getBlock(x + 7, yground + 1, z + 0).setTypeId(50);
								source.getBlock(x + 7, yground + 1, z + 7).setTypeId(50);
								
								break;
								
							default:
								
								// Get ground height
								for(yground = 100; source.getBlock(x + 3, yground, z + 3).getType() == Material.AIR; yground--);
								
								// Generate the hole
								for(int xx = 0; xx < 8; xx++) {
									for(int zz = 0; zz < 8; zz++) {
										for(int yy = y; yy < yground + 1; yy++) {
											if(xx == 0 || xx == 7 || zz == 0 || zz == 7) {
												source.getBlock(x + xx, yy, z + zz).setTypeId(98);
											} else {
												source.getBlock(x + xx, yy, z + zz).setTypeId(0);
											}
										}
									}
								}
								
								// Generate ladders in the hole with some noice for ladders which looks like broken & old ladders
								for(int yy = y; yy < yground + 1; yy++) {
									if(random.nextInt(100) < 80) {
										source.getBlock(x + 3, yy, z + 6).setTypeId(65);
										source.getBlock(x + 3, yy, z + 6).setData((byte) 2);
									}
									if(random.nextInt(100) < 80) {
										source.getBlock(x + 4, yy, z + 6).setTypeId(65);
										source.getBlock(x + 4, yy, z + 6).setData((byte) 2);
									}
								}
								
								// Remove all the dirt above the hole
								for(int xx = 0; xx < 8; xx++) {
									for(int yy = yground + 1; yy < yground + 4; yy++) {
										for(int zz = 0; zz < 8; zz++) {
											source.getBlock(x + xx, yy, z + zz).setTypeId(0);
										}
									}
								}
								
								
								// Generate torches on the corners
								//   corners
								source.getBlock(x + 0, yground + 1, z + 0).setTypeId(50);
								source.getBlock(x + 0, yground + 1, z + 7).setTypeId(50);
								source.getBlock(x + 7, yground + 1, z + 0).setTypeId(50);
								source.getBlock(x + 7, yground + 1, z + 7).setTypeId(50);
							}				
						}
					}	
				}
			}
		}
	}
}