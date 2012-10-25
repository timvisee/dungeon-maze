package com.timvisee.DungeonMaze.Populators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import com.timvisee.DungeonMaze.DungeonMaze;

public class GreatFurnaceRoomPopulator extends BlockPopulator {
	public static final int CHANCE_OF_FURNACE = 1; //Promile

	@Override
	public void populate(World world, Random random, Chunk source) {
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(5*6); y+=6) {
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if (random.nextInt(1000) < CHANCE_OF_FURNACE) {
							if(!DungeonMaze.isConstantRoom(world.getName(), source.getX(), source.getZ(), x, y, z)) {
								DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, y, z);
								
								// Get the floor location 
								int yfloorRelative = 0;
								Block roomBottomBlock = source.getBlock(x+2, y, z+2);  // x and z +2 so that you aren't inside a wall!
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  
									yfloorRelative++;
								}
								
								//floor
								for (int x2=x; x2 <= x + 8; x2+=1) {
								    for (int z2=z; z2 <= z + 8; z2+=1) {
								        source.getBlock(x2, y + 1, z2).setTypeId(1);
								    }
								}
								// Change the layer underneeth the stone floor to cobblestone
								for (int x2=x; x2 <= x + 8; x2++) {
								    for (int z2=z; z2 <= z + 8; z2++) {
								        source.getBlock(x2, y, z2).setTypeId(4);
								    }
								}
								//pillar1
								for (int y2=y + 1; y2 <= y + 5; y2+=1) {
								        source.getBlock(x + 1, y2, z + 1).setTypeId(4);
								    }
								//pillar2
								for (int y2=y + 1; y2 <= y + 5; y2+=1) {
								        source.getBlock(x + 7, y2, z + 1).setTypeId(4);
								    }
								//pillar3
								for (int y2=y + 1; y2 <= y + 5; y2+=1) {
								        source.getBlock(x + 1, y2, z + 7).setTypeId(4);
								    }
								//pillar4
								for (int y2=y + 1; y2 <= y + 5; y2+=1) {
								        source.getBlock(x + 7, y2, z + 7).setTypeId(4);
								    }
								//furnace base
								source.getBlock(x + 2, y + 2, z + 2).setTypeId(61);
								source.getBlock(x + 2, y + 2, z + 2).setData((byte) 2);
								addItemsToFurnace(random, (Furnace) source.getBlock(x + 2, y + 2, z + 2).getState());
								source.getBlock(x + 3, y + 2, z + 2).setTypeId(20);
								source.getBlock(x + 4, y + 2, z + 2).setTypeId(20);
								source.getBlock(x + 5, y + 2, z + 2).setTypeId(61);
								source.getBlock(x + 5, y + 2, z + 2).setData((byte) 2);
								addItemsToFurnace(random, (Furnace) source.getBlock(x + 5, y + 2, z + 2).getState());
								source.getBlock(x + 2, y + 2, z + 3).setTypeId(20);
								source.getBlock(x + 3, y + 2, z + 3).setTypeId(11);
								source.getBlock(x + 4, y + 2, z + 3).setTypeId(11);
								source.getBlock(x + 5, y + 2, z + 3).setTypeId(20);
								source.getBlock(x + 2, y + 2, z + 4).setTypeId(20);
								source.getBlock(x + 3, y + 2, z + 4).setTypeId(11);
								source.getBlock(x + 4, y + 2, z + 4).setTypeId(11);
								source.getBlock(x + 5, y + 2, z + 4).setTypeId(20);
								source.getBlock(x + 2, y + 2, z + 5).setTypeId(61);
								source.getBlock(x + 2, y + 2, z + 5).setData((byte) 3);
								addItemsToFurnace(random, (Furnace) source.getBlock(x + 2, y + 2, z + 5).getState());
								source.getBlock(x + 3, y + 2, z + 5).setTypeId(20);
								source.getBlock(x + 4, y + 2, z + 5).setTypeId(20);
								source.getBlock(x + 5, y + 2, z + 5).setTypeId(61);
								source.getBlock(x + 5, y + 2, z + 5).setData((byte) 3);
								addItemsToFurnace(random, (Furnace) source.getBlock(x + 5, y + 2, z + 5).getState());
								//furnace pipe
								for (int x2=x + 3; x2 <= x + 4; x2+=1) {
								    for (int y2=y + 3; y2 <= y + 5; y2+=1) {
								            for (int z2=z + 3; z2 <= z + 4; z2+=1) {
								            source.getBlock(x2, y2, z2).setTypeId(45);
								        }
								    }
								}
								if(source.getBlock(x+3, y+6, z+3).getTypeId() == 0) {
									for (int x2=x + 3; x2 <= x + 4; x2+=1) {
									    for (int z2=z + 3; z2 <= z + 4; z2+=1) {
									    	source.getBlock(x2, y + 6, z2).setTypeId(45);
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
		if(random.nextInt(100) < 60) {
			furnace.getInventory().setResult(/*random.nextInt(furnace.getInventory().getSize()), */items.get(random.nextInt(items.size())));
		}
		furnace.update();
	}
}