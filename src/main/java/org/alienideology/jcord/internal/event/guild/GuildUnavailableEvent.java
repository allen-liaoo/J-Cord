package org.alienideology.jcord.internal.event.guild;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.object.Guild;

/**
 * @author AlienIdeology
 */
public class GuildUnavailableEvent extends GuildEvent {

    public GuildUnavailableEvent(Identity identity, Guild guild, int sequence) {
        super(identity, guild, sequence);
    }

}
