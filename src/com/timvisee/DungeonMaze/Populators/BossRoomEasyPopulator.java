package com.timvisee.DungeonMaze.populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class BossRoomEasyPopulator extends BlockPopulator {
	public static final int CHANCE_OF_BOSSROOM = 2; //Promile
	public static final double MIN_SPAWN_DISTANCE = 10; // Chunks

	@Override
	public void populate(World world, Random random, Chunk source) {
		// Hold the y on 30 because that's only the lowest layer
			
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(6*6); y+=6) {
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						if(distance(0, 0, source.getX(), source.getZ()) >= MIN_SPAWN_DISTANCE) {
							if (random.nextInt(1000) < CHANCE_OF_BOSSROOM) {
								if(!DungeonMaze.isConstantRoom(world.getName(), source.getX(), source.getZ(), x, y, z)) {
									DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, y, z);
									
									int yfloorRelative = 0;
									Block roomBottomBlock = source.getBlock(x+2, y, z+2);  // x and z +2 so that you aren't inside a wall!
									int typeId = roomBottomBlock.getTypeId();
									if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  
										yfloorRelative++;
									}
									
									//floor
									for (int x2=x; x2 < x + 7; x2+=1) {
									    for (int z2=z; z2 < z + 7; z2+=1) {
									        source.getBlock(x2, y + yfloorRelative, z2).setTypeId(48);
									    }
									}
									//spawners
									source.getBlock(x + 1, y + yfloorRelative + 1, z + 1).setTypeId(52);
									CreatureSpawner S1 = (CreatureSpawner) source.getBlock(x + 1, y + 1 + yfloorRelative, z + 1).getState();
									S1.setSpawnedType(EntityType.ZOMBIE);
									source.getBlock(x + 3, y + yfloorRelative + 1, z + 3).setTypeId(52);
									CreatureSpawner PigSpawner = (CreatureSpawner) source.getBlock(x + 3, y + 1 + yfloorRelative, z + 3).getState();
									PigSpawner.setSpawnedType(EntityType.PIG_ZOMBIE);
									source.getBlock(x + 5, y + yfloorRelative + 1, z + 5).setTypeId(52);
									CreatureSpawner S2 = (CreatureSpawner) source.getBlock(x + 5, y + 1 + yfloorRelative, z + 5).getState();
									S2.setSpawnedType(EntityType.SPIDER);
									//coal ores
									source.getBlock(x + 1, y + yfloorRelative + 1, z + 5).setTypeId(16);
									source.getBlock(x + 5, y + yfloorRelative + 1, z + 1).setTypeId(16);
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