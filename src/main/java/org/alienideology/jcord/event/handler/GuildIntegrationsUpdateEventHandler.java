package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.update.GuildIntegrationsUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GuildIntegrationsUpdateEventHandler extends EventHandler {

    public GuildIntegrationsUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Guild guild = (Guild) identity.getGuild(json.getString("guild_id"));

        dispatchEvent(new GuildIntegrationsUpdateEvent(identity, sequence, guild));
    }

}
