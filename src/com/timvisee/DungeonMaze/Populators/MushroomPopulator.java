package com.timvisee.DungeonMaze.populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class MushroomPopulator extends BlockPopulator {
	public static final int CHANCE_OF_BROWN_MUSHROOM = 1;
	public static final int CHANCE_OF_RED_MUSHROOM = 1;

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(6*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							if (random.nextInt(100) < CHANCE_OF_BROWN_MUSHROOM) {
								int spawnerX = x + random.nextInt(6) + 1;
								int spawnerY = y;
								int spawnerZ = z + random.nextInt(6) + 1;
								
								// Get the floor location 
								int yfloor = y;
								Block roomBottomBlock = source.getBlock(spawnerX, y, spawnerZ);
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!							
									yfloor++;
								}
								spawnerY = yfloor + 1;
								
								if(!(source.getBlock(spawnerX, spawnerY - 1, spawnerZ).getTypeId() == 0)) {
									Block spawnerBlock = source.getBlock(spawnerX, spawnerY, spawnerZ);
									spawnerBlock = source.getBlock(spawnerX, spawnerY, spawnerZ);
									if(spawnerBlock.getTypeId() == 0) {
										spawnerBlock.setTypeId(39);
									}
								}
							}
		
							if (random.nextInt(100) < CHANCE_OF_RED_MUSHROOM) {
								int spawnerX = x + random.nextInt(6) + 1;
								int spawnerY = y;
								int spawnerZ = z + random.nextInt(6) + 1;
								
								// Get the floor location 
								int yfloor = y;
								Block roomBottomBlock = source.getBlock(spawnerX, y, spawnerZ);
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!							
									yfloor++;
								}
								spawnerY = yfloor + 1;
								
								if(!(source.getBlock(spawnerX, spawnerY - 1, spawnerZ).getTypeId() == 0)) {
									Block mushroomBlock = source.getBlock(spawnerX, spawnerY, spawnerZ);
									mushroomBlock = source.getBlock(spawnerX, spawnerY, spawnerZ);
									if(mushroomBlock.getTypeId() == 0) {
										mushroomBlock.setTypeId(40);
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