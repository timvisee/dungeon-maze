package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

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
		final int x = args.getChunkX();
		final int z = args.getChunkZ();
        final int startX;
        final int startY = args.getFloorY() + 1;
        final int startZ;
			
        byte ladderData = (byte) 0;
        switch (rand.nextInt(2)) {
        case 0:
            int r = rand.nextInt(2);
            startX = x + 1 + (r * 5);
            startZ = z + rand.nextInt(2) * 7;
            if(r == 0)
                ladderData = (byte) 5; // North
            else
                ladderData = (byte) 4; // South
            break;

        case 1:
            int r2 = rand.nextInt(2);
            startX = x + rand.nextInt(2) * 7;
            startZ = z + 1 + (r2*5);
            if(r2 == 0)
                ladderData = (byte) 3; // East
            else
                ladderData = (byte) 2; // West
            break;

        default:
            startX = x + 1 + (rand.nextInt(2) * 5);
            startZ = z + rand.nextInt(2) * 7;
        }

        // Make sure there's no wall or anything else
        if(chunk.getBlock(startX, startY, startZ).getType() == Material.AIR) {
            for (int ladderY=startY; ladderY <= startY + 8; ladderY++) {
                chunk.getBlock(startX, ladderY, startZ).setType(Material.LADDER);
                chunk.getBlock(startX, ladderY, startZ).setData(ladderData);
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