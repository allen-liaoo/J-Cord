package org.alienideology.jcord.event.guild.role;

import org.alienideology.jcord.internal.object.Identity;
import org.alienideology.jcord.event.guild.GuildEvent;
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
