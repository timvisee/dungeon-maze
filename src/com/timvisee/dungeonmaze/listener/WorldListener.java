package com.timvisee.dungeonmaze.listener;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.world.WorldManager;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonChunkGridManager;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class WorldListener implements Listener {

	/**
	 * Called when a world is loaded.
	 *
	 * @param event Event reference.
	 */
	@EventHandler
	public void onWorldLoad(WorldLoadEvent event) {
        // Get the world
        World world = event.getWorld();

        // Get the world manager and make sure it's valid
        WorldManager worldManager = Core.getWorldManager();
        if(worldManager == null)
            return;

		// Refresh the list with DM worlds
        worldManager.refresh();

		// Get the chunk grid manager
        DungeonChunkGridManager chunkGridManager = Core.getDungeonChunkGridManager();
        if(chunkGridManager != null)
            // Load the chunk grid if the current world is a Dungeon Maze world
            if(worldManager.isDungeonMazeWorld(world.getName()))
                chunkGridManager.loadChunkGrid(world);
	}

	@EventHandler
	public void onWorldUnload(WorldUnloadEvent event) {
		// Make sure the world instance is valid
		World world = event.getWorld();
		if(world == null)
			return;

		// Get the dungeon chunk grid manager
		DungeonChunkGridManager chunkGridManager = Core.getDungeonChunkGridManager();
		if(chunkGridManager != null)
            // Unload the chunk grid for the specified world
			chunkGridManager.unloadChunkGrid(event.getWorld());
	}
}
