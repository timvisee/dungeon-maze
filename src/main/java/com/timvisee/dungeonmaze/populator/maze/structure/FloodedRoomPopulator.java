package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class FloodedRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
	public static final float ROOM_CHANCE = .005f;

    /** Populator constants. */
	public static final int CHANCE_WATER = 33; // If it's no water it will be lava

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int z = args.getChunkZ();

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, y, z);

        // Walls
        for(int x2=x; x2 <= x + 7; x2+=1) {
            for(int y2= yFloor; y2 <= y + 6; y2+=1) {
                if(chunk.getBlock(x2, y2, z).getType() != Material.COBBLESTONE && chunk.getBlock(x2, y2, z).getType() != Material.MOSSY_COBBLESTONE)
                    chunk.getBlock(x2, y2, z).setType(Material.SMOOTH_BRICK);

                if(chunk.getBlock(x2, y2, z + 7).getType() != Material.COBBLESTONE && chunk.getBlock(x2, y2, z + 7).getType() != Material.MOSSY_COBBLESTONE)
                    chunk.getBlock(x2, y2, z + 7).setType(Material.SMOOTH_BRICK);

                if(chunk.getBlock(x, y2, x2).getType() != Material.COBBLESTONE && chunk.getBlock(x, y2, x2).getType() != Material.MOSSY_COBBLESTONE)
                    chunk.getBlock(x, y2, x2).setType(Material.SMOOTH_BRICK);

                if(chunk.getBlock(x + 7, y2, x2).getType() != Material.COBBLESTONE && chunk.getBlock(x + 7, y2, x2).getType() != Material.MOSSY_COBBLESTONE)
                    chunk.getBlock(x + 7, y2, x2).setType(Material.SMOOTH_BRICK);
            }
        }

        // Fill the room with lava or water
        Material type = Material.LAVA;
        if(rand.nextInt(100) < CHANCE_WATER)
            type = Material.WATER;

        for (int x2=x + 1; x2 <= x + 6; x2+=1)
            for (int y2 = yFloor + 1; y2 <= y + 5; y2+=1)
                for (int z2=z + 1; z2 <= z + 6; z2+=1)
                    chunk.getBlock(x2, y2, z2).setType(type);
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