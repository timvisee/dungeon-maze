package com.timvisee.DungeonMaze.Populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class SlabPopulator extends BlockPopulator {
	public static final int CHANCE_OF_SLAB = 50;
	public static final int ITERATIONS = 7;

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							for(int i = 0; i < ITERATIONS; i++) {
								if (random.nextInt(100) < CHANCE_OF_SLAB) {
									int slabX = x + random.nextInt(6) + 1;
									int slabY = y;
									int slabZ = z + random.nextInt(6) + 1;
									
									// Get the floor location 
									int yfloor = y;
									Block roomBottomBlock = source.getBlock(slabX, y, slabZ);
									int typeId = roomBottomBlock.getTypeId();
									if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!
										yfloor++;
									}
									slabY = yfloor + 1;
									
									if(!(source.getBlock(slabX, slabY - 1, slabZ).getTypeId() == 0)) {
										Block slabBlock = source.getBlock(slabX, slabY, slabZ);
										if(slabBlock.getTypeId() == 0) {
											slabBlock.setTypeId(44);
											slabBlock.setData((byte) 3);
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