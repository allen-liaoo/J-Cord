package org.alienideology.jcord.event.channel.guild;

import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class VoiceChannelCreateEvent extends GuildChannelCreateEvent {

    public VoiceChannelCreateEvent(IdentityImpl identity, int sequence, Channel channel, Guild guild) {
        super(identity, sequence, channel, guild);
    }

    public IVoiceChannel getVoiceChannel() {
        return (IVoiceChannel) getGuildChannel();
    }

}
