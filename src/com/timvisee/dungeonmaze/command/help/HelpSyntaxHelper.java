package com.timvisee.dungeonmaze.command.help;

import com.timvisee.dungeonmaze.command.CommandArgumentDescription;
import com.timvisee.dungeonmaze.command.CommandDescription;
import com.timvisee.dungeonmaze.command.CommandParts;
import com.timvisee.dungeonmaze.util.ListUtils;
import org.bukkit.ChatColor;

public class HelpSyntaxHelper {

    /**
     * Get the proper syntax for a command.
     *
     * @param commandDescription The command to get the syntax for.
     * @param commandReference The reference of the command.
     * @param alternativeLabel The alternative label to use for this command syntax.
     * @param highlight True to highlight the important parts of this command.
     *
     * @return The command with proper syntax.
     */
    public static String getCommandSyntax(CommandDescription commandDescription, CommandParts commandReference, String alternativeLabel, boolean highlight) {
        // Create a string builder to build the command
        StringBuilder sb = new StringBuilder();

        // Set the color and prefix a slash
        sb.append(ChatColor.WHITE + "/");

        CommandParts helpCommandReference = commandDescription.getCommandReference(commandReference);
        final String parentCommand = (new CommandParts(helpCommandReference.getRange(0, helpCommandReference.getCount() - 1))).toString();
        String commandLabel = helpCommandReference.get(helpCommandReference.getCount() - 1);

        if(alternativeLabel != null)
            if(alternativeLabel.trim().length() > 0)
                commandLabel = alternativeLabel;

        sb.append(ListUtils.implode(parentCommand, (highlight ? ChatColor.YELLOW + "" + ChatColor.BOLD : "") + commandLabel, " "));
        if(highlight)
            sb.append(ChatColor.YELLOW);

        for(CommandArgumentDescription arg : commandDescription.getArguments()) {
            if(!arg.isOptional())
                sb.append(ChatColor.ITALIC + " <" + arg.getLabel() + ">");
            else
                sb.append(ChatColor.ITALIC + " [" + arg.getLabel() + "]");
        }

        if(commandDescription.getMaximumArguments() < 0)
            sb.append(ChatColor.ITALIC + " ...");

        return sb.toString();
    }
}
