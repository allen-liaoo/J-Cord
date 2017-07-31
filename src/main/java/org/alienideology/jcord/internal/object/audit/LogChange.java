package org.alienideology.jcord.internal.object.audit;

import org.alienideology.jcord.handle.audit.ChangeType;
import org.alienideology.jcord.handle.audit.ILogChange;

/**
 * @author AlienIdeology
 */
public final class LogChange implements ILogChange {

    private final ChangeType type;
    private final Object newValue;
    private final Object oldValue;

    public LogChange(ChangeType type, Object newValue, Object oldValue) {
        this.type = type;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }

    @Override
    public ChangeType getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getNewValue() {
        return (T) newValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOldValue() {
        return (T) oldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogChange)) return false;

        LogChange logChange = (LogChange) o;

        if (type != logChange.type) return false;
        if (newValue != null ? !newValue.equals(logChange.newValue) : logChange.newValue != null) return false;
        return oldValue != null ? oldValue.equals(logChange.oldValue) : logChange.oldValue == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (newValue != null ? newValue.hashCode() : 0);
        result = 31 * result + (oldValue != null ? oldValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LogChange{" +
                "type=" + type +
                ", newValue=" + newValue +
                ", oldValue=" + oldValue +
                '}';
    }
}
