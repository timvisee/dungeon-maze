package com.timvisee.dungeonmaze.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.timvisee.dungeonmaze.DMUpdateChecker;
import com.timvisee.dungeonmaze.DungeonMaze;

public class DMPlayerListener implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		String w = loc.getWorld().getName();
		double y = loc.getY();
					
		if(y >= 75) {
			// The player is above the surface
			
			DungeonMaze.instance.getWorldManager();
			if(DungeonMaze.instance.getWorldManager().isDMWorld(w)) {
				if(DungeonMaze.instance.getConfigHandler().allowSurface == false) {
					// The player is not allowed on the surface
					
					if(DungeonMaze.instance.getPermissionsManager().hasPermission(p, "dungeonmaze.bypass.surface", p.isOp()) == false) {
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
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		
		// Make sure the player has permission to see update notifications
		if(DungeonMaze.instance.getPermissionsManager().hasPermission(p, "dungeonmaze.notification.update", p.isOp()) &&
				DungeonMaze.instance.getConfig().getBoolean("updateChecker.enabled", true) &&
				DungeonMaze.instance.getConfig().getBoolean("updateChecker.notifyForUpdatesInGame", true)) {
			
			DMUpdateChecker uc = DungeonMaze.instance.getUpdateChecker();
			
			// Check if any update exists
			if(uc.isNewVersionAvailable()) {
				final String newVer = uc.getNewestVersion();
				
				// Is the update important
				if(uc.isImportantUpdateAvailable()) {
					if(!uc.isNewVersionCompatibleWithCurrentBukkit()) {
						p.sendMessage(ChatColor.YELLOW + "[DungeonMaze] New important Dungeon Maze update available! (v" + newVer + ")");
						p.sendMessage(ChatColor.YELLOW + "[DungeonMaze] Version not compatible, please update to Bukkit " + uc.getRequiredBukkitVersion() + " or higher!");
					} else {
						if(uc.isUpdateDownloaded()) {
							p.sendMessage(ChatColor.YELLOW + "[DungeonMaze] New important Dungeon Maze update installed! (v" + newVer + ")");
							p.sendMessage(ChatColor.YELLOW + "[DungeonMaze] Server reload required!");
						} else {
							p.sendMessage(ChatColor.YELLOW + "[DungeonMaze] New important Dungeon Maze update available! (v" + newVer + ")");
							p.sendMessage(ChatColor.YELLOW + "[DungeonMaze] Use " + ChatColor.GOLD + "/dm installupdate" + ChatColor.YELLOW + " to install the update!");
						}
					}
				} else {
					if(uc.isNewVersionCompatibleWithCurrentBukkit()) {
						if(uc.isUpdateDownloaded()) {
							p.sendMessage(ChatColor.YELLOW + "[DungeonMaze] New Dungeon Maze update installed! (v" + newVer + ")");
							p.sendMessage(ChatColor.YELLOW + "[DungeonMaze] Server reload required!");
						} else {
							p.sendMessage(ChatColor.YELLOW + "[DungeonMaze] New important Dungeon Maze update available! (v" + newVer + ")");
							p.sendMessage(ChatColor.YELLOW + "[DungeonMaze] Use " + ChatColor.GOLD + "/dm installupdate" + ChatColor.YELLOW + " to install the update!");
						}
					}
				}
			}
		}
	}
}