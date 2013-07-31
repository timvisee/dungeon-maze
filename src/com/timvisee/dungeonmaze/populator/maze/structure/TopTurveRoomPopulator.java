package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.api.DungeonMazeAPI;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class TopTurveRoomPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 5;
	public static final int CHANCE_OF_TOPTURVE = 2; //Promile
	public static final double CHANCE_OF_TOPTURVE_ADDITION_PER_LEVEL = -0.167; /* to 2 */

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int ceilingOffset = args.getCeilingY();
		int z = args.getChunkZ();
		
		// Apply chances
		if(rand.nextInt(1000) < CHANCE_OF_TOPTURVE + (CHANCE_OF_TOPTURVE_ADDITION_PER_LEVEL * (y - 30) / 6)) {
			
			// Register the current room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c.getX(), c.getZ(), x, y, z);
			
			// Hull
			c.getBlock(x + 3, y + 4 + ceilingOffset, z + 3).setTypeId(48);
			c.getBlock(x + 3, y + 4 + ceilingOffset, z + 4).setTypeId(48);
			c.getBlock(x + 4, y + 4 + ceilingOffset, z + 3).setTypeId(48);
			c.getBlock(x + 4, y + 4 + ceilingOffset, z + 4).setTypeId(48);
			c.getBlock(x + 2, y + 5 + ceilingOffset, z + 3).setTypeId(87);
			c.getBlock(x + 2, y + 5 + ceilingOffset, z + 4).setTypeId(20);
			c.getBlock(x + 3, y + 5 + ceilingOffset, z + 2).setTypeId(20);
			Block ore1 = c.getBlock(x + 3, y + 5 + ceilingOffset, z + 3);
			switch(rand.nextInt(5)) {
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
			
			c.getBlock(x + 3, y + 5 + ceilingOffset, z + 5).setTypeId(87);
			c.getBlock(x + 4, y + 5 + ceilingOffset, z + 2).setTypeId(87);
			Block ore2 = c.getBlock(x + 4, y + 5 + ceilingOffset, z + 4);
			switch(rand.nextInt(5)) {
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
			c.getBlock(x + 4, y + 5 + ceilingOffset, z + 5).setTypeId(20);
			c.getBlock(x + 5, y + 5 + ceilingOffset, z + 3).setTypeId(20);
			c.getBlock(x + 5, y + 5 + ceilingOffset, z + 4).setTypeId(87);
			
			// Spawners
			if (DungeonMazeAPI.allowMobSpawner("Pig")) {
				c.getBlock(x + 3, y + 5 + ceilingOffset, z + 4).setTypeId(52);
				CreatureSpawner PigSpawner = (CreatureSpawner) c.getBlock(x + 3, y + 5 + ceilingOffset, z + 4).getState();
				PigSpawner.setSpawnedType(EntityType.PIG);
			}
			
			if (DungeonMazeAPI.allowMobSpawner("Skeleton")) {
				c.getBlock(x + 4, y + 5 + ceilingOffset, z + 3).setTypeId(52);
				CreatureSpawner PigSpawner2 = (CreatureSpawner) c.getBlock(x + 4, y + 5 + ceilingOffset, z + 3).getState();
				PigSpawner2.setSpawnedType(EntityType.SKELETON);
			}
		}
	}
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return MIN_LAYER;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return MAX_LAYER;
	}
}