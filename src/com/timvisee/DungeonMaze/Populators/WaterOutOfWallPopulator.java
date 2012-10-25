package com.timvisee.DungeonMaze.Populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class WaterOutOfWallPopulator extends BlockPopulator {
	public static final int CHANCE_OF_WATER = 4;
	public static final double CHANCE_OF_WATER_ADDITION_PER_LEVEL = -0.833; /* to 0 */

	@Override
	public void populate(World world, Random random, Chunk source) {
		
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30+(4*6); y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							if (random.nextInt(100) < CHANCE_OF_WATER+(CHANCE_OF_WATER_ADDITION_PER_LEVEL*(y-30)/6)) {
								int lanternX = x + random.nextInt(8);
								int lanternY = y;
								int lanternZ = z + random.nextInt(8);
								
								// Get the floor location 
								int yfloor = y;
								int yfloorRelative = 0;
								Block roomBottomBlock = source.getBlock(lanternX, y, lanternZ);
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!
									yfloor++;
									yfloorRelative = 1;
								}
								lanternY = y + random.nextInt(4 - yfloorRelative) + 2 + yfloorRelative;
								
								Block lanternBlock = source.getBlock(lanternX, lanternY, lanternZ);
								if(lanternBlock.getTypeId() == 4 || lanternBlock.getTypeId() == 48 || lanternBlock.getTypeId() == 98) {
									lanternBlock.setTypeId(10);
								}
							}
						}								
					}
				}
			}
		}
	}
}
