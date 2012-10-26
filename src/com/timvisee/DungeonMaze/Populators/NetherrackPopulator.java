package com.timvisee.DungeonMaze.populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class NetherrackPopulator extends BlockPopulator {
	public static final int ITERATIONS = 15;
	public static final int CHANCE = 5;
	public static final int BURNING_NETHERRACK = 20;
	
	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// do only the first two layers
			for (int y=30; y < 30+(2*6); y+=6) {
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							for (int i = 0; i < ITERATIONS; i++) {
								if (random.nextInt(100) < CHANCE) {
									Block block = source.getBlock(x + random.nextInt(8), random.nextInt(2)+ y, z + random.nextInt(8));
									if (block.getTypeId() == 4) {
										block.setTypeId(87);
										
										if(random.nextInt(100) < BURNING_NETHERRACK) {
											Block burnBlock = source.getBlock(block.getX(), block.getY() + 1, block.getZ());
											if (burnBlock.getTypeId() == 0) {
												burnBlock.setTypeId(51);
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
}