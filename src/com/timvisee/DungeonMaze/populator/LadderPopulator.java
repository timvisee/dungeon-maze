package com.timvisee.DungeonMaze.populator;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class LadderPopulator extends BlockPopulator {
	public static final int CHANCE_OF_LADDER = 5;

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// Go up to y=54 to do only the four lowest layers (otherwise you have a ladder to nothing)
			for (int y=30; y < 30+(6*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							if (random.nextInt(100) < CHANCE_OF_LADDER) {
								int startX = x;
								int startY = y;
								int startZ = z;
								
								byte ladderData = (byte) 0;
								switch (random.nextInt(2)) {
								case 0:
									int r = random.nextInt(2);
									startX = x + 1 + (r*5);
									startZ = z + random.nextInt(2)*7;
									if(r == 0) {
										ladderData = (byte) 5; // North
									} else {
										ladderData = (byte) 4; // South
									}
									break;
								case 1:
									int r2 = random.nextInt(2);
									startX = x + random.nextInt(2)*7;
									startZ = z + 1 + (r2*5);
									if(r2 == 0) {
										ladderData = (byte) 3; // East
									} else {
										ladderData = (byte) 2; // West
									}
									break;
								default:
									startX = x + 1 + (random.nextInt(2)*5);
									startZ = z + random.nextInt(2)*7;
								}
								
								// Get the floor location 
								int yfloor = y;
								Block roomBottomBlock = source.getBlock(startX, y, startZ);
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!							
									yfloor++;
								}
								startY = yfloor + 1;
								
								// Check if here isn't a wall or something
								if(source.getBlock(startX, startY, startZ).getTypeId() == 0) {
									
									for (int ladderY=startY; ladderY <= startY + 8; ladderY++) {
										source.getBlock(startX, ladderY, startZ).setTypeId(65);
										source.getBlock(startX, ladderY, startZ).setData(ladderData);
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