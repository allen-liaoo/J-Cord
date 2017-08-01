package org.alienideology.jcord.handle.client;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.user.IUser;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * IClient - Represents a Discord client.
 *
 * @author AlienIdeology
 */
public interface IClient extends IDiscordObject {

    /**
     * Get the profile (or self user) of this client.
     * Note that this will be a different instance from a self {@link IUser}.
     *
     * @return The profile.
     */
    IProfile getProfile();

    /**
     * Get the Discord client settings for this client.
     *
     * @return The settings.
     */
    IClientSetting getSetting();

    /**
     * Get a relationship by an user key.
     *
     * @param userId The user key.
     * @return The relationship, or null if no relationship is found.
     */
    @Nullable
    IRelationship getRelationship(String userId);

    /**
     * Get a list of all relationships that falls under a certain {@link IRelationship.Type}.
     *
     * @return The relationships.
     */
    List<IRelationship> getRelationships(IRelationship.Type type);

    /**
     * Get a list of all relationships.
     *
     * @return The relationships.
     */
    List<IRelationship> getRelationships();

    /**
     * Get a guild setting by guild ID.
     *
     * @param guildId the guild ID.
     * @return The guild setting, or null if no setting is found.
     */
    @Nullable
    IGuildSetting getGuildSetting(String guildId);

    /**
     * Get a list of muted guilds.
     *
     * @return The muted guilds.
     */
    default List<IGuildSetting> getMutedGuildSettings() {
        return getGuildSettings().stream().filter(IGuildSetting::isMuted).collect(Collectors.toList());
    }

    /**
     * Get a list of guild settings with the specified {@link MessageNotification}.
     *
     * @param notification The notification setting.
     * @return A list of guilds with that notification setting.
     */
    default List<IGuildSetting> getGuildSettingsWith(MessageNotification notification) {
        return getGuildSettings().stream().filter(gs -> gs.getNotificationSetting().equals(notification)).collect(Collectors.toList());
    }

    /**
     * Get a list of all guild settings.
     *
     * @return The guild settings.
     */
    List<IGuildSetting> getGuildSettings();

}