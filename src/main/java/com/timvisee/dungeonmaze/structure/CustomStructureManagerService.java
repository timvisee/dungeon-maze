package com.timvisee.dungeonmaze.structure;

import com.timvisee.dungeonmaze.service.Service;

public class CustomStructureManagerService extends Service {

    /** Service name. */
    private static final String SERVICE_NAME = "Custom Structure Manager";

    /** Custom structure manager instance. */
    private CustomStructureManager customStructureManager;

    /**
     * Initialize the service.
     *
     * @return True on success, false on failure. True will also be returned if the service was initialized already.
     */
    @Override
    public boolean init() {
        this.customStructureManager = new CustomStructureManager();

        // TODO: Load custom structures
        // TODO: Do some error checking!

        return true;
    }

    /**
     * Check whether the service is initialized.
     *
     * @return True if the service is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        return this.customStructureManager != null;
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
        // TODO: Unload the custom structure manager?
        this.customStructureManager = null;
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
     * Get the custom structure manager.
     *
     * @return Custom structure manager instance.
     */
    public CustomStructureManager getCustomStructureManager() {
        return this.customStructureManager;
    }
}
