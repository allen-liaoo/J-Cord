package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildMemberNicknameUpdateEvent extends GuildMemberEvent {

    private String oldNickname;

    public GuildMemberNicknameUpdateEvent(Identity identity, IGuild guild, int sequence, IMember member, String oldNickname) {
        super(identity, guild, sequence, member);
        this.oldNickname = oldNickname;
    }

    public String getNewNickname() {
        return member.getNickname();
    }

    public String getOldNickname() {
        return oldNickname;
    }

}
