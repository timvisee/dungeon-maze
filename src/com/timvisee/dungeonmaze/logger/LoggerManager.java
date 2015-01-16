package com.timvisee.dungeonmaze.logger;

import com.timvisee.dungeonmaze.DungeonMaze;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerManager {

    /** Dungeon Maze Logger instance. */
    public Logger dungeonMazeLogger;
    /** Minecraft Logger instance. */
    public Logger minecraftLogger;

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
        this.dungeonMazeLogger = DungeonMaze.instance.getLogger();
        this.minecraftLogger = Logger.getLogger("Minecraft");
        return true;
    }

    /**
     * Check whether the logger manager is initialized.
     *
     * @return True if the manager is initialized, false otherwise.
     */
    public boolean isInit() {
        return this.dungeonMazeLogger != null && this.minecraftLogger != null;
    }

    /**
     * Destroy the logger manager. The destruction won't be forced.
     *
     * @return True on success, false on failure. True will also be returned if the manager wasn't initialized.
     */
    public boolean destroy() {
        this.dungeonMazeLogger = null;
        this.minecraftLogger = null;
        return true;
    }

    /**
     * Get the default logger.
     *
     * @return Default logger instance.
     */
    public Logger getLogger() {
        // Make sure the Dungeon Maze logger isn't null
        Logger log = getDungeonMazeLogger();
        if(log != null)
            return log;

        // Make sure the Minecraft logger isn't null
        log = getMinecraftLogger();
        if(log != null)
            return log;

        // Return the Minecraft logger
        return DungeonMaze.instance.getLogger();
    }

    /**
     * Get the Dungeon Maze logger.
     *
     * @return Dungeon Maze logger instance.
     */
    public Logger getDungeonMazeLogger() {
        return this.dungeonMazeLogger;
    }

    /**
     * Get the Minecraft logger.
     *
     * @return Minecraft logger instance.
     */
    public Logger getMinecraftLogger() {
        return this.minecraftLogger;
    }
}
