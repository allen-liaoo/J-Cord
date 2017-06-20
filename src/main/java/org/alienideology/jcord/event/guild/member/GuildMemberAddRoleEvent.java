package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class GuildMemberAddRoleEvent extends GuildMemberEvent {

    private List<IRole> addedRoles;

    public GuildMemberAddRoleEvent(IdentityImpl identity, Guild guild, int sequence, Member member, List<IRole> addedRoles) {
        super(identity, guild, sequence, member);
        this.addedRoles = addedRoles;
    }

    public List<IRole> getAddedRoles() {
        return addedRoles;
    }

}
