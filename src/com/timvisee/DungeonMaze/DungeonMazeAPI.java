package com.timvisee.DungeonMaze;

import java.util.List;

import org.bukkit.World;
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

	public static World getDMWorld(Player player) {
		World worldNameByPlayer = null;
		if (isInDMWorld(player)) {
			worldNameByPlayer = player.getWorld();
		}
		return worldNameByPlayer;
	}	
	
	public static String getDMWorldName(Player player) {
		String worldNameByPlayer = null;
		if (isInDMWorld(player)) {
			worldNameByPlayer = player.getWorld().getName();
		}
		return worldNameByPlayer;
	}
	
	public static boolean isADMWorld (World w) {
		List<String> worlds = plugin.getDungeonMazeWorlds();
		boolean result = false;
		for (String world : worlds) {
			if (w.getName() == world) {
				result = true;
			}
		}
		return result;
	}
	
	public static boolean canBuildInDMWorld (World world, Player player) {
		boolean result = true;
		if (isADMWorld(world.getName())) {
			if (plugin.getConfig().getBoolean("worldProtection", false)) {
				if (plugin.hasPermission(player, "dungeonmaze.bypass.build", player.isOp()) == false) {
					result = false;
				}
			}
		}
		return result;
	}
	
	public static boolean isPlayerAllowOnWorldSurface (World world, Player player) {
		boolean result = true;
		if (isADMWorld(world.getName())) {
			if ((plugin.getConfig().getBoolean("allowSurface", false) == false)) {
				if (plugin.hasPermission(player, "dungeonmaze.bypass.surface", player.isOp()) == false) {
					result = false;
				}
			}
		}
		return result;
	}
	
	public static void setPlugin(DungeonMaze plugin) {
		DungeonMazeAPI.plugin = plugin;
	}
	
	public static DungeonMaze getPlugin() {
		return plugin;
	}
}
