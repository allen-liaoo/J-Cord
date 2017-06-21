package org.alienideology.jcord.event.guild.role;

import org.alienideology.jcord.event.guild.GuildEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;

/**
 * @author AlienIdeology
 */
public class GuildRoleEvent extends GuildEvent {

    protected Role role;

    public GuildRoleEvent(IdentityImpl identity, Guild guild, int sequence, Role role) {
        super(identity, guild, sequence);
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

}
