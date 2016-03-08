package com.timvisee.dungeonmaze.listener;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import com.timvisee.dungeonmaze.DungeonMaze;

public class PluginListener implements Listener {

	/**
	 * Called when a plugin is disabled.
	 *
	 * @param event Event reference.
	 */
	@EventHandler
	public void onPluginDisable(PluginDisableEvent event) {
		// Get the plugin instance
		Plugin plugin = event.getPlugin();
		
		// Make sure the plugin instance isn't null
		if(plugin == null)
			return;
		
		// Make sure it's not Dungeon Maze itself
		if(plugin.equals(DungeonMaze.instance))
			return;
		
		// Check if this plugin is hooked in to Dungeon Maze
		if(Core.getApiController().isHooked(plugin))
			// Unhook the plugin from Dungeon Maze and unregister it's API sessions
			Core.getApiController().unhookPlugin(plugin);
	}
}
