package com.timvisee.dungeonmaze.util;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class MazeUtils {

	/**
	 * Get the level of Dungeon Maze at a specific location
	 * @param l Location to get the level of
	 * @return Dungeon Maze level, 0 if there's no level at the param location
	 */
	public static int getDMLevel(Location l) {
		return getDMLevel(l.getWorld().getName(), l.getBlockY());
	}

	/**
	 * Get the level of Dungeon Maze at a specific location
	 * @param b Block to get the level of
	 * @return Dungeon Maze level, 0 if there's no level at the param location
	 */
	public static int getDMLevel(Block b) {
		return getDMLevel(b.getWorld().getName(), b.getY());
	}
	
	/**
	 * Get the level of Dungeon Maze at a specific location in a world
	 * @param w Name of the world to check in
 	 * @param y Y coord to get the level of
	 * @return Dungeon Maze level, 0 if there's no level at the param location
	 */
	public static int getDMLevel(String w, int y) {
		// TODO: Make sure to update this if any modifications are going to be made (possible using config files, optionally)
		
		// Make sure the world name isn't an empty string
		if(w.equals(""))
			return 0;
		
		// Is the block bellow the Dungeon Maze?
		if(y < 30)
			return 0;
		
		// Check if the block is inside the Dungeon Maze, if so return it's level
		int curLevel = 1;
		for (int dml=30; dml < 30+(7*6); dml+=6) {
			if(dml >= y && dml + 6 < y)
				return curLevel;
			curLevel++;
		}
		
		// The block was above the Dungeon Maze, return zero
		return 0;
	}
}
