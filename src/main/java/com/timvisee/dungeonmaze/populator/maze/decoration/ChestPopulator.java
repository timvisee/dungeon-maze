package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.event.generation.GenerationChestEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.MazeStructureType;
import com.timvisee.dungeonmaze.util.ChestUtils;

public class ChestPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
	public static final float ROOM_CHANCE = .03f;

    // TODO: Implement this!
	public static final double CHANCE_CHEST_ADDITION_EACH_LEVEL = -0.333; // to 1

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
        final int x = args.getChunkX();
        final int z = args.getChunkZ();
        final int chestX = x + rand.nextInt(6) + 1;
        final int chestY = args.getFloorY() + 1;
        final int chestZ = z + rand.nextInt(6) + 1;

        if(!(chunk.getBlock(chestX, chestY - 1, chestZ).getType() == Material.AIR)) {
            Block chestBlock = chunk.getBlock(chestX, chestY, chestZ);
            if(chestBlock.getType() == Material.AIR) {

                // Generate new inventory contents
                List<ItemStack> contents = generateChestContents(rand);
                chestBlock.setType(Material.CHEST);

                // Call the chest generation event
                GenerationChestEvent event = new GenerationChestEvent(chestBlock, rand, contents, MazeStructureType.UNSTRUCTURE);
                Bukkit.getServer().getPluginManager().callEvent(event);

                // Do the event
                if(!event.isCancelled()) {
                    // Make sure the chest is still there, a developer could change the chest through the event!
                    if(event.getBlock().getType() != Material.CHEST)
                        return;

                    // Add the contents to the chest
                    ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
                } else {
                    // The event is cancelled
                    // Put the chest back to it's original state (air)
                    chestBlock.setType(Material.AIR);
                }

            } else if (chestBlock.getType() == Material.CHEST) {
                // The follow is for rare case when the chest is generate before the plugin does the event
                Chest chest = (Chest) chestBlock.getState();
                if (chest.getInventory() != null) {
                    // Generate new inventory contents
                    List<ItemStack> contents = generateChestContents(rand);

                    // Call the chest generation event
                    GenerationChestEvent event = new GenerationChestEvent(chestBlock, rand, contents, MazeStructureType.UNSTRUCTURE);
                    Bukkit.getServer().getPluginManager().callEvent(event);

                    // Do the event
                    if(!event.isCancelled()) {
                        // Make sure the chest is still there, a developer could change the chest through the event!
                        if(event.getBlock().getType() != Material.CHEST)
                            return;

                        // Add the contents to the chest
                        ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
                    }
                }
            }
		}
	}

	public List<ItemStack> generateChestContents(Random random) {
		// TODO: Use class for this, to also add feature to re loot chests
        // Create a list to put the item stacks in
		List<ItemStack> items = new ArrayList<>();

        // Add items to the stack
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.TORCH, 4, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.TORCH, 8, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.TORCH, 12, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.APPLE, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.ARROW, 16, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(Material.ARROW, 24, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.DIAMOND, 1, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(new ItemStack(Material.IRON_INGOT, 1, (short) 0));
		if(random.nextInt(100) < 60)
			items.add(new ItemStack(Material.GOLD_INGOT, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_SWORD, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.WOOD_SWORD, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.STONE_SWORD, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.WHEAT, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.WHEAT, 2, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(Material.WHEAT, 3, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.BREAD, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_HELMET, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_CHESTPLATE, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_LEGGINGS, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.LEATHER_BOOTS, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_HELMET, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_HELMET, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_CHESTPLATE, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_LEGGINGS, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.IRON_BOOTS, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(Material.FLINT, 3, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.FLINT, 5, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.FLINT, 7, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.PORK, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.GRILLED_PORK, 1, (short) 0));
		if(random.nextInt(100) < 15)
			items.add(new ItemStack(Material.REDSTONE, 5, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.REDSTONE, 8, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(Material.REDSTONE, 13, (short) 0));
		if(random.nextInt(100) < 3)
			items.add(new ItemStack(Material.REDSTONE, 21, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(Material.COMPASS, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.RAW_FISH, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.COOKED_FISH, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.INK_SACK, 1, (short) 3));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(Material.CAKE, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COOKIE, 3, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.COOKIE, 5, (short) 0));
		
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
			itemCountInChest = 3;
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
			itemCountInChest = 3;
		}
		
		// Create a list of item contents with the right amount of items
		List<ItemStack> newContents = new ArrayList<>();
		for (int i = 0; i < itemCountInChest; i++)
			newContents.add(items.get(random.nextInt(items.size())));
		return newContents;
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