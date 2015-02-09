package com.timvisee.dungeonmaze.world;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.config.ConfigHandler;
import com.timvisee.dungeonmaze.config.ConfigUtils;
import com.timvisee.dungeonmaze.util.Profiler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.timvisee.dungeonmaze.DungeonMaze;

@SuppressWarnings("UnusedDeclaration")
public class WorldManager {

	/** Minecraft world name validation Regex. */
	private static final String MINECRAFT_WORLD_NAME_REGEX = "^[[\\p{Alnum}]_-]+";

	/** Defines whether the world manager is initialized or not. */
	private boolean init = false;

	/** Defines the Dungeon Maze worlds. */
	private List<String> dungeonMazeWorlds = new ArrayList<String>();
	/** Defines the Dungeon Maze worlds that need to be preloaded. */
	private List<String> dungeonMazeWorldsPreload = new ArrayList<String>();

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
			if(preloadDungeonMazeWorlds() < 0)
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

		// TODO: Properly unload the manager and all worlds!?
		this.init = false;
		return true;
	}

	/**
	 * Refresh the list with Dungeon Maze worlds.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean refresh() {
		// Get the Dungeon Maze config
		ConfigHandler configHandler = DungeonMaze.instance.getCore()._getConfigHandler();
		FileConfiguration config = configHandler.config;

		// Make sure the config file was loaded successfully
		if(config == null)
			return false;

		// Load the list from the config
		List<String> worlds = config.getStringList("worlds");

		// Get the multiverse core
		MultiverseCore multiverseCore = Core.getMultiverseHandler().getMultiverseCore();

		// Check whether the Multiverse core instance is valid
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
		this.dungeonMazeWorlds = worlds;

		// Load the list from the config
		List<String> preloadWorlds = config.getStringList("preloadWorlds");
		if(preloadWorlds != null)
			this.dungeonMazeWorldsPreload = preloadWorlds;
		
		// Put all the DM worlds into the bukkit.yml file
		//if(multiverseCore == null)
			setBukkitConfigWorldGenerator(worlds);

		// Return the result
		return true;
	}

	/**
	 * Get all Dungeon Maze worlds.
	 *
	 * @return All Dungeon Maze worlds.
	 */
	public List<String> getDungeonMazeWorlds() {
		return getDungeonMazeWorlds(false);
	}

	/**
	 * Get all Dungeon Maze worlds, or just the worlds that should be preloaded.
	 *
	 * @param preloadWorlds True to only get the Dungeon Maze worlds that should be preloaded on start, false to get all
	 *                      Dungeon Maze worlds.
	 *
	 * @return All Dungeon Maze worlds, or just the worlds that should be preloaded depending on the preloadWorlds
	 * parameter.
	 */
	public List<String> getDungeonMazeWorlds(boolean preloadWorlds) {
		// Just return the worlds that should be preloaded
		if(preloadWorlds)
			return this.dungeonMazeWorldsPreload;

		// Return all Dungeon Maze worlds
		return this.dungeonMazeWorlds;
	}

	/**
	 * Get all loaded Dungeon Maze worlds. This will refresh the list of worlds.
	 *
	 * @return A list of loaded Dungeon Maze worlds.
	 */
	public List<String> getLoadedDungeonMazeWorlds() {
		return getLoadedDungeonMazeWorlds(true);
	}

	/**
	 * Get all loaded Dungeon Maze worlds.
	 *
	 * @param refreshWorlds True to refresh the list of Dungeon Maze worlds.
	 *
	 * @return A list of loaded Dungeon Maze worlds.
	 */
	public List<String> getLoadedDungeonMazeWorlds(boolean refreshWorlds) {
		// Create a list to put all worlds in
		List<String> worlds = new ArrayList<String>();

		// Refresh the world lists
		if(refreshWorlds)
			refresh();

		// Check for each world if it's loaded or not
		for(String entry : this.dungeonMazeWorlds) {
			World w = Bukkit.getWorld(entry);
			if(w != null)
				worlds.add(entry);
		}

		// Return the list of worlds
		return worlds;
	}

	/**
	 * Check whether a world exists. The world doesn't need to be loaded.
	 *
	 * @param worldName The name of the world to check for.
	 *
	 * @return True if any world with this name exists, false otherwise.
	 */
	public boolean isWorld(String worldName) {
		// Check whether the world exists by it's level data, return the result
		File worldLevelFile = new File(Bukkit.getWorldContainer(), worldName + "/level.dat");
		return worldLevelFile.exists();
	}
	
	/**
	 * Check if a world is a Dungeon Maze world.
	 *
	 * @param worldName The name of the world to check for.
	 *
	 * @return True if this world is a Dungeon Maze world.
	 */
	public boolean isDungeonMazeWorld(String worldName) {
		return getDungeonMazeWorlds().contains(worldName);
	}

	/**
	 * Check whether a world is loaded.
	 *
	 * @param worldName The name of the world to check for.
	 *
	 * @return True if the world is loaded, false otherwise.
	 */
	public boolean isWorldLoaded(String worldName) {
		// Loop trough each loaded world to check if it's loaded
		for(World entry : Bukkit.getWorlds())
			if(entry.getName().equals(worldName))
				return true;

		// The world doesn't seem to be loaded, return false
		return false;
	}
	
	/**
	 * Check if a world is a Dungeon Maze world, and check if it's loaded.
	 *
	 * @param worldName The name of the world to check for.
	 *
	 * @return True if the world is a Dungeon Maze world and the world is loaded, false otherwise.
	 */
	public boolean isLoadedDungeonMazeWorld(String worldName) {
		return getLoadedDungeonMazeWorlds(false).contains(worldName);
	}

	/**
	 * Load a world if it isn't loaded yet. The world doesn't need to be a Dungeon Maze world.
	 *
	 * @param worldName The name of the world to load.
	 *
	 * @return True if any world was loaded, false otherwise. True will also be returned if the world was already loaded.
	 */
	public boolean loadWorld(String worldName) {
		// Make sure the world exists
		if(!isWorld(worldName))
			return false;

		// Make sure the world isn't loaded yet
		if(isWorldLoaded(worldName))
			return true;

		// Profile the world loading
		Profiler p = new Profiler(true);

		// Show a status message
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Loading world, expecting lag for a while...");

		// Load the world
		try {
			WorldCreator newWorld = new WorldCreator(worldName);
			if(isDungeonMazeWorld(worldName) && DungeonMaze.instance.getDungeonMazeGenerator() != null)
				newWorld.generator(DungeonMaze.instance.getDungeonMazeGenerator());
			newWorld.createWorld();
		} catch(Exception ex) {
			Core.getLogger().info("Failed to load the world '" + worldName + ", after " + p.getTimeFormatted() + "'!");
			return false;
		}

		// Show a status message, return the result
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "World loaded successfully, took " + p.getTimeFormatted() + "!");
		return true;
	}

	/**
	 * Unload a world if it's loaded. The world doesn't need to be a Dungeon Maze world.
	 *
	 * @param worldName The name of the world to unload.
	 *
	 * @return True if any world was unloaded, false otherwise. True will also be returned if the world was not loaded.
	 */
	public boolean unloadWorld(String worldName) {
		// Make sure the world exists
		if(!isWorld(worldName))
			return false;

		// Make sure the world isn't loaded yet
		if(!isWorldLoaded(worldName))
			return true;

		// Show a status message
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Unloading world, expecting lag for a while...");

		// Profile the world loading
		Profiler p = new Profiler(true);

		// Unload and save the world
		boolean unloaded = Bukkit.unloadWorld(worldName, true);

		// Show a status message, return the result
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "World unloaded successfully, took " + p.getTimeFormatted() + "!");
		return unloaded;
	}
	
	/**
	 * Preload all the Dungeon Maze worlds that should be preloaded.
	 *
	 * @return The number of preloaded worlds. Returns a negative number on failure.
	 */
	public int preloadDungeonMazeWorlds() {
		// Refresh the worlds list
		if(!refresh())
			return -1;

		// Make sure there's any world that should be preloaded
		if(getDungeonMazeWorlds(true).size() == 0)
			return 0;

		// Preload all Dungeon Maze worlds that should be preloaded
		int preloadedWorlds = 0;
		for(String worldName : getDungeonMazeWorlds(true))
			preloadedWorlds += loadWorld(worldName) ? 1 : 0;

		// Return the result
		return preloadedWorlds;
	}

	/**
	 * Prepare the server and Dungeon Maze for a new Dungeon Maze world. This will automatically edit the proper
	 * configuration file as needed.
	 *
	 * @param worldName The name of the world to configure the server for.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean prepareDungeonMazeWorld(String worldName) {
		// Edit the bukkit.yml file so bukkit knows what generator to use for the Dungeon Maze worlds, also update the
		// Dungeon Maze files.
		// Set the generator in the bukkit config file
		setBukkitConfigWorldGenerator(worldName);

		// Edit the Dungeon Maze configuration file, show a message
		Core.getLogger().info("Preparing the Dungeon Maze configuration file...");

		// Get the configuration handler, and make sure it's available
		ConfigHandler configHandler = Core.getConfigHandler();
		if(configHandler == null) {
			Core.getLogger().info("Failed to prepare the Dungeon Maze configuration file, config handler not available!");
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
		try {
			// TODO: Improve this statement below!
			Core.getConfigHandler().config.save(new File("./plugins/DungeonMaze/config.yml"));
		} catch(IOException e) {
			e.printStackTrace();
		}

		// Save the config
		DungeonMaze.instance.saveConfig();

		// Show a message, return the result
		Core.getLogger().info("Successfully prepared!");
		return true;
	}

	/**
	 * Set the Dungeon Maze world generator for the specified world in the Bukkit configuration file.
	 *
	 * @param worldName The name of the world to set the generator for.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean setBukkitConfigWorldGenerator(String worldName) {
		List<String> worlds = new ArrayList<String>();
		worlds.add(worldName);
		return setBukkitConfigWorldGenerator(worlds);
	}

	/**
	 * Set the Dungeon Maze world generator for the specified world in the Bukkit configuration file.
	 *
	 * @param worldNames The name of the world to set the generator for.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean setBukkitConfigWorldGenerator(List<String> worldNames) {
		try {
			// Get the Bukkit configuration file
			Core.getLogger().info("Editing the Bukkit configuration file...");
			File bukkitConfig = new File("bukkit.yml");

			// Load the Bukkit configuration file
			FileConfiguration serverConfig = ConfigUtils.getConfigFromPath(bukkitConfig);

			// Set the world generator
			for(String worldName : worldNames)
				serverConfig.set("worlds." + worldName + ".generator", DungeonMaze.instance.getName());

			// Save the file
			serverConfig.save(bukkitConfig);
			Core.getLogger().info("The Bukkit configuration file has been edited successfully!");
			return true;

		} catch(Exception ex) {
			Core.getLogger().info("Failed to edit the bukkit.yml file!");
			return false;
		}
	}

	/**
	 * Check whether a Minecraft world name is valid.
	 *
	 * param worldName The world name to validate.
	 *
	 * @return True if the world name is valid, false otherwise.
	 */
	public static boolean isValidWorldName(String worldName) {
		// Do a regex check
		return Pattern.compile(MINECRAFT_WORLD_NAME_REGEX).matcher(worldName).matches();
	}
}
