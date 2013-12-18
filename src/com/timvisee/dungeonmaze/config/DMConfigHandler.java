package com.timvisee.dungeonmaze.config;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.timvisee.dungeonmaze.DungeonMaze;

public class DMConfigHandler {
	
	// Configuration cache
	public FileConfiguration config;
	public boolean unloadWorldsOnPluginDisable;
	public boolean allowSurface;
	public boolean worldProtection;
	public List<Object> blockWhiteList;
	public boolean enableUpdateCheckerOnStartup;
	public boolean usePermissions;
	public boolean useBypassPermissions;
	public List<String> mobs;
	
	@SuppressWarnings("unchecked")
	public void load() {
		// Get the config instance
		config = new DMConfig();
		
		// Load (and cache) the properties
		unloadWorldsOnPluginDisable = config.getBoolean("unloadWorldsOnPluginDisable", true);
		allowSurface = config.getBoolean("allowSurface", true);
		worldProtection = config.getBoolean("worldProtection", false);
		enableUpdateCheckerOnStartup = config.getBoolean("updateChecker.enabled", true);
		usePermissions = config.getBoolean("usePermissions", true);
		useBypassPermissions = config.getBoolean("useBypassPermissions", true);
		blockWhiteList = (List<Object>) config.getList("blockWhiteList");
		mobs = config.getStringList("mobs");
	}
	
	/**
	 * Check whether a block is in the block whitelist or not
	 * @param int Block type ID
	 * @return true if the object is in the list
	 */
	@Deprecated // Deprecate this for use Material enum
	public boolean isInWhiteList(int target) {
		List<Object> list = DungeonMaze.instance.getConfigHandler().blockWhiteList;
		
		if(list == null)
			return(false);
		
		for(Object entry : list)
			if(entry instanceof Integer)
				return (((Integer) entry).intValue() == target);
		return false;
	}
	
	/**
	 * Check whether a block is in the block whitelist or not
	 * @param Material Block type
	 * @return true if the object is in the list
	 */
	/*
	 *  TODO: Actually use the getId() magic value,
	 *  need to update when Minecraft/Bukkit will remove them
	 */
	public boolean isInWhiteList(Material material) {
		int target = material.getId();
		List<Object> list = DungeonMaze.instance.getConfigHandler().blockWhiteList;
		
		if(list == null)
			return(false);
		
		for(Object entry : list)
			if(entry instanceof Integer)
				return (((Integer) entry).intValue() == target);
		return false;
	}
	
	/**
	 * Check whether a mob spawner is allowed or not
	 * @param String mobName The name of the mob
	 * @return True if the mob spawner is allowed for this mob
	 */
	public boolean isMobSpawnerAllowed(String mob) {
		return DungeonMaze.instance.getConfigHandler().mobs.contains(mob);
	}
}
