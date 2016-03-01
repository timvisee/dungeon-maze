package com.timvisee.dungeonmaze.update.universal;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.util.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class UniversalUpdater {

    /**
     * Updater checker host.
     */
    private static final String UPDATER_HOST = "http://updates.timvisee.com";

    /**
     * Updater checker query.
     */
    private static final String UPDATER_QUERY = "/check.php?";

    /**
     * Updater application ID key.
     */
    private static final String UPDATER_APP_ID_KEY = "app";

    /**
     * The application ID to identify the current application when updating.
     */
    private String appId;

    /**
     * True to automatically download the update, if there's one available that is compatible.
     */
    private boolean autoDownload = true;

    /**
     * True to automatically install the update, if there's one available that is compatible.
     */
    private boolean autoInstall = true;

    /**
     * The time in milliseconds (from the last OS start) the latest update check has been made. If no check has been made yet, this will be -1.
     */
    private long lastUpdateCheck = -1;

    /**
     * The data received with the last update check.
     */
    private JSONObject lastUpdateCheckData;

    /**
     * Constructor.
     *
     * This automatically checks for updates.
     * It also automatically downloads and installs a new available version if compatible.
     *
     * @param appId Application ID.
     */
    public UniversalUpdater(String appId) {
        // TODO: Properly configure this, using a configuration file maybe?
        this(appId, true, true, true);
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
        // Create a buffered reader to load the remote page in
        BufferedReader reader;

        try {
            // Get the updater URL
            URL updaterUrl = getUpdateUrl();

            // Get the input stream and input stream reader
            InputStream inputStream = updaterUrl.openStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // Set up the reader
            reader = new BufferedReader(inputStreamReader);

            // Create a string buffer to buffer the page that needs to be retrieved and a buffer array to temporarily buffer the page being retrieved
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];

            // Actually retrieve the page using the buffer, and put it into the string buffer
            while((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            // Get the JSON root object
            JSONObject rootObj = new JSONObject(buffer.toString());

            // Get the update data and store it in the data field
            lastUpdateCheckData = rootObj.getJSONObject("app");

            // Get a few application update and request parameters
            String updateVersion = lastUpdateCheckData.getString("version");
            int updateVersionCode = lastUpdateCheckData.getInt("versionCode");
            String updateRequiredJavaVersion = lastUpdateCheckData.getString("requiredJavaVersion");
            String updateRequiredBukkitVersion = lastUpdateCheckData.getString("requiredBukkitVersion");
            boolean updateImportantUpdate = lastUpdateCheckData.getBoolean("importantUpdate");
            String updateDownloadUrl = lastUpdateCheckData.getString("downloadUrl");
            String requestDate = rootObj.getString("date");

            // TODO: Remove these debug messages when finished
            Bukkit.broadcastMessage(ChatColor.GOLD + "RETRIEVED JSON VALUES:");
            Bukkit.broadcastMessage(ChatColor.GOLD + "updateVersion: " + updateVersionCode);
            Bukkit.broadcastMessage(ChatColor.GOLD + "updateVersionCode: " + updateVersion);
            Bukkit.broadcastMessage(ChatColor.GOLD + "updateRequiredJavaVersion: " + updateRequiredJavaVersion);
            Bukkit.broadcastMessage(ChatColor.GOLD + "updateRequiredBukkitVersion: " + updateRequiredBukkitVersion);
            Bukkit.broadcastMessage(ChatColor.GOLD + "updateImportantUpdate: " + updateImportantUpdate);
            Bukkit.broadcastMessage(ChatColor.GOLD + "updateDownloadUrl: " + updateDownloadUrl);
            Bukkit.broadcastMessage(ChatColor.GOLD + "requestDate: " + requestDate);

            // TODO: Determine whether a new update is available

            // Set the last update check time
            this.lastUpdateCheck = System.currentTimeMillis();

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        // Compare the version code of the installed version with the report of the last update check
        int updateVersionCode = lastUpdateCheckData.getInt("versionCode");
        if(DungeonMaze.getVersionCode() < updateVersionCode)
            return false;

        // TODO: Make sure the download URL is reachable

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
        // Ensure we recently did an update check
        // TODO: Make this value customizable, put this in a method, or put it in a constant
        if(this.lastUpdateCheck == -1 || this.lastUpdateCheck < (System.currentTimeMillis() - 60 * 60 * 100))
            return false;

        // TODO: Make sure the file is compatible, by comparing required Bukkit versionAdded update downloading in the universal updater

        // Get the download URL of the update file
        String updateDownloadUrl = lastUpdateCheckData.getString("downloadUrl");

        // Get the update directory and create it if it doesn't exist
        File updateDir = getUpdateDirectory();
        if(updateDir.mkdirs())
            Core.getLogger().info("The updates directory has been created");

        // Get the plugin's update file
        File pluginUpdateFile = getUpdatePluginFile();

        // Try to download the update file
        try {
            // Get an input stream for the update file from the URL
            final URL website = new URL(updateDownloadUrl);
            final InputStream in = website.openStream();

            // Define the copy options
            CopyOption[] copyOptions = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES,
            };

            // Try to copy the file
            Files.copy(in, pluginUpdateFile.toPath(), copyOptions);

        } catch(MalformedURLException e) {
            Core.getLogger().error("The update file URL is invalid.");

        } catch(FileNotFoundException e) {
            Core.getLogger().error("Could not found the update file.");

        } catch(IOException e) {
            Core.getLogger().error("An error occurred while downloading the update file.");
            e.printStackTrace();
        }

        // If an update is downloaded, and it should be installed automatically, install it
        // TODO: Check the returned value, is this valid.
        return isAutomaticInstall() && installUpdate();
    }

    /**
     * Get the directory all update files are placed in.
     *
     * @return The update directory.
     */
    public File getUpdateDirectory() {
        return new File(PluginUtils.getPluginDataDirectory(), "update");
    }

    /**
     * Get the file location of the update JAR.
     *
     * @return Update JAR file location.
     */
    public File getUpdatePluginFile() {
        return new File(getUpdateDirectory(), "DungeonMaze.jar");
    }

    /**
     * Download and installUpdate the newest version available if compatible.
     *
     * @return True if succeed, false on failure.
     */
    public boolean installUpdate() {
        // Get the update file
        File updatePluginFile = getUpdatePluginFile();

        // Make sure an update plugin file is available
        if(!updatePluginFile.exists()) {
            Core.getLogger().warning("Could not install update, no update has been downloaded.");
            return false;
        }

        // Get the plugin file, and make sure it's valid
        File pluginFile = PluginUtils.getPluginFile();
        if(pluginFile == null || !pluginFile.exists()) {
            Core.getLogger().error("Unable to determine the location of the Dungeon Maze plugin file, can't install update.");
            return false;
        }

        try {
            // Define the input stream of the update file
            InputStream in = new FileInputStream(updatePluginFile);

            // Define the copy options
            CopyOption[] copyOptions = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES,
            };

            // Try to copy and replace the plugin file with the update
            Files.copy(in, pluginFile.toPath(), copyOptions);

        } catch(IOException e) {
            Core.getLogger().error("Failed to install the Dungeon Maze update.");
            e.printStackTrace();
            return false;
        }

        // Delete the update file
        if(!updatePluginFile.delete())
            Core.getLogger().warning("Failed to delete the update file, the update has however been installed successfully");

        else
            Core.getLogger().info("The Dungeon Maze has successfully been installed!");

        return false;
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
