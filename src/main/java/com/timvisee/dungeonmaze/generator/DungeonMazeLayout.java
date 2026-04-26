package com.timvisee.dungeonmaze.generator;

import com.timvisee.dungeonmaze.world.dungeon.chunk.DungeonChunk;

import java.util.Random;

public final class DungeonMazeLayout {

    public static final int DUNGEON_MIN_Y = 30;
    public static final int ROOM_SIZE = 8;
    public static final int LAYER_HEIGHT = 6;
    public static final int LAYER_COUNT = 7;
    public static final int MIN_LAYER = 1;
    public static final int MAX_LAYER = LAYER_COUNT;
    public static final int ROOMS_PER_CHUNK_SIDE = DungeonChunk.CHUNK_SIZE / ROOM_SIZE;
    public static final int SURFACE_BASE_Y = DUNGEON_MIN_Y + (LAYER_COUNT * LAYER_HEIGHT);
    public static final int ROOM_DIVIDER_NONE = -1;

    private DungeonMazeLayout() { }

    public static int getLayerBaseY(int layer) {
        if(layer < MIN_LAYER || layer > MAX_LAYER)
            throw new IllegalArgumentException("Invalid dungeon layer: " + layer);

        return DUNGEON_MIN_Y + ((layer - 1) * LAYER_HEIGHT);
    }

    public static int getDungeonLevel(int blockY) {
        if(blockY < DUNGEON_MIN_Y || blockY >= SURFACE_BASE_Y)
            return 0;

        return ((blockY - DUNGEON_MIN_Y) / LAYER_HEIGHT) + 1;
    }

    public static int pickRoomDividerCoordinate(Random random, int roomStart) {
        final int divider = (random.nextInt(3) - 1) * (roomStart + ROOM_SIZE - 1);
        if(divider < roomStart || divider > roomStart + ROOM_SIZE - 1)
            return ROOM_DIVIDER_NONE;

        return divider;
    }

    public static boolean isRoomCornerOrDivider(int roomStartX, int roomStartZ, int blockX, int blockZ, int dividerX, int dividerZ) {
        return isRoomCorner(roomStartX, roomStartZ, blockX, blockZ) ||
                blockX == dividerX ||
                blockZ == dividerZ;
    }

    private static boolean isRoomCorner(int roomStartX, int roomStartZ, int blockX, int blockZ) {
        return (blockX == roomStartX || blockX == roomStartX + ROOM_SIZE - 1) &&
                (blockZ == roomStartZ || blockZ == roomStartZ + ROOM_SIZE - 1);
    }
}
