package com.timvisee.DungeonMaze.populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class PoolPopulator extends BlockPopulator {
	public static final int NO_LAVA_NEAR_SPAWN_RADIUS = 2; // In chunks
	public static final int POOL_CHANCE = 5; // Includes lava pools
	public static final int LAVA_CHANCE = 35; // Rest is water

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// Set y to 30 to do only the lowest rooms
			int y = 30;
			// The 4 rooms on each layer saved in the variables x and z
			for (int x=0; x < 16; x+=8) {
				for (int z=0; z < 16; z+=8) {
					
					if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
						boolean noLava = false;
						if (Math.abs(source.getX()) < NO_LAVA_NEAR_SPAWN_RADIUS
								|| Math.abs(source.getZ()) < NO_LAVA_NEAR_SPAWN_RADIUS) {
							noLava = true;
						}
						
						if (random.nextInt(100) < POOL_CHANCE) {
							Material poolType = Material.WATER;
		
							if (!noLava && random.nextInt(100) < LAVA_CHANCE) {
								poolType = Material.LAVA;
							}
		
							int poolX = random.nextInt(6) + 1;
							int poolY = y;
							int poolZ = random.nextInt(6) + 1;
							int poolW = random.nextInt(5);
							int poolL = random.nextInt(5);
							
							// Get the floor location 
							int yfloor = y;
							Block roomBottomBlock = source.getBlock(poolX, y, poolZ);
							int typeId = roomBottomBlock.getTypeId();
							if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!							
								yfloor++;
							}
							poolY = yfloor;
		
							for (int i = Math.max(poolX - poolW / 2, 1); i < Math.min(poolX - poolW / 2 + poolW, 6); i++) {
								for (int j = Math.max(poolZ - poolL / 2, 1); j < Math.min(poolZ - poolL / 2 + poolL, 6); j++) {
									source.getBlock(i, poolY, j).setType(poolType);
									source.getBlock(i, poolY - 1, j).setTypeId(48);
								}
							}
						}
					}
						
				}
			}
		}
		
	}
}
