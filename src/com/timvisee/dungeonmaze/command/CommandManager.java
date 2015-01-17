package com.timvisee.dungeonmaze.command;

import com.timvisee.dungeonmaze.command.executable.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class CommandManager {

    /** The list of commandDescriptions. */
    private List<CommandDescription> commandDescriptions = new ArrayList<CommandDescription>();

    /**
     * Constructor.
     */
    public CommandManager() {
        CommandDescription dungeonMazeCommand = new CommandDescription(new DungeonMazeCommand(), new ArrayList<String>() {{
            add("dungeonmaze");
            add("dm");
        }}, "Main Dungeon Maze command.", null);

        CommandDescription helpCommand = new CommandDescription(new HelpCommand(), new ArrayList<String>() {{
            add("help");
            add("hlp");
            add("h");
            add("sos");
            add("?");
            add(".");
            add("-");
        }}, "Help command.", dungeonMazeCommand);
        helpCommand.setMaximumArguments(false);

        CommandDescription versionCommand = new CommandDescription(new VersionCommand(), new ArrayList<String>() {{
            add("version");
            add("vers");
            add("vrsn");
            add("ver");
            add("v");
            add("about");
            add("who");
            add("creator");
            add("info");
            add("release");
            add("copyright");
            add("developer");
            add("developers");
            add("contributor");
            add("contributors");
            add("license");
            add("website");
        }}, "Dungeon Maze version command.", dungeonMazeCommand);
        versionCommand.setMaximumArguments(false);

        CommandDescription createWorldCommand = new CommandDescription(new CreateWorldCommand(), new ArrayList<String>() {{
            add("createworld");
            add("createw");
            add("cworld");
            add("cw");
        }}, "Create world command.", dungeonMazeCommand);
        createWorldCommand.addArgument(new CommandArgumentDescription("world", "The name of the world to create.", false));
        createWorldCommand.setCommandPermissions("dungeonmaze.command.createworld", CommandPermissions.DefaultPermission.OP_ONLY);

        CommandDescription teleportCommand = new CommandDescription(new TeleportCommand(), new ArrayList<String>() {{
            add("teleport");
            add("tp");
            add("warp");
            add("go");
            add("move");
        }}, "Teleport command.", dungeonMazeCommand);
        teleportCommand.addArgument(new CommandArgumentDescription("world", "The name of the world to teleport to.", false));
        teleportCommand.setCommandPermissions("dungeonmaze.command.teleport", CommandPermissions.DefaultPermission.OP_ONLY);

        CommandDescription listWorldCommand = new CommandDescription(new ListWorldCommand(), new ArrayList<String>() {{
            add("listworlds");
            add("listworld");
            add("list");
            add("worlds");
            add("lw");
        }}, "List world command.", dungeonMazeCommand);
        listWorldCommand.setCommandPermissions("dungeonmaze.command.listworlds", CommandPermissions.DefaultPermission.OP_ONLY);

        CommandDescription reloadCommand = new CommandDescription(new ReloadCommand(), new ArrayList<String>() {{
            add("reload");
            add("rld");
            add("rel");
            add("r");
        }}, "Reload command.", dungeonMazeCommand);
        reloadCommand.setCommandPermissions("dungeonmaze.command.reload", CommandPermissions.DefaultPermission.OP_ONLY);

        CommandDescription reloadPermissionsCommand = new CommandDescription(new ReloadPermissionsCommand(), new ArrayList<String>() {{
            add("reloadpermissions");
            add("reloadpermission");
            add("reloadperms");
            add("rldperms");
            add("relperms");
            add("rp");
        }}, "Reload permissions command.", dungeonMazeCommand);
        reloadPermissionsCommand.setCommandPermissions("dungeonmaze.command.reloadpermissions", CommandPermissions.DefaultPermission.OP_ONLY);

        CommandDescription checkUpdatesCommand = new CommandDescription(new CheckUpdatesCommand(), new ArrayList<String>() {{
            add("checkupdates");
            add("checkupdate");
            add("check");
            add("updates");
            add("update");
            add("cu");
        }}, "Check updates command.", dungeonMazeCommand);
        checkUpdatesCommand.setCommandPermissions("dungeonmaze.command.checkupdates", CommandPermissions.DefaultPermission.OP_ONLY);

        CommandDescription installUpdateCommand = new CommandDescription(new InstallUpdateCommand(), new ArrayList<String>() {{
            add("installupdates");
            add("installupdate");
            add("install");
            add("iu");
        }}, "Install update command.", dungeonMazeCommand);
        installUpdateCommand.setCommandPermissions("dungeonmaze.command.installupdate", CommandPermissions.DefaultPermission.OP_ONLY);

        this.commandDescriptions.add(dungeonMazeCommand);
    }

    /**
     * Get the list of command descriptions
     *
     * @return List of command descriptions.
     */
    public List<CommandDescription> getCommandDescriptions() {
        return this.commandDescriptions;
    }

    /**
     * Get the number of command description count.
     *
     * @return Command description count.
     */
    public int getCommandDescriptionCount() {
        return this.getCommandDescriptions().size();
    }

    /**
     * Get a suitable command for the specified command reference.
     *
     * @param commandReference The command reference.
     *
     * @return The suitable command result or null.
     */
    public SuitableCommandResult getSuitableCommand(CommandReference commandReference) {
        // Make sure the command reference is valid
        if(!commandReference.isValid())
            return null;

        // Get the root command description
        for(CommandDescription commandDescription : this.commandDescriptions) {
            // Check whether there's a command description available for the current command
            if(!commandDescription.isSuitableLabel(commandReference))
                return null;

            // Get the suitable command reference, return the result
            return commandDescription.getSuitableCommand(commandReference);
        }

        // No applicable command description found, return false
        return null;
    }
}
