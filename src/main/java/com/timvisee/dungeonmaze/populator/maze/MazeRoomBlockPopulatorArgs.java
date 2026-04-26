package com.timvisee.dungeonmaze.populator.maze;

import java.util.Random;

import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import org.bukkit.Chunk;
import org.bukkit.World;

public class MazeRoomBlockPopulatorArgs extends MazeLayerBlockPopulatorArgs {

	/** Defines the room origin X coordinate in the current chunk. */
	private int roomChunkX = 0;
	/** Defines the room origin Z coordinate in the current chunk. */
	private int roomChunkZ = 0;
	/** Defines the room origin X coordinate in the world. */
	private int x = 0;
	/** Defines the room origin Z coordinate in the world. */
	private int z = 0;
	/** Defines the floor offset. */
	private int floorOffset = 0;
	/** Defines the ceiling offset. */
	private int ceilingOffset = 0;
	
	/**
	 * Constructor.
	 *
	 * @param world World.
	 * @param rand Random instance.
	 * @param chunk Source chunk.
	 * @param layer Layer.
	 * @param x Room origin X coordinate in the current chunk.
	 * @param y Y coordinate.
	 * @param z Room origin Z coordinate in the current chunk.
	 * @param floorOffset Floor offset.
	 * @param ceilingOffset Ceiling offset.
	 */
	public MazeRoomBlockPopulatorArgs(World world, Random rand, Chunk chunk, DungeonChunk dungeonChunk, int layer, int x, int y, int z, int floorOffset, int ceilingOffset) {
		super(world, rand, chunk, dungeonChunk, layer, y);
		this.roomChunkX = x;
		this.roomChunkZ = z;
		this.x = (chunk.getX() * 16) + x;
		this.z = (chunk.getZ() * 16) + z;
		this.floorOffset = floorOffset;
		this.ceilingOffset = ceilingOffset;
	}
	
	/**
	 * Get the room origin X coordinate in the world.
     *
	 * @return Room origin X coordinate in the world.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Get the room origin X coordinate in the world.
     *
	 * @return Room origin X coordinate in the world.
	 */
	public int getWorldX() {
		return this.x;
	}
	
	/**
	 * Get the X coordinate inside the current chunk.
     *
	 * @return X coordinate inside the current chunk.
	 */
	public int getRoomChunkX() {
		return this.roomChunkX;
	}
	
	/**
	 * Set the room origin X coordinate in the world.
     *
	 * @param x Room origin X coordinate in the world.
	 */
	public void setX(int x) {
		this.x = x;
		this.roomChunkX = Math.floorMod(x, 16);
	}
	
	/**
	 * Get the room origin Z coordinate in the world.
     *
	 * @return Room origin Z coordinate in the world.
	 */
	public int getZ() {
		return this.z;
	}

	/**
	 * Get the room origin Z coordinate in the world.
     *
	 * @return Room origin Z coordinate in the world.
	 */
	public int getWorldZ() {
		return this.z;
	}
	
	/**
	 * Get the Z coordinate inside the current chunk.
     *
	 * @return Z coordinate inside the current chunk.
	 */
	public int getRoomChunkZ() {
		return this.roomChunkZ;
	}
	
	/**
	 * Set the room origin Z coordinate in the world.
     *
	 * @param z Room origin Z coordinate in the world.
	 */
	public void setZ(int z) {
		this.z = z;
		this.roomChunkZ = Math.floorMod(z, 16);
	}
	
	/**
	 * Get the floor offset.
     *
	 * @return Floor offset.
	 */
	public int getFloorOffset() {
		return this.floorOffset;
	}
	
	/**
	 * Set the floor offset.
     *
	 * @param floorOffset Floor offset.
	 */
	@SuppressWarnings("UnusedDeclaration")
    public void setFloorOffset(int floorOffset) {
		this.floorOffset = floorOffset;
	}
	
	/**
	 * Get the floor Y coordinate (Y coordinate + floor offset).
     *
	 * @return Floor Y coordinate.
	 */
	public int getFloorY() {
		return (this.y + this.floorOffset);
	}
	
	/**
	 * Get the ceiling offset.
     *
	 * @return Ceiling offset.
	 */
	@SuppressWarnings("UnusedDeclaration")
    public int getCeilingOffset() {
		return this.ceilingOffset;
	}
	
	/**
	 * Set the ceiling offset.
     *
	 * @param ceilingOffset Ceiling offset.
	 */
	@SuppressWarnings("UnusedDeclaration")
    public void setCeilingOffset(int ceilingOffset) {
		this.ceilingOffset = ceilingOffset;
	}
	
	/**
	 * Get the ceiling Y coordinate.
     *
	 * @return Ceiling Y coordinate.
	 */
	public int getCeilingY() {
		return (this.y + 6 + this.ceilingOffset);
	}
}
