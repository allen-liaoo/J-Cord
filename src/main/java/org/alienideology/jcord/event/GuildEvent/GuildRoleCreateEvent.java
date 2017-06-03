package org.alienideology.jcord.event.GuildEvent;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GuildRoleCreateEvent extends Event {

    //Role newRole = new Role();

    public GuildRoleCreateEvent(Identity identity) {
        super(identity);
    }

    @Override
    public void handleEvent(JSONObject raw) {
        System.out.println(raw.toString(4));
        String guild_id = raw.getString("guild_id");

        //newRole = new Role(id, id.getGuildById(guild_id), raw.getJSONObject("role"));
    }
}
