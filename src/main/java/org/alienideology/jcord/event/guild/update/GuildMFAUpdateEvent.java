package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class GuildMFAUpdateEvent extends GuildUpdateEvent {

    public GuildMFAUpdateEvent(IdentityImpl identity, Guild newGuild, int sequence, Guild oldGuild) {
        super(identity, newGuild, sequence, oldGuild);
    }

    public Guild.MFA getNewMFA() {
        return guild.getMFALevel();
    }

    public Guild.MFA getOldMFA() {
        return oldGuild.getMFALevel();
    }

}
