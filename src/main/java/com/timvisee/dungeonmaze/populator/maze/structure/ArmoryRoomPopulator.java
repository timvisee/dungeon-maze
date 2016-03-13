package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class ArmoryRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .09f;

	// TODO: Armory room still not used, finish it and put it into Dungeon Maze

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World world = args.getWorld();
		Chunk chunk = args.getSourceChunk();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int floorOffset = args.getFloorOffset();
		final int z = args.getRoomChunkZ();

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

        // Blocks
        for(int x2 = x; x2 <= x + 7; x2 += 1)
            for(int y2 = y; y2 <= y + 5; y2++)
                for(int z2 = z; z2 <= z + 7; z2 += 1)
                    chunk.getBlock(x2, y2 + floorOffset, z2).setType(Material.COBBLESTONE);

        // Bedrock
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int y2 = y; y2 <= y + 4; y2++)
                for(int z2 = z + 1; z2 <= z + 6; z2 += 1)
                    chunk.getBlock(x2, y2 + floorOffset, z2).setType(Material.BEDROCK);

        // Air
        for(int x2 = x + 2; x2 <= x + 5; x2 += 1)
            for(int y2 = y + 1; y2 <= y + 3; y2++)
                for(int z2 = z + 2; z2 <= z + 5; z2 += 1)
                    chunk.getBlock(x2, y2 + floorOffset, z2).setType(Material.AIR);
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int y2 = y + 1; y2 <= y + 5; y2++)
                chunk.getBlock(x2, y2 + floorOffset, z).setType(Material.AIR);

        // Pumpkins
        chunk.getBlock(x + 2, yFloor + 1, z + 2).setType(Material.JACK_O_LANTERN);
        chunk.getBlock(x + 2, yFloor + 1, z + 5).setType(Material.JACK_O_LANTERN);
        chunk.getBlock(x + 5, yFloor + 1, z + 2).setType(Material.JACK_O_LANTERN);
        chunk.getBlock(x + 5, yFloor + 1, z + 5).setType(Material.JACK_O_LANTERN);
        chunk.getBlock(x + 2, yFloor + 2, z + 2).setType(Material.JACK_O_LANTERN);
        chunk.getBlock(x + 2, yFloor + 2, z + 5).setType(Material.JACK_O_LANTERN);
        chunk.getBlock(x + 5, yFloor + 2, z + 2).setType(Material.JACK_O_LANTERN);
        chunk.getBlock(x + 5, yFloor + 2, z + 5).setType(Material.JACK_O_LANTERN);
        chunk.getBlock(x + 2, yFloor + 3, z + 2).setType(Material.JACK_O_LANTERN);
        chunk.getBlock(x + 2, yFloor + 3, z + 5).setType(Material.JACK_O_LANTERN);
        chunk.getBlock(x + 5, yFloor + 3, z + 2).setType(Material.JACK_O_LANTERN);
        chunk.getBlock(x + 5, yFloor + 3, z + 5).setType(Material.JACK_O_LANTERN);

        // Chests
        chunk.getBlock(x + 2, yFloor + 1, z + 3).setType(Material.CHEST);
        chunk.getBlock(x + 2, yFloor + 1, z + 4).setType(Material.CHEST);
        chunk.getBlock(x + 3, yFloor + 1, z + 5).setType(Material.CHEST);
        chunk.getBlock(x + 4, yFloor + 1, z + 5).setType(Material.CHEST);
        chunk.getBlock(x + 5, yFloor + 1, z + 3).setType(Material.CHEST);
        chunk.getBlock(x + 5, yFloor + 1, z + 4).setType(Material.CHEST);

        // Iron doors
        chunk.getBlock(x + 3, yFloor + 1, z + 1).setType(Material.IRON_DOOR_BLOCK);
        chunk.getBlock(x + 3, yFloor + 1, z + 1).setData((byte) 0);
        chunk.getBlock(x + 4, yFloor + 1, z + 1).setType(Material.IRON_DOOR_BLOCK);
        chunk.getBlock(x + 4, yFloor + 1, z + 1).setData((byte) 3);
        chunk.getBlock(x + 3, yFloor + 2, z + 1).setType(Material.IRON_DOOR_BLOCK);
        chunk.getBlock(x + 3, yFloor + 2, z + 1).setData((byte) 8);
        chunk.getBlock(x + 4, yFloor + 2, z + 1).setType(Material.IRON_DOOR_BLOCK);
        chunk.getBlock(x + 4, yFloor + 2, z + 1).setData((byte) 11);
	}
	
	public void addItemsToChest(Random random, Chest chest) {
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
			items.add(new ItemStack(Material.RED_SANDSTONE_STAIRS, 7, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.CAKE, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COOKIE, 8, (short) 0));

		// Determine the number of items to add to the chest
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
		}
		
		// Add the selected items to a random place inside the chest
		for (int i = 0; i < itemCountInChest; i++)
			chest.getInventory().setItem(random.nextInt(chest.getInventory().getSize()), items.get(random.nextInt(items.size())));

		// Update the chest
		chest.update();
	}

    @Override
    public float getRoomChance() {
        return ROOM_CHANCE;
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
}