package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.command.CommandParts;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import com.timvisee.dungeonmaze.permission.PermissionsManager;
import com.timvisee.dungeonmaze.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.security.Permissions;

public class StatusCommand extends ExecutableCommand {

    /**
     * Execute the command.
     *
     * @param sender           The command sender.
     * @param commandReference The command reference.
     * @param commandArguments The command arguments.
     *
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean executeCommand(CommandSender sender, CommandParts commandReference, CommandParts commandArguments) {
        // Print the status info header
        sender.sendMessage(ChatColor.GOLD + "==========[ DUNGEON MAZE STATUS ]==========");

        // Get the world manager
        WorldManager worldManager = Core.getWorldManager();

        // Print the number of Dungeon Maze worlds
        if(worldManager != null)
            sender.sendMessage(ChatColor.GOLD + "DungeonMaze worlds: " + ChatColor.WHITE + worldManager.getDungeonMazeWorlds().size());
        else
            sender.sendMessage(ChatColor.GOLD + "DungeonMaze worlds: " + ChatColor.DARK_RED + ChatColor.ITALIC + "Unknown!");

        // Get the permissions manager
        PermissionsManager permissionsManager = Core.getPermissionsManager();

        // Print the permissions manager status
        if(permissionsManager != null) {
            // Get the used permissions system
            PermissionsManager.PermissionsSystemType type = permissionsManager.getUsedPermissionsSystemType();

            if(!type.equals(PermissionsManager.PermissionsSystemType.NONE))
                sender.sendMessage(ChatColor.GOLD + "Permissions System: " + ChatColor.GREEN + permissionsManager.getUsedPermissionsSystemType().getName());
            else
                sender.sendMessage(ChatColor.GOLD + "Permissions System: " + ChatColor.GRAY + ChatColor.ITALIC + permissionsManager.getUsedPermissionsSystemType().getName());
        } else
            sender.sendMessage(ChatColor.GOLD + "Permissions System: " + ChatColor.DARK_RED + ChatColor.ITALIC + "Unknown!");

        // Show the version status
        sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.WHITE + DungeonMaze.instance.getVersion());
        return true;
    }
}
