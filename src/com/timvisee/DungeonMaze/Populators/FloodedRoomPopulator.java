package com.timvisee.DungeonMaze.Populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class FloodedRoomPopulator extends BlockPopulator {
	public static final int CHANCE_OF_FLOODEDROOM = 5; //Promile
	public static final int CHANCE_OF_WATER = 33; // If it's no water it will be lava

	@Override
	public void populate(World world, Random random, Chunk source) {            
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(7*6); y+=6) {
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if (random.nextInt(1000) < CHANCE_OF_FLOODEDROOM) {
							if(!DungeonMaze.isConstantRoom(world.getName(), source.getX(), source.getZ(), x, y, z)) {
								DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, y, z);
								
								// Get the floor location 
								int yfloorRelative = 0;
								Block roomBottomBlock = source.getBlock(x+2, y, z+2);  // x and z +2 so that you aren't inside a wall!
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  
									yfloorRelative++;
								}
								
								//Walls
								for (int x2=x; x2 <= x + 7; x2+=1) {
								    for (int y2=y + yfloorRelative; y2 <= y + 6; y2+=1) {
								    	if(source.getBlock(x2, y2, z).getTypeId() != 4 && source.getBlock(x2, y2, z).getTypeId() != 48) {
								    		source.getBlock(x2, y2, z).setTypeId(98);
								    	}
								    	if(source.getBlock(x2, y2, z + 7).getTypeId() != 4 && source.getBlock(x2, y2, z + 7).getTypeId() != 48) {
								    		source.getBlock(x2, y2, z + 7).setTypeId(98);
								    	}
								    }
								}
								for (int z2=z; z2 <= z + 7; z2+=1) {
								    for (int y2=y + yfloorRelative; y2 <= y + 6; y2+=1) {
								    	if(source.getBlock(x, y2, z2).getTypeId() != 4 && source.getBlock(x, y2, z2).getTypeId() != 48) {
								    		source.getBlock(x, y2, z2).setTypeId(98);
								    	}
								    	if(source.getBlock(x + 7, y2, z2).getTypeId() != 4 && source.getBlock(x + 7, y2, z2).getTypeId() != 48) {
								    		source.getBlock(x + 7, y2, z2).setTypeId(98);
								    	}
								    }
								}
								
								// Fill the room with lava or water
								if (random.nextInt(100) < CHANCE_OF_WATER) {
									//Water
									for (int x2=x + 1; x2 <= x + 6; x2+=1) {
									    for (int y2=y + yfloorRelative + 1; y2 <= y + 5; y2+=1) {
							    			for (int z2=z + 1; z2 <= z + 6; z2+=1) {
						    					// Fill it with water
						    					source.getBlock(x2, y2, z2).setTypeId(8);
										    }
									    }
									}
								} else {
									//Lava
									for (int x2=x + 1; x2 <= x + 6; x2+=1) {
									    for (int y2=y + yfloorRelative + 1; y2 <= y + 5; y2+=1) {
							    			for (int z2=z + 1; z2 <= z + 6; z2+=1) {
						    					// Fill it with lava
						    					source.getBlock(x2, y2, z2).setTypeId(10);
										    }
									    }
									}
								}
							}
						}
					}
				}
			}
		}
	}
}