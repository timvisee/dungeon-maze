package com.timvisee.DungeonMaze.populator;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class StairsPopulator extends BlockPopulator {
	public static final int CHANCE_OF_STAIR = 2;

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// Go up to y=36 to do only the two lowest layers (otherwise you have a ladder to nothing)
			for (int y=30; y < 30+(6*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							if (random.nextInt(100) < CHANCE_OF_STAIR) {
								int startX = x;
								int startY = y;
								int startZ = z;
								
								// Get the floor location 
								int yfloor = y;
								Block roomBottomBlock = source.getBlock(startX + 4, y, startZ + 4);
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!							
									yfloor++;
								}
								startY = yfloor + 1;
								
								if(!(source.getBlock(startX, startY - 1, startZ).getTypeId() == 0)) {
									source.getBlock(startX + 5, startY, startZ + 2).setTypeId(67);
									source.getBlock(startX + 6, startY, startZ + 2).setTypeId(67);
									source.getBlock(startX + 5, startY + 1, startZ + 3).setTypeId(67);
									source.getBlock(startX + 6, startY + 1, startZ + 3).setTypeId(67);
									source.getBlock(startX + 5, startY + 2, startZ + 4).setTypeId(67);
									source.getBlock(startX + 6, startY + 2, startZ + 4).setTypeId(67);
									source.getBlock(startX + 5, startY + 2, startZ + 5).setTypeId(4);
									source.getBlock(startX + 6, startY + 2, startZ + 5).setTypeId(4);
									source.getBlock(startX + 5, startY + 2, startZ + 6).setTypeId(4);
									source.getBlock(startX + 6, startY + 2, startZ + 6).setTypeId(4);
									source.getBlock(startX + 4, startY + 3, startZ + 5).setTypeId(67);
									source.getBlock(startX + 4, startY + 3, startZ + 6).setTypeId(67);
									source.getBlock(startX + 3, startY + 4, startZ + 5).setTypeId(67);
									source.getBlock(startX + 3, startY + 4, startZ + 6).setTypeId(67);
									source.getBlock(startX + 2, startY + 5, startZ + 5).setTypeId(67);
									source.getBlock(startX + 2, startY + 5, startZ + 6).setTypeId(67);
									
									source.getBlock(startX + 3, startY + 5, startZ + 5).setTypeId(0);
									source.getBlock(startX + 3, startY + 5, startZ + 6).setTypeId(0);
									source.getBlock(startX + 4, startY + 5, startZ + 5).setTypeId(0);
									source.getBlock(startX + 4, startY + 5, startZ + 6).setTypeId(0);
									source.getBlock(startX + 5, startY + 5, startZ + 5).setTypeId(0);
									source.getBlock(startX + 5, startY + 5, startZ + 6).setTypeId(0);
									source.getBlock(startX + 2, startY + 6, startZ + 5).setTypeId(0);
									source.getBlock(startX + 2, startY + 6, startZ + 6).setTypeId(0);
									source.getBlock(startX + 3, startY + 6, startZ + 5).setTypeId(0);
									source.getBlock(startX + 3, startY + 6, startZ + 6).setTypeId(0);
									source.getBlock(startX + 4, startY + 6, startZ + 5).setTypeId(0);
									source.getBlock(startX + 4, startY + 6, startZ + 6).setTypeId(0);
									source.getBlock(startX + 5, startY + 6, startZ + 5).setTypeId(0);
									source.getBlock(startX + 5, startY + 6, startZ + 6).setTypeId(0);
									source.getBlock(startX + 2, startY + 7, startZ + 5).setTypeId(0);
									source.getBlock(startX + 2, startY + 7, startZ + 6).setTypeId(0);
									source.getBlock(startX + 3, startY + 7, startZ + 5).setTypeId(0);
									source.getBlock(startX + 3, startY + 7, startZ + 6).setTypeId(0);
									source.getBlock(startX + 4, startY + 7, startZ + 5).setTypeId(0);
									source.getBlock(startX + 4, startY + 7, startZ + 6).setTypeId(0);
									source.getBlock(startX + 5, startY + 7, startZ + 5).setTypeId(0);
									source.getBlock(startX + 5, startY + 7, startZ + 6).setTypeId(0);
									
									// Set the data value!
									source.getBlock(startX + 5, startY, startZ + 2).setData((byte) 2);
									source.getBlock(startX + 6, startY, startZ + 2).setData((byte) 2);
									source.getBlock(startX + 5, startY + 1, startZ + 3).setData((byte) 2);
									source.getBlock(startX + 6, startY + 1, startZ + 3).setData((byte) 2);
									source.getBlock(startX + 5, startY + 2, startZ + 4).setData((byte) 2);
									source.getBlock(startX + 6, startY + 2, startZ + 4).setData((byte) 2);
									source.getBlock(startX + 4, startY + 3, startZ + 5).setData((byte) 1);
									source.getBlock(startX + 4, startY + 3, startZ + 6).setData((byte) 1);
									source.getBlock(startX + 3, startY + 4, startZ + 5).setData((byte) 1);
									source.getBlock(startX + 3, startY + 4, startZ + 6).setData((byte) 1);
									source.getBlock(startX + 2, startY + 5, startZ + 5).setData((byte) 1);
									source.getBlock(startX + 2, startY + 5, startZ + 6).setData((byte) 1);
			
									source.getBlock(startX + 5, startY + 1, startZ + 4).setTypeId(4);
									source.getBlock(startX + 6, startY + 1, startZ + 4).setTypeId(4);
									source.getBlock(startX + 5, startY + 1, startZ + 5).setTypeId(4);
									source.getBlock(startX + 6, startY + 1, startZ + 5).setTypeId(4);
									source.getBlock(startX + 5, startY + 1, startZ + 6).setTypeId(4);
									source.getBlock(startX + 6, startY + 1, startZ + 6).setTypeId(4);
									source.getBlock(startX + 4, startY + 2, startZ + 5).setTypeId(4);
									source.getBlock(startX + 4, startY + 2, startZ + 6).setTypeId(4);
									source.getBlock(startX + 3, startY + 3, startZ + 5).setTypeId(4);
									source.getBlock(startX + 3, startY + 3, startZ + 6).setTypeId(4);
									source.getBlock(startX + 2, startY + 4, startZ + 5).setTypeId(4);
									source.getBlock(startX + 2, startY + 4, startZ + 6).setTypeId(4);
								}	
							}
						}
							
					}
				}
			}
		}
			
	}
}