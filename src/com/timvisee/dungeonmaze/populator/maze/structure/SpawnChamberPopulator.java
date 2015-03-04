package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.GenerationChestEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.MazeStructureType;
import com.timvisee.dungeonmaze.util.ChestUtils;

public class SpawnChamberPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 7;
	public static final int LAYER_MAX = 7;
    public static final float ROOM_CHANCE = 1.0f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int y = args.getChunkY();
		final int z = args.getChunkZ();
		
		// Make sure this is the chunk at (0, 0)
		if(chunk.getX() != 0 || chunk.getZ() != 0 || x != 0 || z != 0)
			return;
							
		// Register the current room as constant room
		DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, y, z);
		
		// Break out the original walls
		for (int xx = 0; xx < 8; xx++)
			for (int yy = y + 2; yy < 30+(7*6); yy++)
				for(int zz = 0; zz < 8; zz++)
					chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
		
		// Generate corners
		for (int yy = y + 2; yy < 30+(7*6); yy++) {
			chunk.getBlock(x, yy, z).setType(Material.SMOOTH_BRICK);
			chunk.getBlock(x + 7, yy, z).setType(Material.SMOOTH_BRICK);
			chunk.getBlock(x, yy, z + 7).setType(Material.SMOOTH_BRICK);
			chunk.getBlock(x + 7, yy, z + 7).setType(Material.SMOOTH_BRICK);
		}
		
		//floor
		for (int xx=x; xx <= x + 7; xx++)
		    for (int zz=z; zz <= z + 7; zz++)
		        chunk.getBlock(xx, y + 1, zz).setType(Material.SMOOTH_BRICK);
		        
		// Change the layer underneath the stone floor to cobblestone
		for (int xx=x; xx <= x + 8; xx++)
		    for (int zz=z; zz <= z; zz++)
                chunk.getBlock(xx, y + 1, zz).setType(Material.COBBLESTONE);
		        
		//Ceiling
		for (int xx=x; xx <= x + 8; xx++)
		    for (int zz=z; zz <= z + 8; zz++)
		        chunk.getBlock(xx, y + 6, zz).setType(Material.SMOOTH_BRICK);
		
		// Generate 4 circular blocks in the middle of the floor
		for (int xx=x + 3; xx <= x + 4; xx++) {
		    for (int zz=z + 3; zz <= z + 4; zz++) {
		        chunk.getBlock(xx, y + 1, zz).setType(Material.SMOOTH_BRICK);
		        chunk.getBlock(xx, y + 1, zz).setData((byte) 3);
		    }
		}
		
		// Create walls
		for (int xx=x + 1; xx <= x + 6; xx++) {
            for (int yy=y + 2; yy <= y + 5; yy++) {
                chunk.getBlock(xx, yy, z).setType(Material.IRON_FENCE);
                chunk.getBlock(xx, yy, z + 7).setType(Material.IRON_FENCE);
            }
        }
		for (int zz=z + 1; zz <= z + 6; zz++) {
            for (int yy=y + 2; yy <= y + 5; yy++) {
                chunk.getBlock(x, yy, zz).setType(Material.IRON_FENCE);
                chunk.getBlock(x + 7, yy, zz).setType(Material.IRON_FENCE);
            }
        }
		
		// Create gates
		for (int xx=x + 2; xx <= x + 5; xx++) {
            for (int yy=y + 2; yy <= y + 4; yy++) {
                chunk.getBlock(xx, yy, z).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(xx, yy, z + 7).setType(Material.SMOOTH_BRICK);
            }
        }
		for (int zz=z + 2; zz <= z + 5; zz++) {
            for (int yy=y + 2; yy <= y + 4; yy++) {
                chunk.getBlock(x, yy, zz).setType(Material.SMOOTH_BRICK);
                chunk.getBlock(x + 7, yy, zz).setType(Material.SMOOTH_BRICK);
            }
        }
		for (int xx=x + 3; xx <= x + 4; xx++) {
            for (int yy=y + 2; yy <= y + 3; yy++) {
                chunk.getBlock(xx, yy, z).setType(Material.AIR);
                chunk.getBlock(xx, yy, z + 7).setType(Material.AIR);
            }
        }
		for (int zz=z + 3; zz <= z + 4; zz++) {
            for (int yy=y + 2; yy <= y + 3; yy++) {
                chunk.getBlock(x, yy, zz).setType(Material.AIR);
                chunk.getBlock(x + 7, yy, zz).setType(Material.AIR);
            }
        }

		// Empty ItemStack list for events
		List<ItemStack> emptyList = new ArrayList<ItemStack>();

		// Create chests
		chunk.getBlock(x + 1, y + 2, z + 1).setType(Material.CHEST);
		chunk.getBlock(x + 1, y + 2, z + 1).setData((byte) 3);
		
		// Call the Chest generation event
		GenerationChestEvent event = new GenerationChestEvent(chunk.getBlock(x + 1, y + 2, z + 1), rand, emptyList, MazeStructureType.SPAWN_ROOM);
		Bukkit.getServer().getPluginManager().callEvent(event);

		// Do the event
		if(!event.isCancelled()) {
			// Make sure the chest is still there, a developer could change the chest through the event!
			if(event.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
		}

		chunk.getBlock(x + 1, y + 2, z + 6).setType(Material.CHEST);
		chunk.getBlock(x + 1, y + 2, z + 6).setData((byte) 2);

		// Call the Chest generation event
		GenerationChestEvent event2 = new GenerationChestEvent(chunk.getBlock(x + 1, y + 2, z + 6), rand, emptyList, MazeStructureType.SPAWN_ROOM);
		Bukkit.getServer().getPluginManager().callEvent(event2);

		// Do the event
		if(!event2.isCancelled()) {
			// Make sure the chest is still there, a developer could change the chest through the event!
			if(event2.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
		}

		chunk.getBlock(x + 6, y + 2, z + 1).setType(Material.CHEST);
		chunk.getBlock(x + 6, y + 2, z + 1).setData((byte) 3);

		// Call the Chest generation event
		GenerationChestEvent event3 = new GenerationChestEvent(chunk.getBlock(x + 6, y + 2, z + 1), rand, emptyList, MazeStructureType.SPAWN_ROOM);
		Bukkit.getServer().getPluginManager().callEvent(event3);

		// Do the event
		if(!event3.isCancelled()) {
			// Make sure the chest is still there, a developer could change the chest through the event!
			if(event3.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
		}

		chunk.getBlock(x + 6, y + 2, z + 6).setType(Material.CHEST);
		chunk.getBlock(x + 6, y + 2, z + 6).setData((byte) 2);

		// Call the Chest generation event
		GenerationChestEvent event4 = new GenerationChestEvent(chunk.getBlock(x + 6, y + 2, z + 6), rand, emptyList, MazeStructureType.SPAWN_ROOM);
		Bukkit.getServer().getPluginManager().callEvent(event4);

		// Do the event
		if(!event4.isCancelled()) {
			// Make sure the chest is still there, a developer could change the chest through the event!
			if(event4.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
		}

		// Create torches
		chunk.getBlock(x + 1, y + 3, z + 2).setType(Material.TORCH);
		chunk.getBlock(x + 1, y + 3, z + 5).setType(Material.TORCH);
		chunk.getBlock(x + 6, y + 3, z + 2).setType(Material.TORCH);
		chunk.getBlock(x + 6, y + 3, z + 5).setType(Material.TORCH);
		chunk.getBlock(x + 2, y + 3, z + 1).setType(Material.TORCH);
		chunk.getBlock(x + 2, y + 3, z + 6).setType(Material.TORCH);
		chunk.getBlock(x + 5, y + 3, z + 1).setType(Material.TORCH);
		chunk.getBlock(x + 5, y + 3, z + 6).setType(Material.TORCH);
	}
	
	/* We actually do not use that for spawn (empty chests)
	public void addItemsToChest(Random random, Chest chest) {
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
		
		// Add the selected items to a random place inside the chest
		for (int i = 0; i < itemCountInChest; i++)
			chest.getInventory().setItem(random.nextInt(chest.getInventory().getSize()), items.get(random.nextInt(items.size())));
		
		chest.update();
	} */

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