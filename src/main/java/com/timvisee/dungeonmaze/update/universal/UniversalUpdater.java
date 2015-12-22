package com.timvisee.dungeonmaze.update.universal;

import com.timvisee.dungeonmaze.DungeonMaze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class UniversalUpdater {

    /** Updater checker host. */
    private static final String UPDATER_HOST = "http://updates.timvisee.com";

    /** Updater checker query. */
    private static final String UPDATER_QUERY = "/check.php?";

    /** Updater application ID key. */
    private static final String UPDATER_APP_ID_KEY = "app";

    /** The application ID to identify the current application when updating. */
    private String appId;

    /** True to automatically download the update, if there's one available that is compatible. */
    private boolean autoDownload = true;

    /** True to automatically install the update, if there's one available that is compatible. */
    private boolean autoInstall = true;

    /**
     * Constructor.
     *
     * This automatically checks for updates.
     * It also automatically downloads and installs a new available version if compatible.
     */
    public UniversalUpdater() {
        // TODO: Properly configure this, using a configuration file maybe?
        this("0", true, true, true);
    }

    /**
     * Constructor.
     *
     * @param appId Application ID.
     * @param check True to do an update check.
     * @param autoDownload True to automatically download a new and compatible update.
     * @param autoInstall True to automatically install a new and compatible update.
     */
    public UniversalUpdater(String appId, boolean check, boolean autoDownload, boolean autoInstall) {
        // Store the automatically download and install configuration
        this.appId = appId;
        this.autoDownload = autoDownload;
        this.autoInstall = autoInstall;

        // Should we do an update check
        if(check)
            checkUpdates();
    }

    /**
     * Get the URL used to check for updates as plain text.
     *
     * @return Updater URL as plain text.
     */
    public String getUpdateUrlPlain() {
        // Build and return the URL
        return UPDATER_HOST +
                UPDATER_QUERY +
                UPDATER_APP_ID_KEY +
                '=' +
                getApplicationId();
    }

    /**
     * Get the URL used to check for updates.
     *
     * @return Updater URL.
     */
    public URL getUpdateUrl() {
        // Get and return the URL
        try {
            return new URL(getUpdateUrlPlain());
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }

        // Return null on failure
        return null;
    }

    /**
     * Check if there's an new update available.
     * Also verify whether this update is compatible with the current installation or not.
     *
     * @return True if succeed, false if failed.
     */
    public boolean checkUpdates() {
        // Get the updater URL
        URL updaterUrl = getUpdateUrl();

        try {
            // Open an input stream to retrieve the update data
            InputStream inputStream = updaterUrl.openStream();

            // Create a buffered reader to actually retrieve the updates data
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

        } catch(IOException e) {
            e.printStackTrace();
        }

        // TODO: Make sure this URL is available?

        // TODO: Do a version check

        // If there's an update and it should be downloaded automatically, download it
        return isAutomaticDownload() && downloadUpdate();
    }

    /**
     * Download the newest update. The newest update is only downloaded if it's newer than the current installed version,
     * and if it's compatible with the current Bukkit and Java installation.
     * Note: False will also be returned if the update wasn't downloaded because it's not newer than the currently
     * installed version, or if it's not compatible with the current server set up.
     *
     * @return True of succeed, false on failure.
     */
    public boolean downloadUpdate() {
        // TODO: Make sure we recently checked for an update!
        // TODO: Make sure the file is compatible?
        // TODO: Download the file.

        // If an update is downloaded, and it should be installed automatically, install it
        return isAutomaticInstall() && installUpdate();
    }

    /**
     * Download and installUpdate the newest version available if compatible.
     *
     * @return True if succeed, false on failure.
     */
    public boolean installUpdate() {
        // TODO: Make sure the file is downloaded, and can be downloaded.

        return false;
    }

    /**
     * Get the Dungeon Maze instance.
     *
     * @return Dungeon Maze.
     */
    public DungeonMaze getDungeonMaze() {
        return DungeonMaze.instance;
    }

    /**
     * Get the application ID.
     *
     * @return Application ID.
     */
    public String getApplicationId() {
        return appId;
    }

    /**
     * Set the application ID.
     *
     * @param appId Application ID.
     */
    public void setApplicationId(String appId) {
        this.appId = appId;
    }

    /**
     * Check whether the updater should automatically download new and compatible updates.
     *
     * @return True if the updater should automatically install updates.
     */
    public boolean isAutomaticDownload() {
        return this.autoDownload;
    }

    /**
     * Set whether the updater should automatically download new and compatible updates.
     *
     * @param autoDownload True to automatically download new and compatible updates.
     */
    public void setAutomaticDownload(boolean autoDownload) {
        this.autoDownload = autoDownload;
    }

    /**
     * Check whether the updater should automatically install new and compatible updates.
     *
     * @return True if the updater should automatically install updates.
     */
    public boolean isAutomaticInstall() {
        return this.autoInstall;
    }

    /**
     * Set whether the updater should automatically install new and compatible downloaded updates.
     *
     * @param autoInstall True if the updater should automatically install new and compatible downloads.
     */
    public void setAutomaticInstall(boolean autoInstall) {
        this.autoInstall = autoInstall;
    }
}
