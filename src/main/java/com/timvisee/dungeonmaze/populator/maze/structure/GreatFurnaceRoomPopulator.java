package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.util.MaterialUtils;

public class GreatFurnaceRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 5;
	private static final float ROOM_CHANCE = .001f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int z = args.getRoomChunkZ();
        final Material bricksBlock = MaterialUtils.requireBlockMaterial("BRICKS", "BRICK");

        // Register the room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

        // Floor
        for (int x2=x; x2 <= x + 7; x2+=1)
            for (int z2=z; z2 <= z + 7; z2+=1)
                setGeneratedBlock(chunk.getBlock(x2, y + 1, z2), Material.STONE);

        // Change the layer below the stone floor to cobblestone
        for (int x2=x; x2 <= x + 7; x2++)
            for (int z2=z; z2 <= z + 7; z2++)
                    setGeneratedBlock(chunk.getBlock(x2, y, z2), Material.COBBLESTONE);

        // Pillar1
        for (int y2=y + 1; y2 <= y + 5; y2+=1)
            setGeneratedBlock(chunk.getBlock(x + 1, y2, z + 1), Material.COBBLESTONE);

        // Pillar2
        for (int y2=y + 1; y2 <= y + 5; y2+=1)
            setGeneratedBlock(chunk.getBlock(x + 7, y2, z + 1), Material.COBBLESTONE);

        // Pillar3
        for (int y2=y + 1; y2 <= y + 5; y2+=1)
            setGeneratedBlock(chunk.getBlock(x + 1, y2, z + 7), Material.COBBLESTONE);

        // Pillar4
        for (int y2=y + 1; y2 <= y + 5; y2+=1)
            setGeneratedBlock(chunk.getBlock(x + 7, y2, z + 7), Material.COBBLESTONE);

        // Furnace base
        MaterialUtils.setFurnaceFacing(chunk.getBlock(x + 2, y + 2, z + 2), BlockFace.NORTH);
        addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 2, y + 2, z + 2).getState());
        setGeneratedBlock(chunk.getBlock(x + 3, y + 2, z + 2), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 4, y + 2, z + 2), Material.GLASS);
        MaterialUtils.setFurnaceFacing(chunk.getBlock(x + 5, y + 2, z + 2), BlockFace.NORTH);
        addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 5, y + 2, z + 2).getState());
        setGeneratedBlock(chunk.getBlock(x + 2, y + 2, z + 3), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 3, y + 2, z + 3), Material.LAVA);
        setGeneratedBlock(chunk.getBlock(x + 4, y + 2, z + 3), Material.LAVA);
        setGeneratedBlock(chunk.getBlock(x + 5, y + 2, z + 3), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 2, y + 2, z + 4), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 3, y + 2, z + 4), Material.LAVA);
        setGeneratedBlock(chunk.getBlock(x + 4, y + 2, z + 4), Material.LAVA);
        setGeneratedBlock(chunk.getBlock(x + 5, y + 2, z + 4), Material.GLASS);
        MaterialUtils.setFurnaceFacing(chunk.getBlock(x + 2, y + 2, z + 5), BlockFace.SOUTH);
        addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 2, y + 2, z + 5).getState());
        setGeneratedBlock(chunk.getBlock(x + 3, y + 2, z + 5), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 4, y + 2, z + 5), Material.GLASS);
        MaterialUtils.setFurnaceFacing(chunk.getBlock(x + 5, y + 2, z + 5), BlockFace.SOUTH);
        addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 5, y + 2, z + 5).getState());

        // Furnace pipe
        for(int x2 = x + 3; x2 <= x + 4; x2 += 1)
            for(int y2 = y + 3; y2 <= y + 5; y2 += 1)
                for(int z2 = z + 3; z2 <= z + 4; z2 += 1)
                    setGeneratedBlock(chunk.getBlock(x2, y2, z2), bricksBlock);
        if(chunk.getBlock(x + 3, y + 6, z + 3).getType() == Material.AIR)
            for(int x2 = x + 3; x2 <= x + 4; x2 += 1)
                for(int z2 = z + 3; z2 <= z + 4; z2 += 1)
                    setGeneratedBlock(chunk.getBlock(x2, y + 6, z2), bricksBlock);
    }
	
	public void addItemsToFurnace(Random random, Furnace furnace) {
		// Create a list to put the furnace items in
		List<ItemStack> items = new ArrayList<>();

		// Add the items to the list
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.GOLD_BLOCK, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.IRON_BLOCK, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.BRICK, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.COAL, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.COAL, 1, (short) 1));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.IRON_INGOT, 2, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.IRON_INGOT, 4, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.GOLD_INGOT, 2, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.GOLD_INGOT, 4, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.BREAD, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.BUCKET, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.COOKED_CHICKEN, 2, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.COOKED_CHICKEN, 4, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.FLINT, 3, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.FLINT, 5, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.COOKED_PORKCHOP, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.COOKED_COD, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(MaterialUtils.createItemStack(Material.ENDER_PEARL, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(MaterialUtils.createItemStack(Material.BLAZE_ROD, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(MaterialUtils.createItemStack(Material.GHAST_TEAR, 1, (short) 0));
		if(random.nextInt(100) < 45)
			items.add(MaterialUtils.createItemStack(Material.GOLD_NUGGET, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(MaterialUtils.createItemStack(Material.NETHER_WART, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(MaterialUtils.createItemStack(Material.SPIDER_EYE, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(MaterialUtils.createItemStack(Material.BLAZE_POWDER, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(MaterialUtils.createItemStack(Material.MAGMA_CREAM, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(MaterialUtils.createItemStack(Material.ENDER_EYE, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(MaterialUtils.createItemStack(Material.GLISTERING_MELON_SLICE, 1, (short) 0));
		
		// Add the selected items into the furnace
		if(random.nextInt(100) < 60)
			furnace.getInventory().setResult(items.get(random.nextInt(items.size())));

		// Update the furnace
		furnace.update();
	}

    @Override
    public float getRoomChance() {
        return ROOM_CHANCE;
    }
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return LAYER_MIN;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return LAYER_MAX;
	}
}
