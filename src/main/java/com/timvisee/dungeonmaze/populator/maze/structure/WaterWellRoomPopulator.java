package com.timvisee.dungeonmaze.populator.maze.structure;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.util.MaterialUtils;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

public class WaterWellRoomPopulator extends MazeRoomBlockPopulator {

    /**
     * General populator constants.
     */
    private static final int LAYER_MIN = 3;
    private static final int LAYER_MAX = 7;
    private static final float ROOM_CHANCE = .002f;

    @Override
    public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final World world = args.getWorld();
        final Chunk chunk = args.getSourceChunk();
        final int x = args.getRoomChunkX();
        final int y = args.getChunkY();
        final int yFloor = args.getFloorY();
        final int z = args.getRoomChunkZ();

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, y, z);

        // Floor
        for(int x2 = x; x2 <= x + 7; x2 += 1)
            for(int z2 = z; z2 <= z + 7; z2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, yFloor, z2), Material.STONE);

        // Floor (cobblestone underneath the stone floor)
        for(int x2 = x; x2 <= x + 7; x2 += 1)
            for(int z2 = z; z2 <= z + 7; z2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, yFloor - 1, z2), Material.COBBLESTONE);

        // Well
        for(int x2 = x + 2; x2 <= x + 4; x2 += 1)
            for(int z2 = z + 2; z2 <= z + 4; z2 += 1)
                setGeneratedBlock(chunk.getBlock(x2, yFloor + 1, z2), Material.STONE_BRICKS);

        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 1, z + 3), Material.WATER);

        // Poles
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 2, z + 2), Material.OAK_FENCE);
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 2, z + 4), Material.OAK_FENCE);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 2, z + 2), Material.OAK_FENCE);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 2, z + 4), Material.OAK_FENCE);

        // Roof
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 3, z + 2), Material.OAK_SLAB);
        MaterialUtils.setStairs(chunk.getBlock(x + 2, yFloor + 3, z + 3), Material.OAK_STAIRS, BlockFace.EAST);
        setGeneratedBlock(chunk.getBlock(x + 2, yFloor + 3, z + 4), Material.OAK_SLAB);
        MaterialUtils.setStairs(chunk.getBlock(x + 3, yFloor + 3, z + 2), Material.OAK_STAIRS, BlockFace.SOUTH);
        setGeneratedBlock(chunk.getBlock(x + 3, yFloor + 3, z + 3), Material.GLOWSTONE);
        MaterialUtils.setStairs(chunk.getBlock(x + 3, yFloor + 3, z + 4), Material.OAK_STAIRS, BlockFace.NORTH);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 3, z + 2), Material.OAK_SLAB);
        MaterialUtils.setStairs(chunk.getBlock(x + 4, yFloor + 3, z + 3), Material.OAK_STAIRS, BlockFace.WEST);
        setGeneratedBlock(chunk.getBlock(x + 4, yFloor + 3, z + 4), Material.OAK_SLAB);
    }

    @Override
    public float getRoomChance() {
        return ROOM_CHANCE;
    }

    /**
     * Get the minimum layer
     *
     * @return Minimum layer
     */
    @Override
    public int getMinimumLayer() {
        return LAYER_MIN;
    }

    /**
     * Get the maximum layer
     *
     * @return Maximum layer
     */
    @Override
    public int getMaximumLayer() {
        return LAYER_MAX;
    }
}
