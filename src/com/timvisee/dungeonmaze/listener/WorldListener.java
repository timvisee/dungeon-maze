package com.timvisee.dungeonmaze.listener;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldListener implements Listener {
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		// Refresh the list with DM worlds
		Core.getWorldManager().refresh();
	}
}
