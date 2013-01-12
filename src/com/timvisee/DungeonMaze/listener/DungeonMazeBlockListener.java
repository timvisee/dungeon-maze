package com.timvisee.DungeonMaze.listener;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.timvisee.DungeonMaze.DungeonMaze;
import com.timvisee.DungeonMaze.API.DungeonMazeAPI;

public class DungeonMazeBlockListener implements Listener {
	public static Logger log = Logger.getLogger("Minecraft");
	public DungeonMaze plugin;

	public DungeonMazeBlockListener(DungeonMaze instance) {
		this.plugin = instance;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlockPlaced();
		String w = b.getWorld().getName();
		
		if(DungeonMazeAPI.isDMWorld(w)) {
			// The world is a Dungeon Maze world
			
			if(plugin.worldProtection) {
				// The world protection is enable
				
				if((!plugin.getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.build", p.isOp())) && !(DungeonMazeAPI.isInWhiteList(b.getTypeId()))) {
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
		
		if(DungeonMazeAPI.isDMWorld(w)) {
			// The world is a Dungeon Maze world
			
			if(plugin.worldProtection) {
				// The world protection is enable
				
				if((!plugin.getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.build", p.isOp())) && !(DungeonMazeAPI.isInWhiteList(b.getTypeId()))) {
					// The player doesn't have the bypass permission
					e.setCancelled(true);
					p.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
				}
			}
		}
	}
}