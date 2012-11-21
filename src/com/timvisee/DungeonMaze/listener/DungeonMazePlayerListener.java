package com.timvisee.DungeonMaze.listener;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.timvisee.DungeonMaze.DungeonMaze;
import com.timvisee.DungeonMaze.manager.DMWorldManager;

public class DungeonMazePlayerListener implements Listener {
	public static Logger log = Logger.getLogger("Minecraft");
	public static DungeonMaze plugin;

	public DungeonMazePlayerListener(DungeonMaze instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		String w = loc.getWorld().getName();
		double y = loc.getY();
					
		if(y >= 75) {
			// The player is above the surface
			
			plugin.getDMWorldManager();
			if(DMWorldManager.isDMWorld(w)) {
				if(DungeonMaze.allowSurface == false) {
					// The player is not allowed on the surface
					
					if(plugin.getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.surface", p.isOp()) == false) {
						// The player doesn't have the bypass permission
						double x = loc.getX();
						double z = loc.getZ();
						
						// Check if there's a new available player location
						for(int newY = 74; newY > 1; newY--) {
							if(loc.getWorld().getBlockAt((int) x, (int) newY, (int) z).getTypeId() != 0) {
								// This block is a non-air block
								// Check if the two above blocks are air, so the player could be teleported to this place
								if(loc.getWorld().getBlockAt((int) x, (int) newY + 1, (int) z).getTypeId() == 0 &&
										loc.getWorld().getBlockAt((int) x, (int) newY + 2, (int) z).getTypeId() == 0) {
									p.sendMessage(ChatColor.DARK_RED + "You're not allowed on the surface!");
									Location newPLoc = new Location(loc.getWorld(), x, newY + 1, z);
									p.teleport(newPLoc);
									break;
								}
							}
						}
					}
				}
			}
		}
	}
}