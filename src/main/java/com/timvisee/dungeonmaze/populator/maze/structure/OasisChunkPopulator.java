package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import com.timvisee.dungeonmaze.populator.ChunkBlockPopulator;
import com.timvisee.dungeonmaze.populator.ChunkBlockPopulatorArgs;
import com.timvisee.dungeonmaze.util.MaterialUtils;
import com.timvisee.dungeonmaze.util.NumberUtils;
import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;

public class OasisChunkPopulator extends ChunkBlockPopulator {

    /** General populator constants. */
    private static final float CHUNK_CHANCE = .003f;

    /** Populator constants. */
	private static final int CHANCE_CLAYINDIRT = 10;
	private static final double SPAWN_DISTANCE_MIN = 7; // Chunks

	@Override
	public void populateChunk(ChunkBlockPopulatorArgs args) {
        final World world = args.getWorld();
        final Random rand = args.getRandom();
        final Chunk chunk = args.getSourceChunk();
        final DungeonChunk dungeonChunk = args.getDungeonChunk();
        final Material grassPlant = MaterialUtils.requireBlockMaterial("SHORT_GRASS", "GRASS");

        if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < SPAWN_DISTANCE_MIN)
            return;

        // Set this chunk as custom
        dungeonChunk.setCustomChunk(true);

        // Generate a dirt layer
        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++)
                setGeneratedBlock(chunk.getBlock(x, 29, z), Material.DIRT);

        // Generate some clay inside the dirt layer
        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++)
                if(rand.nextInt(100) < CHANCE_CLAYINDIRT)
                    setGeneratedBlock(chunk.getBlock(x, 29, z), Material.CLAY);

        // Generate the grass layer
        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++)
                setGeneratedBlock(chunk.getBlock(x, 30, z), Material.GRASS_BLOCK);

        // Remove all the stone above the grass layer!
        for(int y = 31; y <= 100; y++)
            for(int x = 0; x < 16; x++)
                for(int z = 0; z < 16; z++)
                    setGeneratedBlock(chunk.getBlock(x, y, z), Material.AIR);

        // Generate some tall grass on the oasis
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                if(rand.nextInt(100) < CHANCE_CLAYINDIRT) {
                    setGeneratedBlock(chunk.getBlock(x, 31, z), grassPlant);
                }
            }
        }

        // Random tree offset (0 or 1)
        int treeOffsetX = rand.nextInt(2);
        int treeOffsetZ = rand.nextInt(2);

        // Generate the water around the tree
        for(int x = 5; x <= 11; x++)
            setGeneratedBlock(chunk.getBlock(x + treeOffsetX, 30, 5 + treeOffsetZ), Material.WATER);
        for(int z = 5; z <= 11; z++)
            setGeneratedBlock(chunk.getBlock(5 + treeOffsetX, 30, z + treeOffsetZ), Material.WATER);
        for(int x = 5; x <= 11; x++)
            setGeneratedBlock(chunk.getBlock(x + treeOffsetX, 30, 11 + treeOffsetZ), Material.WATER);
        for(int z = 5; z <= 11; z++)
            setGeneratedBlock(chunk.getBlock(11 + treeOffsetX, 30, z + treeOffsetZ), Material.WATER);

        // Generate some sugar canes
        setGeneratedBlock(chunk.getBlock(6 + treeOffsetX, 31, 6 + treeOffsetZ), Material.SUGAR_CANE);
        setGeneratedBlock(chunk.getBlock(6 + treeOffsetX, 31, 10 + treeOffsetZ), Material.SUGAR_CANE);
        setGeneratedBlock(chunk.getBlock(10 + treeOffsetX, 31, 6 + treeOffsetZ), Material.SUGAR_CANE);
        setGeneratedBlock(chunk.getBlock(10 + treeOffsetX, 31, 10 + treeOffsetZ), Material.SUGAR_CANE);

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
        Location treeLocation = new Location(
                world,
                (chunk.getX() * 16) + (8 + treeOffsetX),
                31,
                (chunk.getZ() * 16) + (8 + treeOffsetZ)
        );
        world.generateTree(treeLocation, treeType);
	}

    @Override
    public float getChunkIterationsChance() {
        return CHUNK_CHANCE;
    }
}
