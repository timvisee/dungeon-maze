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

    /** General populator constants. */
    public static final float CHUNK_CHANCE = .1f;
    public static final int CHUNK_ITERATIONS = 10;

	@Override
	public void populateSurface(SurfaceBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
        final int xTree = rand.nextInt(16);
        final int zTree = rand.nextInt(16);

        // Get the surface level
        int ySurface = args.getSurfaceLevel(xTree, zTree);
        int yTree = ySurface + 1;

        final Biome biome = chunk.getWorld().getBiome((chunk.getX() * 16) + xTree, (chunk.getZ() * 16) + zTree);

        if(biome.equals(Biome.DESERT) || biome.equals(Biome.DESERT_HILLS)) {
            if(chunk.getBlock(xTree, ySurface, zTree).getType() == Material.SAND)
                chunk.getBlock(xTree, yTree, zTree).setType(Material.CACTUS);

        } else if(biome.equals(Biome.FOREST)) {
            if(chunk.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS)
                chunk.getWorld().generateTree(new Location(chunk.getWorld(), (chunk.getX()*16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.BIRCH);

        } else if(biome.equals(Biome.JUNGLE) || biome.equals(Biome.JUNGLE_HILLS)) {
            if(chunk.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS) {
                switch(rand.nextInt(3)) {
                case 0:
                    chunk.getWorld().generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.JUNGLE);
                    break;

                case 1:
                    chunk.getWorld().generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.SMALL_JUNGLE);
                    break;

                case 2:
                    chunk.getWorld().generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.JUNGLE_BUSH);
                    break;
                }
            }

        } else if(biome.equals(Biome.MUSHROOM_ISLAND) || biome.equals(Biome.MUSHROOM_SHORE)) {
            if(chunk.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS) {
                switch(rand.nextInt(2)) {
                case 0:
                    chunk.getWorld().generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.RED_MUSHROOM);
                    break;

                case 1:
                    chunk.getWorld().generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.BROWN_MUSHROOM);
                    break;
                }
            }

        } else if(biome.equals(Biome.SWAMPLAND))
            if(chunk.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS)
                chunk.getWorld().generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.SWAMP);

        else if(biome.equals(Biome.TAIGA) || biome.equals(Biome.TAIGA_HILLS)) {
            if(chunk.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS) {
                switch(rand.nextInt(2)) {
                case 0:
                    chunk.getWorld().generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.REDWOOD);
                    break;

                case 1:
                    chunk.getWorld().generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.TALL_REDWOOD);
                    break;
                }
            }

        } else
            if(chunk.getBlock(xTree, ySurface, zTree).getType() == Material.GRASS)
                chunk.getWorld().generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.TREE);
	}

    @Override
    public float getChunkPopulationChance() {
        return CHUNK_CHANCE;
    }

    @Override
    public int getChunkPopulationIterations() {
        return CHUNK_ITERATIONS;
    }
}