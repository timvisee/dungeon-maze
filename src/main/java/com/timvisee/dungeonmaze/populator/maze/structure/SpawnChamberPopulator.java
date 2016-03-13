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
        final int roomX = args.getChunkX();
        final int roomY = args.getChunkY();
        final int roomZ = args.getChunkZ();

        // Make sure this is the chunk at (0, 0)
        if(chunk.getX() != 0 || chunk.getZ() != 0 || roomX != 0 || roomZ != 0)
            return;

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), roomX, roomY, roomZ);

        // Break out the original walls, but not the corners
        for(int x = 0; x < 8; x++)
            for(int y = roomY + 2; y < 30 + (7 * 6); y++)
                for(int z = 0; z < 8; z++)
                    // Make sure this isn't a corner
                    if((x == 0 || x == 7) && (z == 0 || z == 7))
                        chunk.getBlock(roomX + x, y, roomZ + z).setType(Material.AIR);

        // Floor of a layer stone bricks with cobble stone below it
        for(int x = roomX; x < roomX + 8; x++) {
            for(int z = roomZ; z < roomZ + 8; z++) {
                chunk.getBlock(x, roomY + 1, z).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(x, roomY, z).setType(Material.COBBLESTONE);
            }
        }

        // Ceiling
        for(int x = roomX; x < roomX + 8; x++)
            for(int z = roomZ; z < roomZ + 8; z++)
                chunk.getBlock(x, roomY + 6, z).setType(Material.SMOOTH_BRICK);

        // Generate 4 circular blocks in the middle of the floor
        for(int x = roomX + 3; x <= roomX + 4; x++) {
            for(int z = roomZ + 3; z <= roomZ + 4; z++) {
                chunk.getBlock(x, roomY + 1, z).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(x, roomY + 1, z).setData((byte) 3);
            }
        }

        // Create iron fence walls
        for(int i = 1; i < 7; i++) {
            for(int y = roomY + 2; y < roomY + 6; y++) {
                chunk.getBlock(roomX + i, y, roomZ).setType(Material.IRON_FENCE);
                chunk.getBlock(roomX + i, y, roomZ + 7).setType(Material.IRON_FENCE);

                chunk.getBlock(roomX, y, roomZ + i).setType(Material.IRON_FENCE);
                chunk.getBlock(roomX + 7, y, roomZ + i).setType(Material.IRON_FENCE);
            }
        }

        // Create gates
        for(int x = roomX + 2; x < roomX + 6; x++) {
            for(int y = roomY + 2; y < roomY + 5; y++) {
                chunk.getBlock(x, y, roomZ).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(x, y, roomZ + 7).setType(Material.SMOOTH_BRICK);
            }
        }
        for(int z = roomZ + 2; z < roomZ + 6; z++) {
            for(int y = roomY + 2; y < roomY + 5; y++) {
                chunk.getBlock(roomX, y, z).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(roomX + 7, y, z).setType(Material.SMOOTH_BRICK);
            }
        }
        for(int x = roomX + 3; x < roomX + 5; x++) {
            for(int y = roomY + 2; y < roomY + 4; y++) {
                chunk.getBlock(x, y, roomZ).setType(Material.AIR);
                chunk.getBlock(x, y, roomZ + 7).setType(Material.AIR);
            }
        }
        for(int z = roomZ + 3; z < roomZ + 5; z++) {
            for(int y = roomY + 2; y < roomY + 4; y++) {
                chunk.getBlock(roomX, y, z).setType(Material.AIR);
                chunk.getBlock(roomX + 7, y, z).setType(Material.AIR);
            }
        }

        // Empty ItemStack list for events
        List<ItemStack> emptyList = new ArrayList<>();

        // Create chests
        chunk.getBlock(roomX + 1, roomY + 2, roomZ + 1).setType(Material.CHEST);
        chunk.getBlock(roomX + 1, roomY + 2, roomZ + 1).setData((byte) 3);

        chunk.getBlock(roomX + 1, roomY + 2, roomZ + 6).setType(Material.CHEST);
        chunk.getBlock(roomX + 1, roomY + 2, roomZ + 6).setData((byte) 2);

        chunk.getBlock(roomX + 6, roomY + 2, roomZ + 1).setType(Material.CHEST);
        chunk.getBlock(roomX + 6, roomY + 2, roomZ + 1).setData((byte) 3);

        chunk.getBlock(roomX + 6, roomY + 2, roomZ + 6).setType(Material.CHEST);
        chunk.getBlock(roomX + 6, roomY + 2, roomZ + 6).setData((byte) 2);

        // Call the Chest generation event
        GenerationChestEvent event = new GenerationChestEvent(chunk.getBlock(roomX + 1, roomY + 2, roomZ + 1), rand, emptyList, MazeStructureType.SPAWN_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event);

        // Do the event
        if(!event.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        // Call the Chest generation event
        GenerationChestEvent event2 = new GenerationChestEvent(chunk.getBlock(roomX + 1, roomY + 2, roomZ + 6), rand, emptyList, MazeStructureType.SPAWN_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event2);

        // Do the event
        if(!event2.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event2.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        // Call the Chest generation event
        GenerationChestEvent event3 = new GenerationChestEvent(chunk.getBlock(roomX + 6, roomY + 2, roomZ + 1), rand, emptyList, MazeStructureType.SPAWN_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event3);

        // Do the event
        if(!event3.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event3.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        // Call the Chest generation event
        GenerationChestEvent event4 = new GenerationChestEvent(chunk.getBlock(roomX + 6, roomY + 2, roomZ + 6), rand, emptyList, MazeStructureType.SPAWN_ROOM);
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
            Block b = chunk.getBlock(roomX + xTorch, roomY + 3, roomZ + zTorch);

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