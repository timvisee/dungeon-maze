package com.timvisee.dungeonmaze.config;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config extends CustomConfig {

	/**
	 * The name of the configuration file.
	 */
	public static final String CONFIG_FILE = "config.yml";

	/**
	 * The static configuration instance.
	 */
	private static Config config;

	/**
	 * Constructor
	 */
	public Config() {
		super(new File("./plugins/DungeonMaze/config.yml"));
		config = this;

		load();

		// Merge the default configuration with the current
		mergeConfig();
	}

	/**
	 * Merge the current active configuration with the default.
	 *
	 * All keys available in the default configuration that are not in the current configuration, are added automatically.
	 */
	private void mergeConfig() {
        // Create an input stream of the defaults
		final InputStream in = getClass().getResourceAsStream("/" + CONFIG_FILE);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		// Load the defaults, and set them
		final YamlConfiguration defaults = YamlConfiguration.loadConfiguration(reader);
		setDefaults(defaults);

		// Loop through each default configuration key
		int changedProperties = 0;
		for(String key : defaults.getKeys(true))
			if(!contains(key, true)) {
				set(key, defaults.get(key));
				changedProperties++;
			}

		// Return early if nothing has changed
		if(changedProperties == 0)
			return;

        Core.getLogger().info("Set " + changedProperties + " missing configuration properties to their defaults.");

		Core.getLogger().debug("Saving configuration...");
		this.save();
	}

	/**
	 * Get the singleton configuration instance
	 *
	 * @return Configuration.
	 */
	public static Config getInstance() {
	    return config != null ? config : new Config();
    }
}
