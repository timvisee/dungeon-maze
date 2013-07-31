package com.timvisee.dungeonmaze.populator.maze;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.DungeonMaze;

public abstract class DMMazeRoomBlockPopulator extends DMMazeLayerBlockPopulator {
	
	/**
	 * Population method
	 * @param args Populator arguments
	 */
	@Override
	public void populateLayer(DMMazeLayerBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int l = args.getLayer();
		int y = args.getY();
		
		// The 4 rooms on each layer
		for(int chunkX = 0; chunkX < 16; chunkX += 8) {
			for(int chunkZ = 0; chunkZ < 16; chunkZ += 8) {

				// Make sure this room isn't constant
				if(DungeonMaze.instance.isConstantRoom(w.getName(), c, chunkX, y, chunkZ))
					continue;
				
				// Calculate the global X and Y coords
				int x = (c.getX() * 16) + chunkX;
				int z = (c.getZ() * 16) + chunkZ;
				
				// Get the floor and ceiling offset
				int floorOffset = getFloorOffset(chunkX, y, chunkZ, c);
				int ceilingOffset = getCeilingOffset(chunkX, y, chunkZ, c);
				
				// Construct the DMMazePopulatorArgs to use the the populateMaze method
				DMMazeRoomBlockPopulatorArgs newArgs = new DMMazeRoomBlockPopulatorArgs(w, rand, c, l, x, y, z, floorOffset, ceilingOffset);
				
				// Populate the maze
				populateRoom(newArgs);
			}
		}
	}
	
	/**
	 * Population method
	 * @param args Populator arguments
	 */
	public abstract void populateRoom(DMMazeRoomBlockPopulatorArgs args);
	
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
