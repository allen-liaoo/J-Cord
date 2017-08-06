package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildEvent;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildMemberEvent extends GuildEvent {

    protected IMember member;

    public GuildMemberEvent(Identity identity, IGuild guild, int sequence, IMember member) {
        super(identity, sequence, guild);
        this.member = member;
    }

    public IMember getMember() {
        return member;
    }

}
