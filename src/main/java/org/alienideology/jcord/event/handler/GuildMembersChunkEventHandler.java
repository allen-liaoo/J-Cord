package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GuildMembersChunkEventHandler extends EventHandler {

    public GuildMembersChunkEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Guild guild = (Guild) identity.getGuild(json.getString("guild_id"));
        if (guild == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN GUILD][GUILD_MEMBERS_CHUNK_EVENT]");
            return;
        }

        JSONArray members = json.getJSONArray("members");
        logger.log(LogLevel.DEBUG, "Received members chunk: " + members.length() + " for guild: " + guild);
        for (int i = 0; i < members.length(); i++) {
            JSONObject member = members.getJSONObject(i);
            if (guild.getMember(member.getJSONObject("user").getString("id")) == null)
                guild.addMember(builder.buildMember(member, guild));
        }
    }

}
