package com.timvisee.dungeonmaze.plugin.multiverse;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class MultiverseHandler {

    /** Multiverse core instance. */
    public MultiverseCore multiverseCore;

    /** Defines the required Multiverse version. */
    private final static String REQUIRED_MULTIVERSE_VERSION = "2.5";

    /**
     * Constructor.
     */
    public MultiverseHandler(boolean hook) {
        // Should the handler hook immediately
        // TODO: Do some error handling
        if(hook)
            hook();
    }

    /**
     * Try to hook into Multiverse.
     *
     * @return True on success, false if an error occurred.
     * True will also be returned if the handler didn't hook because Multiverse wasn't found.
     */
    public boolean hook() {
        // TODO: Try to re-hook if we're already hooked?

        // Try to get the multiverse plugin instance
        Plugin multiversePlugin = Bukkit.getPluginManager().getPlugin("Multiverse-Core");

        // Make sure any plugin instance was found
        if(multiversePlugin == null) {
            // Show a status message
            Core.getLogger().info("Multiverse-Core not detected! Disabling Multiverse usage!");
            this.multiverseCore = null;
            return true;
        }

        // Make sure the plugin is loaded/enabled before hooking
        if(!multiversePlugin.isEnabled()) {
            // Show a status message
            Core.getLogger().info("Multiverse-Core isn't loaded yet! Waiting for Multiverse...");
            this.multiverseCore = null;

            // Start a scheduled task to re-hook Multiverse after start
            Bukkit.getScheduler().scheduleSyncDelayedTask(DungeonMaze.instance, new Runnable() {
                @Override
                public void run() {
                    // Try to hook Multiverse again and show a status message
                    Core.getLogger().debug("Trying to hook into Multiverse-Core again...");
                    hook();
                }
            });

            // Return the result
            return true;
        }

        // Set the multiverse core instance and make sure it's
        try {
            // Get the multiverse core instance
            MultiverseCore multiverseCore = (MultiverseCore) multiversePlugin;

            // Get  the multiverse plugin and required version number
            Version multiverseVersion = new Version(multiverseCore.getDescription().getVersion());
            Version requiredVersion = new Version(MultiverseHandler.REQUIRED_MULTIVERSE_VERSION);

            // Make sure the multiverse core version is acceptable, if not, return false
            if(multiverseVersion.compareTo(requiredVersion) > 0) {
                // Show an error message
                Core.getLogger().info("Failed to hook into Multiverse-Core, version not compatible!");
                return false;
            }

            // Try to cast the plugin instance to a multiverse core instance and set the multiverse instance
            this.multiverseCore = multiverseCore;

        } catch(Exception ex) {
            // An error occurred, disable multiverse usage.
            this.multiverseCore = null;

            // Show an error message
            Core.getLogger().info("Failed to hook into Multiverse-Core!");
            return false;
        }

        // Show an status message
        Core.getLogger().info("Hooked into Multiverse-Core");
        return true;
    }

    /**
     * Check whether the handler is successfully hooked into the multiverse core.
     *
     * @return True if hooked, false if not.
     */
    public boolean isHooked() {
        return this.multiverseCore != null;
    }

    /**
     * Unhook from the multiverse core.
     *
     * @return True if the handler unhooked from the multiverse core, false otherwise. True will also be returned if the
     * handler wasn't hooked yet.
     */
    public boolean unhook() {
        // Make sure the handler is hooked
        if(!isHooked())
            return true;

        // TODO: Unhook properly!

        // Unhook from the multiverse core
        this.multiverseCore = null;

        // Return the result
        return !isHooked();
    }

    /**
     * Get the multiverse core.
     *
     * @return The multiverse core instance, or null if the handler isn't hooked yet.
     */
    public MultiverseCore getMultiverseCore() {
        // Make sure the handler is hooked
        if(!isHooked())
            return null;

        // Return the multiverse core instance.
        return this.multiverseCore;
    }

    /**
     * Get the plugin description file of the hooked Multiverse plugin.
     *
     * @return The plugin description file, or null on failure. Null will also be returned if the handler isn't
     * successfully hooked into Multiverse.
     */
    public PluginDescriptionFile getPluginDescription() {
        // Make sure the handler is hooked
        if(!isHooked())
            return null;

        // Get and return the plugin description
        return this.multiverseCore.getDescription();
    }

    /**
     * Get the version number of the hooked Multiverse plugin.
     *
     * @return The version number, or null on failure. Null will also be returned if the handler isn't
     * successfully hooked into Multiverse.
     */
    @SuppressWarnings("UnusedDeclaration")
    public String getPluginVersion() {
        // Make sure the handler is hooked
        if(!isHooked())
            return null;

        // Get and return the plugins version number
        return getPluginDescription().getVersion();
    }
}
