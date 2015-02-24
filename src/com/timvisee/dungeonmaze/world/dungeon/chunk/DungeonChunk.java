package com.timvisee.dungeonmaze.world.dungeon.chunk;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonChunkGrid;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DungeonChunk {

    /** Defines the world the chunks is in. */
    private World world;
    /** Defines the X and Z coordinate of the chunk in the world. */
    private int x, z;

    /** Defines whether this chunk is a custom chunk. */
    private boolean customChunk = false;

    /** Defines the dungeon chunk data section name. */
    private final static String CONFIG_DUNGEON_CHUNK_SECTION = "dungeonChunk";

    /**
     * Constructor.
     *
     * @param world The world the chunks is in.
     * @param x The X coordinate of the chunk.
     * @param z The Z coordinate of the chunk.
     */
    public DungeonChunk(World world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    /**
     * Get the world the chunk is in.
     *
     * @return The world the chunk is in.
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Get the X coordinate of the chunk.
     *
     * @return The X coordinate of the chunk.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the Z coordinate of the chunk.
     *
     * @return The Z coordinate of the chunk.
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Check whether this chunk is at a specific position.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkZ The Z coordinate of the chunk.
     *
     * @return True if the chunk is at this position, false otherwise.
     */
    public boolean isAt(int chunkX, int chunkZ) {
        return this.x == chunkX && this.z == chunkZ;
    }

    /**
     * Check whether this chunk is a custom chunk.
     *
     * @return True if this chunk is a custom chunk, false if not.
     */
    public boolean isCustomChunk() {
        return this.customChunk;
    }

    /**
     * Set whether this chunk is a custom chunk.
     *
     * @param customChunk True if this chunk is a custom chunk.
     */
    public void setCustomChunk(boolean customChunk) {
        this.customChunk = customChunk;
    }

    /**
     * Get the chunk data file.
     *
     * @param dungeonChunkGrid The Dungeon Chunk Grid instance.
     *
     * @return The chunk data file.
     */
    public File getChunkDataFile(DungeonChunkGrid dungeonChunkGrid) {
        return dungeonChunkGrid.getChunkDataFile(this.x, this.z);
    }

    /**
     * Load a dungeon chunk from a file.
     *
     * @param world The world of the chunk.
     * @param file The file to load the dungeon chunk from.
     *
     * @return The loaded dungeon chunk, null on failure.
     */
    public static DungeonChunk load(World world, File file) {
        // Make sure the file exists
        if(!file.exists())
            return null;

        // Load the configuration
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        // Make sure the proper sections are available
        if(!config.isConfigurationSection(CONFIG_DUNGEON_CHUNK_SECTION) || !config.isSet("version"))
            return null;

        // Get the proper configuration section to load the dungeon chunk from, then load and return the actual chunk
        ConfigurationSection configurationSection = config.getConfigurationSection(CONFIG_DUNGEON_CHUNK_SECTION);
        return load(world, configurationSection);
    }

    /**
     * Load a dungeon chunk from a configuration section.
     *
     * @param world The world of the chunk.
     * @param config The configuration section the dungeon chunk is in.
     *
     * @return The dungeon chunk.
     */
    public static DungeonChunk load(World world, ConfigurationSection config) {
        // Make sure the configuration contains the proper values
        if(!config.isSet("position.x") || !config.isSet("position.z"))
            return null;

        // Get the position
        int x = config.getInt("position.x", 0);
        int z = config.getInt("position.z", 0);

        // Construct a new dungeon chunk
        DungeonChunk dungeonChunk = new DungeonChunk(world, x, z);

        // Load whether this chunk is a custom chunk
        dungeonChunk.setCustomChunk(config.getBoolean("customChunk.isCustom", false));

        // Return the instance
        return dungeonChunk;
    }

    /**
     * Save the dungeon chunk in a file.
     *
     * @param file The file to save the dungeon chunk into.
     *
     * @return True on success, false on failure.
     */
    public boolean save(File file) {
        // Save the configuration section
        YamlConfiguration config = new YamlConfiguration();

        // Create the dungeon chunk section and store the chunk
        ConfigurationSection chunkSection = config.createSection(CONFIG_DUNGEON_CHUNK_SECTION);
        save(config.getConfigurationSection(CONFIG_DUNGEON_CHUNK_SECTION));

        // Save whether this is a custom chunk
        chunkSection.set("customChunk.isCustom", this.customChunk);

        // Append the current Dungeon Maze version to the file
        config.set("version.name", DungeonMaze.getVersionName());
        config.set("version.code", DungeonMaze.getVersionCode());

        // Save the file
        try {
            config.save(file);
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Save the dungeon chunk into a configuration section.
     *
     * @param config The configuration section to save the dungeon chunk to.
     */
    public void save(ConfigurationSection config) {
        // Store the chunk position
        config.set("position.x", this.x);
        config.set("position.z", this.z);
    }
}
