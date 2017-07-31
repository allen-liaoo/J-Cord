package org.alienideology.jcord.handle.audit;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.user.IUser;

import java.util.Collection;

/**
 * IAuditLog - An audit log that belongs to a guild. Used to get log entries.
 *
 * @author AlienIdeology
 */
public interface IAuditLog extends IDiscordObject {

    /**
     * Get the guild this audit log belongs to.
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Get the entries belong to this audit log.
     *
     * @return The entries.
     */
    Collection<ILogEntry> getEntries();

    /**
     * Get an log entry by ID.
     *
     * @param id The entry ID.
     * @return The log entry.
     */
    ILogEntry getEntry(String id);

    /**
     * Get entries by the log type.
     *
     * @param type The type of log.
     * @return A collection of log entries with the log type.
     */
    Collection<ILogEntry> getEntriesByType(LogType type);

    /**
     * Get entries by the same target ID.
     *
     * @param targetId The target's ID.
     * @return A collection of log entries with the same target ID.
     */
    Collection<ILogEntry> getEntriesByTarget(String targetId);

    /**
     * Get entries that are caused by the same user.
     *
     * @param user The user.
     * @return A collection of log entries that are caused by the same user.
     */
    Collection<ILogEntry> getEntriesByUser(IUser user);

}
