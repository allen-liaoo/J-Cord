package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class GuildDeleteEvent extends GuildEvent {

    private final OffsetDateTime timeStamp;

    public GuildDeleteEvent(Identity identity, int sequence, IGuild guild, OffsetDateTime timeStamp) {
        super(identity, sequence, guild);
        this.timeStamp = timeStamp;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }
}
