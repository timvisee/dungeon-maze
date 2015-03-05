package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.Random;

import com.timvisee.dungeonmaze.Core;
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

public class CreeperSpawnerRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 5;
	public static final float ROOM_CHANCE = .003f;

    /** Populator constants. */
	public static final double SPAWN_DISTANCE_MIN = 5; // Chunks

    // TODO: Implement this!
    public static final double CHANCE_SPAWNER_ADDITION_EACH_LEVEL = -0.333; /* to 1 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();
		
		// Make sure the distance between the spawn and the current chunk is allowed
		if(distance(0, 0, c.getX(), c.getZ()) < SPAWN_DISTANCE_MIN)
			return;

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(w.getName(), c.getX(), c.getZ(), x, y, z);

        // Create the core
        c.getBlock(x + 3, yFloor + 1, z + 4).setType(Material.NETHER_BRICK);
        c.getBlock(x + 4, yFloor + 1, z + 3).setType(Material.NETHER_BRICK);
        c.getBlock(x + 3, yFloor + 1, z + 2).setType(Material.NETHER_BRICK);
        c.getBlock(x + 2, yFloor + 1, z + 3).setType(Material.NETHER_BRICK);
        c.getBlock(x + 3, yFloor + 2, z + 3).setType(Material.NETHER_BRICK);

        // Create the spawner
        if(Core.getConfigHandler().isMobSpawnerAllowed("Creeper")) {
            Block spawnerBlock = c.getBlock(x + 3, yFloor + 1, z + 3);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.CREEPER, GenerationSpawnerEvent.GenerationSpawnerCause.CREEPER_SPAWNER_ROOM, rand);
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
    }
	
	public double distance(int x1, int y1, int x2, int y2) {
		double dx = x1 - x2;         //horizontal difference
		double dy = y1 - y2;         //vertical difference
        return Math.sqrt( dx*dx + dy*dy );
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