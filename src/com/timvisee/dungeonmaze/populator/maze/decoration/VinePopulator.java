package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;

import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class VinePopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_OF_VINE = 30;
	public static final double CHANCE_OF_TOPTURVE_ADDITION_PER_LEVEL = -2.5; /* to 15 */
	public static final int ITERATIONS = 5;
	public static final int CHANCE_OF_CEILING_VINE = 5;
	public static final int ITERATIONS_CEILING_VINE = 5;

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			if (rand.nextInt(100) < CHANCE_OF_VINE+(CHANCE_OF_TOPTURVE_ADDITION_PER_LEVEL*(y-30)/6)) {
				
				int vineX;
				int vineY;
				int vineZ;
				
				switch(rand.nextInt(4)) {
				case 0:
					vineX = 0;
					vineY = rand.nextInt(4) + 2;
					vineZ = rand.nextInt(6) + 1;
					
					if(c.getBlock(x + vineX, y + vineY, z + vineZ).getTypeId() == 98) {
						c.getBlock(x + vineX + 1, y + vineY, z + vineZ).setTypeId(106);
						c.getBlock(x + vineX + 1, y + vineY, z + vineZ).setData((byte) 2);
					}
					
					break;
				case 1:
					vineX = 7;
					vineY = rand.nextInt(3) + 3;
					vineZ = rand.nextInt(6) + 1;
					
					if(c.getBlock(x + vineX, y + vineY, z + vineZ).getTypeId() == 98) {
						c.getBlock(x + vineX - 1, y + vineY, z + vineZ).setTypeId(106);
						c.getBlock(x + vineX - 1, y + vineY, z + vineZ).setData((byte) 8);
					}
					
					break;
				case 2:
					vineX = rand.nextInt(6) + 1;
					vineY = rand.nextInt(3) + 3;
					vineZ = 0;
					
					if(c.getBlock(x + vineX, y + vineY, z + vineZ).getTypeId() == 98) {
						c.getBlock(x + vineX, y + vineY, z + vineZ + 1).setTypeId(106);
						c.getBlock(x + vineX, y + vineY, z + vineZ + 1).setData((byte) 4);
					}
					
					break;
				case 3:
					vineX = rand.nextInt(6) + 1;
					vineY = rand.nextInt(3) + 3;
					vineZ = 7;
					
					if(c.getBlock(x + vineX, y + vineY, z + vineZ).getTypeId() == 98) {
						c.getBlock(x + vineX, y + vineY, z + vineZ - 1).setTypeId(106);
						c.getBlock(x + vineX, y + vineY, z + vineZ - 1).setData((byte) 1);
					}
					
					break;
				default:
				}	
			}
		}
		
		// Iterate
		for(int i = 0; i < ITERATIONS_CEILING_VINE; i++) {
			if (rand.nextInt(100) < CHANCE_OF_CEILING_VINE) {
				
				int vineX = rand.nextInt(6) + 1;
				int vineY = args.getCeilingY() - 1;
				int vineZ = rand.nextInt(6) + 1;
				
				c.getBlock(x + vineX, vineY, z + vineZ).setTypeId(106);
				c.getBlock(x + vineX, vineY, z + vineZ).setData((byte) 0);
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