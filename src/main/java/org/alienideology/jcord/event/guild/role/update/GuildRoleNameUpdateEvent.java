package org.alienideology.jcord.event.guild.role.update;

import org.alienideology.jcord.event.guild.role.GuildRoleUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;

/**
 * @author AlienIdeology
 */
public class GuildRoleNameUpdateEvent extends GuildRoleUpdateEvent {

    private String oldName;

    public GuildRoleNameUpdateEvent(IdentityImpl identity, Guild guild, int sequence, Role role, String oldName) {
        super(identity, guild, sequence, role);
        this.oldName = oldName;
    }

    public String getNewName() {
        return role.getName();
    }

    public String getOldName() {
        return oldName;
    }

}
