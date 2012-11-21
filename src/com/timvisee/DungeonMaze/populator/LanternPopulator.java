package com.timvisee.DungeonMaze.populator;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class LanternPopulator extends BlockPopulator {
	public static final int CHANCE_OF_1 = 30;
	public static final double CHANCE_OF_1_ADDITION_PER_LEVEL = 7.5; /* to 75 */
	public static final int ITERATIONS_OF_1 = 2;
	public static final int CHANCE_OF_2 = 10;
	public static final double CHANCE_OF_2_ADDITION_PER_LEVEL = 4.167; /* to 35 */
	public static final int ITERATIONS_OF_2 = 2;

	@Override
	public void populate(World world, Random random, Chunk source) {
		
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30+(2*6); y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							if (random.nextInt(100) < CHANCE_OF_1+(CHANCE_OF_1_ADDITION_PER_LEVEL*(y-30)/6)) {
								for (int i = 0; i < ITERATIONS_OF_1; i++) {
									int lanternX = x + random.nextInt(8);
									int lanternY = y;
									int lanternZ = z + random.nextInt(8);
									
									// Get the floor location 
									int yfloor = y;
									int yfloorRelative = 0;
									Block roomBottomBlock = source.getBlock(lanternX, y, lanternZ);
									int typeId = roomBottomBlock.getTypeId();
									if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!
										yfloor++;
										yfloorRelative = 1;
									}
									lanternY = y + random.nextInt(4 - yfloorRelative) + 2 + yfloorRelative;
									
									Block lanternBlock = source.getBlock(lanternX, lanternY, lanternZ);
									if(lanternBlock.getTypeId() == 4 || lanternBlock.getTypeId() == 48 || lanternBlock.getTypeId() == 98) {
										lanternBlock.setTypeId(91);
									}
								}
							}
						}
						
						if(!DungeonMaze.isConstantRoom(world.getName(), x, y, z)) {
							if (random.nextInt(100) < CHANCE_OF_2+(CHANCE_OF_2_ADDITION_PER_LEVEL*(y-30)/6)) {
								for (int i = 0; i < ITERATIONS_OF_2; i++) {
									int lanternX = x + random.nextInt(8);
									int lanternY = y;
									int lanternZ = z + random.nextInt(8);
									
									// Get the floor location 
									int yfloor = y;
									int yfloorRelative = 0;
									Block roomBottomBlock = source.getBlock(lanternX, y, lanternZ);
									if(roomBottomBlock.getTypeId() == 0) {  // x and z +2 so that you aren't inside a wall!
										yfloor++;
										yfloorRelative = 1;
									}
									lanternY = y + random.nextInt(4 - yfloorRelative) + 2 + yfloorRelative;
									
									Block lanternBlock2 = source.getBlock(lanternX, lanternY, lanternZ);
									if(lanternBlock2.getTypeId() == 4 || lanternBlock2.getTypeId() == 48) {
										lanternBlock2.setTypeId(86);
									}
								}
							}
						}
								
					}
				}
			}
		}
		
	}

	// Old function, may needed some time to turn the pumpkins (face to right direction)
	/*private byte getData(int x, int z, int x2, int z2, Material type) {
		if (x == x2) {
			if (z < z2)
				return (byte) (type == Material.JACK_O_LANTERN ? 0 : 2);
			return (byte) (type == Material.JACK_O_LANTERN ? 2 : 0);
		}
		if (x < x2)
			return (byte) (type == Material.JACK_O_LANTERN ? 3 : 1);
		return (byte) (type == Material.JACK_O_LANTERN ? 1 : 3);
	}*/
}
