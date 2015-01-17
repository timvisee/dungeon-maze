package com.timvisee.dungeonmaze;

import com.timvisee.dungeonmaze.api.ApiController;
import com.timvisee.dungeonmaze.api.DungeonMazeApiOld;
import com.timvisee.dungeonmaze.command.CommandHandler;
import com.timvisee.dungeonmaze.generator.Generator;
import com.timvisee.dungeonmaze.util.Profiler;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class DungeonMaze extends JavaPlugin {

	// TODO: Use material enums instead of ID's due to ID deprecation by Mojang

	/** Dungeon Maze instance. */
	public static DungeonMaze instance;

	/** Core instance. */
	private Core core = new Core(false);

	/** The Dungeon Maze chunk generator instance. */
	private final Generator generator = new Generator(this);

	/**
	 * Constructor
	 */
	public DungeonMaze() {
		// Define the DungeonMaze static instance variable
		instance = this;
	}

	/**
	 * On enable method, called when plugin is being enabled
	 */
	public void onEnable() {
		// Profile the start up
		Profiler p = new Profiler(true);

		// Show a status message
		Core.getLogger().info("Starting Dungeon Maze v" + getVersion() + "...");

		// Initialize the core
		initCore();

		// Show a startup message
		Core.getLogger().info("Dungeon Maze v" + getVersion() + " started, took " + p.getTimeFormatted() + "!");
		Core.getLogger().info("Dungeon Maze developed by Tim Visee - timvisee.com");
	}

	/**
	 * On enable method, called when plugin is being disabled
	 */
	public void onDisable() {
		// Profile the shutdown
		Profiler p = new Profiler(true);

		// Show an disabling message
		Core.getLogger().info("Disabling Dungeon Maze...");

		// Destroy the core
		destroyCore(true);

		// Show an disabled message
		Core.getLogger().info("Dungeon Maze Disabled, took " + p.getTimeFormatted() + "!");
	}

	/**
	 * Instantiate and set up the core.
	 * The Core can only be instantiated once.
	 *
	 * @return True on success, false on failure.
	 */
	private boolean initCore() {
		// Profile the initialization
		Profiler p = new Profiler(true);

		// Show a status message
		Core.getLogger().info("Starting core...");

		// Initialize the core, show the result status
		if(!this.core.init()) {
			// Core failed to initialize, show a status message
			Core.getLogger().info("[ERROR] Failed to start the core, after " + p.getTimeFormatted() + "!");
			return false;
		}

		// Core initialized, show a status message
		Core.getLogger().info("Core started successfully, took " + p.getTimeFormatted() + "!");
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
	private boolean destroyCore(boolean force) {
		// Profile the core destruction
		Profiler p = new Profiler(true);

		// Show a status message
		Core.getLogger().info("Stopping core...");

		// Destroy the core, show the result status
		if(!this.core.destroy(force)) {
			// Show a status message, return the result
			Core.getLogger().info("Failed to stop the core, after " + p.getTimeFormatted() + "!");
			return false;
		}

		// Show a status message, return the result
		Core.getLogger().info("Core stopped successfully, took " + p.getTimeFormatted() + "!");
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
	 * Get the Dungeon Maze configuration.
	 *
	 * @return Dungeon Maze configuration.
	 */
	@Override
	public FileConfiguration getConfig() {
		return Core.getConfigHandler().config;
	}

	/**
	 * Get the default world generator for a world. Inject the Dungeon Maze generator for Dungeon Maze worlds.
	 *
	 * @param worldName The name of the world to get the generator for.
	 * @param id Unique ID, if any, that was specified to indicate which generator was requested
	 *
	 * @return The world generator for the specified world.
	 */
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		// Get the world manager, and make sure it's initialized
		WorldManager worldManager = Core.getWorldManager();

		if(worldManager == null)
			return getDungeonMazeGenerator();

		// Return the Dungeon Maze generator if the world is a Dungeon Maze world
		if(worldManager.isDungeonMazeWorld(worldName))
			return getDungeonMazeGenerator();
		return null;
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
	 * Get the current installed Dungeon Maze version.
	 *
	 * @return The version number of the currently installed Dungeon Maze instance.
	 */
	public String getVersion() {
		return getDescription().getVersion();
	}











	// TODO: Put all this code bellow in a manager class to handle all the hard stuff, and to clean up the code.
	// TODO: Also save this data into the data folder of the world files so it can be read if needed

	// Worlds
	public String lastWorld = "";
	public List<String> constantRooms = new ArrayList<String>(); // x;y;z
	public List<String> constantChunks = new ArrayList<String>(); // x;

	// Getters and setters for the two lists with constant chunks and constant rooms
	@SuppressWarnings("UnusedDeclaration")
	public void registerConstantChunk(String world, Chunk chunk) {
		registerConstantChunk(world, chunk.getX(), chunk.getZ());
	}

	public void registerConstantChunk(String world, int chunkX, int chunkZ) {
		if (!lastWorld.equals(world)) {
			lastWorld = world;
			constantChunks.clear();
		}
		constantChunks.add(Integer.toString(chunkX) + ";" + Integer.toString(chunkZ));
	}

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

	public boolean isConstantChunk(String world, Chunk chunk) {
		return isConstantChunk(world, chunk.getX(), chunk.getZ());
	}

	public boolean isConstantChunk(String world, int chunkX, int chunkZ) {
		if(!lastWorld.equals(world)) {
			lastWorld = world;
			constantChunks.clear();
		}
		return constantChunks.contains(Integer.toString(chunkX) + ";" + Integer.toString(chunkZ));
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
