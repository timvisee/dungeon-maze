package com.timvisee.dungeonmaze.populator.surface.plants;

import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulator;
import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulatorArgs;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Random;

public class TreePopulator extends SurfaceBlockPopulator {

    /** General populator constants. */
    private static final int CHUNK_ITERATIONS = 10;
    private static final float CHUNK_ITERATIONS_CHANCE = .1f;

	@Override
	public void populateSurface(SurfaceBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();

        // Determine the position of the tree in the chunk
        final int xTree = rand.nextInt(16);
        final int zTree = rand.nextInt(16);

        // Get the surface block at the trees position, and make sure it's valid
        Block surfaceBlock = world.getHighestBlockAt(chunk.getX() * 16 + xTree, chunk.getZ() * 16 + zTree);
        surfaceBlock = surfaceBlock.getRelative(BlockFace.DOWN);
        if(surfaceBlock == null)
            return;

        // Get the surface level
        final int ySurface = surfaceBlock.getY();
        final int yTree = ySurface + 1;

        // Get the biome
        final Biome biome = chunk.getWorld().getBiome((chunk.getX() * 16) + xTree, ySurface, (chunk.getZ() * 16) + zTree);

        // Spawn the proper tree
        if(surfaceBlock.getType() == Material.GRASS_BLOCK) {
            if(isForestBiome(biome))
                world.generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.BIRCH);

            else if(isJungleBiome(biome)) {
                switch(rand.nextInt(3)) {
                case 0:
                    world.generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.JUNGLE);
                    break;

                case 1:
                    world.generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.SMALL_JUNGLE);
                    break;

                case 2:
                    world.generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.JUNGLE_BUSH);
                    break;
                }

            } else if(isMushroomBiome(biome)) {
                switch(rand.nextInt(2)) {
                case 0:
                    world.generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.RED_MUSHROOM);
                    break;

                case 1:
                    world.generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.BROWN_MUSHROOM);
                    break;
                }

            } else if(isSwampBiome(biome))
                chunk.getWorld().generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.SWAMP);

            else if(isTaigaBiome(biome)) {
                switch(rand.nextInt(2)) {
                case 0:
                    world.generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.REDWOOD);
                    break;

                case 1:
                    world.generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.TALL_REDWOOD);
                    break;
                }

            } else
                world.generateTree(new Location(world, (chunk.getX() * 16) + xTree, yTree, (chunk.getZ() * 16) + zTree), TreeType.TREE);

        } else if(surfaceBlock.getType() == Material.SAND)
            if(isDesertBiome(biome)) {
                // Determine the cactus height
                int cactusHeight = rand.nextInt(3);

                // Place the cactus
                for(int i = 0; i < cactusHeight; i++)
                    setGeneratedBlock(chunk.getBlock(xTree, yTree + i, zTree), Material.CACTUS);
            }
	}

    private boolean isForestBiome(Biome biome) {
        return biome.name().equals("FOREST");
    }

    private boolean isJungleBiome(Biome biome) {
        return biome.name().contains("JUNGLE");
    }

    private boolean isMushroomBiome(Biome biome) {
        return biome.name().contains("MUSHROOM");
    }

    private boolean isSwampBiome(Biome biome) {
        return biome.name().contains("SWAMP");
    }

    private boolean isTaigaBiome(Biome biome) {
        return biome.name().contains("TAIGA");
    }

    private boolean isDesertBiome(Biome biome) {
        return biome.name().contains("DESERT");
    }

    @Override
    public int getChunkIterations() {
        return CHUNK_ITERATIONS;
    }

    @Override
    public float getChunkIterationsChance() {
        return CHUNK_ITERATIONS_CHANCE;
    }
}
