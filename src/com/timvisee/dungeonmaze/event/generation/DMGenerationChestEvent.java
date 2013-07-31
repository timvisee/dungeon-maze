package com.timvisee.dungeonmaze.event.generation;

import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.event.eventhandler.DMEventHandler;
import com.timvisee.dungeonmaze.populator.maze.DMMazeStructureType;
import com.timvisee.dungeonmaze.util.DMMazeUtils;

public class DMGenerationChestEvent extends DMEventHandler {
	
	private Block b;
	private Random rand;
	private List<ItemStack> is;
	private boolean addContentsInOrder = false;
	private DMMazeStructureType structureType = DMMazeStructureType.UNSTRUCTURE;
	private String structureName = "CUSTOM";
	
	public DMGenerationChestEvent(Block b, Random rand, List<ItemStack> is, DMMazeStructureType structureType) {
		this.b = b;
		this.is = is;
		this.rand = rand;
		this.structureType = structureType;
	}
	
	/**
	 * Get the block location where the chest will be created
	 * @return the chest location as a block
	 */
	public Block getBlock() {
		return this.b;
	}
	
	/**
	 * Get the level of the chest is on in Dungeon Maze
	 * @return The level as a DungeonMaze level, returns levels 1-7. Returns 0 when the block isn't on a DungeonMaze level
	 */
	public int getDMLevel() {
		return DMMazeUtils.getDMLevel(this.b);
	}
	
	/**
	 * Get the world the chest will be created in
	 * @return the world
	 */
	public World getWorld() {
		return this.b.getWorld();
	}
	
	/**
	 * Get the contents wich will be added into the chest
	 * @return chest contents
	 */
	public List<ItemStack> getContents() {
		return this.is;
	}
	
	/**
	 * Set the contents of the chest
	 * @param is ItemStack list
	 */
	public void setContents(List<ItemStack> is) {
		if(is != null)
			this.is = is;
	}
		
	/**
	 * Get the random object from the generator, to get the ability to add support for seeds in your listener
	 * @return Random
	 */
	public Random getRandom() {
		return this.rand;
	}
	
	/**
	 * Will the chest contents be placed in order into the chest, or will they be placed randomly
	 * @return true if placed in order
	 */
	public boolean getAddContentsInOrder() {
		return this.addContentsInOrder;
	}
	
	/**
	 * Set if the chests contents should be added in order, or if they should be added randomly
	 * @param addInOrder true if they should be added in order
	 */
	public void setAddContentsInOrder(boolean addInOrder) {
		this.addContentsInOrder = addInOrder;
	}
	
	/**
	 * Get the structure where the chest spawned
	 * @return UNSTRUCTURE type if no structure has been set, DMMazeStructureType enum else
	 */
	public DMMazeStructureType getStructureType() {
		return this.structureType;
	}


	/**
	 * Set the structure where the chest spawned
	 * @param StructureType enum
	*/
	public void setStructureType(DMMazeStructureType structureType) {
		this.structureType = structureType;
	}

	/**
	 * Set the custom structure name
	 * @param String
	 */
	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	/**
	 * Get the current custom structure name
	 * @return structureName
	 */
	public String getStructureName() {
		return structureName;
	}

}
