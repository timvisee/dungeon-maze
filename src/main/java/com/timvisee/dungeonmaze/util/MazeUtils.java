package com.timvisee.dungeonmaze.util;

import com.timvisee.dungeonmaze.generator.DungeonMazeLayout;
import org.bukkit.Location;
import org.bukkit.block.Block;

@SuppressWarnings("UnusedDeclaration")
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
 	 * @param y Y coordinate to get the level of
	 * @return Dungeon Maze level, 0 if there's no level at the param location
	 */
	public static int getDMLevel(String w, int y) {
		// Make sure the world name isn't an empty string
		if(w.equals(""))
			return 0;

		return DungeonMazeLayout.getDungeonLevel(y);
	}
}
