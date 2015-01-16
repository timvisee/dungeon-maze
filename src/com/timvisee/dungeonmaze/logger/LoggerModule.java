package com.timvisee.dungeonmaze.logger;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.module.Module;

import java.util.logging.Logger;

public class LoggerModule extends Module {

    /** Module name. */
    private static final String MODULE_NAME = "Logger";

    /** Logger manager instance. */
    private LoggerManager loggerManager;

    /**
     * Initialize the module.
     *
     * @return True on success, false on failure. True will also be returned if the module was initialized already.
     */
    @Override
    public boolean init() {
        // Make sure the module isn't initialized already
        if(isInit())
            return true;

        // Initialize the logging manager
        this.loggerManager = new LoggerManager(false);
        return this.loggerManager.init();
    }

    /**
     * Check whether the module is initialized.
     *
     * @return True if the module is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        // Make sure the logger manager is set
        if(this.loggerManager == null)
            return false;

        // Check whether the logger manager is initialized
        return this.loggerManager.isInit();
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
        // Make sure the logger manager is initialized, or it must be forced
        if(!isInit() && !force)
            return true;

        // Destroy the logger manager if the instance is available
        if(this.loggerManager != null) {
            if(!this.loggerManager.destroy()) {
                if(force)
                    this.loggerManager = null;
                return false;
            }
        }

        // Reset the logger manager instance, return the result
        this.loggerManager = null;
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
     * Get the logger manager.
     *
     * @return Logger manager instance.
     */
    public LoggerManager getLoggerManager() {
        return this.loggerManager;
    }

    /**
     * Get the default logger.
     *
     * @return Default logger instance.
     */
    public Logger getLogger() {
        // Make sure the module is initialized
        if(!isInit())
            return DungeonMaze.instance.getLogger();

        // Get and return the logger
        return this.loggerManager.getLogger();
    }
}
