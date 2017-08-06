package org.alienideology.jcord.event.channel.guild.text;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.GuildChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;

/**
 * @author AlienIdeology
 */
public class TextChannelUpdateEvent extends GuildChannelUpdateEvent implements ITextChannelEvent {

    // Duplicated fields for subclasses
    protected ITextChannel channel;

    public TextChannelUpdateEvent(Identity identity, int sequence, IChannel channel) {
        super(identity, sequence, channel);
        this.channel = (ITextChannel) channel;
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

}
