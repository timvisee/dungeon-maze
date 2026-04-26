package com.timvisee.dungeonmaze.generator;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.timvisee.dungeonmaze.generator.chunk.ShortChunk;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.decoration.BrokenWallsPopulator;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

class DungeonMazeLayoutTest {

    @Test
    void getLayerBaseYReturnsExpectedHeights() {
        assertEquals(30, DungeonMazeLayout.getLayerBaseY(1));
        assertEquals(36, DungeonMazeLayout.getLayerBaseY(2));
        assertEquals(42, DungeonMazeLayout.getLayerBaseY(3));
        assertEquals(48, DungeonMazeLayout.getLayerBaseY(4));
        assertEquals(54, DungeonMazeLayout.getLayerBaseY(5));
        assertEquals(60, DungeonMazeLayout.getLayerBaseY(6));
        assertEquals(66, DungeonMazeLayout.getLayerBaseY(7));
        assertThrows(IllegalArgumentException.class, () -> DungeonMazeLayout.getLayerBaseY(0));
        assertThrows(IllegalArgumentException.class, () -> DungeonMazeLayout.getLayerBaseY(8));
    }

    @Test
    void getDungeonLevelMapsAllSevenLayers() {
        assertEquals(0, DungeonMazeLayout.getDungeonLevel(29));
        assertEquals(1, DungeonMazeLayout.getDungeonLevel(30));
        assertEquals(1, DungeonMazeLayout.getDungeonLevel(35));
        assertEquals(2, DungeonMazeLayout.getDungeonLevel(36));
        assertEquals(4, DungeonMazeLayout.getDungeonLevel(50));
        assertEquals(7, DungeonMazeLayout.getDungeonLevel(66));
        assertEquals(7, DungeonMazeLayout.getDungeonLevel(71));
        assertEquals(0, DungeonMazeLayout.getDungeonLevel(DungeonMazeLayout.SURFACE_BASE_Y));
    }

    @Test
    void roomDividerCoordinateStaysInsideTheRoom() {
        final Random random = new Random(12345L);

        for(int i = 0; i < 100; i++) {
            assertDividerUsesLegacyRoomGeometry(0, DungeonMazeLayout.pickRoomDividerCoordinate(random, 0));
            assertDividerUsesLegacyRoomGeometry(8, DungeonMazeLayout.pickRoomDividerCoordinate(random, 8));
        }
    }

    @Test
    void generateBaseRoomLeavesRoomBoundariesOpenOutsideCorners() {
        final ShortChunk chunk = new ShortChunk(createTestWorld(), 0, 0);

        DungeonMazeChunkGenerator.generateBaseRoom(chunk, new SequenceRandom(0, 0, 0), DungeonMazeLayout.DUNGEON_MIN_Y, 0, 8);

        final int floorY = DungeonMazeLayout.DUNGEON_MIN_Y;
        assertEquals(Material.COBBLESTONE, chunk.getBlock(0, floorY, 8));
        assertEquals(Material.STONE_BRICKS, chunk.getBlock(0, floorY + 1, 8));
        assertEquals(Material.AIR, chunk.getBlock(1, floorY + 1, 8));
        assertEquals(Material.AIR, chunk.getBlock(0, floorY + 1, 9));
        assertEquals(Material.AIR, chunk.getBlock(4, floorY + 1, 12));
    }

    @Test
    void generateBaseRoomBuildsLegacyRoomGeometryForAnyRoomOrigin() {
        assertRoomGeometry(0L, 0, 0);
        assertRoomGeometry(1L, 8, 8);
    }

    @Test
    void brokenWallPopulatorUsesRoomAxesWithoutSwappingCoordinates() {
        final BlockGrid grid = new BlockGrid();
        final BrokenWallsPopulator populator = new BrokenWallsPopulator();
        final int roomX = 0;
        final int roomZ = 8;
        final int floorY = DungeonMazeLayout.DUNGEON_MIN_Y;

        grid.setType(roomX + 3, floorY + 2, roomZ, Material.STONE_BRICKS);
        grid.setType(roomX + 3, floorY + 3, roomZ, Material.STONE_BRICKS);
        grid.setType(roomX + 11, floorY + 1, roomZ, Material.STONE_BRICKS);

        populator.populateRoom(new MazeRoomBlockPopulatorArgs(
                createTestWorld(),
                new SequenceRandom(new int[] {1, 2}, new boolean[] {false, true}),
                grid.createChunkProxy(),
                null,
                1,
                roomX,
                floorY,
                roomZ,
                0,
                0
        ));

        assertEquals(Material.AIR, grid.getType(roomX + 3, floorY + 2, roomZ));
        assertEquals(Material.AIR, grid.getType(roomX + 3, floorY + 3, roomZ));
        assertEquals(Material.STONE_BRICKS, grid.getType(roomX + 11, floorY + 1, roomZ), "The populator must not carve on the transposed X axis");
    }

    private static void assertDividerUsesLegacyRoomGeometry(int roomStart, int divider) {
        final int roomEnd = roomStart + DungeonMazeLayout.ROOM_SIZE - 1;
        assertTrue(
                divider == DungeonMazeLayout.ROOM_DIVIDER_NONE || divider == roomStart || divider == roomEnd,
                "Divider " + divider + " must follow the legacy room edges for room starting at " + roomStart
        );
    }

    private static void assertRoomGeometry(long seed, int roomStartX, int roomStartZ) {
        final ShortChunk chunk = new ShortChunk(createTestWorld(), 0, 0);
        final Random expectedRandom = new Random(seed);
        final int floorOffset = expectedRandom.nextInt(2);
        final int dividerX = DungeonMazeLayout.pickRoomDividerCoordinate(expectedRandom, roomStartX);
        final int dividerZ = DungeonMazeLayout.pickRoomDividerCoordinate(expectedRandom, roomStartZ);
        final int layerBaseY = DungeonMazeLayout.DUNGEON_MIN_Y;
        final int floorY = layerBaseY + floorOffset;

        DungeonMazeChunkGenerator.generateBaseRoom(chunk, new Random(seed), layerBaseY, roomStartX, roomStartZ);

        for(int x = roomStartX; x < roomStartX + DungeonMazeLayout.ROOM_SIZE; x++) {
            for(int z = roomStartZ; z < roomStartZ + DungeonMazeLayout.ROOM_SIZE; z++)
                assertEquals(Material.COBBLESTONE, chunk.getBlock(x, floorY, z), "Unexpected floor block at " + x + "," + floorY + "," + z);
        }

        for(int y = floorY + 1; y < layerBaseY + DungeonMazeLayout.ROOM_SIZE; y++) {
            for(int x = roomStartX; x < roomStartX + DungeonMazeLayout.ROOM_SIZE; x++) {
                for(int z = roomStartZ; z < roomStartZ + DungeonMazeLayout.ROOM_SIZE; z++) {
                    final Material expected = DungeonMazeLayout.isRoomCornerOrDivider(roomStartX, roomStartZ, x, z, dividerX, dividerZ)
                            ? Material.STONE_BRICKS
                            : Material.AIR;
                    assertEquals(expected, chunk.getBlock(x, y, z), "Unexpected block at " + x + "," + y + "," + z);
                }
            }
        }
    }

    private static World createTestWorld() {
        return (World) Proxy.newProxyInstance(
                World.class.getClassLoader(),
                new Class<?>[] {World.class},
                (proxy, method, args) -> {
                    if(method.getName().equals("getMaxHeight"))
                        return 256;
                    if(method.getName().equals("hashCode"))
                        return System.identityHashCode(proxy);
                    if(method.getName().equals("equals"))
                        return proxy == args[0];
                    if(method.getName().equals("toString"))
                        return "TestWorld";

                    final Class<?> returnType = method.getReturnType();
                    if(returnType == boolean.class)
                        return false;
                    if(returnType == int.class)
                        return 0;
                    if(returnType == long.class)
                        return 0L;
                    if(returnType == double.class)
                        return 0.0d;
                    if(returnType == float.class)
                        return 0.0f;

                    return null;
                }
        );
    }

    private static final class SequenceRandom extends Random {
        private final int[] intValues;
        private final boolean[] booleanValues;
        private int intIndex = 0;
        private int booleanIndex = 0;

        private SequenceRandom(int... intValues) {
            this(intValues, new boolean[0]);
        }

        private SequenceRandom(boolean... booleanValues) {
            this(new int[0], booleanValues);
        }

        private SequenceRandom(int[] intValues, boolean[] booleanValues) {
            this.intValues = intValues;
            this.booleanValues = booleanValues;
        }

        @Override
        public int nextInt(int bound) {
            final int value = this.intValues[this.intIndex++];
            if(value < 0 || value >= bound)
                throw new AssertionError("Random sequence value " + value + " is outside bound " + bound);
            return value;
        }

        @Override
        public boolean nextBoolean() {
            return this.booleanValues[this.booleanIndex++];
        }
    }

    private static final class BlockGrid {
        private final java.util.Map<String, Material> types = new java.util.HashMap<>();

        private Chunk createChunkProxy() {
            return (Chunk) Proxy.newProxyInstance(
                    Chunk.class.getClassLoader(),
                    new Class<?>[] {Chunk.class},
                    (proxy, method, args) -> {
                        if(method.getName().equals("getBlock"))
                            return createBlockProxy((int) args[0], (int) args[1], (int) args[2]);
                        if(method.getName().equals("hashCode"))
                            return System.identityHashCode(proxy);
                        if(method.getName().equals("equals"))
                            return proxy == args[0];
                        if(method.getReturnType() == boolean.class)
                            return false;
                        if(method.getReturnType() == int.class)
                            return 0;
                        return null;
                    }
            );
        }

        private Block createBlockProxy(int x, int y, int z) {
            return (Block) Proxy.newProxyInstance(
                    Block.class.getClassLoader(),
                    new Class<?>[] {Block.class},
                    (proxy, method, args) -> {
                        if(method.getName().equals("getType"))
                            return getType(x, y, z);
                        if(method.getName().equals("setType")) {
                            setType(x, y, z, (Material) args[0]);
                            return null;
                        }
                        if(method.getName().equals("hashCode"))
                            return System.identityHashCode(proxy);
                        if(method.getName().equals("equals"))
                            return proxy == args[0];
                        if(method.getReturnType() == boolean.class)
                            return false;
                        if(method.getReturnType() == int.class)
                            return 0;
                        return null;
                    }
            );
        }

        private Material getType(int x, int y, int z) {
            return this.types.getOrDefault(key(x, y, z), Material.AIR);
        }

        private void setType(int x, int y, int z, Material material) {
            this.types.put(key(x, y, z), material);
        }

        private String key(int x, int y, int z) {
            return x + ":" + y + ":" + z;
        }
    }
}
