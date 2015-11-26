package com.timvisee.dungeonmaze.util;

import org.bukkit.Bukkit;

import java.net.URL;

public class PluginUtils {

    /**
     * Get the URL to the JAR file of the plugin.
     *
     * @return URL.
     */
    public static URL getPluginFileUrl() {
        return Bukkit.getPluginManager().getPlugin("PluginName").getClass().getProtectionDomain().getCodeSource().getLocation();
    }
}
