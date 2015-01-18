package com.timvisee.dungeonmaze.command;

import com.timvisee.dungeonmaze.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import ru.tehkode.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler {

    /** The command manager instance. */
    private CommandManager commandManager;

    /**
     * Constructor.
     *
     * @param init True to immediately initialize.
     */
    public CommandHandler(boolean init) {
        // Initialize
        if(init)
            init();
    }

    /**
     * Initialize the command handler.
     *
     * @return True if succeed, false on failure. True will also be returned if the command handler was already
     * initialized.
     */
    public boolean init() {
        // Make sure the handler isn't initialized already
        if(isInit())
            return true;

        // Initialize the command manager
        this.commandManager = new CommandManager();
        // TODO: Command manager initialization!

        // Return the result
        return true;
    }

    /**
     * Check whether the command handler is initialized.
     *
     * @return True if the command handler is initialized.
     */
    public boolean isInit() {
        return this.commandManager != null;
    }

    /**
     * Destroy the command handler.
     *
     * @return True if the command handler was destroyed successfully, false otherwise. True will also be returned if
     * the command handler wasn't initialized.
     */
    public boolean destroy() {
        // Make sure the command handler is initialized
        if(!isInit())
            return true;

        // Unset the command manager
        this.commandManager = null;
        return true;
    }

    /**
     * Get the command manager.
     *
     * @return Command manager instance.
     */
    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    /**
     * Process a command.
     *
     * @param sender The command sender (Bukkit).
     * @param bukkitCmd The command (Bukkit).
     * @param bukkitCmdLbl The command label (Bukkit).
     * @param bukkitArgs The command arguments (Bukkit).
     *
     * @return True if the command was executed, false otherwise.
     */
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCmd, String bukkitCmdLbl, String[] bukkitArgs) {
        // Process the arguments
        List<String> args = processArguments(bukkitArgs);

        // Create a command reference, and make sure at least one command part is available
        CommandParts commandReference = new CommandParts(bukkitCmdLbl, args);
        if(commandReference.getCount() == 0)
            return false;

        // Get a suitable command for this reference, and make sure it isn't null
        FoundCommandResult result = this.commandManager.findCommand(commandReference);

        if(result == null) {
            Core.getLogger().info("Failed to parse Dungeon Maze command!");
            return false;
        }

        // Get the base command
        String baseCommand = commandReference.get(0);

        // Make sure the command difference isn't too big
        if(result.getDifference() > 0.12) {
            // TODO: Show arguments!
            sender.sendMessage(ChatColor.DARK_RED + "Unknown command!");
            if(result.getCommandDescription() != null)
                sender.sendMessage(ChatColor.YELLOW + "Did you mean " + ChatColor.GOLD + "/" + result.getCommandDescription().getCommandReference(commandReference) + ChatColor.YELLOW + "?");
            sender.sendMessage(ChatColor.YELLOW + "Use the command " + ChatColor.GOLD + "/" + baseCommand + " help" + ChatColor.YELLOW + " to view help.");
            return true;
        }

        // Make sure the command is executable
        if(!result.isExecutable()) {
            // TODO: Show more detailed help!
            sender.sendMessage(ChatColor.DARK_RED + "Unknown command!");
            sender.sendMessage(ChatColor.YELLOW + "Use the command " + ChatColor.GOLD + "/" + baseCommand + " help" + ChatColor.YELLOW + " to view help.");
            return true;
        }

        // Make sure the command sender has permission
        if(!result.hasPermission(sender)) {
            sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this command!");
            return true;
        }

        // Make sure the command sender has permission
        if(!result.hasProperArguments()) {
            CommandParts parts = new CommandParts(result.getCommandDescription().getCommandReference(commandReference).getRange(1));
            // TODO: Show more detailed help!
            sender.sendMessage(ChatColor.DARK_RED + "Incorrect command arguments!");
            sender.sendMessage(ChatColor.YELLOW + "Use the command " + ChatColor.GOLD + "/" + baseCommand + " help " + parts.toString() + ChatColor.YELLOW + " to view help.");
            return true;
        }

        // Execute the command if it's suitable
        return result.executeCommand(sender);

        // Show an warning/error message
        // TODO: Show more detailed help!
        /*sender.sendMessage(ChatColor.DARK_RED + "Invalid command!");
        sender.sendMessage(ChatColor.YELLOW + "Use the command " + ChatColor.GOLD + "/" + baseCommand + " help" + ChatColor.YELLOW + " to view help.");
        return true;*/
    }

    /**
     * Process the command arguments, and return them as an array list.
     *
     * @param args The command arguments to process.
     *
     * @return The processed command arguments.
     */
    private List<String> processArguments(String[] args) {
        // Convert the array into a list of arguments
        List<String> arguments = new ArrayList<String>(Arrays.asList(args));

        /// Remove all empty arguments
        for(int i = 0; i < arguments.size(); i++) {
            // Get the argument value
            final String arg = arguments.get(i);

            // Check whether the argument value is empty
            if(arg.trim().length() == 0) {
                // Remove the current argument
                arguments.remove(i);

                // Decrease the index by one, continue to the next argument
                i--;
            }
        }

        // Return the argument
        return arguments;
    }
}
