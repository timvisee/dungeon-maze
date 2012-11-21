package com.timvisee.DungeonMaze.populator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import com.timvisee.DungeonMaze.event.generation.DMGenerationChestEvent;
import com.timvisee.DungeonMaze.DungeonMaze;

public class ChestPopulator extends BlockPopulator {
	
	public static DungeonMaze plugin;
	
	public ChestPopulator(DungeonMaze instance) {
		plugin = instance;
	}

	public static final int CHANCE_OF_CHEST = 3;
	public static final double CHANCE_OF_CHEST_ADDITION_PER_LEVEL = -0.333; /* to 1 */
	
	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							if (random.nextInt(100) < CHANCE_OF_CHEST+(CHANCE_OF_CHEST_ADDITION_PER_LEVEL*(y-30)/6)) {
								
								int chestX = x + random.nextInt(6) + 1;
								int chestY = y;
								int chestZ = z + random.nextInt(6) + 1;
								
								// Get the floor location 
								int yfloor = y;
								Block roomBottomBlock = source.getBlock(chestX, y, chestZ);
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!
									yfloor++;
								}
								chestY = yfloor + 1;
		
								if(!(source.getBlock(chestX, chestY - 1, chestZ).getTypeId() == 0)) {
									Block chestBlock = source.getBlock(chestX, chestY, chestZ);
									if(chestBlock.getTypeId() == 0) {
										
										// Generate new inventory contents
										List<ItemStack> contents = generateChestContents(random);
										chestBlock.setTypeId(54);
										// Call the chest generation event
										DMGenerationChestEvent event = new DMGenerationChestEvent(chestBlock, random, contents);
										//DungeonMaze.getServer().getPluginManager().callEvent(event);
										//plugin.getDMEventHandler().callEvent(event);
										Bukkit.getServer().getPluginManager().callEvent(event);
										
										// Do the event
										if(!event.isCancelled()) {
											
											// Add the contents to the chest
											event.addItemsToChest(random, (Chest) chestBlock.getState(), event.getContents());
										} else {
											// do nothing
										}
									}
									else if (chestBlock.getTypeId() == 54 ) {
										// The follow is for rare case when the chest is generate before the plugin does the event
										Chest chest = (Chest) chestBlock.getState();
										if (chest.getInventory() != null) {
											// Generate new inventory contents
											List<ItemStack> contents = generateChestContents(random);
										
											// Call the chest generation event
											DMGenerationChestEvent event = new DMGenerationChestEvent(chestBlock, random, contents);
											//DungeonMaze.getServer().getPluginManager().callEvent(event);
											//plugin.getDMEventHandler().callEvent(event);
											Bukkit.getServer().getPluginManager().callEvent(event);
										
											// Do the event
											if(!event.isCancelled()) {	
												// Add the contents to the chest
												event.addItemsToChest(random, (Chest) chestBlock.getState(), event.getContents());
											} else {
												// do nothing
											}
										}
									}
								}
							}
						}	
					}
				}
			}
		}	
	}


	public List<ItemStack> generateChestContents(Random random) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(50, 4, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(50, 8, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(50, 12, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(260, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(262, 16, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(262, 24, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(264, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 50) {
			items.add(new ItemStack(265, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 60) {
			items.add(new ItemStack(266, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(267, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(268, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(272, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(296, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(296, 2, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(296, 3, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(297, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(298, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(299, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(300, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(301, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(302, 1, (short) 0, (byte) 0));
		} 
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(303, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(304, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(305, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(306, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(307, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(308, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(309, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 30) {
			items.add(new ItemStack(318, 3, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(318, 5, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(318, 7, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(319, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(320, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 15) {
			items.add(new ItemStack(331, 5, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(331, 8, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(331, 13, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 3) {
			items.add(new ItemStack(331, 21, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(345, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(349, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(350, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(351, 1, (short) 0, (byte) 3));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(354, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(357, 3, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(357, 5, (short) 0, (byte) 0));
		}
		
		int itemCountInChest = 3;
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
			break;
		}
		
		// Add the selected items to a random place inside the chest
		List<ItemStack> newContents = new ArrayList<ItemStack>();
		for (int i = 0; i < itemCountInChest; i++) {
			newContents.add(items.get(random.nextInt(items.size())));
		}
		return newContents;
	}
}