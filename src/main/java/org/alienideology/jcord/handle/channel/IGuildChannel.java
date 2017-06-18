package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * IGuildChannel - A Channel that exist in a guild
 * @author AlienIdeology
 */
public interface IGuildChannel extends IChannel, ISnowFlake {

    /**
     * Get the guild this channel belongs to.
     *
     * @return The guild
     */
    IGuild getGuild();

    /**
     * Get the manager that manages this GuildChannel.
     *
     * @return The channel manager.
     */
    IChannelManager getChannelManager();

    /**
     * Ge the name of this channel.
     *
     * @return The string name
     */
    String getName();

    /**
     * Get the position of this guild channel in a channel list.
     * The position of a channel can be calculated by counting down the channel list, starting from 1.
     *
     * @return The integer value of the position.
     */
    int getPosition();

}
