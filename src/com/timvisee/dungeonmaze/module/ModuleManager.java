package com.timvisee.dungeonmaze.module;

import com.timvisee.dungeonmaze.Core;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    /** The list of modules. */
    private List<Module> modules = new ArrayList<Module>();

    /**
     * Constructor.
     */
    public ModuleManager() { }

    /**
     * Get a module by it's index.
     * Alias of getModule();
     *
     * @param i The index of the module to get.
     *
     * @return The module, or null if the index is out of bound.
     */
    public Module get(int i) {
        return this.getModule(i);
    }

    /**
     * Get a module by it's index.
     *
     * @param i The index of the module to get.
     *
     * @return The module, or null if the index is out of bound.
     */
    public Module getModule(int i) {
        // Make sure the index is valid
        if(i >= this.getModuleCount() || i < 0)
            return null;

        // Get and return the module
        return this.modules.get(i);
    }

    /**
     * Get the list of available modules.
     *
     * @return The list of available modules.
     */
    public List<Module> getModules() {
        return this.modules;
    }

    /**
     * Get the number of modules.
     *
     * @return Number of modules.
     */
    public int getModuleCount() {
        return this.modules.size();
    }

    /**
     * Register a module without initializing it.
     *
     * @param module The module to register.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean registerModule(Module module) {
        return this.registerModule(module, false);
    }

    /**
     * Register a module.
     *
     * @param module The module to register.
     * @param init True to immediately initialize the module.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean registerModule(Module module, boolean init) {
        // Register the module, return false on failure
        if(!this.modules.add(module))
            return false;

        // Check whether we should initialize the module, return the result
        if(!init)
            return true;

        return module.init();
    }

    /**
     * Unregister all modules.
     */
    public void unregisterAllModules() {
        this.modules.clear();
    }

    /**
     * Initialize all modules.
     *
     * @return True on success, false on failure.
     */
    public boolean initModules() {
        // Initialize each module
        for(int i = 0; i < this.getModuleCount(); i++) {
            // Get the current module
            Module m = this.get(i);

            // Initialize the module
            if(!m.init()) {
                // TODO: Improve this error message!
                Core.getLogger().info("[DungeonMaze] [Module] Load: " + m.getName() + " FAILED!");
                return false;
            }

            // Show a status message
            // TODO: Should we remove this message?
            Core.getLogger().info("[DungeonMaze] [Module] Load: " + m.getName() + " SUCCESS!");
        }

        // Every module was initialized successfully, return the result
        return true;
    }

    /**
     * Destroy all modules.
     *
     * @param force True to force to destroy every module, this will destroy all modules even if one failed to destroy.
     *
     * @return True on success, false on failure. If force is set to true false might still be returned even though
     * all modules are destroyed.
     */
    public boolean destroyModules(boolean force) {
        // Set whether the destruction failed
        boolean failed = false;

        // Destroy each module
        for(int i = this.getModuleCount() - 1; i >= 0; i--) {
            // Get the current module
            Module m = this.get(i);

            // Destroy the module
            if(!m.destroy(force)) {
                // Set the failed state
                failed = true;

                // TODO: Improve this error message!
                Core.getLogger().info("[DungeonMaze] [Module] Unload: " + m.getName() + " FAILED!");

                // Return false if the force mode isn't used
                if(!force)
                    return false;
            }

            // Show a status message
            // TODO: Should we remove this message?
            Core.getLogger().info("[DungeonMaze] [Module] Unload: " + m.getName() + " SUCCESS!");
        }

        // Every module was initialized successfully, return the result
        return !failed;
    }
}
