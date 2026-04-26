package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import com.timvisee.dungeonmaze.populator.ChunkBlockPopulator;
import com.timvisee.dungeonmaze.populator.ChunkBlockPopulatorArgs;
import org.bukkit.Chunk;
import org.bukkit.Material;

public class OresInGroundPopulator extends ChunkBlockPopulator {

    /** Populator constants. */
	private static final float ORE_CHANCE = .005f;

    @Override
    public void populateChunk(ChunkBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();

        // The layers for each 4 rooms in the variable y
        for(int y = 1; y <= 29; y += 1) {
            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    if(rand.nextFloat() < ORE_CHANCE) {
                        switch (rand.nextInt(9)) {
                        case 0:
                            setGeneratedBlock(chunk.getBlock(x, y, z), Material.GOLD_ORE);
                            break;

                        case 1:
                            setGeneratedBlock(chunk.getBlock(x, y, z), Material.IRON_ORE);
                            break;

                        case 2:
                            setGeneratedBlock(chunk.getBlock(x, y, z), Material.COAL_ORE);
                            break;

                        case 3:
                            setGeneratedBlock(chunk.getBlock(x, y, z), Material.LAPIS_ORE);
                            break;

                        case 4:
                            setGeneratedBlock(chunk.getBlock(x, y, z), Material.DIAMOND_ORE);
                            break;

                        case 5:
                            setGeneratedBlock(chunk.getBlock(x, y, z), Material.REDSTONE_ORE);
                            break;

                        case 6:
                            setGeneratedBlock(chunk.getBlock(x, y, z), Material.EMERALD_ORE);
                            break;

                        case 7:
                            setGeneratedBlock(chunk.getBlock(x, y, z), Material.CLAY);
                            break;

                        case 8:
                            setGeneratedBlock(chunk.getBlock(x, y, z), Material.COAL_ORE);
                            break;

                        default:
                            setGeneratedBlock(chunk.getBlock(x, y, z), Material.COAL_ORE);
                        }
                    }
                }
            }
        }
    }
}