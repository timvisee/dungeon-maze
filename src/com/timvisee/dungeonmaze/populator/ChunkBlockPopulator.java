package com.timvisee.dungeonmaze.populator;

import java.util.Random;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonChunkGrid;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonChunkGridManager;
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
            DungeonChunkGridManager chunkGridManager = Core.getDungeonChunkGridManager();
            if(chunkGridManager == null) {
                Core.getLogger().error("Unable to generate Dungeon Maze chunk, couldn't access the chunk grid manager!");
                return;
            }

            // Create or get the chunk grid for the current world
            DungeonChunkGrid dungeonChunkGrid = chunkGridManager.getOrCreateChunkGrid(world);

            // Create or get the chunk data for the current chunk
            dungeonChunk = dungeonChunkGrid.getOrCreateChunk(chunk.getX(), chunk.getZ());

        } catch(Exception ex) {
            Core.getLogger().error("Unable to generate Dungeon Maze chunk, couldn't access the chunk grid manager!");
            ex.printStackTrace();
            return;
        }

		// Make sure the chunk isn't custom
		if(dungeonChunk.isCustomChunk())
			return;
		
		// Construct the MazeBlockPopulatorArgs to use the the populateMaze method
		ChunkBlockPopulatorArgs args = new ChunkBlockPopulatorArgs(world, rand, chunk, dungeonChunk);
		
		// Populate the maze
		populateChunk(args);
	}
	
	/**
	 * Population method.
     *
	 * @param args Populator arguments.
	 */
	public abstract void populateChunk(ChunkBlockPopulatorArgs args);
}
