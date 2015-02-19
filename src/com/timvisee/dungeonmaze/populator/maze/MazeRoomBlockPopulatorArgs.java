package com.timvisee.dungeonmaze.populator.maze;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

public class MazeRoomBlockPopulatorArgs extends MazeLayerBlockPopulatorArgs {

	/** Defines the X coordinate of the chunk. */
	private int x = 0;
	/** Defines the Z coordinate of the chunk. */
	private int z = 0;
	/** Defines the floor offset. */
	private int floorOffset = 0;
	/** Defines the ceiling offset. */
	private int ceilingOffset = 0;
	
	/**
	 * Constructor.
	 *
	 * @param w World
	 * @param rand Random instance
	 * @param c Source chunk
	 * @param layer Layer
	 * @param x X coord
	 * @param y Y coord
	 * @param z Z coord
	 * @param floorOffset Floor offset
	 * @param ceilingOffset Ceiling offset
	 */
	public MazeRoomBlockPopulatorArgs(World w, Random rand, Chunk c, int layer, int x, int y, int z, int floorOffset, int ceilingOffset) {
		super(w, rand, c, layer, y);
		this.x = x;
		this.z = z;
		this.floorOffset = floorOffset;
		this.ceilingOffset = ceilingOffset;
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
