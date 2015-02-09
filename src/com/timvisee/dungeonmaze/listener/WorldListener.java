package com.timvisee.dungeonmaze.listener;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonChunkGridManager;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import java.io.File;
import java.util.Random;

public class WorldListener implements Listener {

	/**
	 * Called when a world is loaded.
	 *
	 * @param event Event reference.
	 */
	@EventHandler
	public void onWorldLoad(WorldLoadEvent event) {
		// Refresh the list with DM worlds
		Core.getWorldManager().refresh();

		// TODO: Load the dungeon chunk grid data.
	}

	@EventHandler
	public void onWorldUnload(WorldUnloadEvent event) {
		// Make sure the world instance is valid
		World world = event.getWorld();
		if(world == null)
			return;

		// Get the dungeon chunk grid manager
		DungeonChunkGridManager dungeonChunkGridManager = Core.getDungeonChunkGridManager();
		if(dungeonChunkGridManager != null) {
			dungeonChunkGridManager.unloadChunkGrid(event.getWorld());
		}
	}
}
