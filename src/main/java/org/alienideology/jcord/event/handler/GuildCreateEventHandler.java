package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildCreateEvent;
import org.alienideology.jcord.object.guild.Guild;
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

        Guild guild = builder.buildGuild(json);
        GuildCreateEvent created = new GuildCreateEvent(identity, guild, sequence);
        fireEvent(created);

    }

}
