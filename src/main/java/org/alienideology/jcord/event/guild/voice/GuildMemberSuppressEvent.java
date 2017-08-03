package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

/**
 * @author AlienIdeology
 */
public class GuildMemberSuppressEvent extends GuildMemberVoiceEvent {

    public GuildMemberSuppressEvent(IdentityImpl identity, int sequence, Guild guild, Member member) {
        super(identity, sequence, guild, member);
    }

    public boolean isSuppressed() {
        return getVoiceState().isSuppressed();
    }

}
