package com.timvisee.dungeonmaze.populator.maze;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

public class DMMazeBlockPopulatorArgs {

	World w;
	Random rand;
	Chunk chunkSrc;
	int layer = 1;
	int x, y, z,
	floorOffset, ceilingOffset = 0;
	
	/**
	 * Constructor
	 * @param w World
	 * @param rand Random instance
	 * @param chunkSrc Source chunk
	 * @param layer Layer
	 * @param x X coord
	 * @param y Y coord
	 * @param z Z coord
	 * @param floorOffset Floor offset
	 * @param ceilingOffset Ceiling offset
	 */
	public DMMazeBlockPopulatorArgs(World w, Random rand, Chunk chunkSrc, int layer, int x, int y, int z, int floorOffset, int ceilingOffset) {
		this.w = w;
		this.rand = rand;
		this.chunkSrc = chunkSrc;
		this.layer = layer;
		this.x = x;
		this.y = y;
		this.z = z;
		this.floorOffset = floorOffset;
		this.ceilingOffset = ceilingOffset;
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
	 * Get the X coord
	 * @return X coord
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Get the X coord inside the current chunk
	 * @return X coord inside the current chunk
	 */
	public int getChunkX() {
		return (this.x % 16);
	}
	
	/**
	 * Set the X coord
	 * @param x X coord
	 */
	public void setX(int x) {
		this.x = x;
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
	
	/**
	 * Get the Z coord
	 * @return Z coord
	 */
	public int getZ() {
		return this.z;
	}
	
	/**
	 * Get the Z coord inside the current chunk
	 * @return Z coord inside the current chunk
	 */
	public int getChunkZ() {
		return (this.z % 16);
	}
	
	/**
	 * Set the Z coord
	 * @param z
	 */
	public void setZ(int z) {
		this.z = z;
	}
	
	/**
	 * Get the floor offset
	 * @return Floor offset
	 */
	public int getFloorOffset() {
		return this.floorOffset;
	}
	
	/**
	 * Set the floor offset
	 * @param floorOffset Floor offset
	 */
	public void setFloorOffset(int floorOffset) {
		this.floorOffset = floorOffset;
	}
	
	/**
	 * Get the floor Y coord (Y coord + floor offset)
	 * @return Floor Y coord
	 */
	public int getFloorY() {
		return (this.y + this.floorOffset);
	}
	
	/**
	 * Get the ceiling offset
	 * @return Ceiling offset
	 */
	public int getCeilingOffset() {
		return this.ceilingOffset;
	}
	
	/**
	 * Set the ceiling offset
	 * @param ceilingOffset Ceiling offset
	 */
	public void setCeilingOffset(int ceilingOffset) {
		this.ceilingOffset = ceilingOffset;
	}
	
	/**
	 * Get the ceiling Y coord
	 * @return Ceiling Y coord
	 */
	public int getCeilingY() {
		return (this.y + 6 + this.ceilingOffset);
	}
}
