package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class WebPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;
	public static final int CHANCE_WEB = 40;
	public static final double CHANCE_WEB_ADDITION_EACH_LEVEL = -1.667; /* to 30 */
	public static final int CHANCE_CORNER = 40;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		if(rand.nextInt(100) < CHANCE_WEB + (CHANCE_WEB_ADDITION_EACH_LEVEL * (y - 30) / 6)) {
			int webX = x + rand.nextInt(6) + 1;
			int webY = args.getFloorY() + 1;
			int webCeilingY = args.getCeilingY() - 1;
			int webZ = z + rand.nextInt(6) + 1;
			
			if(rand.nextInt(100) < CHANCE_CORNER)
				if(c.getBlock(x + (rand.nextInt(2)*5), webCeilingY, z + (rand.nextInt(2)*5)).getType() == Material.AIR)
					c.getBlock(x + (rand.nextInt(2)*5), webCeilingY, z + (rand.nextInt(2)*5)).setType(Material.WEB);
			else
				if(c.getBlock(webX, webY, webZ).getType() == Material.AIR)
					c.getBlock(webX, webY, webZ).setType(Material.WEB);
		}
	}
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return LAYER_MIN;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return LAYER_MAX;
	}
}