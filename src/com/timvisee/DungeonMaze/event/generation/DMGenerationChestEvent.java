package com.timvisee.DungeonMaze.event.generation;

import java.util.List;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class DMGenerationChestEvent extends Event {
	
	private Block b;
	private List<ItemStack> is;
	private boolean isCancelled;
	private static final HandlerList handlers = new HandlerList();
	private static Server s;
	
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
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
	
	public boolean isCancelled() {
		return this.isCancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.isCancelled = cancelled;
	}

	
	public static Server getServer() {
		return s;
	}
	

}
