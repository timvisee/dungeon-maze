package com.timvisee.dungeonmaze.api.permission;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.permission.PermissionsManager;
import com.timvisee.dungeonmaze.permission.PermissionsManager.PermissionsSystemType;

public class ApiPermissionsManager {
	
	private DungeonMaze dm;
	
	/**
	 * Constructor
	 * @param dm 
	 */
	public ApiPermissionsManager(DungeonMaze dm) {
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
	 * Get the DMPermissionsManager instance
	 * @return DMPermissionsManager instance
	 */
	private PermissionsManager getDMPermissionsManager() {
		return this.dm.getCore()._getPermissionsManager();
	}
	
	/**
	 * Return the permissions system where the permissions manager is currently hooked into
	 * @return permissions system type
	 */
	public PermissionsSystemType getUsedPermissionsSystemType() {
		return getDMPermissionsManager().getUsedPermissionsSystemType();
	}
	
	/**
	 * Check if the permissions manager is currently hooked into any of the supported permissions systems
	 * @return false if there isn't any permissions system used
	 */
	public boolean isEnabled() {
		return getDMPermissionsManager().isEnabled();
	}
	
	/**
	 * Set up and hook into the permissions systems
	 * @return the detected permissions system
	 */
	public PermissionsSystemType setUp() {
		return getDMPermissionsManager().setup();
    }
	
	/**
	 * Break the hook with WorldGuard
	 */
	public void unhook() {
		getDMPermissionsManager().unhook();
	}

	/**
	 * Get the logger instance
	 * @return Logger instance
	 */
	public Logger getLogger() {
		return getDMPermissionsManager().getLogger();
	}
	
	/**
	 * Set the logger instance
	 * @param log Logger instance
	 */
	public void setLogger(Logger log) {
		getDMPermissionsManager().setLogger(log);
	}
	
	/**
	 * Check if the player has permission. If no permissions system is used, the player has to be OP
	 * @param p player
	 * @param permsNode permissions node
	 * @return true if the player is permitted
	 */
	public boolean hasPermission(Player p, String permsNode) {
		return getDMPermissionsManager().hasPermission(p, permsNode);
	}
	
	/**
	 * Check if a player has permission
	 * @param player player
	 * @param permissionNode permission node
	 * @param def default if no permissions system is used
	 * @return true if the player is permitted
	 */
	public boolean hasPermission(Player p, String permsNode, boolean def) {
		return getDMPermissionsManager().hasPermission(p, permsNode, def);
	}

	/**
	 * Get the permissions groups a player is in
	 * @param p The player to get the groups from
	 * @return Permissions groups the player is in, empty list if the player isn't in any group
	 * or if the used permissions system doesn't support permissions groups
	 */
	public List<String> getGroups(Player p) {
		return getDMPermissionsManager().getGroups(p);
	}
}
