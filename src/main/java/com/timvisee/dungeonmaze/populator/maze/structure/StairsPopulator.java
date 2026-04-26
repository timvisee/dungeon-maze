package com.timvisee.dungeonmaze.populator.maze.structure;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.util.MaterialUtils;

public class StairsPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 6;
	private static final float ROOM_CHANCE = .02f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        // Get various
		final Chunk chunk = args.getSourceChunk();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int z = args.getRoomChunkZ();

        // Make sure there's some air at the spot we want to place the stair
        if(chunk.getBlock(x, y - 1, z).getType() == Material.AIR)
            return;

        // Build the stairs
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1, z + 2), Material.COBBLESTONE_STAIRS);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 1, z + 2), Material.COBBLESTONE_STAIRS);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 1, z + 3), Material.COBBLESTONE_STAIRS);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 1 + 1, z + 3), Material.COBBLESTONE_STAIRS);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 2, z + 4), Material.COBBLESTONE_STAIRS);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 1 + 2, z + 4), Material.COBBLESTONE_STAIRS);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 2, z + 5), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 1 + 2, z + 5), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 2, z + 6), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 1 + 2, z + 6), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 1 + 3, z + 5), Material.COBBLESTONE_STAIRS);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 1 + 3, z + 6), Material.COBBLESTONE_STAIRS);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1 + 4, z + 5), Material.COBBLESTONE_STAIRS);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1 + 4, z + 6), Material.COBBLESTONE_STAIRS);
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 1 + 5, z + 5), Material.COBBLESTONE_STAIRS);
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 1 + 5, z + 6), Material.COBBLESTONE_STAIRS);

        // Remove blocks blocking the stairway
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1 + 5, z + 5), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1 + 5, z + 6), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 1 + 5, z + 5), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 1 + 5, z + 6), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 5, z + 5), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 5, z + 6), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 1 + 6, z + 5), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 1 + 6, z + 6), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1 + 6, z + 5), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1 + 6, z + 6), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 1 + 6, z + 5), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 1 + 6, z + 6), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 6, z + 5), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 6, z + 6), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 1 + 7, z + 5), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 1 + 7, z + 6), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1 + 7, z + 5), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1 + 7, z + 6), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 1 + 7, z + 5), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 1 + 7, z + 6), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 7, z + 5), Material.AIR);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 7, z + 6), Material.AIR);

        // Properly set the data values of the stair blocks
        // TODO: Use the stair block instance instead (because of deprecation)
        MaterialUtils.setStairs(chunk.getBlock(x + 5, yFloor + 1, z + 2), Material.COBBLESTONE_STAIRS, BlockFace.SOUTH);
        MaterialUtils.setStairs(chunk.getBlock(x + 6, yFloor + 1, z + 2), Material.COBBLESTONE_STAIRS, BlockFace.SOUTH);
        MaterialUtils.setStairs(chunk.getBlock(x + 5, yFloor + 2, z + 3), Material.COBBLESTONE_STAIRS, BlockFace.SOUTH);
        MaterialUtils.setStairs(chunk.getBlock(x + 6, yFloor + 2, z + 3), Material.COBBLESTONE_STAIRS, BlockFace.SOUTH);
        MaterialUtils.setStairs(chunk.getBlock(x + 5, yFloor + 3, z + 4), Material.COBBLESTONE_STAIRS, BlockFace.SOUTH);
        MaterialUtils.setStairs(chunk.getBlock(x + 6, yFloor + 3, z + 4), Material.COBBLESTONE_STAIRS, BlockFace.SOUTH);
        MaterialUtils.setStairs(chunk.getBlock(x + 4, yFloor + 4, z + 5), Material.COBBLESTONE_STAIRS, BlockFace.WEST);
        MaterialUtils.setStairs(chunk.getBlock(x + 4, yFloor + 4, z + 6), Material.COBBLESTONE_STAIRS, BlockFace.WEST);
        MaterialUtils.setStairs(chunk.getBlock(x + 3, yFloor + 5, z + 5), Material.COBBLESTONE_STAIRS, BlockFace.WEST);
        MaterialUtils.setStairs(chunk.getBlock(x + 3, yFloor + 5, z + 6), Material.COBBLESTONE_STAIRS, BlockFace.WEST);
        MaterialUtils.setStairs(chunk.getBlock(x + 2, yFloor + 6, z + 5), Material.COBBLESTONE_STAIRS, BlockFace.WEST);
        MaterialUtils.setStairs(chunk.getBlock(x + 2, yFloor + 6, z + 6), Material.COBBLESTONE_STAIRS, BlockFace.WEST);

        // Put some supports under the staircase
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 1, z + 4), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 1 + 1, z + 4), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 1, z + 5), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 1 + 1, z + 5), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 1 + 1, z + 6), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 1 + 1, z + 6), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 1 + 2, z + 5), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 1 + 2, z + 6), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1 + 3, z + 5), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1 + 3, z + 6), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 1 + 4, z + 5), Material.COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 1 + 4, z + 6), Material.COBBLESTONE);
	}

    @Override
    public float getRoomChance() {
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
