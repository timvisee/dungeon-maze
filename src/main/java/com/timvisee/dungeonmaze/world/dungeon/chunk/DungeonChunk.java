package com.timvisee.dungeonmaze.world.dungeon.chunk;

import com.timvisee.dungeonmaze.generator.chunk.BukkitChunk;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class DungeonChunk {

    /**
     * The size of a Minecraft chunk.
     */
    public static final int MINECRAFT_CHUNK_SIZE = 16;

    /**
     * Defines the size of a chunk.
     */
    public static final int CHUNK_SIZE = MINECRAFT_CHUNK_SIZE;

    /**
     * The dungeon region this chunk is in.
     */
    private final DungeonRegion region;

    /**
     * Defines the X and Y coordinate of the chunk in the world.
     */
    private final int x, y;

    /**
     * Defines whether this chunk is a custom chunk.
     */
    private boolean customChunk = false;

    /**
     * Constructor.
     *
     * @param region The dungeon region this chunk is in.
     * @param x The X coordinate of the chunk.
     * @param y The Y coordinate of the chunk.
     */
    public DungeonChunk(DungeonRegion region, int x, int y) {
        this.region = region;
        this.x = x;
        this.y = y;
    }

    /**
     * Get the dungeon region this world is in.
     *
     * @return Dungeon region.
     */
    public final DungeonRegion getRegion() {
        return this.region;
    }

    /**
     * Get the world the chunk is in.
     *
     * @return The world the chunk is in.
     */
    public final World getWorld() {
        return this.region.getWorld();
    }

    /**
     * Check whether this chunk is in the given world.
     *
     * @param world The world.
     *
     * @return True if this chunk is in the given world, false if not.
     */
    public boolean isWorld(World world) {
        return this.region.isWorld(world);
    }

    /**
     * Get the X coordinate of the chunk in the region space.
     *
     * @return The X coordinate of the chunk in the region space.
     */
    public final int getX() {
        return this.x;
    }

    /**
     * Get the X coordinate of the chunk in the chunk space.
     *
     * @return The X coordinate of the chunk in the chunk space.
     */
    public final int getChunkX() {
        return this.region.getX() * DungeonRegion.REGION_SIZE + this.x;
    }

    /**
     * Get the X coordinate of the chunk in the world space.
     *
     * @return The X coordinate of the chunk in the world space.
     */
    public final int getWorldX() {
        return this.region.getWorldX() + this.x * CHUNK_SIZE;
    }

    /**
     * Get the Y coordinate of the chunk in the region space.
     *
     * @return The Y coordinate of the chunk in the region space.
     */
    public final int getY() {
        return this.y;
    }

    /**
     * Get the Z coordinate of the chunk in the chunk space.
     *
     * @return The Z coordinate of the chunk in the chunk space.
     */
    public final int getChunkZ() {
        return this.region.getX() * DungeonRegion.REGION_SIZE + this.y;
    }

    /**
     * Get the Z coordinate of the chunk in the world space.
     *
     * @return The Z coordinate of the chunk in the world space.
     */
    public final int getWorldZ() {
        return this.region.getWorldZ() + this.y * CHUNK_SIZE;
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
     * Check whether this dungeon chunk is for the given chunk.
     *
     * @param chunk The chunk.
     *
     * @return True if this dungeon chunk is for the given chunk, false if not.
     */
    public boolean isAt(Chunk chunk) {
        return getWorldX() == chunk.getX() * MINECRAFT_CHUNK_SIZE && getWorldZ() == chunk.getZ() * MINECRAFT_CHUNK_SIZE;
    }

    /**
     * Check whether this chunk is for the given chunk and position.
     *
     * @param world The world.
     * @param chunk The chunk.
     *
     * @return True if the chunk is at this position, false otherwise.
     */
    public boolean is(World world, Chunk chunk) {
        return isWorld(world) && isAt(chunk);
    }

    /**
     * Get a new Bukkit chunk from this chunk.
     *
     * @return The new Bukkit chunk.
     */
    public BukkitChunk createBukkitChunk() {
        return new BukkitChunk(this);
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
     * @param region The dungeon region the world is in.
     * @param config The configuration section the dungeon chunk is in.
     *
     * @return The dungeon chunk.
     */
    public static DungeonChunk load(DungeonRegion region, ConfigurationSection config) {
        // Get the position
        int x = config.getInt("loc.x", 0);
        int y = config.getInt("loc.y", 0);

        // Construct a new dungeon chunk
        DungeonChunk dungeonChunk = new DungeonChunk(region, x, y);

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
