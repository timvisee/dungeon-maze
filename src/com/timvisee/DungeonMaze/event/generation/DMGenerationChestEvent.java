package com.timvisee.DungeonMaze.event.generation;

import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.timvisee.DungeonMaze.event.EventHandler.DMEventHandler;

public class DMGenerationChestEvent extends DMEventHandler {
	
	private Block b;
	private List<ItemStack> is;
	
	public DMGenerationChestEvent(Block b, List<ItemStack> is) {
		this.b = b;
		this.is = is;
	}
	
	public Block getBlock() {
		return this.b;
	}
	
	public List<ItemStack> getContents() {
		return this.is;
	}
	
	public void setContents(List<ItemStack> is) {
		if(is != null)
			this.is = is;
	}
	
	public World getWorld() {
		return this.b.getWorld();
	}

}
