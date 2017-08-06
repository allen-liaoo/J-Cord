package org.alienideology.jcord.event.channel.guild.voice;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.GuildChannelDeleteEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class VoiceChannelDeleteEvent extends GuildChannelDeleteEvent implements IVoiceChannelEvent {

    public VoiceChannelDeleteEvent(Identity identity, int sequence, IChannel channel, OffsetDateTime timeStamp, IGuild guild) {
        super(identity, sequence, channel, timeStamp, guild);
    }

    @Override
    public IVoiceChannel getVoiceChannel() {
        return (IVoiceChannel) getGuildChannel();
    }

}
