package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.member.GuildMemberJoinEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GuildMemberAddEventHandler extends EventHandler {

    public GuildMemberAddEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Member member = builder.buildMemberById(json, json.getString("guild_id"));
        ((Guild) member.getGuild()).addMember(member);

        dispatchEvent(new GuildMemberJoinEvent(identity, (Guild) member.getGuild(), sequence, member));
    }

}
