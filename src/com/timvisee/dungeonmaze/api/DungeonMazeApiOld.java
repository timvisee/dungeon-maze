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
import com.timvisee.dungeonmaze.world.WorldManager;
import com.timvisee.dungeonmaze.util.ChestUtils;
import com.timvisee.dungeonmaze.util.MazeUtils;

@SuppressWarnings("UnusedDeclaration")
@Deprecated
public class DungeonMazeApiOld {

	/** Dungeon Maze plugin instance. */
	public static DungeonMaze dungeonMaze;
	
	/**
	 * Constructor.
	 *
	 * @param dungeonMaze The Dungeon Maze plugin instance.
	 */
	public DungeonMazeApiOld(DungeonMaze dungeonMaze) {
		//noinspection deprecation
		DungeonMazeApiOld.dungeonMaze = dungeonMaze;
	}
	
	/**
	 * Hook into the Dungeon Maze plugin.
	 *
	 * @return Dungeon Maze plugin instance.
	 */
	@Deprecated
    public static DungeonMaze hookDungeonMaze() {
		// Get the dungeon maze plugin instance, and make sure it's valid
    	Plugin dungeonMazePlugin = Bukkit.getServer().getPluginManager().getPlugin("DungeonMaze");
        if (dungeonMazePlugin == null)
        	return null;

		// Get and return the Dungeon Maze plugin instance
    	return (DungeonMaze) dungeonMazePlugin;
    }

	/**
	 * Get the Dungeon Maze world manager.
	 *
	 * @return Dungeon Maze world manager.
	 */
	@Deprecated
	public static WorldManager getDMWorldManager() {
		return dungeonMaze.getCore()._getWorldManager();
	}

	/**
	 * Get all Dungeon Maze worlds.
	 *
	 * @return Dungeon Maze worlds.
	 */
	@Deprecated
	public static List<String> getDMWorlds() {
		return dungeonMaze.getCore()._getWorldManager().getDungeonMazeWorlds();
	}

	/**
	 * Get all loaded Dungeon Maze worlds.
	 *
	 * @return Loaded Dungeon Maze worlds.
	 */
	@Deprecated
	public static List<String> getLoadedDMWorlds() {
		return dungeonMaze.getCore()._getWorldManager().getLoadedDungeonMazeWorlds();
	}

	/**
	 * Check if a world is a Dungeon Maze world.
	 *
	 * @param world The world to check for.
	 *
	 * @return True if the world is a Dungeon Maze world, false otherwise.
	 */
	@Deprecated
	public static boolean isDMWorld(World world) {
		//noinspection deprecation
		return isDMWorld(world.getName());
	}

	/**
	 * Check if a world is a Dungeon Maze world.
	 *
	 * @param worldName The world name.
	 *
	 * @return True if the world is a Dungeon Maze world, false otherwise.
	 */
	@Deprecated
	public static boolean isDMWorld(String worldName) {
		return dungeonMaze.getCore()._getWorldManager().isDungeonMazeWorld(worldName);
	}

	/**
	 * Check if a player is in a Dungeon Maze world.
	 *
	 * @param player The player.
	 *
	 * @return True if the player is in an Dungeon Maze world.
	 */
	@Deprecated
	public static boolean isInDMWorld(Player player) {
		//noinspection deprecation
		return isDMWorld(player.getWorld());
	}
	
	/**
	 * Get the Dungeon Maze world a player is in.
	 *
	 * @param player The player.
	 *
	 * @return The Dungeon Maze world a player is in, returns null when a player isn't in a Dungeon Maze world.
	 */
	@Deprecated
	public static World getDMWorld(Player player) {
		// Check if the player is in a Dungeon Maze world
		if(dungeonMaze.getCore()._getWorldManager().isDungeonMazeWorld(player.getWorld().getName()))
			return player.getWorld();
		return null;
	}
	
	/**
	 * Get the Dungeon Maze world name a player is in.
	 *
	 * @param player The player.
	 *
	 * @return The Dungeon Maze world name a player is in, returns an empty string when the player isn't in a Dungeon
	 * Maze world.
	 */
	@Deprecated
	public static String getDMWorldName(Player player) {
		// Check if the player is in a Dungeon Maze world
		if(dungeonMaze.getCore()._getWorldManager().isDungeonMazeWorld(player.getWorld().getName()))
			return player.getWorld().getName();
		return "";
	}
	
	/**
	 * Check if a player is able to build in a Dungeon Maze world.
	 *
	 * @param worldName The name of the world.
	 * @param player The player.
	 *
	 * @return True if the player is allowed to build in the specified world.
	 */
	@Deprecated
	public static boolean canBuildInDMWorld(String worldName, Player player) {
		if(dungeonMaze.getCore()._getWorldManager().isDungeonMazeWorld(worldName))
			if(DungeonMaze.instance.getCore()._getConfigHandler().worldProtection)
				return dungeonMaze.getCore()._getPermissionsManager().hasPermission(player, "dungeonmaze.bypass.build", player.isOp());
		return true;
	}
	
	/**
	 * Check if a player is able to go onto the surface in a Dungeon Maze world.
	 *
	 * @param worldName The name of the world.
	 * @param player The player.
	 *
	 * @return True if the player is allowed to go on the surface.
	 */
	@Deprecated
	public static boolean isPlayerAllowedOnDMWorldSurface(String worldName, Player player) {
		if(dungeonMaze.getCore()._getWorldManager().isDungeonMazeWorld(worldName))
			if(!DungeonMaze.instance.getCore()._getConfigHandler().allowSurface)
				return dungeonMaze.getCore()._getPermissionsManager().hasPermission(player, "dungeonmaze.bypass.surface", player.isOp());
		return true;
	}
	
	/**
	 * Check whether a block is on the build whitelist.
	 *
	 * @param target The block to check for.
	 *
	 * @return True if the object is in the list, false otherwise.
	 */
	@Deprecated
	public static boolean isInWhiteList(Object target){
		List<Object> list = DungeonMaze.instance.getCore()._getConfigHandler().blockWhiteList;
		
		if(list == null)
			return(false);

		for(Object aList : list)
			if(aList.equals(target))
				return true;
		return false;
	}
	
	/**
	 * Check whether a specific mob spawner is allowed.
	 *
	 * @param mob Mob name.
	 *
	 * @return True if the mob spawner is allow for this mob.
	 */
	@Deprecated
	public static boolean allowMobSpawner(String mob) {
		return DungeonMaze.instance.getCore()._getConfigHandler().mobs.contains(mob);
	}
	
	/**
	 * Get the level a block is on in Dungeon Maze.
	 *
	 * @param block The block.
	 *
	 * @return The level as a Dungeon Maze level, returns levels 1-7. Returns 0 when the block isn't on a Dungeon Maze
	 * level.
	 */
	@Deprecated
	public static int getDMLevel(Block block) {
		return MazeUtils.getDMLevel(block);
	}
	
	/**
	 * Add items to Dungeon Maze chest.
	 *
	 * @param inOrder False to add the items in random order.
	 * @param random Random object to use as seed when items are added in random order.
	 * @param chest Chest to add the items too.
	 * @param newContents List of new contents to add.
	 *
	 * @return False if failed.
	 *
	 * @deprecated This method is deprecated, please use the new Dungeon Maze API.
	 */
	@Deprecated
	public static boolean addItemsToChest(boolean inOrder, Random random, Chest chest, List<ItemStack> newContents) {
		return ChestUtils.addItemsToChest(chest, newContents, !inOrder, random);
	}
}