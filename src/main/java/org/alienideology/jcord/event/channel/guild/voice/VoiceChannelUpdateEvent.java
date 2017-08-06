package org.alienideology.jcord.event.channel.guild.voice;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.GuildChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;

/**
 * @author AlienIdeology
 */
public class VoiceChannelUpdateEvent extends GuildChannelUpdateEvent implements IVoiceChannelEvent {

    // Duplicated fields for subclasses
    protected final IVoiceChannel channel;

    public VoiceChannelUpdateEvent(Identity identity, int sequence, IChannel channel) {
        super(identity, sequence, channel);
        this.channel = (IVoiceChannel) channel;
    }

    /**
     * Get the new voice channel.
     *
     * @return The updated voice channel.
     */
    @Override
    public IVoiceChannel getVoiceChannel() {
        return (IVoiceChannel) getGuildChannel();
    }

}
