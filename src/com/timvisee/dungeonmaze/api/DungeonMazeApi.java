package com.timvisee.dungeonmaze.api;

import java.util.List;
import java.util.logging.Logger;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.api.permission.ApiPermissionsManager;
import com.timvisee.dungeonmaze.api.world.ApiWorldManager;
import com.timvisee.dungeonmaze.util.Profiler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.timvisee.dungeonmaze.DungeonMaze;

@SuppressWarnings("UnusedDeclaration")
public class DungeonMazeApi {

	/** Defines the Dungeon Maze plugin name. */
	public static final String DM_PLUGIN_NAME = "DungeonMaze";

	/** The Dungeon Maze instance. */
	private DungeonMaze dungeonMaze;
	/** The instance of the plugin using this Dungeon Maze API session. */
	private Plugin plugin;
	/** Defines whether the API session is hooked or not. */
	private boolean hooked = false;

	/**
	 * Constructor. This will immediately try to hook into Dungeon Maze.
	 *
	 * @param plugin The instance of the plugin using this API session.
	 */
	public DungeonMazeApi(Plugin plugin) {
		this(plugin, true);
	}

	/**
	 * Constructor
	 *
	 * @param plugin The instance of the plugin using this API session.
	 * @param hook True to immediately hook into Dungeon Maze.
	 */
	public DungeonMazeApi(Plugin plugin, boolean hook) {
		// Store the plugin instance
		this.plugin = plugin;

		// Hook into Dungeon Maze
		if(hook)
			hook();
	}
	
	/**
	 * Hook into the Dungeon Maze API.
	 *
	 * @return True if succeed, false otherwise
	 */
	public boolean hook() {
		// Make sure the plugin instance is valid
		if(this.plugin == null) {
			Logger log = Logger.getLogger("Minecraft");
			if(log != null)
				log.info("Failed to hook into Dungeon Maze API, plugin instance invalid!");
			return false;
		}

		// Profile the hooking process
		Profiler profiler = new Profiler(true);

		// Get the plugins logger, and show a status message
		Logger pluginLogger = this.plugin.getLogger();
		pluginLogger.info("Hooking into Dungeon Maze...");

		// Make sure the API layer isn't hooked into Dungeon Maze already
		if(isHooked()) {
			// Show a status message, return the result
			pluginLogger.info("Already hooked into Dungeon Maze, took " + profiler.getTimeFormatted() + "!");
			return true;
		}

		try {
			// Try to get the Dungeon Maze plugin instance
			Plugin dungeonMazePlugin = Bukkit.getServer().getPluginManager().getPlugin(DM_PLUGIN_NAME);
			if(!(dungeonMazePlugin instanceof DungeonMaze)) {
				pluginLogger.info("Can't hook into Dungeon Maze, plugin not found!");
				return false;
			}

			// Set the DungeonMaze plugin instance
			this.dungeonMaze = (DungeonMaze) dungeonMazePlugin;

			// Make sure the Dungeon Maze API is enabled
			if(!this.dungeonMaze.getApiController().isEnabled()) {
				pluginLogger.info("Can't hook into Dungeon Maze, API not enabled!");
				this.dungeonMaze = null;
				return false;
			}

			// Register the current API session in Dungeon Maze
			this.dungeonMaze.getApiController().registerApiSession(this);

			// Show a status message
			pluginLogger.info("Hooked into Dungeon Maze, took " + profiler.getTimeFormatted() + "!");

			// Hook succeed, return true
			this.hooked = true;
			return true;
	        
    	} catch(NoClassDefFoundError ex) {
    		// Unable to hook into DungeonMaze, show warning/error message
			pluginLogger.info("Error while hooking into Dungeon Maze (Error: " + ex.getMessage() + ")!");
    		return false;
    		
    	} catch(Exception ex) {
    		// Unable to hook into DungeonMaze, show warning/error message
			pluginLogger.info("Error while hooking into Dungeon Maze (Error: " + ex.getMessage() + ")!");
    		return false;
    	}
    }
	
	/**
	 * Check if the plugin is hooked into DungeonMaze.
	 *
	 * @return True if hooked.
	 */
	public boolean isHooked() {
		return this.hooked;
	}
	
	/**
	 * Unhook Dungeon Maze.
	 */
	public void unhook() {
		// Make sure the API is hooked
		if(!isHooked())
			return;

		// Reset the hook state
		this.hooked = false;

		// Get the plugins logger
		Logger log;
		if(this.plugin != null)
			log = this.plugin.getLogger();
		else
			log = Logger.getLogger("Minecraft");

		// Try to register the current API session, reset the Dungeon Maze instance afterwards
		this.dungeonMaze.getApiController().unregisterApiSession(this);
		this.dungeonMaze = null;

		// Show a status message
		log.info("Unhooked Dungeon Maze!");
	}

	/**
	 * Get the Dungeon Maze plugin instance.
	 *
	 * @return Dungeon Maze plugin instance, null if not hooked into Dungeon Maze.
	 */
	public DungeonMaze getDungeonMaze() {
		return this.dungeonMaze;
	}

	/**
	 * Get the Dungeon Maze core instance.
	 *
	 * @return Dungeon Maze core instance, null if not hooked into Dungeon Maze.
	 */
	public Core getDungeonMazeCore() {
		// Make sure the Dungeon Maze instance is valid
		if(this.dungeonMaze == null)
			return null;

		// Get and return the Core instance
		return this.dungeonMaze.getCore();
	}
	
	/**
	 * Set the Dungeon Maze plugin instance.
	 *
	 * @param p Dungeon Maze plugin instance.
	 */
	public void setDungeonMaze(DungeonMaze p) {
		this.dungeonMaze = p;
	}
	
	/**
	 * Get the plugin instance that hooked into Dungeon Maze and uses this API layer.
	 *
	 * @return Plugin instance.
	 */
	public Plugin getPlugin() {
		return this.plugin;
	}
	
	/**
	 * Get the running Dungeon Maze version.
	 *
	 * @return Dungeon Maze version number, empty string if not hooked into Dungeon Maze.
	 */
	public String getVersion() {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return "";

		// Get and return the Dungeon Maze version number
		return this.dungeonMaze.getVersion();
	}
	
	/**
	 * Get the world manager instance (API Layer).
	 *
	 * @return World manager instance.
	 */
	public ApiWorldManager getWorldManager() {
		return new ApiWorldManager(this);
	}
	
	/**
	 * Get the permissions manager instance (API Layer).
	 *
	 * @return Permissions manager instance.
	 */
	public ApiPermissionsManager getPermissionsManager() {
		return new ApiPermissionsManager(this);
	}
	
	/**
	 * Check if a player is able to build in a DM world.
	 *
	 * @param w The world name.
	 * @param p The player.
	 *
	 * @return True if the player is allowed to build.
	 */
	public boolean canBuildInDungeonMazeWorld(String w, Player p) {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return false;
		
		if(getDungeonMazeCore()._getWorldManager().isDungeonMazeWorld(w))
			if(getDungeonMazeCore()._getConfigHandler().worldProtection)
				return getDungeonMazeCore()._getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.build", p.isOp());
		return true;
	}
	
	/**
	 * Check if a player is able to go onto the surface in a DM world.
	 *
	 * @param w the world name.
	 * @param p the player.
	 *
	 * @return true if the player is allowed to go on the surface.
	 */
	public boolean isPlayerAllowedOnDungeonMazeWorldSurface(String w, Player p) {
		// Make sure the plugin is hooked into Dungeon Maze
		if(!isHooked())
			return true;
		
		if(getDungeonMazeCore()._getWorldManager().isDungeonMazeWorld(w))
			if(!getDungeonMazeCore()._getConfigHandler().allowSurface)
				return getDungeonMazeCore()._getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.surface", p.isOp());
		return true;
	}
	
	/**
	 * @param target (int)
	 *
	 * @return True if the object is in the list, false otherwise.
	 */
	public boolean isInWhiteList(Object target){
		List<Object> list = Core.getConfigHandler().blockWhiteList;
		
		if(list == null)
			return(false);

		for(Object aList : list)
			if(aList.equals(target))
				return true;
		return false;
	}
	
	/**
	 * Check whether a mob spawner type is allowed to spawn.
	 *
	 * @param mob The name of the mob.
	 *
	 * @return True if the mob spawner is allow for this mob.
	 */
	public boolean isMobSpawnerAllowed(String mob) {
		return getDungeonMazeCore()._getConfigHandler().mobs.contains(mob);
	}
}
