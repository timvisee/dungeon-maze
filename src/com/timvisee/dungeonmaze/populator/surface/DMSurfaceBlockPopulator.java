package com.timvisee.dungeonmaze.populator.surface;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.dungeonmaze.DungeonMaze;

public abstract class DMSurfaceBlockPopulator extends BlockPopulator {
	
	@Override
	public void populate(World w, Random rand, Chunk chunkSrc) {
		// Make sure this chunk is not constant
		if(DungeonMaze.instance.isConstantChunk(w.getName(), chunkSrc))
			return;
			
		// Construct the DMMazePopulatorArgs to use the the populateMaze method
		DMSurfaceBlockPopulatorArgs args = new DMSurfaceBlockPopulatorArgs(w, rand, chunkSrc);
		
		// Populate the maze
		populateSurface(args);
	}
	
	/**
	 * Population method
	 * @param args Populator arguments
	 */
	public abstract void populateSurface(DMSurfaceBlockPopulatorArgs args);
}
