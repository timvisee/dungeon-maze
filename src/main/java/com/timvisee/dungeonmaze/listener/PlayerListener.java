package com.timvisee.dungeonmaze.listener;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.config.ConfigHandler;
import com.timvisee.dungeonmaze.permission.PermissionsManager;
import com.timvisee.dungeonmaze.update.Updater;
import com.timvisee.dungeonmaze.update.Updater.UpdateResult;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

	/**
	 * Called when a player moves.
	 *
	 * @param event The event reference.
	 */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		// Get the player, location, world name and position
		Player player = event.getPlayer();
		Location location = player.getLocation();
		double locationY = location.getY();
		String worldName = location.getWorld().getName();

		// Make sure the player is above the surface
		if(locationY < 75)
			return;

		// Get the world manager, config handler and permissions manager, and make sure it's valid
		WorldManager worldManager = Core.getWorldManager();
		ConfigHandler configHandler = Core.getConfigHandler();
		PermissionsManager permissionsManager = Core.getPermissionsManager();
		if(worldManager == null || configHandler == null || permissionsManager == null)
			return;

		// Make sure the player is in a Dungeon Maze world
		if(!worldManager.isDungeonMazeWorld(worldName))
			return;

		// Check whether the player is allowed on the surface
		if(configHandler.allowSurface)
			return;

		// Check whether the player has permission to go on the Dungeon Maze surface
		if(permissionsManager.hasPermission(player, "dungeonmaze.bypass.surface", player.isOp()))
			return;

		// Get the x and z location of the player
		double locationX = location.getX();
		double locationZ = location.getZ();

		// Check if there's a new available player location
		for(int newY = 74; newY > 1; newY--) {
            if(location.getWorld().getBlockAt((int) locationX, newY, (int) locationZ).getType() != Material.AIR) {
                // This block is a non-air block
                // Check if the two above blocks are air, so the player could be teleported to this place
                if(location.getWorld().getBlockAt((int) locationX, newY + 1, (int) locationZ).getType() == Material.AIR &&
                        location.getWorld().getBlockAt((int) locationX, newY + 2, (int) locationZ).getType() == Material.AIR) {
                    player.sendMessage(ChatColor.DARK_RED + "You're not allowed on the surface!");
                    Location newPLoc = new Location(location.getWorld(), locationX, newY + 1, locationZ);
                    player.teleport(newPLoc);
                    break;
                }
            }
        }
	}

	/**
	 * Called when a player joins the server.
	 *
	 * @param event The event reference.
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		// Get the player
		Player player = event.getPlayer();

		// Get the Dungeon Maze config
		ConfigHandler configHandler = DungeonMaze.instance.getCore()._getConfigHandler();
		FileConfiguration config = configHandler.config;

		// Check whether the update checker is enabled and whether the user should be notified in-game
		boolean updateCheckerEnabled = true;
		boolean updateCheckerNotifyInGame = true;
		if(config != null) {
			updateCheckerEnabled = config.getBoolean("updateChecker.enabled", true);
			updateCheckerNotifyInGame = config.getBoolean("updateChecker.notifyInGame", true);
		}

		// Check whether the player should get update notifications in-game
		if(!Core.getPermissionsManager().hasPermission(player, "dungeonmaze.notification.update", player.isOp()) ||
				!updateCheckerEnabled || !updateCheckerNotifyInGame)
			return;

		// Get the update checker and refresh the updates data, and make sure the updater is valid
		// TODO: Force update!
		Updater uc = Core.getUpdateChecker();
		if(uc == null)
			return;

		// No new version found
		if(uc.getResult() == UpdateResult.NO_UPDATE) {
            player.sendMessage(ChatColor.GREEN + "Dungeon Maze is up to date!");
			return;
        }

		// Get the version number of the new version
		String newVer = uc.getLatestName();

		// Make sure the new version is compatible with the current bukkit version
		if(uc.getResult() == UpdateResult.FAIL_NOVERSION) {
			player.sendMessage(ChatColor.GREEN + "New Dungeon Maze version available: v" + String.valueOf(newVer));
			player.sendMessage(ChatColor.GREEN + "The new version is not compatible with your Bukkit version!");
			player.sendMessage(ChatColor.GREEN + "Please update your Bukkit to " +  uc.getLatestGameVersion() + " or higher!");
		} else {
			if(uc.getResult() == UpdateResult.SUCCESS)
				player.sendMessage(ChatColor.GREEN + "New DungeonMaze version installed (v" + String.valueOf(newVer) + "). Server reboot required!");

			else {
				player.sendMessage(ChatColor.GREEN + "New DungeonMaze version found: " + String.valueOf(newVer));
				player.sendMessage(ChatColor.GREEN + "Use " + ChatColor.GOLD + "/dm installupdate" +
						ChatColor.GREEN + " to automatically install the new version!");
			}
		}
	}
}