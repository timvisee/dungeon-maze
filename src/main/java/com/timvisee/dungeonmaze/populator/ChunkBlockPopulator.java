package com.timvisee.dungeonmaze.populator;

import java.util.Random;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonRegionGrid;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonRegionGridManager;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public abstract class ChunkBlockPopulator extends BlockPopulator {
	
	@Override
	public void populate(World world, Random rand, Chunk chunk) {
        // Store the dungeon chunk instance
        // TODO: Use some kind of caching, to speed up this process!
        DungeonChunk dungeonChunk;

        try {
            // Get the chunk grid manager, and make sure it's valid
            DungeonRegionGridManager chunkGridManager = Core.getDungeonRegionGridManager();
            if(chunkGridManager == null) {
                Core.getLogger().error("Unable to generate Dungeon Maze chunk, couldn't access the chunk grid manager!");
                return;
            }

            // Create or get the chunk grid for the current world
            final DungeonRegionGrid dungeonRegionGrid = chunkGridManager.getOrCreateRegionGrid(world);

            // Create or get the chunk data for the current chunk
            // TODO: Gather the chunk instance from some sort of cache!
            dungeonChunk = dungeonRegionGrid.getOrCreateChunk(chunk.getX(), chunk.getZ());

        } catch(Exception ex) {
            Core.getLogger().error("Unable to generate Dungeon Maze chunk, couldn't access the chunk grid manager!");
            ex.printStackTrace();
            return;
        }

        // Check whether this this chunk should be populated based on it's chance
        if(rand.nextFloat() >= getChunkChance())
            return;

        // Iterate through this chunk
        final int iterations = getChunkIterations();
        final int iterationsMax = getChunkIterationsMax();
        int iterationCount = 0;
        for(int i = 0; i < iterations; i++) {
            // Make sure we didn't iterate too many times
            if(iterationCount >= iterationsMax && iterationsMax >= 0)
                break;

            // Check whether this this chunk should be populated in the current iteration based on it's iteration chance
            if(rand.nextFloat() >= getChunkIterationsChance())
                continue;

            // Increase the iterations counter
            iterationCount++;

            // Make sure the chunk isn't custom
            if(dungeonChunk.isCustomChunk())
                return;

            // Construct the MazeBlockPopulatorArgs to use the the populateMaze method
            ChunkBlockPopulatorArgs args = new ChunkBlockPopulatorArgs(world, rand, chunk, dungeonChunk);

            // Populate the maze
            populateChunk(args);
        }
	}
	
	/**
	 * Population method.
     *
	 * @param args Populator arguments.
	 */
	public abstract void populateChunk(ChunkBlockPopulatorArgs args);

    /**
     * Get the chunk population chance. This value is between 0.0 and 1.0.
     *
     * @return The population chance of the chunk.
     */
    public float getChunkChance() {
        return 1.0f;
    }

    /**
     * Get the number of times to iterate through each chunk.
     *
     * @return The number of iterations.
     */
    public int getChunkIterations() {
        return 1;
    }

    /**
     * Get the chunk population chance for each iteration. This value is between 0.0 and 1.0.
     *
     * @return The population chance of the chunk.
     */
    public float getChunkIterationsChance() {
        return 1.0f;
    }

    /**
     * Get the maximum number of times to iterate based on the chance and iteration count.
     *
     * @return The maximum number of times to iterate. Return a negative number of ignore the maximum.
     */
    public int getChunkIterationsMax() {
        return -1;
    }
}
