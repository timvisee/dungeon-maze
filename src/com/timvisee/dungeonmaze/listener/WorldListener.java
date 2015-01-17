package com.timvisee.dungeonmaze.listener;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldListener implements Listener {

	/**
	 * Called when a world is loaded.
	 *
	 * @param event Event reference.
	 */
	@EventHandler
	public void onWorldLoad(WorldLoadEvent event) {
		// Refresh the list with DM worlds
		Core.getWorldManager().refresh();
	}
}
