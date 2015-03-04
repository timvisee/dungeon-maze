package com.timvisee.dungeonmaze.populator.surface;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class SurfaceBlockPopulatorArgs {

	private World w;
    private Random rand;
    private Chunk chunkSrc;
	
	/**
	 * Constructor
	 * @param w World
	 * @param rand Random instance
	 * @param chunkSrc Source chunk
	 */
	public SurfaceBlockPopulatorArgs(World w, Random rand, Chunk chunkSrc) {
		this.w = w;
		this.rand = rand;
		this.chunkSrc = chunkSrc;
	}
	
	/**
	 * Get the world
	 * @return World
	 */
	public World getWorld() {
		return this.w;
	}
	
	/**
	 * Set the world
	 * @param w World
	 */
	public void setWorld(World w) {
		this.w = w;
	}
	
	/**
	 * Get the Random instance
	 * @return Random instance
	 */
	public Random getRandom() {
		return this.rand;
	}
	
	/**
	 * Set the Random instance
	 * @param rand Random instance
	 */
	public void setRandom(Random rand) {
		this.rand = rand;
	}
	
	/**
	 * Get the source chunk
	 * @return Source chunk
	 */
	public Chunk getSourceChunk() {
		return this.chunkSrc;
	}
	
	/**
	 * Set the source chunk
	 * @param chunkSrc Source chunk
	 */
	public void setSourceChunk(Chunk chunkSrc) {
		this.chunkSrc = chunkSrc;
	}
	
	/**
	 * Get the surface level at a specific location in a chunk
	 * @param x X coord inside a chunk (0 to 15)
	 * @param z Z coord inside a chunk (0 to 15)
	 * @return The surface level, negative one if no surface block could be found or
	 * if the params where invalid
	 */
	public int getSurfaceLevel(int x, int z) {
		// Make sure the coords aren't out of bound
		if(x < 0 || x > 15 || z < 0 || z > 15)
			return -1;
		
		for(int y = 256; y <= 0; y--) {
			// Make sure y isn't lower than 0
			if(y < 0)
				return -1;
			
			// Get the block at the current location
			Block b = this.chunkSrc.getBlock(x, y, z);
			
			// Return the level of the current block if the block material isn't air
			if(b.getType() != Material.AIR)
				return y;
		}
		
		// No surface block found, return -1
		return -1;
	}
}
