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

    /** General populator constants. */
	public static final int LAYER_MIN = 3;
	public static final int LAYER_MAX = 7;
    public static final int ROOM_ITERATIONS = 3;
	public static final float ROOM_ITERATIONS_CHANCE = .08f;
    public static final int ROOM_ITERATIONS_MAX = 2;

    /** Populator constants. */
    public static final float MINECART_CHANCE = .01f;
	public static final float BROKEN_RAIL_CHANCE = .2f;

	public static final BlockFace[] dirs = new BlockFace[] {
			BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };

    // TODO: Implement this feature!
    public static final double RAIL_CHANCE_ADDITION_EACH_LEVEL = -0.333; /* to 6 */

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
		for(int i = 0; i < ROOM_ITERATIONS; i++) {
			
			if(rails <= ROOM_ITERATIONS_MAX) {
				
				if (rand.nextInt(100) < ROOM_ITERATIONS_CHANCE +(RAIL_CHANCE_ADDITION_EACH_LEVEL *(y-30)/6)) {
					int startX = x + rand.nextInt(6) + 1;
					int startZ = z + rand.nextInt(6) + 1;

					BlockFace dir1 = dirs[rand.nextInt(dirs.length)];
					BlockFace dir2 = dirs[rand.nextInt(dirs.length)];

					int x2 = startX;
					int z2 = startZ;
					while(0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
						if(rand.nextFloat() > BROKEN_RAIL_CHANCE) {
							c.getBlock(x2, yFloor + 1, z2).setType(Material.RAILS);
							if(rand.nextFloat() < MINECART_CHANCE)
								spawnMinecart(w, (c.getX() * 16) + x + x2, yFloor + 1, (c.getZ() * 16) + z + z2);
						}

						x2 += dir1.getModX();
						z2 += dir1.getModZ();
					}

					if(dir1 != dir2) {
						x2 = startX;
						z2 = startZ;
						while (0 <= x2 && x2 < 8 && 0 <= z2 && z2 < 8) {
							if(rand.nextFloat() > BROKEN_RAIL_CHANCE) {
								c.getBlock(x2, yFloor + 1, z2).setType(Material.RAILS);
								if(rand.nextFloat() < MINECART_CHANCE)
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

    @Override
    public int getRoomIterations() {
        return ROOM_ITERATIONS;
    }

    @Override
    public float getRoomIterationsChance() {
        return ROOM_ITERATIONS_CHANCE;
    }

    @Override
    public int getRoomIterationsMax() {
        return ROOM_ITERATIONS_MAX;
    }
}
