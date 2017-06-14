package org.alienideology.jcord.handle.channel;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * TextChannel - An IGuildChannel for text messages.
 * @author AlienIdeology
 */
public interface ITextChannel extends IMessageChannel, IGuildChannel, IMention {

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

}
