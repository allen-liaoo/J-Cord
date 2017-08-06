package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class GuildMemberRemoveRoleEvent extends GuildMemberEvent {

    private List<IRole> removedRoles;

    public GuildMemberRemoveRoleEvent(Identity identity, IGuild guild, int sequence, IMember member, List<IRole> removedRoles) {
        super(identity, guild, sequence, member);
        this.removedRoles = removedRoles;
    }

    public List<IRole> getRemovedRoles() {
        return removedRoles;
    }

}
