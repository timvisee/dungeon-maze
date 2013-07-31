package com.timvisee.dungeonmaze.listener;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.manager.DMWorldManager;

public class DMBlockListener implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlockPlaced();
		String w = b.getWorld().getName();
		
		if(DMWorldManager.isDMWorld(w)) {
			// The world is a Dungeon Maze world
			
			if(DungeonMaze.instance.getConfigHandler().worldProtection) {
				// The world protection is enable
				
				if((!DungeonMaze.instance.getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.build", p.isOp())) && !(DungeonMaze.instance.getConfigHandler().isInWhiteList(b.getTypeId()))) {
					// The player doesn't have the bypass permission
					e.setCancelled(true);
					p.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		String w = b.getWorld().getName();
		
		if(DMWorldManager.isDMWorld(w)) {
			// The world is a Dungeon Maze world
			
			if(DungeonMaze.instance.getConfigHandler().worldProtection) {
				// The world protection is enable
				
				if((!DungeonMaze.instance.getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.build", p.isOp())) && !(DungeonMaze.instance.getConfigHandler().isInWhiteList(b.getTypeId()))) {
					// The player doesn't have the bypass permission
					e.setCancelled(true);
					p.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
				}
			}
		}
	}
}