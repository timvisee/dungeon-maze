package com.timvisee.dungeonmaze.event.eventhandler;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DMEventHandler extends Event {
	
	private boolean isCancelled;
	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public HandlerList getHandlerList() {
        return handlers;
    }
	
	public boolean isCancelled() {
		return this.isCancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.isCancelled = cancelled;
	}
}
