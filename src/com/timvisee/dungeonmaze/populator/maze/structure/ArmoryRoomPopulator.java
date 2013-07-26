package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeBlockPopulatorArgs;

public class ArmoryRoomPopulator extends DMMazeBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_OF_ARMORYROOM = 900; // Promile

	// TODO: Armory room still not used, finish it and put it into Dungeon Maze

	@Override
	public void populateMaze(DMMazeBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int floorOffset = args.getFloorOffset();
		int z = args.getChunkZ();
		
		// Apply chances
		if(rand.nextInt(1000) < CHANCE_OF_ARMORYROOM) {
			// Register the current room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c, x, y, z);
			
			// Blocks
			for (int x2=x; x2 <= x + 7; x2+=1) {
			    for (int y2=y; y2 <= y + 5; y2++)
			        for (int z2=z; z2 <= z + 7; z2+=1) {
			        c.getBlock(x2, y2 + floorOffset, z2).setTypeId(4);
			    }
			}
			// Adminium
			for (int x2=x + 1; x2 <= x + 6; x2+=1) {
			    for (int y2=y; y2 <= y + 4; y2++)
			        for (int z2=z + 1; z2 <= z + 6; z2+=1) {
			        c.getBlock(x2, y2 + floorOffset, z2).setTypeId(7);
			    }
			}
			// Air
			for (int x2=x + 2; x2 <= x + 5; x2+=1) {
			    for (int y2=y + 1; y2 <= y + 3; y2++)
			        for (int z2=z + 2; z2 <= z + 5; z2+=1) {
			        c.getBlock(x2, y2 + floorOffset, z2).setTypeId(0);
			    }
			}
			for (int x2=x + 1; x2 <= x + 6; x2+=1) {
			    for (int y2=y + 1; y2 <= y + 5; y2++) {
			        c.getBlock(x2, y2 + floorOffset, z + 0).setTypeId(0);
			    }
			}
			
			// Pumpkins
			c.getBlock(x + 2, yFloor + 1, z + 2).setTypeId(91);
			c.getBlock(x + 2, yFloor + 1, z + 5).setTypeId(91);
			c.getBlock(x + 5, yFloor + 1, z + 2).setTypeId(91);
			c.getBlock(x + 5, yFloor + 1, z + 5).setTypeId(91);
			c.getBlock(x + 2, yFloor + 2, z + 2).setTypeId(91);
			c.getBlock(x + 2, yFloor + 2, z + 5).setTypeId(91);
			c.getBlock(x + 5, yFloor + 2, z + 2).setTypeId(91);
			c.getBlock(x + 5, yFloor + 2, z + 5).setTypeId(91);
			c.getBlock(x + 2, yFloor + 3, z + 2).setTypeId(91);
			c.getBlock(x + 2, yFloor + 3, z + 5).setTypeId(91);
			c.getBlock(x + 5, yFloor + 3, z + 2).setTypeId(91);
			c.getBlock(x + 5, yFloor + 3, z + 5).setTypeId(91);
			
			// Chests
			c.getBlock(x + 2, yFloor + 1, z + 3).setTypeId(54);
			c.getBlock(x + 2, yFloor + 1, z + 4).setTypeId(54);
			c.getBlock(x + 3, yFloor + 1, z + 5).setTypeId(54);
			c.getBlock(x + 4, yFloor + 1, z + 5).setTypeId(54);
			c.getBlock(x + 5, yFloor + 1, z + 3).setTypeId(54);
			c.getBlock(x + 5, yFloor + 1, z + 4).setTypeId(54);
			
			// Iron doors
			c.getBlock(x + 3, yFloor + 1, z + 1).setTypeId(71);
			c.getBlock(x + 3, yFloor + 1, z + 1).setData((byte) 0);
			c.getBlock(x + 4, yFloor + 1, z + 1).setTypeId(71);
			c.getBlock(x + 4, yFloor + 1, z + 1).setData((byte) 3);
			c.getBlock(x + 3, yFloor + 2, z + 1).setTypeId(71);
			c.getBlock(x + 3, yFloor + 2, z + 1).setData((byte) 8);
			c.getBlock(x + 4, yFloor + 2, z + 1).setTypeId(71);
			c.getBlock(x + 4, yFloor + 2, z + 1).setData((byte) 11);
		}
	}
	
	public void addItemsToChest(Random random, Chest chest) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(50, 16, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(50, 20, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(262, 24, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(262, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(264, 3, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(new ItemStack(265, 3, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(new ItemStack(266, 3, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(new ItemStack(267, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(282, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(306, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(307, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(308, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(309, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(310, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(311, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(312, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(313, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(318, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(320, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(322, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(331, 7, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(354, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(357, 8, (short) 0));
		
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
		}
		
		// Add the selected items to a random place inside the chest
		for (int i = 0; i < itemCountInChest; i++)
			chest.getInventory().setItem(random.nextInt(chest.getInventory().getSize()), items.get(random.nextInt(items.size())));
		chest.update();
	}
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return MIN_LAYER;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return MAX_LAYER;
	}
}