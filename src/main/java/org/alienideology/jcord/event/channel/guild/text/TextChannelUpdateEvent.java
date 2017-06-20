package org.alienideology.jcord.event.channel.guild.text;

import org.alienideology.jcord.event.channel.guild.GuildChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class TextChannelUpdateEvent extends GuildChannelUpdateEvent implements ITextChannelEvent {

    // Duplicated fields for subclasses
    protected ITextChannel channel;
    protected ITextChannel oldChannel;

    public TextChannelUpdateEvent(IdentityImpl identity, int sequence, Channel channel, IGuildChannel oldChannel) {
        super(identity, sequence, channel, oldChannel);
        this.channel = (ITextChannel) channel;
        this.oldChannel = (ITextChannel) oldChannel;
    }

    /**
     * Get the new text channel.
     *
     * @return The updated text channel.
     */
    @Override
    public ITextChannel getTextChannel() {
        return channel;
    }

    public ITextChannel getOldTextChannel() {
        return oldChannel;
    }

}
