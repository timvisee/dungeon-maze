package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class TorchPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 2;
	public static final int LAYER_MAX = 7;
	public static final float ROOM_CHANCE = .1f;

    // TODO: Implement this!
	public static final double CHANCE_TORCH_ADDITION_EACH_LEVEL = 3.333; /* to 30 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int z = args.getChunkZ();
        int torchX = x + rand.nextInt(6) + 1;
        int torchY = args.getFloorY() + 1;
        int torchZ = z + rand.nextInt(6) + 1;
								
        if(c.getBlock(torchX, torchY - 1, torchZ).getType() != Material.AIR) {
            Block torchBlock = c.getBlock(torchX, torchY, torchZ);
            if(torchBlock.getType() == Material.AIR) {
                torchBlock = c.getBlock(torchX, torchY, torchZ);
                torchBlock.setType(Material.TORCH);
            }
        }
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