package com.timvisee.dungeonmaze.plugin.authmereloaded;

import com.timvisee.dungeonmaze.Core;
import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class AuthMeReloadedApiProvider {

    /**
     * Name of the plugin.
     * Used to check whether it's available.
     */
    public static final String PLUGIN_NAME = "AuthMe";

    /**
     * AuthMe Reloaded API instance.
     */
    private AuthMeApi api;

    /**
     * Defines the required AuthMe Reloaded version.
     */
    // TODO: Should we include the '...-SNAPSHOT' identifier?
    private final static String REQUIRED_AUTHME_RELOADED_VERSION = "5.3.2";

    /**
     * Constructor.
     */
    public AuthMeReloadedApiProvider(boolean hook) {
        // Should the handler hook immediately
        // TODO: Do some error handling
        if(hook)
            hook();
    }

    /**
     * Try to hook into AuthMe Reloaded.
     *
     * @return True on success, false if an error occurred.
     * True will also be returned if the handler didn't hook because AuthMe Reloaded wasn't found.
     */
    public boolean hook() {
        // Validate the instance
        if(!Bukkit.getPluginManager().isPluginEnabled(PLUGIN_NAME)) {
            Core.getLogger().info("AuthMe Reloaded not detected, disabling it's usage!");
            return true;
        }

        // TODO: Try to re-hook if we're already hooked?
        // Create a variable to store the API instance in
        AuthMeApi api;
        try {
            // Try to hook into AuthMe Reloaded by getting an API instance
            api = AuthMeApi.getInstance();

        } catch(Exception ex) {
            Core.getLogger().info("Failed to hook into AuthMe Reloaded!");
            return true;
        }

        // Validate the instance
        if(api == null) {
            Core.getLogger().info("AuthMe Reloaded not detected, disabling it's usage!");
            return true;
        }

        // Set the API instance
        this.api = api;

        // Show an status message
        Core.getLogger().info("Hooked into AuthMe Reloaded!");
        return true;
    }

    /**
     * Check whether the handler is successfully hooked into the AuthMe Reloaded core.
     *
     * @return True if hooked, false if not.
     */
    public boolean isHooked() {
        return this.api != null;
    }

    /**
     * Unhook from the AuthMe Reloaded plugin.
     *
     * @return True if the handler unhooked from the AuthMe Reloaded core, false otherwise. True will also be returned if the
     * handler wasn't hooked yet.
     */
    public boolean unhook() {
        // Make sure the handler is hooked
        if(!isHooked())
            return true;

        // TODO: Unhook properly!

        // Unhook from the AuthMe Reloaded core
        this.api = null;

        // Return the result
        return !isHooked();
    }

    /**
     * Get the AuthMe Reloaded core.
     *
     * @return The AuthMe Reloaded core instance, or null if the handler isn't hooked yet.
     */
    public AuthMeApi getAuthMeReloadedApi() {
        // Make sure the handler is hooked
        if(!isHooked())
            return null;

        // Return the AuthMe Reloaded core instance.
        return this.api;
    }

    /**
     * Get the plugin description file of the hooked AuthMe Reloaded plugin.
     *
     * @return The plugin description file, or null on failure. Null will also be returned if the handler isn't
     * successfully hooked into AuthMe Reloaded.
     */
    public PluginDescriptionFile getPluginDescription() {
        // Make sure the handler is hooked
        if(!isHooked())
            return null;

        // Get and return the plugin description
        return this.api.getPlugin().getDescription();
    }

    /**
     * Get the version number of the hooked AuthMe Reloaded plugin.
     *
     * @return The version number, or null on failure. Null will also be returned if the handler isn't
     * successfully hooked into AuthMe Reloaded.
     */
    @SuppressWarnings("UnusedDeclaration")
    public String getPluginVersion() {
        // Make sure the handler is hooked
        if(!isHooked())
            return null;

        // Get and return the plugins version number
        return getPluginDescription().getVersion();
    }

    /**
     * Check whether a player is authenticated.
     * The AuthMe Reloaded plugin must be hooked in order for this method to work properly.
     *
     * @param player The player to check for authentication.
     *
     * @return True if the player is authenticated by AuthMe Reloaded, false if not.
     * False will also be returned if the authentication check failed as a security measure.
     */
    public boolean isAuthenticated(Player player) {
        // Check whether the user is authenticated
        try {
            return this.api.isAuthenticated(player);

        } catch(Exception ex) {
            // Failed to check for authentication, show an error
            Core.getLogger().error("Failed to check player authentication through AuthMe Reloaded. Chosen false for security reasons.");

            // Print the stack trace and return
            ex.printStackTrace();
            return false;
        }
    }
}
