package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.GuildDeleteEvent;
import org.alienideology.jcord.event.guild.GuildUnavailableEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.util.log.LogLevel;
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

        Guild guild = (Guild) identity.getGuild(id);
        if (guild == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN GUILD] [GUILD_DELETE_EVENT]");
            return;
        }

        if (unavailable) {
            guild.setUnavailable(true);
            dispatchEvent(new GuildUnavailableEvent(identity, sequence, guild));
        } else {
            identity.removeGuild(id);
            dispatchEvent(new GuildDeleteEvent(identity, sequence, guild, OffsetDateTime.now()));
        }
    }
}
