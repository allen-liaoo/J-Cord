package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Guild;

/**
 * GuildRoleCreatedEvent - Fired whenever a role is created
 * @author AlienIdeology
 */
public class GuildRoleCreateEvent extends GuildEvent {

//    Role role = new Role();

    public GuildRoleCreateEvent(Identity identity, Guild guild, int sequence) {
        super(identity, guild, sequence);
//        this.role = role;
    }

}
