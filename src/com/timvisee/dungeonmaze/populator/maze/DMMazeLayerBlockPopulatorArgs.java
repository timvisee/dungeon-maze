package com.timvisee.dungeonmaze.populator.maze;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.timvisee.dungeonmaze.populator.DMChunkBlockPopulatorArgs;

public class DMMazeLayerBlockPopulatorArgs extends DMChunkBlockPopulatorArgs {

	int layer = 1;
	int y = 0;
	
	/**
	 * Constructor
	 * @param w World
	 * @param rand Random instance
	 * @param c Source chunk
	 * @param layer Layer
	 */
	public DMMazeLayerBlockPopulatorArgs(World w, Random rand, Chunk c, int layer, int y) {
		super(w, rand, c);
		this.layer = layer;
		this.y = y;
	}
	
	/**
	 * Get the current layer that should be generated
	 * @return Current layer
	 */
	public int getLayer() {
		return this.layer;
	}
	
	/**
	 * Set the current layer that should be generated
	 * @param layer 
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	/**
	 * Get the Y coord
	 * @return Y coord
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Get the Y coord in the current chunk
	 * @return Y coord in the current chunk
	 */
	public int getChunkY() {
		return this.getY();
	}
	
	/**
	 * Set the Y coord
	 * @param y Y coord
	 */
	public void setY(int y) {
		this.y = y;
	}
}
