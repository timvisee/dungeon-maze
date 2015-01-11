package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.TNTPrimed;

import com.timvisee.dungeonmaze.populator.ChunkBlockPopulator;
import com.timvisee.dungeonmaze.populator.ChunkBlockPopulatorArgs;

public class ExplosionPopulator extends ChunkBlockPopulator {
	
	public static final int EXPLOSION_CHANCE = 80;
	public static final int BIG_EXPLOSION_CHANCE = 50;
	public static final int HUGE_EXPLOSION_CHANCE = 10;

	@Override
	public void populateChunk(ChunkBlockPopulatorArgs args) {
		World w = args.getWorld();
		Random rand = args.getRandom();
		Chunk c = args.getSourceChunk();
		
		// Apply chances
		if (rand.nextInt(100) < EXPLOSION_CHANCE) {
			float power = 2.0f;
			if (rand.nextInt(100) < BIG_EXPLOSION_CHANCE) {
				power = 4.0f;
				if (rand.nextInt(100) < HUGE_EXPLOSION_CHANCE) {
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
			double x = rand.nextDouble() * 16 + c.getX() * 16;
			double z = rand.nextDouble() * 16 + c.getZ() * 16;
			w.createExplosion(x, rand.nextInt((30+(7*6))-30)+30, z, power);
		}
	}
}