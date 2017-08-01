package org.alienideology.jcord.handle.audit;

/**
 * LogType - Types of log entries.
 *
 * @author AlienIdeology
 */
public enum LogType {

    GUILD_UPDATE (1),

    CHANNEL_CREATE (10),
    CHANNEL_UPDATE (11),
    CHANNEL_DELETE (12),
    CHANNEL_OVERWRITE_CREATE (13),
    CHANNEL_OVERWRITE_UPDATE (14),
    CHANNEL_OVERWRITE_DELETE (15),

    MEMBER_KICK (20),
    MEMBER_PRUNE (21),
    MEMBER_BAN_ADD (22),
    MEMBER_BAN_REMOVE (23),
    MEMBER_UPDATE (24),

    MEMBER_ROLE_UPDATE (25),
    ROLE_CREATE	(30),
    ROLE_UPDATE	(31),
    ROLE_DELETE	(32),

    INVITE_CREATE (40),
    INVITE_UPDATE (41),
    INVITE_DELETE (42),

    WEBHOOK_CREATE (50),
    WEBHOOK_UPDATE (51),
    WEBHOOK_DELETE (52),

    EMOJI_CREATE (60),
    EMOJI_UPDATE (61),
    EMOJI_DELETE (62),

    MESSAGE_DELETE (72),
    UNKNOWN (-1);

    public final int key;

    LogType(int key) {
        this.key = key;
    }

    public static LogType getByKey(int key) {
        for (LogType type : values()) {
            if (type.key == key)
                return type;
        }
        return UNKNOWN;
    }

}
