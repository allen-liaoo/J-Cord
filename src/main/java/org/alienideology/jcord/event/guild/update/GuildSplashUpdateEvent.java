package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class GuildSplashUpdateEvent extends GuildUpdateEvent {

    public GuildSplashUpdateEvent(IdentityImpl identity, int sequence, Guild newGuild, Guild oldGuild) {
        super(identity, sequence, newGuild, oldGuild);
    }

    public String getNewSplash() {
        return guild.getSplash();
    }

    public String getOldSplash() {
        return oldGuild.getSplash();
    }
}
