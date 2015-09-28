package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import com.timvisee.dungeonmaze.populator.ChunkBlockPopulator;
import com.timvisee.dungeonmaze.populator.ChunkBlockPopulatorArgs;
import org.bukkit.Chunk;
import org.bukkit.Material;

public class OresInGroundPopulator extends ChunkBlockPopulator {

    /** Populator constants. */
	public static final float ORE_CHANCE = .005f;

    @Override
    public void populateChunk(ChunkBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();

        // The layers for each 4 rooms in the variable y
        for (int y=1; y <= 29; y+=1) {
            for (int x=0; x < 16; x+=1) {
                for (int z=0; z < 16; z+=1) {
                    if (rand.nextFloat() < ORE_CHANCE) {
                        switch (rand.nextInt(9)) {
                        case 0:
                            chunk.getBlock((chunk.getX() * 16) + x, y, (chunk.getZ() * 16) + z).setType(Material.GOLD_ORE);
                            break;

                        case 1:
                            chunk.getBlock((chunk.getX() * 16) + x, y, (chunk.getZ() * 16) + z).setType(Material.IRON_ORE);
                            break;

                        case 2:
                            chunk.getBlock((chunk.getX() * 16) + x, y, (chunk.getZ() * 16) + z).setType(Material.COAL_ORE);
                            break;

                        case 3:
                            chunk.getBlock((chunk.getX() * 16) + x, y, (chunk.getZ() * 16) + z).setType(Material.LAPIS_ORE);
                            break;

                        case 4:
                            chunk.getBlock((chunk.getX() * 16) + x, y, (chunk.getZ() * 16) + z).setType(Material.DIAMOND_ORE);
                            break;

                        case 5:
                            chunk.getBlock((chunk.getX() * 16) + x, y, (chunk.getZ() * 16) + z).setType(Material.REDSTONE_ORE);
                            break;

                        case 6:
                            chunk.getBlock((chunk.getX() * 16) + x, y, (chunk.getZ() * 16) + z).setType(Material.EMERALD_ORE);
                            break;

                        case 7:
                            chunk.getBlock((chunk.getX() * 16) + x, y, (chunk.getZ() * 16) + z).setType(Material.CLAY);
                            break;

                        case 8:
                            chunk.getBlock((chunk.getX() * 16) + x, y, (chunk.getZ() * 16) + z).setType(Material.COAL_ORE);
                            break;

                        default:
                            chunk.getBlock((chunk.getX() * 16) + x, y, (chunk.getZ() * 16) + z).setType(Material.COAL_ORE);
                        }
                    }
                }
            }
        }
    }
}