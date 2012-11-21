package com.timvisee.DungeonMaze.populator;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class HighRoomPopulator extends BlockPopulator {
	public static final int CHANCE_OF_HIGHROOM = 6; //Promile

	@Override
	public void populate(World world, Random random, Chunk source) {
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(6*6); y+=6) {
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if (random.nextInt(1000) < CHANCE_OF_HIGHROOM) {
							if(!DungeonMaze.isConstantRoom(world.getName(), source.getX(), source.getZ(), x, y, z)) {
								if(!DungeonMaze.isConstantRoom(world.getName(), source.getX(), source.getZ(), x, y + 6, z)) {
									DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, y + 6, z);
									
									//Remove the floor of the room above
									for (int x2=x; x2 <= x + 7; x2+=1) {
									    for (int y2=y + 5; y2 <= y + 8; y2+=1) {
									        for (int z2=z + 1; z2 <= z + 6; z2+=1) {
									            source.getBlock(x2, y2, z2).setTypeId(0);
									        }
									    }
									}
									for (int x2=x + 1; x2 <= x + 6; x2+=1) {
									    for (int y2=y + 5; y2 <= y + 8; y2+=1) {
									        for (int z2=z; z2 <= z + 7; z2+=1) {
									            source.getBlock(x2, y2, z2).setTypeId(0);
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
}