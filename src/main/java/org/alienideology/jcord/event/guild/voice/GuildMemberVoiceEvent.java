package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.event.guild.GuildEvent;
import org.alienideology.jcord.handle.guild.IGuildVoiceState;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

/**
 * @author AlienIdeology
 */
public class GuildMemberVoiceEvent extends GuildEvent {

    private final Member member;

    public GuildMemberVoiceEvent(IdentityImpl identity, int sequence, Guild guild, Member member) {
        super(identity, sequence, guild);
        this.member = member;
    }

    public IMember getMember() {
        return member;
    }

    public IGuildVoiceState getVoiceState() {
        return member.getVoiceState();
    }

}
