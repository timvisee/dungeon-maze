package com.timvisee.DungeonMaze.populator;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class GravelPopulator extends BlockPopulator {
	public static final int MAX_RUINS = 2;
	public static final int RUINS_CHANCE = 5;
	public static final BlockFace[] directions = new BlockFace[] {
			BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };

	@Override
	public void populate(World world, Random random, Chunk source) {		
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y  (start on 36 because there 'cant' be sand on the lowest rooms)
			for (int y=30+(1*6); y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							int ruins = 0;
							while (random.nextInt(100) < RUINS_CHANCE && ruins < MAX_RUINS) {
								int startX = x + random.nextInt(6) + 1;
								int startZ = z + random.nextInt(6) + 1;
								int startY = y;
								
								// Get the floor location 
								int yfloor = y;
								Block roomBottomBlock = source.getBlock(startX, y, startZ);
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!
									yfloor++;
								}
								startY = yfloor + 1;
								
								
								int startHeight = random.nextInt(2) + 1;
		
								BlockFace direction1 = directions[random.nextInt(directions.length)];
								BlockFace direction2 = directions[random.nextInt(directions.length)];
		
								int height = startHeight;
								int x2 = startX;
								int z2 = startZ;
								while (height > 0 && 0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
									for (int y2 = startY; y2 < startY + height; y2++) {
										if(source.getBlock(x2, y2, z2).getTypeId() == 0) {
											source.getBlock(x2, y2, z2).setTypeId(13);
										}
									}
		
									height -= random.nextInt(1);
		
									x2 += direction1.getModX();
									z2 += direction1.getModZ();
								}
		
								if (direction1 != direction2) {
									height = startHeight;
									x2 = startX;
									z2 = startZ;
									while (height > 0 && 0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
										for (int y2 = startY; y2 < startY + height; y2++) {
											if(source.getBlock(x2, y2, z2).getTypeId() == 0) {
												source.getBlock(x2, y2, z2).setTypeId(13);
											}
										}
		
										height -= random.nextInt(1);
		
										x2 += direction2.getModX();
										z2 += direction2.getModZ();
									}
								}
		
								ruins++;
							}
						}
							
					}
				}
			}
		}
		
	}
}