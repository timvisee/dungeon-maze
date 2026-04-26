package com.timvisee.dungeonmaze.generator.chunk;

import com.timvisee.dungeonmaze.util.MaterialUtils;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Arrays;

public class ShortChunk extends AbstractChunk {

    /**
     * Defines the chunk data (blocks).
     */
    private Material[][] blocks;

    /**
     * Defines the size in bytes of each block section.
     */
    private final static int BYTES_PER_SECTION = CHUNK_BLOCK_WIDTH * CHUNK_BLOCK_WIDTH * CHUNK_BLOCK_WIDTH;

    /**
     * Defines the number of block sections per chunk.
     */
    private final static int SECTIONS_PER_CHUNK = 16;

    private static Material[] createSection() {
        final Material[] section = new Material[BYTES_PER_SECTION];
        Arrays.fill(section, Material.AIR);
        return section;
    }

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
        this.blocks = new Material[SECTIONS_PER_CHUNK][];
    }

    /**
     * Get the chunk data (blocks).
     *
     * @return The chunk data.
     */
    public Material[][] getChunkData() {
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
    public Material getBlock(int x, int y, int z) {
        // Check whether the block is set, if not return Air
        if(this.blocks[y >> 4] == null)
            return Material.AIR;

        // Get and return the block material
        return blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
    }

    /**
     * Set the material of a block in the chunk.
     *
     * @param x The X coordinate of the block relative to the chunk's origin.
     * @param y The Y coordinate of the block relative to the chunk's origin.
     * @param z The Z coordinate of the block relative to the chunk's origin.
     * @param material The material to set the block to.
     */
    private void setBlockInternal(int x, int y, int z, Material material) {
        // Make sure the block section has been set, if not create it
        if(blocks[y >> 4] == null)
            blocks[y >> 4] = createSection();

        // Set the block material
        blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = material;
    }

    /**
     * Check whether a block in the chunk has a specific material.
     *
     * @param x The X coordinate of the block relative to the chunk's origin.
     * @param y The Y coordinate of the block relative to the chunk's origin.
     * @param z The Z coordinate of the block relative to the chunk's origin.
     * @param material The material to compare the block to.
     *
     * @return True if the block has the same material, false otherwise.
     */
    public boolean isType(int x, int y, int z, Material material) {
        return getBlock(x, y, z) == material;
    }

    /**
     * Check whether a block in the chunk has a specific material.
     *
     * @param x The X coordinate of the block relative to the chunk's origin.
     * @param y The Y coordinate of the block relative to the chunk's origin.
     * @param z The Z coordinate of the block relative to the chunk's origin.
     * @param materials A list of materials the block will be compared to.
     *
     * @return True if the material of the block exists in the materials list, false otherwise.
     */
    public boolean isType(int x, int y, int z, Material[] materials) {
        // Get the material of the block
        Material material = getBlock(x, y, z);

        // Check if the material list contains the material
        for(Material testMaterial : materials)
            if(material == testMaterial)
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
        // Make sure the current block equals the old material
        if(!isType(x, y, z, oldMaterial))
            return false;

        // Replace the block, return the result
        setBlock(x, y, z, newMaterial);
        return true;
    }

    @Override
    public void setBlock(int x, int y, int z, Material material) {
        setBlockInternal(x, y, z, material);
    }

    @Override
    public void clearBlock(int x, int y, int z) {
        // Clear a block if it isn't cleared already
        if(blocks[y >> 4] != null)
            blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = Material.AIR;
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
     * @param material The material to set the blocks to.
     */
    private void setBlocksInternal(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
        for(int x = x1; x <= x2; x++)
            for(int z = z1; z <= z2; z++)
                for(int y = y1; y <= y2; y++)
                    setBlock(x, y, z, material);
    }

    @Override
    public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
        setBlocksInternal(x1, x2, y1, y2, z1, z2, material);
    }

    @Override
    public final boolean setEmptyBlock(int x, int y, int z, Material material) {
        // Make sure the block is empty (air)
        if(!isEmpty(x, y, z))
            return false;

        // Set the block, return the result
        setBlock(x, y, z, material);
        return true;
    }

    @Override
    public void setEmptyBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
        // Set all empty blocks
        for(int x = x1; x <= x2; x++)
            for(int z = z1; z <= z2; z++)
                for(int y = y1; y <= y2; y++)
                    setEmptyBlock(x, y, z, material);
    }

    @Override
    public void setLayer(int y, Material material) {
        // Set the layer
        for(int x = 0; x < CHUNK_BLOCK_WIDTH; x++)
            for(int z = 0; z < CHUNK_BLOCK_WIDTH; z++)
                setBlock(x, y, z, material);
    }

    @Override
    public void setLayers(int y1, int y2, Material material) {
        // Loop through each layer
        for(int y = y1; y <= y2; y++)
            setLayer(y, material);
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
    public void setAllBlocks(Material material) {
        // Clear the blocks array if the chunks needs to be cleared
        if(MaterialUtils.isEmpty(material)) {
            for(int c = 0; c < SECTIONS_PER_CHUNK; c++)
                blocks[c] = null;

        } else {
            // Set all blocks
            for(int c = 0; c < SECTIONS_PER_CHUNK; c++) {
                if(blocks[c] == null)
                    blocks[c] = createSection();
                Arrays.fill(blocks[c], 0, BYTES_PER_SECTION, material);
            }
        }
    }

    @Override
    public void replaceAllBlocks(Material fromMaterial, Material toMaterial) {
        // Loop through each section
        for(int c = 0; c < SECTIONS_PER_CHUNK; c++) {
            // Use a more efficient method when replacing empty blocks
            if(MaterialUtils.isEmpty(fromMaterial)) {
                // Create the block section if it doesn't exist yet
                if(blocks[c] == null)
                    blocks[c] = createSection();

                // Loop through all the blocks in the section
                for(int i = 0; i < BYTES_PER_SECTION; i++)
                    // Replace the block if the current material equals fromMaterial
                    if(blocks[c][i] == fromMaterial)
                        blocks[c][i] = toMaterial;

            // Replace the blocks
            } else {
                // Make sure the block section is set
                if(blocks[c] != null) {
                    // Loop through all the blocks in the section
                    for(int i = 0; i < BYTES_PER_SECTION; i++)
                        // Replace the block if the current material equals fromMaterial
                        if(blocks[c][i] == fromMaterial)
                            blocks[c][i] = toMaterial;
                }
            }
        }
    }
}
