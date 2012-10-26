package com.timvisee.DungeonMaze;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;

public class DMWorldManager {
	public static DungeonMaze plugin;

	public DMWorldManager(DungeonMaze instance) {
		plugin = instance;
	}

	// DM worlds
	private List<String> worlds = new ArrayList<String>();
	private List<String> preloadWorlds = new ArrayList<String>();
	
	/**
	 * Refresh the list with Dungeon Maze worlds
	 */
	public void refresh() {
		// Load the list from the config
		List<String> w = plugin.getConfig().getStringList("worlds");
		if(w != null)  {
			// Get DM worlds from Dungeon Maze
			if(plugin.useMultiverse) {
				Collection<MultiverseWorld> mvworlds = plugin.multiverseCore.getMVWorldManager().getMVWorlds();
				if(mvworlds != null) {
					for(MultiverseWorld mvw : mvworlds) {
						if(mvw.getCBWorld().getGenerator().equals(plugin.getDMWorldGenerator()))
							if(!w.contains(w.add(mvw.getCBWorld().getName())))
								w.add(mvw.getCBWorld().getName());
					}
				}
			}
			this.worlds = w;
		}
		
		// Load the list from the config
		List<String> pw = plugin.getConfig().getStringList("preloadWorlds");
		if(pw != null) {
			this.preloadWorlds = pw;
		}
		
		// Put all the DM worlds into the bukkit.yml file
		System.out.println("Editing bukkit.yml file...");
		FileConfiguration bukkitConfig = plugin.getConfigFromPath(new File("bukkit.yml"));
		if(bukkitConfig != null) {
			for(String entry : w) {
				bukkitConfig.set("worlds." + w + ".generator", entry);
			}
			try {
				bukkitConfig.save(new File("bukkit.yml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Editing finished!");
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
		
		for(String entry : this.worlds) {
			World w = plugin.getServer().getWorld(entry);
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
		return this.preloadWorlds;
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
	public boolean isLoadedDMWorld(String w) {
		return getLoadedDMWorlds().contains(w);
	}
	
	/**
	 * Preload all 'preload' DM worlds
	 */
	public void preloadWorlds() {
		for(String w : this.preloadWorlds) {
			WorldCreator newWorld = new WorldCreator(w);
			newWorld.generator(plugin.getDMWorldGenerator());
			newWorld.createWorld();
		}
	}
}
