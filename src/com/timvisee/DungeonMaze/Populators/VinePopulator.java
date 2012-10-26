package com.timvisee.DungeonMaze.populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class VinePopulator extends BlockPopulator {
	public static final int CHANCE_OF_VINE = 30;
	public static final double CHANCE_OF_TOPTURVE_ADDITION_PER_LEVEL = -2.5; /* to 15 */
	public static final int ITERATIONS = 5;
	public static final int CHANCE_OF_CEILING_VINE = 5;
	public static final int ITERATIONS_CEILING_VINE = 5;

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source.getX(), source.getZ())) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							for(int i = 0; i < ITERATIONS; i++) {
								if (random.nextInt(100) < CHANCE_OF_VINE+(CHANCE_OF_TOPTURVE_ADDITION_PER_LEVEL*(y-30)/6)) {
									
									int vineX;
									int vineY;
									int vineZ;
									
									switch(random.nextInt(4)) {
									case 0:
										vineX = 0;
										vineY = random.nextInt(4) + 2;
										vineZ = random.nextInt(6) + 1;
										
										if(source.getBlock(x + vineX, y + vineY, z + vineZ).getTypeId() == 98) {
											source.getBlock(x + vineX + 1, y + vineY, z + vineZ).setTypeId(106);
											source.getBlock(x + vineX + 1, y + vineY, z + vineZ).setData((byte) 2);
										}
										
										break;
									case 1:
										vineX = 7;
										vineY = random.nextInt(3) + 3;
										vineZ = random.nextInt(6) + 1;
										
										if(source.getBlock(x + vineX, y + vineY, z + vineZ).getTypeId() == 98) {
											source.getBlock(x + vineX - 1, y + vineY, z + vineZ).setTypeId(106);
											source.getBlock(x + vineX - 1, y + vineY, z + vineZ).setData((byte) 8);
										}
										
										break;
									case 2:
										vineX = random.nextInt(6) + 1;
										vineY = random.nextInt(3) + 3;
										vineZ = 0;
										
										if(source.getBlock(x + vineX, y + vineY, z + vineZ).getTypeId() == 98) {
											source.getBlock(x + vineX, y + vineY, z + vineZ + 1).setTypeId(106);
											source.getBlock(x + vineX, y + vineY, z + vineZ + 1).setData((byte) 4);
										}
										
										break;
									case 3:
										vineX = random.nextInt(6) + 1;
										vineY = random.nextInt(3) + 3;
										vineZ = 7;
										
										if(source.getBlock(x + vineX, y + vineY, z + vineZ).getTypeId() == 98) {
											source.getBlock(x + vineX, y + vineY, z + vineZ - 1).setTypeId(106);
											source.getBlock(x + vineX, y + vineY, z + vineZ - 1).setData((byte) 1);
										}
										
										break;
									default:
											
									}	
								}
							}
							
							for(int i = 0; i < ITERATIONS_CEILING_VINE; i++) {
								if (random.nextInt(100) < CHANCE_OF_CEILING_VINE) {
									
									int vineX = random.nextInt(6) + 1;
									int vineY;
									int vineZ = random.nextInt(6) + 1;
									
									int yceiling = y + 6;
									Block roomCeilingBlock = source.getBlock(x + 2, y + 6, y + 2);
									int typeId2 = roomCeilingBlock.getTypeId();
									if(!(typeId2==4 || typeId2==48 || typeId2==87 || typeId2==88)) {  // x and z +2 so that you aren't inside a wall!							
										yceiling++;
									}
									vineY = yceiling - 1;
									
									source.getBlock(x + vineX, vineY, z + vineZ).setTypeId(106);
									source.getBlock(x + vineX, vineY, z + vineZ).setData((byte) 0);
								}
							}
						}
					}
				}
			}
		}
	}
}