package com.timvisee.dungeonmaze.command.help;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.command.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpProvider {

    /**
     * Show help for a specific command.
     *
     * @param sender The command sender the help needs to be shown to.
     * @param reference The command reference to the help command.
     * @param helpQuery The query to show help for.
     */
    public static void showHelp(CommandSender sender, CommandParts reference, CommandParts helpQuery) {
        showHelp(sender, reference, helpQuery, true, true, true, true, true, true);
    }

    /**
     * Show help for a specific command.
     *
     * @param sender The command sender the help needs to be shown to.
     * @param reference The command reference to the help command.
     * @param helpQuery The query to show help for.
     * @param showCommand True to show the command.
     * @param showDescription True to show the command description, both the short and detailed description.
     * @param showArguments True to show the command argument help.
     * @param showPermissions True to show the command permission help.
     * @param showAlternatives True to show the command alternatives.
     * @param showCommands True to show the child commands.
     */
    public static void showHelp(CommandSender sender, CommandParts reference, CommandParts helpQuery, boolean showCommand, boolean showDescription, boolean showArguments, boolean showPermissions, boolean showAlternatives, boolean showCommands) {
        // Print the help header
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.GREEN + "==========[ DUNGEON MAZE HELP ]==========");

        // Find the command for this help query, one with and one without a prefixed base command
        FoundCommandResult result = Core.getCommandHandler().getCommandManager().findCommand(new CommandParts(helpQuery.getList()));
        CommandParts commandReferenceOther = new CommandParts(reference.get(0), helpQuery.getList());
        FoundCommandResult resultOther = Core.getCommandHandler().getCommandManager().findCommand(commandReferenceOther);
        if(resultOther != null) {
            if(result == null)
                result = resultOther;

            else if(result.getDifference() > resultOther.getDifference())
                result = resultOther;
        }

        // TODO: Make sure the command is close enough to a know command!

        // Make sure a result was found
        if(result == null) {
            // Show a warning message
            sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.ITALIC + helpQuery);
            sender.sendMessage(ChatColor.DARK_RED + "Couldn't show any help information for this help query.");
            return;
        }

        // Get the command description, and make sure it's valid
        CommandDescription command = result.getCommandDescription();
        if(command == null) {
            // Show a warning message
            sender.sendMessage(ChatColor.DARK_RED + "Failed to retrieve any help information!");
            return;
        }

        // Print the command help information
        if(showCommand)
            HelpPrinter.printCommand(sender, command, helpQuery);
        if(showDescription)
            HelpPrinter.printCommandDescription(sender, command);
        if(showArguments)
            HelpPrinter.printArguments(sender, command);
        if(showPermissions)
            HelpPrinter.printPermissions(sender, command);
        if(showAlternatives)
            HelpPrinter.printAlternatives(sender, command, helpQuery);
        if(showCommands)
            HelpPrinter.printChilds(sender, command, helpQuery);
    }
}
