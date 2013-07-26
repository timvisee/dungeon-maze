package com.timvisee.dungeonmaze.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.PluginDisableEvent;

import com.timvisee.dungeonmaze.DungeonMaze;

public class DMPluginListener implements Listener {
	
	@EventHandler
	public void onPluginEnable(PluginEnableEvent e) {
		// Call the onPluginEnable method in the permissions manager
		DungeonMaze.instance.getPermissionsManager().onPluginEnable(e);
	}
	
	@EventHandler
	public void onPluginDisable(PluginDisableEvent e) {
		// Call the onPluginDisable method in the permissions manager
		DungeonMaze.instance.getPermissionsManager().onPluginDisable(e);
	}
}
