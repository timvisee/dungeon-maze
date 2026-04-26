package com.timvisee.dungeonmaze.config;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class ConfigHandler {

    // Configuration cache
    public FileConfiguration config;
    public boolean unloadWorldsOnPluginDisable;
    public boolean allowSurface;
    public boolean worldProtection;
    public Set<Material> blockWhiteList;
    public boolean enableUpdateCheckerOnStartup;
    public boolean usePermissions;
    public boolean useBypassPermissions;
    public boolean alwaysAllowOp;
    public boolean authMeReloadedMustBeRegistered;
    public List<String> mobs;

    public void load() {
        // Get the config instance
        config = new Config();

        // Load (and cache) the properties
        unloadWorldsOnPluginDisable = config.getBoolean("unloadWorldsOnPluginDisable", true);
        allowSurface = config.getBoolean("allowSurface", true);
        worldProtection = config.getBoolean("worldProtection", false);
        enableUpdateCheckerOnStartup = config.getBoolean("updateChecker.enabled", true);
        usePermissions = config.getBoolean("usePermissions", true);
        useBypassPermissions = config.getBoolean("useBypassPermissions", true);
        alwaysAllowOp = config.getBoolean("alwaysAllowOp", false);
        authMeReloadedMustBeRegistered = config.getBoolean("authMeReloadedMustBeRegistered", true);
        blockWhiteList = loadBlockWhiteList();
        mobs = config.getStringList("mobs");
    }

    /**
     * Load the block whitelist as 1.13+ material names.
     *
     * @return Parsed whitelist materials.
     */
    private Set<Material> loadBlockWhiteList() {
        final Set<Material> whiteList = EnumSet.noneOf(Material.class);

        for(Object entry : config.getList("blockWhiteList", Collections.emptyList())) {
            if(entry instanceof String) {
                final Material material = Material.matchMaterial((String) entry);
                if(material == null) {
                    Core.getLogger().warning("Ignoring unknown blockWhiteList material: " + entry);
                    continue;
                }
                if(!material.isBlock()) {
                    Core.getLogger().warning("Ignoring non-block blockWhiteList material: " + entry);
                    continue;
                }

                whiteList.add(material);
                continue;
            }

            if(entry != null)
                Core.getLogger().warning("Ignoring legacy blockWhiteList entry '" + entry + "'. Use 1.13+ material names.");
        }

        return whiteList;
    }

    /**
     * Check whether a block is in the block whitelist or not
     *
     * @param material Block type
     *
     * @return true if the object is in the list
     */
    public boolean isInWhiteList(Material material) {
        return material != null && Core.getConfigHandler().blockWhiteList.contains(material);
    }

    /**
     * Check whether a mob spawner is allowed or not
     *
     * @param mob The name of the mob
     *
     * @return True if the mob spawner is allowed for this mob
     */
    public boolean isMobSpawnerAllowed(String mob) {
        return Core.getConfigHandler().mobs.contains(mob);
    }
}
