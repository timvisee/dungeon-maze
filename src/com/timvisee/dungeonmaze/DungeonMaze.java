package com.timvisee.dungeonmaze;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.timvisee.dungeonmaze.Metrics.Graph;
import com.timvisee.dungeonmaze.api.DungeonMazeAPI;
import com.timvisee.dungeonmaze.listener.DMBlockListener;
import com.timvisee.dungeonmaze.listener.DMPlayerListener;
import com.timvisee.dungeonmaze.listener.DMPluginListener;
import com.timvisee.dungeonmaze.listener.DMWorldListener;
import com.timvisee.dungeonmaze.manager.DMWorldManager;
import com.timvisee.dungeonmaze.manager.PermissionsManager;

public class DungeonMaze extends JavaPlugin {	
	
	public static final Logger log = Logger.getLogger("Minecraft");
	private final DMGenerator dmGenerator = new DMGenerator(this);

	// Dungeon Maze static instance
	public static DungeonMaze instance;
	
	// Listener
	private final DMBlockListener blockListener = new DMBlockListener();
	private final DMPlayerListener playerListener = new DMPlayerListener();
	private final DMPluginListener pluginListener = new DMPluginListener();
	private final DMWorldListener worldListener = new DMWorldListener();
	
	// Configuration
	public static FileConfiguration config;
	public static boolean unloadWorldsOnPluginDisable;
	public static boolean allowSurface;
	public boolean worldProtection;
	public static List<Object> blockWhiteList;
	public static boolean enableUpdateCheckerOnStartup;
	public static boolean usePermissions;
	public static boolean useBypassPermissions;
	public static List<String> mobs;
	
	// Worlds
	public String lastWorld = "";
	public List<String> constantRooms = new ArrayList<String>(); // x;y;z
	public List<String> constantChunks = new ArrayList<String>(); // x;z
	
	// Update Checker
	private DMUpdateChecker uc;

	// Permissions manager
	private PermissionsManager pm;
	
	/* Multiverse */
	public boolean useMultiverse = false;
	public MultiverseCore multiverseCore;
	
	private DMWorldManager dmWorldManager;
	private DungeonMazeAPI dmAPI;
	
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
	    
		// Load the config file
		loadConfig();
		
		// Set up the DM Event Handler
		DungeonMazeAPI.setUpDMEventHandler();
		
		// Set up the DM world manager and preload the worlds
		setUpDMWorldManager();
		DMWorldManager.preloadWorlds();

		// Set up the update checker
		setUpUpdateChecker();
		
		// Remove all (old) update files
		getUpdateChecker().removeUpdateFiles();
		
		// Set up permissions usage
		setUpPermissionsManager();
		
		// Set up multiverse usage
		setUpMultiverse();
		
		// Register all event listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.blockListener, this);
		pm.registerEvents(this.playerListener, this);
		pm.registerEvents(this.pluginListener, this);
		pm.registerEvents(this.worldListener, this);

		// Setup API
		setAPI(new DungeonMazeAPI(this));

		// Show a startup message
		PluginDescriptionFile pdfFile = getDescription();
		log.info("[DungeonMaze] Dungeon Maze v" + pdfFile.getVersion() + " Started");
		log.info("[DungeonMaze] Dungeon Maze made by Tim Visee - timvisee.com");

		// Setup Metrics
		setUpMetrics();
	}

	/**
	 * On enable method, called when plugin is being disabled
	 */
	public void onDisable() {
		// Unload all Dungeon Maze worlds if it's enabled
		if(config.getBoolean("unloadWorldsOnPluginDisable", true)) {
			if(config.getStringList("worlds").size() > 0) {
				// Dungeon Maze does have some worlds
				log.info("[DungeonMaze] Unloading Dungeon Maze worlds...");
				
				// Unload the Dungeon Maze worlds
				List<String> worlds = new ArrayList<String>();
				for(World w : getServer().getWorlds())
					if(config.getStringList("worlds").contains(w.getName()))
						worlds.add(w.getName());
						
				for(String w : worlds)
					getServer().unloadWorld(w, true);
				
				log.info("[DungeonMaze] All Dungeon Maze worlds have been unloaded!");
			} else
				log.info("[DungeonMaze] No Dungeon Maze worlds to unload avaiable");
				
		} else
			log.info("[DungeonMaze] Unloading worlds has been disabled!");
		
		// If any update was downloaded, install the update
		if(getUpdateChecker().isUpdateDownloaded())
			getUpdateChecker().installUpdate();
		
		// Remove all update files
		getUpdateChecker().removeUpdateFiles();
		
		// Show an disabled message
		log.info("[DungeonMaze] Dungeon Maze Disabled");
	}
	
	/**
	 * Setup the permissions manager
	 */
	@SuppressWarnings("static-access")
	public void setUpPermissionsManager() {
		// Setup the permissions manager
		this.pm = new PermissionsManager(this.getServer(), (Plugin) this, this.log);
		this.pm.setup();
	}
	
	/**
	 * Get the permissions manager
	 * @return permissions manager
	 */
	public PermissionsManager getPermissionsManager() {
		return this.pm;
	}
	
	/**
	 * Get the DM world manager
	 * @return
	 */
	public DMWorldManager getDMWorldManager() {
		return dmWorldManager;
	}
	
	/**
	 * Setup the metrics statics feature
	 * @return false if an error occurred
	 */
	public boolean setUpMetrics() {
		try {
		    Metrics metrics = new Metrics(this);
		    // Construct a graph, which can be immediately used and considered as valid
		    // Player count in Dungeon Maze
		    Graph graph = metrics.createGraph("Players in Dungeon Maze");
		    graph.addPlotter(new Metrics.Plotter("Players") {
	            @Override
	            public int getValue() {
	            	List<Player> players = Arrays.asList(getServer().getOnlinePlayers());
	            	int count = 0;
	            	for(Player p : players) {
	            		getDMWorldManager();
						if(DMWorldManager.isDMWorld(p.getWorld().getName()))
	            			count++;
	            	}
	            	return count;
	            }
		    });
		    metrics.start();
		    return true;
		} catch (IOException e) {
		    // Failed to submit the statics :-(
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Set up the update checker
	 */
	public void setUpUpdateChecker() {
		this.uc = new DMUpdateChecker();
	}
	
	/**
	 * Get the update checker instance
	 * @return Update checker instance
	 */
	public DMUpdateChecker getUpdateChecker() {
		return this.uc;
	}
	
	private void setUpMultiverse() {
		// Setup and hook into Multiverse
		Plugin multiversePlugin = this.getServer().getPluginManager().getPlugin("Multiverse-Core");
		if (multiversePlugin == null) {
		    log.info("[DungeonMaze] Multiverse not detected! Disableling Multiverse usage!");
		    useMultiverse = false;
		    return;
		}
		
		log.info("[DungeonMaze] Hooked into Multiverse");
		useMultiverse = true;
		multiverseCore = new MultiverseCore();
	}
	
	private void setUpDMWorldManager() {
		// Setup the DM world manager
		this.dmWorldManager = new DMWorldManager();
		DMWorldManager.refresh();
	}
		
	public boolean usePermissions() {
		return usePermissions;
	}
	
	public boolean useBypassPermissions() {
		return useBypassPermissions;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("dungeonmaze") || commandLabel.equalsIgnoreCase("dm")) {
			if(args.length == 0) {
				sender.sendMessage(ChatColor.DARK_RED + "Unknown command!");
				sender.sendMessage(ChatColor.GOLD + "Use the command " + ChatColor.YELLOW + "/dm createworld <name>" + ChatColor.GOLD + " to create a new Dungeon Maze world");
				return true;
			}
			
			if(args[0].toString().equalsIgnoreCase("createworld") ||
					args[0].toString().equalsIgnoreCase("cw") ||
					args[0].toString().equalsIgnoreCase("create")) {
				// Check permission
				if(sender instanceof Player) {
					if(!getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.createworld", sender.isOp())) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				if(args.length != 2) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}
				
				// Get the world name
				String w = args[1].toString();
				
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
				List<String> worlds = config.getStringList("worlds");
				if(!worlds.contains(w))
					worlds.add(w);
				config.set("worlds", worlds);
				List<String> preloadWorlds = config.getStringList("preloadWorlds");
				if(!preloadWorlds.contains(w))
					preloadWorlds.add(w);
				config.set("preloadWorlds", preloadWorlds);
				saveConfig();
				System.out.println("Editing finished!");

				// Create the world
				WorldCreator newWorld = new WorldCreator(w);
				newWorld.generator(this.dmGenerator);
				World world = newWorld.createWorld();
				
				// If the sender is a player, teleport him!
				if(sender instanceof Player) {
					Player p = (Player) sender;
					p.teleport(world.getSpawnLocation());
					p.sendMessage(ChatColor.GREEN + "The world has been succesfully generated! You have been teleported.");
				}
				
			} else if(args[0].toString().equalsIgnoreCase("teleport") ||
					args[0].toString().equalsIgnoreCase("tp") ||
					args[0].toString().equalsIgnoreCase("warp")) {
				
				// Check permission
				if(sender instanceof Player) {
					if(!getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.teleport", sender.isOp())) {
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
				if(sender instanceof Player) { } else {
					sender.sendMessage(ChatColor.DARK_RED + "You need to be in-game to use this command!");
					return true;
				}
				
				// Get the player and the world name
				Player p = (Player) sender;
				String w = args[1].toString();
				
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
					if(!getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.listworlds", sender.isOp())) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				sender.sendMessage(ChatColor.YELLOW + "==========[ DUNGEON MAZE WORLDS ]==========");
				List<String> worlds = DMWorldManager.getDMWorlds();
				if(worlds.size() > 0) {
					for(String w : worlds) {
						if(DMWorldManager.isLoadedDMWorld(w))
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
					if(!getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.reload")) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				log.info("[DungeonMaze] Reloading plugin...");
				sender.sendMessage(ChatColor.YELLOW + "Reloading Dungeon Maze");
				
				// Setup permissions
				setUpPermissionsManager();
				
				// Reload configs and worlds
				loadConfig();
				getDMWorldManager();
				DMWorldManager.preloadWorlds();
				
				// Show a succes message
				log.info("[DungeonMaze] Dungeon Maze has been reloaded!");
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
					if(!getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.reloadpermissions")) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				// Setup permissions
				setUpPermissionsManager();
				
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
					if(!DungeonMaze.instance.getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.checkupdates")) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				// Setup permissions
				sender.sendMessage(ChatColor.GREEN + "Checking for updates...");
				
				// Get the update checker and refresh the updates data
				DMUpdateChecker uc = getUpdateChecker();
				uc.refreshUpdatesData();
				
				if(!uc.isNewVersionAvailable()) {
					sender.sendMessage(ChatColor.GREEN + "No new version found!");
				} else {
					
					String newVer = uc.getNewestVersion();
					
					// Make sure the new version is compatible with the current bukkit version
					if(!uc.isNewVersionCompatibleWithCurrentBukkit()) {
						sender.sendMessage(ChatColor.GREEN + "New Dungeon Maze version available: v" + String.valueOf(newVer));
						sender.sendMessage(ChatColor.GREEN + "The new version is not compatible with your Bukkit version!");
						sender.sendMessage(ChatColor.GREEN + "Please update your Bukkkit to " +  uc.getRequiredBukkitVersion() + " or higher!");
					} else {
						if(uc.isUpdateDownloaded())
							sender.sendMessage(ChatColor.GREEN + "New version installed (v" + String.valueOf(newVer) + "). Server reload required!");
						else {
							sender.sendMessage(ChatColor.GREEN + "New version found: " + String.valueOf(newVer));
							sender.sendMessage(ChatColor.GREEN + "Use " + ChatColor.GOLD + "/dm installupdate" +
									ChatColor.GREEN + " to automaticly install the new version!");
						}
					}
					return true;
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
					if(!getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.installupdate")) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				// Setup permissions
				sender.sendMessage(ChatColor.GREEN + "Checking for updates...");
				
				// Get the update checker and refresh the updates data
				DMUpdateChecker uc = getUpdateChecker();
				uc.refreshUpdatesData();
				
				if(!uc.isNewVersionAvailable()) {
					sender.sendMessage(ChatColor.GREEN + "No new version available!");
				} else {
					
					String newVer = uc.getNewestVersion();
					
					// Make sure the new version is compatible with the current bukkit version
					if(!uc.isNewVersionCompatibleWithCurrentBukkit()) {
						sender.sendMessage(ChatColor.GREEN + "New Dungeon Maze version available: v" + String.valueOf(newVer));
						sender.sendMessage(ChatColor.GREEN + "The new version is not compatible with your Bukkit version!");
						sender.sendMessage(ChatColor.GREEN + "Please update your Bukkkit to " +  uc.getRequiredBukkitVersion() + " or higher!");
					} else {
						if(uc.isUpdateDownloaded())
							sender.sendMessage(ChatColor.GREEN + "New version already downloaded (v" + String.valueOf(newVer) + "). Server reload required!");
						else {
							sender.sendMessage(ChatColor.GREEN + "Downloading new version (v" + String.valueOf(newVer) + ")");
							uc.downloadUpdate();
							sender.sendMessage(ChatColor.GREEN + "Update downloaded, server reload required!");
						}
					}
					return true;
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
				newWorld.generator(dmGenerator);
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
	
	@SuppressWarnings("unchecked")
	public void loadConfig() {
		config = new DMConfig();
		unloadWorldsOnPluginDisable = config.getBoolean("unloadWorldsOnPluginDisable", true);
		allowSurface = config.getBoolean("allowSurface", true);
		worldProtection = config.getBoolean("worldProtection", false);
		enableUpdateCheckerOnStartup = config.getBoolean("updateChecker.enabled", true);
		usePermissions = config.getBoolean("usePermissions", true);
		useBypassPermissions = config.getBoolean("useBypassPermissions", true);
		blockWhiteList = (List<Object>) config.getList("blockWhiteList");
		mobs = config.getStringList("mobs");
	}
	
	@Override
	public FileConfiguration getConfig() {
		return config;
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return this.dmGenerator;
	}
	
	public ChunkGenerator getDMWorldGenerator() {
		return this.dmGenerator;
	}
	
	public boolean isAnyPlayerOnline() {
		return (getServer().getOnlinePlayers().length > 0);
	}
	
	public Player[] countOnlinePlayers() {
		return getServer().getOnlinePlayers();
	}
	
	
	
	// TODO: Put all this codeb below in a manager class to handle all the hard stuff, and to clean up the code.
	// TODO: Also save this data into the data folder of the world files so it can be read if needed
	// Getters and setters for the two lists with constant chunks and constant rooms
	public void registerConstantChunk(String world, Chunk chunk) {
		registerConstantChunk(world, chunk.getX(), chunk.getZ());
	}
	
	public void registerConstantChunk(String world, int chunkX, int chunkZ) {
		if (lastWorld != world) {
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

	public void setAPI(DungeonMazeAPI dmAPI) {
		this.dmAPI = dmAPI;
	}

	public DungeonMazeAPI getDmAPI() {
		return dmAPI;
	}

	public String getVersion() {
		return getDescription().getVersion();
	}
}
