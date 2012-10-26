package com.timvisee.DungeonMaze.API;

import java.util.List;

import org.bukkit.Chunk;
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
	public DMWorldManager getDMWorldManager() {
		return plugin.getDMWorldManager();
	}

	/**
	 * Get all DM worlds
	 * @return DM worlds
	 */
	public List<String> getDMWorlds() {
		return plugin.getDMWorldManager().getDMWorlds();
	}

	/**
	 * Get all loaded DM worlds
	 * @return loaded DM worlds
	 */
	public List<String> getLoadedDMWorlds() {
		return plugin.getDMWorldManager().getLoadedDMWorlds();
	}

	/**
	 * Check if a world is a DM world
	 * @param w the world
	 * @return true if the world is a DM world
	 */
	public boolean isDMWorld(World w) {
		return isDMWorld(w.getName());
	}

	/**
	 * Check if a world is a DM world
	 * @param w the world name
	 * @return true if the world is a DM world
	 */
	public boolean isDMWorld(String w) {
		return plugin.getDMWorldManager().isDMWorld(w);
	}

	/**
	 * Check if a player is in a DM world
	 * @param p the player
	 * @return true if the player is in an DM world
	 */
	public boolean isInDMWorld(Player p) {
		return plugin.getDMWorldManager().isDMWorld(p.getWorld().getName());
	}
	
	/**
	 * Get the DM world a player is in
	 * @param p the player
	 * @return the DM world a player is in, returns null when a player isn't in a DM world
	 */
	public World getDMWorld(Player p) {
		// Check if the player is in a DM world
		if(plugin.getDMWorldManager().isDMWorld(p.getWorld().getName()))
			return p.getWorld();
		return null;
	}
	
	/**
	 * Get the DM world name a player is in
	 * @param p the player
	 * @return the DM world name a player is in, returns an empty string when the player isn't in a DM world
	 */
	public String getDMWorldName(Player p) {
		// Check if the player is in a DM world
		if(plugin.getDMWorldManager().isDMWorld(p.getWorld().getName()))
			return p.getWorld().getName();
		return "";
	}
	
	/**
	 * Check if a player is able to build in a DM world
	 * @param w the world name
	 * @param p the player
	 * @return true if the player is allowed to build
	 */
	public boolean canBuildInDMWorld(String w, Player p) {
		if(plugin.getDMWorldManager().isDMWorld(w))
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
	public boolean isPlayerAllowedOnDMWorldSurface(String w, Player p) {
		if(plugin.getDMWorldManager().isDMWorld(w))
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