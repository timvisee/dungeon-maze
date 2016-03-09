package com.timvisee.dungeonmaze.generator.chunk;

import com.timvisee.dungeonmaze.util.MaterialUtils;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Arrays;

public class ShortChunk extends AbstractChunk {

    /**
     * Defines the chunk data (blocks).
     */
    private short[][] blocks;

    /**
     * Defines the size in bytes of each block section.
     */
    private final static int BYTES_PER_SECTION = CHUNK_BLOCK_WIDTH * CHUNK_BLOCK_WIDTH * CHUNK_BLOCK_WIDTH;

    /**
     * Defines the number of block sections per chunk.
     */
    private final static int SECTIONS_PER_CHUNK = 16;

    /**
     * Constructor.
     *
     * @param world The world of the chunk.
     * @param chunkX The X coordinate of the chunk.
     * @param chunkZ The Z coordinate of the chunk.
     */
    public ShortChunk(World world, int chunkX, int chunkZ) {
        // Construct the parent
        super(world);

        // Set the chunk coordinates
        setChunkX(chunkX);
        setChunkZ(chunkZ);

        // Instantiate the blocks array
        this.blocks = new short[SECTIONS_PER_CHUNK][];
    }

    /**
     * Get the chunk data (blocks).
     *
     * @return The chunk data.
     */
    public short[][] getChunkData() {
        return this.blocks;
    }

    /**
     * Get the block material from the chunk.
     *
     * @param x The X coordinate of the block relative to the chunk's origin.
     * @param y The Y coordinate of the block relative to the chunk's origin.
     * @param z The Z coordinate of the block relative to the chunk's origin.
     *
     * @return The block material.
     */
    public short getBlock(int x, int y, int z) {
        // Check whether the block is set, if not return Air
        if(this.blocks[y >> 4] == null)
            return MaterialUtils.AIR_ID;

        // Get and return the block material
        return blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
    }

    /**
     * Set the material of a block in the chunk.
     *
     * @param x The X coordinate of the block relative to the chunk's origin.
     * @param y The Y coordinate of the block relative to the chunk's origin.
     * @param z The Z coordinate of the block relative to the chunk's origin.
     * @param materialId The material ID to set the block to.
     */
    public void setBlock(int x, int y, int z, short materialId) {
        // Make sure the block section has been set, if not create it
        if(blocks[y >> 4] == null)
            blocks[y >> 4] = new short[BYTES_PER_SECTION];

        // Set the block material
        blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = materialId;
    }

    /**
     * Check whether a block in the chunk has a specific material.
     *
     * @param x The X coordinate of the block relative to the chunk's origin.
     * @param y The Y coordinate of the block relative to the chunk's origin.
     * @param z The Z coordinate of the block relative to the chunk's origin.
     * @param materialId The material ID to compare the block to.
     *
     * @return True if the block has the same material ID, false otherwise.
     */
    public boolean isType(int x, int y, int z, short materialId) {
        return getBlock(x, y, z) == materialId;
    }

    /**
     * Check whether a block in the chunk has a specific material.
     *
     * @param x The X coordinate of the block relative to the chunk's origin.
     * @param y The Y coordinate of the block relative to the chunk's origin.
     * @param z The Z coordinate of the block relative to the chunk's origin.
     * @param materialIds A list of material ID's the block will be compared to.
     *
     * @return True if the material ID of the block exists in the material ID's list, false otherwise.
     */
    public boolean isType(int x, int y, int z, short[] materialIds) {
        // Get the material ID of the block
        short materialId = getBlock(x, y, z);

        // Check if the material ID's list contains the ID
        for(short testId : materialIds)
            if(materialId == testId)
                return true;

        // The material ID isn't in the list, return false
        return false;
    }

    /**
     * Check whether a block in the chunk is empty (is air).
     *
     * @param x The X coordinate of the block relative to the chunk's origin.
     * @param y The Y coordinate of the block relative to the chunk's origin.
     * @param z The Z coordinate of the block relative to the chunk's origin.
     *
     * @return True if the specified block is empty, false otherwise.
     */
    public boolean isEmpty(int x, int y, int z) {
        return MaterialUtils.isEmpty(getBlock(x, y, z));
    }

    @Override
    public boolean replaceBlock(int x, int y, int z, Material oldMaterial, Material newMaterial) {
        return replaceBlock(x, y, z, MaterialUtils.getMaterialId(oldMaterial), MaterialUtils.getMaterialId(newMaterial));
    }

    /**
     * Replace a block at the specified position.
     *
     * @param x The X coordinate of the block to set.
     * @param y The Y coordinate of the block to set.
     * @param z The Z coordinate of the block to set.
     * @param oldId The old material ID to replace.
     * @param newId The new material ID to set.
     *
     * @return True if the block was replaced, false otherwise.
     */
    public boolean replaceBlock(int x, int y, int z, short oldId, short newId) {
        // Make sure the current block equals the old material
        if(!isType(x, y, z, oldId))
            return false;

        // Replace the block, return the result
        setBlock(x, y, z, newId);
        return true;
    }

    @Override
    public void setBlock(int x, int y, int z, Material material) {
        setBlock(x, y, z, MaterialUtils.getMaterialId(material));
    }

    @Override
    public void clearBlock(int x, int y, int z) {
        // Clear a block if it isn't cleared already
        if(blocks[y >> 4] != null)
            blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = MaterialUtils.AIR_ID;
    }

    @Override
    public void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2) {
        // Clear all the blocks in the range
        for(int x = x1; x <= x2; x++)
            for(int z = z1; z <= z2; z++)
                for(int y = y1; y <= y2; y++)
                    clearBlock(x, y, z);
    }

    /**
     * Set a range of blocks.
     *
     * @param x1 The first X coordinate of the block range relative to the chunk's origin.
     * @param x2 The second X coordinate of the block range relative to the chunk's origin.
     * @param y1 The first Y coordinate of the block range relative to the chunk's origin.
     * @param y2 The second Y coordinate of the block range relative to the chunk's origin.
     * @param z1 The first Z coordinate of the block range relative to the chunk's origin.
     * @param z2 The second Z coordinate of the block range relative to the chunk's origin.
     * @param materialId The material ID to set the blocks to.
     */
    public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, short materialId) {
        for(int x = x1; x <= x2; x++)
            for(int z = z1; z <= z2; z++)
                for(int y = y1; y <= y2; y++)
                    setBlock(x, y, z, materialId);
    }

    @Override
    public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
        setBlocks(x1, x2, y1, y2, z1, z2, MaterialUtils.getMaterialId(material));
    }

    @Override
    public final boolean setEmptyBlock(int x, int y, int z, Material material) {
        return setEmptyBlock(x, y, z, MaterialUtils.getMaterialId(material));
    }

    /**
     * Set a block if it's empty (air).
     *
     * @param x The X coordinate of the block relative to the chunk's origin.
     * @param y The Y coordinate of the block relative to the chunk's origin.
     * @param z The Z coordinate of the block relative to the chunk´s origin.
     * @param materialId The material ID to set the block to.
     *
     * @return True if the block was empty and changed, false if the block wasn't empty.
     */
    public boolean setEmptyBlock(int x, int y, int z, short materialId) {
        // Make sure the block is empty (air)
        if(!isEmpty(x, y, z))
            return false;

        // Set the block, return the result
        setBlock(x, y, z, materialId);
        return true;
    }

    @Override
    public void setEmptyBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
        // Get the material ID
        short materialId = MaterialUtils.getMaterialId(material);

        // Set all empty blocks
        for(int x = x1; x <= x2; x++)
            for(int z = z1; z <= z2; z++)
                for(int y = y1; y <= y2; y++)
                    setEmptyBlock(x, y, z, materialId);
    }

    @Override
    public void setLayer(int y, Material material) {
        setLayer(y, MaterialUtils.getMaterialId(material));
    }

    /**
     * Set a layer in the chunk.
     *
     * @param y The Y coordinate of the layer.
     * @param materialId The material ID to set the blocks to.
     */
    public void setLayer(int y, short materialId) {
        // Set the layer
        for(int x = 0; x <= CHUNK_BLOCK_WIDTH; x++)
            for(int z = 0; z <= CHUNK_BLOCK_WIDTH; z++)
                setBlock(x, y, z, materialId);
    }

    @Override
    public void setLayers(int y1, int y2, Material material) {
        setLayers(y1, y2, MaterialUtils.getMaterialId(material));
    }

    /**
     * Set some layers in the chunk.
     *
     * @param y1 The first Y coordinate of the layer.
     * @param y2 The second Y coordinate of the layer.
     * @param materialId The material ID to set the blocks to.
     */
    public void setLayers(int y1, int y2, short materialId) {
        // Loop through each layer
        for(int y = y1; y <= y2; y++)
            setLayer(y, materialId);
    }

    @Override
    public final int findFirstEmpty(int x, int y, int z) {
        // Check whether the specified block is empty, iterate down if that's the case
        if(isEmpty(x, y, z))
            return findLastEmptyBelow(x, y, z);
        return findFirstEmptyAbove(x, y, z);
    }

    @Override
    public final int findFirstEmptyAbove(int x, int y, int z) {
        int yy = y;

        while(yy < this.chunkHeight - 1) {
            if(isEmpty(x, yy, z))
                return yy;

            yy++;
        }

        return this.chunkHeight - 1;
    }

    @Override
    public int findLastEmptyAbove(int x, int y, int z) {
        int yy = y;

        while(yy < this.chunkHeight - 1 && isEmpty(x, yy + 1, z))
            yy++;

        return yy;
    }

    @Override
    public int findLastEmptyBelow(int x, int y, int z) {
        int yy = y;

        while(yy > 0 && isEmpty(x, yy - 1, z))
            yy--;

        return yy;
    }


    @Override
    public void setAllBlocks(short materialId) {
        // Clear the blocks array if the chunks needs to be cleared
        if(MaterialUtils.isEmpty(materialId)) {
            for(int c = 0; c < SECTIONS_PER_CHUNK; c++)
                blocks[c] = null;

        } else {
            // Set all blocks
            for(int c = 0; c < SECTIONS_PER_CHUNK; c++) {
                if(blocks[c] == null)
                    blocks[c] = new short[BYTES_PER_SECTION];
                Arrays.fill(blocks[c], 0, BYTES_PER_SECTION, materialId);
            }
        }
    }

    @Override
    public void replaceAllBlocks(short fromId, short toId) {
        // Loop through each section
        for(int c = 0; c < SECTIONS_PER_CHUNK; c++) {
            // Use a more efficient method when replacing empty blocks
            if(MaterialUtils.isEmpty(fromId)) {
                // Create the block section if it doesn't exist yet
                if(blocks[c] == null)
                    blocks[c] = new short[BYTES_PER_SECTION];

                // Loop through all the blocks in the section
                for(int i = 0; i < BYTES_PER_SECTION; i++)
                    // Replace the block if the current material ID equals fromId
                    if(blocks[c][i] == fromId)
                        blocks[c][i] = toId;

            // Replace the blocks
            } else {
                // Make sure the block section is set
                if(blocks[c] != null) {
                    // Loop through all the blocks in the section
                    for(int i = 0; i < BYTES_PER_SECTION; i++)
                        // Replace the block if the current material ID equals fromId
                        if(blocks[c][i] == fromId)
                            blocks[c][i] = toId;
                }
            }
        }
    }
}
