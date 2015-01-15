package com.timvisee.dungeonmaze.command.reload;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.command.Command;
import com.timvisee.dungeonmaze.util.Profiler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand extends Command {

    /** Defines the applicable command labels for this command. */
    private static final List<String> APPLICABLE_COMMANDS = new ArrayList<String>() {{
        add("reload");
        add("rld");
        add("rel");
        add("r");
    }};
    /** Defines the minimum number of required arguments for this command. */
    private static final int MIN_ARGS = 0;
    /** Defines the maximum number of required arguments for this command, or a negative number to ignore this. */
    private static final int MAX_ARGS = 0;
    /** Defines the permission node required to execute this command. */
    private static final String PERMISSION_NODE = "dungeonmaze.command.reload";

    /**
     * Get a list of applicable command labels for this command.
     *
     * @return A list of applicable command labels.
     */
    @Override
    public List<String> getApplicableCommands() {
        return APPLICABLE_COMMANDS;
    }

    /**
     * The minimum number of arguments this command requires.
     *
     * @return Minimum number of arguments.
     */
    @Override
    public int getMinArgs() {
        return MIN_ARGS;
    }

    /**
     * The maximum number of arguments this command requires.
     *
     * @return Maximum number of arguments or a negative value if this command doesn't have a maximum number of
     * arguments.
     */
    @Override
    public int getMaxArgs() {
        return MAX_ARGS;
    }

    /**
     * Get the permission node required to execute this command as a player.
     *
     * @return The permission node required to execute this command as a player, or an empty string if this command
     * doesn't require any permission.
     */
    @Override
    public String getPermissionNode() {
        return PERMISSION_NODE;
    }

    /**
     * Get the default permission used if the permission couldn't be checked using any permissions plugin.
     *
     * @param sender The command sender to get the default permission for.
     *
     * @return True if the command sender has permission if the permissions system couldn't be used, false otherwise.
     */
    @Override
    public boolean getDefaultPermission(CommandSender sender) {
        return sender.isOp();
    }

    /**
     * Handle the command.
     *
     * @param sender The command sender.
     * @param cmd    The command label.
     * @param args   The command arguments.
     *
     * @return True if the command was executed, false otherwise.
     */
    @Override
    public boolean onCommand(CommandSender sender, String cmd, List<String> args) {
        // Profile the reload process
        Profiler p = new Profiler(true);

        // Show a reload warning
        Core.getLogger().info("[DungeonMaze] Reloading Dungeon Maze...");
        sender.sendMessage(ChatColor.YELLOW + "Reloading Dungeon Maze...");

        // TODO: Reload the permissions module!

        // Reload configs and worlds
        Core.getConfigHandler().load();
        Core.getWorldManager();
        Core.getWorldManager().preloadWorlds();

        // Show a success message
        Core.getLogger().info("[DungeonMaze] Dungeon Maze has been reloaded successfully, took " + p.getTimeFormatted() + "!");
        sender.sendMessage(ChatColor.GREEN + "Dungeon Maze has been reloaded successfully, took " + p.getTimeFormatted() + "!");
        return true;
    }
}
