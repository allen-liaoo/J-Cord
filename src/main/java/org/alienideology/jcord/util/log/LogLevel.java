package org.alienideology.jcord.util.log;

/**
 * LogLevel - The logger level, used to indicate the log types.
 *
 * @see Logger
 * @see LogMode
 *
 * @author AlienIdeology
 */
public enum LogLevel {

    /**
     * Log level: [FETAL]
     * Used to indicate a internal api error.
     */
    FETAL ("[FETAL]"),

    /**
     * Log level: [ERROR]
     * Used to indicate an user end error.
     */
    ERROR ("[ERROR]"),

    /**
     * Log level: [WARN]
     * Used to warn the user, error related.
     */
    WARN ("[WARN]"),

    /**
     * Log level: [INFO]
     * Default log level, used to present important messages.
     */
    INFO ("[INFO]"),

    /**
     * Log level: [DEBUG]
     * Fine amount of messages, used to track internal events and debug.
     */
    DEBUG ("[DEBUG]"),

    /**
     * Log level: [TRACE]
     * Finest amount of messages, log everything possible.
     */
    TRACE ("[TRACE]");

    public String message;

    LogLevel(String message) {
        this.message = message;
    }

    /**
     * Check if this log level is logged with {@link LogMode#ON}.
     *
     * @return True if this level is logged with the mode.
     */
    public boolean isOnMode() {
        return isSomeMode() || this == INFO;
    }

    /**
     * Check if this log level is logged with {@link LogMode#SOME}.
     *
     * @return True if this level is logged with the mode.
     */
    public boolean isSomeMode() {
        return this == FETAL || this == ERROR || this == WARN;
    }

}
