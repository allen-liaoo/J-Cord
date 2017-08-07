package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.update.GuildIntegrationsUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.util.log.LogLevel;
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
        IGuild guild = identity.getGuild(json.getString("guild_id"));
        if (guild == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN GUILD] [GUILD_INTEGRATIONS_UPDATE_EVENT] ID: " + json.getString("guild_id"));
            return;
        }

        dispatchEvent(new GuildIntegrationsUpdateEvent(identity, sequence, guild));
    }

}
