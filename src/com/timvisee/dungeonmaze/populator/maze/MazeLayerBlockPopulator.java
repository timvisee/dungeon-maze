package com.timvisee.dungeonmaze.populator.maze;

import java.util.Random;

import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import org.bukkit.Chunk;
import org.bukkit.World;

import com.timvisee.dungeonmaze.populator.ChunkBlockPopulator;
import com.timvisee.dungeonmaze.populator.ChunkBlockPopulatorArgs;

public abstract class MazeLayerBlockPopulator extends ChunkBlockPopulator {

	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int LAYER_COUNT = 7;
	
	@Override
	public void populateChunk(ChunkBlockPopulatorArgs args) {
		final World world = args.getWorld();
		Random rand = args.getRandom();
		final Chunk chunk = args.getSourceChunk();
        final DungeonChunk dungeonChunk = args.getDungeonChunk();

        // Make sure the dungeon chunk isn't custom
        if(dungeonChunk.isCustomChunk())
            return;

		// Get the minimum and maximum layer count
		int layerMin = Math.max(getMinimumLayer(), 1);
		int layerMax =  Math.min(getMaximumLayer(), LAYER_COUNT);

		// The layers
		for(int l = layerMin; l <= layerMax; l++) {

            // Iterate through this layer
            final int iterations = getLayerPopulationIterations();
            for(int i = 0; i < iterations; i++) {

                // Check whether this this layer should be populated based on it's chance
                if(rand.nextFloat() >= getLayerPopulationChance())
                    continue;

                // Make sure the dungeon chunk isn't custom
                if(dungeonChunk.isCustomChunk())
                    return;

                // Calculate the Y coordinate based on the current layer
                int y = 30 + ((l - 1) * 6);

                // Construct the MazePopulatorArgs to use the the populateMaze method
                MazeLayerBlockPopulatorArgs newArgs = new MazeLayerBlockPopulatorArgs(world, rand, chunk, dungeonChunk, l, y);

                // Populate the maze
                populateLayer(newArgs);
            }
		}
	}
	
	/**
	 * Population method.
     *
	 * @param args Populator arguments.
	 */
	public abstract void populateLayer(MazeLayerBlockPopulatorArgs args);
	
	/**
	 * Get the minimum layer.
     *
	 * @return Minimum layer.
	 */
	public int getMinimumLayer() {
		return MIN_LAYER;
	}
	
	/**
	 * Get the maximum layer.
     *
	 * @return Maximum layer.
	 */
	public int getMaximumLayer() {
		return MAX_LAYER;
	}

    /**
     * Get the layer population chance. This value is between 0.0 and 1.0.
     *
     * @return The population chance of the room.
     */
    public float getLayerPopulationChance() {
        return 1.0f;
    }

    /**
     * Get the number of times to iterate through each layer.
     *
     * @return The number of iterations.
     */
    public int getLayerPopulationIterations() {
        return 1;
    }
}
