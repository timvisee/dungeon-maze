package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.Random;


import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.util.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import com.timvisee.dungeonmaze.event.generation.GenerationSpawnerEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class SpawnerPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .06f;

    /** Populator constants. */
	private static final double SPAWN_DISTANCE_MIN = 2; // Chunks

    // TODO: Implement this feature!
	public static final double CHANCE_TORCH_ADDITION_EACH_LEVEL = -0.5; /* to 3 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		Chunk chunk = args.getSourceChunk();
		Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();
		
		// Make sure the distance between the spawn and the current chunk is allowed
		if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < SPAWN_DISTANCE_MIN)
			return;

        int spawnerX = x + rand.nextInt(6) + 1;
        int spawnerY = args.getFloorY() + 1;
        int spawnerZ = z + rand.nextInt(6) + 1;

        if(chunk.getBlock(spawnerX, spawnerY - 1, spawnerZ).getType() != Material.AIR) {
            Block spawnerBlock = chunk.getBlock(spawnerX, spawnerY, spawnerZ);

            if(spawnerBlock.getType() == Material.AIR) {
                // Generate a random spawnedType for the spawner
                EntityType spawnedType;
                int i = rand.nextInt(25) + 1;
                if(i >= 1 && i <= 10 && Core.getConfigHandler().isMobSpawnerAllowed("Zombie"))
                    spawnedType = EntityType.ZOMBIE;

                else if(i >= 11 && i <= 15 && Core.getConfigHandler().isMobSpawnerAllowed("Skeleton"))
                    spawnedType = EntityType.SKELETON;

                else if(i >= 16 && i <= 20 && Core.getConfigHandler().isMobSpawnerAllowed("Spider"))
                    spawnedType = EntityType.SPIDER;

                else if(i >= 21 && i <= 22 && Core.getConfigHandler().isMobSpawnerAllowed("PigZombie"))
                    spawnedType = EntityType.ZOMBIFIED_PIGLIN;

                else if(i == 23 && Core.getConfigHandler().isMobSpawnerAllowed("Enderman"))
                    spawnedType = EntityType.ENDERMAN;

                else if(i == 24 && Core.getConfigHandler().isMobSpawnerAllowed("MagmaCube"))
                    spawnedType = EntityType.MAGMA_CUBE;

                else if(i == 25 && Core.getConfigHandler().isMobSpawnerAllowed("Silverfish"))
                    spawnedType = EntityType.SILVERFISH;

                else if(Core.getConfigHandler().isMobSpawnerAllowed("Zombie"))
                    spawnedType = EntityType.ZOMBIE;

                else // if no entity type is allowed and the random return none value, continue the for loop
                    return;

                // Call the spawner generation event
                GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, spawnedType, GenerationSpawnerEvent.GenerationSpawnerCause.NORMAL, rand);
                Bukkit.getServer().getPluginManager().callEvent(event);

                // Apply the generation event
                event._apply();
            }
        }
	}
	
    @Override
    public float getRoomChance() {
        return ROOM_CHANCE;
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