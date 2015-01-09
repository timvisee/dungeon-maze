package com.timvisee.dungeonmaze.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.timvisee.dungeonmaze.DungeonMaze;

public class WorldManager {

	// DM worlds
	private List<String> worlds = new ArrayList<String>();
	private List<String> preloadWorlds = new ArrayList<String>();
	
	/**
	 * Refresh the list with Dungeon Maze worlds
	 */
	public void refresh() {
		// Load the list from the config
		List<String> w = DungeonMaze.instance.getConfig().getStringList("worlds");
		
			if (getMultiverseCore() != null) {
				for (World world : Bukkit.getWorlds()) {
					MultiverseCore mv = getMultiverseCore();
					MultiverseWorld mvWorld = mv.getMVWorldManager().getMVWorld(world);
					try {
						if ((mvWorld.getGenerator().contains("dungeonmaze") || mvWorld.getGenerator().contains("DungeonMaze")) && !w.contains(world.getName()))
							w.add(world.getName());
					} catch (NoClassDefFoundError e) {
					} catch (NullPointerException e) {
					}
				}
			}
		
		worlds = w;

		// Load the list from the config
		List<String> pw = DungeonMaze.instance.getConfig().getStringList("preloadWorlds");
		if(pw != null)
			preloadWorlds = pw;
		
		// Put all the DM worlds into the bukkit.yml file
		if (getMultiverseCore() == null) {
			FileConfiguration bukkitConfig = DungeonMaze.instance.getConfigFromPath(new File("bukkit.yml"));
			if(bukkitConfig != null) {
				//System.out.println("Editing bukkit.yml file...");
				for(String entry : w)
					bukkitConfig.set("worlds." + w + ".generator", entry);
				
				try {
					bukkitConfig.save(new File("bukkit.yml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				//System.out.println("Editing finished!");
			}
		}
	}
	
	/**
	 * Get all DM worlds
	 * @return all DM worlds
	 */
	public List<String> getDMWorlds() {
		return this.worlds;
	}
	
	/**
	 * Get all loaded DM worlds
	 * @return
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
	 * Get all preload worlds of DM
	 * @return all preload worlds
	 */
	public List<String> getPreloadWorlds() {
		return preloadWorlds;
	}
	
	/**
	 * Check if a world is a DM world
	 * @param w the world name
	 * @return true if the world is a DM world
	 */
	public boolean isDMWorld(String w) {
		return getDMWorlds().contains(w);
	}
	
	/**
	 * Check if a world is a loaded DM world
	 * @param w the world name
	 * @return true if the world is a loaded DM world
	 */
	public boolean isDMWorldLoaded(String w) {
		return getLoadedDMWorlds().contains(w);
	}
	
	/**
	 * Preload all 'preload' DM worlds
	 */
	public void preloadWorlds() {
		if (preloadWorlds != null) {
			for(String w : preloadWorlds) {
					WorldCreator newWorld = new WorldCreator(w);
					newWorld.generator(DungeonMaze.instance.getDMWorldGenerator());
					if (Bukkit.getWorld(w) != null)
						newWorld.createWorld();
			}
		}
	}
	
	public MultiverseCore getMultiverseCore() {
        MultiverseCore mv = (MultiverseCore) DungeonMaze.instance.getServer().getPluginManager().getPlugin("Multiverse-Core");
 
        if (mv != null)
        	if (mv.getDescription().getVersion().contains("2.5"))
        			return mv;
        return null;
    }
}
