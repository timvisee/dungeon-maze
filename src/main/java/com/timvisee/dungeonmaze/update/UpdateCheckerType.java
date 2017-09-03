package com.timvisee.dungeonmaze.update;

public enum UpdateCheckerType {

    /** Update checker type. */
    UNIVERSAL,
    BUKKIT;

    /**
     * Get the name of the updater type.
     *
     * Example outputs:
     * - Universal
     * - Bukkit
     *
     * @return The name.
     */
    public String getName() {
        return this.name().toUpperCase().substring(0, 1) + this.name().toLowerCase().substring(1);
    }
}
