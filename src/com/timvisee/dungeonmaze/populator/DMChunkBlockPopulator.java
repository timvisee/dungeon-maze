package com.timvisee.dungeonmaze.populator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.dungeonmaze.DungeonMaze;

public abstract class DMChunkBlockPopulator extends BlockPopulator {
	
	@Override
	public void populate(World w, Random rand, Chunk c) {
		// Make sure this chunk is not constant
		if(DungeonMaze.instance.isConstantChunk(w.getName(), c))
			return;
		
		// Construct the DMMazeBlockPopulatorArgs to use the the populateMaze method
		DMChunkBlockPopulatorArgs args = new DMChunkBlockPopulatorArgs(w, rand, c);
		
		// Populate the maze
		populateChunk(args);
	}
	
	/**
	 * Population method
	 * @param args Populator arguments
	 */
	public abstract void populateChunk(DMChunkBlockPopulatorArgs args);
}
