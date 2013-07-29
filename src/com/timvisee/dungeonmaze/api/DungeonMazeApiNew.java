package com.timvisee.dungeonmaze.api;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.manager.DMPermissionsManager;

public class DungeonMazeApiNew {
	
	public static String DM_PLUGIN_NAME = "DungeonMaze";
	
	private DungeonMaze dm;
	private Plugin p;
	
	/**
	 * Constructor
	 */
	public DungeonMazeApiNew(Plugin p) {
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
				log.info("[" + this.p.getName() + "] Error while hooking into Dungeon Maze!");
    		return false;
    		
    	} catch(Exception ex) {
    		// Unable to hook into DungeonMaze, show warning/error message.
			if(this.p != null)
				log.info("[" + this.p.getName() + "] Error while hooking into Dungeon Maze!");
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
	public void getDM(DungeonMaze p) {
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
	 * Get the Dungeon Maze permissions manager
	 * @return Dungeon Maze permissions manager instance
	 */
	public DMPermissionsManager getPermissionsManager() {
		return this.dm.getPermissionsManager();
	}
}
