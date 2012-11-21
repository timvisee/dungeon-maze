package com.timvisee.DungeonMaze.event.generation;

import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import com.timvisee.DungeonMaze.event.EventHandler.DMEventHandler;

public class DMGenerationChestEvent extends DMEventHandler {
	
	private Block b;
	private List<ItemStack> is;
	private Random random;
	
	public DMGenerationChestEvent(Block b, Random random, List<ItemStack> is) {
		this.b = b;
		this.is = is;
		this.random = random;
	}
	
	public Block getBlock() {
		return this.b;
	}
	
	public List<ItemStack> getContents() {
		return this.is;
	}
	
	public Random getRandom() {
		return this.random;
	}
	
	public void setContents(List<ItemStack> is) {
		if(is != null)
			this.is = is;
	}
	
	public World getWorld() {
		return this.b.getWorld();
	}

	public void addItemsToChest(Random random, Chest chest, List<ItemStack> newContents) {
		// Add new content to a chest
		chest.getInventory().clear();
		for (int i = 0; i < newContents.size(); i++) {
			chest.getInventory().setItem(random.nextInt(chest.getInventory().getSize()), newContents.get(i));
		}
		chest.update();
	}
}
