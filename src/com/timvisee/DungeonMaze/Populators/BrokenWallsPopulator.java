package com.timvisee.DungeonMaze.Populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class BrokenWallsPopulator extends BlockPopulator {
	
	public static final int CHANCE_OF_BROKENWALL = 50;
	
	@Override
	public void populate(World world, Random rand, Chunk chunk) {
		if(!DungeonMaze.isConstantChunk(world.getName(), chunk)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						if(!DungeonMaze.isConstantRoom(world.getName(), chunk, x, y, z)) {
							if (rand.nextInt(100) < CHANCE_OF_BROKENWALL) {
								int pointX, pointY, pointZ;
								
								switch (rand.nextInt(2)) {
								case 0:
									pointX = x + (rand.nextInt(2) * 7);
									pointY = y;
									pointZ = z + 1 + rand.nextInt(6);
									break;
								case 1:
									pointX = x + 1 + rand.nextInt(6);
									pointY = y;
									pointZ = z + (rand.nextInt(2) * 7);
									break;
								default:
									pointX = x + (rand.nextInt(2) * 7);
									pointY = y;
									pointZ = z + 1 + rand.nextInt(6);	
								}
								
								chunk.getBlock(pointX, pointY + 1, pointZ).setTypeId(0);
								chunk.getBlock(pointX, pointY + 2, pointZ).setTypeId(0);
							}
						}
					}
				}
			}
		}
			
	}
}