package com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;

import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulator;
import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulatorArgs;

public class TreePopulator extends SurfaceBlockPopulator {
	public static final int CHANCE_OF_TREE = 10;
	public static final int ITERATIONS = 10;

	@Override
	public void populateSurface(SurfaceBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			// Apply chances
			if (rand.nextInt(100) < CHANCE_OF_TREE) {
				
				int xTree = rand.nextInt(16);
				int zTree = rand.nextInt(16);
				
				// Get the surface level
				int ySurface = args.getSurfaceLevel(xTree, zTree);
				
				int yTree = ySurface + 1;
				
				Biome biome = c.getWorld().getBiome((c.getX() * 16) + xTree, (c.getZ() * 16) + zTree);
				
				if(biome.equals(Biome.DESERT) || biome.equals(Biome.DESERT_HILLS)) {
					if(c.getBlock(xTree, ySurface, zTree).getType() == Material.SAND)
						c.getBlock(xTree, yTree, zTree).setType(Material.CACTUS);
					
				} else if(biome.equals(Biome.FOREST)) {
					if(c.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS)
						c.getWorld().generateTree(new Location(c.getWorld(), (c.getX()*16) + xTree, yTree, (c.getZ() * 16) + zTree), TreeType.BIRCH);
					
				} else if(biome.equals(Biome.JUNGLE) || biome.equals(Biome.JUNGLE_HILLS)) {
					if(c.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS) {
						switch(rand.nextInt(3)) {
						case 0:
							c.getWorld().generateTree(new Location(w, (c.getX() * 16) + xTree, yTree, (c.getZ() * 16) + zTree), TreeType.JUNGLE);
							break;
						case 1:
							c.getWorld().generateTree(new Location(w, (c.getX() * 16) + xTree, yTree, (c.getZ() * 16) + zTree), TreeType.SMALL_JUNGLE);
							break;
						case 2: 
							c.getWorld().generateTree(new Location(w, (c.getX() * 16) + xTree, yTree, (c.getZ() * 16) + zTree), TreeType.JUNGLE_BUSH);
							break;
						}
					}
					
				} else if(biome.equals(Biome.MUSHROOM_ISLAND) || biome.equals(Biome.MUSHROOM_SHORE)) {
					if(c.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS) {
						switch(rand.nextInt(2)) {
						case 0:
							c.getWorld().generateTree(new Location(w, (c.getX() * 16) + xTree, yTree, (c.getZ() * 16) + zTree), TreeType.RED_MUSHROOM);
							break;
						case 1:
							c.getWorld().generateTree(new Location(w, (c.getX() * 16) + xTree, yTree, (c.getZ() * 16) + zTree), TreeType.BROWN_MUSHROOM);
							break;
						}
					}
					
				} else if(biome.equals(Biome.SWAMPLAND))
					if(c.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS)
						c.getWorld().generateTree(new Location(w, (c.getX() * 16) + xTree, yTree, (c.getZ() * 16) + zTree), TreeType.SWAMP);
					
				else if(biome.equals(Biome.TAIGA) || biome.equals(Biome.TAIGA_HILLS)) {
					if(c.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS) {
						switch(rand.nextInt(2)) {
						case 0:
							c.getWorld().generateTree(new Location(w, (c.getX() * 16) + xTree, yTree, (c.getZ() * 16) + zTree), TreeType.REDWOOD);
							break;
						case 1:
							c.getWorld().generateTree(new Location(w, (c.getX() * 16) + xTree, yTree, (c.getZ() * 16) + zTree), TreeType.TALL_REDWOOD);
							break;
						}
					}
					
				} else
					if(c.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS)
						c.getWorld().generateTree(new Location(w, (c.getX() * 16) + xTree, yTree, (c.getZ() * 16) + zTree), TreeType.TREE);
			}
		}
	}
}