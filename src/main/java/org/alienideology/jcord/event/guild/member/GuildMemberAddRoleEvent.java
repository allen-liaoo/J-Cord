package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class GuildMemberAddRoleEvent extends GuildMemberEvent {

    private List<IRole> addedRoles;

    public GuildMemberAddRoleEvent(Identity identity, IGuild guild, int sequence, IMember member, List<IRole> addedRoles) {
        super(identity, guild, sequence, member);
        this.addedRoles = addedRoles;
    }

    public List<IRole> getAddedRoles() {
        return addedRoles;
    }

}
