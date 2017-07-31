package org.alienideology.jcord.handle.audit;

import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.permission.PermOverwrite;

import java.util.Objects;

/**
 * ChangeType - Types of changes for a {@link ILogChange}. The generic field of any enum is the expected value type for both
 * {@link ILogChange#getNewValue()} and {@link ILogChange#getOldValue()}.
 *
 * @author AlienIdeology
 */
public enum ChangeType {

    /* Any */
    ID ("id", String.class),
    TYPE_STRING ("type", String.class),
    TYPE_INT ("type", Integer.class),

    /* Guild */
    GUILD_NAME ("name", String.class),
    GUILD_ICON ("icon_hash", String.class),
    GUILD_SPLASH ("splash_hash", String.class),
    GUILD_OWNER ("owner_id", String.class),
    GUILD_REGION ("region", String.class),

    GUILD_AFK_CHANNEL ("afk_channel_id", String.class),
    GUILD_AFK_TIMEOUT ("afk_timeout", String.class),
    GUILD_MFA_LEVEL ("mfa_level", Integer.class),
    GUILD_VERIFICATION_LEVEL ("verification_level", Integer.class),
    GUILD_EXPLICIT_CONTENT_FILTER ("explicit_content_filter", Integer.class),
    GUILD_NOTIFICATION_LEVEL ("default_message_notifications", Integer.class),
    GUILD_URL_CODE ("vanity_url_code", String.class),

    GUILD_ROLE_ADDED ("$add", IRole[].class),
    GUILD_ROLE_REMOVED ("$remove", IRole[].class),

    GUILD_PRUNE_DAYS ("prune_delete_days", Integer.class),
    GUILD_WIDGET_ENABLED ("widget_enabled", Boolean.class),
    GUILD_WIDGET_CHANNEL ("widget_channel_id", String.class),

    /* Channel */
    CHANNEL_POSITION ("position", Integer.class),
    CHANNEL_TOPIC ("topic", String.class),
    CHANNEL_BITRATE ("bitrate", Integer.class),
    CHANNEL_PERMISSION_OVERWRITES ("permission_overwrites", PermOverwrite[].class),
    CHANNEL_NSFW ("nsfw", Boolean.class),
    CHANNEL_APPLICATION_ID ("application_id", String.class),

    /* Role */
    ROLE_PERMISSIONS ("permissions", Long.class),
    ROLE_COLOR ("color", Integer.class),
    ROLE_HOIST ("hoist", Boolean.class),
    ROLE_MENTIONABLE ("mentionable", Boolean.class),
    ROLE_ALLOW ("allow", Long.class),
    ROLE_DENY ("deny", Long.class),

    /* Invite */
    INVITE_CODE ("code", String.class),
    INVITE_CHANNEL ("channel_id", String.class),
    INVITE_INVITER ("inviter_id", String.class),
    INVITE_MAX_USES ("max_uses", Integer.class),
    INVITE_USES ("uses", Integer.class),
    INVITE_MAX_AGE ("max_age", Long.class),
    INVITE_TEMPORARY ("temporary", Boolean.class),

    /* User */
    USER_DEAF ("deaf", Boolean.class),
    USER_MUTE ("mute", Boolean.class),
    USER_NICK ("nick", String.class),
    USER_AVATAR ("avatar_hash", String.class),

    UNKNOWN ("unknown", Void.class);

    public final String key;
    public final Class generic;

    ChangeType(String key, Class generic) {
        this.key = key;
        this.generic = generic;
    }

    public static ChangeType getByKey(String key) {
        for (ChangeType type : values()) {
            if (Objects.equals(type.key, key))
                return type;
        }
        return UNKNOWN;
    }

}
