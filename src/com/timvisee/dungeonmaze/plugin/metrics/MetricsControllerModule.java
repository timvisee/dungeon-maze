package com.timvisee.dungeonmaze.plugin.metrics;

import com.timvisee.dungeonmaze.module.Module;

public class MetricsControllerModule extends Module {

    /** Module name. */
    private static final String MODULE_NAME = "Metrics Controller";

    /** Metrics controller instance. */
    private MetricsController metricsController;

    /**
     * Initialize the module.
     *
     * @return True on success, false on failure. True will also be returned if the module was initialized already.
     */
    @Override
    public boolean init() {
        // Make sure the controller isn't initialized already
        if(isInit())
            return true;

        // Initialize the metrics controller, return the result
        this.metricsController = new MetricsController(false);
        return this.metricsController.init();
    }

    /**
     * Check whether the module is initialized.
     *
     * @return True if the module is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        // Check whether the controller is constructed
        if(this.metricsController == null)
            return false;

        // Check whether the controller is initialized
        return this.metricsController.isInit();
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
        // Make sure the module is initialized
        if(!isInit())
            return true;

        // Destroy the controller, return false on failure
        if(!this.metricsController.destroy())
            return false;

        // Unset the controller, return the result
        this.metricsController = null;
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
     * Get the metrics controller.
     *
     * @return Metrics controller instance.
     */
    public MetricsController getMetricsController() {
        return this.metricsController;
    }
}
