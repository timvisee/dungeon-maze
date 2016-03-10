package com.timvisee.dungeonmaze.update.universalnew;

public enum UpdateChannelType {

    /**
     * The stable update channel.
     */
    STABLE("stable"),

    /**
     * The beta update channel.
     */
    BETA("beta"),

    /**
     * The alpha update channel.
     */
    ALPHA("alpha");

    String name;

    /**
     * Constructor.
     *
     * @param name Update channel name
     */
    UpdateChannelType(String name) {
        this.name = name;
    }

    /**
     * Get the name of this channel.
     *
     * @return The channel name.
     */
    public String getName() {
        return this.name;
    }
}

