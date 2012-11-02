package com.timvisee.DungeonMaze.API;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.timvisee.DungeonMaze.DMWorldManager;
import com.timvisee.DungeonMaze.DungeonMaze;

public class DungeonMazeAPI {
	
	private static DungeonMaze plugin;

	/**
	 * Get the DM world manager
	 * @return DM world manager
	 */
	public static DMWorldManager getDMWorldManager() {
		return plugin.getDMWorldManager();
	}

	/**
	 * Get all DM worlds
	 * @return DM worlds
	 */
	public static List<String> getDMWorlds() {
		plugin.getDMWorldManager();
		return DMWorldManager.getDMWorlds();
	}

	/**
	 * Get all loaded DM worlds
	 * @return loaded DM worlds
	 */
	public static List<String> getLoadedDMWorlds() {
		plugin.getDMWorldManager();
		return DMWorldManager.getLoadedDMWorlds();
	}

	/**
	 * Check if a world is a DM world
	 * @param w the world
	 * @return true if the world is a DM world
	 */
	public static boolean isDMWorld(World w) {
		return isDMWorld(w.getName());
	}

	/**
	 * Check if a world is a DM world
	 * @param w the world name
	 * @return true if the world is a DM world
	 */
	public static boolean isDMWorld(String w) {
		plugin.getDMWorldManager();
		return DMWorldManager.isDMWorld(w);
	}

	/**
	 * Check if a player is in a DM world
	 * @param p the player
	 * @return true if the player is in an DM world
	 */
	public static boolean isInDMWorld(Player p) {
		return isDMWorld(p.getWorld());
	}
	
	/**
	 * Get the DM world a player is in
	 * @param p the player
	 * @return the DM world a player is in, returns null when a player isn't in a DM world
	 */
	public static World getDMWorld(Player p) {
		plugin.getDMWorldManager();
		// Check if the player is in a DM world
		if(DMWorldManager.isDMWorld(p.getWorld().getName()))
			return p.getWorld();
		return null;
	}
	
	/**
	 * Get the DM world name a player is in
	 * @param p the player
	 * @return the DM world name a player is in, returns an empty string when the player isn't in a DM world
	 */
	public static String getDMWorldName(Player p) {
		plugin.getDMWorldManager();
		// Check if the player is in a DM world
		if(DMWorldManager.isDMWorld(p.getWorld().getName()))
			return p.getWorld().getName();
		return "";
	}
	
	/**
	 * Check if a player is able to build in a DM world
	 * @param w the world name
	 * @param p the player
	 * @return true if the player is allowed to build
	 */
	public static boolean canBuildInDMWorld(String w, Player p) {
		plugin.getDMWorldManager();
		if(DMWorldManager.isDMWorld(w))
			if(plugin.getConfig().getBoolean("worldProtection", false))
				return plugin.hasPermission(p, "dungeonmaze.bypass.build", p.isOp());
		return true;
	}
	
	/**
	 * Check if a player is able to go onto the surface in a DM world
	 * @param w the world name
	 * @param p the player
	 * @return true if the player is allowed to go on the surface
	 */
	public static boolean isPlayerAllowedOnDMWorldSurface(String w, Player p) {
		plugin.getDMWorldManager();
		if(DMWorldManager.isDMWorld(w))
			if(!plugin.getConfig().getBoolean("allowSurface", true))
				return plugin.hasPermission(p, "dungeonmaze.bypass.surface", p.isOp());
		return true;
	}
	
	/**
	 * Set the DM plugin
	 * @param plugin the DM plugin
	 */
	public static void setPlugin(DungeonMaze plugin) {
		DungeonMazeAPI.plugin = plugin;
	}

	/**
	 * Get the DM plugin
	 * @return the DM plugin
	 */
	public static DungeonMaze getPlugin() {
		return plugin;
	}
}