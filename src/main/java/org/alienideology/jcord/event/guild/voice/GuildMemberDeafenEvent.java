package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

/**
 * @author AlienIdeology
 */
public class GuildMemberDeafenEvent extends GuildMemberVoiceEvent {

    public GuildMemberDeafenEvent(IdentityImpl identity, int sequence, Guild guild, Member member) {
        super(identity, sequence, guild, member);
    }

    public boolean isDeafened() {
        return getVoiceState().isDeafened();
    }

}
