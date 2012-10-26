package com.timvisee.DungeonMaze.populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class SanctuaryPopulator extends BlockPopulator {
	public static final int CHANCE_OF_SANCTUARY = 3; //Promile

	@Override
	public void populate(World world, Random random, Chunk source) {
		// Hold the y on 30 because that's only the lowest layer
			
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			for (int x=0; x < 16; x+=8) {
				for (int z=0; z < 16; z+=8) {
					
					if (random.nextInt(1000) < CHANCE_OF_SANCTUARY) {
						DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, 30, z);
						
						// Get the floor location 
						int yfloorRelative = 0;
						Block roomBottomBlock = source.getBlock(x+2, 30, z+2);  // x and z +2 so that you aren't inside a wall!
						int typeId = roomBottomBlock.getTypeId();
						if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  
							yfloorRelative++;
						}
						
						for (int x2=x; x2 < x+8; x2+=1) {
							for (int z2=z; z2 < z+8; z2+=1) {
								source.getBlock(x2, 30 + yfloorRelative, z2).setTypeId(49);
							}
						}
						
						//outline altar right
						source.getBlock(x + 2, 30 + yfloorRelative + 1, z + 2).setTypeId(41);
						source.getBlock(x + 3, 30 + yfloorRelative + 1, z + 2).setTypeId(87);
						source.getBlock(x + 4, 30 + yfloorRelative + 1, z + 2).setTypeId(87);
						source.getBlock(x + 5, 30 + yfloorRelative + 1, z + 2).setTypeId(41);
						//center altar
						source.getBlock(x + 2, 30 + yfloorRelative + 1, z + 3).setTypeId(87);
						source.getBlock(x + 3, 30 + yfloorRelative + 1, z + 3).setTypeId(88);
						source.getBlock(x + 4, 30 + yfloorRelative + 1, z + 3).setTypeId(88);
						source.getBlock(x + 5, 30 + yfloorRelative + 1, z + 3).setTypeId(87);
						//outline altar left
						source.getBlock(x + 2, 30 + yfloorRelative + 1, z + 4).setTypeId(41);
						source.getBlock(x + 3, 30 + yfloorRelative + 1, z + 4).setTypeId(87);
						source.getBlock(x + 4, 30 + yfloorRelative + 1, z + 4).setTypeId(87);
						source.getBlock(x + 5, 30 + yfloorRelative + 1, z + 4).setTypeId(41);
						//torches
						source.getBlock(x + 2, 30 + yfloorRelative + 2, z + 2).setTypeId(50);
						source.getBlock(x + 5, 30 + yfloorRelative + 2, z + 2).setTypeId(50);
						source.getBlock(x + 2, 30 + yfloorRelative + 2, z + 4).setTypeId(50);
						source.getBlock(x + 5, 30 + yfloorRelative + 2, z + 4).setTypeId(50);
					}
				}
			}
		}
	}
}