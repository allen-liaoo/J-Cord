package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

/**
 * @author AlienIdeology
 */
public class GuildMemberMuteByServerEvent extends GuildMemberVoiceEvent {

    public GuildMemberMuteByServerEvent(IdentityImpl identity, int sequence, Guild guild, Member member) {
        super(identity, sequence, guild, member);
    }

    public boolean isMutedByServer() {
        return getVoiceState().isMutedByServer();
    }

}
