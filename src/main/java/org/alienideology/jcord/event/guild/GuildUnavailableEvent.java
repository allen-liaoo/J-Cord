package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Guild;

/**
 * @author AlienIdeology
 */
public class GuildUnavailableEvent extends GuildEvent {

    public GuildUnavailableEvent(Identity identity, Guild guild, int sequence) {
        super(identity, guild, sequence);
    }

}
