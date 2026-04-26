package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Cake;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.GenerationChestEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.MazeStructureType;
import com.timvisee.dungeonmaze.util.ChestUtils;
import com.timvisee.dungeonmaze.util.MaterialUtils;

public class AbandonedDefenceCastleRoomPopulator extends MazeRoomBlockPopulator {

	// TODO: Use material enums instead of ID's due to ID deprecation by Mojang

    /** General populator constants. */
	private static final int LAYER_MIN = 2;
	private static final int LAYER_MAX = 6;
	private static final float ROOM_CHANCE = .001f;

    /** Populator constants. */
	private static final float MOSS_CHANCE = .7f;
	private static final int MOSS_ITERATIONS = 80;
	private static final float CRACKED_CHANCE = .7f;
	private static final int CRACKED_ITERATIONS = 80;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int floorOffset = args.getFloorOffset();
		final int yFloor = args.getFloorY();
		final int yCeiling = args.getCeilingY();
		final int z = args.getRoomChunkZ();

        // Register the room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

        // Break out the original walls
        for(int xx = 1; xx < 7; xx++) {
            for(int yy = yFloor + 1; yy <= yCeiling - 1; yy++) {
                setGeneratedBlock(chunk.getBlock(x + xx, yy, z), Material.AIR);
                setGeneratedBlock(chunk.getBlock(x + xx, yy, z + 7), Material.AIR);
                setGeneratedBlock(chunk.getBlock(x, yy, z + xx), Material.AIR);
                setGeneratedBlock(chunk.getBlock(x + 7, yy, z + xx), Material.AIR);
            }
        }

        // Walls
        for(int xx = 1; xx < 7; xx++) {
            for(int yy = floorOffset + 1; yy <= floorOffset + 2; yy++) {
                setGeneratedBlock(chunk.getBlock(x + xx, y + yy, z + 1), Material.STONE_BRICKS);
                setGeneratedBlock(chunk.getBlock(x + xx, y + yy, z + 6), Material.STONE_BRICKS);
                setGeneratedBlock(chunk.getBlock(x + 1, y + yy, z + xx), Material.STONE_BRICKS);
                setGeneratedBlock(chunk.getBlock(x + 6, y + yy, z + xx), Material.STONE_BRICKS);
            }
        }

        // Generate merlons
        for(int xx = 0; xx < 7; xx++) {
            setGeneratedBlock(chunk.getBlock(x + xx, yFloor + 3, z), Material.STONE_BRICKS);
            setGeneratedBlock(chunk.getBlock(x + xx, yFloor + 3, z + 7), Material.STONE_BRICKS);
            setGeneratedBlock(chunk.getBlock(x, yFloor + 3, z + xx), Material.STONE_BRICKS);
            setGeneratedBlock(chunk.getBlock(x + 7, yFloor + 3, z + xx), Material.STONE_BRICKS);
        }

        setGeneratedBlock(chunk.getBlock(x, yFloor + 4, z + 1), Material.STONE_BRICK_SLAB);
        setGeneratedBlock(chunk.getBlock(x, yFloor + 4, z + 3), Material.STONE_BRICK_SLAB);
        setGeneratedBlock(chunk.getBlock(x, yFloor + 4, z + 5), Material.STONE_BRICK_SLAB);
        setGeneratedBlock(chunk.getBlock(x + 7, yFloor + 4, z + 2), Material.STONE_BRICK_SLAB);
        setGeneratedBlock(chunk.getBlock(x + 7, yFloor + 4, z + 4), Material.STONE_BRICK_SLAB);
        setGeneratedBlock(chunk.getBlock(x + 7, yFloor + 4, z + 6), Material.STONE_BRICK_SLAB);
        setGeneratedBlock(chunk.getBlock(x + 1, yFloor + 4, z), Material.STONE_BRICK_SLAB);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 4, z), Material.STONE_BRICK_SLAB);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 4, z), Material.STONE_BRICK_SLAB);
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 4, z + 7), Material.STONE_BRICK_SLAB);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 4, z + 7), Material.STONE_BRICK_SLAB);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 4, z + 7), Material.STONE_BRICK_SLAB);

        // Place torches (floor torches, no facing data needed in 1.13+)
        setGeneratedBlock(chunk.getBlock(x + 1, yFloor + 3, z + 1), Material.TORCH);
        setGeneratedBlock(chunk.getBlock(x + 1, yFloor + 3, z + 6), Material.TORCH);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 3, z + 1), Material.TORCH);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 3, z + 6), Material.TORCH);

        // Place ladders
        placeDirectional(chunk.getBlock(x + 2, yFloor + 1, z + 5), Material.LADDER, BlockFace.WEST);
        placeDirectional(chunk.getBlock(x + 2, yFloor + 2, z + 5), Material.LADDER, BlockFace.WEST);

        // Place crafting table, chests and furnaces
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 1, z + 2), Material.CRAFTING_TABLE);
        placeDirectional(chunk.getBlock(x + 5, yFloor + 1, z + 2), Material.CHEST, BlockFace.NORTH);

        //Call the Chest generation event
        GenerationChestEvent event = new GenerationChestEvent(chunk.getBlock(x + 5, yFloor + 1, z + 2), rand, genChestContent(rand), MazeStructureType.ABANDONED_DEFENCE_CASTLE_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event);

        // Do the event
        if(!event.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event.getBlock().getType() == Material.CHEST)
            // Add the contents to the chest
            ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        placeDirectional(chunk.getBlock(x + 5, yFloor + 1, z + 3), Material.CHEST, BlockFace.NORTH);

        //Call the Chest generation event
        GenerationChestEvent event2 = new GenerationChestEvent(chunk.getBlock(x + 5, yFloor + 1, z + 3), rand, genChestContent(rand), MazeStructureType.ABANDONED_DEFENCE_CASTLE_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event2);

        // Do the event
        if(!event2.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event2.getBlock().getType() == Material.CHEST)
            // Add the contents to the chest
            ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        placeDirectional(chunk.getBlock(x + 5, yFloor + 1, z + 4), Material.FURNACE, BlockFace.WEST);
        placeDirectional(chunk.getBlock(x + 5, yFloor + 1, z + 5), Material.FURNACE, BlockFace.WEST);
		try {
			addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 5, yFloor + 1, z + 4).getState());
			addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 5, yFloor + 1, z + 5).getState());
		} catch(Exception ex) {
			// Show a proper error message
			Core.getLogger().error("Failed to add items to furnace inventory");
		}

        // Place cake (with random pieces eaten)
        Block cakeBlock = chunk.getBlock(x + 5, yFloor + 2, z + 5);
        setGeneratedBlock(cakeBlock, Material.CAKE);
        Cake cakeData = (Cake) cakeBlock.getBlockData();
        cakeData.setBites(rand.nextInt(4));
        setGeneratedBlockData(cakeBlock, cakeData);

        // TODO: Place painting

        // Place some cobweb
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 2, z + 2), Material.COBWEB);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1, z + 2), Material.COBWEB);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 3, z + 6), Material.COBWEB);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 4, z + 6), Material.COBWEB);
        setGeneratedBlock(chunk.getBlock(x + 5, yFloor + 3, z + 6), Material.COBWEB);
        setGeneratedBlock(chunk.getBlock(x + 6, yFloor + 3, z + 5), Material.COBWEB);
        setGeneratedBlock(chunk.getBlock(x, yFloor + 4, z + 6), Material.COBWEB);

        // Add some moss and cracked stone bricks
        for (int i = 0; i < MOSS_ITERATIONS; i++) {
            if (rand.nextInt(100) < MOSS_CHANCE) {

                Block block = chunk.getBlock(x + rand.nextInt(8), rand.nextInt((y + 6) - y + 1) + y, z + rand.nextInt(8));
                if (block.getType() == Material.COBBLESTONE)
                    setGeneratedBlock(block, Material.MOSSY_COBBLESTONE);

                if (block.getType() == Material.STONE_BRICKS)
                    setGeneratedBlock(block, Material.MOSSY_STONE_BRICKS);
            }
        }

        for (int i = 0; i < CRACKED_ITERATIONS; i++) {
            if (rand.nextInt(100) < CRACKED_CHANCE) {

                Block block = chunk.getBlock(x + rand.nextInt(8), rand.nextInt((y + 6) - y + 1) + y, z + rand.nextInt(8));
                if (block.getType() == Material.STONE_BRICKS)
                    setGeneratedBlock(block, Material.CRACKED_STONE_BRICKS);
            }
        }
	}
	
	private void addItemsToFurnace(Random random, Furnace furnace) {
        // Create a list to put the items in
		List<ItemStack> items = new ArrayList<>();

        // Put the items in the list
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.GOLD_BLOCK, 1, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.IRON_LEGGINGS, 1, (short) 0));
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
		if(random.nextInt(100) < 70)
			furnace.getInventory().setResult(items.get(random.nextInt(items.size())));

        // Update the furnace
		furnace.update();
	}

	private List<ItemStack> genChestContent(Random random) {
        // Create a list to put the chest items in
		List<ItemStack> items = new ArrayList<>();

        // Put the items in the list
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.TORCH, 4, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.TORCH, 8, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.TORCH, 12, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.APPLE, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.ARROW, 16, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.ARROW, 24, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.DIAMOND, 1, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(MaterialUtils.createItemStack(Material.IRON_INGOT, 1, (short) 0));
		if(random.nextInt(100) < 60)
			items.add(MaterialUtils.createItemStack(Material.GOLD_INGOT, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.IRON_SWORD, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.WOODEN_SWORD, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.STONE_SWORD, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.WHEAT, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.WHEAT, 2, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.WHEAT, 3, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.BREAD, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.LEATHER_HELMET, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.LEATHER_CHESTPLATE, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.LEATHER_LEGGINGS, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.LEATHER_BOOTS, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.CHAINMAIL_HELMET, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.CHAINMAIL_CHESTPLATE, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.CHAINMAIL_LEGGINGS, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(MaterialUtils.createItemStack(Material.CHAINMAIL_BOOTS, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.IRON_HELMET, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.IRON_CHESTPLATE, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.IRON_LEGGINGS, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.IRON_BOOTS, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(MaterialUtils.createItemStack(Material.FLINT, 3, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.FLINT, 5, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.FLINT, 7, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.PORKCHOP, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.COOKED_PORKCHOP, 1, (short) 0));
		if(random.nextInt(100) < 15)
			items.add(MaterialUtils.createItemStack(Material.REDSTONE, 5, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.REDSTONE, 8, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.REDSTONE, 13, (short) 0));
		if(random.nextInt(100) < 3)
			items.add(MaterialUtils.createItemStack(Material.REDSTONE, 21, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(MaterialUtils.createItemStack(Material.COMPASS, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.COD, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.COOKED_COD, 1, (short) 0));
        if(random.nextInt(100) < 20)
            items.add(MaterialUtils.createItemStack(Material.COOKED_COD, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.INK_SAC, 1, (short) 3));
		if(random.nextInt(100) < 5)
			items.add(MaterialUtils.createItemStack(Material.CAKE, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(MaterialUtils.createItemStack(Material.COOKIE, 3, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(MaterialUtils.createItemStack(Material.COOKIE, 5, (short) 0));

        // Determine how many items to put in the chest
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
			itemCountInChest = 3;
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
			itemCountInChest = 3;
			break;
		}

        // Create a list of results
		List<ItemStack> result = new ArrayList<>();
		
		// Add the selected items randomly
		for (int i = 0; i < itemCountInChest; i++)
			result.add(items.get(random.nextInt(items.size())));

        // Return the list of results
		return result;
	}

    private void placeDirectional(Block block, Material material, BlockFace facing) {
        setGeneratedBlock(block, material);
        Directional d = (Directional) block.getBlockData();
        d.setFacing(facing);
        setGeneratedBlockData(block, d);
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
