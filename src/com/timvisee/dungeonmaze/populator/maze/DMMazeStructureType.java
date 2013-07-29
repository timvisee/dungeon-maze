package com.timvisee.dungeonmaze.populator.maze;

public enum DMMazeStructureType {
	UNSTRUCTURE("Unstructure", 0),
	ABANDONED_DEFENCE_CASTLE_ROOM("Abandoned_Defence_Castle_Room", 2),
	ARMORY_ROOM("Armory_Room", 3),
	ENTRANCE("Entrance", 4),
	FLOODED_ROOM("Flooded_Room", 5),
	GRAVEL_WALL("Gravel_Wall", 6),
	GREAT_FURNACE_ROOM("Great_Furnace_room", 7),
	HIGH_ROOM("High_Room", 8),
	LIBRARY_ROOM("Library_Room", 9),
	MASSIVE_ROOM("Massive_Room", 10),
	OASIS_CHUNK("Oasis_Chunk", 11),
	RAIL("Rail", 12),
	RUIN("Ruin", 13),
	SANCTUARY_ROOM("Sanctuary_Room", 14),
	SAND_WALL("Sand_Wall", 15),
	SPAWN_ROOM("Spawn_Room", 16),
	STAIRS("Stairs", 17),
	STRUT("Strut", 18),
	WATER_WELL_ROOM("Water_Well_Room", 19),
	CUSTOM_STRUCTURE("Custom_Structure", 1);

	public String name;
	public int id;
	
	/**
	 * Constructor
	 * @param name Structure type name
	 * @param id Structure type ID
	 */
	DMMazeStructureType(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	/**
	 * Get the structure type name
	 * @return Structure type name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the structure type ID
	 * @return Strucutre type ID
	 */
	public int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
