package com.timvisee.DungeonMaze.populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class CobblestonePopulator extends BlockPopulator {
	public static final int CHANCE_OF_COBBLE = 20;
	public static final int CORNER_CHANCE = 75;

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							if (random.nextInt(100) < CHANCE_OF_COBBLE) {
								
								int webX = x + random.nextInt(6) + 1;
								int webY = y;
								int webCeilingY = y + 6;
								int webZ = z + random.nextInt(6) + 1;
								
								// Get the floor location 
								int yfloor = y;
								Block roomBottomBlock = source.getBlock(webX, y, webZ);
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!							
									yfloor++;
								}
								webY = yfloor + 1;
								
								int yceiling = y + 6;
								Block roomCeilingBlock = source.getBlock(webX, y + 6, webZ);
								int typeId2 = roomCeilingBlock.getTypeId();
								if(!(typeId2==4 || typeId2==48 || typeId2==87 || typeId2==88)) {  // x and z +2 so that you aren't inside a wall!							
									yceiling++;
								}
								webCeilingY = yceiling - 1;
								
								if (random.nextInt(100) < CORNER_CHANCE) {
									if(source.getBlock(x + (random.nextInt(2)*5), webCeilingY, z + (random.nextInt(2)*5)).getTypeId() == 0) {
										source.getBlock(x + (random.nextInt(2)*5), webCeilingY, z + (random.nextInt(2)*5)).setTypeId(30);
									}
								} else {
									if(!(source.getBlock(webX, webY - 1, webZ).getTypeId() == 0)) {
										if(source.getBlock(webX, webY, webZ).getTypeId() == 0) {
											source.getBlock(webX, webY, webZ).setTypeId(4);
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