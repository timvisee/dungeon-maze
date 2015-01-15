package com.timvisee.dungeonmaze.command;

import com.timvisee.dungeonmaze.command.createworld.CreateWorldCommand;
import com.timvisee.dungeonmaze.command.listworld.ListWorldCommand;
import com.timvisee.dungeonmaze.command.reload.ReloadCommand;
import com.timvisee.dungeonmaze.command.version.VersionCommand;
import com.timvisee.dungeonmaze.command.teleport.TeleportCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler {

    /** Defines the available commands. */
    private List<Command> cmds = new ArrayList<Command>();

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

        // Initialize the commands
        cmds.add(new VersionCommand());
        cmds.add(new CreateWorldCommand());
        cmds.add(new TeleportCommand());
        cmds.add(new ListWorldCommand());
        cmds.add(new ReloadCommand());

        // Return the result
        return true;
    }

    /**
     * Check whether the command handler is initialized.
     *
     * @return True if the command handler is initialized.
     */
    public boolean isInit() {
        return !cmds.isEmpty();
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

        // Clear the commands list, return the result
        this.cmds.clear();
        return true;
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
        // Make sure the command is a Dungeon Maze command
        if(!bukkitCmdLbl.equalsIgnoreCase("dungeonmaze") && !bukkitCmdLbl.equalsIgnoreCase("dm"))
            return false;

        // Process the arguments
        List<String> args = processArguments(bukkitArgs);

        // Make sure the command has any arguments
        if(args.size() == 0) {
            sender.sendMessage(ChatColor.DARK_RED + "Unknown command!");
            sender.sendMessage(ChatColor.YELLOW + "Use the command " + ChatColor.GOLD + "/dm help" + ChatColor.YELLOW + " to view help.");
            return true;
        }

        // Get the command label
        String cmd = args.get(0);
        args.remove(0);

        // Loop through each available command to check whether it's applicable
        for(Command entry : cmds) {
            // Make sure the command label is applicable
            if(!entry.isApplicableCommand(cmd))
                continue;

            // Make sure the command is applicable
            if(!entry.isApplicable(cmd, args)) {
                // TODO: Improve the quality of the message shown!
                sender.sendMessage(ChatColor.DARK_RED + "Invalid command arguments!");
                sender.sendMessage(ChatColor.YELLOW + "Use the command " + ChatColor.GOLD + "/" + bukkitCmdLbl + " help " + cmd + ChatColor.YELLOW + " to view help!");
                return true;
            }

            // Make sure the command executor has permission
            if(!entry.hasPermission(sender)) {
                sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this command!");
                return true;
            }

            // Execute the command, return true if the command was successfully executed
            if(entry.onCommand(sender, cmd, args))
                return true;
        }

        // Unknown command, show a warning, return the result
        sender.sendMessage(ChatColor.DARK_RED + "Unknown Dungeon Maze command!");
        sender.sendMessage(ChatColor.YELLOW + "Use the command " + ChatColor.GOLD + "/" + bukkitCmdLbl + " help " + ChatColor.YELLOW + "to view help.");
        return true;
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
