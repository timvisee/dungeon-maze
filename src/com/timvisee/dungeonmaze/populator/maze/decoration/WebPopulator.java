package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class WebPopulator extends MazeRoomBlockPopulator {

    // TODO: Iterate?

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
	public static final float ROOM_CHANCE = .4f;

    // TODO: Implement this!
	public static final double CHANCE_WEB_ADDITION_EACH_LEVEL = -1.667; /* to 30 */

	public static final float CORNER_CHANCE = .4f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final Chunk c = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int z = args.getChunkZ();
        final int webX = x + rand.nextInt(6) + 1;
        final int webY = args.getFloorY() + 1;
        final int webCeilingY = args.getCeilingY() - 1;
        final int webZ = z + rand.nextInt(6) + 1;

        if(rand.nextFloat() < CORNER_CHANCE)
            if(c.getBlock(x + (rand.nextInt(2)*5), webCeilingY, z + (rand.nextInt(2)*5)).getType() == Material.AIR)
                c.getBlock(x + (rand.nextInt(2)*5), webCeilingY, z + (rand.nextInt(2)*5)).setType(Material.WEB);
        else
            if(c.getBlock(webX, webY, webZ).getType() == Material.AIR)
                c.getBlock(webX, webY, webZ).setType(Material.WEB);
	}

    @Override
    public float getRoomPopulationChance() {
        return ROOM_CHANCE;
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