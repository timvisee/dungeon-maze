package com.timvisee.DungeonMaze.populator;

import java.util.Random;


import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.API.DungeonMazeAPI;
import com.timvisee.DungeonMaze.event.generation.DMGenerationChestEvent;
import com.timvisee.DungeonMaze.event.generation.DMGenerationSpawnerCause;
import com.timvisee.DungeonMaze.event.generation.DMGenerationSpawnerEvent;
import com.timvisee.DungeonMaze.DungeonMaze;

public class SpawnerPopulator extends BlockPopulator {
	public static final int CHANCE_OF_SPAWNER = 6;
	public static final double CHANCE_OF_TORCH_ADDITION_PER_LEVEL = -0.5; /* to 3 */
	public static final double MIN_SPAWN_DISTANCE = 2; // Chunks

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(distance(0, 0, source.getX(), source.getZ()) >= MIN_SPAWN_DISTANCE) {
							if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
								if (random.nextInt(100) < CHANCE_OF_SPAWNER) {
									
									int spawnerX = x + random.nextInt(6) + 1;
									int spawnerY = y;
									int spawnerZ = z + random.nextInt(6) + 1;
									
									// Get the floor location 
									int yfloor = y;
									Block roomBottomBlock = source.getBlock(spawnerX, y, spawnerZ);
									int typeId = roomBottomBlock.getTypeId();
									if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!							
										yfloor++;
									}
									spawnerY = yfloor + 1;
									
									if(!(source.getBlock(spawnerX, spawnerY - 1, spawnerZ).getTypeId() == 0)) {
										Block spawnerBlock = source.getBlock(spawnerX, spawnerY, spawnerZ);
										spawnerBlock = source.getBlock(spawnerX, spawnerY, spawnerZ);
										if(spawnerBlock.getTypeId() == 0) {
											
											// Generate a random spawnedType for the spawner
											EntityType spawnedType = null;
											int i = random.nextInt(25) + 1;
											if(i >= 1 && i <= 10 && DungeonMazeAPI.allowMobSpawner("Zombie"))
												spawnedType = EntityType.ZOMBIE;
												
											else if(i >= 11 && i <= 15 && DungeonMazeAPI.allowMobSpawner("Skeleton"))
												spawnedType = EntityType.SKELETON;
												
											else if(i >= 16 && i <= 20 && DungeonMazeAPI.allowMobSpawner("Spider"))
												spawnedType = EntityType.SPIDER;
												
											else if(i >= 21 && i <= 22 && DungeonMazeAPI.allowMobSpawner("PigZombie"))
												spawnedType = EntityType.PIG_ZOMBIE;
												
											else if(i == 23 && DungeonMazeAPI.allowMobSpawner("Enderman"))
												spawnedType = EntityType.ENDERMAN;
											
											else if(i == 24 && DungeonMazeAPI.allowMobSpawner("MagmaCube"))
												spawnedType = EntityType.MAGMA_CUBE;
												
											else if(i == 25 && DungeonMazeAPI.allowMobSpawner("Silverfish"))
												spawnedType = EntityType.SILVERFISH;
												
											else if (DungeonMazeAPI.allowMobSpawner("Zombie"))
												spawnedType = EntityType.ZOMBIE;
											else {
												// if no entity type is allowed and the random return none value, continue the for loop
												continue;
											}
											
											// Call the spawner generation event
											DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, spawnedType, DMGenerationSpawnerCause.NORMAL, random);
											Bukkit.getServer().getPluginManager().callEvent(event);
											
											// Make sure the event isn't cancelled yet
											if(event.isCancelled())
												continue;
											
											// Change the block into a creature spawner
											spawnerBlock.setTypeId(52);
											
											// Cast the created s pawner into a CreatureSpawner object
											CreatureSpawner theSpawner = (CreatureSpawner) spawnerBlock.getState();
											
											// Set the spawned type of the spawner
											theSpawner.setSpawnedType(event.getSpawnedType());
										}
									}
								}
							}
						}
					}
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
}