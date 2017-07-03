package org.alienideology.jcord.util.log;

/**
 * LogMode - The logger mode, used to filter logs for the {@link Logger}.
 *
 * @see Logger
 * @see LogLevel
 *
 * @author AlienIdeology
 */
public enum LogMode {
    /**
     * Includes logs from all {@link LogLevel}.
     */
    ALL,

    /**
     * Includes important logs, from {@link LogLevel#FETAL} to {@link LogLevel#INFO}.
     */
    ON,

    /**
     * Only includes error logs, from {@link LogLevel#FETAL} to {@link LogLevel#WARN}.
     */
    SOME,

    /**
     * Does not includes any log.
     */
    OFF
}
