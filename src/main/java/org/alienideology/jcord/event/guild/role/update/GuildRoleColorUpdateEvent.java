package org.alienideology.jcord.event.guild.role.update;

import org.alienideology.jcord.event.guild.role.GuildRoleUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;

import java.awt.*;

/**
 * @author AlienIdeology
 */
public class GuildRoleColorUpdateEvent extends GuildRoleUpdateEvent {

    private Color oldColor;

    public GuildRoleColorUpdateEvent(IdentityImpl identity, Guild guild, int sequence, Role role, Color oldColor) {
        super(identity, guild, sequence, role);
        this.oldColor = oldColor;
    }

    public Color getNewColor() {
        return role.getColor();
    }

    public Color getOldColor() {
        return oldColor;
    }

}
