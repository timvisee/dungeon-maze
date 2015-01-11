package com.timvisee.dungeonmaze.update;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.module.Module;
import com.timvisee.dungeonmaze.update.Updater;

import java.io.File;

public class UpdateCheckerModule extends Module {

    /** Module name. */
    private static final String MODULE_NAME = "Update Checker";

    /** Update checker instance. */
    private Updater updateChecker;

    /**
     * Initialize the module.
     *
     * @return True on success, false on failure. True will also be returned if the module was initialized already.
     */
    @Override
    public boolean init() {
        // Get the plugin JAR
        File pluginJar = new File(DungeonMaze.instance.getDataFolder().getParentFile(), "DungeonMaze.jar");

        // Check whether the update checker should be enabled on startup
        if(Core.getConfigHandler().enableUpdateCheckerOnStartup)
            // Initialize the update checker
            this.updateChecker = new Updater(DungeonMaze.instance, 45175, pluginJar, Updater.UpdateType.DEFAULT, true);

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
        return this.updateChecker != null;
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
        // TODO: Unload the update checker?
        this.updateChecker = null;
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
     * Get the update checker.
     *
     * @return Update checker instance.
     */
    public Updater getUpdateChecker() {
        return this.updateChecker;
    }
}
