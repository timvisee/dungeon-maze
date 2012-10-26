package com.timvisee.DungeonMaze.populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class MassiveRoomPopulator extends BlockPopulator {
	public static final int CHANCE_OF_MASSIVEROOM = 5; //Promile

	@Override
	public void populate(World world, Random random, Chunk source) {            
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(7*6); y+=6) {
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if (random.nextInt(1000) < CHANCE_OF_MASSIVEROOM) {
							if(!DungeonMaze.isConstantRoom(world.getName(), source.getX(), source.getZ(), x, y, z)) {
								DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, y, z);
								
								// Get the floor location 
								int yfloorRelative = 0;
								Block roomBottomBlock = source.getBlock(x+2, y, z+2);  // x and z +2 so that you aren't inside a wall!
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  
									yfloorRelative++;
								}
								
								int yceiling = y + 6; int yceilingRelative = 0;
								Block roomCeilingBlock = source.getBlock(x + 2, y + 6, z + 2);
								int typeId2 = roomCeilingBlock.getTypeId();
								if(!(typeId2==4 || typeId2==48 || typeId2==87 || typeId2==88)) {  // x and z +2 so that you aren't inside a wall!							
									yceiling++; yceilingRelative++;
								}
								
								//Walls
								for (int x2=x; x2 <= x + 7; x2+=1) {
								    for (int y2=y + yfloorRelative; y2 <= y + 6; y2+=1) {
								    	source.getBlock(x2, y2, z).setTypeId(98);
								    	source.getBlock(x2, y2, z + 7).setTypeId(98);
								    }
								}
								for (int z2=z; z2 <= z + 7; z2+=1) {
								    for (int y2=y + yfloorRelative; y2 <= y + 6; y2+=1) {
								    	source.getBlock(x, y2, z2).setTypeId(98);
								    	source.getBlock(x + 7, y2, z2).setTypeId(98);
								    }
								}
								
								// Make the room massive with stone
								for (int x2=x + 1; x2 <= x + 6; x2+=1) {
								    for (int y2=y + yfloorRelative + 1; y2 <= y + 5 + yceilingRelative; y2+=1) {
						    			for (int z2=z + 1; z2 <= z + 6; z2+=1) {
					    					// Make the room massive
					    					source.getBlock(x2, y2, z2).setTypeId(1);
									    }
								    }
								}
								
								// Fill the massive room with some ores!
								for (int x2=x + 1; x2 <= x + 6; x2+=1) {
								    for (int y2=y + yfloorRelative + 1; y2 <= y + 5 + yceilingRelative; y2+=1) {
						    			for (int z2=z + 1; z2 <= z + 6; z2+=1) {
						    				if (random.nextInt(100) < 2) {
						    					switch (random.nextInt(8))
						    					{
						    					case 0:
						    						source.getBlock(x2, y2, z2).setTypeId(14);
						    						break;
						    					case 1:
						    						source.getBlock(x2, y2, z2).setTypeId(15);
						    						break;
						    					case 2:
						    						source.getBlock(x2, y2, z2).setTypeId(16);
						    						break;
						    					case 3:
						    						source.getBlock(x2, y2, z2).setTypeId(21);
						    						break;
						    					case 4:
						    						source.getBlock(x2, y2, z2).setTypeId(56);
						    						break;
						    					case 5:
						    						source.getBlock(x2, y2, z2).setTypeId(73);
						    						break;
						    					case 6:
						    						source.getBlock(x2, y2, z2).setTypeId(82);
						    						break;
						    					case 7:
						    						source.getBlock(x2, y2, z2).setTypeId(16);
						    						break;
						    					default:
						    						source.getBlock(x2, y2, z2).setTypeId(16);
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
}