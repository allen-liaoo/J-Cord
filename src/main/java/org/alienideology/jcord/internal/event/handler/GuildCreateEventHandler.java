package org.alienideology.jcord.internal.event.handler;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.event.guild.GuildCreateEvent;
import org.alienideology.jcord.internal.object.Guild;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GuildCreateEventHandler extends EventHandler {

    public GuildCreateEventHandler(Identity identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {

        // Ignore initial guild create event
        if (identity.getGuild(json.getString("id")) != null) {
        } else {
            Guild guild = builder.buildGuild(json);
            GuildCreateEvent created = new GuildCreateEvent(identity, guild, sequence);
            fireEvent(created);
        }

    }

}
