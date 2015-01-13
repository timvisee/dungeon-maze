package com.timvisee.dungeonmaze;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.timvisee.dungeonmaze.api.ApiController;
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
		Core.getLogger().info("[DungeonMaze] Dungeon Maze made by Tim Visee - timvisee.com");
	}

	/**
	 * On enable method, called when plugin is being disabled
	 */
	public void onDisable() {
		// Get the config instance
		FileConfiguration c = Core.getConfigHandler().config;

		// Unload all Dungeon Maze worlds if it's enabled
		if(c.getBoolean("unloadWorldsOnPluginDisable", true)) {
			if(c.getStringList("worlds").size() > 0) {
				// Dungeon Maze does have some worlds
				Core.getLogger().info("[DungeonMaze] Unloading Dungeon Maze worlds...");

				// Unload the Dungeon Maze worlds
				List<String> worlds = new ArrayList<String>();
				for(World w : getServer().getWorlds())
					if(c.getStringList("worlds").contains(w.getName()))
						worlds.add(w.getName());

				for(String w : worlds)
					getServer().unloadWorld(w, true);

				Core.getLogger().info("[DungeonMaze] All Dungeon Maze worlds have been unloaded!");
			} else
				Core.getLogger().info("[DungeonMaze] No Dungeon Maze worlds to unload avaiable");

		} else
			Core.getLogger().info("[DungeonMaze] Unloading worlds has been disabled!");

		// Destroy the core
		destroyCore(true);

		// Show an disabled message
		Core.getLogger().info("[DungeonMaze] Dungeon Maze Disabled!");
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("dungeonmaze") || commandLabel.equalsIgnoreCase("dm")) {
			if(args.length == 0) {
				sender.sendMessage(ChatColor.DARK_RED + "Unknown command!");
				sender.sendMessage(ChatColor.GOLD + "Use the command " + ChatColor.YELLOW + "/dm createworld <name>" + ChatColor.GOLD + " to create a new Dungeon Maze world");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("createworld") ||
					args[0].equalsIgnoreCase("cw") ||
					args[0].equalsIgnoreCase("create")) {
				// Check permission
				if(sender instanceof Player) {
					if(!Core.getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.createworld", sender.isOp())) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				if(args.length != 2) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}
				
				// Get the world name
				String w = args[1];
				
				if(worldExists(w)) {
					sender.sendMessage(ChatColor.DARK_RED + w);
					sender.sendMessage(ChatColor.DARK_RED + "This world already exists!");
					return true;
				}
				
				// Edit the bukkit.yml file so bukkit knows what generator to use for the Dungeon Maze worlds,
				// also update the Dungeon Maze files.
				System.out.println("Editing bukkit.yml file...");
				FileConfiguration serverConfig = getConfigFromPath(new File("bukkit.yml"));
				serverConfig.set("worlds." + w + ".generator", "DungeonMaze");
				try {
					serverConfig.save(new File("bukkit.yml"));
				} catch (IOException e) {
					e.printStackTrace();
					return true;
				}
				System.out.println("Editing Dungeon Maze config.yml file...");
				List<String> worlds = Core.getConfigHandler().config.getStringList("worlds");
				if(!worlds.contains(w))
					worlds.add(w);
				Core.getConfigHandler().config.set("worlds", worlds);
				List<String> preloadWorlds = Core.getConfigHandler().config.getStringList("preloadWorlds");
				if(!preloadWorlds.contains(w))
					preloadWorlds.add(w);
				Core.getConfigHandler().config.set("preloadWorlds", preloadWorlds);
				saveConfig();
				System.out.println("Editing finished!");

				// Create the world
				WorldCreator newWorld = new WorldCreator(w);
				newWorld.generator(this.generator);
				World world = newWorld.createWorld();
				
				// If the sender is a player, teleport him!
				if(sender instanceof Player) {
					Player p = (Player) sender;
					p.teleport(world.getSpawnLocation());
					p.sendMessage(ChatColor.GREEN + "The world has been succesfully generated! You have been teleported.");
				}
				
			} else if(args[0].equalsIgnoreCase("teleport") ||
					args[0].equalsIgnoreCase("tp") ||
					args[0].equalsIgnoreCase("warp")) {
				
				// Check permission
				if(sender instanceof Player) {
					if(!Core.getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.teleport", sender.isOp())) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				// Check for invalid command arguments
				if(args.length != 2) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}
				
				// The command must be ran by an in-game player
				if(!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.DARK_RED + "You need to be in-game to use this command!");
					return true;
				}

				// Get the player and the world name
				Player p = (Player) sender;
				String w = args[1];
				
				// The world must exist
				if(!worldExists(w)) {
					sender.sendMessage(ChatColor.DARK_RED + w);
					sender.sendMessage(ChatColor.DARK_RED + "This world doesn't exists!");
					return true;
				}
				
				// The world must be loaded, if not force the world to load
				if(!worldIsLoaded(w))
					worldLoad(w);
				
				// Telepor the player, show a status message and return true
				p.teleport(getServer().getWorld(w).getSpawnLocation());
				p.sendMessage(ChatColor.GREEN + "You have been teleported.");
				return true;
				
			} else if(args[0].equalsIgnoreCase("listworlds") || args[0].equalsIgnoreCase("lw") ||
					args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {

				// Check wrong command values
				if(args.length != 1) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}
				
				// Check permission
				if(sender instanceof Player) {
					if(!Core.getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.listworlds", sender.isOp())) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				sender.sendMessage(ChatColor.YELLOW + "==========[ DUNGEON MAZE WORLDS ]==========");
				List<String> worlds = Core.getWorldManager().getDMWorlds();
				if(worlds.size() > 0) {
					for(String w : worlds) {
						if(Core.getWorldManager().isDMWorldLoaded(w))
							sender.sendMessage(ChatColor.GOLD + " - " + w + "   " + ChatColor.GREEN + "Loaded");
						else
							sender.sendMessage(ChatColor.GOLD + " - " + w + "   " + ChatColor.DARK_RED + "Not Loaded");
					}
				} else
					sender.sendMessage(ChatColor.DARK_RED + "You don't have any Dungeon Maze world yet!");
				return true;
				
			} else if(args[0].equalsIgnoreCase("reload")) {
				
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
			} else if(args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("v")) {
				// Check wrong command values
				if(args.length != 1) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");
					sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}

				sender.sendMessage(ChatColor.GREEN + "This server is running Dungeon Maze v" + DungeonMaze.instance.getVersion());
				sender.sendMessage(ChatColor.GREEN + "Developed by Tim Visee - http://timvisee.com/");

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
		return false;
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
				getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[DungeonMaze] Loading world, there's probably some lag for a little while");
				
				WorldCreator newWorld = new WorldCreator(w);
				newWorld.generator(generator);
				newWorld.createWorld();
				
				getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[DungeonMaze] World succesfully loaded!");
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
