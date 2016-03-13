package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.Random;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.util.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.GenerationSpawnerEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class BossRoomEasyPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 6;
	private static final float ROOM_CHANCE = .004f;

    /** Populator constants. */
	private static final double SPAWN_DISTANCE_MIN = 10; // Chunks

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World world = args.getWorld();
		Chunk chunk = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getRoomChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getRoomChunkZ();
		
		// Make sure the distance between the spawn and this chunk is allowed
		if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < SPAWN_DISTANCE_MIN)
			return;

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, y, z);

        // Create the floor
        for(int x2 = x; x2 < x + 7; x2 += 1)
            for(int z2 = z; z2 < z + 7; z2 += 1)
                chunk.getBlock(x2, yFloor, z2).setType(Material.MOSSY_COBBLESTONE);

        // Create the spawners
        if(Core.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
            Block spawnerBlock = chunk.getBlock(x + 1, yFloor + 1, z + 1);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_EASY, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner s = (CreatureSpawner) spawnerBlock.getState();

                    // Set the spawned type of the spawner
                    s.setSpawnedType(event.getSpawnedType());

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }
        if(Core.getConfigHandler().isMobSpawnerAllowed("PigZombie")) {
            Block spawnerBlock = chunk.getBlock(x + 3, yFloor + 1, z + 3);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.PIG_ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_EASY, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner s = (CreatureSpawner) spawnerBlock.getState();

                    // Set the spawned type of the spawner
                    s.setSpawnedType(event.getSpawnedType());

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }
        if(Core.getConfigHandler().isMobSpawnerAllowed("Spider")) {
            Block spawnerBlock = chunk.getBlock(x + 5, yFloor + 1, z + 5);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.SPIDER, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_EASY, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                try {
                    // Cast the created spawner into a CreatureSpawner object
                    CreatureSpawner s = (CreatureSpawner) spawnerBlock.getState();

                    // Set the spawned type of the spawner
                    s.setSpawnedType(event.getSpawnedType());

                } catch(Exception ex) {
                    // Show a proper error message
                    Core.getLogger().error("Failed to set spawner type to " + event.getSpawnedType().name());
                }
            }
        }

        // Coal ores
        chunk.getBlock(x + 1, yFloor + 1, z + 5).setType(Material.COAL_ORE);
        chunk.getBlock(x + 5, yFloor + 1, z + 1).setType(Material.COAL_ORE);
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