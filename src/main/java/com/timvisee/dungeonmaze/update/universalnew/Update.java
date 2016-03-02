package com.timvisee.dungeonmaze.update.universalnew;

import java.util.List;

public class Update {

    /**
     * The version name of this update.
     */
    private String versionName;

    /**
     * The version code of this update.
     */
    private int versionCode;

    /**
     * The update channels this update is in.
     */
    private List<String> channels;

    /**
     * Constructor.
     *
     * @param versionName The version name of this update.
     * @param versionCode The version code of this update.
     */
    public Update(String versionName, int versionCode, List<String> channels) {
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.channels = channels;
    }

    /**
     * Get the version name of this update.
     *
     * @return The version name.
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * Get the version code of this update.
     *
     * @return The version code.
     */
    public int getVersionCode() {
        return versionCode;
    }

    /**
     * Get the channels of this update.
     *
     * @return Update channels.
     */
    public List<String> getChannels() {
        return this.channels;
    }

    /**
     * Check whether this update is in a specific update channel.
     *
     * @param channelName The update channel name.
     *
     * @return True if the update is in the given update channel, false if not.
     */
    public boolean inChannel(String channelName) {
        return this.channels.contains(channelName);
    }

    /**
     * Check whether this update is in the specified update channel.
     *
     * @param channelType The update channel type.
     *
     * @return True if the update is in the given update channel, false if not.
     */
    public boolean inChannel(UpdateChannelType channelType) {
        return inChannel(channelType.getName());
    }
}
