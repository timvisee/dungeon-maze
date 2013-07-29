package com.timvisee.dungeonmaze.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import com.timvisee.dungeonmaze.DungeonMaze;

public class DMPluginListener implements Listener {
	
	@EventHandler
	public void onPluginEnable(PluginEnableEvent e) {
		// Call the onPluginEnable method in the permissions manager
		DungeonMaze.instance.getPermissionsManager().onPluginEnable(e);
	}
	
	@EventHandler
	public void onPluginDisable(PluginDisableEvent e) {
		Plugin p = e.getPlugin();
		
		// Call the onPluginDisable method in the permissions manager
		DungeonMaze.instance.getPermissionsManager().onPluginDisable(e);
		
		// Check if this plugin is hooked in to Dungeon Maze
		if(DungeonMaze.instance.getApiController().isHooked(p)) {
			// Unhook the plugin from Dungeon Maze and unregister it's API sessions
			DungeonMaze.instance.getApiController().unhookPlugin(p);
		}
	}
}
