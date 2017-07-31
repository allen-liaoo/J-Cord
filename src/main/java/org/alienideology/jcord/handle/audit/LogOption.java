package org.alienideology.jcord.handle.audit;

/**
 * LogOption - Optional information that are only visible for certain {@link LogType}.
 *
 * @author AlienIdeology
 */
public enum LogOption {

    DELETE_MEMBER_DAYS ("delete_member_days", LogType.MEMBER_PRUNE),

    MEMBERS_REMOVED ("members_removed",LogType.MEMBER_PRUNE),

    CHANNEL_ID ("channel_id", LogType.MESSAGE_DELETE),

    COUNT ("count", LogType.MESSAGE_DELETE),

    ID ("id", LogType.CHANNEL_OVERWRITE_CREATE, LogType.CHANNEL_OVERWRITE_UPDATE, LogType.CHANNEL_OVERWRITE_DELETE),

    TYPE ("type", LogType.CHANNEL_OVERWRITE_CREATE, LogType.CHANNEL_OVERWRITE_UPDATE, LogType.CHANNEL_OVERWRITE_DELETE),

    ROLE_NAME ("role_name", LogType.CHANNEL_OVERWRITE_CREATE, LogType.CHANNEL_OVERWRITE_UPDATE, LogType.CHANNEL_OVERWRITE_DELETE),

    UNKNOWN ("unknown", LogType.UNKNOWN);

    public final String key;
    public final LogType[] types;

    LogOption(String key, LogType... types) {
        this.key = key;
        this.types = types;
    }

    public static LogOption getByKey(String key) {
        for (LogOption type : values()) {
            if (type.key.equalsIgnoreCase(key))
                return type;
        }
        return UNKNOWN;
    }

}
