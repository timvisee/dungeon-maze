package com.timvisee.DungeonMaze.listener;

import java.util.logging.Logger;

import org.bukkit.event.world.WorldLoadEvent;

import com.timvisee.DungeonMaze.DungeonMaze;
import com.timvisee.DungeonMaze.manager.DMWorldManager;

public class DungeonMazeWorldListener {
	public static Logger log = Logger.getLogger("Minecraft");
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
