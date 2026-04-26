package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.util.MaterialUtils;
import com.timvisee.dungeonmaze.util.NumberUtils;
import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.event.generation.GenerationSpawnerEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeLayerBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeLayerBlockPopulatorArgs;

public class BossRoomHardPopulator extends MazeLayerBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 4;
    private static final float LAYER_CHANCE = .001f;

    /** Populator constants. */
	private static final double SPAWN_DISTANCE_MIN = 10; // Chunks

	@Override
	public void populateLayer(MazeLayerBlockPopulatorArgs args) {
        final Random rand = args.getRandom();
        final Chunk chunk = args.getSourceChunk();
        final DungeonChunk dungeonChunk = args.getDungeonChunk();
        final int x = 0;
        final int y = args.getY();
        final int z = 0;
        final Material netherBricks = MaterialUtils.requireBlockMaterial("NETHER_BRICKS", "NETHER_BRICK");

		// Make sure the distance between the spawn chunk and the current chunk is allowed
		if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < SPAWN_DISTANCE_MIN)
			return;

        // Set this chunk as custom
        dungeonChunk.setCustomChunk(true);

        // Clear the room!
        for(int x2 = x; x2 < x + 15; x2 += 1)
            for(int y2 = y + 1; y2 <= y + (6 * 3) - 1; y2 += 1)
                for(int z2 = z; z2 < z + 15; z2 += 1)
                    setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.AIR);

        // Floor
        for(int x2 = x; x2 < x + 15; x2 += 1)
            for(int z2 = z; z2 < z + 15; z2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, y, z2), Material.OBSIDIAN);

        // Glass shields
        setGeneratedBlock(chunk.getBlock(x + 2, y + 1, z + 3), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 2, y + 1, z + 12), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 3, y + 1, z + 2), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 3, y + 1, z + 4), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 3, y + 1, z + 11), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 3, y + 1, z + 13), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 4, y + 1, z + 3), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 4, y + 1, z + 12), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 5, y + 1, z + 7), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 5, y + 1, z + 8), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 6, y + 1, z + 6), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 6, y + 1, z + 9), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 1, z + 5), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 1, z + 10), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 1, z + 5), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 1, z + 10), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 9, y + 1, z + 6), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 9, y + 1, z + 9), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 10, y + 1, z + 7), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 10, y + 1, z + 8), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 11, y + 1, z + 3), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 11, y + 1, z + 12), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 12, y + 1, z + 2), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 12, y + 1, z + 4), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 12, y + 1, z + 11), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 12, y + 1, z + 13), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 13, y + 1, z + 3), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 13, y + 1, z + 12), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 3, y + 2, z + 3), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 3, y + 2, z + 12), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 5, y + 2, z + 7), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 5, y + 2, z + 8), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 6, y + 2, z + 6), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 6, y + 2, z + 9), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 2, z + 5), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 2, z + 10), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 2, z + 5), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 2, z + 10), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 9, y + 2, z + 6), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 9, y + 2, z + 9), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 10, y + 2, z + 7), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 10, y + 2, z + 8), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 12, y + 2, z + 3), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 12, y + 2, z + 12), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 6, y + 3, z + 7), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 6, y + 3, z + 8), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 3, z + 6), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 3, z + 9), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 3, z + 6), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 3, z + 9), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 9, y + 3, z + 7), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 9, y + 3, z + 8), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 4, z + 7), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 4, z + 8), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 4, z + 7), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 4, z + 8), Material.GLASS);

        // Netherbrick hull
        setGeneratedBlock(chunk.getBlock(x + 6, y + 1, z + 7), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 6, y + 1, z + 8), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 1, z + 6), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 1, z + 9), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 1, z + 6), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 1, z + 9), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 9, y + 1, z + 7), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 9, y + 1, z + 8), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 6, y + 2, z + 7), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 6, y + 2, z + 8), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 2, z + 6), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 2, z + 9), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 2, z + 6), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 2, z + 9), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 9, y + 2, z + 7), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 9, y + 2, z + 8), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 3, z + 7), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 3, z + 8), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 3, z + 7), netherBricks);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 3, z + 8), netherBricks);

        // Ores
        setGeneratedBlock(chunk.getBlock(x + 7, y + 1, z + 7), Material.IRON_ORE);
        setGeneratedBlock(chunk.getBlock(x + 7, y + 1, z + 8), Material.REDSTONE_ORE);
        setGeneratedBlock(chunk.getBlock(x + 8, y + 1, z + 7), Material.IRON_ORE);

        // Chest
        setGeneratedBlock(chunk.getBlock(x + 8, y + 1, z + 8), Material.CHEST);
        addItemsToChest(rand, (Chest) chunk.getBlock(x + 8, y + 1, z + 8).getState());

        // Core spawners
        if (Core.getConfigHandler().isMobSpawnerAllowed("Ghast")) {
            Block spawnerBlock = chunk.getBlock(x + 7, y + 2, z + 7);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.GHAST, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_HARD, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the event
            event._apply();
        }

        if (Core.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
            Block spawnerBlock = chunk.getBlock(x + 7, y + 2, z + 8);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_HARD, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the event
            event._apply();
        }

        if (Core.getConfigHandler().isMobSpawnerAllowed("PigZombie")) {
            Block spawnerBlock = chunk.getBlock(x + 8, y + 2, z + 7);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIFIED_PIGLIN, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_HARD, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the event
            event._apply();
        }

        if (Core.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
            Block spawnerBlock = chunk.getBlock(x + 8, y + 2, z + 8);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_HARD, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the event
            event._apply();
        }

        // Loose spawners
        if (Core.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
            Block spawnerBlock = chunk.getBlock(x + 3, y + 1, z + 3);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_HARD, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the event
            event._apply();
        }

        if (Core.getConfigHandler().isMobSpawnerAllowed("Skeleton")) {
            Block spawnerBlock = chunk.getBlock(x + 3, y + 1, z + 12);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.SKELETON, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_HARD, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the event
            event._apply();
        }

        if (Core.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
            Block spawnerBlock = chunk.getBlock(x + 12, y + 1, z + 3);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_HARD, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the event
            event._apply();
        }

        if (Core.getConfigHandler().isMobSpawnerAllowed("Spider")) {
            Block spawnerBlock = chunk.getBlock(x + 12, y + 1, z + 12);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.SPIDER, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_HARD, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the event
            event._apply();
        }
	}
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return LAYER_MIN;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return LAYER_MAX;
	}
	
	private void addItemsToChest(Random random, Chest chest) {
        // Create a list to put the chest items in
		List<ItemStack> items = new ArrayList<>();

        // Put the items in the list
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.TORCH, 16, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.TORCH, 20, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.ARROW, 24, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.ARROW, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.DIAMOND, 3, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(MaterialUtils.createItemStack(Material.IRON_INGOT, 3, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(MaterialUtils.createItemStack(Material.GOLD_INGOT, 3, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(MaterialUtils.createItemStack(Material.IRON_SWORD, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.MUSHROOM_STEW, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.IRON_HELMET, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.IRON_CHESTPLATE, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.IRON_LEGGINGS, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.IRON_BOOTS, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.DIAMOND_HELMET, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.DIAMOND_CHESTPLATE, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.DIAMOND_LEGGINGS, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.DIAMOND_BOOTS, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.FLINT, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.COOKED_PORKCHOP, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.GOLDEN_APPLE, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.REDSTONE, 7, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.CAKE, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.COOKIE, 8, (short) 0));
		
		int itemCountInChest;
		switch (random.nextInt(8)) {
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
		for (int i = 0; i < itemCountInChest; i++)
			chest.getInventory().setItem(random.nextInt(chest.getInventory().getSize()), items.get(random.nextInt(items.size())));
		chest.update();
	}

    @Override
    public float getLayerIterationsChance() {
        return LAYER_CHANCE;
    }
}
