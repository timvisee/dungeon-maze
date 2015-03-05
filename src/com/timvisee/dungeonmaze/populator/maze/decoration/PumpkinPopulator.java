package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class PumpkinPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
    public static final int LAYER_MIN = 1;
    public static final int LAYER_MAX = 7;
    public static final int ROOM_ITERATIONS = 3;
    public static final float ROOM_ITERATIONS_CHANCE = .02f;

    public static final float JACK_O_LANTERN_CHANCE = .33f;

    @Override
    public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        Chunk c = args.getSourceChunk();
        Random rand = args.getRandom();
        final int x = args.getChunkX();
        final int z = args.getChunkZ();
        final int xPumpkin = x + rand.nextInt(6) + 1;
        final int yPumpkin = args.getFloorY() + 1;
        final int zPumpkin = z + rand.nextInt(6) + 1;

        // Decide whether to place a pumpkin or jack o lantern
        final boolean illuminated = rand.nextFloat() < JACK_O_LANTERN_CHANCE;

        // Place the pumpkin if there's any place
        if(c.getBlock(xPumpkin, yPumpkin - 1, zPumpkin).getType() != Material.AIR) {
            Block slabBlock = c.getBlock(xPumpkin, yPumpkin, zPumpkin);
            if(slabBlock.getType() == Material.AIR) {
                if(!illuminated)
                    slabBlock.setType(Material.PUMPKIN);
                else
                    slabBlock.setType(Material.JACK_O_LANTERN);

                // Randomly rotate the pumpkin
                slabBlock.setData((byte) rand.nextInt(4));
            }
        }
    }

    @Override
    public int getRoomIterations() {
        return ROOM_ITERATIONS;
    }

    @Override
    public float getRoomIterationsChance() {
        return ROOM_ITERATIONS_CHANCE;
    }

    /**
     * Get the minimum layer
     * @return Minimum layer
     */
    @Override
    public int getMinimumLayer() {
        return LAYER_MIN;
    }

    /**
     * Get the maximum layer
     * @return Maximum layer
     */
    @Override
    public int getMaximumLayer() {
        return LAYER_MAX;
    }
}