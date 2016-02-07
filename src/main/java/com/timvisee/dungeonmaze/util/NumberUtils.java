package com.timvisee.dungeonmaze.util;

public class NumberUtils {

    /**
     * Calculate the distance between two 2D points.
     * Note: For the x and y coordinate of the first point, zero is used.
     *
     * @param x X coordinate of second point.
     * @param y Y coordinate of second point.
     *
     * @return Distance between the two points.
     */
    public static double distanceFromZero(int x, int y) {
        return distance(0, 0, x, y);
    }

    /**
     * Calculate the distance between two 2D points.
     *
     * @param x1 X coordinate of first point.
     * @param y1 Y coordinate of first point.
     * @param x2 X coordinate of second point.
     * @param y2 Y coordinate of second point.
     *
     * @return Distance between the two points.
     */
    public static double distance(int x1, int y1, int x2, int y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
