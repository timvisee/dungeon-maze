package com.timvisee.DungeonMaze.populators;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class TorchPopulator extends BlockPopulator {
	public static final int CHANCE_OF_TORCH = 10;
	public static final double CHANCE_OF_TORCH_ADDITION_PER_LEVEL = 3.333; /* to 30 */

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30+(1*6); y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							if (random.nextInt(100) < CHANCE_OF_TORCH+(CHANCE_OF_TORCH_ADDITION_PER_LEVEL*(y-30)/6)) {
								
								int torchX = x + random.nextInt(6) + 1;
								int torchY = y;
								int torchZ = z + random.nextInt(6) + 1;
								
								// Get the floor location 
								int yfloor = y;
								Block roomBottomBlock = source.getBlock(torchX, y, torchZ);
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!
									yfloor++;
								}
								torchY = yfloor + 1;
													
								if(!(source.getBlock(torchX, torchY - 1, torchZ).getTypeId() == 0)) {
									Block torchBlock = source.getBlock(torchX, torchY, torchZ);
									if(torchBlock.getTypeId() == 0) {
										torchBlock = source.getBlock(torchX, torchY, torchZ);
										torchBlock.setTypeId(50);
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