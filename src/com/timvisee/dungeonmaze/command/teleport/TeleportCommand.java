package com.timvisee.dungeonmaze.command.teleport;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportCommand extends Command {

    /** Defines the applicable command labels for this command. */
    private static final List<String> APPLICABLE_COMMANDS = new ArrayList<String>() {{
        add("teleport");
        add("tp");
        add("warp");
        add("go");
        add("move");
    }};
    /** Defines the minimum number of required arguments for this command. */
    private static final int MIN_ARGS = 1;
    /** Defines the maximum number of required arguments for this command, or a negative number to ignore this. */
    private static final int MAX_ARGS = 1;

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
        // If the command executor is a player, make sure the player has permission.
        if(sender instanceof Player) {
            if(!Core.getPermissionsManager().hasPermission((Player) sender, "dungeonmaze.command.teleport", sender.isOp())) {
                sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
                return true;
            }
        }

        // Make sure the command is executed by an in-game player
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "You need to be in-game to use this command!");
            return true;
        }

        // Get the player and the world name to teleport to
        Player player = (Player) sender;
        String worldName = args.get(0);

        // Make sure the world exists
        if(!DungeonMaze.instance.worldExists(worldName)) {
            sender.sendMessage(ChatColor.DARK_RED + worldName);
            sender.sendMessage(ChatColor.DARK_RED + "This world doesn't exists!");
            return true;
        }

        // Force the world to be loaded if it isn't already loaded
        if(!DungeonMaze.instance.worldIsLoaded(worldName))
            DungeonMaze.instance.worldLoad(worldName);

        // Get the world instance and make sure it's valid
        World world = Bukkit.getWorld(worldName);
        if(world == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Failed to teleport!");
            return true;
        }

        // Teleport the player, show a status message and return true
        player.teleport(world.getSpawnLocation());
        player.sendMessage(ChatColor.GREEN + "You have been teleported!");
        return true;
    }
}
