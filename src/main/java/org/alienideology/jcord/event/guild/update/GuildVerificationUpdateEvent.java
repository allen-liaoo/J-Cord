package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.internal.object.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.Guild;

/**
 * @author AlienIdeology
 */
public class GuildVerificationUpdateEvent extends GuildUpdateEvent {

    public GuildVerificationUpdateEvent(Identity identity, Guild newGuild, int sequence, Guild oldGuild) {
        super(identity, newGuild, sequence, oldGuild);
    }

    public Guild.Verification getNewVerification() {
        return guild.getVerificationLevel();
    }

    public Guild.Verification getOldVerification() {
        return oldGuild.getVerificationLevel();
    }

}
