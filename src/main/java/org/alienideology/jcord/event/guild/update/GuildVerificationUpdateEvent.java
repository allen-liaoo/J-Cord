package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class GuildVerificationUpdateEvent extends GuildUpdateEvent {

    public GuildVerificationUpdateEvent(IdentityImpl identity, int sequence, Guild newGuild, Guild oldGuild) {
        super(identity, sequence, newGuild, oldGuild);
    }

    public Guild.Verification getNewVerification() {
        return guild.getVerificationLevel();
    }

    public Guild.Verification getOldVerification() {
        return oldGuild.getVerificationLevel();
    }

}
