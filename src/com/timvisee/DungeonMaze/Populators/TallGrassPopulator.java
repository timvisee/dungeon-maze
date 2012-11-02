package com.timvisee.DungeonMaze.Populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class TallGrassPopulator extends BlockPopulator {
	public static final int CHANCE_OF_GRASS = 35;
	public static final int ITERATIONS = 100;
	public static DungeonMaze plugin;
	
	
	@Override
	public void populate(World world, Random random, Chunk source) {
		
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			for(int i = 0; i < ITERATIONS; i++) {
				if (random.nextInt(100) < CHANCE_OF_GRASS) {
					
					int grassX = random.nextInt(16);
					int grassZ = random.nextInt(16);
					
					int yground;
					for(yground = 100; source.getBlock(grassX, yground, grassZ).getType() == Material.AIR; yground--);
					
					if(source.getBlock(grassX, yground, grassZ).getTypeId() == 2) {
						int grassY = yground + 1;
						
						source.getBlock(grassX, grassY, grassZ).setTypeId(31);
						source.getBlock(grassX, grassY, grassZ).setData((byte) 1);
					}
				}
			}
		}	
	}
}