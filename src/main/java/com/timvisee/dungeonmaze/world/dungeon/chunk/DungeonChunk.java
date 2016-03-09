package com.timvisee.dungeonmaze.world.dungeon.chunk;

import com.timvisee.dungeonmaze.generator.chunk.BukkitChunk;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class DungeonChunk {

    /**
     * Defines the size of a chunk.
     */
    public final static int CHUNK_SIZE = 16;

    /**
     * Defines the world the chunks is in.
     */
    private World world;

    /**
     * Defines the X and Y coordinate of the chunk in the world.
     */
    private int x, y;

    /**
     * Defines whether this chunk is a custom chunk.
     */
    private boolean customChunk = false;

    /**
     * Constructor.
     *
     * @param world The world the chunks is in.
     * @param x The X coordinate of the chunk.
     * @param y The Y coordinate of the chunk.
     */
    public DungeonChunk(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;
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
     * Get the X coordinate of the chunk in the world space.
     *
     * @return The X coordinate of the chunk in the world space.
     */
    public int getWorldX() {
        return this.x * CHUNK_SIZE;
    }

    /**
     * Get the Y coordinate of the chunk.
     *
     * @return The Y coordinate of the chunk.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Get the Z coordinate of the chunk in the world space.
     *
     * @return The Z coordinate of the chunk in the world space.
     */
    public int getWorldZ() {
        return this.y * CHUNK_SIZE;
    }

    /**
     * Check whether this chunk is at a specific position.
     *
     * @param chunkX The X coordinate of the chunk.
     * @param chunkY The Y coordinate of the chunk.
     *
     * @return True if the chunk is at this position, false otherwise.
     */
    public boolean isAt(int chunkX, int chunkY) {
        return this.x == chunkX && this.y == chunkY;
    }

    /**
     * Get a new Bukkit chunk from this chunk.
     *
     * @return The new Bukkit chunk.
     */
    public BukkitChunk createBukkitChunk() {
        return new BukkitChunk(this.world, this.x, this.y);
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
     * Load a dungeon chunk from a configuration section.
     *
     * @param world The world of the chunk.
     * @param config The configuration section the dungeon chunk is in.
     *
     * @return The dungeon chunk.
     */
    public static DungeonChunk load(World world, ConfigurationSection config) {
//        // Make sure the configuration contains the proper values
//        if(!config.isSet("loc.x") || !config.isSet("loc.y"))
//            return null;

        // Get the position
        int x = config.getInt("loc.x", 0);
        int y = config.getInt("loc.y", 0);

        // Construct a new dungeon chunk
        DungeonChunk dungeonChunk = new DungeonChunk(world, x, y);

        // Load whether this chunk is a custom chunk
        dungeonChunk.setCustomChunk(config.getBoolean("customChunk.isCustom", false));

        // Return the instance
        return dungeonChunk;
    }

    /**
     * Save the dungeon chunk in a configuration section.
     *
     * @param config The configuration section to save the dungeon chunk to.
     */
    public void save(ConfigurationSection config) {
        // Store the location of the chunk
        config.set("loc.x", this.x);
        config.set("loc.y", this.y);

        // Save whether this is a custom chunk
        config.set("customChunk.isCustom", this.customChunk);
    }
}
