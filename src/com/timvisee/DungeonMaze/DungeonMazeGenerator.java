package com.timvisee.DungeonMaze;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import com.timvisee.DungeonMaze.populator.*;

public class DungeonMazeGenerator extends ChunkGenerator {
	
	public static DungeonMaze plugin;
	
	public DungeonMazeGenerator(DungeonMaze instance) {
		plugin = instance;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList(
				/*new BrokenWallsPopulator(),*/
				new SpawnChamberPopulator(),
				new OresInGroundPopulator(),
				new OasisChunkPopulator(),
				new BossRoomHardPopulator(),
				new BossRoomInsanePopulator(),
				new LibraryRoomPopulator(),
				new AbandonedDefenceCastleRoomPopulator(),
				/*new ArmoryRoomPopulator(),*/
				new WaterWellRoomPopulator(),
				new SanctuaryPopulator(),
				new BlazeSpawnerRoomPopulator(),
				new HighRoomPopulator(),
				new BossRoomEasyPopulator(),
				new MassiveRoomPopulator(),
				new FloodedRoomPopulator(),
				new GreatFurnaceRoomPopulator(),
				new EntrancePopulator(),
				new TreePopulator(),
				new TallGrassPopulator(),
				new FlowerPopulator(),
				new MossPopulator(),
				new CrackedStoneBrickPopulator(),
				new NetherrackPopulator(),
				new SoulsandPopulator(),
				new CoalorePopulator(),
				new StrutPopulator(),
				new StairsPopulator(),
				new LadderPopulator(),
				new PoolPopulator(),
				new LavaOutOfWallPopulator(),
				new WaterOutOfWallPopulator(),
				new RailPopulator(),
				new TopTurveRoomPopulator(),
				new CreeperSpawnerRoomPopulator(),
				new GravePopulator(),
				new ChestPopulator(plugin),
				new SpawnerPopulator(),
				new SandPopulator(),
				new GravelPopulator(),
				new RuinsPopulator(),
				new IronBarPopulator(),
				new SlabPopulator(),
				new TorchPopulator(),
				new LanternPopulator(),
				new SilverfishBlockPopulator(),
				new WebPopulator(),
				new VinePopulator(),
				new CobblestonePopulator(),
				new MushroomPopulator(),
				new ExplosionPopulator()
				);
	}

	// Here you set the monster spawning to true (you override the default server settings)
	@Override
	public boolean canSpawn(World world, int x, int z) {
		return true;
	}

	// This will return the world spawn location
	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 4, 68, 4);
	}

	// This will convert the relative chunks to bytes that can be written to the chunk
	public int xyzToByte(int x, int y, int z) {
		return (x * 16 + z) * 128 + y;
	}

	// Generate a chunk
	@Override
	public byte[] generate(World world, Random rand, int chunkx, int chunkz) {
		// Create a byte variable to write the chunk inside and return this variable
		byte[] result = new byte[32768];
		
		// This will set the whole floor to stone (the floor of each chunk)
		for (int y = 30 + 3; y > 0; y--)
			for (int x = 0; x < 16; x++)
				for (int z = 0; z < 16; z++)
					result[xyzToByte(x, y, z)] = (byte) Material.STONE.getId();
		
		// Set the lowest layer to bedrock
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++)
				result[xyzToByte(x, 0, z)] = (byte) Material.BEDROCK.getId();

		// The layers for each 5 rooms in the variable y
		for (int y=30; y < 30+(7*6); y+=6) {
			
			// The 4 rooms on each layer saved in the variables x and z
			for (int x=0; x < 16; x+=8) {
				for (int z=0; z < 16; z+=8) {
					
					int xr = (rand.nextInt(3) - 1) * (x + 7);
					int zr = (rand.nextInt(3) - 1) * (z + 7);
					
					int yfloor = rand.nextInt(2);
					
					// All the y of the room in the variable y2
					for (int y2 = y + yfloor; y2 < y+8; y2++) {
						
						// All the x of the room in the variable x2
						for (int x2 = x; x2 < x+8; x2++) {
							
							// All the z of the room in the variable z2
							for (int z2 = z; z2 < z+8; z2++) {
								
								// Make the bottom of the room
								if(y2 == y + yfloor)
									for (int xb = x; xb < x + 8; xb++)
										for (int zb = z; zb < z + 8; zb++)
											result[xyzToByte(xb, y2, zb)] = (byte) Material.COBBLESTONE.getId();
								
								// Fill the walls of the place with cobblestone
								if ((x2 == x || x2 == x + 7) && (z2 == z || z2 == z + 7))
									result[xyzToByte(x2, y2, z2)] = (byte) 98;
								else if (xr == x2)
									result[xyzToByte(x2, y2, z2)] = (byte) 98;
								else if (zr == z2)
									result[xyzToByte(x2, y2, z2)] = (byte) 98;
								else
									result[xyzToByte(x2, y2, z2)] = (byte) Material.AIR.getId();
							}
						}
					}
				}
			}
		}
		
		// Create the nose generator which generates wave formes to use for the surface.
		Random random = new Random(world.getSeed());
		SimplexOctaveGenerator octave = new SimplexOctaveGenerator(random, 8);
		octave.setScale(1 / 48.0);
		
		// Generate the ceiling and the grass land
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				/*int height = getHeight(world, chunkx + x * 0.0625, chunkz + z * 0.0625, 2) + 30+(7*6) + 7;*/
				double height = octave.noise(x + chunkx * 16, z + chunkz * 16, 0.5, 0.5) * 4 + 9;
				
				result[xyzToByte(x, 30+(7*6), z)] = (byte) Material.COBBLESTONE.getId();
				for(int y = 30+(7*6)+1; y < 30+(7*6)+4; y++)
					result[xyzToByte(x, y, z)] = (byte) Material.STONE.getId();
				
				// Get the current biome
				Biome biome = world.getBiome((chunkx*16) + x, (chunkz*16) + z);
				
				if(biome.equals(Biome.DESERT) || biome.equals(Biome.DESERT_HILLS)) {
					for(int y = 30+(7*6)+4; y < 30+(7*6)+2+height; y++)
						result[xyzToByte(x, y, z)] = (byte) Material.SAND.getId();
					
				} else if(biome.equals(Biome.MUSHROOM_ISLAND) || biome.equals(Biome.MUSHROOM_ISLAND)){
					for(int y = 30+(7*6)+4; y < 30+(7*6)+2+height; y++)
						result[xyzToByte(x, y, z)] = (byte) Material.DIRT.getId();
					result[xyzToByte(x, (int) (30+(7*6)+2+height), z)] = (byte) Material.MYCEL.getId();
					
				} else {
					for(int y = 30+(7*6)+4; y < 30+(7*6)+2+height; y++)
						result[xyzToByte(x, y, z)] = (byte) Material.DIRT.getId();
					result[xyzToByte(x, (int) (30+(7*6)+2+height), z)] = (byte) Material.GRASS.getId();
				}
			}
		}	
		
		return result;
	}
}