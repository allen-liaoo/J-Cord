package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildMemberSelfMuteEvent extends GuildMemberVoiceEvent {

    public GuildMemberSelfMuteEvent(Identity identity, int sequence, IGuild guild, IMember member) {
        super(identity, sequence, guild, member);
    }

    public boolean isSelfMuted() {
        return getVoiceState().isSelfMuted();
    }

}
