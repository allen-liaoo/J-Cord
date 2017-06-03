package org.alienideology.jcord.event.GuildEvent;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Guild;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GuildCreateEvent extends GuildEvent {

    public GuildCreateEvent(Identity identity) {
        super(identity);
    }

    @Override
    public void handleEvent(JSONObject raw) {
        guild = new Guild(identity, raw);
        identity.addGuild(guild);
    }
}
