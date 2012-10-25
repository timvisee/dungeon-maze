package com.timvisee.DungeonMaze.Populators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import com.timvisee.DungeonMaze.DungeonMaze;

public class ArmoryRoomPopulator extends BlockPopulator {
	public static final int CHANCE_OF_ARMORYROOM = 900; //Promile

	@Override
	public void populate(World world, Random random, Chunk source) {
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(7*6); y+=6) {
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if (random.nextInt(1000) < CHANCE_OF_ARMORYROOM) {
							if(!DungeonMaze.isConstantRoom(world.getName(), source.getX(), source.getZ(), x, y, z)) {
								DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, y, z);
								
								// Set standaard yfloor relative to 1 so that the room is always on the highest part of the room
								int yfloorRelative = 1;
								
								int yceiling = y + 6; int yceilingRelative = 0;
								Block roomCeilingBlock = source.getBlock(x + 2, y + 6, z + 2);
								int typeId2 = roomCeilingBlock.getTypeId();
								if(!(typeId2==4 || typeId2==48 || typeId2==87 || typeId2==88)) {  // x and z +2 so that you aren't inside a wall!							
									yceiling++; yceilingRelative++;
								}
								
								//block
								for (int x2=x; x2 <= x + 7; x2+=1) {
								    for (int y2=y; y2 <= y + 5; y2++)
								        for (int z2=z; z2 <= z + 7; z2+=1) {
								        source.getBlock(x2, y2 + yfloorRelative, z2).setTypeId(4);
								    }
								}
								//adminium
								for (int x2=x + 1; x2 <= x + 6; x2+=1) {
								    for (int y2=y; y2 <= y + 4; y2++)
								        for (int z2=z + 1; z2 <= z + 6; z2+=1) {
								        source.getBlock(x2, y2 + yfloorRelative, z2).setTypeId(7);
								    }
								}
								//air
								for (int x2=x + 2; x2 <= x + 5; x2+=1) {
								    for (int y2=y + 1; y2 <= y + 3; y2++)
								        for (int z2=z + 2; z2 <= z + 5; z2+=1) {
								        source.getBlock(x2, y2 + yfloorRelative, z2).setTypeId(0);
								    }
								}
								for (int x2=x + 1; x2 <= x + 6; x2+=1) {
								    for (int y2=y + 1; y2 <= y + 5; y2++) {
								        source.getBlock(x2, y2 + yfloorRelative, z + 0).setTypeId(0);
								    }
								}
								//pumpkins
								source.getBlock(x + 2, y + 1 + yfloorRelative, z + 2).setTypeId(91);
								source.getBlock(x + 2, y + 1 + yfloorRelative, z + 5).setTypeId(91);
								source.getBlock(x + 5, y + 1 + yfloorRelative, z + 2).setTypeId(91);
								source.getBlock(x + 5, y + 1 + yfloorRelative, z + 5).setTypeId(91);
								source.getBlock(x + 2, y + 2 + yfloorRelative, z + 2).setTypeId(91);
								source.getBlock(x + 2, y + 2 + yfloorRelative, z + 5).setTypeId(91);
								source.getBlock(x + 5, y + 2 + yfloorRelative, z + 2).setTypeId(91);
								source.getBlock(x + 5, y + 2 + yfloorRelative, z + 5).setTypeId(91);
								source.getBlock(x + 2, y + 3 + yfloorRelative, z + 2).setTypeId(91);
								source.getBlock(x + 2, y + 3 + yfloorRelative, z + 5).setTypeId(91);
								source.getBlock(x + 5, y + 3 + yfloorRelative, z + 2).setTypeId(91);
								source.getBlock(x + 5, y + 3 + yfloorRelative, z + 5).setTypeId(91);
								//chests
								source.getBlock(x + 2, y + 1 + yfloorRelative, z + 3).setTypeId(54);
								source.getBlock(x + 2, y + 1 + yfloorRelative, z + 4).setTypeId(54);
								source.getBlock(x + 3, y + 1 + yfloorRelative, z + 5).setTypeId(54);
								source.getBlock(x + 4, y + 1 + yfloorRelative, z + 5).setTypeId(54);
								source.getBlock(x + 5, y + 1 + yfloorRelative, z + 3).setTypeId(54);
								source.getBlock(x + 5, y + 1 + yfloorRelative, z + 4).setTypeId(54);
								//iron doors
								source.getBlock(x + 3, y + 1 + yfloorRelative, z + 1).setTypeId(71);
								source.getBlock(x + 3, y + 1 + yfloorRelative, z + 1).setData((byte) 0);
								source.getBlock(x + 4, y + 1 + yfloorRelative, z + 1).setTypeId(71);
								source.getBlock(x + 4, y + 1 + yfloorRelative, z + 1).setData((byte) 3);
								source.getBlock(x + 3, y + 2 + yfloorRelative, z + 1).setTypeId(71);
								source.getBlock(x + 3, y + 2 + yfloorRelative, z + 1).setData((byte) 8);
								source.getBlock(x + 4, y + 2 + yfloorRelative, z + 1).setTypeId(71);
								source.getBlock(x + 4, y + 2 + yfloorRelative, z + 1).setData((byte) 11);
							}
						}
					}
				}
			}
		}
	}
	
	public void addItemsToChest(Random random, Chest chest) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(50, 16, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(50, 20, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(262, 24, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(262, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(264, 3, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 50) {
			items.add(new ItemStack(265, 3, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 50) {
			items.add(new ItemStack(266, 3, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 50) {
			items.add(new ItemStack(267, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(282, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(306, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(307, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(308, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(309, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(310, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(311, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(312, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(313, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(318, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(320, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(322, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(331, 7, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(354, 1, (short) 0, (byte) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(357, 8, (short) 0, (byte) 0));
		}
		
		int itemCountInChest = 4;
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
		for (int i = 0; i < itemCountInChest; i++) {
			chest.getInventory().setItem(random.nextInt(chest.getInventory().getSize()), items.get(random.nextInt(items.size())));
		}
		chest.update();
	}
}