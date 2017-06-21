package org.alienideology.jcord.event.guild.role;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;

/**
 * @author AlienIdeology
 */
public class GuildRoleUpdateEvent extends GuildRoleEvent {

    public GuildRoleUpdateEvent(IdentityImpl identity, Guild guild, int sequence, Role role) {
        super(identity, guild, sequence, role);
    }

}
