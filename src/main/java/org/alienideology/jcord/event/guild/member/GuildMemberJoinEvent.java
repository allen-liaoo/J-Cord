package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildMemberJoinEvent extends GuildMemberEvent {

    public GuildMemberJoinEvent(Identity identity, IGuild guild, int sequence, IMember member) {
        super(identity, guild, sequence, member);
    }
}
