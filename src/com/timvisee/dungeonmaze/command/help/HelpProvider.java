package com.timvisee.dungeonmaze.command.help;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.command.*;
import com.timvisee.dungeonmaze.util.ListUtils;
import com.timvisee.dungeonmaze.util.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HelpProvider {

    public static void showHelp(CommandSender sender, CommandParts reference, CommandParts helpQuery) {
        showHelp(sender, reference, helpQuery, true, true, true, true, true, true);
    }

    public static void showHelp(CommandSender sender, CommandParts reference, CommandParts helpQuery, boolean showCommand, boolean showDescription, boolean showArguments, boolean showPermissions, boolean showAlternatives, boolean showCommands) {
        // Show help
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.GREEN + "==========[ DUNGEON MAZE HELP ]==========");

        FoundCommandResult result = Core.getCommandHandler().getCommandManager().findCommand(new CommandParts(helpQuery.getList()));

        CommandParts commandReferenceOther = new CommandParts(reference.get(0), helpQuery.getList());
        FoundCommandResult resultOther = Core.getCommandHandler().getCommandManager().findCommand(commandReferenceOther);

        if(result.getDifference() > resultOther.getDifference())
            result = resultOther;

        if(result == null) {
            sender.sendMessage(ChatColor.DARK_RED + "No help found!");
            return;
        }

        CommandParts helpCommandReference = result.getCommandDescription().getCommandReference(helpQuery);
        final String parentCommand = (new CommandParts(helpCommandReference.getRange(0, helpCommandReference.getCount() - 1))).toString();
        final String commandLabel = helpCommandReference.get(helpCommandReference.getCount() - 1);

        // Get the command
        CommandDescription command = result.getCommandDescription();

        // Print the command arguments
        if(command != null) {
            if(showCommand)
                printCommand(sender, command, helpQuery);
            if(showDescription)
                printCommandDescription(sender, command);
            if(showArguments)
                printArguments(sender, command);
            if(showPermissions)
                printPermissions(sender, command);
            if(showAlternatives)
                printAlternatives(sender, command, helpQuery);
            if(showCommands)
                printChilds(sender, command, helpQuery);
        }
    }

    private static String getCommandSyntax(CommandDescription commandDescription, CommandParts helpQuery, String alternativeLabel, boolean highlight) {
        // Create a string builder to build the command
        StringBuilder sb = new StringBuilder();

        // Set the color and prefix a slash
        sb.append(ChatColor.WHITE + "/");

        CommandParts helpCommandReference = commandDescription.getCommandReference(helpQuery);
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

    private static void printCommand(CommandSender sender, CommandDescription command, CommandParts helpQuery) {
        sender.sendMessage(ChatColor.GOLD + "Command: " + getCommandSyntax(command, helpQuery, null, true));
    }

    private static void printCommandDescription(CommandSender sender, CommandDescription command) {
        // Print the regular description, if available
        if(command.hasDescription())
            sender.sendMessage(ChatColor.GOLD + "Short Description: " + ChatColor.WHITE + command.getDescription());

        // Print the detailed description, if available
        if(command.hasDetailedDescription()) {
            sender.sendMessage(ChatColor.GOLD + "Detailed Description:");
            sender.sendMessage(ChatColor.WHITE + " " + command.getDetailedDescription());
        }
    }

    private static void printArguments(CommandSender sender, CommandDescription command) {
        // Make sure there are any commands to print
        if(!command.hasArguments() && command.getMaximumArguments() >= 0)
            return;

        // Print the header
        sender.sendMessage(ChatColor.GOLD + "Arguments:");

        // Print each argument
        for(CommandArgumentDescription arg : command.getArguments()) {
            // Create a string builder to build the syntax in
            StringBuilder argString = new StringBuilder();
            argString.append(" " + ChatColor.YELLOW + ChatColor.ITALIC + arg.getLabel() + " : " + ChatColor.WHITE + arg.getDescription());

            // Suffix a note if the command is optional
            if(arg.isOptional())
                argString.append(ChatColor.GRAY + "" + ChatColor.ITALIC + " (Optional)");

            // Print the syntax
            sender.sendMessage(argString.toString());
        }

        // Show the unlimited arguments argument
        if(command.getMaximumArguments() < 0)
            sender.sendMessage(" " + ChatColor.YELLOW + ChatColor.ITALIC + "... : " + ChatColor.WHITE + "Any additional arguments." + ChatColor.DARK_GRAY + ChatColor.ITALIC + " (Optional)");
    }

    private static void printPermissions(CommandSender sender, CommandDescription command) {
        // Get the permissions and make sure it isn't null
        CommandPermissions permissions = command.getCommandPermissions();
        if(permissions == null)
            return;

        // Make sure any permission node is set
        if(permissions.getPermissionNodeCount() <= 0)
            return;

        // Print the header
        sender.sendMessage(ChatColor.GOLD + "Permissions:");

        // Print each node
        for(String node : permissions.getPermissionNodes()) {
            boolean nodePermission = true;
            if(sender instanceof Player)
                nodePermission = Core.getPermissionsManager().hasPermission((Player) sender, node, false);
            final String nodePermsString = ChatColor.DARK_GRAY + (nodePermission ? ChatColor.ITALIC + " (Permission!)" : ChatColor.ITALIC + " (No Permission!)");
            sender.sendMessage(" " + ChatColor.YELLOW + ChatColor.ITALIC + node + nodePermsString);
        }

        // Print the default permission
        switch(permissions.getDefaultPermission()) {
        case ALLOWED:
            sender.sendMessage(ChatColor.GOLD + " Default: " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "Permission!");
            break;

        case OP_ONLY:
            final String defaultPermsString = ChatColor.DARK_GRAY + (permissions.getDefaultPermissionCommandSender(sender) ? ChatColor.ITALIC + " (Permission!)" : ChatColor.ITALIC + " (No Permission!)");
            sender.sendMessage(ChatColor.GOLD + " Default: " + ChatColor.YELLOW + ChatColor.ITALIC + "OP's Only!" + defaultPermsString);
            break;

        case NOT_ALLOWED:
        default:
            sender.sendMessage(ChatColor.GOLD + " Default: " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "No Permission!");
            break;
        }

        // Print the permission result
        if(permissions.hasPermission(sender))
            sender.sendMessage(ChatColor.GOLD + " Result: " + ChatColor.GREEN + ChatColor.ITALIC + "Permission!");
        else
            sender.sendMessage(ChatColor.GOLD + " Result: " + ChatColor.DARK_RED + ChatColor.ITALIC + "No Permission!");
    }

    private static void printAlternatives(CommandSender sender, CommandDescription command, CommandParts helpQuery) {
        // Make sure there are any alternatives
        if(command.getLabels().size() <= 1)
            return;

        // Print the header
        sender.sendMessage(ChatColor.GOLD + "Alternatives:");

        // Get the label used
        final String usedLabel = helpQuery.get(command.getParentCount() - 1);

        // Create a list of alternatives
        // TODO: Sometimes showing the non-alternative too!
        List<String> alternatives = new ArrayList<String>();
        for(String entry : command.getLabels()) {
            // Exclude the proper argument
            if(entry.equalsIgnoreCase(usedLabel))
                continue;
            alternatives.add(entry);
        }

        // Sort the alternatives
        Collections.sort(alternatives, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Double.compare(StringUtils.getDifference(usedLabel, o1), StringUtils.getDifference(usedLabel, o2));
            }
        });

        // Print each alternative with proper syntax
        for(String alternative : alternatives)
            sender.sendMessage(" " + getCommandSyntax(command, helpQuery, alternative, true));
    }

    private static void printChilds(CommandSender sender, CommandDescription command, CommandParts helpQuery) {
        // Make sure there are child's
        if(command.getChilds().size() <= 0)
            return;

        // Print the header
        sender.sendMessage(ChatColor.GOLD + "Commands:");

        // Loop through each child
        for(CommandDescription child : command.getChilds())
            sender.sendMessage(" " + getCommandSyntax(child, helpQuery, null, false) + ChatColor.GRAY + ChatColor.ITALIC + " : " + child.getDescription());
    }
}
