package com.timvisee.dungeonmaze.listener;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import com.timvisee.dungeonmaze.DungeonMaze;

public class PluginListener implements Listener {
	
	@EventHandler
	public void onPluginEnable(PluginEnableEvent e) {
		// Call the onPluginEnable method in the permissions manager
		Core.getPermissionsManager().onPluginEnable(e);
	}
	
	@EventHandler
	public void onPluginDisable(PluginDisableEvent e) {
		Plugin p = e.getPlugin();
		
		// Make sure the plugin instance isn't null
		if(p == null)
			return;
		
		// Make sure it's not Dungeon Maze itself
		if(p.equals(DungeonMaze.instance))
			return;
		
		// Call the onPluginDisable method in the permissions manager
		Core.getPermissionsManager().onPluginDisable(e);
		
		// Check if this plugin is hooked in to Dungeon Maze
		if(Core.getApiController().isHooked(p)) {
			// Unhook the plugin from Dungeon Maze and unregister it's API sessions
			Core.getApiController().unhookPlugin(p);
		}
	}
}
