package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import org.bukkit.entity.Player;

public class SkullPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 4;
	private static final int ROOM_ITERATIONS = 5;
	private static final float ROOM_ITERATIONS_CHANCE = .001f;

    /** Populator constants. */
	private static final float POLE_GRAVE_CHANCE = .8f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final Chunk c = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int z = args.getRoomChunkZ();

        // Determine the position of the skull
        int skullX = x + rand.nextInt(6) + 1;
        int skullY = args.getFloorY() + 1;
        int skullZ = z + rand.nextInt(6) + 1;

        // Decide whether it's a grave on a pole
        boolean withPole = false;
        if(rand.nextFloat() < POLE_GRAVE_CHANCE)
            withPole = true;

        // Move the skull one up if it's on a pole
        if(withPole)
            skullY++;

        // Define the blocks
        Block poleBlock = c.getBlock(skullX, skullY - 1, skullZ);
        Block skullBlock = c.getBlock(skullX, skullY, skullZ);

        if(withPole)
            setGeneratedBlock(poleBlock, Material.OAK_FENCE);

        // Get and create the skull block
        setGeneratedBlock(skullBlock, getRandomSkullMaterial(rand));

		try {
			Skull skull = (Skull) skullBlock.getState();

			// Get a random online owner for player heads when available
			final Player skullOwner = getRandomOwner(rand);

			// Set the rotation and owner
            final BlockData blockData = skullBlock.getBlockData();
            if(blockData instanceof Rotatable) {
                ((Rotatable) blockData).setRotation(getRandomSkullRotation(rand));
                setGeneratedBlockData(skullBlock, blockData);
            }
            if(skullOwner != null && skullBlock.getType() == Material.PLAYER_HEAD)
                skull.setOwningPlayer(skullOwner);

			// Force update the skull
			skull.update(true, false);

		} catch(Exception ex) {
			// Show a proper error message
			Core.getLogger().error("Failed to configure player skull");
		}
	}
	
	private Player getRandomOwner(Random rand) {
		if(Bukkit.getOnlinePlayers().size() == 0)
			return null;

		List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
		return onlinePlayers.get(rand.nextInt(onlinePlayers.size()));
	}

	private Material getRandomSkullMaterial(Random rand) {
		Material[] skulls = {
			Material.SKELETON_SKULL,
			Material.ZOMBIE_HEAD,
			Material.CREEPER_HEAD,
			Material.WITHER_SKELETON_SKULL,
			Material.PLAYER_HEAD
		};
		return skulls[rand.nextInt(skulls.length)];
	}

	private BlockFace getRandomSkullRotation(Random rand) {
		BlockFace[] faces = new BlockFace[]{
				BlockFace.EAST,
				BlockFace.EAST_NORTH_EAST,
				BlockFace.EAST_SOUTH_EAST,
				BlockFace.NORTH,
				BlockFace.NORTH_EAST,
				BlockFace.NORTH_NORTH_EAST,
				BlockFace.NORTH_NORTH_WEST,
				BlockFace.NORTH_WEST,
				BlockFace.SOUTH,
				BlockFace.SOUTH_EAST,
				BlockFace.SOUTH_SOUTH_EAST,
				BlockFace.SOUTH_SOUTH_WEST,
				BlockFace.SOUTH_WEST,
				BlockFace.WEST,
				BlockFace.WEST_NORTH_WEST,
				BlockFace.WEST_SOUTH_WEST
		};
		return faces[rand.nextInt(faces.length)];
	}

    @Override
    public int getRoomIterations() {
        return ROOM_ITERATIONS;
    }

    @Override
    public float getRoomIterationsChance() {
        return ROOM_ITERATIONS_CHANCE;
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
