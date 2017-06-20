package org.alienideology.jcord.event.channel.guild;

import org.alienideology.jcord.event.channel.ChannelEvent;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class GuildChannelUpdateEvent extends ChannelEvent implements IGuildChannelEvent {

    private IGuildChannel oldChannel;

    public GuildChannelUpdateEvent(IdentityImpl identity, int sequence, Channel channel, IGuildChannel oldChannel) {
        super(identity, sequence, channel);
        this.oldChannel = oldChannel;
    }

    @Override
    public IGuild getGuild() {
        return oldChannel.getGuild();
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

    public IGuildChannel getOldChannel() {
        return oldChannel;
    }

}
