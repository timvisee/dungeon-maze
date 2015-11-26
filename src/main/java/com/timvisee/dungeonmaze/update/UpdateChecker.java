package com.timvisee.dungeonmaze.update;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.update.universal.Updater;
import com.timvisee.dungeonmaze.util.PluginUtils;

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

        // Get the plugin JAR file
        File pluginJar = PluginUtils.getPluginFile();

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

    /**
     * Check whether an update check is currently being done.
     *
     * @return True if an update check is happening, false if not.
     */
    public boolean isChecking() {
        return false;
    }

    /**
     * Check whether an update check has been done recently.
     *
     * @return True if an update check has been done, false if not.
     */
    public boolean hasChecked() {
        return false;
    }

    /**
     * Check whether the last update check has failed.
     *
     * @return True if failed, false if not.
     */
    public boolean hasFailed() {
        return false;
    }

    /**
     * Check whether a new update is available.
     *
     * @return True if a new update is available, false otherwise.
     */
    public boolean isUpdateAvailable() {
        return false;
    }

    /**
     * Get the version name of the available update. The current version name is returned if no new version is available.
     *
     * @return Version name.
     */
    public String getUpdateVersionName() {
        // TODO: Return the version number of the new update!
        return DungeonMaze.getVersionName();
    }

    /**
     * Get the version code of the available update. The current version code is returned if now new version is available.
     * If the version code is unknown, while a new version is indeed available. The current version number, incremented by one is returned.
     *
     * @return Version code.
     */
    public int getUpdateVersionCode() {
        // TODO: Return the version code of the new update!
        return DungeonMaze.getVersionCode();
    }
}
