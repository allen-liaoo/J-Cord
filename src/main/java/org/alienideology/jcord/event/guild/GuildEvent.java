package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.internal.object.Guild;

/**
 * Guild Events - Any events that happens under a guild
 * @author AlienIdeology
 */
public abstract class GuildEvent extends Event {

    protected Guild guild;

    public GuildEvent(IdentityImpl identity, Guild guild, int sequence) {
        super(identity, sequence);
        this.guild = guild;
    }

    public Guild getGuild() {
        return guild;
    }

}
