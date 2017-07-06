package org.alienideology.jcord.event.guild.role.update;

import org.alienideology.jcord.event.guild.role.GuildRoleUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;

/**
 * @author AlienIdeology
 */
public class GuildRoleSeparateListedUpdateEvent extends GuildRoleUpdateEvent {

    private boolean isSeparateListed;

    public GuildRoleSeparateListedUpdateEvent(IdentityImpl identity, Guild guild, int sequence, Role role, boolean isSeparateListed) {
        super(identity, guild, sequence, role);
        this.isSeparateListed = isSeparateListed;
    }

    public boolean isSeparateListedNow() {
        return role.isSeparateListed();
    }

    public boolean isSeparateListedBefore() {
        return isSeparateListed;
    }

}
