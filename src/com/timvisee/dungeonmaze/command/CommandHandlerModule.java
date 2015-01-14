package com.timvisee.dungeonmaze.command;

import com.timvisee.dungeonmaze.module.Module;

public class CommandHandlerModule extends Module {

    /** Module name. */
    private static final String MODULE_NAME = "Command Handler";

    /** Command handler instance. */
    private CommandHandler commandHandler;

    /**
     * Initialize the module.
     *
     * @return True on success, false on failure. True will also be returned if the module was initialized already.
     */
    @Override
    public boolean init() {
        // Initialize the multiverse handler
        this.commandHandler = new CommandHandler(false);

        // Initialize the command handler, return the result
        return this.commandHandler.init();
    }

    /**
     * Check whether the module is initialized.
     *
     * @return True if the module is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        // Make sure the command handler is set
        if(this.commandHandler == null)
            return false;

        // Check whether the command handler is initialized
        return this.commandHandler.isInit();
    }

    /**
     * Destroy the module. The destruction won't be forced.
     *
     * @param force True to force the destruction. This wil re-destroy the module even if it isn't initialized.
     *              This will also force the initialization state to be set to false even if an error occurred while
     *              destroying.
     *
     * @return True on success, false on failure. True will also be returned if the module wasn't initialized. False
     * might be returned if force is set to true, even though the initialization state is set to false.
     */
    @Override
    public boolean destroy(boolean force) {
        // Make sure the command handler is initialized
        if(!this.isInit() && !force)
            return true;

        // Destroy the command handler
        if(this.commandHandler != null) {
            if(!this.commandHandler.destroy()) {
                if(force)
                    this.commandHandler = null;
                return false;
            }
        }

        // Return the result
        this.commandHandler = null;
        return true;
    }

    /**
     * Get the name of the module.
     *
     * @return Module name.
     */
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    /**
     * Get the command handler.
     *
     * @return Command handler instance.
     */
    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }
}
