package com.timvisee.dungeonmaze.generator;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.generator.chunk.BukkitChunk;
import com.timvisee.dungeonmaze.generator.chunk.ShortChunk;
import com.timvisee.dungeonmaze.populator.maze.decoration.*;
import com.timvisee.dungeonmaze.populator.maze.spawner.*;
import com.timvisee.dungeonmaze.populator.maze.structure.*;
import com.timvisee.dungeonmaze.util.MaterialUtils;
import com.timvisee.dungeonmaze.populator.surface.plants.FlowerPopulator;
import com.timvisee.dungeonmaze.populator.surface.plants.TallGrassPopulator;
import com.timvisee.dungeonmaze.populator.surface.plants.TreePopulator;
import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonRegionGrid;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonRegionGridManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DungeonMazeChunkGenerator extends ChunkGenerator {

    private static final int INITIAL_STONE_FILL_TOP_Y = DungeonMazeLayout.DUNGEON_MIN_Y + 3;

    /**
     * Constructor.
     */
    public DungeonMazeChunkGenerator() { }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        //noinspection unchecked
        return (List<BlockPopulator>) (List<?>) Arrays.asList(
                new BrokenWallsPopulator(),
                new SpawnChamberPopulator(),
                new OresInGroundPopulator(),
                new OasisChunkPopulator(),
                new BossRoomHardPopulator(),
                new BossRoomInsanePopulator(),
                new LibraryRoomPopulator(),
                new AbandonedDefenceCastleRoomPopulator(),
//                new ArmoryRoomPopulator(),
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
                new CoalOrePopulator(),
                new StrutPopulator(),
                new StairsPopulator(),
                new LadderPopulator(),
                new PoolPopulator(),
                new LavaOutOfWallPopulator(),
                new WaterOutOfWallPopulator(),
                new RailPopulator(),
                new SkullPopulator(),
                new TopTurveRoomPopulator(),
                new CreeperSpawnerRoomPopulator(),
                new GravePopulator(),
                new ChestPopulator(),
                new SpawnerPopulator(),
                new SandPopulator(),
                new GravelPopulator(),
                new RuinsPopulator(),
                new IronBarPopulator(),
                new SlabPopulator(),
                new TorchPopulator(),
                new LanternPopulator(),
                new PumpkinPopulator(),
                new SilverfishBlockPopulator(),
                new WebPopulator(),
                new VinePopulator(),
                new CobblestonePopulator(),
                new MushroomPopulator()
                //new ExplosionPopulator() // Temporarily disabled for performance issues
        );
    }

    @Override
    public ChunkData generateChunkData(World world, Random randSrc, int chunkX, int chunkZ, BiomeGrid biomes) {
        ensureDungeonChunkMetadata(world, chunkX, chunkZ);

        // The base layout must not depend on the metadata services being ready yet, because Bukkit can request
        // the first chunks before the plugin core has fully initialized.
        final BukkitChunk bukkitChunk = new BukkitChunk(world, chunkX, chunkZ);

        // This will set the whole floor to stone (the floor of each chunk)
        bukkitChunk.setLayers(0, INITIAL_STONE_FILL_TOP_Y, Material.STONE);

        for(int layer = DungeonMazeLayout.MIN_LAYER; layer <= DungeonMazeLayout.MAX_LAYER; layer++) {
            final int y = DungeonMazeLayout.getLayerBaseY(layer);
            for(int roomX = 0; roomX < DungeonChunk.CHUNK_SIZE; roomX += DungeonMazeLayout.ROOM_SIZE) {
                for(int roomZ = 0; roomZ < DungeonChunk.CHUNK_SIZE; roomZ += DungeonMazeLayout.ROOM_SIZE) {
                    generateBaseRoom(bukkitChunk, randSrc, y, roomX, roomZ);
                }
            }
        }

        // Create the nose generator which generates wave forms to use for the surface.
        Random rand = new Random(world.getSeed());
        SimplexOctaveGenerator octave = new SimplexOctaveGenerator(rand, 8);
        octave.setScale(1.0 / 48.0);

        // Generate the ceiling and the grass land
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                double height = octave.noise(x + chunkX * 16, z + chunkZ * 16, 0.5, 0.5) * 4 + 9;

                bukkitChunk.setBlock(x, DungeonMazeLayout.SURFACE_BASE_Y, z, Material.COBBLESTONE);
                for(int y = DungeonMazeLayout.SURFACE_BASE_Y + 1; y < DungeonMazeLayout.SURFACE_BASE_Y + 4; y++)
                    bukkitChunk.setBlock(x, y, z, Material.STONE);

                // Get the current biome
                Biome biome = getBiome(world, biomes, chunkX, chunkZ, x, z);

                if(isDesertBiome(biome)) {
                    for(int y = DungeonMazeLayout.SURFACE_BASE_Y + 4; y < DungeonMazeLayout.SURFACE_BASE_Y + 2 + height; y++)
                        bukkitChunk.setBlock(x, y, z, Material.SAND);

                } else if(isMushroomBiome(biome)) {
                    for(int y = DungeonMazeLayout.SURFACE_BASE_Y + 4; y < DungeonMazeLayout.SURFACE_BASE_Y + 2 + height; y++)
                        bukkitChunk.setBlock(x, y, z, Material.DIRT);
                    bukkitChunk.setBlock(x, (int) (DungeonMazeLayout.SURFACE_BASE_Y + 2 + height), z, Material.MYCELIUM);

                } else {
                    for(int y = DungeonMazeLayout.SURFACE_BASE_Y + 4; y < DungeonMazeLayout.SURFACE_BASE_Y + 2 + height; y++)
                        bukkitChunk.setBlock(x, y, z, Material.DIRT);
                    bukkitChunk.setBlock(x, (int) (DungeonMazeLayout.SURFACE_BASE_Y + 2 + height), z, Material.GRASS_BLOCK);
                }
            }
        }

        // Set the bottom layer to bedrock
        bukkitChunk.setLayer(0, Material.BEDROCK);

        // Return the chunk data
        return toChunkData(world, bukkitChunk);
    }

    private void ensureDungeonChunkMetadata(World world, int chunkX, int chunkZ) {
        try {
            final DungeonRegionGridManager chunkGridManager = Core.getDungeonRegionGridManager();
            if(chunkGridManager == null)
                return;

            final DungeonRegionGrid dungeonRegionGrid = chunkGridManager.getOrCreateRegionGrid(world);
            if(dungeonRegionGrid == null)
                return;

            dungeonRegionGrid.getOrCreateChunk(chunkX, chunkZ);
        } catch(Exception ex) {
            Core.getLogger().error("Unable to prime Dungeon Maze chunk metadata (" + world.getName() + ", " + chunkX + ", " + chunkZ + ")");
            ex.printStackTrace();
        }
    }

    private ChunkData toChunkData(World world, BukkitChunk bukkitChunk) {
        final ChunkData chunkData = createChunkData(world);
        final Material[][] sections = bukkitChunk.getChunkData();

        for(int sectionY = 0; sectionY < sections.length; sectionY++) {
            final Material[] section = sections[sectionY];
            if(section == null)
                continue;

            for(int y = 0; y < 16; y++) {
                for(int z = 0; z < 16; z++) {
                    for(int x = 0; x < 16; x++) {
                        final Material material = section[(y << 8) | (z << 4) | x];
                        if(MaterialUtils.isEmpty(material))
                            continue;

                        if(material.isBlock() && material != Material.AIR)
                            chunkData.setBlock(x, (sectionY << 4) + y, z, material);
                    }
                }
            }
        }

        return chunkData;
    }

    private boolean isDesertBiome(Biome biome) {
        return biome.name().contains("DESERT");
    }

    private boolean isMushroomBiome(Biome biome) {
        return biome.name().contains("MUSHROOM");
    }

    private Biome getBiome(World world, BiomeGrid biomes, int chunkX, int chunkZ, int x, int z) {
        final int surfaceY = DungeonMazeLayout.SURFACE_BASE_Y + 4;
        if(biomes != null)
            return biomes.getBiome(x, surfaceY, z);

        return world.getBiome((chunkX * 16) + x, surfaceY, (chunkZ * 16) + z);
    }

    static void generateBaseRoom(ShortChunk chunk, Random random, int layerBaseY, int roomStartX, int roomStartZ) {
        final int floorOffset = random.nextInt(2);
        final int floorY = layerBaseY + floorOffset;
        final int dividerX = DungeonMazeLayout.pickRoomDividerCoordinate(random, roomStartX);
        final int dividerZ = DungeonMazeLayout.pickRoomDividerCoordinate(random, roomStartZ);

        for(int y = floorY; y < layerBaseY + DungeonMazeLayout.ROOM_SIZE; y++) {
            for(int x = roomStartX; x < roomStartX + DungeonMazeLayout.ROOM_SIZE; x++) {
                for(int z = roomStartZ; z < roomStartZ + DungeonMazeLayout.ROOM_SIZE; z++) {
                    if(y == floorY) {
                        chunk.setBlock(x, y, z, Material.COBBLESTONE);
                        continue;
                    }

                    if(DungeonMazeLayout.isRoomCornerOrDivider(roomStartX, roomStartZ, x, z, dividerX, dividerZ))
                        chunk.setBlock(x, y, z, Material.STONE_BRICKS);
                    else
                        chunk.clearBlock(x, y, z);
                }
            }
        }
    }

    /**
     * Define whether monsters can spawn in the world. This overwrites the server settings.
     *
     * @param world The world.
     * @param x     The X coordinate of the monster.
     * @param z     The Z coordinate of the monster.
     *
     * @return True if the monster may spawn, false otherwise.
     */
    @Override
    public boolean canSpawn(World world, int x, int z) {
        // Get the world name
        String worldName = world.getName();

        // Return the default value if the world isn't a Dungeon Maze world
        if(!Core.getWorldManager().isDungeonMazeWorld(worldName))
            return true;

        // Return whether monsters can spawn in this Dungeon Maze world
        // TODO: Add some fancy code here that determines whether a monster can spawn!
        return true;
    }

    /**
     * Get the spawn location of a Dungeon Maze world.
     *
     * @param world  The world.
     * @param rand The rand seed.
     *
     * @return The spawn location for the player.
     */
    @Override
    public Location getFixedSpawnLocation(World world, Random rand) {
        // Get the world name
        String worldName = world.getName();

        // Return the world's default spawn location if it isn't a Dungeon Maze world
        if(!Core.getWorldManager().isDungeonMazeWorld(worldName))
            return world.getSpawnLocation();

        // Return the spawn location of the Dungeon Maze world
        return new Location(world, 4, 68, 4);
    }
}
