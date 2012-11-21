package com.timvisee.DungeonMaze.populator;

import java.util.Random;


import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class OasisChunkPopulator extends BlockPopulator {
	public static final int CHANCE_OF_OASIS = 3; //Promile
	public static final int CHANCE_OF_CLAYINDIRT = 10;
	public static final double MIN_SPAWN_DISTANCE = 7; // Chunks

	@Override
	public void populate(World world, Random random, Chunk source) {			
		// The 4 rooms on each layer saved in the variables x and z
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			
			if(distance(0, 0, source.getX(), source.getZ()) >= MIN_SPAWN_DISTANCE) {
				if (random.nextInt(1000) < CHANCE_OF_OASIS) {
					// Flag this chunk as a constant chunk
					DungeonMaze.addConstantChunk(world.getName(), source.getX(), source.getZ());
					
					// Generate a dirt layer
					for (int x = 0; x < 16; x++) {
						for (int z = 0; z < 16; z++) {
							source.getBlock(x, 29, z).setTypeId(3);
						}
					}
					// Generate some clay inside the dirt layer
					for (int x = 0; x < 16; x++) {
						for (int z = 0; z < 16; z++) {
							if (random.nextInt(100) < CHANCE_OF_CLAYINDIRT) {
								source.getBlock(x, 29, z).setTypeId(82);
							}
						}
					}
					
					// Generate the grass layer
					for (int x = 0; x < 16; x++) {
						for (int z = 0; z < 16; z++) {
							source.getBlock(x, 30, z).setTypeId(2);
						}
					}
					
					// Remove all the stone above the grass layer!
					for (int y = 31; y <= 100; y++) {
						for (int x = 0; x < 16; x++) {
							for (int z = 0; z < 16; z++) {
								source.getBlock(x, y, z).setTypeId(0);
							}
						}
					}
					
					// Generate some tall gras on the oasis
					for (int x = 0; x < 16; x++) {
						for (int z = 0; z < 16; z++) {
							if (random.nextInt(100) < CHANCE_OF_CLAYINDIRT) {
								source.getBlock(x, 31, z).setTypeId(31);
								source.getBlock(x, 31, z).setData((byte) 1);
							}
						}
					}
					
					// Random tree offset (0 or 1)
					int treeOffsetX = random.nextInt(2);
					int treeOffsetZ = random.nextInt(2);
					
					// Generate the water around the tree
					for (int x = 5; x <= 11; x++) {
						source.getBlock(x + treeOffsetX, 30, 5 + treeOffsetZ).setTypeId(8);
					}
					for (int z = 5; z <= 11; z++) {
						source.getBlock(5 + treeOffsetX, 30, z + treeOffsetZ).setTypeId(8);
					}
					for (int x = 5; x <= 11; x++) {
						source.getBlock(x + treeOffsetX, 30, 11 + treeOffsetZ).setTypeId(8);
					}
					for (int z = 5; z <= 11; z++) {
						source.getBlock(11 + treeOffsetX, 30, z + treeOffsetZ).setTypeId(8);
					}
					
					// Generate some sugar canes
					source.getBlock(6 + treeOffsetX, 31, 6 + treeOffsetZ).setTypeId(83);
					source.getBlock(6 + treeOffsetX, 31, 10 + treeOffsetZ).setTypeId(83);
					source.getBlock(10 + treeOffsetX, 31, 6 + treeOffsetZ).setTypeId(83);
					source.getBlock(10 + treeOffsetX, 31, 10 + treeOffsetZ).setTypeId(83);
					
					// Random tree type and generate the tree
					TreeType treeType = TreeType.TREE;
					switch (random.nextInt(5)) {
					case 0:
						treeType = TreeType.BIG_TREE;
						break;
					case 1:
						treeType = TreeType.BIRCH;
						break;
					case 2:
						treeType = TreeType.REDWOOD;
						break;
					case 3:
						treeType = TreeType.TALL_REDWOOD;
						break;
					case 4:
						treeType = TreeType.TREE;
						break;
					default:
						treeType = TreeType.TREE;
					}
					// Tree generation currently not working :@
					Location treeLocation = new Location(world, (source.getX()*16) + (8 + treeOffsetX), 31, (source.getZ()*16) + (8 + treeOffsetZ));
					world.generateTree(treeLocation, treeType);
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