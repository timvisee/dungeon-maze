package com.timvisee.dungeonmaze.generator;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

@SuppressWarnings({"UnusedDeclaration", "deprecation"})
public class MaterialUtils {

    /** Defines the material ID for air. */
    public final static short AIR_ID = getMaterialId(Material.AIR);

    /**
     * Get the ID of a material.
     *
     * @param material The material to get the ID for.
     *
     * @return The ID of the material.
     */
    public static short getMaterialId(Material material) {
        return (short) material.getId();
    }

    /**
     * Get the material ID of a block.
     *
     * @param block The block to get the material ID for.
     *
     * @return The material ID of the block.
     */
    public static short getMaterialId(Block block) {
        return (short) block.getTypeId();
    }

    /**
     * Get the material by it's ID.
     *
     * @param id Material ID.
     *
     * @return The material of the ID.
     */
    public static Material getMaterialById(int id) {
        return Material.getMaterial(id);
    }

    /**
     * Get the material data of a block.
     *
     * @param block The block to get the material data for.
     *
     * @return The material data.
     */
    public static short getMaterialData(Block block) {
        return (short) block.getData();
    }

    /**
     * Get the material data value as a short.
     *
     * @param data The material data to get the short value for.
     *
     * @return The short value of the material data.
     */
    public static short getMaterialData(MaterialData data) {
        return data.getData();
    }

    /**
     * Set the material of a block by a material ID.
     *
     * @param block The block to set.
     * @param materialId The material ID.
     */
    public static void setBlockType(Block block, int materialId) {
        block.setTypeId(materialId);
    }

    /**
     * Set the type of a block.
     *
     * @param block The block to set.
     * @param materialId The material ID to set the block to.
     * @param rawData The raw data.
     *
     * @return True on success, false on failure.
     */
    public static boolean setBlockType(Block block, int materialId, int rawData) {
        // Get the block state and set the material by ID
        BlockState state = block.getState();
        state.setTypeId(materialId);

        // Apply the raw data
        if(rawData != 0)
            state.setRawData((byte) (rawData & 0xff));

        // Check if the block update succeed, return the result
        return state.update(true);
    }

    /**
     * Set the material of a block.
     *
     * @param block The block to set the material for.
     * @param material The material to set the block to.
     *
     * @return True on success, false on failure.
     */
    public static boolean setBlockType(Block block, Material material) {
        return setBlockType(block, material, 0);
    }

    /**
     * Set the material of a block.
     *
     * @param block The block to set the material for.
     * @param material The material to set the block to.
     * @param rawData The raw data.
     *
     * @return True on success, false on failure.
     */
    public static boolean setBlockType(Block block, Material material, int rawData) {
        return setBlockType(block, material, rawData, true, true);
    }

    /**
     * Set the material of a block.
     *
     * @param block The block to set the material for.
     * @param material The material to set the block to.
     * @param rawData The raw data.
     * @param update True to update the block, false otherwise.
     * @param physics True to apply the block physics, false otherwise.
     *
     * @return True on success, false on failure.
     */
    public static boolean setBlockType(Block block, Material material, int rawData, boolean update, boolean physics) {
        // Get the block state, set the material
        BlockState state = block.getState();
        state.setType(material);

        // Apply the raw data
        if(rawData != 0)
            state.setRawData((byte) (rawData & 0xff));

        // Check if the block update succeed, return the result
        return state.update(update, physics);
    }

    /**
     * Set the color of a block.
     *
     * @param state The block state.
     * @param color The color to set.
     */
    public static void setBlockStateColor(BlockState state, DyeColor color) {
        state.setRawData(color.getWoolData());
    }

    /**
     * Check whether the specified material is empty (air).
     *
     * @param materialId The material ID to check.
     *
     * @return True if the material ID is empty (air), false otherwise.
     */
    public static boolean isEmpty(short materialId) {
        return materialId == MaterialUtils.AIR_ID;
    }

    /**
     * Check whether the specified material is empty (air).
     *
     * @param material The material to check.
     *
     * @return True if the material is empty (air), false otherwise.
     */
    public static boolean isEmpty(Material material) {
        return material == Material.AIR;
    }
}
