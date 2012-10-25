package com.timvisee.DungeonMaze.Populators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import com.timvisee.DungeonMaze.DungeonMaze;

public class BlazeSpawnerRoomPopulator extends BlockPopulator {
	public static final int CHANCE_OF_SPANWER_ROOM = 2; //Promile
	public static final double CHANCE_OF_SPANWER_ROOM_ADDITION_PER_LEVEL = -0.167; /* to 1 */
	public static final double MIN_SPAWN_DISTANCE = 5; // Chunks

	@Override
	public void populate(World world, Random random, Chunk source) {
		// Hold the y on 30 because that's only the lowest layer
			
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(4*6); y+=6) {
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(distance(0, 0, source.getX(), source.getZ()) >= MIN_SPAWN_DISTANCE) {
							if (random.nextInt(1000) < CHANCE_OF_SPANWER_ROOM+(CHANCE_OF_SPANWER_ROOM_ADDITION_PER_LEVEL*(y-30)/6)) {
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
									
									// Netherbrick floor in the bottom of the room
									for (int xx=x; xx <= x + 7; xx+=1) {
							            for (int zz=z; zz <= z + 7; zz+=1) {
							                source.getBlock(xx, y + yfloorRelative, zz).setTypeId(112);
							            }
							        }
									// Cobblestone layer underneeth the stone floor
									for (int xx=x + 0; xx <= x + 7; xx+=1) {
							            for (int zz=z + 1; zz <= z + 6; zz+=1) {
							                source.getBlock(xx, y + yfloorRelative - 1, zz).setTypeId(4);
							            }
							        }
									
									// Break out the walls and things inside the room
									for (int xx = 0; xx < 8; xx++) {
										for (int yy = y + yfloorRelative + 1; yy < yceiling; yy++) {
											for(int zz = 0; zz < 8; zz++) {
												source.getBlock(x + xx, yy, z + zz).setTypeId(0);
											}
										}
									}
									
									// Generate corners
									for (int yy = y + yfloorRelative + 1; yy < yceiling; yy++) {
										source.getBlock(x + 0, yy, z + 0).setTypeId(112);
										source.getBlock(x + 7, yy, z + 0).setTypeId(112);
										source.getBlock(x + 0, yy, z + 7).setTypeId(112);
										source.getBlock(x + 7, yy, z + 7).setTypeId(112);
									}
									
									// Generate fences in the corners
									for (int yy = y + yfloorRelative + 1; yy < yceiling; yy++) {
										source.getBlock(x + 1, yy, z + 0).setTypeId(113);
										source.getBlock(x + 0, yy, z + 1).setTypeId(113);
										source.getBlock(x + 6, yy, z + 0).setTypeId(113);
										source.getBlock(x + 7, yy, z + 1).setTypeId(113);
										source.getBlock(x + 1, yy, z + 7).setTypeId(113);
										source.getBlock(x + 0, yy, z + 6).setTypeId(113);
										source.getBlock(x + 6, yy, z + 7).setTypeId(113);
										source.getBlock(x + 7, yy, z + 6).setTypeId(113);
									}
									
									// Generate platform in the middle
									for (int xx=x + 2; xx <= x + 5; xx+=1) {
							            for (int zz=z + 2; zz <= z + 5; zz+=1) {
							                source.getBlock(xx, y + yfloorRelative + 1, zz).setTypeId(112);
							            }
							        }
									
									// Generate stairs off the platform
									source.getBlock(x + 3, y + yfloorRelative + 1, z + 2).setTypeId(114);
									source.getBlock(x + 3, y + yfloorRelative + 1, z + 2).setData((byte) 2);
									source.getBlock(x + 4, y + yfloorRelative + 1, z + 2).setTypeId(114);
									source.getBlock(x + 4, y + yfloorRelative + 1, z + 2).setData((byte) 2);
									
									source.getBlock(x + 3, y + yfloorRelative + 1, z + 5).setTypeId(114);
									source.getBlock(x + 3, y + yfloorRelative + 1, z + 5).setData((byte) 3);
									source.getBlock(x + 4, y + yfloorRelative + 1, z + 5).setTypeId(114);
									source.getBlock(x + 4, y + yfloorRelative + 1, z + 5).setData((byte) 3);
									
									source.getBlock(x + 2, y + yfloorRelative + 1, z + 3).setTypeId(114);
									source.getBlock(x + 2, y + yfloorRelative + 1, z + 3).setData((byte) 0);
									source.getBlock(x + 2, y + yfloorRelative + 1, z + 4).setTypeId(114);
									source.getBlock(x + 2, y + yfloorRelative + 1, z + 4).setData((byte) 0);
									
									source.getBlock(x + 5, y + yfloorRelative + 1, z + 3).setTypeId(114);
									source.getBlock(x + 5, y + yfloorRelative + 1, z + 3).setData((byte) 1);
									source.getBlock(x + 5, y + yfloorRelative + 1, z + 4).setTypeId(114);
									source.getBlock(x + 5, y + yfloorRelative + 1, z + 4).setData((byte) 1);
	
									// Generate poles on the platform
									source.getBlock(x + 2, y + yfloorRelative + 2, z + 2).setTypeId(113);
									source.getBlock(x + 5, y + yfloorRelative + 2, z + 2).setTypeId(113);
									source.getBlock(x + 2, y + yfloorRelative + 2, z + 5).setTypeId(113);
									source.getBlock(x + 5, y + yfloorRelative + 2, z + 5).setTypeId(113);
									
									// Generate the spawner
									int spawnerX = x + 3 + random.nextInt(2);
									int spawnerY = y + yfloorRelative + 2;
									int spawnerZ = z + 3 + random.nextInt(2);
									Block spawnerBlock = source.getBlock(spawnerX, spawnerY, spawnerZ);
									spawnerBlock = source.getBlock(spawnerX, spawnerY, spawnerZ);
									spawnerBlock.setTypeId(52);
									CreatureSpawner theSpawner = (CreatureSpawner) spawnerBlock.getState();
									theSpawner.setSpawnedType(EntityType.BLAZE);
								
									// Generate hidden content/recourses underneath the platform
									Block block1 = source.getBlock(x + 3, y + yfloorRelative, z + 3);
									block1.setTypeId(54);
									Chest chestBlock1 = (Chest) block1.getState();
									addItemsToChest(random, chestBlock1);
	
									Block block2 = source.getBlock(x + 4, y + yfloorRelative, z + 4);
									block2.setTypeId(54);
									Chest chestBlock2 = (Chest) block2.getState();
									addItemsToChest(random, chestBlock2);
	
									/*Block block3 = source.getBlock(x + 4, y + yfloorRelative, z + 3);
									switch(random.nextInt(5)) {
									case 0:
										block3.setTypeId(14);
										break;
									case 1:
										block3.setTypeId(15);
										break;
									case 2:
										block3.setTypeId(16);
										break;
									case 3:
										block3.setTypeId(21);
										break;
									case 4:
										block3.setTypeId(56);
										break;
									default:
										block3.setTypeId(16);
									}
	
									Block block4 = source.getBlock(x + 4, y + yfloorRelative, z + 4);
									switch(random.nextInt(5)) {
									case 0:
										block4.setTypeId(14);
										break;
									case 1:
										block4.setTypeId(15);
										break;
									case 2:
										block4.setTypeId(16);
										break;
									case 3:
										block4.setTypeId(21);
										break;
									case 4:
										block4.setTypeId(56);
										break;
									default:
										block4.setTypeId(16);
									}*/
								}	
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
	
	public double distance(int x1, int y1, int x2, int y2) {
		double dx   = x1 - x2;         //horizontal difference 
		double dy   = y1 - y2;         //vertical difference 
		double dist = Math.sqrt( dx*dx + dy*dy ); //distance using Pythagoras theorem
		return dist;
	}
}