package org.alienideology.jcord.internal.object.user;

/**
 * @author AlienIdeology
 */
public enum Status {

    ONLINE ("online"),
    IDLE ("idle"),
    DO_NOT_DISTURB ("dnd"),
    OFFLINE ("offline"),
    UNKNOWN ("unknown");

    private String key;

    Status(String key) {
        this.key = key;
    }

    public static Status getByKey (String key) {
        for (Status status : values()) {
            if (status.key.equals(key))
                return status;
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return "Status{" +
                "key='" + key + '\'' +
                '}';
    }
}
