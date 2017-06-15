package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class GuildDeleteEvent extends GuildEvent {

    private final OffsetDateTime timeStamp;

    public GuildDeleteEvent(IdentityImpl identity, Guild guild, int sequence, OffsetDateTime timeStamp) {
        super(identity, guild, sequence);
        this.timeStamp = timeStamp;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }
}
