package com.timvisee.dungeonmaze.listener;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

	/**
	 * Called when a block is placed.
	 *
	 * @param event The event reference.
	 */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		// Get the player, block and world name
		Player player = event.getPlayer();
		Block block = event.getBlockPlaced();
		String worldName = block.getWorld().getName();

		// Make sure the world is a Dungeon Maze world
		if(!Core.getWorldManager().isDungeonMazeWorld(worldName))
			return;

		// Make sure world protection is enabled
		if(!Core.getConfigHandler().worldProtection)
            return;

		// Make sure the player has permission to build (possibly with the bypass permission)
		if((!Core.getPermissionsManager().hasPermission(player, "dungeonmaze.bypass.build", player.isOp())) && !(Core.getConfigHandler().isInWhiteList(block.getType()))) {
			// The player doesn't have the bypass permission
			event.setCancelled(true);
			player.sendMessage(ChatColor.DARK_RED + "You don't have permission to build here!");
		}
	}

	/**
	 * Called when a block is broken.
	 *
	 * @param event The event reference.
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		// Get the player, block and world name
		Player player = event.getPlayer();
		Block block = event.getBlock();
		String worldName = block.getWorld().getName();

		// Make sure the world is a Dungeon Maze world
		if(!Core.getWorldManager().isDungeonMazeWorld(worldName))
			return;

		// Make sure world protection is enabled
		if(!Core.getConfigHandler().worldProtection)
			return;

		// Make sure the player has permission to build (possibly with the bypass permission)
		if((!Core.getPermissionsManager().hasPermission(player, "dungeonmaze.bypass.build", player.isOp())) && !(Core.getConfigHandler().isInWhiteList(block.getType()))) {
			// The player doesn't have the bypass permission
			event.setCancelled(true);
			player.sendMessage(ChatColor.DARK_RED + "You don't have permission to break anything!");
		}
	}
}