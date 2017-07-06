package org.alienideology.jcord.event.channel.guild.voice;

import org.alienideology.jcord.event.channel.guild.GuildChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class VoiceChannelUpdateEvent extends GuildChannelUpdateEvent implements IVoiceChannelEvent {

    // Duplicated fields for subclasses
    protected IVoiceChannel channel;
    protected IVoiceChannel oldChannel;

    public VoiceChannelUpdateEvent(IdentityImpl identity, int sequence, Channel channel, IGuildChannel oldChannel) {
        super(identity, sequence, channel, oldChannel);
        this.channel = (IVoiceChannel) channel;
        this.oldChannel = (IVoiceChannel) oldChannel;
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

    public IVoiceChannel getOldVoiceChannel() {
        return (IVoiceChannel) getOldChannel();
    }

}
