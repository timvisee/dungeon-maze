package com.timvisee.DungeonMaze.Populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class TopTurveRoomPopulator extends BlockPopulator {
	public static final int CHANCE_OF_TOPTURVE = 1; //Promile
	public static final double CHANCE_OF_TOPTURVE_ADDITION_PER_LEVEL = -0.167; /* to 2 */

	@Override
	public void populate(World world, Random random, Chunk source) {
		// Hold the y on 30 because that's only the lowest layer
			
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(5*6); y+=6) {
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if (random.nextInt(1000) < CHANCE_OF_TOPTURVE+(CHANCE_OF_TOPTURVE_ADDITION_PER_LEVEL*(y-30)/6)) {
							if(!DungeonMaze.isConstantRoom(world.getName(), source.getX(), source.getZ(), x, y, z)) {
								DungeonMaze.addConstantRooms(world.getName(), source.getX(), source.getZ(), x, y, z);
								
								int yceiling = y + 6; int yceilingRelative = 0;
								Block roomCeilingBlock = source.getBlock(x + 2, y + 6, z + 2);
								int typeId2 = roomCeilingBlock.getTypeId();
								if(!(typeId2==4 || typeId2==48 || typeId2==87 || typeId2==88)) {  // x and z +2 so that you aren't inside a wall!							
									yceiling++; yceilingRelative++;
								}
								
								//hull
								source.getBlock(x + 3, y + 4 + yceilingRelative, z + 3).setTypeId(48);
								source.getBlock(x + 3, y + 4 + yceilingRelative, z + 4).setTypeId(48);
								source.getBlock(x + 4, y + 4 + yceilingRelative, z + 3).setTypeId(48);
								source.getBlock(x + 4, y + 4 + yceilingRelative, z + 4).setTypeId(48);
								source.getBlock(x + 2, y + 5 + yceilingRelative, z + 3).setTypeId(87);
								source.getBlock(x + 2, y + 5 + yceilingRelative, z + 4).setTypeId(20);
								source.getBlock(x + 3, y + 5 + yceilingRelative, z + 2).setTypeId(20);
								Block ore1 = source.getBlock(x + 3, y + 5 + yceilingRelative, z + 3);
								switch(random.nextInt(5)) {
								case 0:
									ore1.setTypeId(14);
									break;
								case 1:
									ore1.setTypeId(15);
									break;
								case 2:
									ore1.setTypeId(16);
									break;
								case 3:
									ore1.setTypeId(21);
									break;
								case 4:
									ore1.setTypeId(16); // orriginally diamond, changed to coal because ore1 could be diamond too
									break;
								default:
									ore1.setTypeId(16);
								}
								source.getBlock(x + 3, y + 5 + yceilingRelative, z + 5).setTypeId(87);
								source.getBlock(x + 4, y + 5 + yceilingRelative, z + 2).setTypeId(87);
								Block ore2 = source.getBlock(x + 4, y + 5 + yceilingRelative, z + 4);
								switch(random.nextInt(5)) {
								case 0:
									ore2.setTypeId(14);
									break;
								case 1:
									ore2.setTypeId(15);
									break;
								case 2:
									ore2.setTypeId(16);
									break;
								case 3:
									ore2.setTypeId(21);
									break;
								case 4:
									ore2.setTypeId(56);
									break;
								default:
									ore2.setTypeId(16);
								}
								source.getBlock(x + 4, y + 5 + yceilingRelative, z + 5).setTypeId(20);
								source.getBlock(x + 5, y + 5 + yceilingRelative, z + 3).setTypeId(20);
								source.getBlock(x + 5, y + 5 + yceilingRelative, z + 4).setTypeId(87);
								//spawners
								source.getBlock(x + 3, y + 5 + yceilingRelative, z + 4).setTypeId(52);
								CreatureSpawner PigSpawner = (CreatureSpawner) source.getBlock(x + 3, y + 5 + yceilingRelative, z + 4).getState();
								PigSpawner.setSpawnedType(EntityType.PIG);
								source.getBlock(x + 4, y + 5 + yceilingRelative, z + 3).setTypeId(52);
								CreatureSpawner PigSpawner2 = (CreatureSpawner) source.getBlock(x + 4, y + 5 + yceilingRelative, z + 3).getState();
								PigSpawner2.setSpawnedType(EntityType.SKELETON);

							}	
						}
					}
				}
			}
		}
	}
}