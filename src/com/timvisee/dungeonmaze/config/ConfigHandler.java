package com.timvisee.dungeonmaze.config;

import java.util.List;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler {

	// TODO: Use material enums instead of ID's due to ID deprecation by Mojang
	
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
		config = new Config();
		
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
	 * @param target Block type ID
	 * @return true if the object is in the list
	 */
	@SuppressWarnings("UnusedDeclaration")
	@Deprecated // Deprecate this for use Material enum
	public boolean isInWhiteList(int target) {
		List<Object> list = Core.getConfigHandler().blockWhiteList;
		
		if(list == null)
			return(false);
		
		for(Object entry : list)
			if(entry instanceof Integer)
				return ((Integer) entry == target);
		return false;
	}
	
	/**
	 * Check whether a block is in the block whitelist or not
	 * @param material Block type
	 * @return true if the object is in the list
	 */
	/*
	 *  TODO: Actually use the getId() magic value,
	 *  need to update when Minecraft/Bukkit will remove them
	 */
	public boolean isInWhiteList(Material material) {
		int target = material.getId();
		List<Object> list = Core.getConfigHandler().blockWhiteList;
		
		if(list == null)
			return(false);
		
		for(Object entry : list)
			if(entry instanceof Integer)
				return ((Integer) entry == target);
		return false;
	}
	
	/**
	 * Check whether a mob spawner is allowed or not
	 * @param mob The name of the mob
	 * @return True if the mob spawner is allowed for this mob
	 */
	public boolean isMobSpawnerAllowed(String mob) {
		return Core.getConfigHandler().mobs.contains(mob);
	}
}
