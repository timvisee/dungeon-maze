package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class HighRoomPopulator extends MazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 6;
	public static final int CHANCE_OF_HIGHROOM = 6; //Promile

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		if(rand.nextInt(1000) < CHANCE_OF_HIGHROOM) {
			
			// Register the room above as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c, x, y + 6, z);
			
			// Remove the floor of the room above
			for (int x2=x; x2 <= x + 7; x2+=1) {
			    for (int y2=y + 5; y2 <= y + 8; y2+=1) {
			        for (int z2=z + 1; z2 <= z + 6; z2+=1) {
			            c.getBlock(x2, y2, z2).setType(Material.AIR);
			        }
			    }
			}
			for (int x2=x + 1; x2 <= x + 6; x2+=1) {
			    for (int y2=y + 5; y2 <= y + 8; y2+=1) {
			        for (int z2=z; z2 <= z + 7; z2+=1) {
			            c.getBlock(x2, y2, z2).setType(Material.AIR);
			        }
			    }
			}
		}
	}
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return MIN_LAYER;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return MAX_LAYER;
	}
}