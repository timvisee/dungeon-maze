package com.timvisee.dungeonmaze.populator.maze.decoration;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.util.MinecraftUtils;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

public class LanternPopulator extends MazeRoomBlockPopulator {

	private static final int LAYER_MIN = 3;
	private static final int LAYER_MAX = 7;
    private static final int ROOM_ITERATIONS = 3;
    private static final float ROOM_ITERATIONS_CHANCE = .3f;
    private static final int ROOM_ITERATIONS_MAX = 3;

    private static final float BROKEN_CHANCE = .33f;

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

        // Decide whether the lantern is broken or not
        final boolean broken = rand.nextFloat() < BROKEN_CHANCE;

        // Choose the lantern position
        final int lanternX = x + rand.nextInt(8);
        final int lanternY = y + rand.nextInt(4 - floorOffset) + 2 + floorOffset;
        final int lanternZ = z + rand.nextInt(8);
        final Block b = chunk.getBlock(lanternX, lanternY, lanternZ);

        // Make sure the lantern can be placed
        if(b.getType() != Material.COBBLESTONE && b.getType() != Material.MOSSY_COBBLESTONE && b.getType() != Material.SMOOTH_BRICK)
            return;

        // Place the actual lantern
        if(!broken) {
            // Use the sea lantern if the current version is compatible, use glowstone otherwise
            if(MinecraftUtils.getMinecraftVersion().startsWith("1.8"))
                b.setType(Material.SEA_LANTERN);
            else
                b.setType(Material.GLOWSTONE);

        } else
            b.setType(Material.REDSTONE_LAMP_OFF);
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
}
