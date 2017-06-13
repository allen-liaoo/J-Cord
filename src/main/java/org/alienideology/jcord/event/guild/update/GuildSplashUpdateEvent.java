package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.object.Guild;

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
