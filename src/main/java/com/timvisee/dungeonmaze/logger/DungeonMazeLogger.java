package com.timvisee.dungeonmaze.logger;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public class DungeonMazeLogger {

    /** The logger instance. */
    private Logger log;
    /** Defines whether debug messages are logged. */
    private boolean logDebug = true;
    /** Defines whether warning messages are logged. */
    private boolean logWarning= true;
    /** Defines whether error messages are logged. */
    private boolean logError = true;
    /** The prefix to use for info messages. */
    private String infoPrefix = "";
    /** The prefix to use for debug messages. */
    private String debugPrefix = "[Debug]";
    /** The prefix to use for warning messages. */
    private String warningPrefix = "[Warning]";
    /** The prefix to use for error messages. */
    private String errorPrefix = "[ERROR]";

    /**
     * Constructor.
     *
     * @param log The logger instance.
     */
    public DungeonMazeLogger(Logger log) {
        this.log = log;
    }

    /**
     * Get the logger.
     *
     * @return Logger instance.
     */
    public Logger getLogger() {
        return this.log;
    }

    /**
     * Set the logger.
     *
     * @param log Logger instance.
     */
    public void setLogger(Logger log) {
        this.log = log;
    }

    /**
     * Check whether debug messages are logged.
     *
     * @return True if debug messages are logged, false otherwise.
     */
    public boolean isLoggingDebug() {
        return this.logDebug;
    }

    /**
     * Set whether debug messages are logged.
     *
     * @param logDebug True if debug messages are logged, false otherwise.
     */
    public void setLoggingDebug(boolean logDebug) {
        this.logDebug = logDebug;
    }

    /**
     * Check whether warning messages are logged.
     *
     * @return True if warning messages are logged, false otherwise.
     */
    public boolean isLoggingWarning() {
        return this.logWarning;
    }

    /**
     * Set whether warning messages are logged.
     *
     * @param logWarning True if warning messages are logged, false otherwise.
     */
    public void setLoggingWarning(boolean logWarning) {
        this.logWarning = logWarning;
    }

    /**
     * Check whether error messages are logged.
     *
     * @return True if error messages are logged, false otherwise.
     */
    public boolean isLoggingError() {
        return this.logError;
    }

    /**
     * Set whether error messages are logged.
     *
     * @param logError True if error messages are logged, false otherwise.
     */
    public void setLoggingError(boolean logError) {
        this.logError = logError;
    }

    /**
     * Log a message.
     *
     * @param msg The message to log.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean log(String msg) {
        return log(null, msg);
    }

    /**
     * Log a message with a specified prefix.
     *
     * @param prefix The prefix to use for this log message.
     * @param msg The message to log.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean log(String prefix, String msg) {
        // Make sure the logger is set
        if(this.log == null)
            return false;

        // Build the log message with the proper prefix
        StringBuilder logMsg = new StringBuilder();

        // Append the prefix
        if(prefix != null)
            if(prefix.trim().length() > 0)
                logMsg.append(prefix).append(" ");

        // Append the message itself
        logMsg.append(msg);

        // Log the message, return the result
        this.log.info(logMsg.toString());
        return true;
    }

    /**
     * Log an info message.
     *
     * @param msg The info message to log.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean info(String msg) {
        return log(this.infoPrefix, msg);
    }

    /**
     * Log an debug message.
     *
     * @param msg The debug message to log.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean debug(String msg) {
        // Make sure debug messages should be shown
        if(!this.logDebug)
            return true;

        // Log the debug message
        return log(this.debugPrefix, msg);
    }

    /**
     * Log a warning message.
     *
     * @param msg The warning message to log.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean warning(String msg) {
        // Make sure the warning message should be shown
        if(!this.logError)
            return true;

        // Log the warning message
        return log(this.warningPrefix, msg);
    }

    /**
     * Log an error message.
     *
     * @param msg The error message to log.
     *
     * @return True if succeed, false otherwise.
     */
    public boolean error(String msg) {
        // Make sure the error message should be shown
        if(!this.logError)
            return true;

        // Log the error message
        return log(this.errorPrefix, msg);
    }

    /**
     * Get the info prefix.
     *
     * @return Info prefix.
     */
    public String getInfoPrefix() {
        return infoPrefix;
    }

    /**
     * Set the info prefix.
     *
     * @param infoPrefix Info prefix.
     */
    public void setInfoPrefix(String infoPrefix) {
        this.infoPrefix = infoPrefix;
    }

    /**
     * Get the debug prefix.
     *
     * @return Debug prefix.
     */
    public String getDebugPrefix() {
        return debugPrefix;
    }

    /**
     * Set the debug prefix.
     *
     * @param debugPrefix Debug prefix.
     */
    public void setDebugPrefix(String debugPrefix) {
        this.debugPrefix = debugPrefix;
    }

    /**
     * Get the warning prefix.
     *
     * @return Warning prefix.
     */
    public String getWarningPrefix() {
        return warningPrefix;
    }

    /**
     * Set the warning prefix.
     *
     * @param warningPrefix Warning prefix.
     */
    public void setWarningPrefix(String warningPrefix) {
        this.warningPrefix = warningPrefix;
    }

    /**
     * Get the error prefix.
     *
     * @return Error prefix.
     */
    public String getErrorPrefix() {
        return errorPrefix;
    }

    /**
     * Set the error prefix.
     *
     * @param errorPrefix Error prefix.
     */
    public void setErrorPrefix(String errorPrefix) {
        this.errorPrefix = errorPrefix;
    }
}
