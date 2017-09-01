package com.timvisee.dungeonmaze.populator.maze.spawner;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.event.generation.GenerationSpawnerEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeLayerBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeLayerBlockPopulatorArgs;
import com.timvisee.dungeonmaze.util.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BossRoomInsanePopulator extends MazeLayerBlockPopulator {

    /** General populator constants. */
    private static final int LAYER_MIN = 1;
    private static final int LAYER_MAX = 3;
    private static final float LAYER_CHANCE = .001f;

    /** Populator constants. */
    private static final double SPAWN_DISTANCE_MIN = 10; // Chunks

    @Override
    public void populateLayer(MazeLayerBlockPopulatorArgs args) {
        final Random rand = args.getRandom();
        final Chunk chunk = args.getSourceChunk();
        final int x = 0;
        final int y = args.getY();
        final int z = 0;

        // Make sure the distance between the spawn chunk and the current chunk is allowed
        if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < SPAWN_DISTANCE_MIN)
            return;

        // Set this chunk as custom chunk
        // TODO: Flag the rooms used instead of the whole chunk!
        args.getDungeonChunk().setCustomChunk(true);

        // Clear the room!
        for(int x2 = x; x2 < x + 15; x2 += 1)
            for(int y2 = y + 1; y2 <= y + (6 * 3) - 1; y2 += 1)
                for(int z2 = z; z2 < z + 15; z2 += 1)
                    chunk.getBlock(x2, y2, z2).setType(Material.AIR);
        // Floor
        for(int x2 = x; x2 < x + 15; x2 += 1)
            for(int y2 = y; y2 < y + 1; y2 += 1)
                for(int z2 = z; z2 < z + 15; z2 += 1)
                    chunk.getBlock(x2, y2, z2).setType(Material.OBSIDIAN);

        // Treasures
        chunk.getBlock(x + 7, y + 1, z + 7).setType(Material.GOLD_BLOCK);
        chunk.getBlock(x + 8, y + 1, z + 8).setType(Material.IRON_BLOCK);

        // Chest1
        chunk.getBlock(x + 7, y + 1, z + 8).setType(Material.CHEST);
        addItemsToChest(rand, (Chest) chunk.getBlock(x + 7, y + 1, z + 8).getState());

        // Chest2
        chunk.getBlock(x + 8, y + 1, z + 7).setType(Material.CHEST);
        addItemsToChest(rand, (Chest) chunk.getBlock(x + 8, y + 1, z + 7).getState());

        // Glass shields
        chunk.getBlock(x + 2, y + 1, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 2, y + 1, z + 12).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 1, z + 2).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 1, z + 4).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 1, z + 11).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 1, z + 13).setType(Material.GLASS);
        chunk.getBlock(x + 4, y + 1, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 4, y + 1, z + 12).setType(Material.GLASS);
        chunk.getBlock(x + 11, y + 1, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 11, y + 1, z + 12).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 1, z + 2).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 1, z + 4).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 1, z + 11).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 1, z + 13).setType(Material.GLASS);
        chunk.getBlock(x + 13, y + 1, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 13, y + 1, z + 12).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 2, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 2, z + 12).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 2, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 12, y + 2, z + 12).setType(Material.GLASS);

        // Hull
        chunk.getBlock(x + 5, y + 1, z + 7).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 5, y + 1, z + 8).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 6, y + 1, z + 6).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 6, y + 1, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 6, y + 1, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 6, y + 1, z + 9).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 7, y + 1, z + 5).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 7, y + 1, z + 6).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 1, z + 9).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 1, z + 10).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 8, y + 1, z + 5).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 8, y + 1, z + 6).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 1, z + 9).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 1, z + 10).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 9, y + 1, z + 6).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 9, y + 1, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 9, y + 1, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 9, y + 1, z + 9).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 10, y + 1, z + 7).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 10, y + 1, z + 8).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 5, y + 2, z + 7).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 5, y + 2, z + 8).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 6, y + 2, z + 6).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 6, y + 2, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 6, y + 2, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 6, y + 2, z + 9).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 7, y + 2, z + 5).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 7, y + 2, z + 6).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 2, z + 9).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 2, z + 10).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 8, y + 2, z + 5).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 8, y + 2, z + 6).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 2, z + 9).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 2, z + 10).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 9, y + 2, z + 6).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 9, y + 2, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 9, y + 2, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 9, y + 2, z + 9).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 10, y + 2, z + 7).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 10, y + 2, z + 8).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 6, y + 3, z + 7).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 6, y + 3, z + 8).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 7, y + 3, z + 6).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 7, y + 3, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 3, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 7, y + 3, z + 9).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 8, y + 3, z + 6).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 8, y + 3, z + 7).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 3, z + 8).setType(Material.SOUL_SAND);
        chunk.getBlock(x + 8, y + 3, z + 9).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 9, y + 3, z + 7).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 9, y + 3, z + 8).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 7, y + 4, z + 7).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 7, y + 4, z + 8).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 8, y + 4, z + 7).setType(Material.NETHER_BRICK);
        chunk.getBlock(x + 8, y + 4, z + 8).setType(Material.NETHER_BRICK);

        // Core spawners
        if(Core.getConfigHandler().isMobSpawnerAllowed("Ghast")) {
            Block spawnerBlock = chunk.getBlock(x + 7, y + 2, z + 7);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.GHAST, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        if(Core.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
            Block spawnerBlock = chunk.getBlock(x + 7, y + 2, z + 8);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        if(Core.getConfigHandler().isMobSpawnerAllowed("PigZombie")) {
            Block spawnerBlock = chunk.getBlock(x + 8, y + 2, z + 7);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.PIG_ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);
                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        if(Core.getConfigHandler().isMobSpawnerAllowed("PigZombie")) {
            Block spawnerBlock = chunk.getBlock(x + 8, y + 2, z + 8);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.PIG_ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        if(Core.getConfigHandler().isMobSpawnerAllowed("Skeleton")) {
            Block spawnerBlock = chunk.getBlock(x + 7, y + 3, z + 7);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.SKELETON, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        if(Core.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
            Block spawnerBlock = chunk.getBlock(x + 7, y + 3, z + 8);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        if(Core.getConfigHandler().isMobSpawnerAllowed("PigZombie")) {
            Block spawnerBlock = chunk.getBlock(x + 8, y + 3, z + 7);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.PIG_ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        if(Core.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
            Block spawnerBlock = chunk.getBlock(x + 8, y + 3, z + 8);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        //loose spawners
        if(Core.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
            Block spawnerBlock = chunk.getBlock(x + 3, y + 1, z + 3);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        if(Core.getConfigHandler().isMobSpawnerAllowed("Skeleton")) {
            Block spawnerBlock = chunk.getBlock(x + 3, y + 1, z + 12);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.SKELETON, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        if(Core.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
            Block spawnerBlock = chunk.getBlock(x + 12, y + 1, z + 3);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        if(Core.getConfigHandler().isMobSpawnerAllowed("Spider")) {
            Block spawnerBlock = chunk.getBlock(x + 12, y + 1, z + 12);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.SPIDER, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_INSANE, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Get the block state
                    BlockState state = spawnerBlock.getState();

                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner theSpawner = (CreatureSpawner) state;

                    // Set the spawned type of the spawner
                    theSpawner.setSpawnedType(event.getSpawnedType());

                    // Update the block state
                    state.update();

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }
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

    private void addItemsToChest(Random random, Chest chest) {
        // Create a list to put the chest items in
        List<ItemStack> items = new ArrayList<>();

        // Add the items to the list
        if(random.nextInt(100) < 80)
            items.add(new ItemStack(Material.TORCH, 16, (short) 0));

        if(random.nextInt(100) < 40)
            items.add(new ItemStack(Material.TORCH, 20, (short) 0));

        if(random.nextInt(100) < 80)
            items.add(new ItemStack(Material.ARROW, 24, (short) 0));

        if(random.nextInt(100) < 40)
            items.add(new ItemStack(Material.ARROW, 1, (short) 0));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.DIAMOND, 3, (short) 0));

        if(random.nextInt(100) < 50)
            items.add(new ItemStack(Material.IRON_INGOT, 3, (short) 0));

        if(random.nextInt(100) < 50)
            items.add(new ItemStack(Material.GOLD_INGOT, 3, (short) 0));

        if(random.nextInt(100) < 50)
            items.add(new ItemStack(Material.IRON_SWORD, 1, (short) 0));

        if(random.nextInt(100) < 80)
            items.add(new ItemStack(Material.MUSHROOM_SOUP, 1, (short) 0));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.IRON_HELMET, 1, (short) 0));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.IRON_CHESTPLATE, 1, (short) 0));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.IRON_LEGGINGS, 1, (short) 0));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.IRON_BOOTS, 1, (short) 0));

        if(random.nextInt(100) < 5)
            items.add(new ItemStack(Material.DIAMOND_HELMET, 1, (short) 0));

        if(random.nextInt(100) < 5)
            items.add(new ItemStack(Material.DIAMOND_CHESTPLATE, 1, (short) 0));

        if(random.nextInt(100) < 5)
            items.add(new ItemStack(Material.DIAMOND_LEGGINGS, 1, (short) 0));

        if(random.nextInt(100) < 5)
            items.add(new ItemStack(Material.DIAMOND_BOOTS, 1, (short) 0));

        if(random.nextInt(100) < 40)
            items.add(new ItemStack(Material.FLINT, 1, (short) 0));

        if(random.nextInt(100) < 80)
            items.add(new ItemStack(Material.GRILLED_PORK, 1, (short) 0));

        if(random.nextInt(100) < 10)
            items.add(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 0));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.REDSTONE, 7, (short) 0));

        if(random.nextInt(100) < 20)
            items.add(new ItemStack(Material.CAKE, 1, (short) 0));

        if(random.nextInt(100) < 80)
            items.add(new ItemStack(Material.COOKIE, 8, (short) 0));

        // Determine the number of items to add to the chest
        int itemCountInChest;
        switch(random.nextInt(8)) {
        case 0:
            itemCountInChest = 2;
            break;
        case 1:
            itemCountInChest = 2;
            break;
        case 2:
            itemCountInChest = 3;
            break;
        case 3:
            itemCountInChest = 3;
            break;
        case 4:
            itemCountInChest = 4;
            break;
        case 5:
            itemCountInChest = 4;
            break;
        case 6:
            itemCountInChest = 4;
            break;
        case 7:
            itemCountInChest = 5;
            break;
        default:
            itemCountInChest = 4;
            break;
        }

        // Add the selected items to a random place inside the chest
        for(int i = 0; i < itemCountInChest; i++)
            chest.getInventory().setItem(random.nextInt(chest.getInventory().getSize()), items.get(random.nextInt(items.size())));

        // Update the chest
        chest.update();
    }

    @Override
    public float getLayerIterationsChance() {
        return LAYER_CHANCE;
    }
}