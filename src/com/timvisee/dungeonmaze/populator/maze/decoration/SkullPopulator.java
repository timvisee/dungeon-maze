package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import org.bukkit.entity.Player;

public class SkullPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	public static final int LAYER_MIN = 1;
	public static final int LAYER_MAX = 4;
	public static final int ROOM_ITERATIONS = 5;
	public static final float ROOM_ITERATIONS_CHANCE = .001f;

    /** Populator constants. */
	public static final float POLE_GRAVE_CHANCE = .8f;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final Chunk c = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getChunkX();
		final int z = args.getChunkZ();

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

        Block poleBlock = c.getBlock(skullX, skullY - 1, skullZ);
        Block skullBlock = c.getBlock(skullX, skullY, skullZ);

        if(withPole)
            poleBlock.setType(Material.FENCE);

        skullBlock.setType(Material.SKULL);
        skullBlock.setData((byte) 1);

        BlockState bs = skullBlock.getState();
        Skull s = (Skull) bs;

        s.setSkullType(getRandomSkullType(rand));
        s.setRotation(getRandomSkullRotation(rand));
        s.setOwner(getRandomOwner(rand));
        s.update(true, false);
	}
	
	private String getRandomOwner(Random rand) {
		String name = "";
		if(Bukkit.getOnlinePlayers().size() > 0) {
			List<Player> onlinePlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());
			name = onlinePlayers.get(rand.nextInt(onlinePlayers.size())).getName();
		}
		return name;
	}

	private SkullType getRandomSkullType(Random rand) {
		SkullType[] types = SkullType.values();
		return types[rand.nextInt(types.length)];
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
