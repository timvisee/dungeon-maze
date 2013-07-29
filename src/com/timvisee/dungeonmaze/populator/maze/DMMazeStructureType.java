package com.timvisee.dungeonmaze.populator.maze;

public enum DMMazeStructureType {
	UNSTRUCTURE("Unstructure"),
	ABANDONED_DEFENCE_CASTLE_ROOM("Abandoned_Defence_Castle_Room"),
	LIBRARY_ROOM("Library_Room"),
	SPAWN_ROOM("Spawn_Room"),
	CUSTOM_STRUCTURE("Custom_Structure");

	public String name;
	
	DMMazeStructureType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
