package com.timvisee.DungeonMaze;

import java.util.logging.Logger;

import org.bukkit.event.world.WorldLoadEvent;

public class DungeonMazeWorldListener {
	public static Logger log = Logger.getLogger("Minecraft");
	public static DungeonMaze plugin;

	public DungeonMazeWorldListener(DungeonMaze instance) {
		plugin = instance;
	}
	
	public void onWorldLoad(WorldLoadEvent e) {
		// Refresh the list with Dungeon Maze worlds
		plugin.getDungeonMazeWorlds();
		plugin.getDungeonMazePreloadWorlds();
	}
}
