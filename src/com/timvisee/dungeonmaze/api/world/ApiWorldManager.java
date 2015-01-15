package com.timvisee.dungeonmaze.api.world;

import java.util.List;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.world.WorldManager;

/**
 * API layer for the DMWorldManager class
 */
public class ApiWorldManager {
	
	private DungeonMaze dm;
	
	/**
	 * Constructor
	 * @param dm Dungeon Maze
	 */
	public ApiWorldManager(DungeonMaze dm) {
		this.dm = dm;
	}
	
	/**
	 * Get the Dungeon Maze instance
	 * @return Dungeon Maze instance
	 */
	public DungeonMaze getDungeonMaze() {
		return this.dm;
	}
	
	/**
	 * Set the Dungeon Maze instance
	 * @param dm Dungeon Maze instance
	 */
	public void setDungeonMaze(DungeonMaze dm) {
		this.dm = dm;
	}
	
	/**
	 * Get the DMWorldManager instance
	 * @return DMWorldManager instance
	 */
	private WorldManager getDMWorldManager() {
		return this.dm.getCore()._getWorldManager();
	}
	
	/**
	 * Refresh the list with Dungeon Maze worlds
	 */
	public void refresh() {
		getDMWorldManager().refresh();
	}
	
	/**
	 * Get all DM worlds
	 * @return List of all DM worlds
	 */
	public List<String> getDMWorlds() {
		return getDMWorldManager().getDungeonMazeWorlds();
	}
	
	/**
	 * Get all loaded DM worlds
	 * @return List of all loaded DM worlds
	 */
	public List<String> getLoadedDMWorlds() {
		return getDMWorldManager().getLoadedDungeonMazeWorlds();
	}
	
	/**
	 * Get all preload worlds of DM
	 * @return all preload worlds
	 */
	public List<String> getPreloadWorlds() {
		return getDMWorldManager().getDungeonMazeWorlds(true);
	}
	
	/**
	 * Check if a world is a DM world
	 * @param w the world name
	 * @return true if the world is a DM world
	 */
	public boolean isDMWorld(String w) {
		return getDMWorldManager().isDungeonMazeWorld(w);
	}
	
	/**
	 * Check if a world is a loaded DM world
	 * @param w the world name
	 * @return true if the world is a loaded DM world
	 */
	public boolean isDMWorldLoaded(String w) {
		return getDMWorldManager().isDungeonMazeWorldLoaded(w);
	}
	
	/**
	 * Preload all 'preload' DM worlds
	 */
	public void preloadWorlds() {
		getDMWorldManager().preloadDungeonMazeWorlds();
	}
	
	/**
	 * Get the MultiverseCore instance
	 * @return MultiverseCore instance
	 */
	public MultiverseCore getMultiverseCore() {
		return this.dm.getCore()._getMultiverseHandler().getMultiverseCore();
    }
}
