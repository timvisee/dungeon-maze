package com.timvisee.dungeonmaze.command;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.permission.PermissionsManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
     * Get the permission node required to execute this command as a player.
     *
     * @return The permission node required to execute this command as a player, or an empty string if this command
     * doesn't require any permission.
     */
    public abstract String getPermissionNode();

    /**
     * Get the default permission used if the permission couldn't be checked using any permissions plugin.
     *
     * @param sender The command sender to get the default permission for.
     *
     * @return True if the command sender has permission if the permissions system couldn't be used, false otherwise.
     */
    public abstract boolean getDefaultPermission(CommandSender sender);

    /**
     * Check whether this command requires any permission to be executed. This is based on the getPermission() method.
     *
     * @return True if this command requires any permission to be executed by a player.
     */
    public boolean hasPermission(CommandSender sender) {
        // Make sure any permission is required for this command
        if(getPermissionNode().trim().length() == 0)
            return true;

        // Make sure the command sender is a player, if not use the default
        if(!(sender instanceof Player))
            return getDefaultPermission(sender);

        // Get the player instance
        Player player = (Player) sender;

        // Get the permissions manager, and make sure it's instance is valid
        PermissionsManager permissionsManager = Core.getPermissionsManager();
        if(permissionsManager == null)
            return false;

        // Check whether the player has permission, return the result
        return permissionsManager.hasPermission(player, getPermissionNode(), getDefaultPermission(sender));
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
     * @param parentCmd The parent command, or an empty string if there isn't any.
     * @param cmd The command label.
     * @param args The command arguments.
     *
     * @return True if the command was executed, false otherwise.
     */
    public abstract boolean onCommand(CommandSender sender, String parentCmd, String cmd, List<String> args);
}
