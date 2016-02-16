package com.timvisee.dungeonmaze.server;

public enum ServerType {

    /** Server types. */
    UNKNOWN("Unknown"),
    BUKKIT("Bukkit"),
    SPIGOT("Spigot"),
    PAPER_SPIGOT("PaperSpigot"),
    KCAULDRON("KCauldron"),
    THERMOS("Thermos");

    /** Server type name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name Server type name.
     */
    ServerType(String name) {
        this.name = name;
    }

    /**
     * Get the server type name.
     *
     * @return Server type name.
     */
    public String getName() {
        return this.name;
    }
}
