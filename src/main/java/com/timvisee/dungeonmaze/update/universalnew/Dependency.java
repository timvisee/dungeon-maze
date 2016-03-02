package com.timvisee.dungeonmaze.update.universalnew;

public class Dependency {

    /**
     * The ID of the dependency.
     */
    private String id;

    /**
     * The name of the dependency.
     */
    private String name;

    /**
     * Constructor.
     *
     * @param id The dependency ID.
     * @param name The dependency name.
     */
    public Dependency(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Get the dependency ID.
     *
     * @return Dependency ID.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get the dependency name.
     *
     * @return Dependency name.
     */
    public String getName() {
        return name;
    }
}
