package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.DMGenerationChestEvent;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.DMMazeStructureType;
import com.timvisee.dungeonmaze.util.DMChestUtils;

public class LibraryRoomPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 3;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_OF_LIBRARY = 2; //Promile
	public static final double CHANCE_OF_LIBRARY_ADDITION_PER_LEVEL = -0.167; /* to 1 */

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int yCeiling = args.getCeilingY();
		int z = args.getChunkZ();
		
		// Apply chances
		if(rand.nextInt(1000) < CHANCE_OF_LIBRARY + (CHANCE_OF_LIBRARY_ADDITION_PER_LEVEL * (y - 30) / 6)) {
			// Register the current room als constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c, x, y, z);
			
			//stone floor in the bottom of the room
			for (int x2=x + 1; x2 <= x + 6; x2+=1)
	            for (int z2=z + 1; z2 <= z + 6; z2+=1)
	                c.getBlock(x2, yFloor, z2).setTypeId(1);
			
			// Cobblestone layer underneeth the stone floor
			for (int x2=x + 1; x2 <= x + 6; x2+=1)
	            for (int z2=z + 1; z2 <= z + 6; z2+=1)
	                c.getBlock(x2, yFloor - 1, z2).setTypeId(4);
			
			// Make stone walls on each side of the room
			for (int x2=x + 1; x2 <= x + 6; x2+=1)
	            for (int y2=yFloor; y2 <= yCeiling + 5; y2+=1)
	                c.getBlock(x2, y2, z).setTypeId(98);
			for (int x2=x + 1; x2 <= x + 6; x2+=1)
	            for (int y2=yFloor; y2 <= yCeiling + 5; y2+=1)
	                c.getBlock(x2, y2, z + 7).setTypeId(98);
			for (int z2=z + 1; z2 <= z + 6; z2+=1)
	            for (int y2=yFloor; y2 <= yCeiling + 5; y2+=1) 
	                c.getBlock(x, y2, z2).setTypeId(98);
			for (int z2=z + 1; z2 <= z + 6; z2+=1)
	            for (int y2=yFloor; y2 <= yCeiling + 5; y2+=1)
	                c.getBlock(x + 7, y2, z2).setTypeId(98);
			
			// Generate some holes in the wall to make some kind of doors
			for (int x2=x + 3; x2 <= x + 4; x2+=1)
	            for (int y2=yFloor + 1; y2 <= yFloor + 3; y2+=1)
	                c.getBlock(x2, y2, z).setTypeId(0);
			for (int x2=x + 3; x2 <= x + 4; x2+=1)
	            for (int y2=yFloor + 1; y2 <= yFloor + 3; y2+=1)
	                c.getBlock(x2, y2, z+7).setTypeId(0);
			for (int z2=z + 3; z2 <= z + 4; z2+=1)
	            for (int y2=yFloor + 1; y2 <= yFloor + 3; y2+=1)
	                c.getBlock(x, y2, z2).setTypeId(0);
			for (int z2=z + 3; z2 <= z + 4; z2+=1)
	            for (int y2=yFloor + 1; y2 <= yFloor + 3; y2+=1)
	                c.getBlock(x + 7, y2, z2).setTypeId(0);

			// Generate the bookshelfs, one on each side
			for (int x2=x + 5; x2 <= x + 6; x2+=1)
	            for (int y2=yFloor + 1; y2 <= yFloor + 3; y2+=1)
	                c.getBlock(x2, y2, z + 1).setTypeId(47);
			for (int x2=x + 1; x2 <= x + 2; x2+=1)
	            for (int y2=yFloor + 1; y2 <= yFloor + 3; y2+=1)
	                c.getBlock(x2, y2, z + 6).setTypeId(47);
			for (int z2=z + 1; z2 <= z + 2; z2+=1)
	            for (int y2=yFloor + 1; y2 <= yFloor + 3; y2+=1)
	                c.getBlock(x + 1, y2, z2).setTypeId(47);
			for (int z2=z + 5; z2 <= z + 6; z2+=1)
	            for (int y2=yFloor + 1; y2 <= yFloor + 3; y2+=1)
	                c.getBlock(x + 6, y2, z2).setTypeId(47);

			// Make the two pilars
			for (int y2 = yFloor + 1; y2 <= yFloor + 3; y2+=1) {
				c.getBlock(x + 3, y2, z + 4).setTypeId(86);
				c.getBlock(x + 4, y2, z + 3).setTypeId(86);
            }
			// Add the two chests
			c.getBlock(x + 3, yFloor + 1, z + 3).setTypeId(54);

			// Call the Chest generation event
			DMGenerationChestEvent event = new DMGenerationChestEvent(c.getBlock(x + 3, yFloor + 1, z + 3), rand, genChestContent(rand), DMMazeStructureType.LIBRARY_ROOM);
			Bukkit.getServer().getPluginManager().callEvent(event);

			// Do the event
			if(!event.isCancelled()) {
				// Make sure the chest is still there, a developer could change the chest through the event!
				if(event.getBlock().getTypeId() == 54)
				// Add the contents to the chest
				DMChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
			}

			c.getBlock(x + 4, yFloor + 1, z + 4).setTypeId(54);

			// Call the Chest generation event
			DMGenerationChestEvent event2 = new DMGenerationChestEvent(c.getBlock(x + 4, yFloor + 1, z + 4), rand, genChestContent(rand), DMMazeStructureType.LIBRARY_ROOM);
			Bukkit.getServer().getPluginManager().callEvent(event2);

			// Do the event
			if(!event2.isCancelled()) {
				// Make sure the chest is still there, a developer could change the chest through the event!
				if(event2.getBlock().getTypeId() == 54)
				// Add the contents to the chest
				DMChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
			}

			// Add 4 lanterns on each side of the room near the book shelfs
			c.getBlock(x + 2, yFloor + 2, z + 1).setTypeId(50);
			c.getBlock(x + 2, yFloor + 2, z + 1).setData((byte) 3);
			c.getBlock(x + 6, yFloor + 2, z + 2).setTypeId(50);
			c.getBlock(x + 6, yFloor + 2, z + 2).setData((byte) 2);
			c.getBlock(x + 1, yFloor + 2, z + 5).setTypeId(50);
			c.getBlock(x + 1, yFloor + 2, z + 5).setData((byte) 1);
			c.getBlock(x + 5, yFloor + 2, z + 6).setTypeId(50);
			c.getBlock(x + 5, yFloor + 2, z + 6).setData((byte) 4);
		}
	}
	
	public List<ItemStack> genChestContent(Random random) {
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
			break;
		}
		
		List<ItemStack> result = new ArrayList<ItemStack>();
		
		// Add the selected items randomly
		for (int i = 0; i < itemCountInChest; i++)
			result.add(items.get(random.nextInt(items.size())));
		return result;
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