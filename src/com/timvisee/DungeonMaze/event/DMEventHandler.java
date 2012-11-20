package com.timvisee.DungeonMaze.event;

import org.bukkit.Server;

public class DMEventHandler {
	
	private Server s;
	
	public DMEventHandler(Server s) {
		this.s = s;
	}
	
	public Server getServer() {
		return this.s;
	}
	
	public DMEvent callEvent(DMEvent e) {
		this.s.getPluginManager().callEvent(e);
		return e;
	}
}
