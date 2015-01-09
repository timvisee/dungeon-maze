package com.timvisee.dungeonmaze.api;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.manager.WorldManager;
import com.timvisee.dungeonmaze.util.DMChestUtils;
import com.timvisee.dungeonmaze.util.DMMazeUtils;

@Deprecated
public class DungeonMazeApiOld {
	
	public static DungeonMaze plugin;
	
	/**
	 * Constructor
	 * @param instance
	 */
	public DungeonMazeApiOld(DungeonMaze instance) {
		DungeonMazeApiOld.plugin = instance;
	}
	
	/**
	 * Hook into Dungeon Maze
	 * @return instance DM instance
	 */
	@Deprecated
    public static DungeonMaze hookDungeonMaze() {
    	Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("DungeonMaze");
        if (plugin == null && !(plugin instanceof DungeonMaze))
        	return null;
    	return (DungeonMaze) plugin;
    }

	/**
	 * Get the DM world manager
	 * @return DM world manager
	 */
	@Deprecated
	public static WorldManager getDMWorldManager() {
		return plugin.getCore()._getWorldManager();
	}

	/**
	 * Get all DM worlds
	 * @return DM worlds
	 */
	@Deprecated
	public static List<String> getDMWorlds() {
		return plugin.getCore()._getWorldManager().getDMWorlds();
	}

	/**
	 * Get all loaded DM worlds
	 * @return loaded DM worlds
	 */
	@Deprecated
	public static List<String> getLoadedDMWorlds() {
		return plugin.getCore()._getWorldManager().getLoadedDMWorlds();
	}

	/**
	 * Check if a world is a DM world
	 * @param w the world
	 * @return true if the world is a DM world
	 */
	@Deprecated
	public static boolean isDMWorld(World w) {
		return isDMWorld(w.getName());
	}

	/**
	 * Check if a world is a DM world
	 * @param w the world name
	 * @return true if the world is a DM world
	 */
	@Deprecated
	public static boolean isDMWorld(String w) {
		return plugin.getCore()._getWorldManager().isDMWorld(w);
	}

	/**
	 * Check if a player is in a DM world
	 * @param p the player
	 * @return true if the player is in an DM world
	 */
	@Deprecated
	public static boolean isInDMWorld(Player p) {
		return isDMWorld(p.getWorld());
	}
	
	/**
	 * Get the DM world a player is in
	 * @param p the player
	 * @return the DM world a player is in, returns null when a player isn't in a DM world
	 */
	@Deprecated
	public static World getDMWorld(Player p) {
		// Check if the player is in a DM world
		if(plugin.getCore()._getWorldManager().isDMWorld(p.getWorld().getName()))
			return p.getWorld();
		return null;
	}
	
	/**
	 * Get the DM world name a player is in
	 * @param p the player
	 * @return the DM world name a player is in, returns an empty string when the player isn't in a DM world
	 */
	@Deprecated
	public static String getDMWorldName(Player p) {
		// Check if the player is in a DM world
		if(plugin.getCore()._getWorldManager().isDMWorld(p.getWorld().getName()))
			return p.getWorld().getName();
		return "";
	}
	
	/**
	 * Check if a player is able to build in a DM world
	 * @param w the world name
	 * @param p the player
	 * @return true if the player is allowed to build
	 */
	@Deprecated
	public static boolean canBuildInDMWorld(String w, Player p) {
		if(plugin.getCore()._getWorldManager().isDMWorld(w))
			if(DungeonMaze.instance.getCore()._getConfigHandler().worldProtection)
				return plugin.getCore()._getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.build", p.isOp());
		return true;
	}
	
	/**
	 * Check if a player is able to go onto the surface in a DM world
	 * @param w the world name
	 * @param p the player
	 * @return true if the player is allowed to go on the surface
	 */
	@Deprecated
	public static boolean isPlayerAllowedOnDMWorldSurface(String w, Player p) {
		if(plugin.getCore()._getWorldManager().isDMWorld(w))
			if(!DungeonMaze.instance.getCore()._getConfigHandler().allowSurface)
				return plugin.getCore()._getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.surface", p.isOp());
		return true;
	}
	
	/**
	 * 
	 * @param target
	 * @return true if the object is in the list
	 */
	@Deprecated
	public static boolean isInWhiteList(Object target){
		List<Object> list = DungeonMaze.instance.getCore()._getConfigHandler().blockWhiteList;
		
		if(list == null)
			return(false);
		
		for(int x = 0; x < list.size(); ++x)
			if(list.get(x).equals(target))
				return true;
		return false;
	}
	
	/**
	 * @param mob Mob name
	 * @return true if the mobspawner is allow for this mob
	 */
	@Deprecated
	public static boolean allowMobSpawner(String mob) {
		return DungeonMaze.instance.getCore()._getConfigHandler().mobs.contains(mob);
	}
	
	/**
	 * Get the level a block is on in Dungeon Maze
	 * @param b the block
	 * @return The level as a DungeonMaze level, returns levels 1-7. Returns 0 when the block isn't on a DungeonMaze level
	 */
	@Deprecated
	public static int getDMLevel(Block b) {
		return DMMazeUtils.getDMLevel(b);
	}
	
	/**
	 * DEPRICATED:
	 * Add items to DungeonMaze chest
	 * @param inOrder False to add the items in random order
	 * @param rand Random object to use as seed when items are added in random order
	 * @param c Chest to add the items too
	 * @param newContents List of new contents to add
	 * @return False if failed
	 */
	@Deprecated
	public static boolean addItemsToChest(boolean inOrder, Random rand, Chest c, List<ItemStack> newContents) {
		return DMChestUtils.addItemsToChest(c, newContents, !inOrder, rand);
	}
}