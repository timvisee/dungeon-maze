package com.timvisee.dungeonmaze.util;

import com.timvisee.dungeonmaze.DungeonMaze;
import org.bukkit.Bukkit;

import java.io.File;
import java.net.URL;

public class PluginUtils {

    /**
     * Get the URL to the JAR file of the plugin.
     *
     * @return Plugin file URL.
     */
    public static URL getPluginFileUrl() {
        return Bukkit.getPluginManager().getPlugin("DungeonMaze").getClass().getProtectionDomain().getCodeSource().getLocation();
    }

    /**
     * Get the JAR file of the plugin.
     * Null will be returned if the plugin file couldn't be determined.
     *
     * Note: The use of the getPluginFileUrl method is recommended if possible.
     *
     * @return Plugin file, or null if it couldn't be determined.
     */
    public static File getPluginFile() {
        // Get the JAR file URL
        URL pluginFileUrl = getPluginFileUrl();

        // Get the path proportion of the URL as file name
        String fileName = pluginFileUrl.getPath();

        // Only use the file name, not the whole path
        if(fileName.contains("/"))
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);

        // Dynamically get the file of the plugin
        final File pluginFile = new File(DungeonMaze.instance.getDataFolder().getParentFile(), fileName);

        // Return the file if it exists, null otherwise
        return pluginFile.isFile() ? pluginFile : null;
    }

    /**
     * Get the directory the Dungeon Maze data is stored in. This is usually in the ./plugins/DungeonMaze directory.
     *
     * @return Data directory.
     */
    public static File getPluginDataDirectory() {
        // TODO: Validate the returned path
        return DungeonMaze.instance.getDataFolder();
    }
}
