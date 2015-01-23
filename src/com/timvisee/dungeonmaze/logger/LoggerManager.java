package com.timvisee.dungeonmaze.logger;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.config.ConfigHandler;
import de.bananaco.bpermissions.imp.YamlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

public class LoggerManager {

    /** Dungeon Maze logger instance. */
    private DungeonMazeLogger dungeonMazeLogger;
    /** Logger instance for Dungeon Maze. */
    public Logger loggerDungeonMaze;
    /** Logger instance for Minecraft. */
    public Logger loggerMinecraft;

    /**
     * Constructor.
     *
     * @param init True to immediately initialize.
     */
    public LoggerManager(boolean init) {
        // Initialize
        if(init)
            init();
    }

    /**
     * Initialize the logger manager.
     *
     * @return True on success, false on failure. True will also be returned if the manager was initialized already.
     */
    public boolean init() {
        // Get and initialize the loggers
        this.loggerDungeonMaze = DungeonMaze.instance.getLogger();
        this.loggerMinecraft = Logger.getLogger("Minecraft");
        this.dungeonMazeLogger = new DungeonMazeLogger(this.loggerDungeonMaze);

        // Get the Dungeon Maze configuration
        FileConfiguration config = DungeonMaze.instance.getConfig();
        if(config != null) {
            this.dungeonMazeLogger.setLoggingDebug(config.getBoolean("logging.debug", true));
            this.dungeonMazeLogger.setLoggingError(config.getBoolean("logging.error", true));
        }

        return true;
    }

    /**
     * Check whether the logger manager is initialized.
     *
     * @return True if the manager is initialized, false otherwise.
     */
    public boolean isInit() {
        return this.loggerDungeonMaze != null && this.loggerMinecraft != null;
    }

    /**
     * Destroy the logger manager. The destruction won't be forced.
     *
     * @return True on success, false on failure. True will also be returned if the manager wasn't initialized.
     */
    public boolean destroy() {
        this.loggerDungeonMaze = null;
        this.loggerMinecraft = null;
        return true;
    }

    /**
     * Get the Dungeon Maze logger.
     *
     * @return Dungeon Maze logger instance.
     */
    public DungeonMazeLogger getLogger() {
        // Make sure the Dungeon Maze logger instance is set
        if(this.dungeonMazeLogger == null)
            return new DungeonMazeLogger(DungeonMaze.instance.getLogger());

        // Return the logger
        return this.dungeonMazeLogger;
    }

    /**
     * Get the Dungeon Maze logger.
     *
     * @return Dungeon Maze logger instance.
     */
    public DungeonMazeLogger getDungeonMazeLogger() {
        return this.dungeonMazeLogger;
    }

    /**
     * Get the logger instance for Dungeon Maze.
     *
     * @return Logger instance for Dungeon Maze.
     */
    public Logger getLoggerDungeonMaze() {
        return this.loggerDungeonMaze;
    }

    /**
     * Get the logger instance for Minecraft.
     *
     * @return Logger instance for Minecraft.
     */
    public Logger getLoggerMinecraft() {
        return this.loggerMinecraft;
    }
}
