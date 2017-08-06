package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildMemberDeafenByServerEvent extends GuildMemberVoiceEvent {

    public GuildMemberDeafenByServerEvent(Identity identity, int sequence, IGuild guild, IMember member) {
        super(identity, sequence, guild, member);
    }

    public boolean isDeafenedByServer() {
        return getVoiceState().isDeafenedByServer();
    }

}
