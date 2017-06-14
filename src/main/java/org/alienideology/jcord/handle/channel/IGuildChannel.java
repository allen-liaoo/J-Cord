package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.internal.object.Guild;

/**
 * IGuildChannel - A Channel that exist in a guild
 * @author AlienIdeology
 */
public interface IGuildChannel {

    /**
     * Get the guild this channel belongs to.
     *
     * @return The guild
     */
    Guild getGuild();

    /**
     * Ge the name of this channel.
     *
     * @return The string name
     */
    String getName();

    /**
     * Get the position of this guild channel in a channel list.
     *
     * @return The integer value of the position.
     */
    int getPosition();

}
