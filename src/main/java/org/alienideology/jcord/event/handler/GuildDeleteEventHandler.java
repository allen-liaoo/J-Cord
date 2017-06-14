package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.Identity;
import org.alienideology.jcord.event.guild.GuildDeleteEvent;
import org.alienideology.jcord.event.guild.GuildUnavailableEvent;
import org.alienideology.jcord.internal.object.Guild;
import org.json.JSONObject;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class GuildDeleteEventHandler extends EventHandler {

    public GuildDeleteEventHandler(Identity identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        String id = json.getString("id");
        boolean unavailable = json.has("unavailable") && json.getBoolean("unavailable");

        if (unavailable) {
            Guild unavail = (Guild) identity.getGuild(id);
            fireEvent(new GuildUnavailableEvent(identity, unavail, sequence));
        } else {
            Guild deleted = null;
            for (IGuild guild : identity.getGuilds()) {
                if (guild.getId().equals(id)) {
                    deleted = (Guild) guild;
                    identity.getGuilds().remove(guild);
                    break;
                }
            }
            fireEvent(new GuildDeleteEvent(identity, deleted, sequence, OffsetDateTime.now()));
        }
    }
}
