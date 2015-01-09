package com.timvisee.dungeonmaze.module;

import com.timvisee.dungeonmaze.config.ConfigHandler;

public class ConfigHandlerModule extends Module {

    /** Module name. */
    private static final String MODULE_NAME = "Config Handler";

    /** Config handler instance. */
    private ConfigHandler configHandler;

    /**
     * Initialize the module.
     *
     * @return True on success, false on failure. True will also be returned if the module was initialized already.
     */
    @Override
    public boolean init() {
        // Instantiate and load the config handler
        this.configHandler = new ConfigHandler();
        this.configHandler.load();

        // TODO: Check whether the config handler is loaded successfully

        return true;
    }

    /**
     * Check whether the module is initialized.
     *
     * @return True if the module is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        return configHandler != null;
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
        // TODO: Unload the config handler
        this.configHandler = null;
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
     * Get the config handler.
     *
     * @return Config handler instance.
     */
    public ConfigHandler getConfigHandler() {
        return this.configHandler;
    }
}
