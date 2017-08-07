package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildAFKTimeoutUpdateEvent extends GuildUpdateEvent {

    private final IGuild.AFKTimeout oldTimeout;

    public GuildAFKTimeoutUpdateEvent(Identity identity, int sequence, IGuild guild, IGuild.AFKTimeout oldTimeout) {
        super(identity, sequence, guild);
        this.oldTimeout = oldTimeout;
    }

    public IGuild.AFKTimeout getNewAFKTimeout() {
        return guild.getAfkTimeout();
    }

    public IGuild.AFKTimeout getOldAFKTimeout() {
        return oldTimeout;
    }

}
