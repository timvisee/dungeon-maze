package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.api.DungeonMazeAPI;
import com.timvisee.dungeonmaze.event.generation.DMGenerationSpawnerCause;
import com.timvisee.dungeonmaze.event.generation.DMGenerationSpawnerEvent;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class CreeperSpawnerRoomPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 5;
	public static final int CHANCE_OF_SPAWNER = 3; //Promile
	public static final double CHANCE_OF_SPAWNER_ADDITION_PER_LEVEL = -0.333; /* to 1 */
	public static final double MIN_SPAWN_DISTANCE = 5; // Chunks

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();
		
		// Make sure the distance between the spawn and the current chunk is allowed
		if(distance(0, 0, c.getX(), c.getZ()) < MIN_SPAWN_DISTANCE)
			return;
		
		// Apply chances
		if(rand.nextInt(1000) < CHANCE_OF_SPAWNER + (CHANCE_OF_SPAWNER_ADDITION_PER_LEVEL * (y - 30) / 6)) {
			// Register the current room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c.getX(), c.getZ(), x, y, z);
			
			// Create the core
			c.getBlock(x + 3, yFloor + 1, z + 4).setTypeId(112);
			c.getBlock(x + 4, yFloor + 1, z + 3).setTypeId(112);
			c.getBlock(x + 3, yFloor + 1, z + 2).setTypeId(112);
			c.getBlock(x + 2, yFloor + 1, z + 3).setTypeId(112);
			c.getBlock(x + 3, yFloor + 2, z + 3).setTypeId(112);
			
			// Create the spawner
			if (DungeonMazeAPI.allowMobSpawner("Creeper")) {
				Block spawnerBlock = c.getBlock(x + 3, yFloor + 1, z + 3);
				
				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.CREEPER, DMGenerationSpawnerCause.CREEPER_SPAWNER_ROOM, rand);
				Bukkit.getServer().getPluginManager().callEvent(event);
				
				// Make sure the event isn't cancelled yet
				if(!event.isCancelled()) {
					// Change the block into a creature spawner
					spawnerBlock.setTypeId(52);
					
					// Cast the created s pawner into a CreatureSpawner object
					CreatureSpawner s = (CreatureSpawner) spawnerBlock.getState();
					
					// Set the spawned type of the spawner
					s.setSpawnedType(event.getSpawnedType());
				}
			}
		}
	}
	
	public double distance(int x1, int y1, int x2, int y2) {
		double dx   = x1 - x2;         //horizontal difference 
		double dy   = y1 - y2;         //vertical difference 
		double dist = Math.sqrt( dx*dx + dy*dy ); //distance using Pythagoras theorem
		return dist;
	}
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return MIN_LAYER;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return MAX_LAYER;
	}
}