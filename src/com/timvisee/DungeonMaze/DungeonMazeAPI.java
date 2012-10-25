package com.timvisee.DungeonMaze;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DungeonMazeAPI {

	private static DungeonMaze plugin;
		
	public static boolean isInDMWorld(Player player) {
		List<String> worlds = plugin.getDungeonMazeWorlds();
		boolean result = false;
		for (String world : worlds) {
			if (player.getWorld().getName() == world) {
				result = true;
			}
		}
		return result;
	}
	
	public static List<String> getDMWorlds() {
		return plugin.getDungeonMazeWorlds();
	}
	
	public static void setPlugin(DungeonMaze plugin) {
		DungeonMazeAPI.plugin = plugin;
	}
	
	public static DungeonMaze getPlugin() {
		return plugin;
	}
}
