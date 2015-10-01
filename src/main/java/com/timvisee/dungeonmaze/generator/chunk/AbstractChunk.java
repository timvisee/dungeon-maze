package com.timvisee.dungeonmaze.generator.chunk;

import org.bukkit.Material;
import org.bukkit.World;

@SuppressWarnings("UnusedDeclaration")
public abstract class AbstractChunk {

    /** The world of the chunk. */
    @SuppressWarnings("FieldCanBeLocal")
    private World world;
    /** Defines the width (and length) of the chunk in blocks. */
    private int chunkWidth;
    /** Defines the height of the chunk in blocks. */
    protected int chunkHeight;
    /** The X coordinate of the chunk. */
    private int chunkX;
    /** The Y coordinate of the chunk. */
    private int chunkZ;

    /** Defines the chunkWidth in blocks of a chunk. */
    public final static int CHUNK_BLOCK_WIDTH = 16;

    /**
     * Constructor.
     *
     * @param world The world.
     */
    public AbstractChunk(World world) {
        // Construct the parent
        super();

        // Initialize the fields
        this.chunkWidth = CHUNK_BLOCK_WIDTH;
        this.world = world;
        this.chunkHeight = world.getMaxHeight();
    }

    /**
     * Get the X coordinate of the chunk.
     *
     * @return The X coordinate of the chunk.
     */
    public int getChunkX() {
        return this.chunkX;
    }

    /**
     * Set the X coordinate of the chunk.
     * @param chunkX X coordinate of the chunk.
     */
    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    /**
     * Get the Z coordinate of the chunk.
     *
     * @return Z coordinate of the chunk.
     */
    public int getChunkZ() {
        return this.chunkZ;
    }

    /**
     * Set the Z coordinate of the chunk.
     *
     * @param chunkZ Z coordinate of the chunk.
     */
    public void setChunkZ(int chunkZ) {
        this.chunkZ = chunkZ;
    }

    /**
     * Get the X coordinate of a block in the specified chunk.
     *
     * @param chunkX The X coordinate of the chunk to get the block coordinate for.
     * @param x The X coordinate of the block relative to the chunk to get the block coordinate for.
     *
     * @return The X coordinate of the block.
     */
    public static int getBlockX(int chunkX, int x) {
        return chunkX * CHUNK_BLOCK_WIDTH + x;
    }

    /**
     * Get the X coordinate of a block in the specified chunk.
     *
     * @param chunk The chunk to get the block coordinate for.
     * @param x The X coordinate of the block relative to the chunk to get the block coordinate for.
     *
     * @return The X coordinate of the block.
     */
    public static int getBlockX(AbstractChunk chunk, int x) {
        return getBlockX(chunk.getChunkX(), x);
    }

    /**
     * Get the Z coordinate of a block in the specified chunk.
     *
     * @param chunkZ The Z coordinate of the chunk to get the block coordinate for.
     * @param z The Z coordinate of the block relative to the chunk to get the coordinate for.
     *
     * @return The Z coordinate of the block.
     */
    public static int getBlockZ(int chunkZ, int z) {
        return chunkZ * CHUNK_BLOCK_WIDTH + z;
    }

    /**
     * Get the Z coordinate of a block in the specified chunk.
     *
     * @param chunk The chunk to get the block coordinate for.
     * @param z The Z coordinate of the block relative to the chunk to get the coordinate for.
     *
     * @return The Z coordinate of the block.
     */
    public static int getBlockZ(AbstractChunk chunk, int z) {
        return getBlockZ(chunk.getChunkZ(), z);
    }

    /**
     * Get the X coordinate of a block inside the chunk.
     *
     * @param x The X coordinate of the block relative to this chunk.
     *
     * @return The X coordinate of the block.
     */
    public final int getBlockX(int x) {
        return getOriginX() + x;
    }

    /**
     * Get the Z coordinate of a block inside the chunk.
     *
     * @param z The Z coordinate of the block relative to this chunk.
     *
     * @return The Z coordinate of the block.
     */
    public final int getBlockZ(int z) {
        return getOriginZ() + z;
    }

    /**
     * Get the X coordinate of the block at the chunks origin.
     *
     * @return The X coordinate of the block at the chunks origin.
     */
    public final int getOriginX() {
        return chunkX * chunkWidth;
    }

    /**
     * Get the Z coordinate of the block at the chunks origin.
     *
     * @return The Z coordinate of the block at the chunks origin.
     */
    public final int getOriginZ() {
        return chunkZ * chunkWidth;
    }

    /**
     * Replace a block at the specified position.
     *
     * @param x The X coordinate of the block to set.
     * @param y The Y coordinate of the block to set.
     * @param z The Z coordinate of the block to set.
     * @param oldMaterial The old material to replace.
     * @param newMaterial The new material to set.
     *
     * @return True if the block was replaced, false otherwise.
     */
    public abstract boolean replaceBlock(int x, int y, int z, Material oldMaterial, Material newMaterial);

    /**
     * Set the block at the specified position.
     *
     * @param x The X coordinate of the block to set.
     * @param y The Y coordinate of the block to set.
     * @param z The Z coordinate of the block to set.
     * @param material The material to set.
     */
    public abstract void setBlock(int x, int y, int z, Material material);

    /**
     * Set a range of blocks.
     *
     * @param x1 The first X coordinate of the block range relative to the chunk's origin.
     * @param x2 The second X coordinate of the block range relative to the chunk's origin.
     * @param y1 The first Y coordinate of the block range relative to the chunk's origin.
     * @param y2 The second Y coordinate of the block range relative to the chunk's origin.
     * @param z1 The first Z coordinate of the block range relative to the chunk's origin.
     * @param z2 The second Z coordinate of the block range relative to the chunk's origin.
     * @param material The material to set the blocks to.
     */
    public abstract void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material);

    /**
     * Clear the specified block.
     *
     * @param x The X coordinate of the block to clear relative to the chunk's origin.
     * @param y The Y coordinate of the block to clear relative to the chunk's origin.
     * @param z The Z coordinate of the block to clear relative to the chunk's origin.
     */
    public abstract void clearBlock(int x, int y, int z);

    /**
     * Clear a range of blocks.
     *
     * @param x1 The first X coordinate of the block range relative to the chunk's origin.
     * @param x2 The second X coordinate of the block range relative to the chunk's origin.
     * @param y1 The first Y coordinate of the block range relative to the chunk's origin.
     * @param y2 The second Y coordinate of the block range relative to the chunk's origin.
     * @param z1 The first Z coordinate of the block range relative to the chunk's origin.
     * @param z2 The second Z coordinate of the block range relative to the chunk's origin.
     */
    public abstract void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2);

    /**
     * Set a block if it's currently empty (air).
     *
     * @param x The X coordinate of the block to set relative to the chunk's origin.
     * @param y The Y coordinate of the block to set relative to the chunk's origin.
     * @param z The Z coordinate of the block to set relative to the chunk's origin.
     * @param material The material to set the block to.
     *
     * @return True if the block was empty and changed, false if the block wasn't empty.
     */
    public abstract boolean setEmptyBlock(int x, int y, int z, Material material);

    /**
     * Set a range of blocks if it's currently empty (air).
     *
     * @param x1 The first X coordinate of the block range relative to the chunk's origin.
     * @param x2 The second X coordinate of the block range relative to the chunk's origin.
     * @param y1 The first Y coordinate of the block range relative to the chunk's origin.
     * @param y2 The second Y coordinate of the block range relative to the chunk's origin.
     * @param z1 The first Z coordinate of the block range relative to the chunk's origin.
     * @param z2 The second Z coordinate of the block range relative to the chunk's origin.
     * @param material The material to set the blocks to.
     */
    public abstract void setEmptyBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material);

    /**
     * Set a layer in the chunk.
     *
     * @param y The Y coordinate of the layer.
     * @param material The material to set the blocks to.
     */
    public abstract void setLayer(int y, Material material);

    /**
     * Set some layers in the chunk.
     *
     * @param y1 The first Y coordinate of the layer.
     * @param y2 The second Y coordinate of the layer.
     * @param material The material to set the blocks to.
     */
    public abstract void setLayers(int y1, int y2, Material material);

    /**
     * Find the first empty block at the specified position relative to the chunk's origin. If the specified block is
     * empty the lowest empty block will be returned. If the specified block is not empty, the first empty block above
     * will be returned.
     *
     * @param x The X coordinate of the block relative to the chunks origin.
     * @param y The Y coordinate of the block relative to the chunks origin.
     * @param z The Z coordinate of the block relative to the chunks origin.
     *
     * @return The Y coordinate of the first empty block.
     */
    public abstract int findFirstEmpty(int x, int y, int z);

    /**
     * Find the first empty block above the specified position relative to the chunk's origin.
     *
     * @param x The X coordinate of the block relative to the chunks origin.
     * @param y The Y coordinate of the block relative to the chunks origin.
     * @param z The Z coordinate of the block relative to the chunks origin.
     *
     * @return The Y coordinate of the first empty block above.
     */
    public abstract int findFirstEmptyAbove(int x, int y, int z);

    /**
     * Find the last empty block above the specified position relative to the chunk's origin.
     *
     * @param x The X coordinate of the block relative to the chunks origin.
     * @param y The Y coordinate of the block relative to the chunks origin.
     * @param z The Z coordinate of the block relative to the chunks origin.
     *
     * @return The Y coordinate of the last empty block above.
     */
    public abstract int findLastEmptyAbove(int x, int y, int z);

    /**
     * Find the last empty block below the specified position relative to the chunk's origin.
     *
     * @param x The X coordinate of the block relative to the chunks origin.
     * @param y The Y coordinate of the block relative to the chunks origin.
     * @param z The Z coordinate of the block relative to the chunks origin.
     *
     * @return The Y coordinate of the last empty block below.
     */
    public abstract int findLastEmptyBelow(int x, int y, int z);

    /**
     * Set all the blocks in the chunk.
     *
     * @param materialId The material ID to set the blocks to.
     */
    public abstract void setAllBlocks(short materialId);

    /**
     * Replace all blocks in the chunk.
     *
     * @param fromId The material ID of the blocks that need to be replaced.
     * @param toId The material ID to set the blocks to.
     */
    public abstract void replaceAllBlocks(short fromId, short toId);
}
