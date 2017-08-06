package org.alienideology.jcord.event.channel.guild.voice;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.GuildChannelCreateEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class VoiceChannelCreateEvent extends GuildChannelCreateEvent implements IVoiceChannelEvent {

    public VoiceChannelCreateEvent(Identity identity, int sequence, IChannel channel, IGuild guild) {
        super(identity, sequence, channel, guild);
    }

    @Override
    public IVoiceChannel getVoiceChannel() {
        return (IVoiceChannel) getGuildChannel();
    }

}
