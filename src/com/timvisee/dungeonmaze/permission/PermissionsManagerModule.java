package com.timvisee.dungeonmaze.permission;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.module.Module;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PermissionsManagerModule extends Module {

    /** Module name. */
    private static final String MODULE_NAME = "Permissions Manager";

    /** Permissions manager instance. */
    private PermissionsManager permissionsManager;

    /**
     * Initialize the module.
     *
     * @return True on success, false on failure. True will also be returned if the module was initialized already.
     */
    @Override
    public boolean init() {
        // Initialize the update checker
        this.permissionsManager = new PermissionsManager(Bukkit.getServer(), (Plugin) DungeonMaze.instance, Core.getLogger());

        // Set up the permissions manager
        this.permissionsManager.setup();

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
        // TODO: Better check!
        return this.permissionsManager != null;
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
        // Make sure the permissions manager is initialized
        if(!this.isInit())
            return true;

        // Unhook the permissions manager
        this.permissionsManager.unhook();
        this.permissionsManager = null;
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
     * Get the permissions manager.
     *
     * @return Permissions manager instance.
     */
    public PermissionsManager getPermissionsManager() {
        return this.permissionsManager;
    }

    /**
     * Reload the permissions manager.
     *
     * @return True on success, false on failure.
     */
    public boolean reloadPermissionsManager() {
        return this.permissionsManager.reload();
    }
}
