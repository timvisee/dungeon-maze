package com.timvisee.dungeonmaze;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DMConfig extends DMCustomConfig {

	private static DMConfig config;

	public DMConfig() {
		super(new File("./plugins/DungeonMaze/config.yml"));
		config = this;
		load();
		save();
		mergeConfig();
	}

	private void mergeConfig() {
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
		if(!contains("updateChecker.notifyForUpdatesInGame"))
			set("updateChecker.notifyForUpdatesInGame", true);
		if(!contains("updateChecker.autoInstallUpdates"))
			set("updateChecker.autoInstallUpdates", true);
		if(!contains("usePermissions"))
			set("usePermissions", true);
		if(!contains("useBypassPermissions"))
			set("useBypassPermissions", true);
		if(!contains("mobs")) {
			List<String> mobs = new ArrayList<String>();
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

	public static DMConfig getInstance() {
        if (config == null) {
            config = new DMConfig();
        }        
        return config;
    }
}
