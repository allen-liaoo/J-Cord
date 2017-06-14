package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.internal.object.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.Guild;

/**
 * @author AlienIdeology
 */
public class GuildMFAUpdateEvent extends GuildUpdateEvent {

    public GuildMFAUpdateEvent(Identity identity, Guild newGuild, int sequence, Guild oldGuild) {
        super(identity, newGuild, sequence, oldGuild);
    }

    public Guild.MFA getNewMFA() {
        return guild.getMFALevel();
    }

    public Guild.MFA getOldMFA() {
        return oldGuild.getMFALevel();
    }

}
