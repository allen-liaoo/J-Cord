package org.alienideology.jcord.event.channel.guild.voice;

import org.alienideology.jcord.event.channel.guild.GuildChannelCreateEvent;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class VoiceChannelCreateEvent extends GuildChannelCreateEvent implements IVoiceChannelEvent {

    public VoiceChannelCreateEvent(IdentityImpl identity, int sequence, Channel channel, Guild guild) {
        super(identity, sequence, channel, guild);
    }

    @Override
    public IVoiceChannel getVoiceChannel() {
        return (IVoiceChannel) getGuildChannel();
    }

}
