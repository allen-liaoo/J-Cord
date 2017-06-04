package org.alienideology.jcord.event.GuildEvent;

import org.alienideology.jcord.Identity;
import org.json.JSONObject;

/**
 * GuildRoleCreatedEvent - Fired whenever a role is created
 * @author AlienIdeology
 */
public class GuildRoleCreateEvent extends GuildEvent {

    //Role newRole = new Role();

    public GuildRoleCreateEvent(Identity identity) {
        super(identity);
    }

    @Override
    public void handleEvent(JSONObject raw) {
        System.out.println(raw.toString(4));
        String guild_id = raw.getString("guild_id");
        //guild = identity.getGuild(guild_id);
        //newRole = new Role(id, id.getGuildById(guild_id), raw.getJSONObject("role"));
    }
}
