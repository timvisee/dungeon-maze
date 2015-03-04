package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class SilverfishBlockPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 3;
	public static final int LAYER_MAX = 7;
	public static final float ROOM_CHANCE = .75f;
	public static final int ROOM_ITERATIONS = 8;

    // TODO: Implement this feature!
	public static final double CHANCE_ADDITION_EACH_LEVEL = -4.167; /* to 75 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int y = args.getChunkY();
		final int floorOffset = args.getFloorOffset();
		final int z = args.getChunkZ();
        int blockX = x + rand.nextInt(8);
        int blockY = y + rand.nextInt(4 - floorOffset) + 1 + floorOffset;
        int blockZ = z + rand.nextInt(8);

        Block lanternBlock = c.getBlock(blockX, blockY, blockZ);
        if(lanternBlock.getType() == Material.STONE) {
            lanternBlock.setType(Material.MONSTER_EGGS);
            lanternBlock.setData((byte) 0);
        } else if(lanternBlock.getType() == Material.COBBLESTONE) {
            lanternBlock.setType(Material.MONSTER_EGGS);
            lanternBlock.setData((byte) 1);
        } else if(lanternBlock.getType() == Material.MOSSY_COBBLESTONE) {
            lanternBlock.setType(Material.MONSTER_EGGS);
            lanternBlock.setData((byte) 1);
        } else if(lanternBlock.getType() == Material.SMOOTH_BRICK) {
            lanternBlock.setType(Material.MONSTER_EGGS);
            lanternBlock.setData((byte) 2);
        }
	}

    @Override
    public float getRoomPopulationChance() {
        return ROOM_CHANCE;
    }

    @Override
    public int getRoomPopulationIterations() {
        return ROOM_ITERATIONS;
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
