package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

/**
 * @author AlienIdeology
 */
public class GuildMemberJoinVoiceEvent extends GuildMemberVoiceEvent {

    public GuildMemberJoinVoiceEvent(IdentityImpl identity, int sequence, Guild guild, Member member) {
        super(identity, sequence, guild, member);
    }

    public IVoiceChannel getChannelJoined() {
        return getVoiceState().getVoiceChannel();
    }

}
