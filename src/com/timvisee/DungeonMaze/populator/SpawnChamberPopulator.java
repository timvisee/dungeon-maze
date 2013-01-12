package com.timvisee.DungeonMaze.populator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import com.timvisee.DungeonMaze.DungeonMaze;

public class SpawnChamberPopulator extends BlockPopulator {
	@Override
	public void populate(World world, Random random, Chunk source) {
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			if(source.getX() == 0 && source.getZ() == 0) {
				int x = 0;
				int y = 30+(6*6);
				int z = 0;
								
				DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, y, z);
				
				// Break out the orriginal walls
				for (int xx = 0; xx < 8; xx++) {
					for (int yy = y + 2; yy < 30+(7*6); yy++) {
						for(int zz = 0; zz < 8; zz++) {
							source.getBlock(x + xx, yy, z + zz).setTypeId(0);
						}
					}
				}
				
				// Generate corners
				for (int yy = y + 2; yy < 30+(7*6); yy++) {
					source.getBlock(x + 0, yy, z + 0).setTypeId(98);
					source.getBlock(x + 7, yy, z + 0).setTypeId(98);
					source.getBlock(x + 0, yy, z + 7).setTypeId(98);
					source.getBlock(x + 7, yy, z + 7).setTypeId(98);
				}
				
				//floor
				for (int xx=x; xx <= x + 7; xx++) {
				    for (int zz=z; zz <= z + 7; zz++) {
				        source.getBlock(xx, y + 1, zz).setTypeId(98);
				    }
				}
				// Change the layer underneeth the stone floor to cobblestone
				for (int xx=x; xx <= x + 8; xx++) {
				    for (int zz=z; zz <= z + 0; zz++) {
				        source.getBlock(xx, y + 1, zz).setTypeId(4);
				    }
				}
				//Ceiling
				for (int xx=x; xx <= x + 8; xx++) {
				    for (int zz=z; zz <= z + 8; zz++) {
				        source.getBlock(xx, y + 6, zz).setTypeId(98);
				    }
				}
				// Generate 4 circulair blocks in the middle of the floor
				for (int xx=x + 3; xx <= x + 4; xx++) {
				    for (int zz=z + 3; zz <= z + 4; zz++) {
				        source.getBlock(xx, y + 1, zz).setTypeId(98);
				        source.getBlock(xx, y + 1, zz).setData((byte) 3);
				    }
				}
				
				// Create walls
				for (int xx=x + 1; xx <= x + 6; xx++) {
		            for (int yy=y + 2; yy <= y + 5; yy++) {
		                source.getBlock(xx, yy, z).setTypeId(101);
		                source.getBlock(xx, yy, z + 7).setTypeId(101);
		            }
		        }
				for (int zz=z + 1; zz <= z + 6; zz++) {
		            for (int yy=y + 2; yy <= y + 5; yy++) {
		                source.getBlock(x, yy, zz).setTypeId(101);
		                source.getBlock(x + 7, yy, zz).setTypeId(101);
		            }
		        }
				
				// Create gates
				for (int xx=x + 2; xx <= x + 5; xx++) {
		            for (int yy=y + 2; yy <= y + 4; yy++) {
		                source.getBlock(xx, yy, z).setTypeId(98);
		                source.getBlock(xx, yy, z + 7).setTypeId(98);
		            }
		        }
				for (int zz=z + 2; zz <= z + 5; zz++) {
		            for (int yy=y + 2; yy <= y + 4; yy++) {
		                source.getBlock(x, yy, zz).setTypeId(98);
		                source.getBlock(x + 7, yy, zz).setTypeId(98);
		            }
		        }
				for (int xx=x + 3; xx <= x + 4; xx++) {
		            for (int yy=y + 2; yy <= y + 3; yy++) {
		                source.getBlock(xx, yy, z).setTypeId(0);
		                source.getBlock(xx, yy, z + 7).setTypeId(0);
		            }
		        }
				for (int zz=z + 3; zz <= z + 4; zz++) {
		            for (int yy=y + 2; yy <= y + 3; yy++) {
		                source.getBlock(x, yy, zz).setTypeId(0);
		                source.getBlock(x + 7, yy, zz).setTypeId(0);
		            }
		        }
				
				// Create chests
				source.getBlock(x + 1, y + 2, z + 1).setTypeId(54);
				source.getBlock(x + 1, y + 2, z + 1).setData((byte) 3);
				source.getBlock(x + 1, y + 2, z + 6).setTypeId(54);
				source.getBlock(x + 1, y + 2, z + 6).setData((byte) 2);
				source.getBlock(x + 6, y + 2, z + 1).setTypeId(54);
				source.getBlock(x + 6, y + 2, z + 1).setData((byte) 3);
				source.getBlock(x + 6, y + 2, z + 6).setTypeId(54);
				source.getBlock(x + 6, y + 2, z + 6).setData((byte) 2);
				
				// Create torches
				source.getBlock(x + 1, y + 3, z + 2).setTypeId(50);
				source.getBlock(x + 1, y + 3, z + 5).setTypeId(50);
				source.getBlock(x + 6, y + 3, z + 2).setTypeId(50);
				source.getBlock(x + 6, y + 3, z + 5).setTypeId(50);
				source.getBlock(x + 2, y + 3, z + 1).setTypeId(50);
				source.getBlock(x + 2, y + 3, z + 6).setTypeId(50);
				source.getBlock(x + 5, y + 3, z + 1).setTypeId(50);
				source.getBlock(x + 5, y + 3, z + 6).setTypeId(50);				
			}
		}
	}
	
	public void addItemsToChest(Random random, Chest chest) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(50, 4, (short) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(50, 8, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(50, 12, (short) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(260, 1, (short) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(262, 16, (short) 0));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(262, 24, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(264, 1, (short) 0));
		}
		if(random.nextInt(100) < 50) {
			items.add(new ItemStack(265, 1, (short) 0));
		}
		if(random.nextInt(100) < 60) {
			items.add(new ItemStack(266, 1, (short) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(267, 1, (short) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(268, 1, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(272, 1, (short) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(296, 1, (short) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(296, 2, (short) 0));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(296, 3, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(297, 1, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(298, 1, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(299, 1, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(300, 1, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(301, 1, (short) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(302, 1, (short) 0));
		} 
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(303, 1, (short) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(304, 1, (short) 0));
		}
		if(random.nextInt(100) < 40) {
			items.add(new ItemStack(305, 1, (short) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(306, 1, (short) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(307, 1, (short) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(308, 1, (short) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(309, 1, (short) 0));
		}
		if(random.nextInt(100) < 30) {
			items.add(new ItemStack(318, 3, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(318, 5, (short) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(318, 7, (short) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(319, 1, (short) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(320, 1, (short) 0));
		}
		if(random.nextInt(100) < 15) {
			items.add(new ItemStack(331, 5, (short) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(331, 8, (short) 0));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(331, 13, (short) 0));
		}
		if(random.nextInt(100) < 3) {
			items.add(new ItemStack(331, 21, (short) 0));
		}
		if(random.nextInt(100) < 10) {
			items.add(new ItemStack(345, 1, (short) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(349, 1, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(350, 1, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(350, 1, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(351, 1, (short) 3));
		}
		if(random.nextInt(100) < 5) {
			items.add(new ItemStack(354, 1, (short) 0));
		}
		if(random.nextInt(100) < 80) {
			items.add(new ItemStack(357, 3, (short) 0));
		}
		if(random.nextInt(100) < 20) {
			items.add(new ItemStack(357, 5, (short) 0));
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