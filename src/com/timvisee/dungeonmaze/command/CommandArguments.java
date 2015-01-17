package com.timvisee.dungeonmaze.command;

import java.util.ArrayList;
import java.util.List;

public class CommandArguments {

    /** The list of arguments for this command. */
    private List<String> arguments = new ArrayList<String>();

    /**
     * Constructor.
     */
    public CommandArguments() { }

    /**
     * Constructor.
     *
     * @param arguments The list of arguments.
     */
    public CommandArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    /**
     * Get the command arguments.
     *
     * @return Command arguments.
     */
    public List<String> getArguments() {
        return this.arguments;
    }

    /**
     * Get the number of arguments.
     *
     * @return Argument count.
     */
    public int getArgumentCount() {
        return this.arguments.size();
    }

    /**
     * Get an arguemnt by it's index.
     *
     * @param i Arguemnt index.
     *
     * @return The argument.
     */
    public String get(int i) {
        return getArgument(i);
    }

    /**
     * Get an arguemnt by it's index.
     *
     * @param i Arguemnt index.
     *
     * @return The argument.
     */
    public String getArgument(int i) {
        // Make sure the index is in-bound
        if(i < 0 || i >= getArgumentCount())
            return null;

        // Get and return the argument
        return this.arguments.get(i);
    }

    /**
     * Get a range of the arguments starting at the specified index up to the end of the range.
     *
     * @param start The starting index.
     *
     * @return The argument range. Arguments that were out of bound are not included.
     */
    public List<String> getArgumentRange(int start) {
        return getArgumentRange(start, getArgumentCount() - start);
    }

    /**
     * Get a range of the arguments.
     *
     * @param start The starting index.
     * @param count The number of arguments to get.
     *
     * @return The argument range. Arguments that were out of bound are not included.
     */
    public List<String> getArgumentRange(int start, int count) {
        // Create a new list to put the range into
        List<String> elements = new ArrayList<String>();

        // Get the range
        for(int i = start; i < start + count; i++) {
            // Get the element and add it if it's valid
            String element = getArgument(i);
            if(element != null)
                elements.add(element);
        }

        // Return the list of elements
        return elements;
    }
}
