package com.timvisee.dungeonmaze.structure;

import org.bukkit.Location;

public class CustomStructure {
	
	private String name;
	private int chance = 100;
	private int minLayer = 1;
	private int maxLayer = 7;
	
	/**
	 * Constructor
	 * @param name Structure name
	 * @param chance Spawning chance (percentage)
	 */
	public CustomStructure(String name, int chance) {
		this.name = name;
		this.chance = chance;
	}
	
	/**
	 * Constructor
	 * @param name Structure name
	 * @param chance Spawning chance (percentage)
	 * @param minLayer Minimum layer this structure is allowed to spawn on
	 * @param maxLayer Maximum layer this structure is allowed to spawn on
	 */
	public CustomStructure(String name, int chance, int minLayer, int maxLayer) {
		this.name = name;
		this.chance = chance;
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
	}
	
	/**
	 * Get the structure name
	 * @return Structure name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the spawning chance (percentage)
	 * @return Spawning chance (percentage)
	 */
	public int getChance() {
		return this.chance;
	}
	
	/**
	 * Set the spawning chance (percentage)
	 * @param chance Spawning chance (percentage)
	 */
	public void setChance(int chance) {
		this.chance = chance;
	}
	
	/**
	 * Get the minimum layer this structure is allowed to spawn on
	 * @return Min layer
	 */
	public int getMinLayer() {
		return this.minLayer;
	}
	
	/**
	 * Set the minimum layer this structure is allowed to spawn on
	 * @param minLayer Min layer
	 */
	public void setMinLayer(int minLayer) {
		this.minLayer = minLayer;
	}
	
	/**
	 * Get the maximum layer this structure is allowed to spawn on
	 * @return Max layer
	 */
	public int getMaxLayer() {
		return this.maxLayer;
	}
	
	/**
	 * Set the maximum layer this structure is allowed to spawn on
	 * @param maxLayer Max layer
	 */
	public void setMaxLayer(int maxLayer) {
		this.maxLayer = maxLayer;
	}
	
	/**
	 * Build the custom structure at a location in a world
	 * @param l The location to build the structure at
	 * @return False if failed
	 */
	public boolean build(Location l) {
		// TODO: Build the structure
		
		return false;
	}
}
