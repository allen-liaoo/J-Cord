package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.GuildDeleteEvent;
import org.alienideology.jcord.event.guild.GuildUnavailableEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.json.JSONObject;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class GuildDeleteEventHandler extends EventHandler {

    public GuildDeleteEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        String id = json.getString("id");
        boolean unavailable = json.has("unavailable") && json.getBoolean("unavailable");

        if (unavailable) {
            Guild unavail = (Guild) identity.getGuild(id);
            unavail.setUnavailable(true);
            fireEvent(new GuildUnavailableEvent(identity, unavail, sequence));
        } else {
            Guild deleted = (Guild) identity.getGuild(id);
            identity.removeGuild(id);
            fireEvent(new GuildDeleteEvent(identity, deleted, sequence, OffsetDateTime.now()));
        }
    }
}
