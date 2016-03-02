package com.timvisee.dungeonmaze.update.universalnew;

public class Dependency {

    private String name;

    /**
     * Constructor.
     *
     *@param name The dependency name.
     */
    public Dependency(String name) {
        this.name = name;
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
