package com.timvisee.dungeonmaze.world.dungeon.chunk;

import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonRegionGrid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DungeonChunkTest {

    @Test
    void chunkCoordinatesUseTheRegionZAxisForChunkZ() {
        final DungeonRegion region = new DungeonRegion(new DungeonRegionGrid(null), 2, 3);
        final DungeonChunk chunk = new DungeonChunk(region, 4, 5);

        assertEquals(68, chunk.getChunkX());
        assertEquals(101, chunk.getChunkZ());
        assertEquals((2 * DungeonRegion.REGION_SIZE * DungeonChunk.CHUNK_SIZE) + (4 * DungeonChunk.CHUNK_SIZE), chunk.getWorldX());
        assertEquals((3 * DungeonRegion.REGION_SIZE * DungeonChunk.CHUNK_SIZE) + (5 * DungeonChunk.CHUNK_SIZE), chunk.getWorldZ());
    }
}
