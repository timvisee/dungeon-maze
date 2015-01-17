package com.timvisee.dungeonmaze.command;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class CommandReference {

    /** Defines the command elements. */
    private List<String> commandElements = new ArrayList<String>();

    /**
     * Constructor.
     *
     * @param commandLabel Main command label.
     * @param arguments Command arguments.
     */
    public CommandReference(String commandLabel, String[] arguments) {
        addCommandElement(commandLabel);
        addCommandElements(arguments);
    }

    /**
     * Constructor.
     *
     * @param commandLabel Main command label.
     * @param arguments Command arguments.
     */
    public CommandReference(String commandLabel, List<String> arguments) {
        addCommandElement(commandLabel);
        addCommandElements(arguments);
    }

    /**
     * Constructor.
     *
     * @param commandElements List of command elements.
     */
    public CommandReference(List<String> commandElements) {
        addCommandElements(commandElements);
    }

    /**
     * Add a command element.
     *
     * @param element Command element to add.
     *
     * @return True on success, false on failure.
     */
    public boolean addCommandElement(String element) {
        // Make sure the element isn't empty
        if(element.trim().length() == 0)
            return false;

        // Add the element
        this.commandElements.add(element);
        return true;
    }

    /**
     * Add a list of command elements.
     *
     * @param elements List of command elements.
     */
    public void addCommandElements(List<String> elements) {
        for(String element : elements)
            addCommandElement(element);
    }

    /**
     * Get all command elements.
     *
     * @return All command elements.
     */
    public List<String> getCommandElements() {
        return this.commandElements;
    }

    /**
     * Add an array of command elements.
     *
     * @param elements Array of command elements.
     */
    public void addCommandElements(String[] elements) {
        for(String element : elements)
            addCommandElement(element);
    }

    /**
     * Get the number of command elements.
     *
     * @return Command element count.
     */
    public int getCommandElementCount() {
        return this.commandElements.size();
    }

    /**
     * Check whether the current reference is valid.
     *
     * @return True if the reference is valid, false otherwise.
     */
    public boolean isValid() {
        return getCommandElementCount() > 0;
    }

    /**
     * Get a specific command reference element.
     *
     * @param i Command reference element index to get.
     *
     * @return The command reference element, or null on failure.
     */
    public String get(int i) {
        return getCommandElement(i);
    }

    /**
     * Get a specific command reference element.
     *
     * @param i Command reference element index to get.
     *
     * @return The command reference element, or null on failure.
     */
    public String getCommandElement(int i) {
        // Make sure the index is in-bound
        if(i < 0 || i >= getCommandElementCount())
            return null;

        // Get and return the element
        return this.commandElements.get(i);
    }

    /**
     * Get a range of the command reference starting at the specified index up to the end of the range.
     *
     * @param start The starting index.
     *
     * @return The command reference range. Command references that were out of bound are not included.
     */
    public List<String> getCommandElementRange(int start) {
        return getCommandElemetRange(start, getCommandElementCount() - start);
    }

    /**
     * Get a range of the command reference elements.
     *
     * @param start The starting index.
     * @param count The number of elements to get.
     *
     * @return The element range. Elements that were out of bound are not included.
     */
    public List<String> getCommandElemetRange(int start, int count) {
        // Create a new list to put the range into
        List<String> elements = new ArrayList<String>();

        // Get the range
        for(int i = start; i < start + count; i++) {
            // Get the element and add it if it's valid
            String element = getCommandElement(i);
            if(element != null)
                elements.add(element);
        }

        // Return the list of elements
        return elements;
    }
}
