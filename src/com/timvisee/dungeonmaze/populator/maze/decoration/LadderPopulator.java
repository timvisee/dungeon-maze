package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class LadderPopulator extends MazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 6;
	public static final int CHANCE_OF_LADDER = 5;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int z = args.getChunkZ();
		
		// Apply chances
		if (rand.nextInt(100) < CHANCE_OF_LADDER) {
			int startX = x;
			int startY = args.getFloorY() + 1;
			int startZ = z;
			
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
			if(c.getBlock(startX, startY, startZ).getType() == Material.AIR) {
				for (int ladderY=startY; ladderY <= startY + 8; ladderY++) {
					c.getBlock(startX, ladderY, startZ).setType(Material.LADDER);
					c.getBlock(startX, ladderY, startZ).setData(ladderData);
				}
			}
		}
	}
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return MIN_LAYER;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return MAX_LAYER;
	}
}