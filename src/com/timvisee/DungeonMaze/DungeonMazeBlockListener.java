package com.timvisee.DungeonMaze;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class DungeonMazeBlockListener implements Listener {
	public static Logger log = Logger.getLogger("Minecraft");
	public static DungeonMaze plugin;

	public DungeonMazeBlockListener(DungeonMaze instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlockPlaced();
		String w = b.getWorld().getName();
		
		plugin.getDMWorldManager();
		if(DMWorldManager.isDMWorld(w)) {
			// The world is a Dungeon Maze world
			
			if(plugin.getConfig().getBoolean("worldProtection", false)) {
				// The player is not allowed on the surface
				
				if(!plugin.hasPermission(p, "dungeonmaze.bypass.build", p.isOp())) {
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
		
		plugin.getDMWorldManager();
		if(DMWorldManager.isDMWorld(w)) {
			// The world is a Dungeon Maze world
			
			if(plugin.getConfig().getBoolean("worldProtection", false)) {
				// The player is not allowed on the surface
				
				if(plugin.hasPermission(p, "dungeonmaze.bypass.build", p.isOp()) == false) {
					// The player doesn't have the bypass permission
					e.setCancelled(true);
					p.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
				}
			}
		}
	}
}