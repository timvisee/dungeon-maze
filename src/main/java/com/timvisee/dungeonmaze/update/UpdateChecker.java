package com.timvisee.dungeonmaze.update;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.server.ServerType;
import com.timvisee.dungeonmaze.update.bukkit.Updater;
import com.timvisee.dungeonmaze.update.universal.UniversalUpdater;
import com.timvisee.dungeonmaze.util.MinecraftUtils;
import com.timvisee.dungeonmaze.util.PluginUtils;
import com.timvisee.dungeonmaze.util.SystemUtils;

import java.io.File;

public class UpdateChecker {

    /** Used update checker type, or null if none. */
    private UpdateCheckerType type = null;

    /** Universal updater instance if started. */
    private UniversalUpdater universalUpdater = null;

    /** Bukkit updater instance if started. */
    private Updater bukkitUpdater = null;

    /**
     * Constructor.
     *
     * This automatically chooses the update checker type depending on the server type that the plugin is running on.
     */
    public UpdateChecker() {
        this(null);
    }

    /**
     * Constructor.
     *
     * @param type Update checker type to use.
     */
    public UpdateChecker(UpdateCheckerType type) {
        // Figure out the update checker type to use
        if(type == null) {
            // Get the server type this plugin is running on
            ServerType serverType = MinecraftUtils.getServerType();

            // Select the update checker type.
            switch(serverType) {
                case BUKKIT:
                    type = UpdateCheckerType.BUKKIT;
                    break;

                default:
                    type = UpdateCheckerType.UNIVERSAL;
            }
        }

        // Set the the updater type
        this.type = type;
    }

    /**
     * Start the updater.
     */
    public void start() {
        // Start the proper updater
        switch(this.type) {
        case BUKKIT:
            startBukkit();
            break;

        default:
            startUniversal();
        }

        // TODO: Validate that the updater has started correctly?
    }

    /**
     * Start the universal updater.
     */
    private void startUniversal() {
        // Show a status message
        Core.getLogger().info("Using universal updater. Starting...");

        // Set up the universal updater
        this.universalUpdater = new UniversalUpdater(DungeonMaze.getUpdaterApplicationId());

        // Show a status message
        Core.getLogger().info("Universal updater has been started!");
    }

    /**
     * Start the Bukkit updater.
     */
    private void startBukkit() {
        // Show a status message
        Core.getLogger().info("Using Bukkit updater. Starting...");

        // TODO: Implement this code!

        // Get the plugin JAR file
        File pluginJar = PluginUtils.getPluginFile();

        // Set up the update checker
        this.bukkitUpdater = new Updater(DungeonMaze.instance, 45175, pluginJar, Updater.UpdateType.DEFAULT, true);

        // TODO: Do some error checking on the updater!

        // Show a status message
        Core.getLogger().info("Bukkit updater has been started!");
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
    public UniversalUpdater getUniversalUpdater() {
        return this.universalUpdater;
    }

    /**
     * Get the Bukkit updater instance if set.
     *
     * @return Bukkit updater instance.
     */
    public Updater getBukkitUpdater() {
        return this.bukkitUpdater;
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
            case BUKKIT:
                return getBukkitUpdater().getResult() != Updater.UpdateResult.NO_UPDATE;

            default:
                return getUniversalUpdater().isUpdateAvailable();
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
            case BUKKIT:
                // Get the update version, remove spaces and the plugin name from the version number
                return getBukkitUpdater().getLatestName()
                        .toLowerCase()
                        .replace(" ", "")
                        .replace("dungeon", "")
                        .replace("maze", "")
                        .trim();

            default:
                return getUniversalUpdater().getUpdateDataUpdateName();
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
            case BUKKIT:
                // Imagine a new version code, as these are not present with the Bukkit update data
                return DungeonMaze.getVersionCode() + 1;

            default:
                return getUniversalUpdater().getUpdateDataUpdateCode();
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
