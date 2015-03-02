package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class GravelPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 2;
	public static final int LAYER_MAX = 7;
    public static final int RUINS_CHANCE = 5;
	public static final int RUINS_MAX = 2;

	public static final BlockFace[] DIRECTIONS = new BlockFace[] {
			BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();
		
		int ruins = 0;
		while (rand.nextInt(100) < RUINS_CHANCE && ruins < RUINS_MAX) {
			int startX = x + rand.nextInt(6) + 1;
			int startY = yFloor + 1;
			int startZ = z + rand.nextInt(6) + 1;
			
			int startHeight = rand.nextInt(2) + 1;

			BlockFace dir1 = DIRECTIONS[rand.nextInt(DIRECTIONS.length)];
			BlockFace dir2 = DIRECTIONS[rand.nextInt(DIRECTIONS.length)];

			int height = startHeight;
			int x2 = startX;
			int z2 = startZ;
			while (height > 0 && 0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
				for (int y2 = startY; y2 < startY + height; y2++)
					if(c.getBlock(x2, y2, z2).getType() == Material.AIR)
						c.getBlock(x2, y2, z2).setType(Material.GRAVEL);

				height -= rand.nextInt(1);

				x2 += dir1.getModX();
				z2 += dir1.getModZ();
			}

			if (dir1 != dir2) {
				height = startHeight;
				x2 = startX;
				z2 = startZ;
				while (height > 0 && 0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
					for (int y2 = startY; y2 < startY + height; y2++)
						if(c.getBlock(x2, y2, z2).getType() == Material.AIR)
							c.getBlock(x2, y2, z2).setType(Material.GRAVEL);

					height -= rand.nextInt(1);

					x2 += dir2.getModX();
					z2 += dir2.getModZ();
				}
			}

			ruins++;
		}
	}

    @Override
    public float getRoomPopulationChance() {
        // TODO: Improve this!
        return 1.0f;
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