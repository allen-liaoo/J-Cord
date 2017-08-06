package org.alienideology.jcord.event.channel.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.ChannelEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildChannelUpdateEvent extends ChannelEvent implements IGuildChannelEvent {

    public GuildChannelUpdateEvent(Identity identity, int sequence, IChannel channel) {
        super(identity, sequence, channel);
    }

    @Override
    public IGuild getGuild() {
        return ((IGuildChannel) channel).getGuild();
    }

    /**
     * Get the new guild channel.
     *
     * @return The updated guild channel.
     */
    @Override
    public IGuildChannel getGuildChannel() {
        return (IGuildChannel) channel;
    }

}
