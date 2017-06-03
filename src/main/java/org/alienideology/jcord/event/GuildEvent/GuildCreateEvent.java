package org.alienideology.jcord.event.GuildEvent;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Guild;
import org.json.JSONObject;

/**
 * This event is a general guild event.
 * Three ways this event is fired:
 *  - Initially connecting
 *  - Guild become available
 *  - User join new guild
 * Do not use this event for checking if the user join a new guild or not.
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
