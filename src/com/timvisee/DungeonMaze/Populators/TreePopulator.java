package com.timvisee.DungeonMaze.populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class TreePopulator extends BlockPopulator {
	public static final int CHANCE_OF_TREE = 10;
	public static final int ITERATIONS = 10;
	public static DungeonMaze plugin;
	
	
	@Override
	public void populate(World world, Random random, Chunk source) {
		
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			for(int i = 0; i < ITERATIONS; i++) {
				if (random.nextInt(100) < CHANCE_OF_TREE) {
					
					int treeX = random.nextInt(16);
					int treeZ = random.nextInt(16);
					
					int yground;
					for(yground = 100; source.getBlock(treeX, yground, treeZ).getType() == Material.AIR; yground--);
					
					int treeY = yground + 1;
					
					Biome biome = source.getWorld().getBiome((source.getX()*16) + treeX, (source.getZ()*16) + treeZ);
					
					if(biome.equals(Biome.DESERT) || biome.equals(Biome.DESERT_HILLS)) {
						if(source.getBlock(treeX, yground, treeZ).getTypeId() == 12) {
							source.getBlock(treeX, treeY, treeZ).setType(Material.CACTUS);
						}
						
					} else if(biome.equals(Biome.FOREST)) {
						if(source.getBlock(treeX, yground, treeZ).getTypeId() == 2) {
							source.getWorld().generateTree(new Location(source.getWorld(), (source.getX()*16) + treeX, treeY, (source.getZ()*16) + treeZ), TreeType.BIRCH);
						}
						
					} else if(biome.equals(Biome.JUNGLE) || biome.equals(Biome.JUNGLE_HILLS)) {
						if(source.getBlock(treeX, yground, treeZ).getTypeId() == 2) {
							switch(random.nextInt(3)) {
							case 0:
								source.getWorld().generateTree(new Location(source.getWorld(), (source.getX()*16) + treeX, treeY, (source.getZ()*16) + treeZ), TreeType.JUNGLE);
								break;
							case 1:
								source.getWorld().generateTree(new Location(source.getWorld(), (source.getX()*16) + treeX, treeY, (source.getZ()*16) + treeZ), TreeType.SMALL_JUNGLE);
								break;
							case 2: 
								source.getWorld().generateTree(new Location(source.getWorld(), (source.getX()*16) + treeX, treeY, (source.getZ()*16) + treeZ), TreeType.JUNGLE_BUSH);
								break;
							}
							
						}
						
					} else if(biome.equals(Biome.MUSHROOM_ISLAND) || biome.equals(Biome.MUSHROOM_SHORE)) {
						if(source.getBlock(treeX, yground, treeZ).getTypeId() == 2) {
							switch(random.nextInt(2)) {
							case 0:
								source.getWorld().generateTree(new Location(source.getWorld(), (source.getX()*16) + treeX, treeY, (source.getZ()*16) + treeZ), TreeType.RED_MUSHROOM);
								break;
							case 1:
								source.getWorld().generateTree(new Location(source.getWorld(), (source.getX()*16) + treeX, treeY, (source.getZ()*16) + treeZ), TreeType.BROWN_MUSHROOM);
								break;
							}
						}
						
					} else if(biome.equals(Biome.SWAMPLAND)) {
						if(source.getBlock(treeX, yground, treeZ).getTypeId() == 2) {
							source.getWorld().generateTree(new Location(source.getWorld(), (source.getX()*16) + treeX, treeY, (source.getZ()*16) + treeZ), TreeType.SWAMP);
						}
						
					} else if(biome.equals(Biome.TAIGA) || biome.equals(Biome.TAIGA_HILLS)) {
						if(source.getBlock(treeX, yground, treeZ).getTypeId() == 2) {
							switch(random.nextInt(2)) {
							case 0:
								source.getWorld().generateTree(new Location(source.getWorld(), (source.getX()*16) + treeX, treeY, (source.getZ()*16) + treeZ), TreeType.REDWOOD);
								break;
							case 1:
								source.getWorld().generateTree(new Location(source.getWorld(), (source.getX()*16) + treeX, treeY, (source.getZ()*16) + treeZ), TreeType.TALL_REDWOOD);
								break;
							}
						}
						
					} else {
						if(source.getBlock(treeX, yground, treeZ).getTypeId() == 2) {
							source.getWorld().generateTree(new Location(source.getWorld(), (source.getX()*16) + treeX, treeY, (source.getZ()*16) + treeZ), TreeType.TREE);
						}
					}
				}
			}
		}	
	}
}