package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.TNTPrimed;

import com.timvisee.dungeonmaze.populator.ChunkBlockPopulator;
import com.timvisee.dungeonmaze.populator.ChunkBlockPopulatorArgs;

public class ExplosionPopulator extends ChunkBlockPopulator {

    // FIXME: This populator has some terrible performance issues, fix this!

    /** General populator constants. */
	private static final float CHUNK_CHANCE = .8f;

    /** Populator constants. */
	private static final int CHANCE_EXPLOSION_BIG = 50;
	private static final int CHANCE_EXPLOSION_HUGE = 10;

	@Override
	public void populateChunk(ChunkBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Random rand = args.getRandom();
		final Chunk chunk = args.getSourceChunk();

		// Make sure the world instance is valid
		if(world == null)
			return;

        float power = 2.0f;
        if (rand.nextInt(100) < CHANCE_EXPLOSION_BIG) {
            power = 4.0f;
            if (rand.nextInt(100) < CHANCE_EXPLOSION_HUGE) {
                for (int x = -3; x <= 3; x++)
                    for (int z = -3; z <= 3; z++)
                        if (x != 0 || z != 0)
                            world.loadChunk(x + chunk.getX(), z + chunk.getZ());

                Location location = chunk.getBlock(8, 50, 8).getLocation();
                int tntCount = rand.nextInt(10) + 10;
                while (tntCount-- > 0) {
                    TNTPrimed tnt = world.spawn(location, TNTPrimed.class);
                    tnt.setIsIncendiary(false);
                    tnt.setFuseTicks(rand.nextInt(25) + 2);
                }
                for (int x = -3; x <= 3; x++)
                    for (int z = -3; z <= 3; z++)
                        if (x != 0 || z != 0)
                            world.unloadChunkRequest(x + chunk.getX(), z + chunk.getZ());
                return;
            }
        }

        final double x = rand.nextDouble() * 16 + chunk.getX() * 16;
        final double z = rand.nextDouble() * 16 + chunk.getZ() * 16;
        world.createExplosion(x, rand.nextInt((30 + (7 * 6)) - 30) + 30, z, power);
	}

    @Override
    public float getChunkIterationsChance() {
        return CHUNK_CHANCE;
    }
}