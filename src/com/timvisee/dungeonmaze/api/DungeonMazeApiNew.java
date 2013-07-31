package com.timvisee.dungeonmaze.api;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.eventhandler.DMEventHandler;
import com.timvisee.dungeonmaze.manager.DMPermissionsManager;
import com.timvisee.dungeonmaze.manager.DMWorldManager;

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
	
	/**
	 * Get the DM world manager
	 * @return DM world manager
	 */
	public DMWorldManager getDMWorldManager() {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return null;
		
		// Return the world manager instance
		return getDM().getDMWorldManager();
	}

	/**
	 * Get all DM worlds
	 * @return DM worlds
	 */
	public List<String> getDMWorlds() {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return null;
		
		// Return the list of Dungeon Maze worlds
		return DMWorldManager.getDMWorlds();
	}

	/**
	 * Get all loaded DM worlds
	 * @return loaded DM worlds
	 */
	public List<String> getLoadedDMWorlds() {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return null;
		
		// Get and return the list of loaded Dungeon Maze worlds
		return DMWorldManager.getLoadedDMWorlds();
	}

	/**
	 * Check if a world is a DM world
	 * @param w The world
	 * @return True if the world is a DM world
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
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return false;
		
		return DMWorldManager.isDMWorld(w);
	}

	/**
	 * Check if a player is in a DM world
	 * @param p the player
	 * @return true if the player is in an DM world
	 */
	public boolean isInDMWorld(Player p) {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return false;
		
		return isDMWorld(p.getWorld());
	}
	
	/**
	 * Get the DM world a player is in
	 * @param p the player
	 * @return the DM world a player is in, returns null when a player isn't in a DM world
	 */
	public World getDMWorld(Player p) {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return null;
		
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
	public String getDMWorldName(Player p) {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return "";
		
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
	public boolean canBuildInDMWorld(String w, Player p) {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return false;
		
		if(DMWorldManager.isDMWorld(w))
			if(getDM().worldProtection)
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
		
		if(DMWorldManager.isDMWorld(w))
			if(!DungeonMaze.allowSurface)
				return getDM().getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.surface", p.isOp());
		return true;
	}
	
	/**
	 * 
	 * @param Object (int)
	 * @return true if the object is in the list
	 */
	public boolean isInWhiteList(Object target){
		List<Object> list = DungeonMaze.blockWhiteList;
		
		if(list == null)
			return(false);
		
		for(int x = 0; x < list.size(); ++x)
			if(list.get(x).equals(target))
				return true;
		return false;
	}
	
	/**
	 * 
	 * @param String mobName
	 * @return true if the mobspawner is allow for this mob
	 */
	public boolean allowMobSpawner(String mob) {
		return DungeonMaze.mobs.contains(mob);
	}
	
	/**
	 * Setup and get the DM Event handler
	 * @return DM Event handler
	 */
	public DMEventHandler setUpDMEventHandler() {
		return (DMEventHandler) DMEventHandler.getServer();
	}
	
	/**
	 * Get the level a block is on in Dungeon Maze
	 * @param b the block
	 * @return The level as a DungeonMaze level, returns levels 1-7. Returns 0 when the block isn't on a DungeonMaze level
	 */
	public int getDMLevel(Block b) {
		// Get the height of the spawner
		int y = b.getY();
		
		// Is the block bellow the Dungeon Maze?
		if(y < 30)
			return 0;
		
		// Check if the block is inside the Dungeon Maze, if so return it's level
		int curLevel = 1;
		for (int dml=30; dml < 30 + (7 * 6); dml += 6) {
			if(dml >= y && dml + 6 < y)
				return curLevel;
			curLevel++;
		}
		
		// The block was above the Dungeon Maze, return zero
		return 0;
	}
}
