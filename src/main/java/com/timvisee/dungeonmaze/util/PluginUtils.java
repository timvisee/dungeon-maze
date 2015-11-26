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
        return Bukkit.getPluginManager().getPlugin("PluginName").getClass().getProtectionDomain().getCodeSource().getLocation();
    }

    /**
     * Get the JAR file of the plugin.
     * Note: The use of the getPluginFileUrl method is recommended if possible.
     *
     * @return Plugin file.
     */
    public static File getPluginFile() {
        return new File(DungeonMaze.instance.getDataFolder().getParentFile(), "DungeonMaze.jar");
    }
}
