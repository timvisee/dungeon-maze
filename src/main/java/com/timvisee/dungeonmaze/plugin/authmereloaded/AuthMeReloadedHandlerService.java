package com.timvisee.dungeonmaze.plugin.authmereloaded;

import com.timvisee.dungeonmaze.service.Service;

public class AuthMeReloadedHandlerService extends Service {

    /** Service name. */
    private static final String SERVICE_NAME = "AuthMe Reloaded Handler";

    /** AuthMe Reloaded handler instance. */
    private AuthMeReloadedHandler authMeReloadedHandler;

    /**
     * Initialize the service.
     *
     * @return True on success, false on failure. True will also be returned if the service was initialized already.
     */
    @Override
    public boolean init() {
        // Initialize the AuthMe Reloaded handler
        this.authMeReloadedHandler = new AuthMeReloadedHandler(false);

        // Hook the handler into the AuthMe Reloaded core, return the result
        return this.authMeReloadedHandler.hook();
    }

    /**
     * Check whether the service is initialized.
     *
     * @return True if the service is initialized, false otherwise.
     */
    @Override
    public boolean isInit() {
        // TODO: Better check!
        return this.authMeReloadedHandler != null;
    }

    /**
     * Destroy the service. The destruction won't be forced.
     *
     * @param force True to force the destruction. This wil re-destroy the service even if it isn't initialized.
     *              This will also force the initialization state to be set to false even if an error occurred while
     *              destroying.
     *
     * @return True on success, false on failure. True will also be returned if the service wasn't initialized. False
     * might be returned if force is set to true, even though the initialization state is set to false.
     */
    @Override
    public boolean destroy(boolean force) {
        // Make sure the AuthMe Reloaded handler is initialized
        if(!this.isInit() && !force)
            return true;

        // Unhook the AuthMe Reloaded handler
        if(this.authMeReloadedHandler != null) {
            if(!this.authMeReloadedHandler.unhook()) {
                if(force)
                    this.authMeReloadedHandler = null;
                return false;
            }
        }

        // Return the result
        this.authMeReloadedHandler = null;
        return true;
    }

    /**
     * Get the name of the service.
     *
     * @return Service name.
     */
    @Override
    public String getName() {
        return SERVICE_NAME;
    }

    /**
     * Get the AuthMe Reloaded handler.
     *
     * @return AuthMe Reloaded handler instance.
     */
    public AuthMeReloadedHandler getAuthMeReloadedHandler() {
        return this.authMeReloadedHandler;
    }
}
