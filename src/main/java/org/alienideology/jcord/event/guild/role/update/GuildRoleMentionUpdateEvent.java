package org.alienideology.jcord.event.guild.role.update;

import org.alienideology.jcord.event.guild.role.GuildRoleUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;

/**
 * @author AlienIdeology
 */
public class GuildRoleMentionUpdateEvent extends GuildRoleUpdateEvent {

    private boolean canMentionBefore;

    public GuildRoleMentionUpdateEvent(IdentityImpl identity, Guild guild, int sequence, Role role, boolean canMentionBefore) {
        super(identity, guild, sequence, role);
        this.canMentionBefore = canMentionBefore;
    }

    public boolean canMentionNow() {
        return role.canMention();
    }

    public boolean canMentionBefore() {
        return canMentionBefore;
    }
}
