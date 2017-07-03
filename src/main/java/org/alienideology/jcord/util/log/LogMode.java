package org.alienideology.jcord.util.log;

/**
 * LogMode - The logger mode, used to filter logs.
 *
 * @author AlienIdeology
 */
public enum LogMode {
    /**
     * Log all messages, down to {@link LogLevel#TRACE} level.
     */
    ALL,

    /**
     * Log important messages, down to {@link LogLevel#INFO} level.
     */
    ON,

    /**
     * Log only error messages, down to {@link LogLevel#WARN} level.
     */
    SOME,

    /**
     * Log no messages
     */
    OFF
}
