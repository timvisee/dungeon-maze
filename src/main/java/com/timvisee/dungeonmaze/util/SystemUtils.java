package com.timvisee.dungeonmaze.util;

public class SystemUtils {

    /**
     * Get the full architecture specification of the current OS.
     *
     * @return System architecture specification.
     */
    public static String getSystemArchFull() {
        return System.getProperty("os.arch");
    }

    /**
     * Get the system architecture number, such as 32 or 64.
     *
     * @return System architecture number.
     */
    public static int getSystemArchNumber() {
        return getSystemArchFull().contains("64") ? 64 : 32;
    }
}
