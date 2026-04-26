package com.timvisee.dungeonmaze.util;

import java.util.EnumSet;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("UnusedDeclaration")
public class MaterialUtils {

    /**
     * Set the material of a block.
     *
     * @param block The block to set the material for.
     * @param material The material to set the block to.
     *
     * @return True on success, false on failure.
     */
    public static boolean setBlockType(Block block, Material material) {
        block.setType(material);
        return true;
    }

    public static ItemStack createItemStack(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public static ItemStack createItemStack(Material material, int amount, short legacyData) {
        if(legacyData == 0)
            return createItemStack(material, amount);

        if(material == Material.COAL && legacyData == 1)
            return createItemStack(Material.CHARCOAL, amount);

        if(material == Material.INK_SAC && legacyData == 3)
            return createItemStack(Material.COCOA_BEANS, amount);

        throw new IllegalArgumentException("Unsupported legacy item data " + material + ":" + legacyData);
    }

    public static boolean setBlockType(Block block, String... materialNames) {
        return setBlockType(block, requireBlockMaterial(materialNames));
    }

    public static Material requireBlockMaterial(String... materialNames) {
        if(materialNames == null || materialNames.length == 0)
            throw new IllegalArgumentException("At least one material name must be provided");

        for(String materialName : materialNames) {
            if(materialName == null || materialName.isEmpty())
                continue;

            try {
                final Material material = Material.valueOf(materialName);
                if(material.isBlock())
                    return material;
            } catch(IllegalArgumentException ignored) {
                // Try the next material alias.
            }
        }

        throw new IllegalArgumentException("No compatible block material found for aliases: " + String.join(", ", materialNames));
    }

    public static boolean setChestFacing(Block block, BlockFace facing) {
        return setDirectionalBlock(block, Material.CHEST, facing);
    }

    public static boolean setFurnaceFacing(Block block, BlockFace facing) {
        return setDirectionalBlock(block, Material.FURNACE, facing);
    }

    public static boolean setLadderFacing(Block block, BlockFace facing) {
        return setDirectionalBlock(block, Material.LADDER, facing);
    }

    public static boolean setWallTorch(Block block, BlockFace facing) {
        return setDirectionalBlock(block, Material.WALL_TORCH, facing);
    }

    public static boolean setStairs(Block block, Material material, BlockFace facing) {
        return setStairs(block, material, facing, Bisected.Half.BOTTOM);
    }

    public static boolean setStairs(Block block, Material material, BlockFace facing, Bisected.Half half) {
        if(facing == null || half == null)
            return false;

        return setTypedBlockData(block, material, Stairs.class, stairs -> {
            stairs.setFacing(facing);
            stairs.setHalf(half);
        }, true);
    }

    public static boolean setDoorHalf(Block block, Material material, BlockFace facing, Bisected.Half half, Door.Hinge hinge, boolean open) {
        if(facing == null || half == null || hinge == null)
            return false;

        return setTypedBlockData(block, material, Door.class, door -> {
            door.setFacing(facing);
            door.setHalf(half);
            door.setHinge(hinge);
            door.setOpen(open);
        }, true);
    }

    public static boolean setVines(Block block, BlockFace... faces) {
        if(faces == null)
            return false;

        return setTypedBlockData(block, Material.VINE, MultipleFacing.class, vine -> {
            final EnumSet<BlockFace> requestedFaces = EnumSet.noneOf(BlockFace.class);
            for(BlockFace face : faces)
                if(face != null)
                    requestedFaces.add(face);

            for(BlockFace face : new BlockFace[] {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST})
                if(vine.getAllowedFaces().contains(face))
                    vine.setFace(face, requestedFaces.contains(face));
        }, true);
    }

    private static boolean setDirectionalBlock(Block block, Material material, BlockFace facing) {
        if(facing == null)
            return false;

        return setTypedBlockData(block, material, Directional.class, directional -> directional.setFacing(facing), true);
    }

    private static <T extends BlockData> boolean setTypedBlockData(Block block, Material material, Class<T> type, Consumer<T> mutator, boolean physics) {
        block.setType(material, false);
        final BlockData blockData = block.getBlockData();
        if(!type.isInstance(blockData))
            return false;

        final T typedBlockData = type.cast(blockData);
        mutator.accept(typedBlockData);
        block.setBlockData(typedBlockData, physics);
        return true;
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
