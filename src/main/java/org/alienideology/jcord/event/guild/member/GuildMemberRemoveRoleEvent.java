package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class GuildMemberRemoveRoleEvent extends GuildMemberEvent {

    private List<IRole> removedRoles;

    public GuildMemberRemoveRoleEvent(IdentityImpl identity, Guild guild, int sequence, Member member, List<IRole> removedRoles) {
        super(identity, guild, sequence, member);
        this.removedRoles = removedRoles;
    }

    public List<IRole> getRemovedRoles() {
        return removedRoles;
    }

}
