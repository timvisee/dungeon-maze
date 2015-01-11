package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class GreatFurnaceRoomPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 5;
	public static final int CHANCE_FURNACE = 1; //Promile

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		if (rand.nextInt(1000) < CHANCE_FURNACE) {
			// Register the room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c, x, y, z);
			
			// Floor
			for (int x2=x; x2 <= x + 8; x2+=1)
			    for (int z2=z; z2 <= z + 8; z2+=1)
			        c.getBlock(x2, y + 1, z2).setType(Material.STONE);
			
			// Change the layer below the stone floor to cobblestone
			for (int x2=x; x2 <= x + 8; x2++)
			    for (int z2=z; z2 <= z + 8; z2++)
			    		c.getBlock(x2, y, z2).setType(Material.COBBLESTONE);
			
			// Pillar1
			for (int y2=y + 1; y2 <= y + 5; y2+=1)
			    c.getBlock(x + 1, y2, z + 1).setType(Material.COBBLESTONE);
			
			// Pillar2
			for (int y2=y + 1; y2 <= y + 5; y2+=1)
			    c.getBlock(x + 7, y2, z + 1).setType(Material.COBBLESTONE);
			
			// Pillar3
			for (int y2=y + 1; y2 <= y + 5; y2+=1)
			    c.getBlock(x + 1, y2, z + 7).setType(Material.COBBLESTONE);
			
			// Pillar4
			for (int y2=y + 1; y2 <= y + 5; y2+=1)
				c.getBlock(x + 7, y2, z + 7).setType(Material.COBBLESTONE);
			
			// Furnace base
			c.getBlock(x + 2, y + 2, z + 2).setType(Material.FURNACE);
			c.getBlock(x + 2, y + 2, z + 2).setData((byte) 2);
			addItemsToFurnace(rand, (Furnace) c.getBlock(x + 2, y + 2, z + 2).getState());
			c.getBlock(x + 3, y + 2, z + 2).setType(Material.GLASS);
			c.getBlock(x + 4, y + 2, z + 2).setType(Material.GLASS);
			c.getBlock(x + 5, y + 2, z + 2).setType(Material.FURNACE);
			c.getBlock(x + 5, y + 2, z + 2).setData((byte) 2);
			addItemsToFurnace(rand, (Furnace) c.getBlock(x + 5, y + 2, z + 2).getState());
			c.getBlock(x + 2, y + 2, z + 3).setType(Material.GLASS);
			c.getBlock(x + 3, y + 2, z + 3).setType(Material.STATIONARY_LAVA);
			c.getBlock(x + 4, y + 2, z + 3).setType(Material.STATIONARY_LAVA);
			c.getBlock(x + 5, y + 2, z + 3).setType(Material.GLASS);
			c.getBlock(x + 2, y + 2, z + 4).setType(Material.GLASS);
			c.getBlock(x + 3, y + 2, z + 4).setType(Material.STATIONARY_LAVA);
			c.getBlock(x + 4, y + 2, z + 4).setType(Material.STATIONARY_LAVA);
			c.getBlock(x + 5, y + 2, z + 4).setType(Material.GLASS);
			c.getBlock(x + 2, y + 2, z + 5).setType(Material.FURNACE);
			c.getBlock(x + 2, y + 2, z + 5).setData((byte) 3);
			addItemsToFurnace(rand, (Furnace) c.getBlock(x + 2, y + 2, z + 5).getState());
			c.getBlock(x + 3, y + 2, z + 5).setType(Material.GLASS);
			c.getBlock(x + 4, y + 2, z + 5).setType(Material.GLASS);
			c.getBlock(x + 5, y + 2, z + 5).setType(Material.FURNACE);
			c.getBlock(x + 5, y + 2, z + 5).setData((byte) 3);
			addItemsToFurnace(rand, (Furnace) c.getBlock(x + 5, y + 2, z + 5).getState());
			
			// Furnace pipe
			for (int x2=x + 3; x2 <= x + 4; x2+=1)
			    for (int y2=y + 3; y2 <= y + 5; y2+=1)
			            for (int z2=z + 3; z2 <= z + 4; z2+=1)
			            c.getBlock(x2, y2, z2).setType(Material.BRICK);
			if(c.getBlock(x+3, y+6, z+3).getType() == Material.AIR)
				for (int x2=x + 3; x2 <= x + 4; x2+=1)
				    for (int z2=z + 3; z2 <= z + 4; z2+=1)
				    	c.getBlock(x2, y + 6, z2).setType(Material.BRICK);
		}
	}
	
	public void addItemsToFurnace(Random random, Furnace furnace) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(41, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(42, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(45, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(263, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(263, 1, (short) 1));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(265, 2, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(265, 4, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(266, 2, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(266, 4, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(297, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(325, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(366, 2, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(366, 4, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(318, 3, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(318, 5, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(320, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(350, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(368, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(369, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(370, 1, (short) 0));
		if(random.nextInt(100) < 45)
			items.add(new ItemStack(371, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(372, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(375, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(377, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(378, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(381, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(382, 1, (short) 0));
		
		// Add the selected items into the furnace
		if(random.nextInt(100) < 60)
			furnace.getInventory().setResult(items.get(random.nextInt(items.size())));
		
		furnace.update();
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