package com.timvisee.dungeonmaze.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import com.timvisee.dungeonmaze.DungeonMaze;

public class DMWorldListener implements Listener {
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		// Refresh the list with DM worlds
		DungeonMaze.instance.getWorldManager().refresh();
	}
}
