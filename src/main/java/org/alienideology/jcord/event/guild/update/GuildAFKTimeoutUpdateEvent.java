package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class GuildAFKTimeoutUpdateEvent extends GuildUpdateEvent {

    public GuildAFKTimeoutUpdateEvent(IdentityImpl identity, Guild newGuild, int sequence, Guild oldGuild) {
        super(identity, newGuild, sequence, oldGuild);
    }

    public IGuild.AFKTimeout getNewAFKTimeout() {
        return guild.getAfkTimeout();
    }

    public IGuild.AFKTimeout getOldAFKTimeout() {
        return oldGuild.getAfkTimeout();
    }

}
