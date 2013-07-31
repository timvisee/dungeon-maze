package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.DMGenerationSpawnerCause;
import com.timvisee.dungeonmaze.event.generation.DMGenerationSpawnerEvent;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class BossRoomEasyPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 6;
	public static final int CHANCE_OF_BOSSROOM = 4; //Promile
	public static final double MIN_SPAWN_DISTANCE = 10; // Chunks

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();
		
		// Make sure the distance between the spawn and this chunk is allowed
		if(distance(0, 0, c.getX(), c.getZ()) < MIN_SPAWN_DISTANCE)
			return;
			
		// Apply chances
		if(rand.nextInt(1000) < CHANCE_OF_BOSSROOM) {
			// Register the current room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c.getX(), c.getZ(), x, y, z);
			
			// Create the floor
			for(int x2 = x; x2 < x + 7; x2 += 1)
			    for(int z2 = z; z2 < z + 7; z2 += 1)
			        c.getBlock(x2, yFloor, z2).setTypeId(48);
			        
			// Create the spawners
			if(DungeonMaze.instance.getConfigHandler().isMobSpawnerAllowed("Zombie")) {
				Block spawnerBlock = c.getBlock(x + 1, yFloor + 1, z + 1);
				
				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, DMGenerationSpawnerCause.BOSSROOM_EASY, rand);
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
			if(DungeonMaze.instance.getConfigHandler().isMobSpawnerAllowed("PigZombie")) {
				Block spawnerBlock = c.getBlock(x + 3, yFloor + 1, z + 3);
				
				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.PIG_ZOMBIE, DMGenerationSpawnerCause.BOSSROOM_EASY, rand);
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
			if(DungeonMaze.instance.getConfigHandler().isMobSpawnerAllowed("Spider")) {
				Block spawnerBlock = c.getBlock(x + 5, yFloor + 1, z + 5);
				
				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.SPIDER, DMGenerationSpawnerCause.BOSSROOM_EASY, rand);
				Bukkit.getServer().getPluginManager().callEvent(event);
				
				// Make sure the event isn't cancelled yet
				if(!event.isCancelled()) {
					// Change the block into a creature spawner
					spawnerBlock.setTypeId(52);
					
					// Cast the created s pawner into a CreatureSpawner object
					BlockState bs = spawnerBlock.getState();
					CreatureSpawner s = (CreatureSpawner) bs;
					
					// Set the spawned type of the spawner
					s.setSpawnedType(event.getSpawnedType());
				}
			}
			
			// Coal ores
			c.getBlock(x + 1, yFloor + 1, z + 5).setTypeId(16);
			c.getBlock(x + 5, yFloor + 1, z + 1).setTypeId(16);
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