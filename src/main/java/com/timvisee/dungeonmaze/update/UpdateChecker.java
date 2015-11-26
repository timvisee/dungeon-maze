package com.timvisee.dungeonmaze.update;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.update.universal.Updater;
import com.timvisee.dungeonmaze.util.MinecraftUtils;
import com.timvisee.dungeonmaze.util.PluginUtils;
import com.timvisee.dungeonmaze.util.SystemUtils;

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
        // TODO: Implement this!
        return false;
    }

    /**
     * Check whether an update check has been done recently.
     *
     * @return True if an update check has been done, false if not.
     */
    public boolean hasChecked() {
        // TODO: Implement this!
        return false;
    }

    /**
     * Check whether the last update check has failed.
     *
     * @return True if failed, false if not.
     */
    public boolean hasFailed() {
        // TODO: Implement this!
        return false;
    }

    /**
     * Check whether a new update is available.
     *
     * @return True if a new update is available, false otherwise.
     */
    public boolean isUpdateAvailable() {
        // Check with the correct updater
        switch(getType()) {
            case UNIVERSAL:
                // TODO: Implement this!
                return false;

            case BUKKIT:
                return getUpdaterBukkit().getResult() == com.timvisee.dungeonmaze.update.bukkit.Updater.UpdateResult.NO_UPDATE;

            default:
                return false;
        }
    }

    /**
     * Get the version name of the available update. The current version name is returned if no new version is available.
     *
     * @return Version name.
     */
    public String getUpdateVersionName() {
        // Check with the correct updater
        switch(getType()) {
            case UNIVERSAL:
                // TODO: Return the version number of the new update!
                return DungeonMaze.getVersionName();

            case BUKKIT:
                return getUpdaterBukkit().getLatestName();

            default:
                return DungeonMaze.getVersionName();
        }
    }

    /**
     * Get the version code of the available update. The current version code is returned if now new version is available.
     * If the version code is unknown, while a new version is indeed available. The current version number, incremented by one is returned.
     *
     * @return Version code.
     */
    public int getUpdateVersionCode() {
        // Check with the correct updater
        switch(getType()) {
            case UNIVERSAL:
                // TODO: Return the version code of the new update!
                return DungeonMaze.getVersionCode();

            case BUKKIT:
            default:
                return DungeonMaze.getVersionCode() + 1;
        }
    }

    /**
     * Check whether the update is compatible with the current running server and installed Java version.
     * If there's no update available, true will be returned.
     *
     * @return True if the update is compatible, or if no update is available. False otherwise.
     */
    public boolean isUpdateCompatible() {
        // TODO: Implement this!
        return isUpdateMinecraftCompatible() && isUpdateJavaCompatible();
    }

    /**
     * Check whether the update is compatible with the current running server.
     * If there's no update available, true will be returned.
     *
     * @return true if the update is compatible, or if no update is available. False otherwise
     */
    public boolean isUpdateMinecraftCompatible() {
        // TODO: Implement this!
        return true;
    }

    /**
     * Get the required Minecraft version for the update.
     * If there's no update available the current Minecraft version will be returned.
     *
     * @return Required Minecraft version.
     */
    public String getUpdateMinecraftVersion() {
        // TODO: Implement this!
        return MinecraftUtils.getMinecraftVersion();
    }

    /**
     * Check whether the update is compatible with the current installed Java version.
     * If there's no update available, true will be returned.
     *
     * @return true if the update is compatible, or if no update is available. False otherwise
     */
    public boolean isUpdateJavaCompatible() {
        // TODO: Implement this!
        return true;
    }

    /**
     * Get the required Java version for the update.
     * If there's no update available the current Java version will be returned.
     *
     * @return Required Java version.
     */
    public String getUpdateJavaVersion() {
        // TODO: Implement this!
        return SystemUtils.getJavaVersion();
    }

    /**
     * Check whether the update is downloaded, if there is any available.
     * If there's no update available, false will be returned.
     *
     * @return True if an update is downloaded, false otherwise.
     */
    public boolean isUpdateDownloaded() {
        // TODO: Implement this!
        return false;
    }

    /**
     * Check whether the update is downloaded and installed, if there's any available.
     * If there's no update available, false will be returned.
     *
     * @return True if an update is downloaded and installed, false otherwise.
     */
    public boolean isUpdateInstalled() {
        // TODO: Implement this!
        return true;
    }
}
