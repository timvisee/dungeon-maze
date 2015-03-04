package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class HighRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 6;
	public static final float ROOM_CHANCE = .006f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final int x = args.getChunkX();
		final int y = args.getChunkY();
		final int z = args.getChunkZ();

        // Register the room above as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y + 6, z);

        // Remove the floor of the room above
        for(int x2 = x; x2 <= x + 7; x2 += 1)
            for(int y2 = y + 5; y2 <= y + 8; y2 += 1)
                for(int z2 = z + 1; z2 <= z + 6; z2 += 1)
                    chunk.getBlock(x2, y2, z2).setType(Material.AIR);

        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int y2 = y + 5; y2 <= y + 8; y2 += 1)
                for(int z2 = z; z2 <= z + 7; z2 += 1)
                    chunk.getBlock(x2, y2, z2).setType(Material.AIR);
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