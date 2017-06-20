package org.alienideology.jcord.event.channel.guild.voice;

import org.alienideology.jcord.event.channel.guild.GuildChannelDeleteEvent;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.guild.Guild;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class VoiceChannelDeleteEvent extends GuildChannelDeleteEvent implements IVoiceChannelEvent {

    public VoiceChannelDeleteEvent(IdentityImpl identity, int sequence, Channel channel, OffsetDateTime timeStamp, Guild guild) {
        super(identity, sequence, channel, timeStamp, guild);
    }

    @Override
    public IVoiceChannel getVoiceChannel() {
        return (IVoiceChannel) getGuildChannel();
    }

}
