package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class VersionCommand extends ExecutableCommand {

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
        sender.sendMessage(ChatColor.GREEN + "This server is running Dungeon Maze v" + DungeonMaze.instance.getVersion());
        sender.sendMessage(ChatColor.GREEN + "Developed by Tim Visee - http://timvisee.com/");
        return true;
    }
}
