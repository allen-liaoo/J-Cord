package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildUnavailableEvent extends GuildEvent {

    public GuildUnavailableEvent(Identity identity, int sequence, IGuild guild) {
        super(identity, sequence, guild);
    }

}
