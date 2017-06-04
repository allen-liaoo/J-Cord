package org.alienideology.jcord.event.GuildEvent;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.ObjectBuilder;
import org.json.JSONObject;

/**
 * GuildCreateEvent - Fired whenever a guild is detected
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
        guild = new ObjectBuilder(identity).buildGuild(raw);
        identity.addGuild(guild);
    }
}
