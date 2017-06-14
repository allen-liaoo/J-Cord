package org.alienideology.jcord.handle;

import java.util.ArrayList;
import java.util.List;

import static org.alienideology.jcord.handle.Permission.PermissionLevel.*;

/**
 * Permission - A way to limit and grant certain abilities to members.
 * @author AlienIdeology
 */
public enum Permission {

    /* Guild Permissions */
    CREATE_INSTANT_INVITE (0x00000001, "Allows creation of instant invites", ALL),
    KICK_MEMBERS (0x00000002, "Allows kicking members", GUILD_ONLY),
    BAN_MEMBERS (0x00000004, "Allows banning members", GUILD_ONLY),
    ADMINISTRATOR (0x00000008, "Allows all permissions and bypasses channel permission overwrites", GUILD_ONLY),
    MANAGE_CHANNELS (0x00000010, "Allows management and editing of channels", ALL),
    MANAGE_SERVER (0x00000020, "Allows management and editing of the guild", GUILD_ONLY),
    ADD_REACTIONS (0x00000040, "Allows for the addition of reactions to messages", ALL),
    VIEW_AUDIT_LOGS (0x00000080, "Allows for viewing the audit logs of the guild", GUILD_ONLY),

    /* TextChannel Permissions */
    READ_MESSAGES (0x00000400, "Allows reading messages in a channel. The channel will not appear for users without this permission", ALL),
    SEND_MESSAGES (0x00000800, "Allows for sending messages in a channel", ALL),
    SEND_TTS_MESSAGES (0x00001000, "Allows for sending of /tts messages", ALL),
    MANAGE_MESSAGES (0x00002000, "Allows for deletion of other users messages", ALL),
    EMBED_LINKS (0x00004000, "Links sent by this user will be auto-embedded", ALL),
    ATTACH_FILES (0x00008000, "Allows for uploading images and files", ALL),
    READ_MESSAGE_HISTORY (0x00010000, "Allows for reading of message history", ALL),
    MENTION_EVERYONE (0x00020000, "Allows for using the @everyone tag to notify all users in a channel, and the @here tag to notify all online users in a channel", ALL),
    USE_EXTERNAL_EMOJIS (0x00040000, "Allows the usage of custom emojis from other servers", ALL),

    /* VoiceChannel Permissions */
    CONNECT (0x00100000, "Allows for joining of a voice channel", ALL),
    SPEAK (0x00200000, "Allows for speaking in a voice channel", ALL),
    MUTE_MEMBERS (0x00400000, "Allows for muting members in a voice channel", ALL),
    DEAFEN_MEMBERS (0x00800000, "Allows for deafening of members in a voice channel", ALL),
    MOVE_MEMBERS (0x01000000, "Allows for moving of members between voice channels", ALL),
    USE_VOICE_ACTIVITY (0x02000000, "Allows for using voice-activity-detection in a voice channel", ALL),

    CHANGE_NICKNAME (0x04000000, "Allows for modification of own nickname", GUILD_ONLY),
    MANAGE_NICKNAMES (0x08000000, "Allows for modification of other users nicknames", GUILD_ONLY),
    MANAGE_ROLES (0x10000000, "Allows management and editing of roles", GUILD_ONLY),
    MANAGE_WEBHOOKS (0x20000000, "Allows management and editing of webhooks", ALL),
    MANAGE_EMOJIS (0x40000000, "Allows management and editing of emojis", GUILD_ONLY),
    MANAGE_PERMISSIONS (0x80000000, "Allows management and editing permissions of a role in a specific channel", CHANNEL_ONLY),

    UNKNOWN (-1, "Unknown permission", UNKNOWN_LEVEL);

    private long value;
    private String description;
    private PermissionLevel level;

    Permission (long value, String description, PermissionLevel level) {
        this.value = value;
        this.description = description;
        this.level = level;
    }

    public static boolean hasPermission (long permissions, Permission permission) {
        return (permissions & permission.value) != 0;
    }

    public static List<Permission> getPermissionsByLong (long permissionsLong) {
        List<Permission> permissions = new ArrayList<>();

        for (Permission perm : Permission.values()) {
            if (hasPermission(permissionsLong, perm)) {
                permissions.add(perm);
            }
        }
        return permissions;
    }

    public static long getLongByPermissions (Permission... permissions) {
        long permLong = 0;
        for (Permission perm : permissions) {
            permLong |= perm.value;
        }
        return permLong;
    }

    public boolean isAllLevel() {
        return this.level == ALL;
    }

    public boolean isGuildLevel() {
        return this.level == ALL || this.level == GUILD_ONLY;
    }

    public boolean isChannelLevel() {
        return this.level == ALL || this.level == CHANNEL_ONLY;
    }

    @Override
    public String toString() {
        return name().substring(0,1)+name().substring(1).toLowerCase();
    }

    enum PermissionLevel {
        ALL,
        GUILD_ONLY,
        CHANNEL_ONLY,
        UNKNOWN_LEVEL;
    }

}
