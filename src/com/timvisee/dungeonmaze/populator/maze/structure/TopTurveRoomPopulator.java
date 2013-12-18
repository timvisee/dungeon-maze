package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import com.timvisee.dungeonmaze.DungeonMaze;
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
			c.getBlock(x + 3, y + 4 + ceilingOffset, z + 3).setType(Material.MOSSY_COBBLESTONE);
			c.getBlock(x + 3, y + 4 + ceilingOffset, z + 4).setType(Material.MOSSY_COBBLESTONE);
			c.getBlock(x + 4, y + 4 + ceilingOffset, z + 3).setType(Material.MOSSY_COBBLESTONE);
			c.getBlock(x + 4, y + 4 + ceilingOffset, z + 4).setType(Material.MOSSY_COBBLESTONE);
			c.getBlock(x + 2, y + 5 + ceilingOffset, z + 3).setType(Material.NETHERRACK);
			c.getBlock(x + 2, y + 5 + ceilingOffset, z + 4).setType(Material.GLASS);
			c.getBlock(x + 3, y + 5 + ceilingOffset, z + 2).setType(Material.GLASS);
			Block ore1 = c.getBlock(x + 3, y + 5 + ceilingOffset, z + 3);
			switch(rand.nextInt(5)) {
			case 0:
				ore1.setType(Material.GOLD_ORE);
				break;
			case 1:
				ore1.setType(Material.IRON_ORE);
				break;
			case 2:
				ore1.setType(Material.COAL_ORE);
				break;
			case 3:
				ore1.setType(Material.LAPIS_ORE);
				break;
			case 4:
				ore1.setType(Material.COAL_ORE); // orriginally diamond, changed to coal because ore2 could be diamond too
				break;
			default:
				ore1.setType(Material.COAL_ORE);
			}
			
			c.getBlock(x + 3, y + 5 + ceilingOffset, z + 5).setType(Material.NETHERRACK);
			c.getBlock(x + 4, y + 5 + ceilingOffset, z + 2).setType(Material.NETHERRACK);
			Block ore2 = c.getBlock(x + 4, y + 5 + ceilingOffset, z + 4);
			switch(rand.nextInt(5)) {
			case 0:
				ore2.setType(Material.GOLD_ORE);
				break;
			case 1:
				ore2.setType(Material.IRON_ORE);
				break;
			case 2:
				ore2.setType(Material.COAL_ORE);
				break;
			case 3:
				ore2.setType(Material.LAPIS_ORE);
				break;
			case 4:
				ore2.setType(Material.DIAMOND_ORE);
				break;
			default:
				ore2.setType(Material.COAL_ORE);
			}
			c.getBlock(x + 4, y + 5 + ceilingOffset, z + 5).setType(Material.GLASS);
			c.getBlock(x + 5, y + 5 + ceilingOffset, z + 3).setType(Material.GLASS);
			c.getBlock(x + 5, y + 5 + ceilingOffset, z + 4).setType(Material.NETHERRACK);
			
			// Spawners
			if(DungeonMaze.instance.getConfigHandler().isMobSpawnerAllowed("Pig")) {
				c.getBlock(x + 3, y + 5 + ceilingOffset, z + 4).setType(Material.MOB_SPAWNER);
				CreatureSpawner PigSpawner = (CreatureSpawner) c.getBlock(x + 3, y + 5 + ceilingOffset, z + 4).getState();
				PigSpawner.setSpawnedType(EntityType.PIG);
			}
			
			if(DungeonMaze.instance.getConfigHandler().isMobSpawnerAllowed("Skeleton")) {
				c.getBlock(x + 4, y + 5 + ceilingOffset, z + 3).setType(Material.MOB_SPAWNER);
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