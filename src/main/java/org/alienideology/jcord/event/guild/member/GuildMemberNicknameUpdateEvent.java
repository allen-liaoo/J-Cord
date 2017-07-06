package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

/**
 * @author AlienIdeology
 */
public class GuildMemberNicknameUpdateEvent extends GuildMemberEvent {

    private String oldNickname;

    public GuildMemberNicknameUpdateEvent(IdentityImpl identity, Guild guild, int sequence, Member member, String oldNickname) {
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
