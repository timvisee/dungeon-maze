package com.timvisee.dungeonmaze.populator.maze;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.dungeonmaze.DungeonMaze;

public abstract class DMMazeBlockPopulator extends BlockPopulator {

	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int LAYER_AMOUNT = 7;
	
	@Override
	public void populate(World w, Random rand, Chunk chunkSrc) {
		// Make sure this chunk is not constant
		if(DungeonMaze.instance.isConstantChunk(w.getName(), chunkSrc))
			return;
		
		// The layers
		for(int l = Math.max(getMinimumLayer(), 1); l <= Math.min(getMaximumLayer(), LAYER_AMOUNT); l++) {
			
			// Calculate the Y coord based on the current layer
			int y = 30 + ((l - 1) * 6);
			
			// The 4 rooms on each layer
			for(int chunkX = 0; chunkX < 16; chunkX += 8) {
				for(int chunkZ = 0; chunkZ < 16; chunkZ += 8) {

					// Make sure this room isn't constant
					if(DungeonMaze.instance.isConstantRoom(w.getName(), chunkSrc, chunkX, y, chunkZ))
						continue;
					
					// Calculate the global X and Y coords
					int x = (chunkSrc.getX() * 16) + chunkX;
					int z = (chunkSrc.getZ() * 16) + chunkZ;
					
					// Get the floor and ceiling offset
					int floorOffset = getFloorOffset(chunkX, y, chunkZ, chunkSrc);
					int ceilingOffset = getCeilingOffset(chunkX, y, chunkZ, chunkSrc);
					
					// Construct the DMMazePopulatorArgs to use the the populateMaze method
					DMMazeBlockPopulatorArgs args = new DMMazeBlockPopulatorArgs(w, rand, chunkSrc, l, x, y, z, floorOffset, ceilingOffset);
					
					// Populate the maze
					populateMaze(args);
				}
			}
		}
	}
	
	/**
	 * Population method
	 * @param args Populator arguments
	 */
	public abstract void populateMaze(DMMazeBlockPopulatorArgs args);
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	public int getMinimumLayer() {
		return MIN_LAYER;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	public int getMaximumLayer() {
		return MAX_LAYER;
	}
	
	/**
	 * Get the floor offset in a specific room
	 * @param x X coord
	 * @param y Y coord
	 * @param z Z coord
	 * @param c Chunk
	 * @return Floor offset
	 */
	private int getFloorOffset(int x, int y, int z, Chunk c) {
		Block testBlock = c.getBlock(x + 3, y, z + 3);
		int typeId = testBlock.getTypeId();
		
		// x and z +2 so that you aren't inside a wall!
		if(!(typeId == 4 || typeId == 48 ||
				typeId == 87 || typeId == 88))
			return 1;
		
		return 0;
	}
	
	/**
	 * Get the ceiling offset in a specific room
	 * @param x X coord
	 * @param y Y coord
	 * @param z Z coord
	 * @param c Chunk
	 * @return Ceiling offset
	 */
	private int getCeilingOffset(int x, int y, int z, Chunk c) {
		Block testBlock = c.getBlock(x + 3, y + 6, z + 3);
		int typeId = testBlock.getTypeId();
		
		// x and z +2 so that you aren't inside a wall!
		if(!(typeId == 4 || typeId == 48 ||
				typeId == 87 || typeId == 88))
			return 1;
		
		return 0;
	}
}
