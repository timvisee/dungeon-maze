package com.timvisee.dungeonmaze.listener;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.world.WorldManager;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonRegionGridManager;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class WorldListener implements Listener {

	/**
	 * Called when a world is being initialized.
	 *
	 * @param event Event reference.
     */
	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		// Make sure the world instance is valid
		World world = event.getWorld();
		if(world == null)
			return;

		// Get the world manager and make sure it's valid
		WorldManager worldManager = Core.getWorldManager();
		if(worldManager == null)
			return;

		// Disable the spawn in memory option for the dungeon maze world, to improve performance while generating the world
		if(worldManager.isDungeonMazeWorld(world.getName())) {
			// Show a status message
			Core.getLogger().debug("Disabling 'keepSpawnInMemory' option for '" + world.getName() + "' to improve performance!");

			// Disable the spawn in memory option
			// TODO: Keep this, or make it configurable?
			world.setKeepSpawnInMemory(false);
		}
	}

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
        DungeonRegionGridManager chunkGridManager = Core.getDungeonRegionGridManager();
        if(chunkGridManager != null)
            // Load the chunk grid if the current world is a Dungeon Maze world
            if(worldManager.isDungeonMazeWorld(world.getName()))
                chunkGridManager.loadRegionGrid(world);
	}

	@EventHandler
	public void onWorldUnload(WorldUnloadEvent event) {
		// Make sure the world instance is valid
		World world = event.getWorld();
		if(world == null)
			return;

		// Get the dungeon chunk grid manager
		DungeonRegionGridManager chunkGridManager = Core.getDungeonRegionGridManager();
		if(chunkGridManager != null)
			// Unload the chunk grid for the specified world
			chunkGridManager.unloadRegionGrid(world);
	}

	@EventHandler
	public void onWorldSave(WorldSaveEvent event) {
		// Make sure the world instance is valid
		World world = event.getWorld();
		if(world == null)
			return;

		// Get the dungeon chunk grid manager
		DungeonRegionGridManager chunkGridManager = Core.getDungeonRegionGridManager();
		if(chunkGridManager != null)
			// Unload the chunk grid for the specified world
			chunkGridManager.saveRegionGrid(world);
	}
}
