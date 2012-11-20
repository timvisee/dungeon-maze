package com.timvisee.DungeonMaze.populator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class StrutPopulator extends BlockPopulator {
	public static final int CHANCE_OF_STRUT = 2;
	public static final int CHANCE_OF_STRUT_NEAR_SPAWN = 50;
	public static final int MAX_STRUT_DISTANCE_NEAR_SPAWN = 1; // Distance in chunks
	public static DungeonMaze plugin;
	
	
	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							if(distance(source.getX(), source.getZ(), 0, 0) > MAX_STRUT_DISTANCE_NEAR_SPAWN) {
								
								// Normal strut
								if (random.nextInt(100) < CHANCE_OF_STRUT) {
									// Get the floor location 
									int yfloor = y;
									Block roomBottomBlock = source.getBlock(x + 2, y, z + 2);
									int typeId = roomBottomBlock.getTypeId();
									if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!
										yfloor++;
									}
									
									int yceiling = y + 6;
									Block roomCeilingBlock = source.getBlock(x + 2, y + 6, y + 2);
									int typeId2 = roomCeilingBlock.getTypeId();
									if(!(typeId2==4 || typeId2==48 || typeId2==87 || typeId2==88)) {  // x and z +2 so that you aren't inside a wall!							
										yceiling++;
									}
			
									int ystrutbar = yceiling - 1;
									
									if(source.getBlock(x + 2, ystrutbar, z).getTypeId() == 0) {
										// Generate strut bar
										for(int xx = 1; xx < 7; xx++) {
											source.getBlock(x + xx, ystrutbar, z+0).setTypeId(5);
										}
										// Generate strut poles
										for(int yy = yfloor + 1; yy < ystrutbar; yy++) {
											source.getBlock(x+1, yy, z+0).setTypeId(85);
											source.getBlock(x+6, yy, z+0).setTypeId(85);
										}
									}
										
								}
								if (random.nextInt(100) < CHANCE_OF_STRUT) {
									// Get the floor location 
									int yfloor = y;
									Block roomBottomBlock = source.getBlock(x + 2, y, z + 2);
									int typeId = roomBottomBlock.getTypeId();
									if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!
										yfloor++;
									}
									
									int yceiling = y + 6;
									Block roomCeilingBlock = source.getBlock(x + 2, y + 6, y + 2);
									int typeId2 = roomCeilingBlock.getTypeId();
									if(!(typeId2==4 || typeId2==48 || typeId2==87 || typeId2==88)) {  // x and z +2 so that you aren't inside a wall!							
										yceiling++;
									}
			
									int ystrutbar = yceiling - 1;

									if(source.getBlock(x, ystrutbar, z + 2).getTypeId() == 0) {
										// Generate strut bar
										for(int zz = 1; zz < 7; zz++) {
											source.getBlock(x + 0, ystrutbar, z + zz).setTypeId(5);
										}
										// Generate strut poles
										for(int yy = yfloor + 1; yy < ystrutbar; yy++) {
											source.getBlock(x+0, yy, z+1).setTypeId(85);
											source.getBlock(x+0, yy, z+6).setTypeId(85);
										}
									}
										
								}
							} else {
								
								// Strut near spawn
								if (random.nextInt(100) < CHANCE_OF_STRUT_NEAR_SPAWN) {
									// Get the floor location 
									int yfloor = y;
									Block roomBottomBlock = source.getBlock(x + 2, y, z + 2);
									int typeId = roomBottomBlock.getTypeId();
									if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!
										yfloor++;
									}
									
									int yceiling = y + 6;
									Block roomCeilingBlock = source.getBlock(x + 2, y + 6, y + 2);
									int typeId2 = roomCeilingBlock.getTypeId();
									if(!(typeId2==4 || typeId2==48 || typeId2==87 || typeId2==88)) {  // x and z +2 so that you aren't inside a wall!							
										yceiling++;
									}
			
									int ystrutbar = yceiling - 1;

									if(source.getBlock(x + 2, ystrutbar, z).getTypeId() == 0) {
										// Generate strut bar
										for(int xx = 1; xx < 7; xx++) {
											source.getBlock(x + xx, ystrutbar, z+0).setTypeId(5);
										}
										// Generate strut poles
										for(int yy = yfloor + 1; yy < ystrutbar; yy++) {
											source.getBlock(x+1, yy, z+0).setTypeId(85);
											source.getBlock(x+6, yy, z+0).setTypeId(85);
										}
									}
										
								}
								if (random.nextInt(100) < CHANCE_OF_STRUT_NEAR_SPAWN) {
									// Get the floor location 
									int yfloor = y;
									Block roomBottomBlock = source.getBlock(x + 2, y, z + 2);
									int typeId = roomBottomBlock.getTypeId();
									if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!
										yfloor++;
									}
									
									int yceiling = y + 6;
									Block roomCeilingBlock = source.getBlock(x + 2, y + 6, y + 2);
									int typeId2 = roomCeilingBlock.getTypeId();
									if(!(typeId2==4 || typeId2==48 || typeId2==87 || typeId2==88)) {  // x and z +2 so that you aren't inside a wall!							
										yceiling++;
									}
			
									int ystrutbar = yceiling - 1;

									if(source.getBlock(x, ystrutbar, z + 2).getTypeId() == 0) {
										// Generate strut bar
										for(int zz = 1; zz < 7; zz++) {
											source.getBlock(x + 0, ystrutbar, z + zz).setTypeId(5);
										}
										// Generate strut poles
										for(int yy = yfloor + 1; yy < ystrutbar; yy++) {
											source.getBlock(x+0, yy, z+1).setTypeId(85);
											source.getBlock(x+0, yy, z+6).setTypeId(85);
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
	
	public double distance(int x1, int y1, int x2, int y2)
	{
		double dx   = x1 - x2;         //horizontal difference 
		double dy   = y1 - y2;         //vertical difference 
		double dist = Math.sqrt( dx*dx + dy*dy ); //distance using Pythagoras theorem
		return dist;
	}
}