package com.timvisee.dungeonmaze.populator.maze;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.timvisee.dungeonmaze.populator.ChunkBlockPopulator;
import com.timvisee.dungeonmaze.populator.ChunkBlockPopulatorArgs;

public abstract class MazeLayerBlockPopulator extends ChunkBlockPopulator {

	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int LAYER_AMOUNT = 7;
	
	@Override
	public void populateChunk(ChunkBlockPopulatorArgs args) {
		World w = args.getWorld();
		Random rand = args.getRandom();
		Chunk c = args.getSourceChunk();
		
		// The layers
		for(int l = Math.max(getMinimumLayer(), 1); l <= Math.min(getMaximumLayer(), LAYER_AMOUNT); l++) {
			
			// Calculate the Y coord based on the current layer
			int y = 30 + ((l - 1) * 6);
			
			// Construct the DMMazePopulatorArgs to use the the populateMaze method
			MazeLayerBlockPopulatorArgs newArgs = new MazeLayerBlockPopulatorArgs(w, rand, c, l, y);
			
			// Populate the maze
			populateLayer(newArgs);
		}
	}
	
	/**
	 * Population method
	 * @param args Populator arguments
	 */
	public abstract void populateLayer(MazeLayerBlockPopulatorArgs args);
	
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

	public void populateLayer(MazeRoomBlockPopulatorArgs args) {
		// TODO Auto-generated method stub
		
	}
}
