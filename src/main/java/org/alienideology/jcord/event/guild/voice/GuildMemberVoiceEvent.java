package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildEvent;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IGuildVoiceState;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildMemberVoiceEvent extends GuildEvent {

    private final IMember member;

    public GuildMemberVoiceEvent(Identity identity, int sequence, IGuild guild, IMember member) {
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
