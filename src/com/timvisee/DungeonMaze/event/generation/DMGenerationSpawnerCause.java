package com.timvisee.DungeonMaze.event.generation;

public enum DMGenerationSpawnerCause {
	UNKNOWN(0),
	NORMAL(1),
	BOSSROOM_EASY(3),
	BOSSROOM_HARD(4),
	BOSSROOM_INSANE(5),
	BLAZE_SPAWNER_ROOM(6),
	CREEPER_SPAWNER_ROOM(7),
	OTHER(2);
	
	public int id;
	
	DMGenerationSpawnerCause(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
}