package org.alienideology.jcord.internal.event.guild;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.event.Event;
import org.alienideology.jcord.internal.object.Guild;

/**
 * Guild Events - Any events that happens under a guild
 * @author AlienIdeology
 */
public abstract class GuildEvent extends Event {

    protected Guild guild;

    public GuildEvent(Identity identity, Guild guild, int sequence) {
        super(identity, sequence);
        this.guild = guild;
    }

    public Guild getGuild() {
        return guild;
    }

}
