package com.timvisee.dungeonmaze.populator.maze;

import java.util.Random;

import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import org.bukkit.Chunk;
import org.bukkit.World;

import com.timvisee.dungeonmaze.populator.ChunkBlockPopulatorArgs;

public class MazeLayerBlockPopulatorArgs extends ChunkBlockPopulatorArgs {

	/** Defines the current layer number. */
    private int layer = 1;
	/** Defines the current Y coordinate of the room. */
    protected int y = 0;
	
	/**
	 * Constructor.
     *
	 * @param world World.
	 * @param rand Random instance.
	 * @param chunk Source chunk.
     * @param dungeonChunk Dungeon chunk.
	 * @param layer Layer.
	 */
	public MazeLayerBlockPopulatorArgs(World world, Random rand, Chunk chunk, DungeonChunk dungeonChunk, int layer, int y) {
		super(world, rand, chunk, dungeonChunk);
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
	 * @param layer The layer.
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	/**
	 * Get the Y coordinate.
     *
	 * @return Y coordinate.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Get the Y coordinate in the current chunk.
     *
	 * @return Y coordinate in the current chunk.
	 */
	public int getChunkY() {
		return this.getY();
	}
	
	/**
	 * Set the Y coordinate.
     *
	 * @param y Y coordinate.
	 */
	public void setY(int y) {
		this.y = y;
	}
}
