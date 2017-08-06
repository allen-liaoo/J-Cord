package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildMemberMuteByServerEvent extends GuildMemberVoiceEvent {

    public GuildMemberMuteByServerEvent(Identity identity, int sequence, IGuild guild, IMember member) {
        super(identity, sequence, guild, member);
    }

    public boolean isMutedByServer() {
        return getVoiceState().isMutedByServer();
    }

}
