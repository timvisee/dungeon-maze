package com.timvisee.dungeonmaze.listener;

import com.timvisee.dungeonmaze.DungeonMaze;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;

public class EventListenerManager {

    /** Defines whether the event listener is initialized. */
    private boolean init = false;

    /** Block listener instance. */
    private BlockListener blockListener;
    /** Player listener instance. */
    private PlayerListener playerListener;
    /** Plugin listener instance. */
    private PluginListener pluginListener;
    /** World listener instance. */
    private WorldListener worldListener;

    /**
     * Constructor.
     *
     * @param init True to immediately initialize.
     */
    public EventListenerManager(boolean init) {
        // Initialize the event listener manager
        // TODO: Do some error handling
        if(init)
            init(false);
    }

    /**
     * Initialize the event listener manager.
     *
     * @param registerListeners True to register the event listeners immediately, false otherwise.
     *
     * @return True on success, false on failure. True will also be returned if the manager was initialized already.
     */
    public boolean init(boolean registerListeners) {
        // Make sure the manager isn't initialized already
        if(this.isInit())
            return true;

        // Instantiate the listeners
        this.blockListener = new BlockListener();
        this.playerListener = new PlayerListener();
        this.pluginListener = new PluginListener();
        this.worldListener = new WorldListener();

        // Set whether the manager is initialized
        this.init = true;

        // Register the event listeners if needed, return false on failure
        if(registerListeners)
            if(!this.registerListeners())
                return false;

        // Return the result
        return true;
    }

    /**
     * Check whether the event listener manager is initialized.
     *
     * @return True if initialized, false otherwise.
     */
    public boolean isInit() {
        return this.init;
    }

    /**
     * Destroy the event listener manager.
     *
     * @return True if the manager was successfully destroyed, false otherwise. True will also be returned if the
     * manager wasn't initialized.
     */
    public boolean destroy() {
        // Make sure the manager is initialized
        if(!this.isInit())
            return true;

        // Unregister all listeners, if any are registered
        unregisterListeners();

        // Set whether the manager is initialized, return the result
        this.init = false;
        return true;
    }

    /**
     * Register all event listeners.
     *
     * @return Register all event listeners.
     */
    public boolean registerListeners() {
        // Make sure the manager is initialized
        if(!this.isInit())
            return false;

        // Get the plugin manager
        PluginManager pm = Bukkit.getPluginManager();

        // Register the events
        pm.registerEvents(this.blockListener, DungeonMaze.instance);
        pm.registerEvents(this.playerListener, DungeonMaze.instance);
        pm.registerEvents(this.pluginListener, DungeonMaze.instance);
        pm.registerEvents(this.worldListener, DungeonMaze.instance);
        return true;
    }

    /**
     * Get a list of registered Dungeon Maze event listeners.
     *
     * @return A list of registered Dungeon Maze event listeners.
     */
    public ArrayList<RegisteredListener> getRegisteredListeners() {
        return HandlerList.getRegisteredListeners(DungeonMaze.instance);
    }

    /**
     * Unregister all event listeners.
     */
    public void unregisterListeners() {
        // Unregister all listeners
        HandlerList.unregisterAll(DungeonMaze.instance);
    }

    /**
     * Get the block listener.
     *
     * @return Block listener instance.
     */
    public BlockListener getBlockListener() {
        return this.blockListener;
    }

    /**
     * Get the player listener.
     *
     * @return Player listener instance.
     */
    public PlayerListener getPlayerListener() {
        return this.playerListener;
    }

    /**
     * Get the plugin listener.
     *
     * @return Plugin listener instance.
     */
    public PluginListener getPluginListener() {
        return this.pluginListener;
    }

    /**
     * Get the world listener.
     *
     * @return World listener instance.
     */
    public WorldListener getWorldListener() {
        return this.worldListener;
    }
}
