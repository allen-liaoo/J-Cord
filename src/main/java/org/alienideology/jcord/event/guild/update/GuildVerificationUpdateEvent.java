package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.Guild;

/**
 * @author AlienIdeology
 */
public class GuildVerificationUpdateEvent extends GuildUpdateEvent {

    public GuildVerificationUpdateEvent(IdentityImpl identity, Guild newGuild, int sequence, Guild oldGuild) {
        super(identity, newGuild, sequence, oldGuild);
    }

    public Guild.Verification getNewVerification() {
        return guild.getVerificationLevel();
    }

    public Guild.Verification getOldVerification() {
        return oldGuild.getVerificationLevel();
    }

}
