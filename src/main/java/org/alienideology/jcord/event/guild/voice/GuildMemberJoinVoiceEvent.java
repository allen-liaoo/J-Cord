package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildMemberJoinVoiceEvent extends GuildMemberVoiceEvent {

    public GuildMemberJoinVoiceEvent(Identity identity, int sequence, IGuild guild, IMember member) {
        super(identity, sequence, guild, member);
    }

    public IVoiceChannel getChannelJoined() {
        return getVoiceState().getVoiceChannel();
    }

}
