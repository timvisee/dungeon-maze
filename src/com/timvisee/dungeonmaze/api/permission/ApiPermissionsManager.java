package com.timvisee.dungeonmaze.api.permission;

import java.util.List;
import java.util.logging.Logger;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.api.DungeonMazeApi;
import org.bukkit.entity.Player;

import com.timvisee.dungeonmaze.permission.PermissionsManager;
import com.timvisee.dungeonmaze.permission.PermissionsManager.PermissionsSystemType;

public class ApiPermissionsManager {

	/** Defines the Dungeon Maze API instance. */
	private DungeonMazeApi dungeonMazeApi;
	
	/**
	 * Constructor.
	 *
	 * @param dungeonMazeApi Dungeon Maze API instance.
	 */
	public ApiPermissionsManager(DungeonMazeApi dungeonMazeApi) {
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
	 * Get the Dungeon Maze permissions manager.
	 *
	 * @return Dungeon Maze permissions manager.
	 */
	private PermissionsManager getPermissionsManager() {
		// Get the Core instance and make sure it's valid
		Core core = getCore();
		if(core == null)
			return null;

		// Get and return the world manager
		return core._getPermissionsManager();
	}

	/**
	 * Check whether the permissions manager is valid and ready to use.
	 *
	 * @return True if the permissions manager is valid and ready, false otherwise.
	 */
	private boolean isPermissionsManagerValid() {
		return getPermissionsManager() != null;
	}
	
	/**
	 * Return the used permissions system where the permissions manager is currently hooked into.
	 *
	 * @return Used permissions system type.
	 */
	public PermissionsSystemType getUsedPermissionsSystemType() {
		// Make sure the permissions system instance is valid
		if(!isPermissionsManagerValid())
			return null;

		// Get the used permissions system type
		return getPermissionsManager().getUsedPermissionsSystemType();
	}
	
	/**
	 * Check if the permissions manager is currently hooked into any of the supported permissions systems.
	 *
	 * @return False if there isn't any permissions system used.
	 */
	public boolean isEnabled() {
		// Make sure the permissions system instance is valid
		if(!isPermissionsManagerValid())
			return false;

		// Check if the permissions manager is enabled
		return getPermissionsManager().isEnabled();
	}
	
	/**
	 * Set up and hook into the permissions systems.
	 *
	 * @return The detected permissions system.
	 */
	public PermissionsSystemType setUp() {
		// Make sure the permissions system instance is valid
		if(!isPermissionsManagerValid())
			return null;

		// Set up and hook into the permissions system
		return getPermissionsManager().setup();
    }
	
	/**
	 * Unhook the permissions system.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean unhook() {
		// Make sure the permissions system instance is valid
		if(!isPermissionsManagerValid())
			return false;

		// Unhook
		getPermissionsManager().unhook();
		return true;
	}

	/**
	 * Get the logger instance.
	 *
	 * @return Logger instance.
	 */
	public Logger getLogger() {
		// Make sure the permissions system instance is valid
		if(!isPermissionsManagerValid())
			return null;

		// Get and return the logger
		return getPermissionsManager().getLogger();
	}
	
	/**
	 * Set the logger instance
	 *
	 * @param log Logger instance.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean setLogger(Logger log) {
		// Make sure the permissions system instance is valid
		if(!isPermissionsManagerValid())
			return false;

		// Set the logger
		getPermissionsManager().setLogger(log);
		return true;
	}
	
	/**
	 * Check if the player has permission. If no permissions system is used, the player has to be OP.
	 *
	 * @param player The player.
	 * @param permissionsNode The permission node.
	 *
	 * @return True if the player is permitted, false otherwise.
	 */
	public boolean hasPermission(Player player, String permissionsNode) {
		// Make sure the permissions system instance is valid
		if(!isPermissionsManagerValid())
			return false;

		// Check if the player has permission
		return getPermissionsManager().hasPermission(player, permissionsNode);
	}
	
	/**
	 * Check if a player has permission.
	 *
	 * @param player The player.
	 * @param permissionsNode The permissions node.
	 * @param def The default if no permissions system was used.
	 *
	 * @return True if the player is permitted, false otherwise.
	 */
	public boolean hasPermission(Player player, String permissionsNode, boolean def) {
		// Make sure the permissions system instance is valid
		if(!isPermissionsManagerValid())
			return false;

		// Check if the player has permission
		return getPermissionsManager().hasPermission(player, permissionsNode, def);
	}

	/**
	 * Get the permissions groups a player is in.
	 *
	 * @param player The player to get the groups from.
	 *
	 * @return Permissions groups the player is in, empty list if the player isn't in any group.
	 * or if the used permissions system doesn't support permissions groups
	 */
	public List<String> getGroups(Player player) {
		// Make sure the permissions system instance is valid
		if(!isPermissionsManagerValid())
			return null;

		// Get the permission groups
		return getPermissionsManager().getGroups(player);
	}
}
