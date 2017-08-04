package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

/**
 * @author AlienIdeology
 */
public class GuildMemberJoinEvent extends GuildMemberEvent {

    public GuildMemberJoinEvent(IdentityImpl identity, Guild guild, int sequence, Member member) {
        super(identity, guild, sequence, member);
    }
}
