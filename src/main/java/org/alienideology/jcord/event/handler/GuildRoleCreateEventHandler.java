package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.role.GuildRoleCreateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GuildRoleCreateEventHandler extends EventHandler {

    public GuildRoleCreateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Guild guild = (Guild) identity.getGuild(json.getString("guild_id"));
        Role role = builder.buildRole(json.getJSONObject("role"), guild);
        guild.addRole(role);

        dispatchEvent(new GuildRoleCreateEvent(identity, guild, sequence, role));
    }

}
