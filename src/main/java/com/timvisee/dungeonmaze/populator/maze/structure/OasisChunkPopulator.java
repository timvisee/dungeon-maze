package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import com.timvisee.dungeonmaze.populator.ChunkBlockPopulator;
import com.timvisee.dungeonmaze.populator.ChunkBlockPopulatorArgs;
import com.timvisee.dungeonmaze.util.NumberUtils;
import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;

public class OasisChunkPopulator extends ChunkBlockPopulator {

    /** General populator constants. */
    public static final float CHUNK_CHANCE = .003f;

    /** Populator constants. */
	public static final int CHANCE_CLAYINDIRT = 10;
	public static final double SPAWN_DISTANCE_MIN = 7; // Chunks

	@Override
	public void populateChunk(ChunkBlockPopulatorArgs args) {
        final World world = args.getWorld();
        final Random rand = args.getRandom();
        final Chunk chunk = args.getSourceChunk();
        final DungeonChunk dungeonChunk = args.getDungeonChunk();

        if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < SPAWN_DISTANCE_MIN)
            return;

        // Set this chunk as custom
        dungeonChunk.setCustomChunk(true);

        // Generate a dirt layer
        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++)
                chunk.getBlock(x, 29, z).setType(Material.DIRT);

        // Generate some clay inside the dirt layer
        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++)
                if(rand.nextInt(100) < CHANCE_CLAYINDIRT)
                    chunk.getBlock(x, 29, z).setType(Material.CLAY);

        // Generate the grass layer
        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++)
                chunk.getBlock(x, 30, z).setType(Material.GRASS);

        // Remove all the stone above the grass layer!
        for(int y = 31; y <= 100; y++)
            for(int x = 0; x < 16; x++)
                for(int z = 0; z < 16; z++)
                    chunk.getBlock(x, y, z).setType(Material.AIR);

        // Generate some tall grass on the oasis
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                if(rand.nextInt(100) < CHANCE_CLAYINDIRT) {
                    chunk.getBlock(x, 31, z).setType(Material.LONG_GRASS);
                    chunk.getBlock(x, 31, z).setData((byte) 1);
                }
            }
        }

        // Random tree offset (0 or 1)
        int treeOffsetX = rand.nextInt(2);
        int treeOffsetZ = rand.nextInt(2);

        // Generate the water around the tree
        for(int x = 5; x <= 11; x++)
            chunk.getBlock(x + treeOffsetX, 30, 5 + treeOffsetZ).setType(Material.WATER);
        for(int z = 5; z <= 11; z++)
            chunk.getBlock(5 + treeOffsetX, 30, z + treeOffsetZ).setType(Material.WATER);
        for(int x = 5; x <= 11; x++)
            chunk.getBlock(x + treeOffsetX, 30, 11 + treeOffsetZ).setType(Material.WATER);
        for(int z = 5; z <= 11; z++)
            chunk.getBlock(11 + treeOffsetX, 30, z + treeOffsetZ).setType(Material.WATER);

        // Generate some sugar canes
        chunk.getBlock(6 + treeOffsetX, 31, 6 + treeOffsetZ).setType(Material.SUGAR_CANE_BLOCK);
        chunk.getBlock(6 + treeOffsetX, 31, 10 + treeOffsetZ).setType(Material.SUGAR_CANE_BLOCK);
        chunk.getBlock(10 + treeOffsetX, 31, 6 + treeOffsetZ).setType(Material.SUGAR_CANE_BLOCK);
        chunk.getBlock(10 + treeOffsetX, 31, 10 + treeOffsetZ).setType(Material.SUGAR_CANE_BLOCK);

        // Random tree type and generate the tree
        TreeType treeType;
        switch (rand.nextInt(5)) {
        case 0:
            treeType = TreeType.BIG_TREE;
            break;
        case 1:
            treeType = TreeType.BIRCH;
            break;
        case 2:
            treeType = TreeType.REDWOOD;
            break;
        case 3:
            treeType = TreeType.TALL_REDWOOD;
            break;
        case 4:
            treeType = TreeType.TREE;
            break;
        default:
            treeType = TreeType.TREE;
        }

        // Tree generation currently not working :@
        Location treeLocation = new Location(world, (chunk.getX()*16) + (8 + treeOffsetX), 31, (chunk.getZ()*16) + (8 + treeOffsetZ));
        world.generateTree(treeLocation, treeType);
	}

    @Override
    public float getChunkIterationsChance() {
        return CHUNK_CHANCE;
    }
}