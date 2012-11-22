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
import com.timvisee.DungeonMaze.event.generation.DMGenerationSpawnerCause;
import com.timvisee.DungeonMaze.event.generation.DMGenerationSpawnerEvent;
import com.timvisee.DungeonMaze.DungeonMaze;

public class CreeperSpawnerRoomPopulator extends BlockPopulator {
	public static final int CHANCE_OF_SPAWNER = 3; //Promile
	public static final double CHANCE_OF_SPAWNER_ADDITION_PER_LEVEL = -0.333; /* to 1 */
	public static final double MIN_SPAWN_DISTANCE = 5; // Chunks

	@Override
	public void populate(World world, Random random, Chunk source) {
		// Hold the y on 30 because that's only the lowest layer
			
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y (the layer 2, 3 and 4)
			for (int y=30; y < 30+(5*6); y+=6) {
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(distance(0, 0, source.getX(), source.getZ()) >= MIN_SPAWN_DISTANCE) {
							if (random.nextInt(1000) < CHANCE_OF_SPAWNER+(CHANCE_OF_SPAWNER_ADDITION_PER_LEVEL*(y-30)/6)) {
								if(!DungeonMaze.isConstantRoom(world.getName(), source.getX(), source.getZ(), x, y, z)) {
									DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, y, z);
									
									int yfloorRelative = 0;
									Block roomBottomBlock = source.getBlock(x+2, y, z+2);  // x and z +2 so that you aren't inside a wall!
									int typeId = roomBottomBlock.getTypeId();
									if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  
										yfloorRelative++;
									}
									
									//core
									source.getBlock(x + 3, y + yfloorRelative + 1, z + 4).setTypeId(112);
									source.getBlock(x + 4, y + yfloorRelative + 1, z + 3).setTypeId(112);
									source.getBlock(x + 3, y + yfloorRelative + 1, z + 2).setTypeId(112);
									source.getBlock(x + 2, y + yfloorRelative + 1, z + 3).setTypeId(112);
									source.getBlock(x + 3, y + yfloorRelative + 2, z + 3).setTypeId(112);
									
									//spawner
									if (DungeonMazeAPI.allowMobSpawner("Creeper")) {
										Block spawnerBlock = source.getBlock(x + 3, y + yfloorRelative + 1, z + 3);
										
										// Call the spawner generation event
										DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.CREEPER, DMGenerationSpawnerCause.CREEPER_SPAWNER_ROOM, random);
										Bukkit.getServer().getPluginManager().callEvent(event);
										
										// Make sure the event isn't cancelled yet
										if(!event.isCancelled()) {
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