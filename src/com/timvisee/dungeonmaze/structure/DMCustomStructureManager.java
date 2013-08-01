package com.timvisee.dungeonmaze.structure;

import java.util.ArrayList;
import java.util.List;

public class DMCustomStructureManager {
	
	private List<DMCustomStructure> structures = new ArrayList<DMCustomStructure>();
	
	/**
	 * Constructor
	 */
	public DMCustomStructureManager() { }
	
	/**
	 * Get the amount of loaded custom structures
	 * @return Amount of loaded custom structures
	 */
	public int getStructureCount() {
		return this.structures.size();
	}
}
