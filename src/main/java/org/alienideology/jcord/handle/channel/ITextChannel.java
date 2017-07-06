package org.alienideology.jcord.handle.channel;

import org.jetbrains.annotations.NotNull;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.user.IWebhook;

import java.util.List;

/**
 * TextChannel - An IGuildChannel for text messages.
 * @author AlienIdeology
 */
public interface ITextChannel extends IGuildChannel, IMessageChannel, IMention, Comparable<ITextChannel> {

    /**
     * Get the guild of this text channel.
     *
     * @return The guild, always not null.
     */
    @Override
    @NotNull
    IGuild getGuild();

    /**
     * Get the topic of this channel.
     *
     * @return The string topic message.
     */
    String getTopic();

    /**
     * Get a list of webhooks that can send messages to this channel.
     *
     * @return A list of webhooks.
     */
    List<IWebhook> getWebhooks();

    /**
     * @return True if this channel is the default channel of its guild.
     */
    default boolean isDefaultChannel() {
        return getId().equals(getGuild().getId());
    }

    /**
     * @return True if this channel is locked for NSFW (Not safe for work) contents.
     */
    default boolean isNSFWChannel() {
        return getName().startsWith("nsfw-") || getName().equals("nsfw");
    }

    @Override
    default String mention() {
        return "<#"+getId()+">";
    }

    /**
     * Compare this to another TextChannel by their integer positions.
     * @see IGuildChannel#getPosition()
     *
     * @param o The other TextChannel.
     * @return the value {@code 0} if the positions are the same;
     *         a value less than {@code 0} if this text channel's position is smaller than the other text channel; and
     *         a value greater than {@code 0} if this text channel's position is greater than the other text channel
     */
    @Override
    default int compareTo(ITextChannel o) {
        return Integer.compare(this.getPosition(), o.getPosition());
    }

}
