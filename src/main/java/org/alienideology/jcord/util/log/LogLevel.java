package org.alienideology.jcord.util.log;

/**
 * LogLevel - The logger level, used to indicate the log types.
 *
 * @author AlienIdeology
 */
public enum LogLevel {

    /**
     * Log level: [FETAL]
     * Used to indicate a library internal error.
     */
    FETAL("[FETAL]"),

    /**
     * Log level: [ERROR]
     * Used to indicate a user end error.
     */
    ERROR("[ERROR]"),

    /**
     * Log level: [WARN]
     * Used to warn the user, error related.
     */
    WARN("[WARN]"),

    /**
     * Log level: [INFO]
     * Default log level, used to present important messages.
     */
    INFO("[INFO]"),

    /**
     * Log level: [DEBUG]
     * Fine amount of messages, used to track internal events and debug.
     */
    DEBUG("[DEBUG]"),

    /**
     * Log level: [TRACE]
     * Finest amount of messages, log everything possible.
     */
    TRACE("[TRACE]");

    public String message;

    LogLevel(String message) {
        this.message = message;
    }

    public boolean isOnMode() {
        return isSomeMode() || this == INFO;
    }

    public boolean isSomeMode() {
        return this == FETAL || this == ERROR || this == WARN;
    }

}
