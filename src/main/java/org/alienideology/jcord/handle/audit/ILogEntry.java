package org.alienideology.jcord.handle.audit;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.user.IUser;

import java.util.Map;

/**
 * ILogEntry - An audit log entry, with information such as changes and options of an entry.
 *
 * @author AlienIdeology
 */
public interface ILogEntry extends ISnowFlake {

    /**
     * Get the log type of this audit log entry.
     *
     * @return The log type.
     */
    LogType getType();

    /**
     * Get the ID of the target this audit log entry refers to.
     *
     * @return The target ID.
     */
    String getTargetId();

    /**
     * Get the author of the log entry.
     *
     * @return The user.
     */
    IUser getUser();

    /**
     * Get changes made in this log entry.
     *
     * @return The changes.
     */
    Map<ChangeType, ILogChange> getChanges();

    /**
     * Get a {@link ILogChange} by the change type.
     *
     * @param type The change type.
     * @return The change, or null if no change is found.
     */
    default ILogChange getChange(ChangeType type) {
        return getChanges().get(type);
    }

    /**
     * Get the options that are attached to this log entry.
     *
     * @return The options.
     */
    Map<LogOption, String> getOptions();

    /**
     * Get the string reason of this audit log.
     *
     * @return The reason.
     */
    String getReason();

}
