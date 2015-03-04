package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import org.bukkit.World;

public class WebPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
	public static final float ROOM_CHANCE = .25f;
    public static final int ROOM_ITERATIONS = 4;

    /** Populator constants. */
	public static final float CEILING_CHANCE = .4f;

    // TODO: Implement this!
    public static final double CHANCE_WEB_ADDITION_EACH_LEVEL = -1.667; /* to 30 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final World world = args.getWorld();
		final Chunk c = args.getSourceChunk();
		final Random rand = args.getRandom();
        final int x = args.getChunkX();
        final int z = args.getChunkZ();

        // Decide whether the web should be on the ground
        final boolean onCeiling = rand.nextFloat() < CEILING_CHANCE;

        // Choose a x,z position in the room to spawn the web
        int xWeb;
        int yWeb = args.getFloorY();
        int zWeb;
        while(true) {
            // Choose a random position
            xWeb = x + rand.nextInt(8);
            zWeb = z + rand.nextInt(8);

            // Make sure it isn't in the dungeon maze pillars
            if((xWeb == 0 || xWeb == 7) ||zWeb == 0 || zWeb == 7)
                continue;

            // The position seems to be ok
            break;
        }

        // Check whether the web should be placed on the ground
        if(!onCeiling) {
            // Check whether the position is free, if not iterate up
            while(c.getBlock(xWeb, yWeb, zWeb).getType() != Material.AIR) {
                yWeb++;

                if(yWeb + 1 >= world.getMaxHeight())
                    return;
            }

        } else {
            // Set the y position
            yWeb = args.getCeilingY();

            // Check whether the position is free, if not iterate up
            while(c.getBlock(xWeb, yWeb, zWeb).getType() != Material.AIR) {
                yWeb--;

                if(yWeb == 1)
                    return;
            }
        }

        // Place the web
        c.getBlock(xWeb, yWeb, zWeb).setType(Material.WEB);
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

    @Override
    public float getRoomPopulationChance() {
        return ROOM_CHANCE;
    }

    @Override
    public int getRoomPopulationIterations() {
        return ROOM_ITERATIONS;
    }
}