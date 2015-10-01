package com.timvisee.dungeonmaze.util;

import java.util.List;
import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("UnusedDeclaration")
public class ChestUtils {

	/**
	 * Get the chest state instance from a block
	 * @param b Block to get the chest state instance from (Block must be a chest)
	 * @return Chest state instance, or null if failed
	 */
	public static Chest getChest(Block b) {
		// Make sure the block isn't null
		if(b == null)
			return null;
		
		// Cast the block to a chest state instance
		try {
			BlockState bs = b.getState();
			if(bs instanceof Chest)
				// Return the chest state instance
				return (Chest) bs;
		
		} catch(Exception ignored) { }
		
		return null;
	}

	/**
	 * Check whether a block can be casted to a chest state instance or not
	 * @param b The block to check
	 * @return True if the block could be casted to a chest state instance
	 */
	public static boolean isChest(Block b) {
		return (getChest(b) != null);
	}
	
	/**
	 * Empty a chest
	 * @param b Chest to empty
	 * @return False if failed
	 */
	public static boolean emptyChest(Block b) {
		// Cast the block to a chest
		Chest c = getChest(b);
		
		// Make sure the chest isn't null
		if(c == null)
			return false;
		
		// Empty the chest and return the result
		return emptyChest(c);
	}
	
	/**
	 * Empty a chest
	 * @param c Chest to empty
	 * @return False if failed
	 */
	public static boolean emptyChest(Chest c) {
		// Make sure the chest state instance isn't null
		if(c == null)
			return false;
		
		// Clear the inventory
		try {
			// Clear the chest
			c.getInventory().clear();
			
			// Force update the chest contents
			c.update(true, false);
			
			return true;
			
		} catch(Exception ex) {
			return false;
		}
	}
	
	/**
	 * Add a list of items to a chest
	 * @param b Chest to add the items in
	 * @param newContents List of item stacks to add to the chest
	 * @return False if failed
	 */
	public static boolean addItemsToChest(Block b, List<ItemStack> newContents) {
		// Cast the block to a chest
		Chest c = getChest(b);
		
		// Make sure the chest isn't null
		if(c == null)
			return false;
		
		// Add the items and return the result
		return addItemsToChest(c, newContents);
	}
	
	/**
	 * Add a list of items to a chest
	 * @param c Chest to add the items in
	 * @param newContents List of item stacks to add to the chest
	 * @return False if failed
	 */
	public static boolean addItemsToChest(Chest c, List<ItemStack> newContents) {
		// Add the items and return the result
		return addItemsToChest(c, newContents, false, null);
	}
	
	/**
	 * Add a list of items to a chest
	 * @param b Chest to add the items in
	 * @param newContents List of item stacks to add to the chest
	 * @param randOrder True to add items in a random order
	 * @param rand Random instance to use as seed
	 * @return False if failed
	 */
	public static boolean addItemsToChest(Block b, List<ItemStack> newContents, boolean randOrder, Random rand) {
		// Cast the block to a chest
		Chest c = getChest(b);
		
		// Make sure the chest isn't null
		if(c == null)
			return false;
		
		// Add the items and return the result
		return addItemsToChest(c, newContents, randOrder, rand);
	}
	
	/**
	 * Add a list of items to a chest
	 * @param c Chest to add the items in
	 * @param newContents List of item stacks to add to the chest
	 * @param randOrder True to add items in a random order
	 * @param rand Random instance to use as seed
	 * @return False if failed
	 */
	public static boolean addItemsToChest(Chest c, List<ItemStack> newContents, boolean randOrder, Random rand) {
		// Make sure the chest instance and the item stack list aren't null
		if(c == null || newContents == null)
			return false;
		
		// Make sure the random object isn't null if the items should be added in random order
		if(randOrder && rand == null)
			return false;
		
		// Clear the chest inventory first
		c.getInventory().clear();
		
		// Add all the items
		if(randOrder) {
			// Add the contents randomly
			for(ItemStack curStack : newContents) {
				// Make sure the current ItemStack isn't null
				if(curStack == null)
					continue;
				
				// Set the item in the chest
				c.getInventory().setItem(rand.nextInt(c.getInventory().getSize()), curStack);
			}
			
		} else {
			
			// Add the contents in order
			for(int i = 0; i < newContents.size(); i++) {
				ItemStack curStack = newContents.get(i);
				
				// Make sure the current ItemStack isn't null
				if(curStack == null)
					continue;
				
				// Make sure the current item fits in the chest, to prevent errors
				if(i >= c.getInventory().getSize())
					continue;
				
				// Set the item in the chest
				c.getInventory().setItem(i, curStack);
			}
		}
		
		// Force the chest to update it's contents
		c.update(true, false);
		
		return true;
	}
}
