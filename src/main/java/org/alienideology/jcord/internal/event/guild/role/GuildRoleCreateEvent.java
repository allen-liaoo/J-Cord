package org.alienideology.jcord.internal.event.guild.role;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.event.guild.GuildEvent;
import org.alienideology.jcord.internal.object.Guild;

/**
 * GuildRoleCreatedEvent - Fired whenever a role is created
 * @author AlienIdeology
 */
public class GuildRoleCreateEvent extends GuildEvent {


    public GuildRoleCreateEvent(Identity identity, Guild guild, int sequence) {
        super(identity, guild, sequence);

    }

}
