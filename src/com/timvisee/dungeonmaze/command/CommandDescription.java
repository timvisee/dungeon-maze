package com.timvisee.dungeonmaze.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandDescription {

    /** Defines the acceptable labels. */
    private List<String> labels = new ArrayList<String>();
    /** Command description. */
    private String description = "";
    /** The executable command instance. */
    private ExecutableCommand executableCommand;
    /** The parent command. */
    private CommandDescription parent = null;
    /** The child labels. */
    private List<CommandDescription> childs = new ArrayList<CommandDescription>();
    /** The command arguments. */
    private List<CommandArgumentDescription> arguments = new ArrayList<CommandArgumentDescription>();
    /** Defines whether there is an argument maximum or not. */
    private boolean noArgumentMaximum = false;
    /** Defines the command permissions. */
    private CommandPermissions permissions = new CommandPermissions();

    /**
     * Constructor.
     * @param executableCommand The executable command, or null.
     * @param label Command label.
     * @param description Command description.
     * @param parent Parent command.
     */
    public CommandDescription(ExecutableCommand executableCommand, String label, String description, CommandDescription parent) {
        this(executableCommand, label, description, parent, null);
    }

    /**
     * Constructor.
     * @param executableCommand The executable command, or null.
     * @param labels List of command labels.
     * @param description Command description.
     * @param parent Parent command.
     */
    public CommandDescription(ExecutableCommand executableCommand, List<String> labels, String description, CommandDescription parent) {
        this(executableCommand, labels, description, parent, null);
    }

    /**
     * Constructor.
     * @param executableCommand The executable command, or null.
     * @param label Command label.
     * @param description Command description.
     * @param parent Parent command.
     * @param arguments Command arguments.
     */
    public CommandDescription(ExecutableCommand executableCommand, String label, String description, CommandDescription parent, List<CommandArgumentDescription> arguments) {
        setExecutableCommand(executableCommand);
        setLabel(label);
        setDescription(description);
        setParent(parent);
        setArguments(arguments);
    }

    /**
     * Constructor.
     * @param executableCommand The executable command, or null.
     * @param labels List of command labels.
     * @param description Command description.
     * @param parent Parent command.
     * @param arguments Command arguments.
     */
    public CommandDescription(ExecutableCommand executableCommand, List<String> labels, String description, CommandDescription parent, List<CommandArgumentDescription> arguments) {
        setExecutableCommand(executableCommand);
        setLabels(labels);
        setDescription(description);
        setParent(parent);
        setArguments(arguments);
    }

    /**
     * Get the first relative command label.
     *
     * @return First relative command label.
     */
    public String getLabel() {
        // Ensure there's any item in the command list
        if(this.labels.size() == 0)
            return "";

        // Return the first command on the list
        return this.labels.get(0);
    }

    /**
     * Get all relative command labels.
     *
     * @return All relative labels labels.
     */
    public List<String> getLabels() {
        return this.labels;
    }

    /**
     * Set the list of command labels.
     *
     * @param labels New list of command labels. Null to clear the list of labels.
     */
    public void setLabels(List<String> labels) {
        // Check whether the command label list should be cleared
        if(labels == null)
            this.labels.clear();

        else
            this.labels = labels;
    }

    /**
     * Set the command label, this will append the command label to already existing ones.
     *
     * @param commandLabel Command label to set or add.
     */
    public void setLabel(String commandLabel) {
        setLabel(commandLabel, false);
    }

    /**
     * Set the command label.
     *
     * @param commandLabel Command label to set.
     *
     * @param overwrite True to replace all old command labels, false to append this command label to the currently
     *                  existing labels.
     */
    public void setLabel(String commandLabel, boolean overwrite) {
        // Check whether this new command should overwrite the previous ones
        if(!overwrite) {
            addLabel(commandLabel);
            return;
        }

        // Replace all labels with this new one
        this.labels.clear();
        this.labels.add(commandLabel);
    }

    /**
     * Add a command label to the list.
     *
     * @param commandLabel Command label to add.
     */
    public void addLabel(String commandLabel) {
        // TODO: Verify command label

        // Ensure this command isn't a duplicate
        if(hasLabel(commandLabel))
            return;

        // Add the command to the list
        this.labels.add(commandLabel);
    }

    /**
     * Add a list of command labels.
     *
     * @param commandLabels List of command labels to add.
     */
    public void addLabels(List<String> commandLabels) {
        // Add each command label separately
        for(String cmd : commandLabels)
            addLabel(cmd);
    }

    /**
     * Check whether this command description has a specific command.
     *
     * @param commandLabel Command to check for.
     *
     * @return True if this command label equals to the param command.
     */
    public boolean hasLabel(String commandLabel) {
        // Check whether any command matches with the argument
        for(String entry : this.labels)
            if(commandLabelEquals(entry, commandLabel))
                return true;

        // No match found, return false
        return false;
    }

    /**
     * Check whether this command description has a list of labels
     * @param commandLabels List of labels
     * @return True if all labels match, false otherwise
     */
    public boolean hasLabels(List<String> commandLabels) {
        // Check if there's a match for every command
        for(String cmd : commandLabels)
            if(!hasLabel(cmd))
                return false;

        // There seems to be a match for every command, return true
        return true;
    }

    /**
     * Check whether this command label is applicable with a command reference. This doesn't check if the parent
     * are suitable too.
     *
     * @param commandReference The command reference.
     *
     * @return True if the command reference is suitable to this command label, false otherwise.
     */
    public boolean isSuitableLabel(CommandReference commandReference) {
        // Make sure the command reference is valid
        if(!commandReference.isValid())
            return false;

        // Get the parent count
        String element = commandReference.getCommandElement(getParentCount());

        // Check whether this command description has this command label
        return hasLabel(element);
    }

    /**
     * Get the absolute command label.
     *
     * @return Absolute command label.
     */
    // TODO: Rewrite this method!
    public String getAbsoluteLabel() {
        // Create a string builder to shape the command in
        StringBuilder sb = new StringBuilder();

        // Check whether this command has a parent, if so, add the absolute parent command
        if(getParent() != null)
            sb.append(getParent().getAbsoluteLabel());

        // Add the command
        sb.append(" ").append(getLabel());

        // Return the build command
        return sb.toString();
    }

    /**
     * Get the executable command.
     *
     * @return The executable command.
     */
    public ExecutableCommand getExecutableCommand() {
        return this.executableCommand;
    }

    /**
     * Set the executable command.
     *
     * @param executableCommand The executable command.
     */
    public void setExecutableCommand(ExecutableCommand executableCommand) {
        this.executableCommand = executableCommand;
    }

    /**
     * Check whether this command is executable, based on the assigned executable command.
     *
     * @return True if this command is executable.
     */
    public boolean isExecutable() {
        return this.executableCommand != null;
    }

    /**
     * Execute the command, if possible.
     *
     * @param sender The command sender that triggered the execution of this command.
     * @param commandReference The command reference.
     * @param commandArguments The command arguments.
     *
     * @return True on success, false on failure.
     */
    public boolean execute(CommandSender sender, CommandReference commandReference, CommandArguments commandArguments) {
        // Make sure the command is executable
        if(!isExecutable())
            return false;

        // Execute the command, return the result
        return this.executableCommand.executeCommand(sender, commandReference, commandArguments);
    }

    /**
     * Get the parent command if this command description has a parent.
     *
     * @return Parent command, or null
     */
    public CommandDescription getParent() {
        return this.parent;
    }

    /**
     * Get the number of parent this description has.
     *
     * @return The number of parents.
     */
    public int getParentCount() {
        // Check whether the this description has a parent
        if(!hasParent())
            return 0;

        // Get the parent count of the parent, return the result
        return getParent().getParentCount() + 1;
    }

    /**
     * Set the parent command.
     *
     * @param parent Parent command.
     *
     * @return True on success, false on failure.
     */
    public boolean setParent(CommandDescription parent) {
        // Make sure the parent is different
        if(this.parent == parent)
            return true;

        // Set the parent
        this.parent = parent;

        // Make sure the parent isn't null
        if(parent == null)
            return true;

        // Add this description as a child to the parent
        return parent.addChild(this);
    }

    /**
     * Check whether the plugin description has a parent command.
     *
     * @return True if the description has a parent command, false otherwise.
     */
    public boolean hasParent() {
        return this.parent != null;
    }

    /**
     * Get all command childs.
     *
     * @return Command childs.
     */
    public List<CommandDescription> getChilds() {
        return this.childs;
    }

    /**
     * Add a child to the command description.
     *
     * @param commandDescription The child to add.
     *
     * @return True on success, false on failure.
     */
    public boolean addChild(CommandDescription commandDescription) {
        // Make sure the description is valid
        if(commandDescription == null)
            return false;
        if(!commandDescription.isValid())
            return false;

        // Make sure the child doesn't exist already
        if(isChild(commandDescription))
            return true;

        // The command description to add as a child
        if(!this.childs.add(commandDescription))
            return false;

        // Set this description as parent on the child
        return commandDescription.setParent(this);
    }

    /**
     * Set the childs of this command.
     *
     * @param childs New command childs. Null to remove all childs.
     */
    public void setChilds(List<CommandDescription> childs) {
        // Check whether the childs list should be cleared
        if(childs == null)
            this.childs.clear();

        else
            this.childs = childs;
    }

    /**
     * Check whether this command has any child labels.
     *
     * @return True if this command has any child labels.
     */
    public boolean hasChilds() {
        return (this.childs.size() != 0);
    }

    /**
     * Check if this command description has a specific child.
     *
     * @param commandDescription The command description to check for.
     *
     * @return True if this command description has the specific child, false otherwise.
     */
    public boolean isChild(CommandDescription commandDescription) {
        // Make sure the description is valid
        if(commandDescription == null)
            return false;
        if(!commandDescription.isValid())
            return false;

        // Check whether this child exists, return the result
        return this.childs.contains(commandDescription);
    }

    /**
     * Add an argument.
     *
     * @param argument The argument to add.
     *
     * @return True if succeed, false if failed.
     */
    public boolean addArgument(CommandArgumentDescription argument) {
        // Make sure the argument is valid
        if(argument == null)
            return false;

        // Make sure the argument isn't added already
        if(hasArgument(argument))
            return true;

        // Add the argument, return the result
        return this.arguments.add(argument);
    }

    /**
     * Get all command arguments.
     *
     * @return Command arguments.
     */
    public List<CommandArgumentDescription> getArguments() {
        return this.arguments;
    }

    /**
     * Set the arguments of this command.
     *
     * @param arguments New command arguments. Null to clear the list of arguments.
     */
    public void setArguments(List<CommandArgumentDescription> arguments) {
        // Convert null into an empty argument list
        if(arguments == null)
            this.arguments.clear();

        else
            this.arguments = arguments;
    }

    /**
     * Check whether an argument exists.
     *
     * @param argument The argument to check for.
     *
     * @return True if this argument already exists, false otherwise.
     */
    public boolean hasArgument(CommandArgumentDescription argument) {
        // Make sure the argument is valid
        if(argument == null)
            return false;

        // Check whether the argument exists, return the result
        return this.arguments.contains(argument);
    }

    /**
     * Check whether this command has any arguments.
     *
     * @return True if this command has any arguments.
     */
    public boolean hasArguments() {
        return (this.arguments.size() != 0);
    }

    /**
     * The minimum number of arguments required for this command.
     *
     * @return The minimum number of required arguments.
     */
    public int getMinimumArguments() {
        // Get the number of required and optional arguments
        int requiredArguments = 0;
        int optionalArgument = 0;

        // Loop through each argument
        for(CommandArgumentDescription argument : this.arguments) {
            // Check whether the command is optional
            if(!argument.isOptional()) {
                requiredArguments += optionalArgument + 1;
                optionalArgument = 0;

            } else
                optionalArgument++;
        }

        // Return the number of required arguments
        return requiredArguments;
    }

    /**
     * Get the maximum number of arguments.
     *
     * @return The maximum number of arguments. A negative number will be returned if there's no maximum.
     */
    public int getMaximumArguments() {
        // Check whether there is a maximum set
        if(this.noArgumentMaximum)
            return -1;

        // Return the maximum based on the registered arguments
        return this.arguments.size();
    }

    /**
     * Set whether there is an argument maximum.
     *
     * @param maximumArguments True if there is an argument maximum, based on the number of registered arguments.
     */
    public void setMaximumArguments(boolean maximumArguments) {
        this.noArgumentMaximum = !maximumArguments;
    }

    /**
     * Get the command description.
     *
     * @return Command description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the command description.
     *
     * @param description New command description. Null to reset the description.
     */
    public void setDescription(String description) {
        if(description == null)
            this.description = "";

        else
            this.description = description;
    }

    /**
     * Check whether this command has any description.
     *
     * @return True if this command has any description.
     */
    public boolean hasDescription() {
        return (this.description.trim().length() != 0);
    }

    /**
     * Get the command description that matches the specified command reference.
     *
     * @param commandReference The command reference.
     *
     * @return The suitable command result, or null.
     */
    // TODO: Return the closest command?
    public SuitableCommandResult getSuitableCommand(CommandReference commandReference) {
        // Make sure the command reference is valid
        if(!commandReference.isValid())
            return null;

        // Check whether this description is for the last element in the command reference, if so return this
        if(commandReference.getCommandElementCount() <= getParentCount() + 1)
            return new SuitableCommandResult(this, commandReference, new CommandArguments());

        // Get the new command reference and arguments
        CommandReference newReference = new CommandReference(commandReference.getCommandElemetRange(0, getParentCount() + 1));
        CommandArguments newArguments = new CommandArguments(commandReference.getCommandElementRange(getParentCount() + 1));

        // Loop through all the childs
        for(CommandDescription child : this.childs) {
            if(!child.isSuitableLabel(commandReference))
                continue;

            // Get and return the command description, and make sure the result isn't null
            SuitableCommandResult result = child.getSuitableCommand(commandReference);
            if(result == null)
                return new SuitableCommandResult(SuitableCommandResult.SuitableCommandResultType.WRONG_ARGUMENTS, this, newReference, newArguments);

            // Check each parent to see if the command reference suits
            while(result.getCommandDescription() != null) {
                // Check whether this command description has suitable, or near-suitable arguments
                int resultDifference = result.getCommandDescription().getSuitableArgumentsDifference(commandReference);
                if(resultDifference >= 0) {
                    if(resultDifference > 0)
                        result.setResultType(SuitableCommandResult.SuitableCommandResultType.WRONG_ARGUMENTS);
                    return result;
                }

                // Get the parent description
                //noinspection ConstantConditions
                result.setCommandDescription(result.getCommandDescription().getParent());
            }

            // Return null if there really isn't any command
            return null;
        }

        // Check if the remaining command reference elements fit the arguments for this command
        int resultDifference = getSuitableArgumentsDifference(commandReference);
        if(resultDifference >= 0) {
            SuitableCommandResult result = new SuitableCommandResult(this, newReference, newArguments);
            if(resultDifference > 0)
                result.setResultType(SuitableCommandResult.SuitableCommandResultType.WRONG_ARGUMENTS);
            return result;
        }

        // Return null if there really isn't a command
        return null;
    }

    /**
     * Check whether there's any command description that matches the specified command reference.
     *
     * @param commandReference The command reference.
     *
     * @return True if so, false otherwise.
     */
    public boolean hasSuitableCommand(CommandReference commandReference) {
        return getSuitableCommand(commandReference) != null;
    }

    /**
     * Check if the remaining command reference elements are suitable with arguments of the current command description.
     *
     * @param commandReference The command reference.
     *
     * @return True if the arguments are suitable, false otherwise.
     */
    public boolean hasSuitableArguments(CommandReference commandReference) {
        return getSuitableArgumentsDifference(commandReference) == 0;
    }

    /**
     * Check if the remaining command reference elements are suitable with arguments of the current command description,
     * and get the difference in argument count.
     *
     * @param commandReference The command reference.
     *
     * @return The difference in argument count between the reference and the actual command.
     */
    public int getSuitableArgumentsDifference(CommandReference commandReference) {
        // Make sure the command reference is valid
        if(!commandReference.isValid())
            return -1;

        // Get the remaining command reference element count
        int remainingElementCount = commandReference.getCommandElementCount() - getParentCount() - 1;

        // Check if there are too less arguments
        if(getMinimumArguments() > remainingElementCount)
            return Math.abs(getMinimumArguments() - remainingElementCount);

        // Check if there are too many arguments
        if(getMaximumArguments() < remainingElementCount && getMaximumArguments() >= 0)
            return Math.abs(remainingElementCount - getMaximumArguments());

        // The arguments seem to be OK, return the result
        return 0;
    }

    /**
     * Get the command permissions.
     *
     * @return The command permissions.
     */
    public CommandPermissions getCommandPermissions() {
        return this.permissions;
    }

    /**
     * Set the command permissions.
     *
     * @param commandPermissions The command permissions.
     */
    public void setCommandPermissions(CommandPermissions commandPermissions) {
        this.permissions = commandPermissions;
    }

    /**
     * Set the command permissions.
     *
     * @param permissionNode The permission node required.
     * @param defaultPermission The default permission.
     */
    public void setCommandPermissions(String permissionNode, CommandPermissions.DefaultPermission defaultPermission) {
        this.permissions = new CommandPermissions(permissionNode, defaultPermission);
    }

    /**
     * Check whether two labels equal to each other.
     *
     * @param commandLabel The first command label.
     * @param otherCommandLabel The other command label.
     *
     * @return True if the labels are equal to each other.
     */
    private static boolean commandLabelEquals(String commandLabel, String otherCommandLabel) {
        // Trim the command labels from unwanted whitespaces
        commandLabel = commandLabel.trim();
        otherCommandLabel = otherCommandLabel.trim();

        // Check whether the the two command labels are equal (case insensitive)
        return (commandLabel.equalsIgnoreCase(otherCommandLabel));
    }

    /**
     * Check whether the command description has been set up properly.
     *
     * @return True if the command description is valid, false otherwise.
     */
    public boolean isValid() {
        // TODO: Improve the quality of this method

        // Make sure any command label is set
        if(getLabels().size() == 0)
            return false;

        // Everything seems to be correct, return the result
        return true;
    }
}
