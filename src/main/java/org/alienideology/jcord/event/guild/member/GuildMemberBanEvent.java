package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

/**
 * @author AlienIdeology
 */
public class GuildMemberBanEvent extends GuildMemberEvent {

    private Member bannedMember;

    public GuildMemberBanEvent(IdentityImpl identity, Guild guild, int sequence, Member bannedMember) {
        super(identity, guild, sequence, bannedMember);
        this.bannedMember = bannedMember;
    }

    public Member getBannedMember() {
        return bannedMember;
    }

    public IUser getBannedUser() {
        return bannedMember.getUser();
    }

}
