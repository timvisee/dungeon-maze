package com.timvisee.dungeonmaze.command;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.command.executable.CheckUpdatesCommand;
import com.timvisee.dungeonmaze.command.executable.CreateWorldCommand;
import com.timvisee.dungeonmaze.command.executable.HelpCommand;
import com.timvisee.dungeonmaze.command.executable.InstallUpdateCommand;
import com.timvisee.dungeonmaze.command.executable.ListWorldCommand;
import com.timvisee.dungeonmaze.command.executable.ReloadCommand;
import com.timvisee.dungeonmaze.command.executable.ReloadPermissionsCommand;
import com.timvisee.dungeonmaze.command.executable.VersionCommand;
import com.timvisee.dungeonmaze.command.executable.TeleportCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler {

    /** The command manager instance. */
    private CommandManager commandManager;

    /** Defines the available commands. */
    private List<ExecutableCommand> commands = new ArrayList<ExecutableCommand>();

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

        // Initialize the commands
        commands.add(new HelpCommand());
        commands.add(new VersionCommand());
        commands.add(new CreateWorldCommand());
        commands.add(new TeleportCommand());
        commands.add(new ListWorldCommand());
        commands.add(new ReloadCommand());
        commands.add(new ReloadPermissionsCommand());
        commands.add(new CheckUpdatesCommand());
        commands.add(new InstallUpdateCommand());

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

        // Create a command reference
        CommandReference commandReference = new CommandReference(bukkitCmdLbl, args);

        // Get a suitable command for this reference, and make sure it isn't null
        SuitableCommandResult result = this.commandManager.getSuitableCommand(commandReference);
        if(result == null) {
            Core.getLogger().info("Failed to parse Dungeon Maze command!");
            return false;
        }

        // TODO: Handel non-suitable commands, for invalid argument numbers and such!
        if(!result.isSuitable())
            return false;

        // Make sure the command is executable
        if(!result.isExecutable()) {
            // TODO: Show more detailed help!
            sender.sendMessage(ChatColor.DARK_RED + "Incomplete command!");
            sender.sendMessage(ChatColor.YELLOW + "Use the command " + ChatColor.GOLD + "/dm help" + ChatColor.YELLOW + " to view help.");
            return true;
        }

        // Make sure the command sender has permission
        if(!result.hasPermission(sender)) {
            sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this command!");
            return true;
        }

        // Execute the command, return the result
        // TODO: Should we return the result, or whether the command was used?
        return result.executeCommand(sender);
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
