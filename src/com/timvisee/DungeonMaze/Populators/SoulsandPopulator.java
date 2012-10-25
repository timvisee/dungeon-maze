package com.timvisee.DungeonMaze.Populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class SoulsandPopulator extends BlockPopulator {
	public static final int CHANCE = 20;
	public static final double CHANCE_ADDITION_PER_LEVEL = -1.667; /* to 10 */
	public static final int ITERATIONS = 15;

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y (go up to y=36 so do only the two lowest layers
			for(int y=30+(2*6); y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							for (int i = 0; i < ITERATIONS; i++) {
								if (random.nextInt(100) < CHANCE+(CHANCE_ADDITION_PER_LEVEL*(y-30)/6)) {
									Block block = source.getBlock(x + random.nextInt(8), random.nextInt(2) + y, z + random.nextInt(8));
									if (block.getTypeId() == 4) {
										block.setTypeId(88);
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
