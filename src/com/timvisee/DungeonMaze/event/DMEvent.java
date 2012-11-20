package com.timvisee.DungeonMaze.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.timvisee.DungeonMaze.DungeonMaze;

public class DMEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	public DMEvent() {}
	
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
