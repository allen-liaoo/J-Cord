package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class GuildMFAUpdateEvent extends GuildUpdateEvent {

    private final IGuild.MFA oldMFA;

    public GuildMFAUpdateEvent(Identity identity, int sequence, IGuild guild, IGuild.MFA oldMFA) {
        super(identity, sequence, guild);
        this.oldMFA = oldMFA;
    }

    public Guild.MFA getNewMFA() {
        return guild.getMFALevel();
    }

    public Guild.MFA getOldMFA() {
        return oldMFA;
    }

}
