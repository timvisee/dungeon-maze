package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class SanctuaryPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 1;
	public static final float ROOM_CHANCE = .003f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		final int x = args.getChunkX();
		final int yFloor = args.getFloorY();
		final int z = args.getChunkZ();
			
        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(w.getName(), c.getX(), c.getZ(), x, 30, z);

        for (int x2=x; x2 < x+8; x2+=1)
            for (int z2=z; z2 < z+8; z2+=1)
                c.getBlock(x2, yFloor, z2).setType(Material.OBSIDIAN);

        // Outline altar right
        c.getBlock(x + 2, yFloor + 1, z + 2).setType(Material.GOLD_BLOCK);
        c.getBlock(x + 3, yFloor + 1, z + 2).setType(Material.NETHERRACK);
        c.getBlock(x + 4, yFloor + 1, z + 2).setType(Material.NETHERRACK);
        c.getBlock(x + 5, yFloor + 1, z + 2).setType(Material.GOLD_BLOCK);

        // Center altar
        c.getBlock(x + 2, yFloor + 1, z + 3).setType(Material.NETHERRACK);
        c.getBlock(x + 3, yFloor + 1, z + 3).setType(Material.SOUL_SAND);
        c.getBlock(x + 4, yFloor + 1, z + 3).setType(Material.SOUL_SAND);
        c.getBlock(x + 5, yFloor + 1, z + 3).setType(Material.NETHERRACK);

        // Outline altar left
        c.getBlock(x + 2, yFloor + 1, z + 4).setType(Material.GOLD_BLOCK);
        c.getBlock(x + 3, yFloor + 1, z + 4).setType(Material.NETHERRACK);
        c.getBlock(x + 4, yFloor + 1, z + 4).setType(Material.NETHERRACK);
        c.getBlock(x + 5, yFloor + 1, z + 4).setType(Material.GOLD_BLOCK);

        // Torches
        c.getBlock(x + 2, yFloor + 2, z + 2).setType(Material.TORCH);
        c.getBlock(x + 5, yFloor + 2, z + 2).setType(Material.TORCH);
        c.getBlock(x + 2, yFloor + 2, z + 4).setType(Material.TORCH);
        c.getBlock(x + 5, yFloor + 2, z + 4).setType(Material.TORCH);
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