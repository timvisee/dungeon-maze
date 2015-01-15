package com.timvisee.dungeonmaze.plugin.metrics;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mcstats.Metrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetricsController {

    /** Metrics instance. */
    private Metrics metrics;

    /**
     * Constructor.
     *
     * @param init True to immediately initialize, false otherwise.
     */
    public MetricsController(boolean init) {
        // Check whether the controller should initialize
        if(init)
            init();
    }

    /**
     * Initialize.
     *
     * @return True if succeed, false otherwise. True will also be returned if the controller was initialized already.
     */
    public boolean init() {
        // Make sure the controller isn't initialized already
        if(isInit())
            return true;

        // Initialize Metrics
        try {
            // Construct the object
            this.metrics = new Metrics(DungeonMaze.instance);

            // Construct a graph, which can be immediately used and considered as valid player count in Dungeon Maze
            Metrics.Graph graph = metrics.createGraph("Players in Dungeon Maze");
            graph.addPlotter(new Metrics.Plotter("Players") {
                @Override
                public int getValue() {
                    List<Player> players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
                    int count = 0;
                    for(Player p : players) {
                        if(Core.getWorldManager().isDungeonMazeWorld(p.getWorld().getName()))
                            count++;
                    }
                    return count;
                }
            });

            // Start Metrics, return the result
            metrics.start();
            return true;

        } catch(IOException e) {
            // Failed to start Metrics
            e.printStackTrace();

            // Show an error message, reset the metrics instance and return the result
            Core.getLogger().info("[DungeonMaze] Failed to set up Metrics!");
            this.metrics = null;
            return false;
        }
    }

    /**
     * Check whether the controller is initialized.
     *
     * @return True if the controller is initialized, false otherwise.
     */
    public boolean isInit() {
        return this.metrics != null;
    }

    /**
     * Destroy the controller.
     *
     * @return True if the controller was successfully destroyed, false otherwise. True will also be returned if the
     * controller wasn't initialized.
     */
    public boolean destroy() {
        // Make sure the controller is initialized
        if(!isInit())
            return true;

        // Un-set Metrics. return the result
        this.metrics = null;
        return !isInit();
    }
}
