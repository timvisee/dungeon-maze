package com.timvisee.dungeonmaze.populator.maze;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.timvisee.dungeonmaze.populator.DMChunkBlockPopulator;
import com.timvisee.dungeonmaze.populator.DMChunkBlockPopulatorArgs;

public abstract class DMMazeLayerBlockPopulator extends DMChunkBlockPopulator {

	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int LAYER_AMOUNT = 7;
	
	@Override
	public void populateChunk(DMChunkBlockPopulatorArgs args) {
		World w = args.getWorld();
		Random rand = args.getRandom();
		Chunk c = args.getSourceChunk();
		
		// The layers
		for(int l = Math.max(getMinimumLayer(), 1); l <= Math.min(getMaximumLayer(), LAYER_AMOUNT); l++) {
			
			// Calculate the Y coord based on the current layer
			int y = 30 + ((l - 1) * 6);
			
			// Construct the DMMazePopulatorArgs to use the the populateMaze method
			DMMazeLayerBlockPopulatorArgs newArgs = new DMMazeLayerBlockPopulatorArgs(w, rand, c, l, y);
			
			// Populate the maze
			populateLayer(newArgs);
		}
	}
	
	/**
	 * Population method
	 * @param args Populator arguments
	 */
	public abstract void populateLayer(DMMazeLayerBlockPopulatorArgs args);
	
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

	public void populateLayer(DMMazeRoomBlockPopulatorArgs args) {
		// TODO Auto-generated method stub
		
	}
}
