package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * Guild Events - Any events that happens under a guild
 * @author AlienIdeology
 */
public class GuildEvent extends Event {

    protected final IGuild guild;

    public GuildEvent(Identity identity, int sequence, IGuild guild) {
        super(identity, sequence);
        this.guild = guild;
    }

    public IGuild getGuild() {
        return guild;
    }

}
