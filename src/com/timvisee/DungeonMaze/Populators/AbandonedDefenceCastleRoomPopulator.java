package com.timvisee.DungeonMaze.Populators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import com.timvisee.DungeonMaze.DungeonMaze;

public class AbandonedDefenceCastleRoomPopulator extends BlockPopulator {
	public static final int CHANCE_OF_CASTLE = 1; //Promile
	public static final int MOSS_ITERATIONS = 80;
	public static final int MOSS_CHANCE = 70;
	public static final int CRACKED_ITERATIONS = 80;
	public static final int CRACKED_CHANCE = 70;

	@Override
	public void populate(World world, Random random, Chunk source) {
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30+(1*6); y < 30+(6*6); y+=6) {
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if (random.nextInt(1000) < CHANCE_OF_CASTLE) {
							if(!DungeonMaze.isConstantRoom(world.getName(), source.getX(), source.getZ(), x, y, z)) {
								
								DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, y, z);
								
								// Get the floor location 
								int yfloorRelative = 0;
								Block roomBottomBlock = source.getBlock(x+2, y, z+2);  // x and z +2 so that you aren't inside a wall!
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  
									yfloorRelative++;
								}
								
								// Get ceiling location
								int yceiling = y + 6; int yceilingRelative = 0;
								Block roomCeilingBlock = source.getBlock(x + 2, y + 6, z + 2);
								int typeId2 = roomCeilingBlock.getTypeId();
								if(!(typeId2==4 || typeId2==48 || typeId2==87 || typeId2==88)) {  // x and z +2 so that you aren't inside a wall!							
									yceiling++; yceilingRelative++;
								}
								
								// Break out the orriginal walls
								for(int xx = 1; xx < 7; xx++) {
									for(int yy = y + yfloorRelative + 1; yy <= yceiling - 1; yy++) {
										source.getBlock(x + xx, yy, z + 0).setTypeId(0);
										source.getBlock(x + xx, yy, z + 7).setTypeId(0);
									}
								}
								for(int zz = 1; zz < 7; zz++) {
									for(int yy = y + yfloorRelative + 1; yy < yceiling; yy++) {
										source.getBlock(x + 0, yy, z + zz).setTypeId(0);
										source.getBlock(x + 7, yy, z + zz).setTypeId(0);
									}
								}
								
								// Walls
								for(int xx = 1; xx < 7; xx++) {
									for(int yy = yfloorRelative + 1; yy <= yfloorRelative + 2; yy++) {
										source.getBlock(x + xx, y + yy, z + 1).setTypeId(98);
										source.getBlock(x + xx, y + yy, z + 6).setTypeId(98);
									}
								}
								for(int zz = 1; zz < 7; zz++) {
									for(int yy = yfloorRelative + 1; yy <= yfloorRelative + 2; yy++) {
										source.getBlock(x + 1, y + yy, z + zz).setTypeId(98);
										source.getBlock(x + 6, y + yy, z + zz).setTypeId(98);
									}
								}
								
								// Generate merlons
								for(int xx = 0; xx < 7; xx++) {
									source.getBlock(x + xx, y + yfloorRelative + 3, z + 0).setTypeId(98);
									source.getBlock(x + xx, y + yfloorRelative + 3, z + 7).setTypeId(98);
								}
								for(int zz = 0; zz < 7; zz++) {
									source.getBlock(x + 0, y + yfloorRelative + 3, z + zz).setTypeId(98);
									source.getBlock(x + 7, y + yfloorRelative + 3, z + zz).setTypeId(98);
								}
								source.getBlock(x + 0, y + yfloorRelative + 4, z + 1).setTypeId(44);
								source.getBlock(x + 0, y + yfloorRelative + 4, z + 1).setData((byte) 5);
								source.getBlock(x + 0, y + yfloorRelative + 4, z + 3).setTypeId(44);
								source.getBlock(x + 0, y + yfloorRelative + 4, z + 3).setData((byte) 5);
								source.getBlock(x + 0, y + yfloorRelative + 4, z + 5).setTypeId(44);
								source.getBlock(x + 0, y + yfloorRelative + 4, z + 5).setData((byte) 5);
								source.getBlock(x + 7, y + yfloorRelative + 4, z + 2).setTypeId(44);
								source.getBlock(x + 7, y + yfloorRelative + 4, z + 2).setData((byte) 5);
								source.getBlock(x + 7, y + yfloorRelative + 4, z + 4).setTypeId(44);
								source.getBlock(x + 7, y + yfloorRelative + 4, z + 4).setData((byte) 5);
								source.getBlock(x + 7, y + yfloorRelative + 4, z + 6).setTypeId(44);
								source.getBlock(x + 7, y + yfloorRelative + 4, z + 6).setData((byte) 5);
								source.getBlock(x + 1, y + yfloorRelative + 4, z + 0).setTypeId(44);
								source.getBlock(x + 1, y + yfloorRelative + 4, z + 0).setData((byte) 5);
								source.getBlock(x + 3, y + yfloorRelative + 4, z + 0).setTypeId(44);
								source.getBlock(x + 3, y + yfloorRelative + 4, z + 0).setData((byte) 5);
								source.getBlock(x + 5, y + yfloorRelative + 4, z + 0).setTypeId(44);
								source.getBlock(x + 5, y + yfloorRelative + 4, z + 0).setData((byte) 5);
								source.getBlock(x + 2, y + yfloorRelative + 4, z + 7).setTypeId(44);
								source.getBlock(x + 2, y + yfloorRelative + 4, z + 7).setData((byte) 5);
								source.getBlock(x + 4, y + yfloorRelative + 4, z + 7).setTypeId(44);
								source.getBlock(x + 4, y + yfloorRelative + 4, z + 7).setData((byte) 5);
								source.getBlock(x + 6, y + yfloorRelative + 4, z + 7).setTypeId(44);
								source.getBlock(x + 6, y + yfloorRelative + 4, z + 7).setData((byte) 5);
								
								// Place torches
								source.getBlock(x + 1, y + yfloorRelative + 3, z + 1).setTypeId(50);
								source.getBlock(x + 1, y + yfloorRelative + 3, z + 1).setData((byte) 5);
								source.getBlock(x + 1, y + yfloorRelative + 3, z + 6).setTypeId(50);
								source.getBlock(x + 1, y + yfloorRelative + 3, z + 6).setData((byte) 5);
								source.getBlock(x + 6, y + yfloorRelative + 3, z + 1).setTypeId(50);
								source.getBlock(x + 6, y + yfloorRelative + 3, z + 1).setData((byte) 5);
								source.getBlock(x + 6, y + yfloorRelative + 3, z + 6).setTypeId(50);
								source.getBlock(x + 6, y + yfloorRelative + 3, z + 6).setData((byte) 5);
								
								// Place ladders
								source.getBlock(x + 2, y + yfloorRelative + 1, z + 5).setTypeId(65);
								source.getBlock(x + 2, y + yfloorRelative + 1, z + 5).setData((byte) 2);
								source.getBlock(x + 2, y + yfloorRelative + 2, z + 5).setTypeId(65);
								source.getBlock(x + 2, y + yfloorRelative + 2, z + 5).setData((byte) 2);
								
								// Place crafting table, chests and furnaces
								source.getBlock(x + 2, y + yfloorRelative + 1, z + 2).setTypeId(58);
								source.getBlock(x + 5, y + yfloorRelative + 1, z + 2).setTypeId(54);
								source.getBlock(x + 5, y + yfloorRelative + 1, z + 2).setData((byte) 2);
								addItemsToChest(random, (Chest) source.getBlock(x + 5, y + yfloorRelative + 1, z + 2).getState());
								source.getBlock(x + 5, y + yfloorRelative + 1, z + 3).setTypeId(54);
								source.getBlock(x + 5, y + yfloorRelative + 1, z + 3).setData((byte) 2);
								addItemsToChest(random, (Chest) source.getBlock(x + 5, y + yfloorRelative + 1, z + 3).getState());
								source.getBlock(x + 5, y + yfloorRelative + 1, z + 4).setTypeId(61);
								source.getBlock(x + 5, y + yfloorRelative + 1, z + 4).setData((byte) 4);
								addItemsToFurnace(random, (Furnace) source.getBlock(x + 5, y + yfloorRelative + 1, z + 4).getState());
								source.getBlock(x + 5, y + yfloorRelative + 1, z + 5).setTypeId(61);
								source.getBlock(x + 5, y + yfloorRelative + 1, z + 5).setData((byte) 4);
								addItemsToFurnace(random, (Furnace) source.getBlock(x + 5, y + yfloorRelative + 1, z + 5).getState());
								
								// Place cake (with random pieces eaten)
								source.getBlock(x + 5, y + yfloorRelative + 2, z + 5).setTypeId(92);
								source.getBlock(x + 5, y + yfloorRelative + 2, z + 5).setData((byte) random.nextInt(4));
								
								// Place painting
								// ===== not added yet! =====
								
								// Place some cobweb
								source.getBlock(x + 2, y + yfloorRelative + 2, z + 2).setTypeId(30);
								source.getBlock(x + 3, y + yfloorRelative + 1, z + 2).setTypeId(30);
								source.getBlock(x + 6, y + yfloorRelative + 3, z + 6).setTypeId(30);
								source.getBlock(x + 6, y + yfloorRelative + 4, z + 6).setTypeId(30);
								source.getBlock(x + 5, y + yfloorRelative + 3, z + 6).setTypeId(30);
								source.getBlock(x + 6, y + yfloorRelative + 3, z + 5).setTypeId(30);
								source.getBlock(x + 0, y + yfloorRelative + 4, z + 6).setTypeId(30);
								
								
								
								// Add some moss and cracked stone bricks
								for (int i = 0; i < MOSS_ITERATIONS; i++) {
									if (random.nextInt(100) < MOSS_CHANCE) {
										
										Block block = source.getBlock(x + random.nextInt(8), random.nextInt((y + 6) - y + 1) + y, z + random.nextInt(8));
										if (block.getTypeId() == 4) {
											block.setTypeId(48);
										}
										if (block.getTypeId() == 98) {
											block.setData((byte) 1);
										}
									}
								}
								for (int i = 0; i < CRACKED_ITERATIONS; i++) {
									if (random.nextInt(100) < CRACKED_CHANCE) {
										
										Block block = source.getBlock(x + random.nextInt(8), random.nextInt((y + 6) - y + 1) + y, z + random.nextInt(8));
										if (block.getTypeId() == 98) {
											block.setData((byte) 2);
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
	
	public void addItemsToFurnace(Random random, Furnace furnace) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(41, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(42, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(45, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(263, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(263, 1, (short) 1, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(265, 2, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(265, 4, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(266, 2, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(266, 4, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(297, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(325, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(366, 2, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(366, 4, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(318, 3, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(318, 5, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(320, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(350, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 30) {
			items.add(new ItemStack(368, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 30) {
			items.add(new ItemStack(369, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 30) {
			items.add(new ItemStack(370, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 45) {
			items.add(new ItemStack(371, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 30) {
			items.add(new ItemStack(372, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 30) {
			items.add(new ItemStack(375, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 30) {
			items.add(new ItemStack(377, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 30) {
			items.add(new ItemStack(378, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 30) {
			items.add(new ItemStack(381, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 30) {
			items.add(new ItemStack(382, 1, (short) 0, (byte) 0));
		}
		
		// Add the selected items into the furnace
		if(random.nextInt(100) < 70) {
			furnace.getInventory().setResult(/*random.nextInt(furnace.getInventory().getSize()), */items.get(random.nextInt(items.size())));
		}
		furnace.update();
	}
	
	public void addItemsToChest(Random random, Chest chest) {
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
		for (int i = 0; i < itemCountInChest; i++) {
			chest.getInventory().setItem(random.nextInt(chest.getInventory().getSize()), items.get(random.nextInt(items.size())));
		}
		chest.update();
	}
}