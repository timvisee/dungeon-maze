package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.CommandArguments;
import com.timvisee.dungeonmaze.command.CommandReference;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DungeonMazeCommand extends ExecutableCommand {

    /**
     * Execute the command.
     *
     * @param sender           The command sender.
     * @param commandReference The command reference.
     * @param commandArguments The command arguments.
     *
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean executeCommand(CommandSender sender, CommandReference commandReference, CommandArguments commandArguments) {
        // Show some version info
        sender.sendMessage(ChatColor.GREEN + "This server is running Dungeon Maze v" + DungeonMaze.instance.getVersion() + "!");
        sender.sendMessage(ChatColor.YELLOW + "Use the command " + ChatColor.GOLD + "/" + commandReference.get(0) + " help" + ChatColor.YELLOW + " to view help.");
        sender.sendMessage(ChatColor.YELLOW + "Use the command " + ChatColor.GOLD + "/" + commandReference.get(0) + " about" + ChatColor.YELLOW + " to view about.");
        return true;
    }
}
