package com.timvisee.dungeonmaze.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import com.timvisee.dungeonmaze.manager.DMWorldManager;

public class DMWorldListener implements Listener {
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		// Refresh the list with DM worlds
		DMWorldManager.refresh();
	}
}
