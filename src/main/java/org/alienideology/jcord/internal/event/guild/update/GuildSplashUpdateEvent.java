package org.alienideology.jcord.internal.event.guild.update;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.Guild;

/**
 * @author AlienIdeology
 */
public class GuildSplashUpdateEvent extends GuildUpdateEvent {

    public GuildSplashUpdateEvent(Identity identity, Guild newGuild, int sequence, Guild oldGuild) {
        super(identity, newGuild, sequence, oldGuild);
    }

    public String getNewSplash() {
        return guild.getSplash();
    }

    public String getOldSplash() {
        return oldGuild.getSplash();
    }
}
