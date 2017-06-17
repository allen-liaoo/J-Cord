package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.member.GuildMemberBanEvent;
import org.alienideology.jcord.event.guild.member.GuildUnbanEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.object.user.User;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GuildBanEventHandler extends EventHandler {

    private boolean isBanned;

    public GuildBanEventHandler(IdentityImpl identity, boolean isBanned) {
        super(identity);
        this.isBanned = true;
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Guild guild = (Guild) identity.getGuild(json.getString("guild_id"));

        if (isBanned) {
            Member member = (Member) guild.getMember(json.getString("id"));
            guild.removeMember(member.getId());
            fireEvent(new GuildMemberBanEvent(identity, guild, sequence, member));
        } else {
            User user = builder.buildUser(json);
            fireEvent(new GuildUnbanEvent(identity, guild, sequence, user));
        }
    }

}