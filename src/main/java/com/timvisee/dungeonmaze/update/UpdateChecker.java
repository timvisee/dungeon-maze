package com.timvisee.dungeonmaze.update;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.update.universal.Updater;

import java.io.File;

public class UpdateChecker {

    /** Used update checker type, or null if none. */
    private UpdateCheckerType type = null;

    /** Universal updater instance if started. */
    private Updater updaterUniversal = null;
    /** Bukkit updater instance if started. */
    private com.timvisee.dungeonmaze.update.bukkit.Updater updaterBukkit = null;

    /**
     * Constructor.
     */
    public UpdateChecker() { }

    /**
     * Start the update checker of the specified type.
     *
     * @param type Update checker type.
     */
    public void start(UpdateCheckerType type) {
        // Set the updater type
        this.type = type;

        // Start the proper updater
        switch(type) {
        case BUKKIT:
            startBukkit();
            break;

        case UNIVERSAL:
        default:
            startUniversal();
        }

        // TODO: Validate that the updater has started correctly?
    }

    /**
     * Start the universal updater.
     */
    private void startUniversal() {
        // Set up the universal updater
        this.updaterUniversal = new Updater();
    }

    /**
     * Start the Bukkit updater.
     */
    private void startBukkit() {
        // TODO: Implement this code!

        // Get the plugin JAR
        File pluginJar = new File(DungeonMaze.instance.getDataFolder().getParentFile(), "DungeonMaze.jar");

        // Set up the update checker
        this.updaterBukkit = new com.timvisee.dungeonmaze.update.bukkit.Updater(DungeonMaze.instance, 45175, pluginJar, com.timvisee.dungeonmaze.update.bukkit.Updater.UpdateType.DEFAULT, true);

        // TODO: Do some error checking on the updater!
    }

    /**
     * Get the used update checker type.
     *
     * @return Update checker type, or null if no updater is loaded.
     */
    public UpdateCheckerType getType() {
        return this.type;
    }

    /**
     * Get the universal updater instance if set.
     *
     * @return Universal updater instance.
     */
    public Updater getUpdaterUniversal() {
        return this.updaterUniversal;
    }

    /**
     * Get the Bukkit updater instance if set.
     *
     * @return Bukkit updater instance.
     */
    public com.timvisee.dungeonmaze.update.bukkit.Updater getUpdaterBukkit() {
        return this.updaterBukkit;
    }
}
