package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.GenerationChestEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.MazeStructureType;
import com.timvisee.dungeonmaze.util.ChestUtils;

public class AbandonedDefenceCastleRoomPopulator extends MazeRoomBlockPopulator {

	// TODO: Use material enums instead of ID's due to ID deprecation by Mojang

    /** General populator constants. */
	public static final int LAYER_MIN = 2;
	public static final int LAYER_MAX = 6;
	public static final float ROOM_CHANCE = .001f;

    /** Populator constants. */
	public static final float MOSS_CHANCE = .7f;
	public static final int MOSS_ITERATIONS = 80;
	public static final float CRACKED_CHANCE = .7f;
	public static final int CRACKED_ITERATIONS = 80;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int y = args.getChunkY();
		final int floorOffset = args.getFloorOffset();
		final int yFloor = args.getFloorY();
		final int yCeiling = args.getCeilingY();
		final int z = args.getChunkZ();

        // Register the room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

        // Break out the original walls
        for(int xx = 1; xx < 7; xx++) {
            for(int yy = yFloor + 1; yy <= yCeiling - 1; yy++) {
                chunk.getBlock(x + xx, yy, z + 0).setType(Material.AIR);
                chunk.getBlock(x + xx, yy, z + 7).setType(Material.AIR);
                chunk.getBlock(x + 0, yy, z + xx).setType(Material.AIR);
                chunk.getBlock(x + 7, yy, z + xx).setType(Material.AIR);
            }
        }

        // Walls
        for(int xx = 1; xx < 7; xx++) {
            for(int yy = floorOffset + 1; yy <= floorOffset + 2; yy++) {
                chunk.getBlock(x + xx, y + yy, z + 1).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(x + xx, y + yy, z + 6).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(x + 1, y + yy, z + xx).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(x + 6, y + yy, z + xx).setType(Material.SMOOTH_BRICK);
            }
        }

        // Generate merlons
        for(int xx = 0; xx < 7; xx++) {
            chunk.getBlock(x + xx, yFloor + 3, z + 0).setType(Material.SMOOTH_BRICK);
            chunk.getBlock(x + xx, yFloor + 3, z + 7).setType(Material.SMOOTH_BRICK);
            chunk.getBlock(x + 0, yFloor + 3, z + xx).setType(Material.SMOOTH_BRICK);
            chunk.getBlock(x + 7, yFloor + 3, z + xx).setType(Material.SMOOTH_BRICK);
        }

        chunk.getBlock(x + 0, yFloor + 4, z + 1).setType(Material.STEP);
        chunk.getBlock(x + 0, yFloor + 4, z + 1).setData((byte) 5);
        chunk.getBlock(x + 0, yFloor + 4, z + 3).setType(Material.STEP);
        chunk.getBlock(x + 0, yFloor + 4, z + 3).setData((byte) 5);
        chunk.getBlock(x + 0, yFloor + 4, z + 5).setType(Material.STEP);
        chunk.getBlock(x + 0, yFloor + 4, z + 5).setData((byte) 5);
        chunk.getBlock(x + 7, yFloor + 4, z + 2).setType(Material.STEP);
        chunk.getBlock(x + 7, yFloor + 4, z + 2).setData((byte) 5);
        chunk.getBlock(x + 7, yFloor + 4, z + 4).setType(Material.STEP);
        chunk.getBlock(x + 7, yFloor + 4, z + 4).setData((byte) 5);
        chunk.getBlock(x + 7, yFloor + 4, z + 6).setType(Material.STEP);
        chunk.getBlock(x + 7, yFloor + 4, z + 6).setData((byte) 5);
        chunk.getBlock(x + 1, yFloor + 4, z + 0).setType(Material.STEP);
        chunk.getBlock(x + 1, yFloor + 4, z + 0).setData((byte) 5);
        chunk.getBlock(x + 3, yFloor + 4, z + 0).setType(Material.STEP);
        chunk.getBlock(x + 3, yFloor + 4, z + 0).setData((byte) 5);
        chunk.getBlock(x + 5, yFloor + 4, z + 0).setType(Material.STEP);
        chunk.getBlock(x + 5, yFloor + 4, z + 0).setData((byte) 5);
        chunk.getBlock(x + 2, yFloor + 4, z + 7).setType(Material.STEP);
        chunk.getBlock(x + 2, yFloor + 4, z + 7).setData((byte) 5);
        chunk.getBlock(x + 4, yFloor + 4, z + 7).setType(Material.STEP);
        chunk.getBlock(x + 4, yFloor + 4, z + 7).setData((byte) 5);
        chunk.getBlock(x + 6, yFloor + 4, z + 7).setType(Material.STEP);
        chunk.getBlock(x + 6, yFloor + 4, z + 7).setData((byte) 5);

        // Place torches
        chunk.getBlock(x + 1, yFloor + 3, z + 1).setType(Material.TORCH);
        chunk.getBlock(x + 1, yFloor + 3, z + 1).setData((byte) 5);
        chunk.getBlock(x + 1, yFloor + 3, z + 6).setType(Material.TORCH);
        chunk.getBlock(x + 1, yFloor + 3, z + 6).setData((byte) 5);
        chunk.getBlock(x + 6, yFloor + 3, z + 1).setType(Material.TORCH);
        chunk.getBlock(x + 6, yFloor + 3, z + 1).setData((byte) 5);
        chunk.getBlock(x + 6, yFloor + 3, z + 6).setType(Material.TORCH);
        chunk.getBlock(x + 6, yFloor + 3, z + 6).setData((byte) 5);

        // Place ladders
        chunk.getBlock(x + 2, yFloor + 1, z + 5).setType(Material.LADDER);
        chunk.getBlock(x + 2, yFloor + 1, z + 5).setData((byte) 2);
        chunk.getBlock(x + 2, yFloor + 2, z + 5).setType(Material.LADDER);
        chunk.getBlock(x + 2, yFloor + 2, z + 5).setData((byte) 2);

        // Place crafting table, chests and furnaces
        chunk.getBlock(x + 2, yFloor + 1, z + 2).setType(Material.WORKBENCH);
        chunk.getBlock(x + 5, yFloor + 1, z + 2).setType(Material.CHEST);
        chunk.getBlock(x + 5, yFloor + 1, z + 2).setData((byte) 2);

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

        chunk.getBlock(x + 5, yFloor + 1, z + 3).setType(Material.CHEST);
        chunk.getBlock(x + 5, yFloor + 1, z + 3).setData((byte) 2);

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

        chunk.getBlock(x + 5, yFloor + 1, z + 4).setType(Material.FURNACE);
        chunk.getBlock(x + 5, yFloor + 1, z + 4).setData((byte) 4);
        addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 5, yFloor + 1, z + 4).getState());
        chunk.getBlock(x + 5, yFloor + 1, z + 5).setType(Material.FURNACE);
        chunk.getBlock(x + 5, yFloor + 1, z + 5).setData((byte) 4);
        addItemsToFurnace(rand, (Furnace) chunk.getBlock(x + 5, yFloor + 1, z + 5).getState());

        // Place cake (with random pieces eaten)
        chunk.getBlock(x + 5, yFloor + 2, z + 5).setType(Material.CAKE_BLOCK);
        chunk.getBlock(x + 5, yFloor + 2, z + 5).setData((byte) rand.nextInt(4));

        // TODO: Place painting

        // Place some cobweb
        chunk.getBlock(x + 2, yFloor + 2, z + 2).setType(Material.WEB);
        chunk.getBlock(x + 3, yFloor + 1, z + 2).setType(Material.WEB);
        chunk.getBlock(x + 6, yFloor + 3, z + 6).setType(Material.WEB);
        chunk.getBlock(x + 6, yFloor + 4, z + 6).setType(Material.WEB);
        chunk.getBlock(x + 5, yFloor + 3, z + 6).setType(Material.WEB);
        chunk.getBlock(x + 6, yFloor + 3, z + 5).setType(Material.WEB);
        chunk.getBlock(x + 0, yFloor + 4, z + 6).setType(Material.WEB);

        // Add some moss and cracked stone bricks
        for (int i = 0; i < MOSS_ITERATIONS; i++) {
            if (rand.nextInt(100) < MOSS_CHANCE) {

                Block block = chunk.getBlock(x + rand.nextInt(8), rand.nextInt((y + 6) - y + 1) + y, z + rand.nextInt(8));
                if (block.getType() == Material.COBBLESTONE)
                    block.setType(Material.MOSSY_COBBLESTONE);

                if (block.getType() == Material.SMOOTH_BRICK)
                    block.setData((byte) 1);
            }
        }

        for (int i = 0; i < CRACKED_ITERATIONS; i++) {
            if (rand.nextInt(100) < CRACKED_CHANCE) {

                Block block = chunk.getBlock(x + rand.nextInt(8), rand.nextInt((y + 6) - y + 1) + y, z + rand.nextInt(8));
                if (block.getType() == Material.SMOOTH_BRICK)
                    block.setData((byte) 2);
            }
        }
	}
	
	public void addItemsToFurnace(Random random, Furnace furnace) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(41, 1, (short) 0));
		
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(42, 1, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(45, 1, (short) 0));
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(263, 1, (short) 0));
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(263, 1, (short) 1));
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(265, 2, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(265, 4, (short) 0));
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(266, 2, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(266, 4, (short) 0));
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(297, 1, (short) 0));
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(325, 1, (short) 0));
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(366, 2, (short) 0));
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(366, 4, (short) 0));
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(318, 3, (short) 0));
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(318, 5, (short) 0));
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(320, 1, (short) 0));
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(350, 1, (short) 0));
		
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(368, 1, (short) 0));
		
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(369, 1, (short) 0));
		
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(370, 1, (short) 0));
		
		if(random.nextInt(100) < 45)
			items.add(new ItemStack(371, 1, (short) 0));
		
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(372, 1, (short) 0));
		
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(375, 1, (short) 0));
		
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(377, 1, (short) 0));
		
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(378, 1, (short) 0));
		
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(381, 1, (short) 0));
		
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(382, 1, (short) 0));
		
		// Add the selected items into the furnace
		if(random.nextInt(100) < 70)
			furnace.getInventory().setResult(/*random.nextInt(furnace.getInventory().getSize()), */items.get(random.nextInt(items.size())));
		furnace.update();
	}
	
	public List<ItemStack> genChestContent(Random random) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(50, 4, (short) 0));
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(50, 8, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(50, 12, (short) 0));
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(260, 1, (short) 0));
		
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(262, 16, (short) 0));
		
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(262, 24, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(264, 1, (short) 0));
		
		if(random.nextInt(100) < 50)
			items.add(new ItemStack(265, 1, (short) 0));
		
		if(random.nextInt(100) < 60)
			items.add(new ItemStack(266, 1, (short) 0));
		
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(267, 1, (short) 0));
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(268, 1, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(272, 1, (short) 0));
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(296, 1, (short) 0));
		
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(296, 2, (short) 0));
		
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(296, 3, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(297, 1, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(298, 1, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(299, 1, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(300, 1, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(301, 1, (short) 0));
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(302, 1, (short) 0)); 
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(303, 1, (short) 0));
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(304, 1, (short) 0));
		
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(305, 1, (short) 0));
		
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(306, 1, (short) 0));
		
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(307, 1, (short) 0));
		
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(308, 1, (short) 0));
		
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(309, 1, (short) 0));
		
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(318, 3, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(318, 5, (short) 0));
		
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(318, 7, (short) 0));
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(319, 1, (short) 0));
		
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(320, 1, (short) 0));
		
		if(random.nextInt(100) < 15)
			items.add(new ItemStack(331, 5, (short) 0));
		
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(331, 8, (short) 0));
		
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(331, 13, (short) 0));
		
		if(random.nextInt(100) < 3)
			items.add(new ItemStack(331, 21, (short) 0));
		
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(345, 1, (short) 0));
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(349, 1, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(350, 1, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(350, 1, (short) 0));
		
		if(random.nextInt(100) < 20) 			
			items.add(new ItemStack(351, 1, (short) 3));
		
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(354, 1, (short) 0));
		
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(357, 3, (short) 0));
		
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(357, 5, (short) 0));
		
		int itemCountInChest = 3;
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
		
		List<ItemStack> result = new ArrayList<ItemStack>();
		
		// Add the selected items randomly
		for (int i = 0; i < itemCountInChest; i++)
			result.add(items.get(random.nextInt(items.size())));

		return result;
	}

    @Override
    public float getRoomPopulationChance() {
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