package com.timvisee.DungeonMaze.event;

public class DMCancellableEvent extends DMEvent {
	
	private boolean isCancelled = false;
	
	public DMCancellableEvent() {
		super();
	}
	
	public boolean isCancelled() {
		return this.isCancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.isCancelled = cancelled;
	}
}
