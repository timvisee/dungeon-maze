package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class MassiveRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .005f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int yCeiling = args.getCeilingY();
		final int z = args.getRoomChunkZ();

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

        // Walls
        for(int x2 = x; x2 <= x + 7; x2 += 1) {
            for(int y2 = yFloor + 1; y2 <= yCeiling - 1; y2 += 1) {
                setGeneratedBlock(chunk.getBlock(x2, y2, z), Material.STONE_BRICKS);
                setGeneratedBlock(chunk.getBlock(x2, y2, z + 7), Material.STONE_BRICKS);
            }
        }
        for(int z2 = z; z2 <= z + 7; z2 += 1) {
            for(int y2 = yFloor + 1; y2 <= yCeiling - 1; y2 += 1) {
                setGeneratedBlock(chunk.getBlock(x, y2, z2), Material.STONE_BRICKS);
                setGeneratedBlock(chunk.getBlock(x + 7, y2, z2), Material.STONE_BRICKS);
            }
        }

        // Make the room massive with stone
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int y2 = yFloor + 1; y2 <= yCeiling - 1; y2 += 1)
                for(int z2 = z + 1; z2 <= z + 6; z2 += 1)
                    setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.STONE);

        // Fill the massive room with some ores!
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1) {
            for(int y2 = yFloor + 1; y2 <= yCeiling - 1; y2 += 1) {
                for(int z2 = z + 1; z2 <= z + 6; z2 += 1) {
                    if(rand.nextInt(100) < 2) {
                        switch(rand.nextInt(8)) {
                        case 0:
                            setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.GOLD_ORE);
                            break;
                        case 1:
                            setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.IRON_ORE);
                            break;
                        case 2:
                            setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.COAL_ORE);
                            break;
                        case 3:
                            setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.LAPIS_ORE);
                            break;
                        case 4:
                            setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.DIAMOND_ORE);
                            break;
                        case 5:
                            setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.REDSTONE_ORE);
                            break;
                        case 6:
                            setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.EMERALD_ORE);
                            break;
                        case 7:
                            setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.CLAY);
                            break;
                        case 8:
                            setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.COAL_ORE);
                            break;
                        default:
                            setGeneratedBlock(chunk.getBlock(x2, y2, z2), Material.COAL_ORE);
                        }
                    }
                }
            }
        }
	}

    @Override
    public float getRoomChance() {
        return ROOM_CHANCE;
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
}