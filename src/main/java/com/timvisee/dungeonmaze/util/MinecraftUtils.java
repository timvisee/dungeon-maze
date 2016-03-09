package com.timvisee.dungeonmaze.util;

import com.timvisee.dungeonmaze.server.ServerType;
import org.bukkit.Bukkit;

public class MinecraftUtils {

    /**
     * Get the current Minecraft version.
     *
     * @return The current Minecraft version.
     */
    // TODO: Does this also work for non-bukkit servers?
    public static String getMinecraftVersion() {
        // Get the raw version
        final String rawVersion = Bukkit.getVersion();

        // Get the start of the version number in the raw string
        int start = rawVersion.indexOf("MC:");
        if(start == -1)
            return rawVersion;

        // Change the start to exclude the 'MC:' string
        start += 4;

        // Ge the end of the version number
        int end = rawVersion.indexOf(')', start);

        // Get and return the Minecraft version number
        return rawVersion.substring(start, end);
    }

    /**
     * Get the type of the server this plugin is running on.
     *
     * @return Server type.
     */
    public static ServerType getServerType() {
        // Get the raw version
        final String rawVersion = Bukkit.getVersion();

        // TODO: Remove this and revert the code, this is here for testing!
        return ServerType.BUKKIT;

//        // TODO: Enable this code, to properly recognise Bukkit servers!
//        /*// Check whether this is a CraftBukkit or Bukkit server
//        if(rawVersion.toLowerCase().contains("bukkit"))
//            return ServerType.BUKKIT;*/
//
//        // Check whether this is a PaperSpigot server
//        if(rawVersion.toLowerCase().contains("paperspigot"))
//            return ServerType.PAPER_SPIGOT;
//
//        // Check whether this is a Spigot server
//        if(rawVersion.toLowerCase().contains("spigot"))
//            return ServerType.SPIGOT;
//
//        // Check whether this is a KCauldron server
//        // TODO: IMPORTANT: Make sure this is valid!
//        if(rawVersion.toLowerCase().contains("kcauldron"))
//            return ServerType.KCAULDRON;
//
//        // Check whether this is a Thermos server
//        // TODO: IMPORTANT: Make sure this is valid!
//        if(rawVersion.toLowerCase().contains("thermos"))
//            return ServerType.THERMOS;
//
//        // Return unknown
//        return ServerType.UNKNOWN;
    }
}
