package com.timvisee.dungeonmaze.structure;

import java.util.ArrayList;
import java.util.List;

public class CustomStructureManager {
	
	private List<CustomStructure> structures = new ArrayList<CustomStructure>();
	
	/**
	 * Constructor
	 */
	public CustomStructureManager() { }
	
	/**
	 * Get the amount of loaded custom structures
	 * @return Amount of loaded custom structures
	 */
	@SuppressWarnings("UnusedDeclaration")
	public int getStructureCount() {
		return this.structures.size();
	}
}
