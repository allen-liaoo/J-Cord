package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.user.IUser;

/**
 * @author AlienIdeology
 */
public class GuildMemberBanEvent extends GuildMemberEvent {

    private IMember bannedMember;

    public GuildMemberBanEvent(Identity identity, IGuild guild, int sequence, IMember bannedMember) {
        super(identity, guild, sequence, bannedMember);
        this.bannedMember = bannedMember;
    }

    public IMember getBannedMember() {
        return bannedMember;
    }

    public IUser getBannedUser() {
        return bannedMember.getUser();
    }

}
