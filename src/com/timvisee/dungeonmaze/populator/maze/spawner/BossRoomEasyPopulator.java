package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.Random;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.GenerationSpawnerEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class BossRoomEasyPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 6;
	public static final float ROOM_CHANCE = .004f;

    /** Populator constants. */
	public static final double SPAWN_DISTANCE_MIN = 10; // Chunks

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();
		
		// Make sure the distance between the spawn and this chunk is allowed
		if(distance(0, 0, c.getX(), c.getZ()) < SPAWN_DISTANCE_MIN)
			return;

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(w.getName(), c.getX(), c.getZ(), x, y, z);

        // Create the floor
        for(int x2 = x; x2 < x + 7; x2 += 1)
            for(int z2 = z; z2 < z + 7; z2 += 1)
                c.getBlock(x2, yFloor, z2).setType(Material.MOSSY_COBBLESTONE);

        // Create the spawners
        if(Core.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
            Block spawnerBlock = c.getBlock(x + 1, yFloor + 1, z + 1);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_EASY, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                // Cast the created s pawner into a CreatureSpawner object
                CreatureSpawner s = (CreatureSpawner) spawnerBlock.getState();

                // Set the spawned type of the spawner
                s.setSpawnedType(event.getSpawnedType());
            }
        }
        if(Core.getConfigHandler().isMobSpawnerAllowed("PigZombie")) {
            Block spawnerBlock = c.getBlock(x + 3, yFloor + 1, z + 3);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.PIG_ZOMBIE, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_EASY, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                // Cast the created s pawner into a CreatureSpawner object
                CreatureSpawner s = (CreatureSpawner) spawnerBlock.getState();

                // Set the spawned type of the spawner
                s.setSpawnedType(event.getSpawnedType());
            }
        }
        if(Core.getConfigHandler().isMobSpawnerAllowed("Spider")) {
            Block spawnerBlock = c.getBlock(x + 5, yFloor + 1, z + 5);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.SPIDER, GenerationSpawnerEvent.GenerationSpawnerCause.BOSSROOM_EASY, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Make sure the event isn't cancelled yet
            if(!event.isCancelled()) {
                // Change the block into a creature spawner
                spawnerBlock.setType(Material.MOB_SPAWNER);

                // Cast the created s pawner into a CreatureSpawner object
                BlockState bs = spawnerBlock.getState();
                CreatureSpawner s = (CreatureSpawner) bs;

                // Set the spawned type of the spawner
                s.setSpawnedType(event.getSpawnedType());
            }
        }

        // Coal ores
        c.getBlock(x + 1, yFloor + 1, z + 5).setType(Material.COAL_ORE);
        c.getBlock(x + 5, yFloor + 1, z + 1).setType(Material.COAL_ORE);
    }
	
	public double distance(int x1, int y1, int x2, int y2) {
		double dx   = x1 - x2;         //horizontal difference 
		double dy   = y1 - y2;         //vertical difference 
		double dist = Math.sqrt( dx*dx + dy*dy ); //distance using Pythagoras theorem
		return dist;
	}

    @Override
    public float getRoomPopulationChance() {
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