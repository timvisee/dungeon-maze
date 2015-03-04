package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.TNTPrimed;

import com.timvisee.dungeonmaze.populator.ChunkBlockPopulator;
import com.timvisee.dungeonmaze.populator.ChunkBlockPopulatorArgs;

public class ExplosionPopulator extends ChunkBlockPopulator {

    /** General populator constants. */
	public static final float CHUNK_CHANCE = .8f;

    /** Populator constants. */
	public static final int CHANCE_EXPLOSION_BIG = 50;
	public static final int CHANCE_EXPLOSION_HUGE = 10;

	@Override
	public void populateChunk(ChunkBlockPopulatorArgs args) {
		final World w = args.getWorld();
		final Random rand = args.getRandom();
		final Chunk c = args.getSourceChunk();

		// Make sure the world instance is valid
		if(w == null)
			return;

        float power = 2.0f;
        if (rand.nextInt(100) < CHANCE_EXPLOSION_BIG) {
            power = 4.0f;
            if (rand.nextInt(100) < CHANCE_EXPLOSION_HUGE) {
                for (int x = -3; x <= 3; x++)
                    for (int z = -3; z <= 3; z++)
                        if (x != 0 || z != 0)
                            w.loadChunk(x + c.getX(), z + c.getZ());

                Location location = c.getBlock(8, 50, 8).getLocation();
                int tntCount = rand.nextInt(10) + 10;
                while (tntCount-- > 0) {
                    TNTPrimed tnt = w.spawn(location, TNTPrimed.class);
                    tnt.setIsIncendiary(false);
                    tnt.setFuseTicks(rand.nextInt(25) + 2);
                }
                for (int x = -3; x <= 3; x++)
                    for (int z = -3; z <= 3; z++)
                        if (x != 0 || z != 0)
                            w.unloadChunkRequest(x + c.getX(), z + c.getZ());
                return;
            }
        }

        final double x = rand.nextDouble() * 16 + c.getX() * 16;
        final double z = rand.nextDouble() * 16 + c.getZ() * 16;
        w.createExplosion(x, rand.nextInt((30 + (7 * 6)) - 30) + 30, z, power);
	}

    @Override
    public float getChunkPopulationChance() {
        return CHUNK_CHANCE;
    }
}