package com.timvisee.DungeonMaze;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
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

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.timvisee.DungeonMaze.Metrics.Graph;

public class DungeonMaze extends JavaPlugin {	
	public static final Logger log = Logger.getLogger("Minecraft");
	private static final DungeonMazeGenerator dmGenerator = new DungeonMazeGenerator();

	private final DungeonMazeBlockListener blockListener = new DungeonMazeBlockListener(this);
	private final DungeonMazePlayerListener playerListener = new DungeonMazePlayerListener(this);
	
	FileConfiguration config;
	
	public static String lastWorld = "";
	public static List<String> constantRooms = new ArrayList<String>(); // x;y;z
	public static List<String> constantChunks = new ArrayList<String>(); // x;z
	
	// Update Checker
	boolean isUpdateAvailable = false;
	String newestVersion = "1.0";
	
	/* Multiverse */
	boolean useMultiverse = false;
	MultiverseCore multiverseCore;
	
	/* Permissions */
	/*
	 * 0 = none
	 * 1 = PermissionsEx
	 * 2 = PermissionsBukkit
	 * 3 = bPermissions
	 * 4 = Essentials Group Manager
	 * 5 = Permissions
	 */
	private int permissionsSystem = 0;
	private PermissionManager pexPermissions;
	private PermissionHandler defaultPermsissions;
	private GroupManager groupManagerPermissions;
	
	// Worlds which use Dungeon Maze
	List<String> dmWorlds = new ArrayList<String>();
	List<String> dmPreloadWorlds = new ArrayList<String>();
	
	@Override
	public void onEnable() {
		// Check if all the config file exists
		try {
			checkConigFilesExist();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		// Load the config file
		loadConfig();
		
		// Refresh the list with Dungeon Maze worlds
		getDungeonMazeWorlds();
		getDungeonMazePreloadWorlds();
		
		// Preload all the Dungeon Maze worlds
		loadWorlds();

		// Setup permissions usage
		setupPermissions();
		
		// Setup multiverse usage
		setupMultiverse();
		
		// Register all event listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.blockListener, this);
		pm.registerEvents(this.playerListener, this);
		
		// Show a startup message
		PluginDescriptionFile pdfFile = getDescription();
		log.info("[DungeonMaze] Dungeon Maze v" + pdfFile.getVersion() + " Started");
		log.info("[DungeonMaze] Dungeon Maze made by Tim Visee - timvisee.com");

		// Setup Metrics
		setupMetrics();
		
		// Enable update checker on startup if it's enabled
		if(getConfig().getBoolean("enableUpdateCheckerOnStartup", true)) {
			checkUpdates();
		}
	}
	
	@Override
	public void onDisable() {
		// Unload all Dungeon Maze worlds if it's enabled
		if(config.getBoolean("unloadWorldsOnPluginDisable", true)) {
			if(config.getStringList("worlds").size() > 0) {
				// Dungeon Maze does have some worlds
				log.info("[DungeonMaze] Unloading Dungeon Maze worlds...");
				
				// Unload the Dungeon Maze worlds
				List<String> worlds = new ArrayList<String>();
				for(World w : getServer().getWorlds()) {
					if(config.getStringList("worlds").contains(w.getName())) {
						worlds.add(w.getName());
					}
				}
				for(String w : worlds) {
					getServer().unloadWorld(w, true);
				}
				
				log.info("[DungeonMaze] All Dungeon Maze worlds have been unloaded!");
			} else {
				log.info("[DungeonMaze] No Dungeon Maze worlds to unload avaiable");
			}
		} else {
			log.info("[DungeonMaze] Unloading worlds has been disabled!");
		}
		
		// Show an disabled message
		log.info("[DungeonMaze] Dungeon Maze Disabled");
	}
	
	public void checkConigFilesExist() throws Exception {
		// Check if the config files exist
		if(!getDataFolder().exists()) {
			log.info("[DungeonMaze] Creating default files");
			getDataFolder().mkdirs();
		}
		File f = new File(getDataFolder(), "config.yml");
		if(!f.exists()) {
			log.info("[DungeonMaze] Generating new config file");
			copy(getResource("res/defaultFiles/DungeonMaze/config.yml"), f);
		}
	}
	
	private void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Setup the metrics statics feature
	 * @return false if an error occurred
	 */
	public boolean setupMetrics() {
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
	            		if(dmWorlds.contains(p.getWorld().getName()))
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
	
	public boolean checkUpdates() {
		// Check for new updates
		DungeonMazeUpdateChecker scuc = new DungeonMazeUpdateChecker(this);
		isUpdateAvailable = scuc.checkUpdates();
		newestVersion = scuc.getLastVersion();
		
		if(isUpdateAvailable) {
			// A new update is available, print a message in the console
			log.info("[DungeonMaze] New version available, version " + newestVersion + ".");
		}
		
		return isUpdateAvailable;
	}
	
	private void setupMultiverse() {
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
	
	private void setupPermissions() {
		// Setup the permissions systems
		// Reset permissions
		permissionsSystem = 0;
		
		if(!getConfig().getBoolean("usePermissions", true)) {
			permissionsSystem = 0;
			System.out.println("[DungeonMaze] Permissions usage disabled in config file!");
			if(useBypassPermissions()) {
				System.out.println("[DungeonMaze] Bypass permissions auto disabled!");
			} else {
				System.out.println("[DungeonMaze] Bypass permissions disabled!");
			}
			return;
		}
		
		// Check PermissionsEx system
		Plugin testPex = this.getServer().getPluginManager().getPlugin("PermissionsEx");
		if(testPex != null) {
			pexPermissions = PermissionsEx.getPermissionManager();
			if(pexPermissions != null) {
				permissionsSystem = 1;
				
				System.out.println("[DungeonMaze] Hooked into PermissionsEx!");
				if(useBypassPermissions()) {
					System.out.println("[DungeonMaze] Bypass permissions enabled!");
				} else {
					System.out.println("[DungeonMaze] Bypass permissions disabled!");
				}
				return;
			}
		}
		
		// Check PermissionsBukkit system
		Plugin testBukkitPerms = this.getServer().getPluginManager().getPlugin("PermissionsBukkit");
		if(testBukkitPerms != null) {
			permissionsSystem = 2;
			System.out.println("[DungeonMaze] Hooked into PermissionsBukkit!");
			if(useBypassPermissions()) {
				System.out.println("[DungeonMaze] Bypass permissions enabled!");
			} else {
				System.out.println("[DungeonMaze] Bypass permissions disabled!");
			}
			return;
		}
		
		// Check bPermissions system
		// Not available yet!
		
		// Check Essentials Group Manager system
		final PluginManager pluginManager = getServer().getPluginManager();
		final Plugin GMplugin = pluginManager.getPlugin("GroupManager");
		if (GMplugin != null && GMplugin.isEnabled())
		{
			permissionsSystem = 4;
			groupManagerPermissions = (GroupManager)GMplugin;
            System.out.println("[DungeonMaze] Hooked into Essentials Group Manager!");
			if(useBypassPermissions()) {
				System.out.println("[DungeonMaze] Bypass permissions enabled!");
			} else {
				System.out.println("[DungeonMaze] Bypass permissions disabled!");
			}
            return;
		}
		
		// Check Permissions system
	    Plugin testPerms = this.getServer().getPluginManager().getPlugin("Permissions");
	    if (this.defaultPermsissions == null) {
	        if (testPerms != null) {
	        	permissionsSystem = 5;
	            this.defaultPermsissions = ((Permissions) testPerms).getHandler();
	            System.out.println("[DungeonMaze] Hooked into Permissions!");
	            return;
	        }
	    }
	    
	    // None of the permissions systems worked >:c.
	    permissionsSystem = 0;
	    System.out.println("[DungeonMaze] No Permissions system found! Permissions disabled!");
		if(useBypassPermissions()) {
			System.out.println("[DungeonMaze] Bypass permissions auto disabled!");
		} else {
			System.out.println("[DungeonMaze] Bypass permissions disabled!");
		}
	}
	
	public List<String> getDungeonMazeWorlds() {
		// Load the list from the config
		List<String> worlds = config.getStringList("worlds");
		if(worlds == null) 
			return new ArrayList<String>();
		
		// Get DM worlds from Dungeon Maze
		if(useMultiverse) {
			Collection<MultiverseWorld> mvworlds = multiverseCore.getMVWorldManager().getMVWorlds();
			if(mvworlds != null) {
				for(MultiverseWorld mvw : mvworlds) {
					if(mvw.getCBWorld().getGenerator().equals(dmGenerator))
						if(!worlds.contains(worlds.add(mvw.getCBWorld().getName())))
							worlds.add(mvw.getCBWorld().getName());
				}
			}
		}
		dmWorlds = worlds;
		return worlds;
	}
	
	public List<String> getDungeonMazePreloadWorlds() {
		// Load the list from the config
		List<String> worlds = config.getStringList("preloadWorlds");
		if(worlds == null) 
			return new ArrayList<String>();
		dmPreloadWorlds = dmWorlds;
		return worlds;
	}
	
	public boolean usePermissions() {
		if(getConfig().getBoolean("usePermissions", true)) {
			return true;
		}
		return false;
	}
	
	public boolean useBypassPermissions() {
		if(getConfig().getBoolean("useBypassPermissions", false)) {
			return true;
		}
		return false;
	}
	
	public boolean isPermissionsSystemEnabled() {
		if(permissionsSystem == 0) {
			return false;
		}
		return true;
	}
	
	public int getPermissionsSystem() {
		return permissionsSystem;
	}
	
	public boolean hasPermission(Player player, String permissionNode) {
		return hasPermission(player, permissionNode, player.isOp());
	}
	
	public boolean hasPermission(Player player, String permissionNode, boolean def) {
		if(!usePermissions()) {
			return def;
		}
		if(!isPermissionsSystemEnabled()) {
			return def;
		}
		
		// Using PermissionsEx
		if(getPermissionsSystem() == 1) {
			PermissionUser user  = PermissionsEx.getUser(player);
			return user.has(permissionNode);
		}
		
		// Using PermissionsBukkit
		if(getPermissionsSystem() == 2) {
			return player.hasPermission(permissionNode);
		}
		
		// Using bPemissions
		// Comming soon!
		
		// Using Essentials Group Manager
		if(getPermissionsSystem() == 4) {
			final AnjoPermissionsHandler handler = groupManagerPermissions.getWorldsHolder().getWorldPermissions(player);
			if (handler == null)
			{
				return false;
			}
			return handler.has(player, permissionNode);
		}
		
		// Using Permissions
		if(getPermissionsSystem() == 5) {
			return this.defaultPermsissions.has(player, permissionNode);
		}

		return def;
	}
	
	public void loadWorlds() {
		// Edit the server config file
		System.out.println("Editing bukkit.yml file...");
		FileConfiguration serverConfig = getConfigFromPath(new File("bukkit.yml"));
		List<String> worlds = getDungeonMazeWorlds();
		if(serverConfig != null) {
			for(String w : worlds) {
				serverConfig.set("worlds." + w + ".generator", "DungeonMaze");
			}
			try {
				serverConfig.save(new File("bukkit.yml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Editing finished!");
		}
		
		// Preload worlds
		List<String> preloadWorlds = getDungeonMazePreloadWorlds();
		for(String w : preloadWorlds) {
			WorldCreator newWorld = new WorldCreator(w);
			newWorld.generator(dmGenerator);
			newWorld.createWorld();
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("dungeonmaze") || commandLabel.equalsIgnoreCase("dm")) {
			if(args.length == 0) {
				sender.sendMessage(ChatColor.DARK_RED + "Unknown command!");
				sender.sendMessage(ChatColor.GOLD + "Use the command " + ChatColor.YELLOW + "/dm createworld <name>" + ChatColor.GOLD + " to create a new Dungeon Maze world");
				return true;
			}
			
			if(args[0].toString().equalsIgnoreCase("createworld") || args[0].toString().equalsIgnoreCase("cw") || args[0].toString().equalsIgnoreCase("create")) {
				// Check permission
				if(sender instanceof Player) {
					if(!hasPermission((Player) sender, "dungeonmaze.command.createworld", sender.isOp())) {
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
				
				// Edit the server config file and the Dungeon Maze config file
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
				if(!worlds.contains(w)) {
					worlds.add(w);
				}
				config.set("worlds", worlds);
				List<String> preloadWorlds = config.getStringList("preloadWorlds");
				if(!preloadWorlds.contains(w)) {
					preloadWorlds.add(w);
				}
				config.set("preloadWorlds", preloadWorlds);
				saveConfig();
				System.out.println("Editing finished!");

				// Create the world
				WorldCreator newWorld = new WorldCreator(w);
				newWorld.generator(dmGenerator);
				World world = newWorld.createWorld();
				
				// If the sender is a player, teleport him!
				if(sender instanceof Player) {
					Player p = (Player) sender;
					p.teleport(world.getSpawnLocation());
					p.sendMessage(ChatColor.GREEN + "The world has been succesfully generated! You have been teleported.");
				}
			} else if(args[0].toString().equalsIgnoreCase("teleport") || args[0].toString().equalsIgnoreCase("tp") || args[0].toString().equalsIgnoreCase("warp")) {
				// Check permission
				if(sender instanceof Player) {
					if(!hasPermission((Player) sender, "dungeonmaze.command.teleport", sender.isOp())) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				if(args.length != 2) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}
				
				if(sender instanceof Player) { } else {
					sender.sendMessage(ChatColor.DARK_RED + "You need to be in-game to use this command!");
					return true;
				}
				
				// Get the world name
				String w = args[1].toString();
				
				if(!worldExists(w)) {
					sender.sendMessage(ChatColor.DARK_RED + w);
					sender.sendMessage(ChatColor.DARK_RED + "This world doesn't exists!");
					return true;
				}
				
				if(!worldIsLoaded(w))
					worldLoad(w);
				
				// If the sender is a player, teleport him!
				if(sender instanceof Player) {
					Player p = (Player) sender;
					p.teleport(getServer().getWorld(w).getSpawnLocation());
					p.sendMessage(ChatColor.GREEN + "You have been teleported.");
				} else
					sender.sendMessage(ChatColor.DARK_RED + "This command could only be used in-game!");
				
				return true;
				
			} else if(args[0].equalsIgnoreCase("listworlds") || args[0].equalsIgnoreCase("lw") || args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {

				// Check wrong command values
				if(args.length != 1) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}
				
				// Check permission
				if(sender instanceof Player) {
					if(!hasPermission((Player) sender, "dungeonmaze.command.listworlds", sender.isOp())) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				sender.sendMessage(ChatColor.YELLOW + "==========[ DUNGEON MAZE WORLDS ]==========");
				List<String> worlds = config.getStringList("worlds");
				if(worlds != null) {
					for(String w : worlds) {
						if(worldIsLoaded(w)) {
							sender.sendMessage(ChatColor.GOLD + " - " + w + "   " + ChatColor.GREEN + "Loaded");
						} else {
							sender.sendMessage(ChatColor.GOLD + " - " + w + "   " + ChatColor.DARK_RED + "Not Loaded");
						}
					}
				}
				return true;
				
			} else if(args[0].equalsIgnoreCase("reload")) {
				
				// Check wrong command values
				if(args.length != 1) {
					sender.sendMessage(ChatColor.DARK_RED + "Wrong command values!");sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/" + commandLabel + " help " + ChatColor.YELLOW + "to view help");
					return true;
				}
				
				// Check permission
				if(sender instanceof Player) {
					if(!hasPermission((Player) sender, "dungeonmaze.command.reload")) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				log.info("[DungeonMaze] Reloading plugin...");
				sender.sendMessage(ChatColor.YELLOW + "Reloading Dungeon Maze");
				
				// Setup permissions
				setupPermissions();
				
				// Reload configs and worlds
				loadConfig();
				loadWorlds();
				
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
					if(!hasPermission((Player) sender, "dungeonmaze.command.reloadpermissions")) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				// Setup permissions
				setupPermissions();
				
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
					if(!hasPermission((Player) sender, "dungeonmaze.command.checkupdates")) {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return true;
					}
				}
				
				// Setup permissions
				sender.sendMessage(ChatColor.YELLOW + "Checking for updates...");
				
				if(checkUpdates()) {
					sender.sendMessage(ChatColor.GREEN + "New version found! (v" + newestVersion + ")");
				} else {
					sender.sendMessage(ChatColor.YELLOW + "No new version found!");
				}
				return true;
				
			} else if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?")) {
				
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
				sender.sendMessage(ChatColor.GOLD + "/" + commandLabel + " <version/ver/v> " + ChatColor.WHITE + ": Check plugin version");
				
				return true;
			} else {
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
		for(World entry : getServer().getWorlds()) {
			if(entry.getName().equals(w))
				return true;
		}
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
		FileConfiguration c;
		
		if (file == null) {
		    return null;
		}

	    c = YamlConfiguration.loadConfiguration(file);
	    
	    return c;
	}
	
	public void loadConfig() {
		config = getConfigFromPath(new File(getDataFolder(), "config.yml"));
	}
	
	public void saveConfig() {
		try {
			config.save(new File(getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public FileConfiguration getConfig() {
		return config;
	}
	

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return dmGenerator;
	}
	
	public boolean isAnyPlayerOnline() {
		return (getServer().getOnlinePlayers().length == 0);
	}
	
	public Player[] countOnlinePlayers() {
		return getServer().getOnlinePlayers();
	}
	
	// Getters and setters for the two lists with constant chunks and constant rooms
	public static void addConstantChunk(String world, Chunk chunk) {
		addConstantChunk(world, chunk.getX(), chunk.getZ());
	}
	public static void addConstantChunk(String world, int chunkX, int chunkZ) {
		if (lastWorld != world) {
			lastWorld = world;
			constantChunks.clear();
		}
		constantChunks.add(Integer.toString(chunkX) + ";" + Integer.toString(chunkZ));
	}
	
	public static void addConstantRooms(String world, Chunk chunk, int roomX, int roomY, int roomZ) {
		addConstantRooms(world, chunk.getX(), chunk.getZ(), roomX, roomY, roomZ);
	}
	public static void addConstantRooms(String world, int chunkX, int chunkZ, int roomX, int roomY, int roomZ) {
		addConstantRooms(world, (chunkX * 16) + roomX, roomY, (chunkZ * 16) + roomZ);
	}
	public static void addConstantRooms(String world, int roomX, int roomY, int roomZ) {
		if (lastWorld != world) {
			lastWorld = world;
			constantRooms.clear();
		}
		constantRooms.add(Integer.toString(roomX) + ";" + Integer.toString(roomY) + ";" + Integer.toString(roomZ));
	}
	
	public static boolean isConstantChunk(String world, Chunk chunk) {
		return isConstantChunk(world, chunk.getX(), chunk.getZ());
	}
	public static boolean isConstantChunk(String world, int chunkX, int chunkZ) {
		if (lastWorld != world) {
			lastWorld = world;
			constantChunks.clear();
		}
		return constantChunks.contains(Integer.toString(chunkX) + ";" + Integer.toString(chunkZ));
	}
	public static boolean isConstantRoom(String world, Chunk chunk, int roomX, int roomY, int roomZ) {
		return isConstantRoom(world, chunk.getX(), chunk.getZ(), roomX, roomY, roomZ);
	}
	public static boolean isConstantRoom(String world, int chunkX, int chunkZ, int roomX, int roomY, int roomZ) {
		return isConstantRoom(world, (chunkX * 16) + roomX, roomY, (chunkZ * 16) + roomZ);
	}
	public static boolean isConstantRoom(String world, int roomX, int roomY, int roomZ) {
		if (lastWorld != world) {
			lastWorld = world;
			constantRooms.clear();
		}
		return constantRooms.contains(Integer.toString(roomX) + ";" + Integer.toString(roomY) + ";" + Integer.toString(roomZ));
	}
}
