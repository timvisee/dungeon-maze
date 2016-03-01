package com.timvisee.dungeonmaze;

import com.timvisee.dungeonmaze.api.ApiController;
import com.timvisee.dungeonmaze.api.DungeonMazeApiOld;
import com.timvisee.dungeonmaze.command.CommandHandler;
import com.timvisee.dungeonmaze.generator.Generator;
import com.timvisee.dungeonmaze.util.MinecraftUtils;
import com.timvisee.dungeonmaze.util.Profiler;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class DungeonMaze extends JavaPlugin {

	/** Defines the name of the plugin. */
	private static final String PLUGIN_NAME = "Dungeon Maze";

	/** Defines the current Dungeon Maze version name. */
	private static final String PLUGIN_VERSION_NAME = "0.2.2";

	/** Defines the current Dungeon Maze version code. */
	private static final int PLUGIN_VERSION_CODE = 18;

    /** Defines the application ID used when checking for updates. */
    private static final String UPDATER_APP_ID = "2";

	/** Dungeon Maze instance. */
	public static DungeonMaze instance;

	/** Core instance. */
	private Core core = new Core(false);

	/** The Dungeon Maze chunk generator instance. */
	private final Generator generator = new Generator(this);

	/**
	 * Constructor.
	 */
	public DungeonMaze() {
		instance = this;
	}

	/**
	 * On enable method, called when plugin is being enabled.
	 */
	public void onEnable() {
		// Profile the start up
		Profiler profiler = new Profiler(true);

		// Show a status message
		Core.getLogger().info("Starting " + getVersionComplete(true) + "...");
        Core.getLogger().info("Detected Minecraft version: v" + MinecraftUtils.getMinecraftVersion() + " (" + MinecraftUtils.getServerType().getName() + ")");

		// Initialize the core
		initCore();

		// Show a startup message
		Core.getLogger().info(getVersionComplete(true) + " started, took " + profiler.getTimeFormatted() + "!");
		Core.getLogger().info(getPluginName() + " developed by Tim Visee - timvisee.com");
	}

	/**
	 * On enable method, called when plugin is being disabled.
	 */
	public void onDisable() {
		// Profile the shutdown
		Profiler profiler = new Profiler(true);

		// Show an disabling message
		Core.getLogger().info("Disabling " + getPluginName() + "...");

		// Destroy the core
		destroyCore(true);

		// Show an disabled message
		Core.getLogger().info(getVersionComplete(true) + " disabled, took " + profiler.getTimeFormatted() + "!");
	}

	/**
	 * Instantiate and set up the core.
	 * The Core can only be instantiated once.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean initCore() {
		// Profile the initialization
		Profiler profiler = new Profiler(true);

		// Show a status message
		Core.getLogger().info("Starting core...");

		// Initialize the core, show the result status, show a status message if it failed to initialize
		if(!this.core.init()) {
			Core.getLogger().info("[ERROR] Failed to start the core, after " + profiler.getTimeFormatted() + "!");
			return false;
		}

		// Core initialized successfully, show a status message
		Core.getLogger().info("Core started successfully, took " + profiler.getTimeFormatted() + "!");
		return true;
	}

	/**
	 * Get the core.
	 *
	 * @return Core instance.
	 */
	public Core getCore() {
		return this.core;
	}

	/**
	 * Destroy the core.
	 *
	 * @param force True to force destroy the core, false otherwise.
	 *
	 * @return True on success, false on failure.
	 */
	public boolean destroyCore(boolean force) {
		// Profile the core destruction
		Profiler profiler = new Profiler(true);

		// Show a status message
		Core.getLogger().info("Stopping core...");

		// Destroy the core, show the result status
		if(!this.core.destroy(force)) {
			// Show a status message, return the result
			Core.getLogger().info("Failed to stop the core, after " + profiler.getTimeFormatted() + "!");
			return false;
		}

		// Show a status message, return the result
		Core.getLogger().info("Core stopped successfully, took " + profiler.getTimeFormatted() + "!");
		return true;
	}

    /**
	 * Handle Bukkit commands.
	 *
	 * @param sender The command sender (Bukkit).
	 * @param cmd The command (Bukkit).
	 * @param commandLabel The command label (Bukkit).
	 * @param args The command arguments (Bukkit).
	 *
	 * @return True if the command was executed, false otherwise.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		// Get the command handler, and make sure it's valid
		CommandHandler commandHandler = Core.getCommandHandler();
		if(commandHandler == null)
			return false;


		// Handle the command, return the result
		return commandHandler.onCommand(sender, cmd, commandLabel, args);
	}

	/**
	 * Return the Dungeon Maze world generator.
	 *
	 * @param worldName The name of the world. (Currently not used)
	 * @param id Unique ID, if any, that was specified to indicate which generator was requested.
	 *
	 * @return The Dungeon Maze world generator.
	 */
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        // Always return the DungeonMaze generator
		return getDungeonMazeGenerator();

        /*
        // NOTE: This seems to be obsolete, and causes various problems. For example, Multiverse doesn't detect the generator anymore
		// Get the world manager, and make sure it's initialized
		WorldManager worldManager = Core.getWorldManager();
		if(worldManager == null)
			return getDungeonMazeGenerator();

		// Return the Dungeon Maze generator if the world is a Dungeon Maze world
		if(worldManager.isDungeonMazeWorld(worldName))
			return getDungeonMazeGenerator();
		return null;
        */
	}

	/**
	 * Get the Dungeon Maze chunk generator instance.
	 *
	 * @return Dungeon Maze chunk generator.
	 */
	public ChunkGenerator getDungeonMazeGenerator() {
		return this.generator;
	}

	/**
	 * Get the Dungeon Maze API controller.
	 *
	 * @return Dungeon Maze API controller.
	 */
	public ApiController getApiController() {
		return Core.getApiController();
	}

	/**
	 * Get the old Dungeon Maze API.
	 *
	 * @return Old Dungeon Maze API.
	 *
	 * @deprecated This API is deprecated. The new Dungeon Maze API should be used instead. This API will be removed
	 * soon.
	 */
	@SuppressWarnings({"deprecation", "UnusedDeclaration"})
	@Deprecated
	public DungeonMazeApiOld getDmAPI() {
		return Core.getOldApiController().getApi();
	}

	/**
	 * Get the name of the plugin.
	 *
	 * @return Plugin name.
     */
	public static String getPluginName() {
		return PLUGIN_NAME;
	}

	/**
	 * Get the current installed Dungeon Maze version name.
	 *
	 * @return The version name of the currently installed Dungeon Maze instance.
	 */
	public static String getVersionName() {
		return PLUGIN_VERSION_NAME;
	}

	/**
	 * Get the current installed Dungeon Maze version code.
	 *
	 * @return The version code of the currently installed Dungeon Maze instance.
	 */
	public static int getVersionCode() {
		return PLUGIN_VERSION_CODE;
	}

	/**
	 * Get the complete version identifier.
	 * The includes a prefixed 'v' sign, the version name and the version code between brackets.
	 *
	 * @param name True to include the plugin name in front.
	 *
	 * @return The complete version string.
     */
	public static String getVersionComplete(boolean name) {
		return (name ? DungeonMaze.PLUGIN_NAME : "") + " v" + getVersionName() + " (" + getVersionCode() + ")";
	}

    /**
     * Get the application ID of the plugin to use when checking for updates.
     *
     * @return Application ID.
     */
    public static String getUpdaterApplicationId() {
        return UPDATER_APP_ID;
    }



	// TODO: Put all this code bellow in a manager class to handle all the hard stuff, and to clean up the code.
	// TODO: Also save this data into the data folder of the world files so it can be read if needed

	// Worlds
	public String lastWorld = "";
	public List<String> constantRooms = new ArrayList<>(); // x;y;z

	public void registerConstantRoom(String world, Chunk chunk, int roomX, int roomY, int roomZ) {
		registerConstantRoom(world, chunk.getX(), chunk.getZ(), roomX, roomY, roomZ);
	}

	public void registerConstantRoom(String world, int chunkX, int chunkZ, int roomX, int roomY, int roomZ) {
		registerConstantRoom(world, (chunkX * 16) + roomX, roomY, (chunkZ * 16) + roomZ);
	}

	public void registerConstantRoom(String world, int roomX, int roomY, int roomZ) {
		if(!lastWorld.equals(world)) {
			lastWorld = world;
			constantRooms.clear();
		}
		constantRooms.add(Integer.toString(roomX) + ";" + Integer.toString(roomY) + ";" + Integer.toString(roomZ));
	}

	public boolean isConstantRoom(String world, Chunk chunk, int roomX, int roomY, int roomZ) {
		return isConstantRoom(world, chunk.getX(), chunk.getZ(), roomX, roomY, roomZ);
	}

	public boolean isConstantRoom(String world, int chunkX, int chunkZ, int roomX, int roomY, int roomZ) {
		return isConstantRoom(world, (chunkX * 16) + roomX, roomY, (chunkZ * 16) + roomZ);
	}

	public boolean isConstantRoom(String world, int roomX, int roomY, int roomZ) {
		if(!lastWorld.equals(world)) {
			lastWorld = world;
			constantRooms.clear();
		}
		return constantRooms.contains(Integer.toString(roomX) + ";" + Integer.toString(roomY) + ";" + Integer.toString(roomZ));
	}
}
