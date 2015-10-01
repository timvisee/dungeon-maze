package com.timvisee.dungeonmaze.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigUtils {

    /**
     * Get a configuration file by it's path.
     *
     * @param file The path to get the configuration file from.
     *
     * @return The configuration file instance, or null on failure.
     */
    public static FileConfiguration getConfigFromPath(File file) {
        // The file param may not be null
        if(file == null)
            return null;

        // Get and return the config from an external file
        return YamlConfiguration.loadConfiguration(file);
    }
}
