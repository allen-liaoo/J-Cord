package org.alienideology.jcord.internal.object.audit;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.audit.IAuditLog;
import org.alienideology.jcord.handle.audit.ILogEntry;
import org.alienideology.jcord.handle.audit.LogType;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.user.IUser;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlienIdeology
 */
public final class AuditLog implements IAuditLog {

    private IGuild guild;
    private List<ILogEntry> entries;

    public AuditLog(IGuild guild, List<ILogEntry> entries) {
        this.guild = guild;
        this.entries = entries;
    }

    @Override
    public Identity getIdentity() {
        return guild.getIdentity();
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public List<ILogEntry> getEntries() {
        return entries;
    }

    @Override
    public ILogEntry getEntry(String id) {
        return entries.stream().filter(e -> e.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public Collection<ILogEntry> getEntriesByType(LogType type) {
        return entries.stream().filter(e -> e.getType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public Collection<ILogEntry> getEntriesByTarget(String id) {
        return entries.stream().filter(e -> e.getTargetId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public Collection<ILogEntry> getEntriesByUser(IUser user) {
        return entries.stream().filter(e -> e.getUser().equals(user)).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditLog)) return false;

        AuditLog auditLog = (AuditLog) o;

        if (!guild.equals(auditLog.guild)) return false;
        return entries.equals(auditLog.entries);
    }

    @Override
    public int hashCode() {
        int result = guild.hashCode();
        result = 31 * result + entries.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "guild=" + guild +
                ", entries=" + entries +
                '}';
    }
}
