package com.timvisee.DungeonMaze.populators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import com.timvisee.DungeonMaze.DungeonMaze;

public class BossRoomInsanePopulator extends BlockPopulator {
	public static final int CHANCE_OF_BOSSROOM = 1; //Promile
	public static final double MIN_SPAWN_DISTANCE = 10; // Chunks

	@Override
	public void populate(World world, Random random, Chunk source) {
		// Hold the y on 30 because that's only the lowest layer
			
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			
			int x = 0;
			int z = 0;
			
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(3*6); y+=6) { /*go up to 3 layers because the room is 3 heigh*/
				if(distance(0, 0, source.getX(), source.getZ()) >= MIN_SPAWN_DISTANCE) {
					if (random.nextInt(1000) < CHANCE_OF_BOSSROOM) {
						DungeonMaze.addConstantChunk(world.getName(), source.getX(), source.getZ());					
						
						// Clear the room!
					     for (int x2=x; x2 < x + 15; x2+=1) {
					      for (int y2=y + 1; y2 <= y + (6*3) - 1; y2+=1) {
					       for (int z2=z; z2 <z + 15; z2+=1) {
					        source.getBlock(x2, y2, z2).setTypeId(0);
					       }
					      }
					     }
					     //floor
					     for (int x2=x; x2 < x + 15; x2+=1) {
					         for (int y2=y; y2 < y + 1; y2+=1) {
					            for (int z2=z; z2 < z + 15; z2+=1) {
					             source.getBlock(x2, y2, z2).setTypeId(49);
					            }
					         }
					     }
					     //treasures
					     source.getBlock(x + 7, y + 1, z + 7).setTypeId(41);
					     source.getBlock(x + 8, y + 1, z + 8).setTypeId(42);
					     //chest1
					     source.getBlock(x + 7, y + 1, z + 8).setTypeId(54);
					     addItemsToChest(random, (Chest) source.getBlock(x + 7, y + 1, z + 8).getState());
					     //chest2
					     source.getBlock(x + 8, y + 1, z + 7).setTypeId(54);
					     addItemsToChest(random, (Chest) source.getBlock(x + 8, y + 1, z + 7).getState());
					     //glass shields
					     source.getBlock(x + 2, y + 1, z + 3).setTypeId(20);
					     source.getBlock(x + 2, y + 1, z + 12).setTypeId(20);
					     source.getBlock(x + 3, y + 1, z + 2).setTypeId(20);
					     source.getBlock(x + 3, y + 1, z + 4).setTypeId(20);
					     source.getBlock(x + 3, y + 1, z + 11).setTypeId(20);
					     source.getBlock(x + 3, y + 1, z + 13).setTypeId(20);
					     source.getBlock(x + 4, y + 1, z + 3).setTypeId(20);
					     source.getBlock(x + 4, y + 1, z + 12).setTypeId(20);
					     source.getBlock(x + 11, y + 1, z + 3).setTypeId(20);
					     source.getBlock(x + 11, y + 1, z + 12).setTypeId(20);
					     source.getBlock(x + 12, y + 1, z + 2).setTypeId(20);
					     source.getBlock(x + 12, y + 1, z + 4).setTypeId(20);
					     source.getBlock(x + 12, y + 1, z + 11).setTypeId(20);
					     source.getBlock(x + 12, y + 1, z + 13).setTypeId(20);
					     source.getBlock(x + 13, y + 1, z + 3).setTypeId(20);
					     source.getBlock(x + 13, y + 1, z + 12).setTypeId(20);
					     source.getBlock(x + 3, y + 2, z + 3).setTypeId(20);
					     source.getBlock(x + 3, y + 2, z + 12).setTypeId(20);
					     source.getBlock(x + 12, y + 2, z + 3).setTypeId(20);
					     source.getBlock(x + 12, y + 2, z + 12).setTypeId(20);
					     //hull
					     source.getBlock(x + 5, y + 1, z + 7).setTypeId(112);
					     source.getBlock(x + 5, y + 1, z + 8).setTypeId(112);
					     source.getBlock(x + 6, y + 1, z + 6).setTypeId(112);
					     source.getBlock(x + 6, y + 1, z + 7).setTypeId(88);
					     source.getBlock(x + 6, y + 1, z + 8).setTypeId(88);
					     source.getBlock(x + 6, y + 1, z + 9).setTypeId(112);
					     source.getBlock(x + 7, y + 1, z + 5).setTypeId(112);
					     source.getBlock(x + 7, y + 1, z + 6).setTypeId(88);
					     source.getBlock(x + 7, y + 1, z + 9).setTypeId(88);
					     source.getBlock(x + 7, y + 1, z + 10).setTypeId(112);
					     source.getBlock(x + 8, y + 1, z + 5).setTypeId(112);
					     source.getBlock(x + 8, y + 1, z + 6).setTypeId(88);
					     source.getBlock(x + 8, y + 1, z + 9).setTypeId(88);
					     source.getBlock(x + 8, y + 1, z + 10).setTypeId(112);
					     source.getBlock(x + 9, y + 1, z + 6).setTypeId(112);
					     source.getBlock(x + 9, y + 1, z + 7).setTypeId(88);
					     source.getBlock(x + 9, y + 1, z + 8).setTypeId(88);
					     source.getBlock(x + 9, y + 1, z + 9).setTypeId(112);
					     source.getBlock(x + 10, y + 1, z + 7).setTypeId(112);
					     source.getBlock(x + 10, y + 1, z + 8).setTypeId(112);
					     source.getBlock(x + 5, y + 2, z + 7).setTypeId(112);
					     source.getBlock(x + 5, y + 2, z + 8).setTypeId(112);
					     source.getBlock(x + 6, y + 2, z + 6).setTypeId(112);
					     source.getBlock(x + 6, y + 2, z + 7).setTypeId(88);
					     source.getBlock(x + 6, y + 2, z + 8).setTypeId(88);
					     source.getBlock(x + 6, y + 2, z + 9).setTypeId(112);
					     source.getBlock(x + 7, y + 2, z + 5).setTypeId(112);
					     source.getBlock(x + 7, y + 2, z + 6).setTypeId(88);
					     source.getBlock(x + 7, y + 2, z + 9).setTypeId(88);
					     source.getBlock(x + 7, y + 2, z + 10).setTypeId(112);
					     source.getBlock(x + 8, y + 2, z + 5).setTypeId(112);
					     source.getBlock(x + 8, y + 2, z + 6).setTypeId(88);
					     source.getBlock(x + 8, y + 2, z + 9).setTypeId(88);
					     source.getBlock(x + 8, y + 2, z + 10).setTypeId(112);
					     source.getBlock(x + 9, y + 2, z + 6).setTypeId(112);
					     source.getBlock(x + 9, y + 2, z + 7).setTypeId(88);
					     source.getBlock(x + 9, y + 2, z + 8).setTypeId(88);
					     source.getBlock(x + 9, y + 2, z + 9).setTypeId(112);
					     source.getBlock(x + 10, y + 2, z + 7).setTypeId(112);
					     source.getBlock(x + 10, y + 2, z + 8).setTypeId(112);
					     source.getBlock(x + 6, y + 3, z + 7).setTypeId(112);
					     source.getBlock(x + 6, y + 3, z + 8).setTypeId(112);
					     source.getBlock(x + 7, y + 3, z + 6).setTypeId(112);
					     source.getBlock(x + 7, y + 3, z + 7).setTypeId(88);
					     source.getBlock(x + 7, y + 3, z + 8).setTypeId(88);
					     source.getBlock(x + 7, y + 3, z + 9).setTypeId(112);
					     source.getBlock(x + 8, y + 3, z + 6).setTypeId(112);
					     source.getBlock(x + 8, y + 3, z + 7).setTypeId(88);
					     source.getBlock(x + 8, y + 3, z + 8).setTypeId(88);
					     source.getBlock(x + 8, y + 3, z + 9).setTypeId(112);
					     source.getBlock(x + 9, y + 3, z + 7).setTypeId(112);
					     source.getBlock(x + 9, y + 3, z + 8).setTypeId(112);
					     source.getBlock(x + 7, y + 4, z + 7).setTypeId(112);
					     source.getBlock(x + 7, y + 4, z + 8).setTypeId(112);
					     source.getBlock(x + 8, y + 4, z + 7).setTypeId(112);
					     source.getBlock(x + 8, y + 4, z + 8).setTypeId(112);
					     //core spawners
					     source.getBlock(x + 7, y + 2, z + 7).setTypeId(52);
					     CreatureSpawner CS1 = (CreatureSpawner) source.getBlock(x + 7, y + 2, z + 7).getState();
					     CS1.setSpawnedType(EntityType.GHAST);
					     source.getBlock(x + 7, y + 2, z + 8).setTypeId(52);
					     CreatureSpawner CS2 = (CreatureSpawner) source.getBlock(x + 7, y + 2, z + 8).getState();
					     CS2.setSpawnedType(EntityType.ZOMBIE);
					     source.getBlock(x + 8, y + 2, z + 7).setTypeId(52);
					     CreatureSpawner CS3 = (CreatureSpawner) source.getBlock(x + 8, y + 2, z + 7).getState();
					     CS3.setSpawnedType(EntityType.PIG_ZOMBIE);
					     source.getBlock(x + 8, y + 2, z + 8).setTypeId(52);
					     CreatureSpawner CS4 = (CreatureSpawner) source.getBlock(x + 8, y + 2, z + 8).getState();
					     CS4.setSpawnedType(EntityType.PIG_ZOMBIE);
					     source.getBlock(x + 7, y + 3, z + 7).setTypeId(52);
					     CreatureSpawner CTS1 = (CreatureSpawner) source.getBlock(x + 7, y + 3, z + 7).getState();
					     CTS1.setSpawnedType(EntityType.SKELETON);
					     source.getBlock(x + 7, y + 3, z + 8).setTypeId(52);
					     CreatureSpawner CTS2 = (CreatureSpawner) source.getBlock(x + 7, y + 3, z + 8).getState();
					     CTS2.setSpawnedType(EntityType.ZOMBIE);
					     source.getBlock(x + 8, y + 3, z + 7).setTypeId(52);
					     CreatureSpawner CTS3 = (CreatureSpawner) source.getBlock(x + 8, y + 3, z + 7).getState();
					     CTS3.setSpawnedType(EntityType.PIG_ZOMBIE);
					     source.getBlock(x + 8, y + 3, z + 8).setTypeId(52);
					     CreatureSpawner CTS4 = (CreatureSpawner) source.getBlock(x + 8, y + 3, z + 8).getState();
					     CTS4.setSpawnedType(EntityType.ZOMBIE);
					     //loose spawners
					     source.getBlock(x + 3, y + 1, z + 3).setTypeId(52);
					     CreatureSpawner LS1 = (CreatureSpawner) source.getBlock(x + 3, y + 1, z + 3).getState();
					     LS1.setSpawnedType(EntityType.ZOMBIE);
					     source.getBlock(x + 3, y + 1, z + 12).setTypeId(52);
					     CreatureSpawner LS2 = (CreatureSpawner) source.getBlock(x + 3, y + 1, z + 12).getState();
					     LS2.setSpawnedType(EntityType.SKELETON);
					     source.getBlock(x + 12, y + 1, z + 3).setTypeId(52);
					     CreatureSpawner LS3 = (CreatureSpawner) source.getBlock(x + 12, y + 1, z + 3).getState();
					     LS3.setSpawnedType(EntityType.ZOMBIE);
					     source.getBlock(x + 12, y + 1, z + 12).setTypeId(52);
					     CreatureSpawner LS4 = (CreatureSpawner) source.getBlock(x + 12, y + 1, z + 12).getState();
					     LS4.setSpawnedType(EntityType.SPIDER);
					}
				}
			}
		}
	}
	
	public double distance(int x1, int y1, int x2, int y2)
	{
		double dx   = x1 - x2;         //horizontal difference 
		double dy   = y1 - y2;         //vertical difference 
		double dist = Math.sqrt( dx*dx + dy*dy ); //distance using Pythagoras theorem
		return dist;
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