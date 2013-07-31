package com.timvisee.dungeonmaze.populator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

public class DMChunkBlockPopulatorArgs {

	World w;
	Random rand;
	Chunk chunkSrc;
	
	/**
	 * Constructor
	 * @param w World
	 * @param rand Random instance
	 * @param c Source chunk
	 */
	public DMChunkBlockPopulatorArgs(World w, Random rand, Chunk c) {
		this.w = w;
		this.rand = rand;
		this.chunkSrc = c;
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
}
