package com.timvisee.dungeonmaze.api;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.api.manager.DMAPermissionsManager;
import com.timvisee.dungeonmaze.api.manager.DMAWorldManager;

public class DungeonMazeApi {
	
	public static String DM_PLUGIN_NAME = "DungeonMaze";
	
	private DungeonMaze dm;
	private Plugin p;
	
	/**
	 * Constructor
	 */
	public DungeonMazeApi(Plugin p) {
		// Store the plugin instance
		this.p = p;
		
		// Try to hook into Dungeon Maze
		hook();
	}
	
	/**
	* Hook Dungeon Maze
	* @return True if succeed
	*/
	public boolean hook() {
		Logger log = Logger.getLogger("Minecraft");
		
		try {
    		// Unhook Dungeon Maze first if already hooked
			if(isHooked())
				unhook();
			
			// Try to get the Dungeon Maze plugin instance
			Plugin p = Bukkit.getServer().getPluginManager().getPlugin(DM_PLUGIN_NAME);
			if(p == null && !(p instanceof DungeonMaze)) {
				if(this.p != null)
					log.info("[" + this.p.getName() + "] Can't hook into Dungeon Maze, plugin not found!");
					
				return false;
			}

			// Show a status message
			if(this.p != null)
				log.info("[" + this.p.getName() + "] Hooked into Dungeon Maze!");
    		
			// Set the DungeonMaze plugin instance
			this.dm = (DungeonMaze) p;
			
			// Make sure the Dungeon Maze API is enabled
			if(!this.dm.getApiController().isEnabled()) {
				log.info("[" + this.p.getName() + "] Can't hook into Dungeon Maze, API not enabled!");
				this.dm = null;
				return false;
			}
			
			// Register the current API session in Dungeon Maze
			this.dm.getApiController().registerApiSession(this);
			
			// Hook succeed, return true
			return true;
	        
    	} catch(NoClassDefFoundError ex) {
    		// Unable to hook into DungeonMaze, show warning/error message.
			if(this.p != null)
				log.info("[" + this.p.getName() + "] Error while hooking into Dungeon Maze (Error: " + ex.getMessage() + ")!");
    		return false;
    		
    	} catch(Exception ex) {
    		// Unable to hook into DungeonMaze, show warning/error message.
			if(this.p != null)
				log.info("[" + this.p.getName() + "] Error while hooking into Dungeon Maze (Error: " + ex.getMessage() + ")!");
    		return false;
    	}
    }
	
	/**
	 * Check if the plugin is hooked into DungeonMaze
	 * @return True if hooked
	 */
	public boolean isHooked() {
		return (this.dm != null);
	}
	
	/**
	 * Unhook Dungeon Maze
	 */
	public void unhook() {
		Logger log = Logger.getLogger("Minecraft");
		
		// Unhook Dungeon Maze
		this.dm = null;
		
		// Show a status message
		if(this.p != null)
			log.info("[" + this.p.getName() + "] Unhooked Dungeon Maze!");
	}
	
	/**
	 * Get the Dungeon Maze plugin instance
	 * @return Dungeon Maze plugin instance, null if not hooked into Dungeon Maze
	 */
	public DungeonMaze getDM() {
		return getDungeonMaze();
	}
	
	/**
	 * Get the Dungeon Maze plugin instance
	 * @return Dungeon Maze plugin instance, null if not hooked into Dungeon Maze
	 */
	public DungeonMaze getDungeonMaze() {
		return this.dm;
	}
	
	/**
	 * Set the Dungeon Maze plugin instance
	 * @param p Dungeon Maze plugin instance
	 */
	public void setDM(DungeonMaze p) {
		setDungeonMaze(p);
	}
	
	/**
	 * Set the Dungeon Maze plugin instance
	 * @param p Dungeon Maze plugin instance
	 */
	public void setDungeonMaze(DungeonMaze p) {
		this.dm = p;
	}
	
	/**
	 * Get the plugin instance that hooked into Dungeon Maze and uses this API layer
	 * @return Plugin instance
	 */
	public Plugin getPlugin() {
		return this.p;
	}
	
	/**
	 * Get the running Dungeon Maze version
	 * @return Dungeon Maze version number, empty string if not hooked into Dungeon Maze
	 */
	public String getVersion() {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return "";
		
		return this.dm.getVersion();
	}
	
	/**
	 * Get the world manager instance (API Layer)
	 * @return World manager instance
	 */
	public DMAWorldManager getWorldManager() {
		return new DMAWorldManager(this.dm);
	}
	
	/**
	 * Get the permissions manager instance (API Layer)
	 * @return Permissions manager instance
	 */
	public DMAPermissionsManager getPermissionsManager() {
		return new DMAPermissionsManager(this.dm);
	}
	
	/**
	 * Check if a player is able to build in a DM world
	 * @param w the world name
	 * @param p the player
	 * @return true if the player is allowed to build
	 */
	public boolean canBuildInDMWorld(String w, Player p) {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return false;
		
		if(getDM().getWorldManager().isDMWorld(w))
			if(getDM().getConfigHandler().worldProtection)
				return getDM().getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.build", p.isOp());
		return true;
	}
	
	/**
	 * Check if a player is able to go onto the surface in a DM world
	 * @param w the world name
	 * @param p the player
	 * @return true if the player is allowed to go on the surface
	 */
	public boolean isPlayerAllowedOnDMWorldSurface(String w, Player p) {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return true;
		
		if(getDM().getWorldManager().isDMWorld(w))
			if(!getDM().getConfigHandler().allowSurface)
				return getDM().getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.surface", p.isOp());
		return true;
	}
	
	/**
	 * 
	 * @param Object (int)
	 * @return true if the object is in the list
	 */
	public boolean isInWhiteList(Object target){
		List<Object> list = DungeonMaze.instance.getConfigHandler().blockWhiteList;
		
		if(list == null)
			return(false);
		
		for(int x = 0; x < list.size(); ++x)
			if(list.get(x).equals(target))
				return true;
		return false;
	}
	
	/**
	 * Check whether a mob spawner type is allowed to spawn
	 * @param String mobName
	 * @return true if the mobspawner is allow for this mob
	 */
	public boolean isMobSpawnerAllowed(String mob) {
		return getDM().getConfigHandler().mobs.contains(mob);
	}
}
