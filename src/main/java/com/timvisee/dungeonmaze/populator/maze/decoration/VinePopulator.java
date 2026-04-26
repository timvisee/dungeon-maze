package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;

import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class VinePopulator extends MazeRoomBlockPopulator {

	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 7;

	private static final int CHANCE_VINE = 30;
	private static final double CHANCE_VINE_ADDITION_EACH_LEVEL = -2.5; /* to 15 */
	private static final int ITERATIONS = 5;
	private static final int CHANCE_CEILING_VINE = 5;
	private static final int ITERATIONS_CEILING_VINE = 5;

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final Chunk chunk = args.getSourceChunk();
        final Random rand = args.getRandom();
        final int x = args.getRoomChunkX();
        final int y = args.getChunkY();
        final int z = args.getRoomChunkZ();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			if (rand.nextInt(100) < CHANCE_VINE +(CHANCE_VINE_ADDITION_EACH_LEVEL *(y-30)/6)) {
				
				int vineX;
				int vineY;
				int vineZ;
				
				switch(rand.nextInt(4)) {
				case 0:
					vineX = 0;
					vineY = rand.nextInt(4) + 2;
					vineZ = rand.nextInt(6) + 1;

					if(chunk.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.STONE_BRICKS) {
						org.bukkit.block.Block vb0 = chunk.getBlock(x + vineX + 1, y + vineY, z + vineZ);
						setGeneratedBlock(vb0, Material.VINE);
						MultipleFacing vd0 = (MultipleFacing) vb0.getBlockData();
						vd0.setFace(BlockFace.WEST, true);
						setGeneratedBlockData(vb0, vd0);
					}

					break;
				case 1:
					vineX = 7;
					vineY = rand.nextInt(3) + 3;
					vineZ = rand.nextInt(6) + 1;

					if(chunk.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.STONE_BRICKS) {
						org.bukkit.block.Block vb1 = chunk.getBlock(x + vineX - 1, y + vineY, z + vineZ);
						setGeneratedBlock(vb1, Material.VINE);
						MultipleFacing vd1 = (MultipleFacing) vb1.getBlockData();
						vd1.setFace(BlockFace.EAST, true);
						setGeneratedBlockData(vb1, vd1);
					}

					break;
				case 2:
					vineX = rand.nextInt(6) + 1;
					vineY = rand.nextInt(3) + 3;
					vineZ = 0;

					if(chunk.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.STONE_BRICKS) {
						org.bukkit.block.Block vb2 = chunk.getBlock(x + vineX, y + vineY, z + vineZ + 1);
						setGeneratedBlock(vb2, Material.VINE);
						MultipleFacing vd2 = (MultipleFacing) vb2.getBlockData();
						vd2.setFace(BlockFace.NORTH, true);
						setGeneratedBlockData(vb2, vd2);
					}

					break;
				case 3:
					vineX = rand.nextInt(6) + 1;
					vineY = rand.nextInt(3) + 3;
					vineZ = 7;

					if(chunk.getBlock(x + vineX, y + vineY, z + vineZ).getType() == Material.STONE_BRICKS) {
						org.bukkit.block.Block vb3 = chunk.getBlock(x + vineX, y + vineY, z + vineZ - 1);
						setGeneratedBlock(vb3, Material.VINE);
						MultipleFacing vd3 = (MultipleFacing) vb3.getBlockData();
						vd3.setFace(BlockFace.SOUTH, true);
						setGeneratedBlockData(vb3, vd3);
					}

					break;
				default:
				}
			}
		}

		// Iterate
		for(int i = 0; i < ITERATIONS_CEILING_VINE; i++) {
			if (rand.nextInt(100) < CHANCE_CEILING_VINE) {
				int vineX = rand.nextInt(6) + 1;
				int vineY = args.getCeilingY() - 1;
				int vineZ = rand.nextInt(6) + 1;

				org.bukkit.block.Block cvb = chunk.getBlock(x + vineX, vineY, z + vineZ);
				setGeneratedBlock(cvb, Material.VINE);
				MultipleFacing cvd = (MultipleFacing) cvb.getBlockData();
				cvd.setFace(BlockFace.UP, true);
				setGeneratedBlockData(cvb, cvd);
			}
		}
	}

    @Override
    public float getRoomChance() {
        // TODO: Improve this!
        return 1.0f;
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