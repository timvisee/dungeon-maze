package com.timvisee.dungeonmaze.permission;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.plugin.authmereloaded.AuthMeReloadedApiProvider;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * PermissionsManager.
 *
 * A permissions manager, to manage and use various permissions systems.
 * This manager supports dynamic plugin hooking and various other features.
 *
 * Written by Tim Visée.
 *
 * @author Tim Visée, http://timvisee.com
 * @version 0.3
 */
public class PermissionsManager {

    /**
     * Server instance.
     */
    private Server server;

    /**
     * Plugin instance.
     */
    private Plugin plugin;

    /**
     * Logger instance.
     */
    private Logger log;

    /**
     * The permissions manager Bukkit listener instance.
     */
    private PermissionsManagerBukkitListener bukkitListener;

    /**
     * A flag whether the permissions manager is started or not.
     */
    private boolean started = false;

    /**
     * Type of permissions system that is currently used.
     * Null if no permissions system is used.
     */
    private PermissionsSystemType permsType = null;

    /**
     * True to always allow OP players, even if they don't have the proper permissions supplied by the permissions plugin.
     */
    private boolean alwaysAllowOp = false;

    /**
     * Constructor.
     *
     * @param server Server instance
     * @param plugin Plugin instance
     * @param log    Logger
     * @param start True to start the permissions manager immediately.
     */
    public PermissionsManager(Server server, Plugin plugin, Logger log, boolean start) {
        this.server = server;
        this.plugin = plugin;
        this.log = log;

        // Create and register the Bukkit listener on the server if it's valid
        if(this.server != null) {
            // Create the Bukkit listener
            this.bukkitListener = new PermissionsManagerBukkitListener(this);

            // Get the plugin manager instance
            PluginManager pluginManager = this.server.getPluginManager();

            // Register the Bukkit listener
            pluginManager.registerEvents(this.bukkitListener, this.plugin);
        }

        // Start
        if(start)
            start();
    }

    /**
     * Constructor.
     * This will automatically start the permissions manager.
     *
     * @param server Server instance
     * @param plugin Plugin instance
     * @param log    Logger
     */
    public PermissionsManager(Server server, Plugin plugin, Logger log) {
        this(server, plugin, log, true);
    }

    /**
     * Start the permissions manager.
     * This will automatically set up the manager to hook into available permission systems.
     */
    public void start() {
        // Make sure the permissions manager isn't started already
        if(isStarted())
            return;

        // Create and register the Bukkit listener on the server if it's valid
        if(this.server != null) {
            // Create the Bukkit listener
            this.bukkitListener = new PermissionsManagerBukkitListener(this);

            // Get the plugin manager instance
            PluginManager pluginManager = this.server.getPluginManager();

            // Register the Bukkit listener
            pluginManager.registerEvents(this.bukkitListener, this.plugin);

            // Show a status message.
            this.log.info("Started permission plugins state listener!");
        }

        // Set the started flag
        this.started = true;

        // Set up the manager
        setup();
    }

    /**
     * Stop the permissions manager.
     */
    public void stop() {
        // Make sure the manager is started
        if(!isStarted())
            return;

        // Disable the Bukkit listener if it was initialized and if the server instance is set
        if(this.server != null && this.bukkitListener != null)
            this.bukkitListener.setEnabled(false);

        // Unhook
        unhook();

        // Set the started flag
        this.started = false;
    }

    /**
     * Check whether the permissions manager is started.
     *
     * @return True if the permissions manager is started.
     */
    public boolean isStarted() {
        return this.started;
    }

    /**
     * Check if the permissions manager is currently hooked into any of the supported permissions systems.
     *
     * @return False if there isn't any permissions system used.
     *
     * @deprecated Replaced with isStarted() and isHooked()
     */
    @Deprecated
    public boolean isEnabled() {
        return isHooked();
    }

    /**
     * Check whether the permission manager is hooked into any permissions system plugin.
     * False will also be returned if the permissions manager isn't started.
     *
     * @return True if properly hooked into a permissions system plugin, false otherwise.
     */
    public boolean isHooked() {
        return isStarted() && this.permsType != null;
    }

    /**
     * Return the permissions system where the permissions manager is currently hooked into.
     * Null is returned if no permissions system is hooked.
     *
     * @return Permissions system type or null.
     */
    public PermissionsSystemType getUsedPermissionsSystemType() {
        return this.permsType;
    }

    /**
     * Setup and hook into the permissions systems.
     *
     * @return The detected permissions system.
     */
    public PermissionsSystemType setup() {
        unhook();
        this.permsType = PermissionsSystemType.PERMISSIONS_BUKKIT;
        this.log.info("Using Bukkit permissions!");
        return this.permsType;
    }

    /**
     * Break the hook with all permission systems.
     * A status message will be print to the log if a permissions system was current hooked.
     */
    public void unhook() {
        // Store the permissions system that was hooked
        PermissionsSystemType hookedSystem = getUsedPermissionsSystemType();

        // Reset the current used permissions system
        this.permsType = null;

        // Print a status message to the console
        if (hookedSystem != null)
            this.log.info("Unhooked from " + hookedSystem.getName() + "!");
    }

    /**
     * Reload the permissions manager, and re-hook all permission plugins.
     *
     * @return True on success, false on failure.
     * If no permissions system was hooked because none is available, true will be returned too.
     */
    public boolean reload() {
        // Unhook all permission plugins
        unhook();

        // Set up the permissions manager again, return the result
        setup();
        return true;
    }

    /**
     * Check whether a plugin is a permissions system plugin that is supported by the permissions manager.
     *
     * @param plugin The plugin to check.
     * @return True if the plugin is a supported permissions system, false if not.
     */
    public boolean isSupportedPlugin(Plugin plugin) {
        // Make sure the plugin isn't null
        if (plugin == null)
            return false;

        // Check whether this plugin is supported by it's name
        return isSupportedPlugin(plugin.getName());
    }

    /**
     * Check whether a plugin is supported by the permissions manager by it's plugin name.
     * The name of the plugin is case sensitive.
     *
     * @param pluginName The name of the plugin.
     * @return True if the plugin is supported, false if not.
     */
    public boolean isSupportedPlugin(String pluginName) {
        // Make sure the name isn't empty
        if (pluginName.trim().length() == 0)
            return false;

        // Loop through the list with permissions systems, and compare it's plugin name to the given name
        for (PermissionsSystemType type : PermissionsSystemType.values())
            if (type.getPluginName().equals(pluginName))
                return true;

        // This doesn't seem to be a supported permissions system plugin, return false
        return false;
    }

    /**
     * Method called when a plugin is being enabled.
     *
     * @param event Event instance.
     */
    public void onPluginEnable(PluginEnableEvent event) {
        // Get the plugin and it's name
        Plugin plugin = event.getPlugin();
        String pluginName = plugin.getName();

        // Check if any known permissions system is enabling
        if (isSupportedPlugin(plugin)) {
            this.log.info(pluginName + " plugin enabled, dynamically updating permissions hooks!");
            setup();
        }
    }

    /**
     * Method called when a plugin is being disabled.
     *
     * @param event Event instance.
     */
    public void onPluginDisable(PluginDisableEvent event) {
        // Get the plugin instance and name
        Plugin plugin = event.getPlugin();
        String pluginName = plugin.getName();

        // Is the WorldGuard plugin disabled
        if (isSupportedPlugin(plugin)) {
            this.log.info(pluginName + " plugin disabled, updating hooks!");
            setup();
        }
    }

    /**
     * Get the logger instance.
     *
     * @return Logger instance.
     */
    public Logger getLogger() {
        return this.log;
    }

    /**
     * Set the logger instance.
     *
     * @param log Logger instance.
     */
    public void setLogger(Logger log) {
        this.log = log;
    }

    /**
     * Get the permissions manager Bukkit listener instance.
     *
     * @return Listener instance.
     */
    public PermissionsManagerBukkitListener getListener() {
        return this.bukkitListener;
    }

    /**
     * Check if the player has permission. If no permissions system is used, the player has to be OP.
     *
     * @param player    The player.
     * @param permsNode Permissions node.
     * @return True if the player has permission.
     */
    public boolean hasPermission(Player player, String permsNode) {
        return hasPermission(player, permsNode, player.isOp());
    }

    /**
     * Check if a player has permission.
     *
     * @param player    The player.
     * @param permsNode The permission node.
     * @param def       Default returned if no permissions system is used.
     * @return True if the player has permission.
     */
    public boolean hasPermission(Player player, String permsNode, boolean def) {
        // Make sure the manager is started and is hooked into a permissions system
        if(!isStarted() || !isHooked())
            return def;

        // Check whether OP players always have permission
        if(isAlwaysAllowOp() && player.isOp())
            return true;

        // Get the AuthMe Reloaded handler
        AuthMeReloadedApiProvider authMeReloadedApiProvider = Core.getAuthMeReloadedHandler();

        // Make sure the user is authenticated if AuthMe Reloaded is hooked
        // TODO: Move this to some sort of listener, to keep this permissions manager universal!
        if(Core.getConfigHandler().authMeReloadedMustBeRegistered &&
                authMeReloadedApiProvider.isHooked() &&
                !authMeReloadedApiProvider.isAuthenticated(player))
                return false;

        // Use the proper API
        switch (this.permsType) {
            case PERMISSIONS_BUKKIT:
                return player.hasPermission(permsNode);
        }

        // Failed, return the default
        return def;
    }

    /**
     * Check whether the current permissions system has group support.
     * If no permissions system is hooked, false will be returned.
     *
     * @return True if the current permissions system supports groups, false otherwise.
     */
    public boolean hasGroupSupport() {
        // Make sure the manager is enabled and is hooked into a permissions system
        if(!isEnabled() || !isHooked())
            return false;

        return false;
    }

    /**
     * Get the permission groups of a player, if available.
     *
     * @param player The player.
     * @return Permission groups, or an empty list if this feature is not supported.
     */
    public List<String> getGroups(Player player) {
        // Make sure the manager is enabled and is hooked into a permissions system
        if(!isEnabled() || !isHooked())
            return new ArrayList<>();

        return new ArrayList<>();
    }

    /**
     * Get the primary group of a player, if available.
     *
     * @param player The player.
     * @return The name of the primary permission group. Or null.
     */
    public String getPrimaryGroup(Player player) {
        // Make sure the manager is enabled and is hooked into a permissions system
        if(!isEnabled() || !isHooked())
            return null;

        List<String> groups = getGroups(player);
        return groups.isEmpty() ? null : groups.get(0);
    }

    /**
     * Check whether the player is in the specified group.
     *
     * @param player    The player.
     * @param groupName The group name.
     * @return True if the player is in the specified group, false otherwise.
     * False is also returned if groups aren't supported by the used permissions system.
     */
    public boolean inGroup(Player player, String groupName) {
        // Make sure the manager is enabled and is hooked into a permissions system
        if(!isEnabled() || !isHooked())
            return false;

        return getGroups(player).contains(groupName);
    }

    /**
     * Add the permission group of a player, if supported.
     *
     * @param player    The player
     * @param groupName The name of the group.
     * @return True if succeed, false otherwise.
     * False is also returned if this feature isn't supported for the current permissions system.
     */
    public boolean addGroup(Player player, String groupName) {
        // Make sure the manager is started and is hooked into a permissions system
        if(!isStarted() || !isHooked())
            return false;

        return false;
    }

    /**
     * Add the permission groups of a player, if supported.
     *
     * @param player     The player
     * @param groupNames The name of the groups to add.
     * @return True if succeed, false otherwise.
     * False is also returned if this feature isn't supported for the current permissions system.
     */
    public boolean addGroups(Player player, List<String> groupNames) {
        // Make sure the manager is enabled and is hooked into a permissions system
        if(!isEnabled() || !isHooked())
            return false;

        // Add each group to the user
        boolean result = true;
        for (String groupName : groupNames)
            if (!addGroup(player, groupName))
                result = false;

        // Return the result
        return result;
    }

    /**
     * Remove the permission group of a player, if supported.
     *
     * @param player    The player
     * @param groupName The name of the group.
     * @return True if succeed, false otherwise.
     * False is also returned if this feature isn't supported for the current permissions system.
     */
    public boolean removeGroup(Player player, String groupName) {
        // Make sure the manager is enabled and is hooked into a permissions system
        if(!isEnabled() || !isHooked())
            return false;

        return false;
    }

    /**
     * Remove the permission groups of a player, if supported.
     *
     * @param player     The player
     * @param groupNames The name of the groups to add.
     * @return True if succeed, false otherwise.
     * False is also returned if this feature isn't supported for the current permissions system.
     */
    public boolean removeGroups(Player player, List<String> groupNames) {
        // Make sure the manager is enabled and is hooked into a permissions system
        if(!isEnabled() || !isHooked())
            return false;

        // Add each group to the user
        boolean result = true;
        for(String groupName : groupNames)
            if(!removeGroup(player, groupName))
                result = false;

        // Return the result
        return result;
    }

    /**
     * Set the permission group of a player, if supported.
     * This clears the current groups of the player.
     *
     * @param player    The player
     * @param groupName The name of the group.
     * @return True if succeed, false otherwise.
     * False is also returned if this feature isn't supported for the current permissions system.
     */
    public boolean setGroup(Player player, String groupName) {
        // Make sure the manager is enabled and is hooked into a permissions system
        if(!isEnabled() || !isHooked())
            return false;

        return false;
    }

    /**
     * Set the permission groups of a player, if supported.
     * This clears the current groups of the player.
     *
     * @param player     The player
     * @param groupNames The name of the groups to set.
     * @return True if succeed, false otherwise.
     * False is also returned if this feature isn't supported for the current permissions system.
     */
    public boolean setGroups(Player player, List<String> groupNames) {
        // Make sure the manager is enabled and is hooked into a permissions system
        if(!isEnabled() || !isHooked())
            return false;

        // Set the main group
        if (!setGroup(player, groupNames.get(0)))
            return false;

        // Add the rest of the groups
        boolean result = true;
        for (int i = 1; i < groupNames.size(); i++) {
            // Get the group name
            String groupName = groupNames.get(i);

            // Add this group
            if (!addGroup(player, groupName))
                result = false;
        }

        // Return the result
        return result;
    }

    /**
     * Remove all groups of the specified player, if supported.
     * Systems like Essentials GroupManager don't allow all groups to be removed from a player, thus the user will stay
     * in it's primary group. All the subgroups are removed just fine.
     *
     * @param player The player to remove all groups from.
     * @return True if succeed, false otherwise.
     * False will also be returned if this feature isn't supported for the used permissions system.
     */
    public boolean removeAllGroups(Player player) {
        // Make sure the manager is enabled and is hooked into a permissions system
        if(!isEnabled() || !isHooked())
            return false;

        // Get a list of current groups
        List<String> groupNames = getGroups(player);

        // Remove each group
        return removeGroups(player, groupNames);
    }

    /**
     * Check whether OP players should always have permission.
     *
     * @return True to always allow OP players, false to use the regular permissions system.
     */
    public boolean isAlwaysAllowOp() {
        return this.alwaysAllowOp;
    }

    /**
     * Set whether OP players always have permission.
     *
     * @param alwaysAllowOp True if OP players should always have permission.
     */
    public void setAlwaysAllowOp(boolean alwaysAllowOp) {
        this.alwaysAllowOp = alwaysAllowOp;
    }

    /**
     * The various permission system types.
     * This is used to identify all the permission system types that are supported by the permissions manager.
     */
    public enum PermissionsSystemType {
        /**
         * Permissions Bukkit.
         */
        PERMISSIONS_BUKKIT("Permissions Bukkit", "PermissionsBukkit");

        /**
         * The display name of the permissions system.
         */
        public String name;

        /**
         * The name of the permissions system plugin.
         */
        public String pluginName;

        /**
         * Constructor for PermissionsSystemType.
         *
         * @param name       Display name of the permissions system.
         * @param pluginName Name of the plugin.
         */
        PermissionsSystemType(String name, String pluginName) {
            this.name = name;
            this.pluginName = pluginName;
        }

        /**
         * Get the display name of the permissions system.
         *
         * @return Display name.
         */
        public String getName() {
            return this.name;
        }

        /**
         * Return the plugin name.
         *
         * @return Plugin name.
         */
        public String getPluginName() {
            return this.pluginName;
        }

        /**
         * Cast the permissions system type to a string.
         *
         * @return The display name of the permissions system.
         */
        @Override
        public String toString() {
            return getName();
        }
    }

    /**
     * Permissions manager Bukkit listener.
     * This listener is automatically deployed to listen for Bukkit plugin changes so the permissions manager can
     * react on them accordingly.
     */
    public class PermissionsManagerBukkitListener implements Listener {

        /**
         * The permissions manager instance.
         */
        private PermissionsManager permissionsManager;

        /**
         * Whether the listener is enabled or not.
         */
        private boolean enabled = true;

        /**
         * Constructor.\
         *
         * @param permissionsManager Permissions manager instance.
         */
        public PermissionsManagerBukkitListener(PermissionsManager permissionsManager) {
            this.permissionsManager = permissionsManager;
        }

        /**
         * Check whether the listener is enabled.
         *
         * @return True if the listener is enabled.
         */
        public boolean isEnabled() {
            return this.enabled;
        }

        /**
         * Set whether the listener is enabled.
         * Disabling the listener will stop the event handling until it's enabled again.
         *
         * @param enabled True if enabled, false if disabled.
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * Called when a plugin is enabled.
         *
         * @param event Event reference.
         */
        @EventHandler
        public void onPluginEnable(PluginEnableEvent event) {
            // Make sure the listener is enabled
            if(!isEnabled())
                return;

            // Make sure the permissions manager is set
            if(this.permissionsManager == null)
                return;

            // Call the onPluginEnable method in the permissions manager
            permissionsManager.onPluginEnable(event);
        }

        /**
         * Called when a plugin is disabled.
         *
         * @param event Event reference.
         */
        @EventHandler
        public void onPluginDisable(PluginDisableEvent event) {
            // Make sure the listener is enabled
            if(!isEnabled())
                return;

            // Make sure the permissions manager is set
            if(this.permissionsManager == null)
                return;

            // Call the onPluginDisable method in the permissions manager
            permissionsManager.onPluginDisable(event);
        }
    }
}
