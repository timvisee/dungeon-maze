package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class LanternPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 3;
	public static final int LAYER_MAX = 7;
    public static final int ROOM_ITERATIONS = 3;
    public static final float ROOM_ITERATIONS_CHANCE = .3f;
    public static final int ROOM_ITERATIONS_MAX = 3;

    public static final float BROKEN_CHANCE = .33f;

    // TODO: Implement this feature!
    public static final double CHANCE_SINGLE_ADDITION_EACH_LEVEL = 7.5; /* to 75 */
    public static final double CHANCE_DOUBLE_ADDITION_EACH_LEVEL = 4.167; /* to 35 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
        final int x = args.getChunkX();
        final int y = args.getChunkY();
        final int z = args.getChunkZ();
        final int floorOffset = args.getFloorOffset();

        final boolean broken = rand.nextFloat() < BROKEN_CHANCE;

        final int lanternX = x + rand.nextInt(8);
        final int lanternY = y + rand.nextInt(4 - floorOffset) + 2 + floorOffset;
        final int lanternZ = z + rand.nextInt(8);
        final Block b = chunk.getBlock(lanternX, lanternY, lanternZ);

        if(b.getType() == Material.COBBLESTONE || b.getType() == Material.MOSSY_COBBLESTONE || b.getType() == Material.SMOOTH_BRICK) {
            if(!broken){
            	String MCversion;
                String raw = Bukkit.getVersion();
                int start = raw.indexOf("MC:");
                if (start == -1)
                	 MCversion = raw;
                start += 4;
                int end = raw.indexOf(')', start);
                MCversion = raw.substring(start, end);
            	boolean compatible = MCversion.startsWith("1.8");
                if (compatible)
                	b.setType(Material.SEA_LANTERN);
                else
                	b.setType(Material.GLOWSTONE);
            }
            else
                b.setType(Material.REDSTONE_LAMP_OFF);
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

    @Override
    public int getRoomIterations() {
        return ROOM_ITERATIONS;
    }

    @Override
    public float getRoomIterationsChance() {
        return ROOM_ITERATIONS_CHANCE;
    }

    @Override
    public int getRoomIterationsMax() {
        return ROOM_ITERATIONS_MAX;
    }

	// Deprecated, might use later again to rotate pumpkins correctly
	/*private byte getData(int x, int z, int x2, int z2, Material type) {
		if (x == x2) {
			if (z < z2)
				return (byte) (type == Material.JACK_O_LANTERN ? 0 : 2);
			return (byte) (type == Material.JACK_O_LANTERN ? 2 : 0);
		}
		if (x < x2)
			return (byte) (type == Material.JACK_O_LANTERN ? 3 : 1);
		return (byte) (type == Material.JACK_O_LANTERN ? 1 : 3);
	}*/
}
