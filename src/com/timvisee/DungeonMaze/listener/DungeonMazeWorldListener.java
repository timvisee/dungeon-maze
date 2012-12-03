package com.timvisee.DungeonMaze.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import com.timvisee.DungeonMaze.DungeonMaze;
import com.timvisee.DungeonMaze.manager.DMWorldManager;

public class DungeonMazeWorldListener implements Listener {
	public static DungeonMaze plugin;

	public DungeonMazeWorldListener(DungeonMaze instance) {
		plugin = instance;
	}
	
	public void onWorldLoad(WorldLoadEvent e) {
		plugin.getDMWorldManager();
		// Refresh the list with DM worlds
		DMWorldManager.refresh();
	}
}
