package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.event.guild.GuildEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

/**
 * @author AlienIdeology
 */
public class GuildMemberEvent extends GuildEvent {

    protected Member member;

    public GuildMemberEvent(IdentityImpl identity, Guild guild, int sequence, Member member) {
        super(identity, guild, sequence);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

}
