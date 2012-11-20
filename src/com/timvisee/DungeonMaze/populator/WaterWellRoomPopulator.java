package com.timvisee.DungeonMaze.populator;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class WaterWellRoomPopulator extends BlockPopulator {
	public static final int CHANCE_OF_WATERWELL = 2; //Promile

	@Override
	public void populate(World world, Random random, Chunk source) {			
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30+(2*6); y < 30+(7*6); y+=6) {
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if (random.nextInt(1000) < CHANCE_OF_WATERWELL) {
							if(!DungeonMaze.isConstantRoom(world.getName(), source.getX(), source.getZ(), x, y, z)) {
								DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, y, z);
								
								// Get the floor location 
								int yfloorRelative = 1; // Set this directly to one so that the water well is always on the highest floor.
								
								//floor
								for (int x2=x; x2 <= x + 7; x2+=1) {
								    for (int z2=z; z2 <= z + 7; z2+=1) {
								        source.getBlock(x2, y + yfloorRelative, z2).setTypeId(1);
								    }
								}
								//floor (cobbelstone underneeth the stone floor)
								for (int x2=x; x2 <= x + 7; x2+=1) {
								    for (int z2=z; z2 <= z + 7; z2+=1) {
								        source.getBlock(x2, y + yfloorRelative - 1, z2).setTypeId(4);
								    }
								}
								//well
								for (int x2=x + 2; x2 <= x + 4; x2+=1) {
								    for (int z2=z + 2; z2 <= z + 4; z2+=1) {
								        source.getBlock(x2, y + yfloorRelative + 1, z2).setTypeId(98);
								    }
								}
								source.getBlock(x + 3, y + yfloorRelative + 1, z + 3).setTypeId(9);
								
								//poles
								source.getBlock(x + 2, y + yfloorRelative + 2, z + 2).setTypeId(85);
								source.getBlock(x + 2, y + yfloorRelative + 2, z + 4).setTypeId(85);
								source.getBlock(x + 4, y + yfloorRelative + 2, z + 2).setTypeId(85);
								source.getBlock(x + 4, y + yfloorRelative + 2, z + 4).setTypeId(85);
								//roof
								source.getBlock(x + 2, y + yfloorRelative + 3, z + 2).setTypeId(44);
								source.getBlock(x + 2, y + yfloorRelative + 3, z + 2).setData((byte) 2);
								source.getBlock(x + 2, y + yfloorRelative + 3, z + 3).setTypeId(53);
								source.getBlock(x + 2, y + yfloorRelative + 3, z + 3).setData((byte) 0);
								source.getBlock(x + 2, y + yfloorRelative + 3, z + 4).setTypeId(44);
								source.getBlock(x + 2, y + yfloorRelative + 3, z + 4).setData((byte) 2);
								source.getBlock(x + 3, y + yfloorRelative + 3, z + 2).setTypeId(53);
								source.getBlock(x + 3, y + yfloorRelative + 3, z + 2).setData((byte) 2); 
								source.getBlock(x + 3, y + yfloorRelative + 3, z + 3).setTypeId(89);
								source.getBlock(x + 3, y + yfloorRelative + 3, z + 4).setTypeId(53);
								source.getBlock(x + 3, y + yfloorRelative + 3, z + 4).setData((byte) 3);
								source.getBlock(x + 4, y + yfloorRelative + 3, z + 2).setTypeId(44);
								source.getBlock(x + 4, y + yfloorRelative + 3, z + 2).setData((byte) 2);
								source.getBlock(x + 4, y + yfloorRelative + 3, z + 3).setTypeId(53);
								source.getBlock(x + 4, y + yfloorRelative + 3, z + 3).setData((byte) 1);
								source.getBlock(x + 4, y + yfloorRelative + 3, z + 4).setTypeId(44);
								source.getBlock(x + 4, y + yfloorRelative + 3, z + 4).setData((byte) 2);
							}
						}
					}
				}
			}
		}
	}
}