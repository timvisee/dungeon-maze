package com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.dungeonmaze.DungeonMaze;

public class FlowerPopulator extends BlockPopulator {
	public static final int CHANCE_OF_FLOWER = 15;
	public static final int ITERATIONS = 10;
	public static DungeonMaze plugin;
	
	
	@Override
	public void populate(World world, Random random, Chunk source) {
		
		if(!DungeonMaze.instance.isConstantChunk(world.getName(), source)) {
			for(int i = 0; i < ITERATIONS; i++) {
				if (random.nextInt(100) < CHANCE_OF_FLOWER) {
					
					int flowerX = random.nextInt(16);
					int flowerZ = random.nextInt(16);
					
					int yground;
					for(yground = 100; source.getBlock(flowerX, yground, flowerZ).getType() == Material.AIR; yground--);
					
					if(source.getBlock(flowerX, yground, flowerZ).getTypeId() == 2) {
						int flowerY = yground + 1;
						
						switch(random.nextInt(2)) {
						case 0:
							source.getBlock(flowerX, flowerY, flowerZ).setTypeId(37);
							break;
						case 1:
							source.getBlock(flowerX, flowerY, flowerZ).setTypeId(38);
							break;
						default:
							source.getBlock(flowerX, flowerY, flowerZ).setTypeId(37);	
						}
						
					}
				}
			}
		}	
	}
}