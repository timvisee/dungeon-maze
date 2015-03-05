package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class VinePopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 7;

	public static final int CHANCE_VINE = 30;
	public static final double CHANCE_TOP_TURVE_ADDITION_EACH_LEVEL = -2.5; /* to 15 */
	public static final int ITERATIONS = 5;
	public static final int CHANCE_CEILING_VINE = 5;
	public static final int ITERATIONS_CEILING_VINE = 5;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			if (rand.nextInt(100) < CHANCE_VINE +(CHANCE_TOP_TURVE_ADDITION_EACH_LEVEL *(y-30)/6)) {
				
				int vineX;
				int vineY;
				int vineZ;
				
				switch(rand.nextInt(4)) {
				case 0:
					vineX = 0;
					vineY = rand.nextInt(4) + 2;
					vineZ = rand.nextInt(6) + 1;
					
					if(c.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.SMOOTH_BRICK) {
						c.getBlock(x + vineX + 1, y + vineY, z + vineZ).setType(Material.VINE);
						c.getBlock(x + vineX + 1, y + vineY, z + vineZ).setData((byte) 2);
					}
					
					break;
				case 1:
					vineX = 7;
					vineY = rand.nextInt(3) + 3;
					vineZ = rand.nextInt(6) + 1;
					
					if(c.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.SMOOTH_BRICK) {
						c.getBlock(x + vineX - 1, y + vineY, z + vineZ).setType(Material.VINE);
						c.getBlock(x + vineX - 1, y + vineY, z + vineZ).setData((byte) 8);
					}
					
					break;
				case 2:
					vineX = rand.nextInt(6) + 1;
					vineY = rand.nextInt(3) + 3;
					vineZ = 0;
					
					if(c.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.SMOOTH_BRICK) {
						c.getBlock(x + vineX, y + vineY, z + vineZ + 1).setType(Material.VINE);
						c.getBlock(x + vineX, y + vineY, z + vineZ + 1).setData((byte) 4);
					}
					
					break;
				case 3:
					vineX = rand.nextInt(6) + 1;
					vineY = rand.nextInt(3) + 3;
					vineZ = 7;
					
					if(c.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.SMOOTH_BRICK) {
						c.getBlock(x + vineX, y + vineY, z + vineZ - 1).setType(Material.VINE);
						c.getBlock(x + vineX, y + vineY, z + vineZ - 1).setData((byte) 1);
					}
					
					break;
				default:
				}	
			}
		}
		
		// Iterate
		for(int i = 0; i < ITERATIONS_CEILING_VINE; i++) {
			if (rand.nextInt(100) < CHANCE_CEILING_VINE) {
				
				int vineX = rand.nextInt(6) + 1;
				int vineY = args.getCeilingY() - 1;
				int vineZ = rand.nextInt(6) + 1;
				
				c.getBlock(x + vineX, vineY, z + vineZ).setType(Material.VINE);
				c.getBlock(x + vineX, vineY, z + vineZ).setData((byte) 0);
			}
		}
	}

    @Override
    public float getRoomChance() {
        // TODO: Improve this!
        return 1.0f;
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