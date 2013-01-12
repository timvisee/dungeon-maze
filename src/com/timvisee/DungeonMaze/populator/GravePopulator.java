package com.timvisee.DungeonMaze.populator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;

import com.timvisee.DungeonMaze.DungeonMaze;

public class GravePopulator extends BlockPopulator {
	public static final int CHANCE_OF_GRAVE = 5; // Promile
	
	private DungeonMaze plugin = new DungeonMaze();

	@Override
	public void populate(World world, Random random, Chunk source) {
		if(!DungeonMaze.isConstantChunk(world.getName(), source)) {
			// The layers for each 4 rooms in the variable y
			for (int y=30; y < 30+(7*6); y+=6) {
				
				// The 4 rooms on each layer saved in the variables x and z
				for (int x=0; x < 16; x+=8) {
					for (int z=0; z < 16; z+=8) {
						
						if(!DungeonMaze.isConstantRoom(world.getName(), source, x, y, z)) {
							if (random.nextInt(1000) < CHANCE_OF_GRAVE) {
								
								int graveX = x + random.nextInt(6 - 2) + 1 + 2; // +2 because the grave is 3 long (so you also need to put the random on 4)
								int graveY = y;
								int graveZ = z + random.nextInt(6) + 1;
								
								// Get the floor location 
								int yfloor = y;
								Block roomBottomBlock = source.getBlock(graveX, y, graveZ);
								int typeId = roomBottomBlock.getTypeId();
								if(!(typeId==4 || typeId==48 || typeId==87 || typeId==88)) {  // x and z +2 so that you aren't inside a wall!							
									yfloor++;
								}
								graveY = yfloor + 1;
								
								// The grave
								source.getBlock(graveX, graveY, graveZ).setTypeId(43);
								source.getBlock(graveX - 1, graveY, graveZ).setTypeId(44);
								source.getBlock(graveX - 2, graveY, graveZ).setTypeId(44);
								
								// Put a sign on a grave and write some text on it
								source.getBlock(graveX, graveY + 1, graveZ).setTypeId(63);
								source.getBlock(graveX, graveY + 1, graveZ).setData((byte) 4);
								//addGraveTextToSign((Sign) source.getBlock(graveX, graveY + 1, graveX), random);
							}
						}
					}
				}
			}
		}
	}
	
	// Code to add text to a sign
	@SuppressWarnings("null")
	public void addGraveTextToSign(Sign sign, Random random) {
		if (random.nextInt(100) < 5) {
			changeSignLine(sign, 1, "-404-");
			changeSignLine(sign, 2, "");
			changeSignLine(sign, 3, "No name");
			changeSignLine(sign, 4, "found");
		} else {
			String[] graveUsernames = {
					"timvisee",
					"Metonymia",
					"Notch",
					"Jeb"
					};
			String[][] graveText = {
					{"Rest In","Pieces"},
					{"Ate raw", "porkchop"},
					{"Tried the","Nether"},
					{"Took a bath in","a lake of lava"},
					{"CREEPER!",""},
					{"Shot an arrow","straight up"},
					{"Found a hole","in the bedrock"},
					{"Gravel fallen","on his head"},
					{"Tried the","/kill command"},
					{"Flew with a pig","of a mountain"},
					{"Lava ended","his life"},
					{"Used TNT in the","wrong way"},
					{"Found a","flooded room"},
					{"Found a","creeper spawner"},
					{"Found a","Boss Room"},
					{"Joined the","bad side!"}
					};
			
			String graveUsername = "Notch";
			String[] selectedText = null;
			int selectedGraveTextIndex = random.nextInt(graveText.length);
			selectedText[0] = graveText[selectedGraveTextIndex][0];
			selectedText[1] = graveText[selectedGraveTextIndex][1];
			
			if(plugin.isAnyPlayerOnline() == false) {
				graveUsername = graveUsernames[random.nextInt(graveUsernames.length)];
			} else {
				Player[] onlinePlayers = plugin.countOnlinePlayers();
				graveUsername = onlinePlayers[random.nextInt(onlinePlayers.length)].getName();
			}
			
			changeSignLine(sign, 1, graveUsername);
			changeSignLine(sign, 2, "");
			changeSignLine(sign, 3, selectedText[0]);
			changeSignLine(sign, 4, selectedText[1]);
		}	
	}
	
	// Code to change a line on a sign
	public void changeSignLine(Sign sign, int line, String text) {
		if(sign != null) {
			if(line >= 1 && line <= 4) {
				if(text != null) {
					sign.setLine(line - 1, text);
					sign.update();
				}
			}
		}
	}
}