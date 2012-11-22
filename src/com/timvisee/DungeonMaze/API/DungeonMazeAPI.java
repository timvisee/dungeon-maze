package com.timvisee.DungeonMaze.API;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.timvisee.DungeonMaze.DungeonMaze;
import com.timvisee.DungeonMaze.event.EventHandler.DMEventHandler;
import com.timvisee.DungeonMaze.manager.DMWorldManager;

public class DungeonMazeAPI {
	
	private static DungeonMaze plugin;
	
	/**
	 * Hook into Dungeon Maze
	 * @return instance DM instance
	 */
    public static DungeonMaze hookDungeonMaze() {
    	Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("DungeonMaze");
        if (plugin == null && !(plugin instanceof DungeonMaze)) {
        	return null;
         }
    	return (DungeonMaze) plugin;
    }

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
		if(DMWorldManager.isDMWorld(p.getWorld().getName())) {
			return p.getWorld();			
		}
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
		if(DMWorldManager.isDMWorld(p.getWorld().getName())) {
			return p.getWorld().getName();	
		}
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
			if(DungeonMaze.worldProtection) {
				return plugin.getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.build", p.isOp());	
			}
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
			if(!DungeonMaze.allowSurface)
				return plugin.getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.surface", p.isOp());
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
	
	/**
	 * 
	 * @param Object (int)
	 * @return true if the object is in the list
	 */
	public static boolean isInWhiteList(Object target){
		List<Object> list = DungeonMaze.blockWhiteList;
		if(list == null) return(false);
		for(int x = 0; x < list.size(); ++x) if(list.get(x).equals(target)) return(true);
		return(false);
		}
	
	/**
	 * 
	 * @param String mobName
	 * @return true if the mobspawner is allow for this mob
	 */
	public static boolean allowMobSpawner(String mob) {
		return DungeonMaze.mobs.contains(mob);
	}
	
	/**
	 * Setup and get the DM Event handler
	 * @return DM Event handler
	 */
	public static DMEventHandler setupDMEventHandler() {
		return (DMEventHandler) DMEventHandler.getServer();
	}
	
	/**
	 * Get the level a block is on in Dungeon Maze
	 * @param b the block
	 * @return The level as a DungeonMaze level, returns levels 1-7. Returns 0 when the block isn't on a DungeonMaze level
	 */
	public static int getDMLevel(Block b) {
		// Get the height of the spawner
		int y = b.getY();
		
		// Is the block bellow the Dungeon Maze?
		if(y < 30)
			return 0;
		
		// Check if the block is inside the Dungeon Maze, if so return it's level
		int curLevel = 1;
		for (int dml=30; dml < 30+(7*6); dml+=6) {
			if(dml >= y && dml + 6 < y)
				return curLevel;
			curLevel++;
		}
		
		// The block was above the Dungeon Maze, return zero
		return 0;
	}
}