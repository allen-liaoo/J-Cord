package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.member.GuildMemberLeaveEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GuildMemberRemoveEventHandler extends EventHandler {

    public GuildMemberRemoveEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Guild guild = (Guild) identity.getGuild(json.getString("guild_id"));
        Member member = guild.removeMember(json.getJSONObject("user").getString("id"));

        fireEvent(new GuildMemberLeaveEvent(identity, guild, sequence, member));
    }
}
