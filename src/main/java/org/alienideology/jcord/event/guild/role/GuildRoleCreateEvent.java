package org.alienideology.jcord.event.guild.role;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.guild.GuildEvent;
import org.alienideology.jcord.internal.object.Guild;

/**
 * GuildRoleCreatedEvent - Fired whenever a role is created
 * @author AlienIdeology
 */
public class GuildRoleCreateEvent extends GuildEvent {


    public GuildRoleCreateEvent(IdentityImpl identity, Guild guild, int sequence) {
        super(identity, guild, sequence);

    }

}
