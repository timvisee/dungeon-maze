package com.timvisee.dungeonmaze.listener;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.update.Updater;
import com.timvisee.dungeonmaze.update.Updater.UpdateResult;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		String w = loc.getWorld().getName();
		double y = loc.getY();
					
		if(y >= 75) {
			// The player is above the surface
			
			Core.getWorldManager();
			if(Core.getWorldManager().isDMWorld(w)) {
				if(Core.getConfigHandler().allowSurface == false) {
					// The player is not allowed on the surface
					
					if(Core.getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.surface", p.isOp()) == false) {
						// The player doesn't have the bypass permission
						double x = loc.getX();
						double z = loc.getZ();
						
						// Check if there's a new available player location
						for(int newY = 74; newY > 1; newY--) {
							if(loc.getWorld().getBlockAt((int) x, (int) newY, (int) z).getType() != Material.AIR) {
								// This block is a non-air block
								// Check if the two above blocks are air, so the player could be teleported to this place
								if(loc.getWorld().getBlockAt((int) x, (int) newY + 1, (int) z).getType() == Material.AIR &&
										loc.getWorld().getBlockAt((int) x, (int) newY + 2, (int) z).getType() == Material.AIR) {
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
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		
		// Make sure the player has permission to see update notifications
		if(Core.getPermissionsManager().hasPermission(p, "dungeonmaze.notification.update", p.isOp()) &&
				DungeonMaze.instance.getConfig().getBoolean("updateChecker.enabled", true) &&
				DungeonMaze.instance.getConfig().getBoolean("updateChecker.notifyInGame", true)) {
			
			// Get the update checker and refresh the updates data
			Updater uc = Core.getUpdateChecker();
			
			if (uc == null) return;
			
			if(uc.getResult() != UpdateResult.SUCCESS && uc.getResult() == UpdateResult.UPDATE_AVAILABLE && uc.getResult() != UpdateResult.FAIL_NOVERSION) {
				p.sendMessage(ChatColor.GREEN + "No new DungeonMaze version found!");
			} else {
				
				String newVer = uc.getLatestName();
				
				// Make sure the new version is compatible with the current bukkit version
				if(uc.getResult() == UpdateResult.FAIL_NOVERSION) {
					p.sendMessage(ChatColor.GREEN + "New Dungeon Maze version available: v" + String.valueOf(newVer));
					p.sendMessage(ChatColor.GREEN + "The new version is not compatible with your Bukkit version!");
					p.sendMessage(ChatColor.GREEN + "Please update your Bukkkit to " +  uc.getLatestGameVersion() + " or higher!");
				} else {
					if(uc.getResult() == UpdateResult.SUCCESS)
						p.sendMessage(ChatColor.GREEN + "New DungeonMaze version installed (v" + String.valueOf(newVer) + "). Server reboot required!");
					else {
						p.sendMessage(ChatColor.GREEN + "New DungeonMaze version found: " + String.valueOf(newVer));
						p.sendMessage(ChatColor.GREEN + "Use " + ChatColor.GOLD + "/dm installupdate" +
								ChatColor.GREEN + " to automaticly install the new version!");
					}
				}
			}
		}
	}
}