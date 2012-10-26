package com.timvisee.DungeonMaze.populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class RailPopulator extends BlockPopulator {
	public static final int RAIL_CHANCE = 8;
	public static final double RAIL_CHANCE_ADDITION_PER_LEVEL = -0.333; /* to 6 */
	public static final int ITERATIONS = 2;
	public static final int MAX_RAIL = 2;
	public static final int BROKEN_RAIL_CHANCE = 20;
	public static final int MINECART_ON_RAIL_CHANCE = 1;
	public static final BlockFace[] directions = new BlockFace[] {
			BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };

	@Override
	public void populate(World world, Random random, Chunk source) {
		
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y (start on 36 because there 'cant' be sand on the lowest rooms)
			for (int y=30+(2*6); y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							int rails = 0;
							
							for(int i = 0; i < ITERATIONS; i++) {
								
								if(rails <= MAX_RAIL) {
									
									if (random.nextInt(100) < RAIL_CHANCE+(RAIL_CHANCE_ADDITION_PER_LEVEL*(y-30)/6)) { 
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
				
										BlockFace direction1 = directions[random.nextInt(directions.length)];
										BlockFace direction2 = directions[random.nextInt(directions.length)];
				
										int x2 = startX;
										int z2 = startZ;
										while (0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
											if(random.nextInt(100) > BROKEN_RAIL_CHANCE) {
												source.getBlock(x2, yfloor + 1, z2).setTypeId(66);
												if(random.nextInt(100) < MINECART_ON_RAIL_CHANCE) {
													spawnMinecart(world, (source.getX() * 16) + x + x2, yfloor + 1, (source.getZ() * 16) + z + z2);
												}
											}
				
											x2 += direction1.getModX();
											z2 += direction1.getModZ();
										}
				
										if (direction1 != direction2) {
											x2 = startX;
											z2 = startZ;
											while (0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
												if(random.nextInt(100) > BROKEN_RAIL_CHANCE) {
													source.getBlock(x2, yfloor + 1, z2).setTypeId(66);
													if(random.nextInt(100) < MINECART_ON_RAIL_CHANCE) {
														spawnMinecart(world, (source.getX() * 16) + x + x2, yfloor + 1, (source.getZ() * 16) + z + z2);
													}
												}
				
												x2 += direction2.getModX();
												z2 += direction2.getModZ();
											}
										}
				
										rails++;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public Minecart spawnMinecart(World world, int x, int y, int z) {
		return spawnMinecart(new Location(world, x, y, z));
	}
	
	public Minecart spawnMinecart(Location location) {
    	return spawnMinecart(location.getWorld(), location);
    }
	
    public Minecart spawnMinecart(World world, Location location) {
    	return world.spawn(location, Minecart.class);
    }
}
