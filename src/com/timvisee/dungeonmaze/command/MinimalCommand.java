package com.timvisee.dungeonmaze.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class MinimalCommand extends Command {

    /*
     * This is an example of a minimalist Command object, and could be used to easily create new commands.
     */

    /** Defines the applicable command labels for this command. */
    private static final List<String> APPLICABLE_COMMANDS = new ArrayList<String>() {{
        add("minimal");
    }};
    /** Defines the minimum number of required arguments for this command. */
    private static final int MIN_ARGS = 0;
    /** Defines the maximum number of required arguments for this command, or a negative number to ignore this. */
    private static final int MAX_ARGS = -1;

    /**
     * Get a list of applicable command labels for this command.
     *
     * @return A list of applicable command labels.
     */
    @Override
    public List<String> getApplicableCommands() {
        return APPLICABLE_COMMANDS;
    }

    /**
     * The minimum number of arguments this command requires.
     *
     * @return Minimum number of arguments.
     */
    @Override
    public int getMinArgs() {
        return MIN_ARGS;
    }

    /**
     * The maximum number of arguments this command requires.
     *
     * @return Maximum number of arguments or a negative value if this command doesn't have a maximum number of
     * arguments.
     */
    @Override
    public int getMaxArgs() {
        return MAX_ARGS;
    }

    /**
     * Handle the command.
     *
     * @param sender The command sender.
     * @param cmd    The command label.
     * @param args   The command arguments.
     *
     * @return True if the command was executed, false otherwise.
     */
    @Override
    public boolean onCommand(CommandSender sender, String cmd, List<String> args) {
        // Execute the command here!

        // Return the result
        return false;
    }
}
