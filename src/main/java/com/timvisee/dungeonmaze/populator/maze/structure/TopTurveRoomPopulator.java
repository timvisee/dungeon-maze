package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.Random;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.event.generation.GenerationSpawnerEvent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class TopTurveRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 5;
	private static final float ROOM_CHANCE = .002f;

    // TODO: Implement this feature!
	public static final double CHANCE_TOPTURVE_ADDITION_EACH_LEVEL = -0.167; /* to 2 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getY();
		final int yCeiling = args.getCeilingY();
		final int z = args.getRoomChunkZ();
			
        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

        // Hull
        setGeneratedBlock(chunk.getBlock(x + 3, yCeiling - 2, z + 3), Material.MOSSY_COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 3, yCeiling - 2, z + 4), Material.MOSSY_COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 4, yCeiling - 2, z + 3), Material.MOSSY_COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 4, yCeiling - 2, z + 4), Material.MOSSY_COBBLESTONE);
        setGeneratedBlock(chunk.getBlock(x + 2, yCeiling - 1, z + 3), Material.NETHERRACK);
        setGeneratedBlock(chunk.getBlock(x + 2, yCeiling - 1, z + 4), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 3, yCeiling - 1, z + 2), Material.GLASS);

        Block ore1 = chunk.getBlock(x + 3, yCeiling - 1, z + 3);
        switch(rand.nextInt(5)) {
        case 0:
            setGeneratedBlock(ore1, Material.GOLD_ORE);
            break;
        case 1:
            setGeneratedBlock(ore1, Material.IRON_ORE);
            break;
        case 2:
            setGeneratedBlock(ore1, Material.COAL_ORE);
            break;
        case 3:
            setGeneratedBlock(ore1, Material.LAPIS_ORE);
            break;
        case 4:
            setGeneratedBlock(ore1, Material.COAL_ORE); // Originally diamond, changed to coal because ore2 could be diamond too
            break;
        default:
            setGeneratedBlock(ore1, Material.COAL_ORE);
        }

        setGeneratedBlock(chunk.getBlock(x + 3, yCeiling - 1, z + 5), Material.NETHERRACK);
        setGeneratedBlock(chunk.getBlock(x + 4, yCeiling - 1, z + 2), Material.NETHERRACK);

        Block ore2 = chunk.getBlock(x + 4, yCeiling - 1, z + 4);
        switch(rand.nextInt(5)) {
        case 0:
            setGeneratedBlock(ore2, Material.GOLD_ORE);
            break;
        case 1:
            setGeneratedBlock(ore2, Material.IRON_ORE);
            break;
        case 2:
            setGeneratedBlock(ore2, Material.COAL_ORE);
            break;
        case 3:
            setGeneratedBlock(ore2, Material.LAPIS_ORE);
            break;
        case 4:
            setGeneratedBlock(ore2, Material.DIAMOND_ORE);
            break;
        default:
            setGeneratedBlock(ore2, Material.COAL_ORE);
        }

        setGeneratedBlock(chunk.getBlock(x + 4, yCeiling - 1, z + 5), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 5, yCeiling - 1, z + 3), Material.GLASS);
        setGeneratedBlock(chunk.getBlock(x + 5, yCeiling - 1, z + 4), Material.NETHERRACK);

        // Pig spawner
        if(Core.getConfigHandler().isMobSpawnerAllowed("Pig")) {
            // Get the spawner block
            Block spawnerBlock = chunk.getBlock(x + 3, yCeiling - 1, z + 4);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.PIG, GenerationSpawnerEvent.GenerationSpawnerCause.NORMAL, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the generation event
            event._apply();
        }

        // Skeleton spawner
        if(Core.getConfigHandler().isMobSpawnerAllowed("Skeleton")) {
            // Get the spawner block
            Block spawnerBlock = chunk.getBlock(x + 4, yCeiling - 1, z + 3);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.SKELETON, GenerationSpawnerEvent.GenerationSpawnerCause.NORMAL, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the generation event
            event._apply();
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
