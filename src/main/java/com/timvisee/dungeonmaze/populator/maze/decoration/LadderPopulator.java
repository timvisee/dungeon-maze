package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class LadderPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 6;
	private static final float ROOM_CHANCE = .05f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();
        final int startX;
        final int startY = args.getFloorY() + 1;
        final int startZ;
			
        BlockFace ladderFace = BlockFace.NORTH;
        switch (rand.nextInt(2)) {
        case 0:
            int r = rand.nextInt(2);
            startX = x + 1 + (r * 5);
            startZ = z + rand.nextInt(2) * 7;
            ladderFace = (r == 0) ? BlockFace.NORTH : BlockFace.SOUTH;
            break;

        case 1:
            int r2 = rand.nextInt(2);
            startX = x + rand.nextInt(2) * 7;
            startZ = z + 1 + (r2*5);
            ladderFace = (r2 == 0) ? BlockFace.EAST : BlockFace.WEST;
            break;

        default:
            startX = x + 1 + (rand.nextInt(2) * 5);
            startZ = z + rand.nextInt(2) * 7;
        }

        // Make sure there's no wall or anything else
        if(chunk.getBlock(startX, startY, startZ).getType() == Material.AIR) {
            for (int ladderY=startY; ladderY <= startY + 8; ladderY++) {
                org.bukkit.block.Block lb = chunk.getBlock(startX, ladderY, startZ);
                setGeneratedBlock(lb, Material.LADDER);
                Directional ladderData = (Directional) lb.getBlockData();
                ladderData.setFacing(ladderFace);
                setGeneratedBlockData(lb, ladderData);
            }
        }
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