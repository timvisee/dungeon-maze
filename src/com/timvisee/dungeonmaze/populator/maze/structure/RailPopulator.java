package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class RailPopulator extends MazeRoomBlockPopulator {

	public static final int LAYER_MIN = 3;
	public static final int LAYER_MAX = 7;
	public static final int RAIL_CHANCE = 8;
	public static final double RAIL_CHANCE_ADDITION_EACH_LEVEL = -0.333; /* to 6 */
	public static final int ITERATIONS = 2;
	public static final int RAIL_MAX = 2;
	public static final int BROKEN_RAIL_CHANCE = 20;
	public static final int MINECART_ON_RAIL_CHANCE = 1;
	public static final BlockFace[] dirs = new BlockFace[] {
			BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getChunkZ();
		
		// Count the amount of placed rails
		int rails = 0;
				
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			
			if(rails <= RAIL_MAX) {
				
				if (rand.nextInt(100) < RAIL_CHANCE+(RAIL_CHANCE_ADDITION_EACH_LEVEL *(y-30)/6)) {
					int startX = x + rand.nextInt(6) + 1;
					int startZ = z + rand.nextInt(6) + 1;

					BlockFace dir1 = dirs[rand.nextInt(dirs.length)];
					BlockFace dir2 = dirs[rand.nextInt(dirs.length)];

					int x2 = startX;
					int z2 = startZ;
					while(0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
						if(rand.nextInt(100) > BROKEN_RAIL_CHANCE) {
							c.getBlock(x2, yFloor + 1, z2).setType(Material.RAILS);
							if(rand.nextInt(100) < MINECART_ON_RAIL_CHANCE)
								spawnMinecart(w, (c.getX() * 16) + x + x2, yFloor + 1, (c.getZ() * 16) + z + z2);
						}

						x2 += dir1.getModX();
						z2 += dir1.getModZ();
					}

					if(dir1 != dir2) {
						x2 = startX;
						z2 = startZ;
						while (0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
							if(rand.nextInt(100) > BROKEN_RAIL_CHANCE) {
								c.getBlock(x2, yFloor + 1, z2).setType(Material.RAILS);
								if(rand.nextInt(100) < MINECART_ON_RAIL_CHANCE)
									spawnMinecart(w, (c.getX() * 16) + x + x2, yFloor + 1, (c.getZ() * 16) + z + z2);
							}

							x2 += dir2.getModX();
							z2 += dir2.getModZ();
						}
					}

					rails++;
				}
			}
		}
	}
	
	public Minecart spawnMinecart(World world, int x, int y, int z) {
		return spawnMinecart(new Location(world, x, y, z));
	}
	
	public Minecart spawnMinecart(Location location) {
    	return spawnMinecart(location.getWorld(), location);
    }
	
    public Minecart spawnMinecart(World world, Location location) {
    	return world.spawn(location, Minecart.class);
    }

    @Override
    public float getRoomPopulationChance() {
        // TODO: Improve this!
        return 1.0f;
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
