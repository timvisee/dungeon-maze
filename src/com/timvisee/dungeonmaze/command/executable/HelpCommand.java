package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.command.CommandArguments;
import com.timvisee.dungeonmaze.command.CommandReference;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommand extends ExecutableCommand {

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
        // Get the root command
        String rootCommand = commandReference.get(0);

        // View the help
        sender.sendMessage(ChatColor.GREEN + "==========[ DUNGEON MAZE HELP ]==========");
        sender.sendMessage(ChatColor.GOLD + "/" + rootCommand + " help " + ChatColor.WHITE + ": View help");
        sender.sendMessage(ChatColor.GOLD + "/" + rootCommand + " createworld <name> " + ChatColor.WHITE + ": Create a Dungeon Maze world");
        sender.sendMessage(ChatColor.GOLD + "/" + rootCommand + " teleport <world> " + ChatColor.WHITE + ": Teleport to a world");
        sender.sendMessage(ChatColor.GOLD + "/" + rootCommand + " listworlds " + ChatColor.WHITE + ": List Dungeon Maze worlds");
        sender.sendMessage(ChatColor.GOLD + "/" + rootCommand + " reload " + ChatColor.WHITE + ": Reload config files");
        sender.sendMessage(ChatColor.GOLD + "/" + rootCommand + " reloadperms " + ChatColor.WHITE + ": Reload permissions system");
        sender.sendMessage(ChatColor.GOLD + "/" + rootCommand + " checkupdates " + ChatColor.WHITE + ": Check for updates");
        sender.sendMessage(ChatColor.GOLD + "/" + rootCommand + " installupdate " + ChatColor.WHITE + ": Install new updates");
        sender.sendMessage(ChatColor.GOLD + "/" + rootCommand + " version " + ChatColor.WHITE + ": Check plugin version");

        // Return the result
        return true;
    }
}
