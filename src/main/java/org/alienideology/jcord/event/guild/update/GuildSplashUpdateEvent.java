package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildSplashUpdateEvent extends GuildUpdateEvent {

    private final String oldSplash;

    public GuildSplashUpdateEvent(Identity identity, int sequence, IGuild guild, String oldSplash) {
        super(identity, sequence, guild);
        this.oldSplash = oldSplash;
    }

    public String getNewSplash() {
        return guild.getSplash();
    }

    public String getOldSplash() {
        return oldSplash;
    }
}
