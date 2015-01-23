package com.timvisee.dungeonmaze.api;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.config.ConfigHandler;
import com.timvisee.dungeonmaze.service.Service;
import de.bananaco.bpermissions.imp.YamlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

public class ApiControllerService extends Service {

    /** Service name. */
    private static final String SERVICE_NAME = "API Controller";

    /** API Controller instance. */
    private ApiController apiController;

    /**
     * Initialize the service.
     *
     * @return True on success, false on failure. True will also be returned if the service was initialized already.
     */
    @Override
    public boolean init() {
        // Initialize the API controller
        this.apiController = new ApiController(false);

        // Show a status message
        Core.getLogger().info("Dungeon Maze API started!");

        // Get the Dungeon Maze config
        ConfigHandler configHandler = DungeonMaze.instance.getCore()._getConfigHandler();
        FileConfiguration config = configHandler.config;

        // Check whether the API is enabled
        boolean apiEnabled = true;
        if(config != null)
            apiEnabled = config.getBoolean("api.enabled", true);

        // Enable the API if it should be enabled
        if(apiEnabled)
            this.apiController.setEnabled(true);
        else
            Core.getLogger().info("Not enabling Dungeon Maze API, disabled in config file!");

        // TODO: Do some error handling!

        return true;
    }

    /**
     * Check whether the service is initialized.
     *
     * @return True if the service is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        // Check whether the API controller is instantiated
        if(this.apiController == null)
            return false;

        // TODO: Better check!
        return true;
    }

    /**
     * Destroy the service. The destruction won't be forced.
     *
     * @param force True to force the destruction. This wil re-destroy the service even if it isn't initialized.
     *              This will also force the initialization state to be set to false even if an error occurred while
     *              destroying.
     *
     * @return True on success, false on failure. True will also be returned if the service wasn't initialized. False
     * might be returned if force is set to true, even though the initialization state is set to false.
     */
    @Override
    public boolean destroy(boolean force) {
        // Make sure the api controller is initialized, or it must be forced
        if(!this.isInit() && !force)
            return true;

        // Unregister all API sessions and disable the controller
        if(this.apiController != null) {
            // Count the API sessions
            final int apiSessionCount = getApiController().getApiSessionsCount();

            // Unhook all hooked plugins and sessions
            if(apiSessionCount > 0) {
                Core.getLogger().info("Unhooking " + apiSessionCount + " hooked plugin" + (apiSessionCount != 1 ? "s" : "") + "...");
                this.apiController.unregisterAllApiSessions();
            }

            // Disable the API controller
            this.apiController.setEnabled(false);
        }

        // Return the result
        this.apiController = null;
        return true;
    }

    /**
     * Get the name of the service.
     *
     * @return Service name.
     */
    @Override
    public String getName() {
        return SERVICE_NAME;
    }

    /**
     * Get the API controller.
     *
     * @return API controller instance.
     */
    public ApiController getApiController() {
        return this.apiController;
    }
}
