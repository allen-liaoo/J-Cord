package org.alienideology.jcord.event.guild.role.update;

import org.alienideology.jcord.event.guild.role.GuildRoleUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;

/**
 * @author AlienIdeology
 */
public class GuildRolePositionUpdateEvent extends GuildRoleUpdateEvent {

    private int oldPosition;

    public GuildRolePositionUpdateEvent(IdentityImpl identity, Guild guild, int sequence, Role role, int oldPosition) {
        super(identity, guild, sequence, role);
        this.oldPosition = oldPosition;
    }

    public int getNewPosition() {
        return role.getPosition();
    }

    public int getOldPosition() {
        return oldPosition;
    }

    public boolean movedUp() {
        return getNewPosition() < getOldPosition();
    }

}
