package org.alienideology.jcord.handle.client.setting;

import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.guild.IGuild;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * IGuildSetting - A guild setting specifically for clients.
 *
 * @author AlienIdeology
 */
public interface IGuildSetting extends IClientObject {

    /**
     * Get the guild of this guild setting.
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Get a channel setting by ID.
     *
     * @param channelId The channel ID.
     * @return The channel setting, or null if no channel is found.
     */
    @Nullable
    IChannelSetting getChannelSetting(String channelId);

    /**
     * Get a list of muted channels.
     *
     * @return The muted channels.
     */
    default List<IChannelSetting> getMutedChannelSettings() {
        return getChannelSettings().stream().filter(IChannelSetting::isMuted).collect(Collectors.toList());
    }

    /**
     * Get a list of channel settings with the specified {@link MessageNotification}.
     *
     * @param notification The notification setting.
     * @return A list of channel with that notification setting.
     */
    default List<IChannelSetting> getChannelSettingsWith(MessageNotification notification) {
        return getChannelSettings().stream().filter(gs -> gs.getNotificationSetting().equals(notification)).collect(Collectors.toList());
    }

    /**
     * Get a list of channel settings.
     * If the channel is at default settings, then it will not be included in this list.
     * Default setting is not muted, and with {@link MessageNotification#ALL_MESSAGES} setting.
     *
     * @return A list of non default channel settings.
     */
    List<IChannelSetting> getChannelSettings();

    /**
     * Get the notification setting for this guild.
     *
     * @return The notification setting.
     */
    MessageNotification getNotificationSetting();

    /**
     * Get if the guild is muted by the client.
     *
     * @return True if the guild is muted by the client.
     */
    boolean isMuted();

    /**
     * Get if the client has mobile push enabled in this guild setting.
     *
     * @return True if the mobile push is enabled.
     */
    boolean isMobilePushEnabled();

    /**
     * Get if the client suppressed the {@code @everyone} mention.
     *
     * @return True if the {@code @everyone} mention is suppressed.
     */
    boolean isEveryoneMentionSuppressed();

}
