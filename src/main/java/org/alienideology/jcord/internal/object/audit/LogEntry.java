package org.alienideology.jcord.internal.object.audit;

import org.alienideology.jcord.handle.audit.*;
import org.alienideology.jcord.internal.object.user.User;

import java.util.Map;

/**
 * @author AlienIdeology
 */
public final class LogEntry implements ILogEntry {

    private final LogType type;

    private final String id;
    private final String targetId;
    private User user;

    private Map<ChangeType, ILogChange> changes;
    private Map<LogOption, String> options;
    private String reason;

    public LogEntry(LogType type, String id, String targetId, User user, String reason) {
        this.type = type;
        this.id = id;
        this.targetId = targetId;
        this.user = user;
        this.reason = reason;
    }

    @Override
    public LogType getType() {
        return type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTargetId() {
        return targetId;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Map<ChangeType, ILogChange> getChanges() {
        return changes;
    }

    @Override
    public Map<LogOption, String> getOptions() {
        return options;
    }

    @Override
    public String getReason() {
        return reason;
    }


    //---------------Internal---------------
    public void setChanges(Map<ChangeType, ILogChange> changes) {
        this.changes = changes;
    }

    public void setOptions(Map<LogOption, String> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogEntry)) return false;

        LogEntry logEntry = (LogEntry) o;

        if (!id.equals(logEntry.id)) return false;
        return targetId.equals(logEntry.targetId);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + targetId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "type=" + type +
                ", id='" + id + '\'' +
                ", targetId='" + targetId + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }

}
