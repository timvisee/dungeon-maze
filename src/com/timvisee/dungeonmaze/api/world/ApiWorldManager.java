package com.timvisee.dungeonmaze.api.world;

import java.util.List;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.api.DungeonMazeApi;
import com.timvisee.dungeonmaze.plugin.multiverse.MultiverseHandler;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.World;

/**
 * API layer for the DMWorldManager class
 */
@SuppressWarnings("UnusedDeclaration")
public class ApiWorldManager {

	/** Defines the Dungeon Maze API instance. */
	private DungeonMazeApi dungeonMazeApi;
	
	/**
	 * Constructor.
	 *
	 * @param dungeonMazeApi Dungeon Maze API instance.
	 */
	public ApiWorldManager(DungeonMazeApi dungeonMazeApi) {
		this.dungeonMazeApi = dungeonMazeApi;
	}
	
	/**
	 * Get the Dungeon Maze API instance.
	 *
	 * @return Dungeon Maze API instance.
	 */
	public DungeonMazeApi getDungeonMazeApi() {
		return this.dungeonMazeApi;
	}
	
	/**
	 * Set the Dungeon Maze API instance.
	 *
	 * @param dungeonMazeApi Dungeon Maze API instance.
	 */
	public void setDungeonMazeApi(DungeonMazeApi dungeonMazeApi) {
		this.dungeonMazeApi = dungeonMazeApi;
	}

	/**
	 * Get the Dungeon Maze core.
	 *
	 * @return Dungeon Maze core instance.
	 */
	private Core getCore() {
		// Make sure the API instance is valid
		if(this.dungeonMazeApi == null)
			return null;

		// Get and return the Core instance
		return this.dungeonMazeApi.getDungeonMazeCore();
	}

	/**
	 * Get the Dungeon Maze world manager.
	 *
	 * @return Dungeon Maze world manager instance.
	 */
	private WorldManager getWorldManager() {
		// Get the Core instance and make sure it's valid
		Core core = getCore();
		if(core == null)
			return null;

		// Get and return the world manager
		return core._getWorldManager();
	}

	/**
	 * Check whether the world manager is valid and ready to use.
	 *
	 * @return True if the world manager is valid and ready, false otherwise.
	 */
	private boolean isWorldManagerValid() {
		// Make sure the world manager instance is set
		WorldManager worldManager = getWorldManager();
		if(worldManager == null)
			return false;

		// Check whether the world manager is initialized, return the result
		return worldManager.isInit();
	}

	/**
	 * Refresh the list with Dungeon Maze worlds.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean refresh() {
		// Make sure the world manager instance is valid
		if(!isWorldManagerValid())
			return false;

		// Refresh the world manager
		return getWorldManager().refresh();
	}

	/**
	 * Get all Dungeon Maze worlds.
	 *
	 * @return List of all Dungeon Maze worlds.
	 */
	public List<String> getDungeonMazeWorlds() {
		// Make sure the world manager instance is valid
		if(!isWorldManagerValid())
			return null;

		// Get the list of Dungeon Maze worlds
		return getWorldManager().getDungeonMazeWorlds();
	}

	/**
	 * Get all Dungeon Maze worlds.
	 *
	 * @param preloadWorlds True to return the Dungeon Maze worlds that should be preloaded.
	 *
	 * @return List of all Dungeon Maze worlds.
	 */
	public List<String> getDungeonMazeWorlds(boolean preloadWorlds) {
		// Make sure the world manager instance is valid
		if(!isWorldManagerValid())
			return null;

		// Get the list of Dungeon Maze worlds
		return getWorldManager().getDungeonMazeWorlds(preloadWorlds);
	}

	/**
	 * Get all loaded Dungeon Maze worlds.
	 *
	 * @return List of all loaded Dungeon Maze worlds
	 */
	public List<String> getLoadedDungeonMazeWorlds() {
		// Make sure the world manager instance is valid
		if(!isWorldManagerValid())
			return null;

		// Get the list of loaded Dungeon Maze worlds
		return getWorldManager().getLoadedDungeonMazeWorlds();
	}

	/**
	 * Check whether a world exists. The world doesn't need to be loaded.
	 *
	 * @param worldName The name of the world to check for.
	 *
	 * @return True if any world with this name exists, false otherwise.
	 */
	public boolean isWorld(String worldName) {
		// Make sure the world manager instance is valid
		if(!isWorldManagerValid())
			return false;

		// Check whether the world exists
		return getWorldManager().isWorld(worldName);
	}
	
	/**
	 * Check if a world is a Dungeon Maze world.
	 *
	 * @param w The name of the world to check for.
	 *
	 * @return True if the world is a Dungeon Maze world.
	 */
	public boolean isDungeonMazeWorld(String w) {
		// Make sure the world manager instance is valid
		if(!isWorldManagerValid())
			return false;

		// Check whether a world is a Dungeon Maze world
		return getWorldManager().isDungeonMazeWorld(w);
	}

	/**
	 * Check whether a world is loaded.
	 *
	 * @param worldName The name of the world to check for.
	 *
	 * @return True if the world is loaded, false otherwise.
	 */
	public boolean isWorldLoaded(String worldName) {
		// Make sure the world manager instance is valid
		if(!isWorldManagerValid())
			return false;

		// Check whether a world is loaded
		return getWorldManager().isWorldLoaded(worldName);
	}
	
	/**
	 * Check if a world is a loaded Dungeon Maze world.
	 *
	 * @param w The name of the world to check for.
	 *
	 * @return True if the world is a loaded Dungeon Maze world.
	 */
	public boolean isLoadedDungeonMazeWorld(String w) {
		// Make sure the world manager instance is valid
		if(!isWorldManagerValid())
			return false;

		// Check whether a world is a loaded Dungeon Maze world
		return getWorldManager().isLoadedDungeonMazeWorld(w);
	}

	/**
	 * Load a world if it isn't loaded yet. The world doesn't need to be a Dungeon Maze world.
	 *
	 * @param worldName The name of the world to load.
	 *
	 * @return The world instance if the world is loaded, null otherwise.
	 * The world instance will also be returned if the world was already loaded.
	 */
	public World loadWorld(String worldName) {
		// Make sure the world manager instance is valid
		if(!isWorldManagerValid())
			return null;

		// Load a world, return the result
		return getWorldManager().loadWorld(worldName);
	}

	/**
	 * Preload all the Dungeon Maze worlds that should be preloaded.
	 *
	 * @return The number of preloaded worlds. Returns a negative number on failure.
	 */
	public int preloadDungeonMazeWorlds() {
		// Make sure the world manager instance is valid
		if(!isWorldManagerValid())
			return -1;

		// Preload the Dungeon Maze worlds
		return getWorldManager().preloadDungeonMazeWorlds();
	}

	/**
	 * Prepare the server and Dungeon Maze for a new Dungeon Maze world. This will automatically edit the proper
	 * configuration file as needed.
	 *
	 * @param worldName The name of the world to configure the server for.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean prepareDungeonMazeWorld(String worldName) {
		// Make sure the world manager instance is valid
		if(!isWorldManagerValid())
			return false;

		// Prepare a Dungeon Maze world
		return getWorldManager().prepareDungeonMazeWorld(worldName);
	}

	/**
	 * Check whether a Minecraft world name is valid.
	 *
	 * param worldName The world name to validate.
	 *
	 * @return True if the world name is valid, false otherwise.
	 */
	public static boolean isValidWorldName(String worldName) {
		return WorldManager.isValidWorldName(worldName);
	}
	
	/**
	 * Get the Multiverse Core.
	 *
	 * @return Multiverse Core instance.
	 */
	public MultiverseCore getMultiverseCore() {
		// Get the Core instance and make sure it's valid
		Core core = getCore();
		if(core == null)
			return null;

		// Get the Multiverse Core handler, and make sure it's valid
		MultiverseHandler multiverseHandler = core._getMultiverseHandler();
		if(multiverseHandler == null)
			return null;

		// Get and return the Multiverse Core instance
		return multiverseHandler.getMultiverseCore();
    }
}
