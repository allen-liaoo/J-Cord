package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.GuildCreateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.user.User;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GuildCreateEventHandler extends EventHandler {

    public GuildCreateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {

        Guild guild = (Guild) identity.getGuild(json.getString("id"));

        // Ignore initial guild create event
        if (guild != null) {
            // Do not get guild members or channels object, since they are requested using http request.
            // Build presences
            if (json.has("presences")) {
                JSONArray presences = json.getJSONArray("presences");

                for (int i = 0; i < presences.length(); i++) {
                    JSONObject presence = presences.getJSONObject(i);
                    builder.buildPresence(presence, (User) identity.getUser(presence.getJSONObject("user").getString("id")));  // Presences are set automatically
                }
            }

        } else {
            Guild newGuild = builder.buildGuild(json);
            dispatchEvent(new GuildCreateEvent(identity, newGuild, sequence));
        }

    }

}
