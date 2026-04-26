package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.GenerationChestEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.MazeStructureType;
import com.timvisee.dungeonmaze.util.ChestUtils;
import com.timvisee.dungeonmaze.util.MaterialUtils;

public class LibraryRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 3;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .002f;

    // TODO: Implement this feature!
	public static final double CHANCE_LIBRARY_ADDITION_EACH_LEVEL = -0.167; /* to 1 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int yCeiling = args.getCeilingY();
		final int z = args.getRoomChunkZ();

        // Register the current room als constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

        //stone floor in the bottom of the room
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int z2 = z + 1; z2 <= z + 6; z2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, yFloor, z2), Material.STONE);

        // Cobblestone layer underneath the stone floor
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int z2 = z + 1; z2 <= z + 6; z2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, yFloor - 1, z2), Material.COBBLESTONE);

        // Make stone walls on each side of the room
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, y2, z), Material.STONE_BRICKS);
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, y2, z + 7), Material.STONE_BRICKS);
        for(int z2 = z + 1; z2 <= z + 6; z2 += 1)
            for(int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x, y2, z2), Material.STONE_BRICKS);
        for(int z2 = z + 1; z2 <= z + 6; z2 += 1)
            for(int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x + 7, y2, z2), Material.STONE_BRICKS);

        // Generate some holes in the wall to make some kind of doors
        for(int x2 = x + 3; x2 <= x + 4; x2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, y2, z), Material.AIR);
        for(int x2 = x + 3; x2 <= x + 4; x2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, y2, z + 7), Material.AIR);
        for(int z2 = z + 3; z2 <= z + 4; z2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x, y2, z2), Material.AIR);
        for(int z2 = z + 3; z2 <= z + 4; z2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x + 7, y2, z2), Material.AIR);

        // Generate the bookshelves, one on each side
        for(int x2 = x + 5; x2 <= x + 6; x2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, y2, z + 1), Material.BOOKSHELF);
        for(int x2 = x + 1; x2 <= x + 2; x2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, y2, z + 6), Material.BOOKSHELF);
        for(int z2 = z + 1; z2 <= z + 2; z2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x + 1, y2, z2), Material.BOOKSHELF);
        for(int z2 = z + 5; z2 <= z + 6; z2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                setGeneratedBlock(chunk.getBlock(x + 6, y2, z2), Material.BOOKSHELF);

        /* // Make the two pilars - Change to enchant table
        for (int y2 = yFloor + 1; y2 <= yFloor + 3; y2+=1) {
            setGeneratedBlock(c.getBlock(x + 3, y2, z + 4), Material.PUMPKIN);
            setGeneratedBlock(c.getBlock(x + 4, y2, z + 3), Material.PUMPKIN);
        } */

        // Add enchant tables supports
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor +1, z + 4), Material.BOOKSHELF);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor +1, z + 3), Material.BOOKSHELF);
        // Add the two enchant tables
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor +2, z + 4), Material.ENCHANTING_TABLE);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor +2, z + 3), Material.ENCHANTING_TABLE);
        // Add the two chests
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1, z + 3), Material.CHEST);

        // Call the Chest generation event
        GenerationChestEvent event = new GenerationChestEvent(chunk.getBlock(x + 3, yFloor + 1, z + 3), rand, genChestContent(rand), MazeStructureType.LIBRARY_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event);

        // Do the event
        if(!event.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 1, z + 4), Material.CHEST);

        // Call the Chest generation event
        GenerationChestEvent event2 = new GenerationChestEvent(chunk.getBlock(x + 4, yFloor + 1, z + 4), rand, genChestContent(rand), MazeStructureType.LIBRARY_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event2);

        // Do the event
        if(!event2.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event2.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event2.getBlock(), event2.getContents(), !event2.getAddContentsInOrder(), rand);
        }

        // Add 4 lanterns on each side of the room near the book shelves
        MaterialUtils.setWallTorch(chunk.getBlock(x + 2, yFloor + 2, z + 1), BlockFace.SOUTH);
        MaterialUtils.setWallTorch(chunk.getBlock(x + 6, yFloor + 2, z + 2), BlockFace.WEST);
        MaterialUtils.setWallTorch(chunk.getBlock(x + 1, yFloor + 2, z + 5), BlockFace.EAST);
        MaterialUtils.setWallTorch(chunk.getBlock(x + 5, yFloor + 2, z + 6), BlockFace.NORTH);
	}
	
	public List<ItemStack> genChestContent(Random random) {
		// Create a list to put all the chest contents in
		List<ItemStack> items = new ArrayList<>();

		// Add the items to the list
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.TORCH, 16, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.TORCH, 20, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.ARROW, 24, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.ARROW, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.DIAMOND, 3, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(MaterialUtils.createItemStack(Material.IRON_INGOT, 3, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(MaterialUtils.createItemStack(Material.GOLD_INGOT, 3, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(MaterialUtils.createItemStack(Material.IRON_SWORD, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.MUSHROOM_STEW, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.IRON_HELMET, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.IRON_CHESTPLATE, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.IRON_LEGGINGS, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.IRON_BOOTS, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.DIAMOND_HELMET, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.DIAMOND_CHESTPLATE, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.DIAMOND_LEGGINGS, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.DIAMOND_BOOTS, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.FLINT, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.COOKED_PORKCHOP, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.GOLDEN_APPLE, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.REDSTONE, 7, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.CAKE, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.COOKIE, 8, (short) 0));

		// Determine the number of items to put in the chest
		int itemCountInChest;
		switch (random.nextInt(8)) {
		case 0:
			itemCountInChest = 2;
			break;
		case 1:
			itemCountInChest = 2;
			break;
		case 2:
			itemCountInChest = 3;
			break;
		case 3:
			itemCountInChest = 3;
			break;
		case 4:
			itemCountInChest = 4;
			break;
		case 5:
			itemCountInChest = 4;
			break;
		case 6:
			itemCountInChest = 4;
			break;
		case 7:
			itemCountInChest = 5;
			break;
		default:
			itemCountInChest = 4;
			break;
		}

		// Create the result list
		List<ItemStack> result = new ArrayList<>();
		
		// Add the selected items randomly
		for(int i = 0; i < itemCountInChest; i++)
			result.add(items.get(random.nextInt(items.size())));

		// Return the result
		return result;
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
