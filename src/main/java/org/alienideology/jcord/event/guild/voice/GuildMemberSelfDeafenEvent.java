package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildMemberSelfDeafenEvent extends GuildMemberVoiceEvent {

    public GuildMemberSelfDeafenEvent(Identity identity, int sequence, IGuild guild, IMember member) {
        super(identity, sequence, guild, member);
    }

    public boolean isSelfDeafened() {
        return getVoiceState().isSelfDeafened();
    }

}
