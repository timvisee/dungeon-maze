package com.timvisee.DungeonMaze.Populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;
import com.timvisee.DungeonMaze.API.DungeonMazeAPI;

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
											spawnerBlock.setTypeId(52);
											
											// Set the spawn monster of the spawner
											try {
												CreatureSpawner theSpawner = (CreatureSpawner) spawnerBlock.getState();
												
												int i = random.nextInt(25) + 1;
												if(i >= 1 && i <= 10 && DungeonMazeAPI.allowMobSpawner("Zombie")) {
													theSpawner.setSpawnedType(EntityType.ZOMBIE);
													
												} else if(i >= 11 && i <= 15 && DungeonMazeAPI.allowMobSpawner("Skeleton")) {
													theSpawner.setSpawnedType(EntityType.SKELETON);
													
												} else if(i >= 16 && i <= 20 && DungeonMazeAPI.allowMobSpawner("Spider")) {
													theSpawner.setSpawnedType(EntityType.SPIDER);
													
												} else if(i >= 21 && i <= 22 && DungeonMazeAPI.allowMobSpawner("PigZombie")) {
													theSpawner.setSpawnedType(EntityType.PIG_ZOMBIE);
													
												} else if(i == 23 && DungeonMazeAPI.allowMobSpawner("Enderman")) {
													theSpawner.setSpawnedType(EntityType.ENDERMAN);
													
												} else if(i == 24 && DungeonMazeAPI.allowMobSpawner("MagmaCube")) {
													theSpawner.setSpawnedType(EntityType.MAGMA_CUBE);
													
												} else if(i == 25 && DungeonMazeAPI.allowMobSpawner("Silverfish")) {
													theSpawner.setSpawnedType(EntityType.SILVERFISH);
													
												} else if (DungeonMazeAPI.allowMobSpawner("Zombie")) {
													theSpawner.setSpawnedType(EntityType.ZOMBIE);
												} else {
													// if no zombie is allow and the random return none value, remove the spawner
													spawnerBlock.setTypeId(0);
												}
												
												
											}catch (Exception e) {
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
	}
	
	public double distance(int x1, int y1, int x2, int y2) {
		double dx   = x1 - x2;         //horizontal difference 
		double dy   = y1 - y2;         //vertical difference 
		double dist = Math.sqrt( dx*dx + dy*dy ); //distance using Pythagoras theorem
		return dist;
	}
}