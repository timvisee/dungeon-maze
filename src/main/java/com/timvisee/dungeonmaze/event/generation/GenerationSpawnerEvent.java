package com.timvisee.dungeonmaze.event.generation;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.event.EventHandler;
import com.timvisee.dungeonmaze.util.MazeUtils;

public class GenerationSpawnerEvent extends EventHandler {
	
	private Block b;
	private Random rand;
	private EntityType spawnedType;
	private GenerationSpawnerCause generatedCause;
	
	public GenerationSpawnerEvent(Block b, EntityType spawnedType, GenerationSpawnerCause generatedCause, Random rand) {
		this.b = b;
		this.spawnedType = spawnedType;
		this.rand = rand;
		this.generatedCause = generatedCause;
	}
	
	/**
	 * Get the location the spawned will be created
	 * @return spawner location
	 */
	public Block getBlock() {
		return this.b;
	}
	
	/**
	 * Get the level of the spawner is on in Dungeon Maze
	 * @return The level as a DungeonMaze level, returns levels 1-7. Returns 0 when the block isn't on a DungeonMaze level
	 */
	public int getDMLevel() {
		return MazeUtils.getDMLevel(b);
	}
	
	/**
	 * Get the world the spawner will be created in
	 * @return spawner world
	 */
	public World getWorld() {
		return this.b.getWorld();
	}
	
	/**
	 * Get the spawned entity type of the spawner
	 * @return spawned entity type
	 */
	public EntityType getSpawnedType() {
		return this.spawnedType;
	}
	
	/**
	 * Set the spawned entity type of the spawner
	 * @param spawnedType spawned entity type
	 */
	public void setSpawnedType(EntityType spawnedType) {
		if(spawnedType != null && Core.getConfigHandler().isMobSpawnerAllowed(spawnedType.name()))
			this.spawnedType = spawnedType;
	}
	
	public GenerationSpawnerCause getCause() {
		return this.generatedCause;
	}
	
	/**
	 * Get the random object from the generator, to get the ability to add support for seeds in your listener
	 * @return Random
	 */
	public Random getRandom() {
		return this.rand;
	}

	public enum GenerationSpawnerCause {
		UNKNOWN(0),
		NORMAL(1),
		BOSSROOM_EASY(3),
		BOSSROOM_HARD(4),
		BOSSROOM_INSANE(5),
		BLAZE_SPAWNER_ROOM(6),
		CREEPER_SPAWNER_ROOM(7),
		OTHER(2);

		public int id;

		GenerationSpawnerCause(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}
	}
}