package com.timvisee.dungeonmaze.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config extends CustomConfig {

	private static Config config;

	/**
	 * Constructor
	 */
	public Config() {
		super(new File("./plugins/DungeonMaze/config.yml"));
		config = this;
		load();
		save();
		mergeConfig();
	}

	private void mergeConfig() {
		// TODO: Load the default configuration file from the /res directory.

		if(!contains("worlds"))
			set("worlds", new ArrayList<String>());
		if(!contains("preloadWorlds"))
			set("preloadWorlds", new ArrayList<String>());
		if(!contains("unloadWorldsOnPluginDisable"))
			set("unloadWorldsOnPluginDisable", true);
		if(!contains("allowSurface"))
			set("allowSurface", true);
		if(!contains("worldProtection"))
			set("worldProtection", false);
		if(!contains("blockWhiteList"))
			set("blockWhiteList", new ArrayList<String>());
		if(!contains("updateChecker.enabled"))
			set("updateChecker.enabled", true);
		if(!contains("updateChecker.notifyInGame"))
			set("updateChecker.notifyInGame", true);
		if(!contains("usePermissions"))
			set("usePermissions", true);
		if(!contains("useBypassPermissions"))
			set("useBypassPermissions", true);
		if(!contains("mobs")) {
			List<String> mobs = new ArrayList<>();
			mobs.add("Blaze");
			mobs.add("Creeper");
			mobs.add("Enderman");
			mobs.add("Ghast");
			mobs.add("MagmaCube");
			mobs.add("Pig");
			mobs.add("PigZombie");
			mobs.add("SilverFish");
			mobs.add("Skeleton");
			mobs.add("Spider");
			mobs.add("Zombie");
			set("mobs", mobs);
		}
		
		this.save();
	}

	public static Config getInstance() {
        if (config == null)
            config = new Config();
            
        return config;
    }
}
