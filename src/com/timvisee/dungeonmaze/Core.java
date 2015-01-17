package com.timvisee.dungeonmaze;

import com.timvisee.dungeonmaze.api.ApiController;
import com.timvisee.dungeonmaze.api.OldApiController;
import com.timvisee.dungeonmaze.api.OldApiControllerModule;
import com.timvisee.dungeonmaze.command.CommandHandler;
import com.timvisee.dungeonmaze.command.CommandHandlerModule;
import com.timvisee.dungeonmaze.config.ConfigHandler;
import com.timvisee.dungeonmaze.listener.EventListenerManager;
import com.timvisee.dungeonmaze.logger.LoggerManager;
import com.timvisee.dungeonmaze.permission.PermissionsManager;
import com.timvisee.dungeonmaze.module.*;
import com.timvisee.dungeonmaze.api.ApiControllerModule;
import com.timvisee.dungeonmaze.config.ConfigHandlerModule;
import com.timvisee.dungeonmaze.plugin.metrics.MetricsController;
import com.timvisee.dungeonmaze.plugin.metrics.MetricsControllerModule;
import com.timvisee.dungeonmaze.structure.CustomStructureManagerModule;
import com.timvisee.dungeonmaze.listener.EventListenerManagerModule;
import com.timvisee.dungeonmaze.logger.LoggerModule;
import com.timvisee.dungeonmaze.plugin.multiverse.MultiverseHandler;
import com.timvisee.dungeonmaze.plugin.multiverse.MultiverseHandlerModule;
import com.timvisee.dungeonmaze.permission.PermissionsManagerModule;
import com.timvisee.dungeonmaze.update.UpdateCheckerModule;
import com.timvisee.dungeonmaze.world.WorldManagerModule;
import com.timvisee.dungeonmaze.structure.CustomStructureManager;
import com.timvisee.dungeonmaze.update.Updater;
import com.timvisee.dungeonmaze.world.WorldManager;

import java.util.logging.Logger;

@SuppressWarnings("UnusedDeclaration")
public class Core {

    // TODO: Do we need to keep a non-static version of each method available?

    /** Static Core instance. */
    public static Core instance;

    /** Defines whether the core has been initialized. */
    private boolean init = false;

    /** Module manager instance. */
    private ModuleManager moduleManager;

    /** Logger module instance. */
    private LoggerModule loggerModule = new LoggerModule();
    /** Config handler module instance. */
    private ConfigHandlerModule configHandlerModule = new ConfigHandlerModule();
    /** Command handler module instance. */
    private CommandHandlerModule commandHandlerModule = new CommandHandlerModule();
    /** World manager module instance. */
    private WorldManagerModule worldManagerModule = new WorldManagerModule();
    /** Update checker module instance. */
    private UpdateCheckerModule updateCheckerModule = new UpdateCheckerModule();
    /** Permissions manager module instance. */
    private PermissionsManagerModule permissionsManagerModule = new PermissionsManagerModule();
    /** Multiverse handler module instance. */
    private MultiverseHandlerModule multiverseHandlerModule = new MultiverseHandlerModule();
    /** Custom structure manager module instance. */
    private CustomStructureManagerModule customStructureManagerModule = new CustomStructureManagerModule();
    /** API Controller module instance. */
    private ApiControllerModule apiControllerModule = new ApiControllerModule();
    /** Old API Controller module instance. */
    private OldApiControllerModule oldApiControllerModule = new OldApiControllerModule();
    /** Event listener manager module instance. */
    private EventListenerManagerModule eventListenerManagerModule = new EventListenerManagerModule();
    /** Metrics controller module instance. */
    private MetricsControllerModule metricsControllerModule = new MetricsControllerModule();

    /**
     * Constructor.
     *
     * @param init True to immediately initialize.
     */
    public Core(boolean init) {
        // Set the static instance
        Core.instance = this;

        // Initialize
        if(init)
            this.init();
    }

    /**
     * Initialize the core.
     * This method will initialize and set up all handlers and managers used by the core.
     *
     * @return True on success, false on failure.
     */
    public boolean init() {
        // Make sure the Core hasn't been instantiated already
        if(this.isInit())
            return true;

        // Initialize the module manager
        this.moduleManager = new ModuleManager();

        // Register all modules
        this.moduleManager.unregisterAllModules();
        this.moduleManager.registerModule(this.loggerModule);
        this.moduleManager.registerModule(this.configHandlerModule);
        this.moduleManager.registerModule(this.commandHandlerModule);
        this.moduleManager.registerModule(this.multiverseHandlerModule);
        this.moduleManager.registerModule(this.worldManagerModule);
        this.moduleManager.registerModule(this.updateCheckerModule);
        this.moduleManager.registerModule(this.permissionsManagerModule);
        this.moduleManager.registerModule(this.customStructureManagerModule);
        this.moduleManager.registerModule(this.apiControllerModule);
        this.moduleManager.registerModule(this.oldApiControllerModule);
        this.moduleManager.registerModule(this.eventListenerManagerModule);
        this.moduleManager.registerModule(this.metricsControllerModule);

        // Initialize all modules
        if(!this.moduleManager.initModules())
            return false;

        // Everything has been set up successfully, return the result
        this.init = true;
        return true;
    }

    /**
     * Destroy all modules.
     *
     * @param force True to force to destroy all modules, even if one module couldn't be destroyed. If force mode is
     *              used all module states will be set to destroyed even if the destruction failed.
     *
     * @return True on success, false on failure.
     */
    public boolean destroy(boolean force) {
        // Make sure the core is initialized, or the method must be forced
        if(!this.isInit() && !force)
            return true;

        // Destroy all modules
        if(!this.moduleManager.destroyModules(force))
            if(!force)
                return false;

        // Return the result
        this.init = false;
        return true;
    }

    /**
     * Check whether the Core has been initialized.
     *
     * @return True if the core has been initialized, false otherwise.
     */
    public boolean isInit() {
        return this.init;
    }

    /**
     * Get the module manager.
     *
     * @return Module manager instance.
     */
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    /**
     * Get the logger manager.
     *
     * @return Logger manager instance.
     */
    public static LoggerManager getLoggerManager() {
        return Core.instance._getLoggerManager();
    }

    /**
     * Get the logger manager.
     *
     * @return Logger manager instance.
     */
    public LoggerManager _getLoggerManager() {
        return this.loggerModule.getLoggerManager();
    }

    /**
     * Get the logger.
     *
     * @return Logger instance.
     */
    public static Logger getLogger() {
        return Core.instance._getLogger();
    }
    
    /**
     * Get the logger.
     *
     * @return Logger instance.
     */
    public Logger _getLogger() {
        /// Make sure the logger module is initialized
        if(this.loggerModule == null)
            return DungeonMaze.instance.getLogger();

        // Get and return the logger
        return this.loggerModule.getLogger();
    }

    /**
     * Get the config handler.
     *
     * @return Config handler instance.
     */
    public static ConfigHandler getConfigHandler() {
        return Core.instance._getConfigHandler();
    }

    /**
     * Get the config handler.
     *
     * @return Config handler instance.
     */
    public ConfigHandler _getConfigHandler() {
        return this.configHandlerModule.getConfigHandler();
    }

    /**
     * Get the command handler.
     *
     * @return Command handler instance.
     */
    public static CommandHandler getCommandHandler() {
        return Core.instance._getCommandHandler();
    }

    /**
     * Get the command handler.
     *
     * @return Command handler instance.
     */
    public CommandHandler _getCommandHandler() {
        return this.commandHandlerModule.getCommandHandler();
    }

    /**
     * Get the multiverse handler.
     *
     * @return Multiverse handler instance.
     */
    public static MultiverseHandler getMultiverseHandler() {
        return Core.instance._getMultiverseHandler();
    }

    /**
     * Get the multiverse handler.
     *
     * @return Multiverse handler instance.
     */
    public MultiverseHandler _getMultiverseHandler() {
        return this.multiverseHandlerModule.getMultiverseHandler();
    }

    /**
     * Get the world manager.
     *
     * @return World manager instance.
     */
    public static WorldManager getWorldManager() {
        return Core.instance._getWorldManager();
    }

    /**
     * Get the world manager.
     *
     * @return World manager instance.
     */
    public WorldManager _getWorldManager() {
        return this.worldManagerModule.getWorldManager();
    }

    /**
     * Get the update checker.
     *
     * @return Update checker instance.
     */
    public static Updater getUpdateChecker() {
        return Core.instance._getUpdateChecker();
    }

    /**
     * Get the update checker.
     *
     * @return Update checker instance.
     */
    public Updater _getUpdateChecker() {
        return this.updateCheckerModule.getUpdateChecker();
    }

    /**
     * Get the permissions manager.
     *
     * @return Permissions manager instance.
     */
    public static PermissionsManager getPermissionsManager() {
        return Core.instance._getPermissionsManager();
    }

    /**
     * Get the permissions manager.
     *
     * @return Permissions manager instance.
     */
    public PermissionsManager _getPermissionsManager() {
        return this.permissionsManagerModule.getPermissionsManager();
    }

    /**
     * Get the custom structure manager.
     *
     * @return Custom structure manager instance.
     */
    public static CustomStructureManager getCustomStructureManager() {
        return Core.instance._getCustomStructureManager();
    }

    /**
     * Get the custom structure manager.
     *
     * @return Custom structure manager instance.
     */
    public CustomStructureManager _getCustomStructureManager() {
        return this.customStructureManagerModule.getCustomStructureManager();
    }

    /**
     * Get the API Controller.
     *
     * @return API Controller instance.
     */
    public static ApiController getApiController() {
        return Core.instance._getApiController();
    }

    /**
     * Get the API Controller.
     *
     * @return API Controller instance.
     */
    public ApiController _getApiController() {
        return this.apiControllerModule.getApiController();
    }

    /**
     * Get the old API Controller.
     *
     * @return Old API Controller instance.
     */
    public static OldApiController getOldApiController() {
        return Core.instance._getOldApiController();
    }

    /**
     * Get the old API Controller.
     *
     * @return Old API Controller instance.
     */
    public OldApiController _getOldApiController() {
        return this.oldApiControllerModule.getApiController();
    }

    /**
     * Get the event listener manager.
     *
     * @return Event listener manager instance.
     */
    public static EventListenerManager getEventListenerManager() {
        return Core.instance._getEventListenerManager();
    }

    /**
     * Get the event listener manager.
     *
     * @return Event listener manager instance.
     */
    public EventListenerManager _getEventListenerManager() {
        return this.eventListenerManagerModule.getEventListenerManager();
    }

    /**
     * Get the metrics controller.
     *
     * @return Metrics controller instance.
     */
    public static MetricsController getMetricsController() {
        return Core.instance._getMetricsController();
    }

    /**
     * Get the metrics controller.
     *
     * @return Metrics controller instance.
     */
    public MetricsController _getMetricsController() {
        return this.metricsControllerModule.getMetricsController();
    }
}
