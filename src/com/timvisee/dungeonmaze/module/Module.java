package com.timvisee.dungeonmaze.module;

public abstract class Module {

    /**
     * Initialize the module.
     *
     * @return True on success, false on failure. True will also be returned if the module was initialized already.
     */
    public abstract boolean init();

    /**
     * Check whether the module is initialized.
     *
     * @return True if the module is initialized, false otherwise.
     */
    public abstract boolean isInit();

    /**
     * Destroy the module. The destruction won't be forced.
     *
     * @return True on success, false on failure. True will also be returned if the module wasn't initialized.
     */
    public boolean destroy() {
        return this.destroy(false);
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
    public abstract boolean destroy(boolean force);

    /**
     * Get the name of the module.
     *
     * @return Module name.
     */
    public abstract String getName();
}
