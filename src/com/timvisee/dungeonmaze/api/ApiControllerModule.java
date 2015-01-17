package com.timvisee.dungeonmaze.api;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.module.Module;

public class ApiControllerModule extends Module {

    /** Module name. */
    private static final String MODULE_NAME = "API Controller";

    /** API Controller instance. */
    private ApiController apiController;

    /**
     * Initialize the module.
     *
     * @return True on success, false on failure. True will also be returned if the module was initialized already.
     */
    @Override
    public boolean init() {
        // Initialize the API controller
        this.apiController = new ApiController(false);

        // Show a status message
        Core.getLogger().info("Dungeon Maze API started!");

        // Enable the API if it should be enabled
        if(DungeonMaze.instance.getConfig().getBoolean("api.enabled", true))
            this.apiController.setEnabled(true);
        else
            Core.getLogger().info("Not enabling Dungeon Maze API, disabled in config file!");

        // TODO: Do some error handling!

        return true;
    }

    /**
     * Check whether the module is initialized.
     *
     * @return True if the module is initialized, false otherwise.
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
     * Destroy the module. The destruction won't be forced.
     *
     * @param force True to force the destruction. This wil re-destroy the module even if it isn't initialized.
     *              This will also force the initialization state to be set to false even if an error occurred while
     *              destroying.
     *
     * @return True on success, false on failure. True will also be returned if the module wasn't initialized. False
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
     * Get the name of the module.
     *
     * @return Module name.
     */
    @Override
    public String getName() {
        return MODULE_NAME;
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
