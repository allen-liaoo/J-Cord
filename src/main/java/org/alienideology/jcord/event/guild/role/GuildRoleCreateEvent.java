package org.alienideology.jcord.event.guild.role;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;

/**
 * @author AlienIdeology
 */
public class GuildRoleCreateEvent extends GuildRoleEvent {

    public GuildRoleCreateEvent(IdentityImpl identity, Guild guild, int sequence, Role role) {
        super(identity, guild, sequence, role);
    }

}
