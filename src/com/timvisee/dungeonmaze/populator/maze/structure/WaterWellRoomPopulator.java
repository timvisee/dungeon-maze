package com.timvisee.dungeonmaze.populator.maze.structure;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class WaterWellRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 3;
	public static final int LAYER_MAX = 7;
	public static final float ROOM_CHANCE = .002f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final int x = args.getChunkX();
		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int z = args.getChunkZ();
			
        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, y, z);

        // Floor
        for (int x2=x; x2 <= x + 7; x2+=1)
            for (int z2=z; z2 <= z + 7; z2+=1)
                chunk.getBlock(x2,yFloor, z2).setType(Material.STONE);

        // Floor (cobblestone underneath the stone floor)
        for (int x2=x; x2 <= x + 7; x2+=1)
            for (int z2=z; z2 <= z + 7; z2+=1)
                chunk.getBlock(x2,yFloor - 1, z2).setType(Material.COBBLESTONE);

        // Well
        for (int x2=x + 2; x2 <= x + 4; x2+=1)
            for (int z2=z + 2; z2 <= z + 4; z2+=1)
                chunk.getBlock(x2,yFloor + 1, z2).setType(Material.SMOOTH_BRICK);

        chunk.getBlock(x + 3,yFloor + 1, z + 3).setType(Material.STATIONARY_WATER);

        // Poles
        chunk.getBlock(x + 2,yFloor + 2, z + 2).setType(Material.FENCE);
        chunk.getBlock(x + 2,yFloor + 2, z + 4).setType(Material.FENCE);
        chunk.getBlock(x + 4,yFloor + 2, z + 2).setType(Material.FENCE);
        chunk.getBlock(x + 4,yFloor + 2, z + 4).setType(Material.FENCE);

        // Roof
        chunk.getBlock(x + 2,yFloor + 3, z + 2).setType(Material.STEP);
        chunk.getBlock(x + 2,yFloor + 3, z + 2).setData((byte) 2);
        chunk.getBlock(x + 2,yFloor + 3, z + 3).setType(Material.WOOD_STAIRS);
        chunk.getBlock(x + 2,yFloor + 3, z + 3).setData((byte) 0);
        chunk.getBlock(x + 2,yFloor + 3, z + 4).setType(Material.STEP);
        chunk.getBlock(x + 2,yFloor + 3, z + 4).setData((byte) 2);
        chunk.getBlock(x + 3,yFloor + 3, z + 2).setType(Material.WOOD_STAIRS);
        chunk.getBlock(x + 3,yFloor + 3, z + 2).setData((byte) 2);
        chunk.getBlock(x + 3,yFloor + 3, z + 3).setType(Material.GLOWSTONE);
        chunk.getBlock(x + 3,yFloor + 3, z + 4).setType(Material.WOOD_STAIRS);
        chunk.getBlock(x + 3,yFloor + 3, z + 4).setData((byte) 3);
        chunk.getBlock(x + 4,yFloor + 3, z + 2).setType(Material.STEP);
        chunk.getBlock(x + 4,yFloor + 3, z + 2).setData((byte) 2);
        chunk.getBlock(x + 4,yFloor + 3, z + 3).setType(Material.WOOD_STAIRS);
        chunk.getBlock(x + 4,yFloor + 3, z + 3).setData((byte) 1);
        chunk.getBlock(x + 4,yFloor + 3, z + 4).setType(Material.STEP);
        chunk.getBlock(x + 4,yFloor + 3, z + 4).setData((byte) 2);
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