package com.timvisee.DungeonMaze.Populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class CoalorePopulator extends BlockPopulator {
	public static final int ITERATIONS = 5;
	public static final int CHANCE = 2;

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y (up to 4 layers)
			for (int y=30; y < 30+(6*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							for (int i = 0; i < ITERATIONS; i++) {
								if (random.nextInt(100) < CHANCE) {
									Block block = source.getBlock(x + random.nextInt(8), random.nextInt((y + 6) - y + 1) + y, z + random.nextInt(8));
									if (block.getTypeId() == 4) {
										block.setTypeId(16);
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
