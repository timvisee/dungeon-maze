package com.timvisee.dungeonmaze.world;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.config.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.timvisee.dungeonmaze.DungeonMaze;

public class WorldManager {

	// TODO: Improve the quality of the code!

	/** Defines whether the world manager is initialized or not. */
	private boolean init = false;

	/** Defines the Dungeon Maze worlds. */
	private List<String> worlds = new ArrayList<String>();
	/** Defines the Dungeon Maze worlds that need to be preloaded. */
	private List<String> preloadWorlds = new ArrayList<String>();

	/**
	 * Constructor. This won't initialize the manager immediately.
	 */
	public WorldManager() {
		this(false);
	}

	/**
	 * Constructor.
	 *
	 * @param init True to initialize the world manager immediately. This will refresh the Dungeon Maze worlds but won't
	 *             preload any world.
	 */
	public WorldManager(boolean init) {
		// Initialize
		if(init)
			init();
	}

	/**
	 * Initialize the world manager. This will automatically refresh the Dungeon Maze worlds on initialization, but
	 * won't preload any world.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean init() {
		return init(true, false);
	}

	/**
	 * Initialize the world manager. Optionally refresh the Dungeon Maze worlds and preload the worlds.
	 *
	 * @param refresh True to refresh the Dungeon Maze worlds, false otherwise.
	 * @param preload True to preload the worlds, false otherwise.
	 *
	 * @return True on success, false on failure. True will also be returned if the world manager was already
	 * initialized.
	 */
	public boolean init(boolean refresh, boolean preload) {
		// Make sure the world manager isn't initialized already
		if(isInit())
			return true;

		// Refresh the Dungeon Maze worlds
		if(refresh)
			if(!refresh())
				return false;

		// Preload the worlds
		if(preload)
			if(!preloadWorlds())
				return false;

		// Set whether the world manager is initialized, return the result
		this.init = true;
		return true;
	}

	/**
	 * Check whether the world manager is initialized.
	 *
	 * @return True if the world manager is initialized, false otherwise.
	 */
	public boolean isInit() {
		return this.init;
	}

	/**
	 * Destroy the world manager.
	 *
	 * @param force True for force destroy the world manager.
	 *
	 * @return True if the world manager was successfully destroyed. True will also be returned if the world manager
	 * wasn't initialized.
	 */
	public boolean destroy(boolean force) {
		// Make sure the world manager is initialized or the destruction must be forced
		if(!isInit() && !force)
			return true;

		// TODO: Peacefully unload the manager and all worlds!?
		this.init = false;
		return true;
	}

	/**
	 * Refresh the list with Dungeon Maze worlds.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean refresh() {
		// Load the list from the config
		List<String> worlds = DungeonMaze.instance.getConfig().getStringList("worlds");

		// Get the multiverse core
		MultiverseCore multiverseCore = Core.getMultiverseHandler().getMultiverseCore();

		if(multiverseCore != null) {
			for(World world : Bukkit.getWorlds()) {
				MultiverseWorld mvWorld = multiverseCore.getMVWorldManager().getMVWorld(world);
				try {
					if((mvWorld.getGenerator().contains("dungeonmaze") || mvWorld.getGenerator().contains("DungeonMaze")) && !worlds.contains(world.getName()))
						worlds.add(world.getName());
				} catch (NoClassDefFoundError ignored) {
				} catch (NullPointerException ignored) {
				}
			}
		}

		// Set the worlds
		this.worlds = worlds;

		// Load the list from the config
		List<String> preloadWorlds = DungeonMaze.instance.getConfig().getStringList("preloadWorlds");
		if(preloadWorlds != null)
			this.preloadWorlds = preloadWorlds;
		
		// Put all the DM worlds into the bukkit.yml file
		if(multiverseCore == null) {
			FileConfiguration bukkitConfig = DungeonMaze.instance.getConfigFromPath(new File("bukkit.yml"));
			if(bukkitConfig != null) {
				//System.out.println("Editing bukkit.yml file...");
				for(String entry : worlds)
					bukkitConfig.set("worlds." + worlds + ".generator", entry);
				
				try {
					bukkitConfig.save(new File("bukkit.yml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				//System.out.println("Editing finished!");
			}
		}

		// Return the result
		return true;
	}
	
	/**
	 * Get all DM worlds.
	 *
	 * @return all DM worlds.
	 */
	public List<String> getDMWorlds() {
		return this.worlds;
	}
	
	/**
	 * Get all loaded DM worlds.
	 *
	 * @return A list of loaded Dungeon Maze world names.
	 */
	public List<String> getLoadedDMWorlds() {
		List<String> loadedWorlds = new ArrayList<String>();
		refresh();
		for(String entry : this.worlds) {
			World w = DungeonMaze.instance.getServer().getWorld(entry);
			if(w != null)
				loadedWorlds.add(entry);
		}
		
		return loadedWorlds;
	}
	
	/**
	 * Get all preload worlds of DM.
	 *
	 * @return all preload worlds.
	 */
	public List<String> getPreloadWorlds() {
		return preloadWorlds;
	}
	
	/**
	 * Check if a world is a DM world.
	 *
	 * @param w the world name.
	 *
	 * @return true if the world is a DM world.
	 */
	public boolean isDMWorld(String w) {
		return getDMWorlds().contains(w);
	}
	
	/**
	 * Check if a world is a loaded DM world.
	 *
	 * @param w the world name.
	 *
	 * @return true if the world is a loaded DM world.
	 */
	public boolean isDMWorldLoaded(String w) {
		return getLoadedDMWorlds().contains(w);
	}
	
	/**
	 * Preload all 'preload' DM worlds.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean preloadWorlds() {
		if(preloadWorlds != null) {
			for(String w : preloadWorlds) {
					WorldCreator newWorld = new WorldCreator(w);
					newWorld.generator(DungeonMaze.instance.getDMWorldGenerator());
					if(Bukkit.getWorld(w) != null)
						newWorld.createWorld();
			}
		}

		// Return the result
		return true;
	}

	/**
	 * Prepare the server and Dungeon Maze for a new Dungeon Maze world. This will automatically edit the proper
	 * configuration file as needed.
	 *
	 * @param worldName The name of the world to configure the server for.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean prepareDMWorld(String worldName) {
		// Edit the bukkit.yml file so bukkit knows what generator to use for the Dungeon Maze worlds, also update the
		// Dungeon Maze files.
		Core.getLogger().info("[DungeonMaze] Preparing bukkit.yml file...");

		// Load the Bukkit configuration file
		FileConfiguration serverConfig = DungeonMaze.instance.getConfigFromPath(new File("bukkit.yml"));

		// Prepare the file
		serverConfig.set("worlds." + worldName + ".generator", DungeonMaze.instance.getName());

		// Save the file
		try {
			serverConfig.save(new File("bukkit.yml"));
		} catch (IOException e) {
			e.printStackTrace();
			Core.getLogger().info("[DungeonMaze] Failed to prepare the bukkit.yml file!");
			return false;
		}

		// Edit the Dungeon Maze configuration file, show a message
		Core.getLogger().info("[DungeonMaze] Preparing the Dungeon Maze configuration file...");

		// Get the configuration handler, and make sure it's available
		ConfigHandler configHandler = Core.getConfigHandler();
		if(configHandler == null) {
			Core.getLogger().info("[DungeonMaze] Failed to prepare the Dungeon Maze configuration file, config handler not available!");
			return false;
		}

		// Get the current list of worlds and preload worlds form the configuration file
		List<String> worlds = configHandler.config.getStringList("worlds");
		List<String> preloadWorlds = configHandler.config.getStringList("preloadWorlds");

		// Add the world if it doesn't exist yet
		if(!worlds.contains(worldName))
			worlds.add(worldName);
		if(!preloadWorlds.contains(worldName))
			preloadWorlds.add(worldName);

		// Update the worlds and preload worlds section again
		Core.getConfigHandler().config.set("worlds", worlds);
		Core.getConfigHandler().config.set("preloadWorlds", preloadWorlds);

		// Save the config
		DungeonMaze.instance.saveConfig();

		// Show a message, return the result
		System.out.println("[DungeonMaze] Successfully prepared!");
		return true;
	}
}
