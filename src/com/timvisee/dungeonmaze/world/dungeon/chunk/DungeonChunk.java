package com.timvisee.dungeonmaze.world.dungeon.chunk;

import com.timvisee.dungeonmaze.DungeonMaze;
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

    /** Defines the dungeon chunk data section name. */
    private final static String CONFIG_DUNGEON_CHUNK_SECTION = "dungeonChunk";
    /** Defines the name of the dungeon chunk data file. */
    private final static String CHUNK_DATA_FILE = "data.dmc";

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
     * Get the chunk data file.
     *
     * @param chunkGridDataDirectory The chunk grid data directory.
     *
     * @return The chunk data file.
     */
    public File getChunkDataFile(File chunkGridDataDirectory) {
        return new File(chunkGridDataDirectory, this.x + "/" + this.z + "/" + CHUNK_DATA_FILE);
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
        // Get the position
        int x = config.getInt("position.x", 0);
        int z = config.getInt("position.z", 0);

        // Construct a new dungeon chunk
        DungeonChunk dungeonChunk = new DungeonChunk(world, x, z);

        // TODO: Load all other stuff!

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
        config.createSection(CONFIG_DUNGEON_CHUNK_SECTION);
        save(config.getConfigurationSection(CONFIG_DUNGEON_CHUNK_SECTION));

        // Append the current Dungeon Maze version to the file
        config.set("version", DungeonMaze.instance.getVersionName());

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
