package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.role.GuildRoleDeleteEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GuildRoleDeleteEventHandler extends EventHandler {

    public GuildRoleDeleteEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Guild guild = (Guild) identity.getGuild(json.getString("guild_id"));
        Role role = (Role) guild.getRole(json.getJSONObject("role").getString("id"));
        guild.removeRole(role.getId());

        fireEvent(new GuildRoleDeleteEvent(identity, guild, sequence, role));
    }

}
