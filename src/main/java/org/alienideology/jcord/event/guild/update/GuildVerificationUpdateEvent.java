package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildVerificationUpdateEvent extends GuildUpdateEvent {

    private final IGuild.Verification oldVerification;

    public GuildVerificationUpdateEvent(Identity identity, int sequence, IGuild guild, IGuild.Verification oldVerification) {
        super(identity, sequence, guild);
        this.oldVerification = oldVerification;
    }

    public IGuild.Verification getNewVerification() {
        return guild.getVerificationLevel();
    }

    public IGuild.Verification getOldVerification() {
        return oldVerification;
    }

}
