package com.timvisee.dungeonmaze.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class Command {

    /**
     * Get a list of applicable command labels for this command.
     *
     * @return A list of applicable command labels.
     */
    public abstract List<String> getApplicableCommands();

    /**
     * Check whether a command label is applicable for the current command.
     *
     * @param cmd The command to check for.
     *
     * @return True if the command is applicable, false otherwise.
     */
    public boolean isApplicableCommand(String cmd) {
        // Trim and lowercase the command
        cmd = cmd.trim();

        // Make sure the command isn't an empty string
        if(cmd.length() == 0)
            return false;

        // Check whether the command label is applicable
        for(String entry : getApplicableCommands())
            if(cmd.equalsIgnoreCase(entry))
                return true;

        // The command doesn't seem to be applicable, return the result
        return false;
    }

    /**
     * The minimum number of arguments this command requires.
     *
     * @return Minimum number of arguments.
     */
    public abstract int getMinArgs();

    /**
     * The maximum number of arguments this command requires.
     *
     * @return Maximum number of arguments or a negative value if this command doesn't have a maximum number of
     * arguments.
     */
    public abstract int getMaxArgs();

    /**
     * Check whether this command has a maximum number of arguments, this is based on the value returned by the
     * getMaxArgs() method.
     *
     * @return True if this command has a maximum number of arguments, false otherwise.
     */
    public boolean hasMaxArgs() {
        return getMaxArgs() >= 0;
    }

    /**
     * Check whether the command is applicable for the current set of arguments.
     *
     * @return True if the command seems to be applicable, false otherwise.
     */
    public boolean isApplicable(String cmd, List<String> args) {
        // Check whether the command label is applicable
        if(!isApplicableCommand(cmd))
            return false;

        // Make sure the minimum number of command arguments is available
        if(args.size() < getMinArgs())
            return false;

        // Make sure the maximum number of commands is available, if there is a maximum
        if(hasMaxArgs())
            if(args.size() > getMaxArgs())
                return false;

        // The command seems to be applicable, return the result
        return true;
    }

    /**
     * Handle the command.
     *
     * @param sender The command sender.
     * @param cmd The command label.
     * @param args The command arguments.
     *
     * @return True if the command was executed, false otherwise.
     */
    public abstract boolean onCommand(CommandSender sender, String cmd, List<String> args);
}
