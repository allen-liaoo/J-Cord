package org.alienideology.jcord.handle.audit;

/**
 * ILogChange - A change to a certain value. An audit log entry can contain multiple changes.
 *
 * @author AlienIdeology
 */
public interface ILogChange {

    /**
     * Get the log change type.
     *
     * @return The change type.
     */
    ChangeType getType();

    /**
     * Get the new value that replaced the old value.
     *
     * @return The new value.
     */
    <T> T getNewValue();

    /**
     * Get the old value that was replaced by the new value.
     *
     * @return The old value.
     */
    <T> T getOldValue();

}
