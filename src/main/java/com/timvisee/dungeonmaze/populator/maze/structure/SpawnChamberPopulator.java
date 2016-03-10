package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.GenerationChestEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.MazeStructureType;
import com.timvisee.dungeonmaze.util.ChestUtils;
import org.bukkit.material.Torch;

public class SpawnChamberPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
    private static final int LAYER_MIN = 7;
    private static final int LAYER_MAX = 7;
    private static final float ROOM_CHANCE = 1.0f;

    @Override
    public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final World world = args.getWorld();
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
        final int x = args.getChunkX();
        final int y = args.getChunkY();
        final int z = args.getChunkZ();

        // Make sure this is the chunk at (0, 0)
        if(chunk.getX() != 0 || chunk.getZ() != 0 || x != 0 || z != 0)
            return;

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, y, z);

        // Break out the original walls
        for(int xx = 0; xx < 8; xx++)
            for(int yy = y + 2; yy < 30 + (7 * 6); yy++)
                for(int zz = 0; zz < 8; zz++)
                    chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);

        // Generate corners
        for(int yy = y + 2; yy < 30 + (7 * 6); yy++) {
            chunk.getBlock(x, yy, z).setType(Material.SMOOTH_BRICK);
            chunk.getBlock(x + 7, yy, z).setType(Material.SMOOTH_BRICK);
            chunk.getBlock(x, yy, z + 7).setType(Material.SMOOTH_BRICK);
            chunk.getBlock(x + 7, yy, z + 7).setType(Material.SMOOTH_BRICK);
        }

        //floor
        for(int xx = x; xx <= x + 7; xx++)
            for(int zz = z; zz <= z + 7; zz++)
                chunk.getBlock(xx, y + 1, zz).setType(Material.SMOOTH_BRICK);

        // Change the layer underneath the stone floor to cobblestone
        for(int xx = x; xx <= x + 8; xx++)
            for(int zz = z; zz <= z; zz++)
                chunk.getBlock(xx, y + 1, zz).setType(Material.COBBLESTONE);

        //Ceiling
        for(int xx = x; xx <= x + 8; xx++)
            for(int zz = z; zz <= z + 8; zz++)
                chunk.getBlock(xx, y + 6, zz).setType(Material.SMOOTH_BRICK);

        // Generate 4 circular blocks in the middle of the floor
        for(int xx = x + 3; xx <= x + 4; xx++) {
            for(int zz = z + 3; zz <= z + 4; zz++) {
                chunk.getBlock(xx, y + 1, zz).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(xx, y + 1, zz).setData((byte) 3);
            }
        }

        // Create walls
        for(int xx = x + 1; xx <= x + 6; xx++) {
            for(int yy = y + 2; yy <= y + 5; yy++) {
                chunk.getBlock(xx, yy, z).setType(Material.IRON_FENCE);
                chunk.getBlock(xx, yy, z + 7).setType(Material.IRON_FENCE);
            }
        }
        for(int zz = z + 1; zz <= z + 6; zz++) {
            for(int yy = y + 2; yy <= y + 5; yy++) {
                chunk.getBlock(x, yy, zz).setType(Material.IRON_FENCE);
                chunk.getBlock(x + 7, yy, zz).setType(Material.IRON_FENCE);
            }
        }

        // Create gates
        for(int xx = x + 2; xx <= x + 5; xx++) {
            for(int yy = y + 2; yy <= y + 4; yy++) {
                chunk.getBlock(xx, yy, z).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(xx, yy, z + 7).setType(Material.SMOOTH_BRICK);
            }
        }
        for(int zz = z + 2; zz <= z + 5; zz++) {
            for(int yy = y + 2; yy <= y + 4; yy++) {
                chunk.getBlock(x, yy, zz).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(x + 7, yy, zz).setType(Material.SMOOTH_BRICK);
            }
        }
        for(int xx = x + 3; xx <= x + 4; xx++) {
            for(int yy = y + 2; yy <= y + 3; yy++) {
                chunk.getBlock(xx, yy, z).setType(Material.AIR);
                chunk.getBlock(xx, yy, z + 7).setType(Material.AIR);
            }
        }
        for(int zz = z + 3; zz <= z + 4; zz++) {
            for(int yy = y + 2; yy <= y + 3; yy++) {
                chunk.getBlock(x, yy, zz).setType(Material.AIR);
                chunk.getBlock(x + 7, yy, zz).setType(Material.AIR);
            }
        }

        // Empty ItemStack list for events
        List<ItemStack> emptyList = new ArrayList<>();

        // Create chests
        chunk.getBlock(x + 1, y + 2, z + 1).setType(Material.CHEST);
        chunk.getBlock(x + 1, y + 2, z + 1).setData((byte) 3);

        // Call the Chest generation event
        GenerationChestEvent event = new GenerationChestEvent(chunk.getBlock(x + 1, y + 2, z + 1), rand, emptyList, MazeStructureType.SPAWN_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event);

        // Do the event
        if(!event.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        chunk.getBlock(x + 1, y + 2, z + 6).setType(Material.CHEST);
        chunk.getBlock(x + 1, y + 2, z + 6).setData((byte) 2);

        // Call the Chest generation event
        GenerationChestEvent event2 = new GenerationChestEvent(chunk.getBlock(x + 1, y + 2, z + 6), rand, emptyList, MazeStructureType.SPAWN_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event2);

        // Do the event
        if(!event2.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event2.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        chunk.getBlock(x + 6, y + 2, z + 1).setType(Material.CHEST);
        chunk.getBlock(x + 6, y + 2, z + 1).setData((byte) 3);

        // Call the Chest generation event
        GenerationChestEvent event3 = new GenerationChestEvent(chunk.getBlock(x + 6, y + 2, z + 1), rand, emptyList, MazeStructureType.SPAWN_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event3);

        // Do the event
        if(!event3.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event3.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        chunk.getBlock(x + 6, y + 2, z + 6).setType(Material.CHEST);
        chunk.getBlock(x + 6, y + 2, z + 6).setData((byte) 2);

        // Call the Chest generation event
        GenerationChestEvent event4 = new GenerationChestEvent(chunk.getBlock(x + 6, y + 2, z + 6), rand, emptyList, MazeStructureType.SPAWN_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event4);

        // Do the event
        if(!event4.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event4.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        // Define the relative positions of the torches in the spawn chamber
        int relativeTorchCoords[][] = {
                {1, 2},
                {1, 5},
                {6, 2},
                {6, 5},
                {2, 1},
                {2, 6},
                {5, 1},
                {5, 6},
        };

        // Place the torches
        for(int[] torchCoords : relativeTorchCoords) {
            // Get the relative coordinates of the torch block
            int xTorch = torchCoords[0];
            int zTorch = torchCoords[1];

            // Get the block to convert to a torch
            Block b = chunk.getBlock(x + xTorch, y + 3, z + zTorch);

            // Set the material of the block
            b.setType(Material.TORCH);

            // Get the torch data instance
            Torch torch = new Torch();

            // Determine and set the facing of the torch
            BlockFace torchFace = BlockFace.NORTH;
            if(xTorch == 6)
                torchFace = BlockFace.WEST;
            else if(xTorch == 1)
                torchFace = BlockFace.EAST;
            else if(zTorch == 1)
                torchFace = BlockFace.SOUTH;
            torch.setFacingDirection(torchFace);

            // Set the data value based on the torch facing, and update the block
            b.setData(torch.getData());
            b.getState().update(true);
        }
    }

    @Override
    public float getRoomChance() {
        return ROOM_CHANCE;
    }

    /**
     * Get the minimum layer
     *
     * @return Minimum layer
     */
    @Override
    public int getMinimumLayer() {
        return LAYER_MIN;
    }

    /**
     * Get the maximum layer
     *
     * @return Maximum layer
     */
    @Override
    public int getMaximumLayer() {
        return LAYER_MAX;
    }
}