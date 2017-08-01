package org.alienideology.jcord.handle.client.setting;

import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * IChannelSetting - A channel notification setting for clients.
 *
 * @author AlienIdeology
 */
public interface IChannelSetting extends IClientObject {

    /**
     * Get the guild of this channel setting belongs to.
     *
     * @return The guild.
     */
    default IGuild getGuild() {
        return getChannel().getGuild();
    }

    /**
     * Get the channel of this channel setting.
     *
     * @return The text channel.
     */
    ITextChannel getChannel();

    /**
     * Get the notification setting for this text channel.
     *
     * @return The notification setting.
     */
    MessageNotification getNotificationSetting();

    /**
     * Get if the channel is muted by the client.
     *
     * @return True if the channel is muted.
     */
    boolean isMuted();

}
