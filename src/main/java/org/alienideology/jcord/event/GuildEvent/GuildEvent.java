package org.alienideology.jcord.event.GuildEvent;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.object.Guild;
import org.json.JSONObject;

/**
 * General Guild Events
 * @author AlienIdeology
 */
public abstract class GuildEvent extends Event {

    protected Guild guild;

    public GuildEvent(Identity identity) {
        super(identity);
    }

    public Guild getGuild() {
        return guild;
    }

    @Override
    public abstract void handleEvent(JSONObject raw);
}
