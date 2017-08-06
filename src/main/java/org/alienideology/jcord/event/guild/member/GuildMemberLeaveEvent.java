package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildMemberLeaveEvent extends GuildMemberEvent {

    public GuildMemberLeaveEvent(Identity identity, IGuild guild, int sequence, IMember member) {
        super(identity, guild, sequence, member);
    }

}
