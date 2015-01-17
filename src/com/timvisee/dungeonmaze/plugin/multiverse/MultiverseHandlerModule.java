package com.timvisee.dungeonmaze.plugin.multiverse;

import com.timvisee.dungeonmaze.module.Module;

public class MultiverseHandlerModule extends Module {

    /** Module name. */
    private static final String MODULE_NAME = "Multiverse Handler";

    /** Multiverse handler instance. */
    private MultiverseHandler multiverseHandler;

    /**
     * Initialize the module.
     *
     * @return True on success, false on failure. True will also be returned if the module was initialized already.
     */
    @Override
    public boolean init() {
        // Initialize the multiverse handler
        this.multiverseHandler = new MultiverseHandler(false);

        // Hook the handler into the multiverse core, return the result
        return this.multiverseHandler.hook();
    }

    /**
     * Check whether the module is initialized.
     *
     * @return True if the module is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        // TODO: Better check!
        return this.multiverseHandler != null;
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
        // Make sure the multiverse handler is initialized
        if(!this.isInit() && !force)
            return true;

        // Unhook the multiverse handler
        if(this.multiverseHandler != null) {
            if(!this.multiverseHandler.unhook()) {
                if(force)
                    this.multiverseHandler = null;
                return false;
            }
        }

        // Return the result
        this.multiverseHandler = null;
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
     * Get the multiverse handler.
     *
     * @return Multiverse handler instance.
     */
    public MultiverseHandler getMultiverseHandler() {
        return this.multiverseHandler;
    }
}
