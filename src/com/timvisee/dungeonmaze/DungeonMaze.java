package com.timvisee.dungeonmaze;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.timvisee.dungeonmaze.api.ApiController;
import com.timvisee.dungeonmaze.command.CommandHandler;
import com.timvisee.dungeonmaze.generator.Generator;
import com.timvisee.dungeonmaze.update.Updater;
import com.timvisee.dungeonmaze.util.Profiler;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import com.timvisee.dungeonmaze.update.Updater.UpdateResult;
import com.timvisee.dungeonmaze.api.DungeonMazeApiOld;

public class DungeonMaze extends JavaPlugin {

	// TODO: Use material enums instead of ID's due to ID deprecation by Mojang

	/** Dungeon Maze instance. */
	public static DungeonMaze instance;

	/** Core instance. */
	private Core core = new Core(false);

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
		Core.getLogger().info("[DungeonMaze] Starting Dungeon Maze v" + getVersion() + "...");

		// Initialize the core
		initCore();

		// Show a startup message
		Core.getLogger().info("[DungeonMaze] Dungeon Maze v" + getVersion() + " started, took " + p.getTimeFormatted() + "!");
		Core.getLogger().info("[DungeonMaze] Dungeon Maze developed by Tim Visee - timvisee.com");
	}

	/**
	 * On enable method, called when plugin is being disabled
	 */
	public void onDisable() {
		// Profile the shutdown
		Profiler p = new Profiler(true);

		// Show an disabling message
		Core.getLogger().info("[DungeonMaze] Disabling Dungeon Maze...");

		// Destroy the core
		destroyCore(true);

		// Show an disabled message
		Core.getLogger().info("[DungeonMaze] Dungeon Maze Disabled, took " + p.getTimeFormatted() + "!");
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
		Core.getLogger().info("[DungeonMaze] Starting core...");

		// Initialize the core, show the result status
		if(!this.core.init()) {
			// Core failed to initialize, show a status message
			Core.getLogger().info("[DungeonMaze] [ERROR] Failed to start the core, after " + p.getTimeFormatted() + "!");
			return false;
		}

		// Core initialized, show a status message
		Core.getLogger().info("[DungeonMaze] Core started successfully, took " + p.getTimeFormatted() + "!");
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
		Core.getLogger().info("[DungeonMaze] Stopping core...");

		// Destroy the core, show the result status
		if(!this.core.destroy(force)) {
			// Show a status message, return the result
			Core.getLogger().info("[DungeonMaze] Failed to stop the core, after " + p.getTimeFormatted() + "!");
			return false;
		}

		// Show a status message, return the result
		Core.getLogger().info("[DungeonMaze] Core stopped successfully, took " + p.getTimeFormatted() + "!");
		return true;
	}










	public ApiController getApiController() {
		return Core.getApiController();
	}

	private final Generator generator = new Generator(this);
	
	// Worlds
	public String lastWorld = "";
	public List<String> constantRooms = new ArrayList<String>(); // x;y;z
	public List<String> constantChunks = new ArrayList<String>(); // x;
		
	public boolean usePermissions() {
		return Core.getConfigHandler().usePermissions;
	}
	
	public boolean useBypassPermissions() {
		return Core.getConfigHandler().useBypassPermissions;
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
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		// Get the command handler, and make sure it's valid
		CommandHandler commandHandler = Core.getCommandHandler();
		if(commandHandler == null)
			return false;

		// Handle the command, return the result
		return commandHandler.onCommand(sender, cmd, commandLabel, args);

		/*if(commandLabel.equalsIgnoreCase("dungeonmaze") || commandLabel.equalsIgnoreCase("dm")) {
			if(args.length == 0) {
				sender.sendMessage(ChatColor.DARK_RED + "Unknown command!");
				sender.sendMessage(ChatColor.GOLD + "Use the command " + ChatColor.YELLOW + "/dm createworld <name>" + ChatColor.GOLD + " to create a new Dungeon Maze world");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("reload")) {
				
				// Check wrong command values
				if(args.length != 1) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}
				
				// Check permission
				if(sender instanceof Player) {
					if(!Core.getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.reload")) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}

				Core.getLogger().info("[DungeonMaze] Reloading plugin...");
				sender.sendMessage(ChatColor.YELLOW + "Reloading Dungeon Maze");
				
				// TODO: Reload the permissions module!
				
				// Reload configs and worlds
				Core.getConfigHandler().load();
				Core.getWorldManager();
				Core.getWorldManager().preloadWorlds();
				
				// Show a succes message
				Core.getLogger().info("[DungeonMaze] Dungeon Maze has been reloaded!");
				sender.sendMessage(ChatColor.GREEN + "Dungeon Maze has been reloaded!");
				return true;
				
			} else if(args[0].equalsIgnoreCase("reloadpermissions") || args[0].equalsIgnoreCase("reloadperms")) {
				
				// Check wrong command values
				if(args.length != 1) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}
				
				// Check permission
				if(sender instanceof Player) {
					if(!Core.getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.reloadpermissions")) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				// TODO: (Re)load the permissions module!
				
				// Show a succes message
				sender.sendMessage(ChatColor.GREEN + "Permissions succesfully reloaded!");
				return true;
				
			} else if(args[0].equalsIgnoreCase("check") || args[0].equalsIgnoreCase("checkupdates")) {
				
				// Check wrong command values
				if(args.length != 1) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}
				
				// Check permission
				if(sender instanceof Player) {
					if(!Core.getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.checkupdates")) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				// Setup permissions
				sender.sendMessage(ChatColor.GREEN + "Checking for updates...");
				
				// Get the update checker and refresh the updates data
				// TODO: Force check for an update!
				Updater uc = Core.getUpdateChecker();
				
				if(uc.getResult() != UpdateResult.SUCCESS && uc.getResult() == UpdateResult.UPDATE_AVAILABLE) {
					sender.sendMessage(ChatColor.GREEN + "No new version found!");
				} else {
					
					String newVer = uc.getLatestName();
					
					// Make sure the new version is compatible with the current bukkit version
					if(uc.getResult() == UpdateResult.FAIL_NOVERSION) {
						sender.sendMessage(ChatColor.GREEN + "New Dungeon Maze version available: v" + String.valueOf(newVer));
						sender.sendMessage(ChatColor.GREEN + "The new version is not compatible with your Bukkit version!");
						sender.sendMessage(ChatColor.GREEN + "Please update your Bukkkit to " +  uc.getLatestGameVersion() + " or higher!");
					} else {
						if(uc.getResult() == UpdateResult.SUCCESS)
							sender.sendMessage(ChatColor.GREEN + "New version installed (v" + String.valueOf(newVer) + "). Server reboot required!");
						else {
							sender.sendMessage(ChatColor.GREEN + "New version found: " + String.valueOf(newVer));
							sender.sendMessage(ChatColor.GREEN + "Use " + ChatColor.GOLD + "/dm installupdate" +
									ChatColor.GREEN + " to automaticly install the new version!");
						}
					}
				}
				
				return true;
				
			} else if(args[0].equalsIgnoreCase("installupdate") || args[0].equalsIgnoreCase("installupdates")) {
				// Check wrong command values
				if(args.length != 1) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");
					sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}

				// Check permission
				if(sender instanceof Player) {
					if(!Core.getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.installupdate")) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}

				// Setup permissions
				sender.sendMessage(ChatColor.GREEN + "Checking for updates...");

				// Get the update checker and refresh the updates data
				// TODO: Force check for an update!
				Updater uc = Core.getUpdateChecker();

				if(uc.getResult() != UpdateResult.SUCCESS && uc.getResult() == UpdateResult.UPDATE_AVAILABLE) {
					sender.sendMessage(ChatColor.GREEN + "No new version found!");
				} else {

					String newVer = uc.getLatestName();

					// Make sure the new version is compatible with the current bukkit version
					if(uc.getResult() == UpdateResult.FAIL_NOVERSION) {
						sender.sendMessage(ChatColor.GREEN + "New Dungeon Maze version available: v" + String.valueOf(newVer));
						sender.sendMessage(ChatColor.GREEN + "The new version is not compatible with your Bukkit version!");
						sender.sendMessage(ChatColor.GREEN + "Please update your Bukkkit to " +  uc.getLatestGameVersion() + " or higher!");
					} else {
						if(uc.getResult() == UpdateResult.SUCCESS)
							sender.sendMessage(ChatColor.GREEN + "New version installed (v" + String.valueOf(newVer) + "). Server reboot required!");
						else {
							sender.sendMessage(ChatColor.GREEN + "New version found: " + String.valueOf(newVer) + ", but auto-install failed, please update by yourself!");
						}
					}
				}
				return true;
			} else if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") ||
					args[0].equalsIgnoreCase("?")) {
				
				// Check wrong command values
				if(args.length != 1) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");
					sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}
				
				// View the help
				sender.sendMessage(ChatColor.GREEN + "==========[ DUNGEON MAZE HELP ]==========");
				sender.sendMessage(ChatColor.GOLD + "/" + commandLabel + " <help/h/?> " + ChatColor.WHITE + ": View help");
				sender.sendMessage(ChatColor.GOLD + "/" + commandLabel + " createworld <name>" + ChatColor.WHITE + ": Create a Dungeon Maze world");
				sender.sendMessage(ChatColor.GOLD + "/" + commandLabel + " teleport <world> " + ChatColor.WHITE + ": Teleport to a world");
				sender.sendMessage(ChatColor.GOLD + "/" + commandLabel + " listworlds " + ChatColor.WHITE + ": List Dungeon Maze worlds");
				sender.sendMessage(ChatColor.GOLD + "/" + commandLabel + " reload " + ChatColor.WHITE + ": Reload config files");
				sender.sendMessage(ChatColor.GOLD + "/" + commandLabel + " reloadperms " + ChatColor.WHITE + ": Reload permissions system");
				sender.sendMessage(ChatColor.GOLD + "/" + commandLabel + " <checkupdates/check> " + ChatColor.WHITE + ": Check for updates");
				sender.sendMessage(ChatColor.GOLD + "/" + commandLabel + " installupdate" + ChatColor.WHITE + ": Install new updates");
				sender.sendMessage(ChatColor.GOLD + "/" + commandLabel + " <version/ver/v> " + ChatColor.WHITE + ": Check plugin version");
				
				return true;
			} else {
				// Handle unknown commands
				sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
				return true;
			}
		}
		return false;*/
	}
	
	public boolean worldExists(String w) {
		// Neat feature to check if a world exists
		File worldLevelFile = new File(w + "/level.dat");
		return worldLevelFile.exists();
	}
	
	public boolean worldIsLoaded(String w) {
		for(World entry : getServer().getWorlds())
			if(entry.getName().equals(w))
				return true;
		return false;
	}
	
	public void worldLoad(String w) {
		if(worldExists(w)) {
			if(!worldIsLoaded(w)) {
				getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[DungeonMaze] Loading world, expecting lag for a while...");
				
				WorldCreator newWorld = new WorldCreator(w);
				newWorld.generator(generator);
				newWorld.createWorld();
				
				getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[DungeonMaze] World loaded successfully!");
			}
		}
	}
	
	// Function to get a costum configuration file
	public FileConfiguration getConfigFromPath(File file) {
		// The file param may not be null
		if (file == null)
		    return null;
	    
		// Get and return the config from an external file
	    return YamlConfiguration.loadConfiguration(file);
	}
	
	@Override
	public FileConfiguration getConfig() {
		return Core.getConfigHandler().config;
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return this.generator;
	}
	
	public ChunkGenerator getDMWorldGenerator() {
		return this.generator;
	}
	
	public boolean isAnyPlayerOnline() {
		return (getServer().getOnlinePlayers().size() > 0);
	}
	
	
	
	// TODO: Put all this codeb below in a manager class to handle all the hard stuff, and to clean up the code.
	// TODO: Also save this data into the data folder of the world files so it can be read if needed
	// Getters and setters for the two lists with constant chunks and constant rooms
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

	/**
	 * Get the old Dungeon Maze API.
	 *
	 * @return Old Dungeon Maze API.
	 *
	 * @deprecated This API is deprecated. The new Dungeon Maze API should be used instead. This API will be removed
	 * soon.
	 */
	@Deprecated
	public DungeonMazeApiOld getDmAPI() {
		return Core.getOldApiController().getApi();
	}

	public String getVersion() {
		return getDescription().getVersion();
	}
}
