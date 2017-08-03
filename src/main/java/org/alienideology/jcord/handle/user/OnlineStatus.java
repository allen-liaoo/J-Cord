package org.alienideology.jcord.handle.user;

/**
 * OnlineStatus - The online status of a user.
 *
 * @author AlienIdeology
 */
public enum OnlineStatus {

    ONLINE ("online"),
    IDLE ("idle"),
    DO_NOT_DISTURB ("dnd"),
    INVISIBLE ("invisible"),
    OFFLINE ("offline"),

    // The key will not be provided by Discord gateway.
    // This will counts as ONLINE.
    STREAMING ("streaming"),

    UNKNOWN ("unknown");

    public String key;

    OnlineStatus(String key) {
        this.key = key;
    }

    public String getAppropriateKey() {
        switch (this) {
            case OFFLINE:
            case UNKNOWN:
                return INVISIBLE.key;
            case STREAMING:
                return ONLINE.key;
        }
        return key;
    }

    public static OnlineStatus getByKey (String key) {
        for (OnlineStatus status : values()) {
            if (status.key.equals(key))
                return status;
        }
        return UNKNOWN;
    }

    public boolean isIdle() {
        return this.equals(IDLE) || this.equals(DO_NOT_DISTURB);
    }

    @Override
    public String toString() {
        return "OnlineStatus{" +
                "id='" + key + '\'' +
                '}';
    }
}
