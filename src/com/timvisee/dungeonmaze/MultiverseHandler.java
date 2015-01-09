package com.timvisee.dungeonmaze;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MultiverseHandler {

    /** Multiverse core instance. */
    public MultiverseCore multiverseCore;

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
        // TODO: Rehook if we're already hooked?

        // Try to get the multiverse plugin instance
        Plugin multiversePlugin = Bukkit.getPluginManager().getPlugin("Multiverse-Core");

        // Make sure any plugin instance was found
        if(multiversePlugin == null) {
            // Show a status message
            Core.getLogger().info("[DungeonMaze] Multiverse not detected! Disabling Multiverse usage!");
            this.multiverseCore = null;
            return true;
        }

        try {
            // Try to cast the plugin instance to a multiverse core instance and set the multiverse instance
            this.multiverseCore = (MultiverseCore) multiversePlugin;

        } catch(Exception ex) {
            // An error occurred, disable multiverse usage.
            this.multiverseCore = null;

            // Show an error message
            Core.getLogger().info("[DungeonMaze] Failed to hook into Multiverse!");
            return false;
        }

        // Show an status message
        Core.getLogger().info("[DungeonMaze] Hooked into Multiverse");
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
}
