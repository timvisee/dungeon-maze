package com.timvisee.dungeonmaze.structure;

import com.timvisee.dungeonmaze.module.Module;
import com.timvisee.dungeonmaze.structure.CustomStructureManager;

public class CustomStructureManagerModule extends Module {

    /** Module name. */
    private static final String MODULE_NAME = "Custom Structure Manager";

    /** Custom structure manager instance. */
    private CustomStructureManager customStructureManager;

    /**
     * Initialize the module.
     *
     * @return True on success, false on failure. True will also be returned if the module was initialized already.
     */
    @Override
    public boolean init() {
        this.customStructureManager = new CustomStructureManager();

        // TODO: Load custom structures
        // TODO: Do some error checking!

        return true;
    }

    /**
     * Check whether the module is initialized.
     *
     * @return True if the module is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        return this.customStructureManager != null;
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
        // TODO: Unload the custom strucutre manager?
        this.customStructureManager = null;
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
     * Get the custom structure manager.
     *
     * @return Custom structure manager instance.
     */
    public CustomStructureManager getCustomStructureManager() {
        return this.customStructureManager;
    }
}
