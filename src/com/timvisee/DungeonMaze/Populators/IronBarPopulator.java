package com.timvisee.DungeonMaze.Populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class IronBarPopulator extends BlockPopulator {
	public static final int CHANCE = 25;
	public static final int CHANCE_2_HEIGHT = 66;
	public static final int ITERATIONS = 2;

	@Override
	public void populate(World world, Random random, Chunk source) {
		
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30+(0*6); y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							for (int i = 0; i < ITERATIONS; i++) {
								if (random.nextInt(100) < CHANCE) {
									int blockX = x + random.nextInt(8);
									int blockY = y;
									int blockZ = z + random.nextInt(8);
									
									// Get the floor location 
									int yfloor = y;
									int yfloorRelative = 0;
									Block roomBottomBlock = source.getBlock(blockX, y, blockZ);
									int typeId = roomBottomBlock.getTypeId();
									if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!
										yfloor++;
										yfloorRelative = 1;
									}
									blockY = y + random.nextInt(4 - yfloorRelative) + 1 + yfloorRelative;
									
									Block block = source.getBlock(blockX, blockY, blockZ);
									if(block.getTypeId() == 4 || block.getTypeId() == 48 || block.getTypeId() == 98) {
										block.setTypeId(101);
										if(random.nextInt(100) < CHANCE_2_HEIGHT) {
											Block block2 = source.getBlock(blockX, blockY + 1, blockZ);
											block2.setTypeId(101);
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
