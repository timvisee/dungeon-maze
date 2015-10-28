package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class GreatFurnaceRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 5;
	public static final float ROOM_CHANCE = .001f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int y = args.getChunkY();
		final int z = args.getChunkZ();

        // Register the room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

        // Floor
        for (int x2=x; x2 <= x + 8; x2+=1)
            for (int z2=z; z2 <= z + 8; z2+=1)
                chunk.getBlock(x2, y + 1, z2).setType(Material.STONE);

        // Change the layer below the stone floor to cobblestone
        for (int x2=x; x2 <= x + 8; x2++)
            for (int z2=z; z2 <= z + 8; z2++)
                    chunk.getBlock(x2, y, z2).setType(Material.COBBLESTONE);

        // Pillar1
        for (int y2=y + 1; y2 <= y + 5; y2+=1)
            chunk.getBlock(x + 1, y2, z + 1).setType(Material.COBBLESTONE);

        // Pillar2
        for (int y2=y + 1; y2 <= y + 5; y2+=1)
            chunk.getBlock(x + 7, y2, z + 1).setType(Material.COBBLESTONE);

        // Pillar3
        for (int y2=y + 1; y2 <= y + 5; y2+=1)
            chunk.getBlock(x + 1, y2, z + 7).setType(Material.COBBLESTONE);

        // Pillar4
        for (int y2=y + 1; y2 <= y + 5; y2+=1)
            chunk.getBlock(x + 7, y2, z + 7).setType(Material.COBBLESTONE);

        // Furnace base
        chunk.getBlock(x + 2, y + 2, z + 2).setType(Material.FURNACE);
        chunk.getBlock(x + 2, y + 2, z + 2).setData((byte) 2);
        addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 2, y + 2, z + 2).getState());
        chunk.getBlock(x + 3, y + 2, z + 2).setType(Material.GLASS);
        chunk.getBlock(x + 4, y + 2, z + 2).setType(Material.GLASS);
        chunk.getBlock(x + 5, y + 2, z + 2).setType(Material.FURNACE);
        chunk.getBlock(x + 5, y + 2, z + 2).setData((byte) 2);
        addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 5, y + 2, z + 2).getState());
        chunk.getBlock(x + 2, y + 2, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 2, z + 3).setType(Material.STATIONARY_LAVA);
        chunk.getBlock(x + 4, y + 2, z + 3).setType(Material.STATIONARY_LAVA);
        chunk.getBlock(x + 5, y + 2, z + 3).setType(Material.GLASS);
        chunk.getBlock(x + 2, y + 2, z + 4).setType(Material.GLASS);
        chunk.getBlock(x + 3, y + 2, z + 4).setType(Material.STATIONARY_LAVA);
        chunk.getBlock(x + 4, y + 2, z + 4).setType(Material.STATIONARY_LAVA);
        chunk.getBlock(x + 5, y + 2, z + 4).setType(Material.GLASS);
        chunk.getBlock(x + 2, y + 2, z + 5).setType(Material.FURNACE);
        chunk.getBlock(x + 2, y + 2, z + 5).setData((byte) 3);
        addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 2, y + 2, z + 5).getState());
        chunk.getBlock(x + 3, y + 2, z + 5).setType(Material.GLASS);
        chunk.getBlock(x + 4, y + 2, z + 5).setType(Material.GLASS);
        chunk.getBlock(x + 5, y + 2, z + 5).setType(Material.FURNACE);
        chunk.getBlock(x + 5, y + 2, z + 5).setData((byte) 3);
        addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 5, y + 2, z + 5).getState());

        // Furnace pipe
        for(int x2 = x + 3; x2 <= x + 4; x2 += 1)
            for(int y2 = y + 3; y2 <= y + 5; y2 += 1)
                for(int z2 = z + 3; z2 <= z + 4; z2 += 1)
                    chunk.getBlock(x2, y2, z2).setType(Material.BRICK);
        if(chunk.getBlock(x + 3, y + 6, z + 3).getType() == Material.AIR)
            for(int x2 = x + 3; x2 <= x + 4; x2 += 1)
                for(int z2 = z + 3; z2 <= z + 4; z2 += 1)
                    chunk.getBlock(x2, y + 6, z2).setType(Material.BRICK);
    }
	
	public void addItemsToFurnace(Random random, Furnace furnace) {
		// Create a list to put the furnace items in
		List<ItemStack> items = new ArrayList<>();

		// Add the items to the list
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(Material.GOLD_BLOCK, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(Material.IRON_BLOCK, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.BRICK, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COAL, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COAL, 1, (short) 1));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.IRON_INGOT, 2, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.IRON_INGOT, 4, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.GOLD_INGOT, 2, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(Material.GOLD_INGOT, 4, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.BREAD, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.BUCKET, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.COOKED_CHICKEN, 2, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.COOKED_CHICKEN, 4, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.FLINT, 3, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.FLINT, 5, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(Material.GRILLED_PORK, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(Material.COOKED_FISH, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(Material.ENDER_PEARL, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(Material.BLAZE_ROD, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(Material.GHAST_TEAR, 1, (short) 0));
		if(random.nextInt(100) < 45)
			items.add(new ItemStack(Material.GOLD_NUGGET, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(Material.NETHER_STALK, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(Material.SPIDER_EYE, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(Material.BLAZE_POWDER, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(Material.MAGMA_CREAM, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(Material.EYE_OF_ENDER, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(Material.SPECKLED_MELON, 1, (short) 0));
		
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